// shared_data.cpp

#include "shared_data.h"
#include "Separador.h"

// Inicialización de las variables globales
SemaphoreHandle_t xMutex_MPU_Data = NULL;

float g_accelX, g_accelY, g_accelZ;
float g_gyroX, g_gyroY, g_gyroZ;
float g_Roll;

float g_ax_offset = 0;
float g_ay_offset = 0;
float g_az_offset = 0;
float g_gx_offset = 0;
float g_gy_offset = 0;
float g_gz_offset = 0;

float g_FILTRO = 0.9850;
float g_EMA_ALPHA = 0.6;
int g_YAW_ALPHA = 0;
int g_GYRO_ESC = 131;
float g_GYRO_LB = 131.0;

float g_ang_limite = 10.0;
float g_ang_limite2 = 5.0;
float g_aLim, g_aLim2;

int g_CICLOS_ACT = 20;
int g_CICLOS_DES = 20;
int g_ImuDelay = 20;

int g_eje = 1;
bool g_invertir = false;

int g_modo = MODO_AUTOMATICO;

byte g_salidaLuzAuxIzq = SAL_ACT;
byte g_salidaLuzAuxDer = SAL_ACT;

int g_Sal_DER_forzada = SAL_ACT;
int g_Sal_IZQ_forzada = SAL_ACT;

float g_calib_x = 0;
float g_calib_y = 0;
bool g_calibrar_flag = false;

String g_NombreBT = "Corner Light";
int g_TipoFiltro = 2;

int g_perfil = 0;

BluetoothSerial SerialBT;
Separador s;

// Inicialización de variables para Madgwick
float g_MADGWICK_BETA = 0.1f;        // Valor beta inicial, ajusta según sea necesario
float g_MADGWICK_BETA_NORMAL = 0.5f; // Valor beta normal, ajusta según sea necesario
