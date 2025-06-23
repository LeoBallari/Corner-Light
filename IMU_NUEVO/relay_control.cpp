// relay_control.cpp

#include "relay_control.h"

// Variables internas de control de histeresis (se mantienen aquí ya que son específicas del relé)
static int cuenta_onD = 0, cuenta_offD = 0;
static int cuenta_onI = 0, cuenta_offI = 0;
static bool AlarmaDer = false;
static bool AlarmaIzq = false;


void relays_init() {
  pinMode(PIN_SALIDA_IZQ, OUTPUT);
  pinMode(PIN_SALIDA_DER, OUTPUT);
  // Inicializar relés en estado desactivado (dependiendo de tu módulo, !SAL_ACT o SAL_DESACT)
  digitalWrite(PIN_SALIDA_IZQ, !SAL_ACT);
  digitalWrite(PIN_SALIDA_DER, !SAL_ACT);
  Serial.println("Relés inicializados.");
}

// Funciones de control de salida (Histeresis para luces) - Originalmente SalidaDerecha/Izquierda
// Se renombran a SalidaDerecha/Izquierda2 para evitar conflicto y por la lógica original que usa ang_limite y ang_limite2
bool SalidaDerecha2(float angulo, float LIMITE_ACT, float LIMITE_DES) {
  if ((angulo >= LIMITE_ACT) && (AlarmaDer == false)) {
    if (cuenta_onD < g_CICLOS_ACT) cuenta_onD++;
  } else if ((angulo <= LIMITE_DES) && (AlarmaDer == true)) {
    if (cuenta_offD < g_CICLOS_DES) cuenta_offD++;
    cuenta_onD = 0; // Resetear contador de activación al empezar a desactivar
  } else if ((angulo <= LIMITE_ACT) && (AlarmaDer == false)) {
    cuenta_onD = 0; // Resetear contador de activación si el ángulo baja antes de activar
  }

  if (cuenta_onD >= g_CICLOS_ACT) {
    AlarmaDer = true;
    cuenta_onD = g_CICLOS_ACT - 1; // Mantener en el límite para evitar desbordamiento
    cuenta_offD = 0; // Resetear contador de desactivación
  }
  if (cuenta_offD >= g_CICLOS_DES) {
    AlarmaDer = false;
    cuenta_offD = g_CICLOS_DES - 1; // Mantener en el límite
    cuenta_onD = 0; // Resetear contador de activación
  }

  return AlarmaDer;
}

bool SalidaIzquierda2(float angulo, float LIMITE_ACT, float LIMITE_DES) {
  if ((angulo <= LIMITE_ACT) && (AlarmaIzq == false)) {
    if (cuenta_onI < g_CICLOS_ACT) cuenta_onI++;
  } else if ((angulo >= LIMITE_DES) && (AlarmaIzq == true)) {
    if (cuenta_offI < g_CICLOS_DES) cuenta_offI++;
    cuenta_onI = 0; // Resetear contador de activación
  } else if ((angulo >= LIMITE_ACT) && (AlarmaIzq == false)) {
    cuenta_onI = 0; // Resetear contador de activación
  }

  if (cuenta_onI >= g_CICLOS_ACT) {
    AlarmaIzq = true;
    cuenta_onI = g_CICLOS_ACT - 1; // Mantener en el límite
    cuenta_offI = 0; // Resetear contador de desactivación
  }
  if (cuenta_offI >= g_CICLOS_DES) {
    AlarmaIzq = false;
    cuenta_offI = g_CICLOS_DES - 1; // Mantener en el límite
    cuenta_onI = 0; // Resetear contador de activación
  }

  return AlarmaIzq;
}

void activarAmbasSalidas() {
  // Aseguramos acceso exclusivo al mutex antes de modificar g_salidaLuzAuxIzq/Der
  if (xSemaphoreTake(xMutex_MPU_Data, portMAX_DELAY) == pdTRUE) {
    g_salidaLuzAuxIzq = SAL_ACT;
    g_salidaLuzAuxDer = SAL_ACT;
    xSemaphoreGive(xMutex_MPU_Data);
  }
}

void desactivarAmbasSalidas() {
  if (xSemaphoreTake(xMutex_MPU_Data, portMAX_DELAY) == pdTRUE) {
    g_salidaLuzAuxIzq = SAL_DESACT;
    g_salidaLuzAuxDer = SAL_DESACT;
    xSemaphoreGive(xMutex_MPU_Data);
  }
}

void initCLight() {
  // Esta función no necesita mutex ya que solo controla los pines directamente
  // y se llama una vez en setup().
  digitalWrite(PIN_SALIDA_IZQ, !SAL_ACT);
  digitalWrite(PIN_SALIDA_DER, !SAL_ACT);
  delay(300);
  digitalWrite(PIN_SALIDA_IZQ, SAL_ACT);
  digitalWrite(PIN_SALIDA_DER, SAL_ACT);
  delay(300);

  for (int i = 0; i < 3; i++) {
    digitalWrite(PIN_SALIDA_IZQ, !SAL_ACT);
    digitalWrite(PIN_SALIDA_DER, !SAL_ACT);
    delay(100);
    digitalWrite(PIN_SALIDA_IZQ, SAL_ACT);
    digitalWrite(PIN_SALIDA_DER, SAL_ACT);
    delay(100);
  }
}

void calCLight() {
  // Esta función no necesita mutex, solo parpadea los relés para indicar calibración
  digitalWrite(PIN_SALIDA_IZQ, !SAL_ACT);
  digitalWrite(PIN_SALIDA_DER, !SAL_ACT);
  delay(100);
  digitalWrite(PIN_SALIDA_IZQ, SAL_ACT);
  digitalWrite(PIN_SALIDA_DER, SAL_ACT);
  delay(100);
}