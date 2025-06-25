// mpu_handler.cpp

#include "mpu_handler.h"
#include <Arduino.h>
#include <math.h>
#include <Wire.h>
#include <Adafruit_MPU6050.h>
#include <Adafruit_Sensor.h>
#include "shared_data.h"
#include "config_manager.h"
#include "bluetooth_handler.h"

Adafruit_MPU6050 mpu;
long tiempo_prev_imu = 0;
long dt_imu = 0;
float EMA_LP_filter_val = 0;

// Definir el umbral de aceleración aquí para facilitar su ajuste
// Este valor puede requerir ajuste experimental (ej. 1.0 a 2.5 m/s^2)
float ACCEL_MAGNITUDE_THRESHOLD = 1.5f; 

// Definir el coeficiente de alta confianza en el giroscopio para el filtro complementario
const float COMP_FILTER_HIGH_GYRO_CONFIDENCE_COEFF = 0.9999f;

void mpu_task_core0(void *pvParameters) {
    Serial.println("[Core 0] MPU_Task: Iniciando MPU6050...");
    Wire.begin();
    Wire.setClock(400000L); // Usar 400kHz para I2C

    if (!mpu_init_sensor()) {
        Serial.println("[Core 0] MPU_Task: Fallo en la inicializacion del MPU. Deteniendo tarea.");
        vTaskDelete(NULL); // Terminar la tarea si el MPU no se inicializa
        return; // Salir de la función
    }

    tiempo_prev_imu = millis();

    for (;;) {
        // Manejo de la solicitud de calibración
        bool needs_calibration = false;
        if (xSemaphoreTake(xMutex_MPU_Data, (TickType_t)10) == pdTRUE) { // Intenta tomar el mutex con timeout
            if (g_calibrar_flag) {
                needs_calibration = true;
                g_calibrar_flag = false; // Resetear el flag DENTRO del mutex
            }
            xSemaphoreGive(xMutex_MPU_Data);
        } // No es crítico si no se puede tomar el mutex aquí inmediatamente para leer el flag

        if (needs_calibration) {
            mpu_calibrate_sensor_internal(); // Llamar a calibración fuera del mutex para no bloquear otras tareas
        }

        mpu_read_and_process_data(); // Leer y procesar datos del MPU

        // Lógica de control de relés basada en g_Roll y g_modo
        if (xSemaphoreTake(xMutex_MPU_Data, portMAX_DELAY) == pdTRUE) {
            if (g_modo == MODO_AUTOMATICO) {
                bool izq_auto = true;
                bool der_auto = true;

                if (g_Sal_IZQ_forzada == 0) { // 0 significa Forzar ON
                    g_salidaLuzAuxIzq = SAL_ACT;
                    izq_auto = false;
                }
                if (g_Sal_DER_forzada == 0) { // 0 significa Forzar ON
                    g_salidaLuzAuxDer = SAL_ACT;
                    der_auto = false;
                }

                if (izq_auto) {
                     g_salidaLuzAuxIzq = SalidaIzquierda2(g_Roll, g_aLim * -1.0f, g_aLim2 * -1.0f);
                }
                if (der_auto) {
                    g_salidaLuzAuxDer = SalidaDerecha2(g_Roll, g_aLim, g_aLim2);
                }

            } else if (g_modo == MODO_MANUAL) {
                g_salidaLuzAuxIzq = (g_Sal_IZQ_forzada == 0) ? SAL_ACT : SAL_DESACT;
                g_salidaLuzAuxDer = (g_Sal_DER_forzada == 0) ? SAL_ACT : SAL_DESACT;
            }

            digitalWrite(PIN_SALIDA_IZQ, !g_salidaLuzAuxIzq);
            digitalWrite(PIN_SALIDA_DER, !g_salidaLuzAuxDer);

            xSemaphoreGive(xMutex_MPU_Data);
        }

        vTaskDelay(g_ImuDelay / portTICK_PERIOD_MS);
    }
}

boolean mpu_init_sensor() {
    Serial.println("[Core 0] MPU_Task: Inicializando MPU6050...");
    if (!mpu.begin(0x68)) {
        Serial.println("[Core 0] MPU_Task: Fallo al encontrar MPU6050 en 0x68. Reintentando con 0x69...");
        if (!mpu.begin(0x69)) {
            Serial.println("[Core 0] MPU_Task: Error: Fallo al encontrar el chip MPU6050. Verifica las conexiones y la direccion I2C.");
            return false;
        }
    }
    Serial.println("[Core 0] MPU_Task: MPU6050 encontrado y configurado.");
    
    mpu.setAccelerometerRange(MPU6050_RANGE_8_G); 
    mpu.setFilterBandwidth(MPU6050_BAND_5_HZ);
    mpu.setCycleRate(MPU6050_CYCLE_5_HZ);


    return true;
}

void mpu_calibrate_sensor_wrapper() {
    if (xSemaphoreTake(xMutex_MPU_Data, portMAX_DELAY) == pdTRUE) {
        g_calibrar_flag = true;
        xSemaphoreGive(xMutex_MPU_Data);
        Serial.println("Solicitud de calibracion enviada a la tarea MPU.");
    }
}

void mpu_calibrate_sensor_internal() {
    Serial.println("Iniciando calibracion del MPU6050...");
    Serial.println("Asegurese de que el sensor este completamente ESTACIONARIO y HORIZONTAL para el acelerometro.");
    delay(3000); 

    calCLight(); 

    const int Muestreo = 1000; 
    float sumAccX = 0, sumAccY = 0, sumAccZ = 0;
    float sumGyroX = 0, sumGyroY = 0, sumGyroZ = 0;

    sensors_event_t a, g, temp_event; 

    Serial.print("Recolectando datos de calibracion (");
    for (int i = 0; i < Muestreo; i++) {
        mpu.getEvent(&a, &g, &temp_event);

        sumAccX += a.acceleration.x;
        sumAccY += a.acceleration.y;
        sumAccZ += a.acceleration.z;
        sumGyroX += g.gyro.x;
        sumGyroY += g.gyro.y;
        sumGyroZ += g.gyro.z;
        
        if (i % (Muestreo / 20) == 0) { 
             Serial.print(".");
        }
        vTaskDelay(2 / portTICK_PERIOD_MS); 
    }
    Serial.println(")");

    g_ax_offset = sumAccX / Muestreo;
    g_ay_offset = sumAccY / Muestreo;
    g_az_offset = (sumAccZ / Muestreo) - GRAVEDAD; 

    g_gx_offset = sumGyroX / Muestreo;
    g_gy_offset = sumGyroY / Muestreo;
    g_gz_offset = sumGyroZ / Muestreo;

    g_calib_x = 0;
    g_calib_y = 0;

    calCLight(); 
    config_save_profile(); 

    Serial.println("Calibracion del MPU6050 completada y offsets guardados:");
    Serial.printf("Accel Offsets: X: %.3f, Y: %.3f, Z: %.3f\n", g_ax_offset, g_ay_offset, g_az_offset);
    Serial.printf("Gyro Offsets: X: %.3f, Y: %.3f, Z: %.3f\n", g_gx_offset, g_gy_offset, g_gz_offset);
}

void mpu_read_and_process_data() {
    sensors_event_t a, g, temp_event; 
    mpu.getEvent(&a, &g, &temp_event); 

    long current_time = millis();
    dt_imu = current_time - tiempo_prev_imu;
    tiempo_prev_imu = current_time;

    if (dt_imu == 0) { 
        dt_imu = 1; 
    }

    float local_accel_angle_ema_filtered;
    float local_gyro_rate_post_deadband;
    bool local_is_moving_linearly = false; 
    float local_accel_magnitude;      

    if (xSemaphoreTake(xMutex_MPU_Data, portMAX_DELAY) == pdTRUE) {
        g_accelX = a.acceleration.x - g_ax_offset;
        g_accelY = a.acceleration.y - g_ay_offset;
        g_accelZ = a.acceleration.z - g_az_offset;

        g_gyroX = g.gyro.x - g_gx_offset;
        g_gyroY = g.gyro.y - g_gy_offset;
        g_gyroZ = g.gyro.z - g_gz_offset;

        const float GYRO_NOISE_THRESHOLD = 0.1f; 
        if (fabs(g_gyroX) < GYRO_NOISE_THRESHOLD) g_gyroX = 0.0f;
        if (fabs(g_gyroY) < GYRO_NOISE_THRESHOLD) g_gyroY = 0.0f;
        if (fabs(g_gyroZ) < GYRO_NOISE_THRESHOLD) g_gyroZ = 0.0f;
        
        if (g_eje == 1) { 
            local_gyro_rate_post_deadband = g_gyroY;
        } else if (g_eje == 2) { 
            local_gyro_rate_post_deadband = g_gyroX;
        } else {
            local_gyro_rate_post_deadband = 0.0f; 
        }

        local_accel_magnitude = sqrt(g_accelX * g_accelX + g_accelY * g_accelY + g_accelZ * g_accelZ);
        if (fabs(local_accel_magnitude - GRAVEDAD) > ACCEL_MAGNITUDE_THRESHOLD) {
            local_is_moving_linearly = true;
        }
        
        float accel_angle_raw;
        if (g_eje == 1) {
            accel_angle_raw = atan2(g_accelX, g_accelZ) * RAD_TO_DEG;
        } else if (g_eje == 2) {
            accel_angle_raw = atan2(g_accelY, g_accelZ) * RAD_TO_DEG;
        } else {
            accel_angle_raw = 0.0f; 
        }
        local_accel_angle_ema_filtered = EMALowPassFilter(accel_angle_raw);
        
        float calib_offset_temp_val;
        if (g_eje == 1) {
            calib_offset_temp_val = g_calib_y;
        } else if (g_eje == 2) {
            calib_offset_temp_val = g_calib_x;
        } else {
            calib_offset_temp_val = 0.0f;
        }
        
        //ACCEL_MAGNITUDE_THRESHOLD = 1 - (g_EMA_ALPHA * 3 * 1.5f);
        ACCEL_MAGNITUDE_THRESHOLD = mapearValorConMap(g_EMA_ALPHA);


        // --- INICIO CÁLCULO DE g_Roll CON FILTRO SELECCIONADO Y ADAPTACIÓN ---
        #ifdef USAR_MADGWICK
            static float q0 = 1.0f, q1 = 0.0f, q2 = 0.0f, q3 = 0.0f;
            float recipNorm;
            float s0, s1, s2, s3;
            float qDot1, qDot2, qDot3, qDot4;
            float _2q0, _2q1, _2q2, _2q3, _4q0, _4q1, _4q2, _8q1, _8q2, q0q0, q1q1, q2q2, q3q3;

            float ax_norm = g_accelX; float ay_norm = g_accelY; float az_norm = g_accelZ;
            float gx_rad = radians(g_gyroX); float gy_rad = radians(g_gyroY); float gz_rad = radians(g_gyroZ);

            recipNorm = 1.0f / sqrt(ax_norm * ax_norm + ay_norm * ay_norm + az_norm * az_norm);
            if (recipNorm > 0) { 
                ax_norm *= recipNorm; ay_norm *= recipNorm; az_norm *= recipNorm;
            }

            _2q0 = 2.0f * q0; _2q1 = 2.0f * q1; _2q2 = 2.0f * q2; _2q3 = 2.0f * q3;
            _4q0 = 4.0f * q0; _4q1 = 4.0f * q1; _4q2 = 4.0f * q2; 
            _8q1 = 8.0f * q1; _8q2 = 8.0f * q2;
            q0q0 = q0 * q0; q1q1 = q1 * q1; q2q2 = q2 * q2; q3q3 = q3 * q3;

            s0 = _4q0 * q2q2 + _2q2 * ax_norm + _4q0 * q1q1 - _2q1 * ay_norm;
            s1 = _4q1 * q3q3 - _2q3 * ax_norm + 4.0f * q0q0 * q1 - _2q0 * ay_norm - _4q1 + _8q1 * q1q1 + _8q1 * q2q2 + _4q1 * az_norm;
            s2 = 4.0f * q0q0 * q2 + _2q0 * ax_norm + _4q2 * q3q3 - _2q3 * ay_norm - _4q2 + _8q2 * q1q1 + _8q2 * q2q2 + _4q2 * az_norm;
            s3 = 4.0f * q1q1 * q3 - _2q1 * ax_norm + 4.0f * q2q2 * q3 - _2q2 * ay_norm;
            
            recipNorm = 1.0f / sqrt(s0 * s0 + s1 * s1 + s2 * s2 + s3 * s3); 
            if (recipNorm > 0) {
                s0 *= recipNorm; s1 *= recipNorm; s2 *= recipNorm; s3 *= recipNorm;
            }

            float current_madgwick_beta;
            if (local_is_moving_linearly) {
                current_madgwick_beta = g_MADGWICK_BETA_NORMAL; 
            } else {
                current_madgwick_beta = g_MADGWICK_BETA;
            }

            qDot1 = 0.5f * (-q1 * gx_rad - q2 * gy_rad - q3 * gz_rad) - current_madgwick_beta * s0;
            qDot2 = 0.5f * (q0 * gx_rad + q2 * gz_rad - q3 * gy_rad) - current_madgwick_beta * s1;
            qDot3 = 0.5f * (q0 * gy_rad - q1 * gz_rad + q3 * gx_rad) - current_madgwick_beta * s2;
            qDot4 = 0.5f * (q0 * gz_rad + q1 * gy_rad - q2 * gx_rad) - current_madgwick_beta * s3;

            float dt_sec = dt_imu / 1000.0f;
            q0 += qDot1 * dt_sec; q1 += qDot2 * dt_sec; q2 += qDot3 * dt_sec; q3 += qDot4 * dt_sec;

            recipNorm = 1.0f / sqrt(q0 * q0 + q1 * q1 + q2 * q2 + q3 * q3);
            q0 *= recipNorm; q1 *= recipNorm; q2 *= recipNorm; q3 *= recipNorm;

            if (g_eje == 1) { 
                 g_Roll = asin(-2.0f * (q1 * q3 - q0 * q2)) * RAD_TO_DEG;
            } else if (g_eje == 2) { 
                 g_Roll = atan2(2.0f * (q2 * q3 + q0 * q1), q0 * q0 - q1 * q1 - q2 * q2 + q3 * q3) * RAD_TO_DEG;
            } else {
                 g_Roll = 0.0f;
            }
        #else // Usar Filtro Complementario
            float current_filter_coeff;
            if (local_is_moving_linearly) {
                current_filter_coeff = COMP_FILTER_HIGH_GYRO_CONFIDENCE_COEFF; 
            } else {
                current_filter_coeff = g_FILTRO; 
            }
            
            float dt_sec = dt_imu / 1000.0f;
            float gyro_angle_change = (local_gyro_rate_post_deadband / g_GYRO_LB) * dt_sec;
            
            g_Roll = current_filter_coeff * (g_Roll + gyro_angle_change) + \
                     (1.0f - current_filter_coeff) * local_accel_angle_ema_filtered;
        #endif
        // --- FIN CÁLCULO DE g_Roll ---

        g_Roll += calib_offset_temp_val; 

        // Ajustar la escala de g_YAW_ALPHA según sea necesario. Ejemplo:
        // Si g_YAW_ALPHA es un entero que representa un valor que necesita ser escalado (ej. 100 para 0.1)
        // float scaled_yaw_alpha = (float)g_YAW_ALPHA / 1000.0f; // Ajusta 1000.0f al factor de escala correcto
        // g_Roll = g_Roll + (g_gyroZ * scaled_yaw_alpha);
        // Si g_YAW_ALPHA ya es un float con la escala correcta o se maneja como entero intencionalmente:
        g_Roll = g_Roll + (g_gyroZ * g_YAW_ALPHA);

        // if (g_invertir == 1) {
        //     g_Roll = -g_Roll;  // Invierte el signo de g_Roll
        // }

        MonitorROLL(local_accel_angle_ema_filtered, local_gyro_rate_post_deadband, local_is_moving_linearly, local_accel_magnitude);
        // MonitorRAW(a, g); 

        xSemaphoreGive(xMutex_MPU_Data); 

    } else {
        if (Serial) Serial.println("ERROR CRITICO: No se pudo tomar el mutex en mpu_read_and_process_data().");
    }
}

float EMALowPassFilter(float value) {
    EMA_LP_filter_val = (g_EMA_ALPHA * value) + ((1.0f - g_EMA_ALPHA) * EMA_LP_filter_val);
    return EMA_LP_filter_val;
}

void MonitorROLL(float accel_angle_ema_filtered, float gyro_rate_post_deadband, bool is_moving_linearly, float accel_magnitude) {
    if (Serial) { 
        Serial.print(g_Roll, 3); Serial.print("\t");
        Serial.print(accel_angle_ema_filtered, 3); Serial.print("\t");
        Serial.print(is_moving_linearly ? 1 : 0); Serial.print("\t"); 

        Serial.print(ACCEL_MAGNITUDE_THRESHOLD, 3); Serial.print("\t"); 
        Serial.print(g_EMA_ALPHA, 3); Serial.print("\t"); 

        #ifdef USAR_MADGWICK
            //Serial.print("MADG\t");
            Serial.print(g_MADGWICK_BETA, 4);    
        #else
            //Serial.print("COMP\t");
            Serial.print(g_FILTRO, 4);
        #endif

        Serial.print("\t");        
        Serial.print(-20); Serial.print("\t"); 
        Serial.print(20); Serial.println();   
    }
}

void MonitorRAW(sensors_event_t accel_event, sensors_event_t gyro_event){
    if (Serial) {
        Serial.print(accel_event.acceleration.x - g_ax_offset, 3); Serial.print("\t");
        Serial.print(accel_event.acceleration.y - g_ay_offset, 3); Serial.print("\t");
        Serial.print(accel_event.acceleration.z - g_az_offset, 3); Serial.print("\t");

        Serial.print(gyro_event.gyro.x - g_gx_offset, 3); Serial.print("\t");
        Serial.print(gyro_event.gyro.y - g_gy_offset, 3); Serial.print("\t");
        Serial.print(gyro_event.gyro.z - g_gz_offset, 3); Serial.print("\t");
        Serial.println();
    }
}

// Función alternativa usando la función map() de Arduino (CORREGIDA)
float mapearValorConMap(float entrada) {
  // Verificar rango (con un pequeño margen de tolerancia para errores de float)
  if (entrada < 0.019 || entrada > 0.201) {
    Serial.print("Valor fuera de rango: ");
    Serial.println(entrada, 6);
    return -1;
  }
  
  // Convertir a enteros para usar map() - usar más precisión
  long entradaLong = (long)(entrada * 100000);  // Multiplicar por 100000
  long minEntrada = 2000;      // 0.02 * 100000
  long maxEntrada = 20000;     // 0.2 * 100000
  long minSalida = 50000;      // 0.5 * 100000
  long maxSalida = 150000;     // 1.5 * 100000
  
  long resultadoLong = map(entradaLong, minEntrada, maxEntrada, minSalida, maxSalida);
  
  float resultado = resultadoLong / 100000.0;
  
  return resultado;
}