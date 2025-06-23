// IMU_proyecto.ino

#include <Wire.h>
#include "BluetoothSerial.h"
#include <Adafruit_MPU6050.h>
#include <Adafruit_Sensor.h>
#include <Preferences.h> // Ya incluido en config_manager, pero lo dejamos por si acaso.

// Incluye nuestros módulos
#include "shared_data.h"
#include "config_manager.h"
#include "relay_control.h"
#include "mpu_handler.h"
#include "bluetooth_handler.h"

// Handles de las tareas de FreeRTOS
TaskHandle_t mpuTaskHandle;
TaskHandle_t bluetoothTaskHandle;

void setup() {
  Serial.begin(115200);
  while (!Serial) {
    delay(10);
  }
  Serial.println("Iniciando configuracion del ESP32...");

  // 1. Inicializar el Mutex para proteger datos compartidos
  xMutex_MPU_Data = xSemaphoreCreateMutex();
  if (xMutex_MPU_Data == NULL) {
    Serial.println("ERROR: No se pudo crear el Mutex de datos del MPU. Deteniendo...");
    while(1);
  } else {
    Serial.println("Mutex para datos del MPU creado correctamente.");
  }

  // 2. Inicializar la gestión de preferencias (para guardar/cargar configuración)
  if (!config_init()) {
    Serial.println("ERROR: No se pudo inicializar Preferences. Deteniendo...");
    while(1);
  }
  
  // g_modo = MODO_AUTOMATICO;
  // config_save_profile(); // Guardar los nuevos parámetros en flash


  config_load_profile(); // Cargar el perfil de configuración inicial
  config_print_all(); // Imprimir configuración cargada (para depuración)

  // 3. Inicializar los pines de los relés
  relays_init();

  // 4. Inicializar Bluetooth Serial
  // NombreBT se carga desde config_load_profile()
  SerialBT.begin(g_NombreBT); 
  Serial.printf("%s Listo en el Nucleo...: %d\n", g_NombreBT.c_str(), xPortGetCoreID());


  // 5. Crear Tarea para MPU6050 en Core 0
  xTaskCreatePinnedToCore(
    mpu_task_core0,
    "MPU_Task",
    // Aumentamos el stack size como precaución
    8192, // Stack size (en bytes). Aumentado de 4096 para mayor seguridad.
    NULL,
    5, // Prioridad de la tarea
    &mpuTaskHandle, // Handle de la tarea
    0  // Núcleo 0
  );
  Serial.println("Tarea 'MPU_Task' creada en Core 0.");

  // 6. Crear Tarea para Bluetooth en Core 1
  xTaskCreatePinnedToCore(
    bluetooth_task_core1,
    "Bluetooth_Task",
    // Aumentamos el stack size para Bluetooth que consume bastante.
    12288, // Stack size (en bytes). Aumentado de 8192 para mayor seguridad.
    NULL,
    5, // Prioridad de la tarea
    &bluetoothTaskHandle, // Handle de la tarea
    1  // Núcleo 1
  );
  Serial.println("Tarea 'Bluetooth_Task' creada en Core 1.");

  Serial.println("Configuracion inicial del ESP32 completada. Las tareas estan ejecutandose.");
}


// loop() se mantiene vacío ya que FreeRTOS gestiona las tareas.
void loop() {
  // Las tareas de FreeRTOS se ejecutan de forma independiente.
  // Puedes usar este loop si necesitas algo que no sea una tarea FreeRTOS,
  // pero generalmente no es necesario en proyectos con FreeRTOS.
  delay(10); // Pequeño delay para evitar un loop vacío y hambriento de CPU.
}