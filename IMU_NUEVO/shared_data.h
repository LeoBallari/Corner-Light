// shared_data.h

#ifndef SHARED_DATA_H
#define SHARED_DATA_H

#include <Arduino.h>
#include <freertos/FreeRTOS.h>
#include <freertos/semphr.h>
#include <BluetoothSerial.h>
#include "Separador.h"

// Handles de semáforos (mutex) para proteger datos compartidos
extern SemaphoreHandle_t xMutex_MPU_Data;

// --- Variables de configuración y estado compartidas ---
// Nota: Algunas de estas ya se gestionaban en config_manager,
// pero es bueno tener un lugar centralizado para las globales.

// Variables de MPU (resultados procesados que las tareas comparten)
extern float g_accelX, g_accelY, g_accelZ;
extern float g_gyroX, g_gyroY, g_gyroZ;
extern float g_Roll; // Este será tu ángulo principal

// Offsets de calibración (se cargan/guardan vía config_manager)
extern float g_ax_offset, g_ay_offset, g_az_offset;
extern float g_gx_offset, g_gy_offset, g_gz_offset;

// Variables para el filtro complementario y control
extern float g_FILTRO; // Peso del Giroscopo en el FILTRO COMPLEMENTARIO
extern float g_EMA_ALPHA; // Factor filtro pasa bajos para el acelerometro
extern int g_YAW_ALPHA; // Factor para compensar inclinacion con el Yaw
extern int g_GYRO_ESC; // Factor en el que divido el dato crudo del giroscopio
extern float g_GYRO_LB;

extern float g_ang_limite; // Angulo limite para el disparo de salidas
extern float g_ang_limite2; // Angulo limite para el apagado de las salidas
extern float g_aLim, g_aLim2; // Valores ajustados para lógica de salida

extern int g_CICLOS_ACT; // Histeresis para la activacion (ciclos * delay)
extern int g_CICLOS_DES; // Histeresis para la desactivacion (ciclos * delay)
extern int g_ImuDelay; // Delay en las lecturas y transmisión

extern int g_eje; // Eje de actuacion (Y o X)
extern bool g_invertir; // Invertir 180 de la ecu sobre el eje Z

extern int g_modo; // Modo automatico o manual
#define MODO_AUTOMATICO 1
#define MODO_MANUAL     0

// Variables de salida de relés
extern byte g_salidaLuzAuxIzq;
extern byte g_salidaLuzAuxDer;
#define SAL_ACT    HIGH
#define SAL_DESACT LOW

// Variables forzadas de salida manual
extern int g_Sal_DER_forzada;
extern int g_Sal_IZQ_forzada;

// Variables de calibración especial
extern float g_calib_x; // Offset temporal si se aplica desde la app
extern float g_calib_y; // Offset temporal si se aplica desde la app
extern bool g_calibrar_flag; // Flag para indicar que se debe calibrar

// Variables para el nombre Bluetooth y el tipo de filtro
extern String g_NombreBT;
extern int g_TipoFiltro; // 1-Kalman (no usado), 2-Complementario (usado), 3-Ambos (no usado)

// Variables de la app
extern int g_perfil; // Perfil de usuario

// Objeto BluetoothSerial (global para ambas tareas)
extern BluetoothSerial SerialBT;

// Objeto Separador (global, usado en bluetooth_handler)
extern Separador s;

// Configuración para filtro de Madgwick
#define USAR_MADGWICK  // Comentar esta línea para usar el filtro complementario
extern float g_MADGWICK_BETA; // Factor beta para el filtro de Madgwick
extern float g_MADGWICK_BETA_NORMAL;

#endif // SHARED_DATA_H