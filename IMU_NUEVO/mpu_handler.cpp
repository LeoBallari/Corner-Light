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

void mpu_task_core0(void *pvParameters) {
    Serial.println("[Core 0] MPU_Task: Iniciando MPU6050...");
    Wire.begin();
    Wire.setClock(400000L);

    if (!mpu_init_sensor()) {
        Serial.println("[Core 0] MPU_Task: Fallo en la inicializacion del MPU. Deteniendo tarea.");
        vTaskDelete(NULL);
    }

    tiempo_prev_imu = millis();

    for (;;) {
        if (xSemaphoreTake(xMutex_MPU_Data, portMAX_DELAY) == pdTRUE) {
            if (g_calibrar_flag) {
                g_calibrar_flag = false;
                xSemaphoreGive(xMutex_MPU_Data);
                mpu_calibrate_sensor_internal();
            } else {
                xSemaphoreGive(xMutex_MPU_Data);
            }
        }

        mpu_read_and_process_data();

        if (xSemaphoreTake(xMutex_MPU_Data, portMAX_DELAY) == pdTRUE) {
            if (g_modo == MODO_AUTOMATICO) {
                if (g_eje == 1) {
                    if ((g_Sal_IZQ_forzada == 0) && (g_Sal_DER_forzada == 1)) {
                        g_salidaLuzAuxIzq = SAL_ACT;
                        g_salidaLuzAuxDer = SalidaDerecha2(g_Roll, g_aLim, g_aLim2);
                    }
                    if ((g_Sal_DER_forzada == 0) && (g_Sal_IZQ_forzada == 1)) {
                        g_salidaLuzAuxDer = SAL_ACT;
                        g_salidaLuzAuxIzq = SalidaIzquierda2(g_Roll, g_aLim * -1, g_aLim2 * -1);
                    }
                    if ((g_Sal_DER_forzada == 0) && (g_Sal_IZQ_forzada == 0)) {
                        g_salidaLuzAuxIzq = SAL_ACT;
                        g_salidaLuzAuxDer = SAL_ACT;
                    }
                    if ((g_Sal_DER_forzada == 1) && (g_Sal_IZQ_forzada == 1)) {
                        g_salidaLuzAuxIzq = SalidaIzquierda2(g_Roll, g_aLim * -1, g_aLim2 * -1);
                        g_salidaLuzAuxDer = SalidaDerecha2(g_Roll, g_aLim, g_aLim2);
                    }
                }
                else if (g_eje == 2) {
                    if ((g_Sal_IZQ_forzada == 0) && (g_Sal_DER_forzada == 1)) {
                        g_salidaLuzAuxIzq = SAL_ACT;
                        g_salidaLuzAuxDer = SalidaDerecha2(g_Roll, g_aLim, g_aLim2);
                    }
                    if ((g_Sal_DER_forzada == 0) && (g_Sal_IZQ_forzada == 1)) {
                        g_salidaLuzAuxDer = SAL_ACT;
                        g_salidaLuzAuxIzq = SalidaIzquierda2(g_Roll, g_aLim * -1, g_aLim2 * -1);
                    }
                    if ((g_Sal_DER_forzada == 0) && (g_Sal_IZQ_forzada == 0)) {
                        g_salidaLuzAuxIzq = SAL_ACT;
                        g_salidaLuzAuxDer = SAL_ACT;
                    }
                    if ((g_Sal_DER_forzada == 1) && (g_Sal_IZQ_forzada == 1)) {
                        g_salidaLuzAuxIzq = SalidaIzquierda2(g_Roll, g_aLim * -1, g_aLim2 * -1);
                        g_salidaLuzAuxDer = SalidaDerecha2(g_Roll, g_aLim, g_aLim2);
                    }
                }
            } else if (g_modo == MODO_MANUAL) {
                activarAmbasSalidas();
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

    sensors_event_t a, g, temp;

    Serial.print("Recolectando datos de calibracion (");
    for (int i = 0; i < Muestreo; i++) {
        mpu.getEvent(&a, &g, &temp);

        sumAccX += a.acceleration.x;
        sumAccY += a.acceleration.y;
        sumAccZ += a.acceleration.z;
        sumGyroX += g.gyro.x;
        sumGyroY += g.gyro.y;
        sumGyroZ += g.gyro.z;
        Serial.print(".");
        vTaskDelay(2);
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

    sensors_event_t a, g, temp;
    mpu.getEvent(&a, &g, &temp);

    long current_time = millis();
    dt_imu = current_time - tiempo_prev_imu;
    tiempo_prev_imu = current_time;

    if (dt_imu == 0) dt_imu = 1;

    float local_accel_angle_ema_filtered = 0.0;
    float local_gyro_rate_post_deadband = 0.0;
    bool local_is_moving_linearly = false;
    float local_accel_magnitude = 0.0;

    if (xSemaphoreTake(xMutex_MPU_Data, portMAX_DELAY) == pdTRUE) {
        g_accelX = a.acceleration.x - (g_ax_offset);
        g_accelY = a.acceleration.y - (g_ay_offset);
        g_accelZ = a.acceleration.z - (g_az_offset);

        g_gyroX = g.gyro.x - (g_gx_offset);
        g_gyroY = g.gyro.y - (g_gy_offset);
        g_gyroZ = g.gyro.z - (g_gz_offset);

        const float GYRO_NOISE_THRESHOLD = 5.0;
        if (fabs(g_gyroX) < GYRO_NOISE_THRESHOLD) g_gyroX = 0.0;
        if (fabs(g_gyroY) < GYRO_NOISE_THRESHOLD) g_gyroY = 0.0;
        if (fabs(g_gyroZ) < GYRO_NOISE_THRESHOLD) g_gyroZ = 0.0;
        
        if (g_eje == 1) {
            local_gyro_rate_post_deadband = g_gyroY;
        } else if (g_eje == 2) {
            local_gyro_rate_post_deadband = g_gyroX;
        }

        local_accel_magnitude = sqrt(g_accelX * g_accelX + g_accelY * g_accelY + g_accelZ * g_accelZ);
        const float ACCEL_MAGNITUDE_THRESHOLD = 0.5;
        if (fabs(local_accel_magnitude - GRAVEDAD) > ACCEL_MAGNITUDE_THRESHOLD) {
            local_is_moving_linearly = true; // ACA es donde digo que tengo movimiento lateral
        }
        
        float accel_angle_raw;
        float calib_offset_temp_val;

        if (g_eje == 1) {
            accel_angle_raw = atan2(g_accelX, g_accelZ) * RAD_TO_DEG;
            calib_offset_temp_val = g_calib_y;
        } else if (g_eje == 2) {
            accel_angle_raw = atan2(g_accelY, g_accelZ) * RAD_TO_DEG;
            calib_offset_temp_val = g_calib_x;
        } else {
            accel_angle_raw = 0;
            calib_offset_temp_val = 0;
            g_Roll = 0;
        }

        local_accel_angle_ema_filtered = EMALowPassFilter(accel_angle_raw);
        
        if (!local_is_moving_linearly) {
            #ifdef USAR_MADGWICK
                // Implementación del filtro de Madgwick
                static float q0 = 1.0f, q1 = 0.0f, q2 = 0.0f, q3 = 0.0f;
                float recipNorm;
                float s0, s1, s2, s3;
                float qDot1, qDot2, qDot3, qDot4;
                float _2q0, _2q1, _2q2, _2q3, _4q0, _4q1, _4q2, _8q1, _8q2, q0q0, q1q1, q2q2, q3q3;

                float ax = g_accelX / GRAVEDAD;
                float ay = g_accelY / GRAVEDAD;
                float az = g_accelZ / GRAVEDAD;
                float gx = radians(g_gyroX);
                float gy = radians(g_gyroY);
                float gz = radians(g_gyroZ);

                recipNorm = 1.0f / sqrt(ax * ax + ay * ay + az * az);
                ax *= recipNorm;
                ay *= recipNorm;
                az *= recipNorm;

                _2q0 = 2.0f * q0;
                _2q1 = 2.0f * q1;
                _2q2 = 2.0f * q2;
                _2q3 = 2.0f * q3;
                _4q0 = 4.0f * q0;
                _4q1 = 4.0f * q1;
                _4q2 = 4.0f * q2;
                _8q1 = 8.0f * q1;
                _8q2 = 8.0f * q2;
                q0q0 = q0 * q0;
                q1q1 = q1 * q1;
                q2q2 = q2 * q2;
                q3q3 = q3 * q3;

                s0 = _4q0 * q2q2 + _2q2 * ax + _4q0 * q1q1 - _2q1 * ay;
                s1 = _4q1 * q3q3 - _2q3 * ax + 4.0f * q0q0 * q1 - _2q0 * ay - _4q1 + _8q1 * q1q1 + _8q1 * q2q2 + _4q1 * az;
                s2 = 4.0f * q0q0 * q2 + _2q0 * ax + _4q2 * q3q3 - _2q3 * ay - _4q2 + _8q2 * q1q1 + _8q2 * q2q2 + _4q2 * az;
                s3 = 4.0f * q1q1 * q3 - _2q1 * ax + 4.0f * q2q2 * q3 - _2q2 * ay;
                recipNorm = 1.0f / sqrt(s0 * s0 + s1 * s1 + s2 * s2 + s3 * s3);
                s0 *= recipNorm;
                s1 *= recipNorm;
                s2 *= recipNorm;
                s3 *= recipNorm;

                // Agregando..................
                float current_madgwick_beta; // Se define aquí, no necesita ser global
                current_madgwick_beta = g_MADGWICK_BETA;

                if (local_is_moving_linearly) {
                    current_madgwick_beta = g_MADGWICK_BETA_NORMAL;
                } else {
                    current_madgwick_beta = g_MADGWICK_BETA;
                }
                //.............................

                qDot1 = 0.5f * (-q1 * gx - q2 * gy - q3 * gz) - current_madgwick_beta * s0;
                qDot2 = 0.5f * (q0 * gx + q2 * gz - q3 * gy) - current_madgwick_beta * s1;
                qDot3 = 0.5f * (q0 * gy - q1 * gz + q3 * gx) - current_madgwick_beta * s2;
                qDot4 = 0.5f * (q0 * gz + q1 * gy - q2 * gx) - current_madgwick_beta * s3;

                // qDot1 = 0.5f * (-q1 * gx - q2 * gy - q3 * gz) - g_MADGWICK_BETA * s0;
                // qDot2 = 0.5f * (q0 * gx + q2 * gz - q3 * gy) - g_MADGWICK_BETA * s1;
                // qDot3 = 0.5f * (q0 * gy - q1 * gz + q3 * gx) - g_MADGWICK_BETA * s2;
                // qDot4 = 0.5f * (q0 * gz + q1 * gy - q2 * gx) - g_MADGWICK_BETA * s3;

                q0 += qDot1 * (dt_imu / 1000.0f);
                q1 += qDot2 * (dt_imu / 1000.0f);
                q2 += qDot3 * (dt_imu / 1000.0f);
                q3 += qDot4 * (dt_imu / 1000.0f);

                recipNorm = 1.0f / sqrt(q0 * q0 + q1 * q1 + q2 * q2 + q3 * q3);
                q0 *= recipNorm;
                q1 *= recipNorm;
                q2 *= recipNorm;
                q3 *= recipNorm;

                float roll = atan2(q0*q1 + q2*q3, 0.5f - q1*q1 - q2*q2) * RAD_TO_DEG;
                g_Roll = roll;
            #else

                // Agregando..................
                float current_complementario_beta; // Se define aquí, no necesita ser global
                current_complementario_beta = g_FILTRO;
                if (local_is_moving_linearly) {
                    current_complementario_beta = 0.9999;
                } 
                //.............................

                g_Roll = current_complementario_beta * (g_Roll + (local_gyro_rate_post_deadband / g_GYRO_LB) * (dt_imu / 1000.0)) + 
                         (1 - current_complementario_beta) * local_accel_angle_ema_filtered;
            #endif

            g_Roll += calib_offset_temp_val;
            g_Roll = g_Roll + (g_gyroZ * g_YAW_ALPHA);

            if (g_invertir) {
                g_Roll = g_Roll * -1;
            }
        }

        // Llamar a MonitorROLL antes de liberar el mutex
        MonitorROLL(local_accel_angle_ema_filtered, 
                   local_gyro_rate_post_deadband, 
                   local_is_moving_linearly, 
                   local_accel_magnitude);

        //MonitorRAW(a, g); // Llama a la función MonitorRAW, pasándole los datos 'a' y 'g'

        xSemaphoreGive(xMutex_MPU_Data);

    } else {
        Serial.println("ERROR: No se pudo tomar el mutex en mpu_read_and_process_data().");
    }
}

float EMALowPassFilter(float value) {
    EMA_LP_filter_val = g_EMA_ALPHA * value + (1 - g_EMA_ALPHA) * EMA_LP_filter_val;
    return EMA_LP_filter_val;
}

void MonitorROLL(float accel_angle_ema_filtered, float gyro_rate_post_deadband, bool is_moving_linearly, float accel_magnitude) {
    // Columnas para el Serial Plotter:
    // 1. g_Roll (Ángulo Final)
    // 2. accel_angle_ema_filtered 
    //// 3. gyro_rate_post_deadband
    //// 4. is_moving_linearly
    // 5. accel_magnitude
    // 6. Tipo de filtro (COMP/MADG)
    // 7. Parámetro del filtro (con 3 decimales)
    // 8. Límite Inferior (-20)
    // 9. Límite Superior (20)

    Serial.print(g_Roll, 3); Serial.print("\t");
    Serial.print(accel_angle_ema_filtered, 3); Serial.print("\t");
    //Serial.print(gyro_rate_post_deadband, 3); Serial.print("\t");
    //Serial.print(is_moving_linearly ? 1 : 0); Serial.print("\t");
    Serial.print(accel_magnitude, 3); Serial.print("\t");
    
    #ifdef USAR_MADGWICK
        Serial.print("MADG\t");                     // Identificador Madgwick
        Serial.print(g_MADGWICK_BETA, 3);           // Beta con 3 decimales
        Serial.print("\t");                             // Separador
        Serial.print(g_MADGWICK_BETA_NORMAL, 3);           // Beta con 3 decimales
    #else
        Serial.print("COMP\t");                     // Identificador Complementario
        Serial.print(g_FILTRO, 3);                  // Filtro con 3 decimales
    #endif
    Serial.print("\t");                             // Separador
    
    Serial.print(-20); Serial.print("\t");
    Serial.print(20); Serial.println();
}

void MonitorRAW(sensors_event_t a, sensors_event_t g){ // Se pasan 'a' y 'g' como argumentos
    // Asegúrate de que g_ax_offset, g_ay_offset, etc., sean variables globales o accesibles.
    // Asumo que ya lo son por tu código anterior.
    Serial.print(a.acceleration.x - g_ax_offset); Serial.print("\t");
    Serial.print(a.acceleration.y - g_ay_offset); Serial.print("\t");
    Serial.print(a.acceleration.z - g_az_offset); Serial.print("\t");

    Serial.print(g.gyro.x - g_gx_offset); Serial.print("\t");
    Serial.print(g.gyro.y - g_gy_offset); Serial.print("\t");
    Serial.print(g.gyro.z - g_gz_offset); Serial.print("\t");

    Serial.println();
}
