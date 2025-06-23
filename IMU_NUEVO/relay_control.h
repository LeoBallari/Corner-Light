// relay_control.h

#ifndef RELAY_CONTROL_H
#define RELAY_CONTROL_H

#include <Arduino.h>
#include "shared_data.h" // Para usar SAL_ACT, SAL_DESACT y las variables g_salidaLuzAux*

// Pines de los relés
#define PIN_SALIDA_IZQ 18
#define PIN_SALIDA_DER 19

// Declaraciones de funciones de control de relés
void relays_init();
bool SalidaDerecha(long angulo, float LIMITE_DER);
bool SalidaIzquierda(long angulo, float LIMITE_IZQ);
bool SalidaDerecha2(float angulo, float LIMITE_ACT, float LIMITE_DES);
bool SalidaIzquierda2(float angulo, float LIMITE_ACT, float LIMITE_DES);
int Val_Frenado(float acelY, float Limite);
void activarAmbasSalidas();
void desactivarAmbasSalidas();
void initCLight();
void calCLight();

#endif // RELAY_CONTROL_H