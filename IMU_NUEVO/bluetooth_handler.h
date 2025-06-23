// bluetooth_handler.h

#ifndef BLUETOOTH_HANDLER_H
#define BLUETOOTH_HANDLER_H

#include <Arduino.h>
#include "BluetoothSerial.h"
#include "shared_data.h" // Para acceder a variables globales y el mutex
#include "config_manager.h" // Para guardar la configuración modificada
#include "mpu_handler.h" // Para llamar a mpu_calibrate_sensor_wrapper

// Prototipos de funciones
void bluetooth_task_core1(void *pvParameters); // Tarea FreeRTOS para Bluetooth
void process_bluetooth_data(); // Maneja el envío y recepción
int Decodifico(String msg); // Función para decodificar comandos entrantes

#endif // BLUETOOTH_HANDLER_H