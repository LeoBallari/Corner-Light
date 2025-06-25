// mpu_handler.h

#ifndef MPU_HANDLER_H
#define MPU_HANDLER_H

#include "shared_data.h"     // Para acceder a variables globales y el mutex
#include <Arduino.h>
#include <Wire.h>
#include <Adafruit_MPU6050.h>
#include <Adafruit_Sensor.h>
#include "config_manager.h"  // Para guardar los offsets
#include "relay_control.h"   // Para calCLight

// Declaración de la instancia del MPU
extern Adafruit_MPU6050 mpu;

// Variables de tiempo para cálculos internos de la tarea MPU
extern long tiempo_prev_imu;    // ¡CORREGIDO! Solo 'extern' aquí
extern long dt_imu;             // ¡CORREGIDO! Solo 'extern' aquí

// Prototipos de funciones
float mapearValorConMap(float entrada);
bool mpu_init_sensor();
void mpu_calibrate_sensor_wrapper();  // Función de calibración llamada desde fuera de la tarea
void mpu_calibrate_sensor_internal(); // Función interna de calibración (sin mutex)
void mpu_read_and_process_data();
void mpu_task_core0(void *pvParameters); // Tarea FreeRTOS para el MPU

// Funciones auxiliares de filtrado
float EMALowPassFilter(float value);
extern float EMA_LP_filter_val; // ¡CORREGIDO! Solo 'extern' aquí

// Funciones de monitoreo (para Serial)
void MonitorROLL(float accel_angle_ema_filtered, float gyro_rate_post_deadband, bool is_moving_linearly, float accel_magnitude);
void MonitorRAW(sensors_event_t a, sensors_event_t g); 

#define GRAVEDAD 9.81 // Usar 9.81 m/s^2 como valor de gravedad

#endif // MPU_HANDLER_H