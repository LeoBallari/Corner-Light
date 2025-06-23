// config_manager.cpp

#include "config_manager.h"

Preferences configuracion;

bool config_init() {
  // No hay nada específico que inicializar aquí, ya que preferences.begin() se hace en load/save
  return true;
}

void config_load_profile() {
  Serial.println("Cargando perfil de configuracion...");
  // Asegurarse de que begin() se abre en modo lectura (true) y verificar si fue exitoso
  if (!configuracion.begin("my-app", true)) { // 'my-app' es el namespace, true para R/O
    Serial.println("ERROR: No se pudo iniciar Preferences para cargar. Asegurarse de que el namespace sea correcto.");
    // En caso de fallo, los valores por defecto que se pasen a getFloat/getInt se usarán.
  }

  // Offsets de calibración del MPU
  g_ax_offset = configuracion.getFloat("ax_offset", 0.0);
  g_ay_offset = configuracion.getFloat("ay_offset", 0.0);
  g_az_offset = configuracion.getFloat("az_offset", 0.0);
  g_gx_offset = configuracion.getFloat("gx_offset", 0.0);
  g_gy_offset = configuracion.getFloat("gy_offset", 0.0);
  g_gz_offset = configuracion.getFloat("gz_offset", 0.0);

  // Parámetros de control y filtro
  g_FILTRO = configuracion.getFloat("filtro", 0.9850);
  g_ang_limite = configuracion.getFloat("angulo", 10.0);
  g_ang_limite2 = configuracion.getFloat("angulo2", 5.0);
  g_CICLOS_ACT = configuracion.getInt("ciclos_act", 20);
  g_CICLOS_DES = configuracion.getInt("ciclos_des", 20);
  g_ImuDelay = configuracion.getInt("delay", 20);
  g_EMA_ALPHA = configuracion.getFloat("ema_alpha", 0.6);
  g_YAW_ALPHA = configuracion.getInt("YAW_ALPHA", 0); // Asumiendo que YAW_ALPHA es int
  g_GYRO_ESC = configuracion.getInt("gyro_esc", 131);
  g_GYRO_LB = 131.0 / g_GYRO_ESC; // Recalcular GYRO_LB
  g_NombreBT = configuracion.getString("NombreBT", "Corner Light");

  // Parámetros de modo y eje
  g_eje = configuracion.getInt("eje", 1); // 1 o 2 (Y o X)
  Serial.printf("DEBUG (Load): g_eje cargado desde flash = %d\n", g_eje); // <-- DEBUG para g_eje

  g_modo = configuracion.getInt("modo", MODO_AUTOMATICO);
  g_invertir = configuracion.getBool("invertir", false); // Usar getBool si es booleano

  g_perfil = configuracion.getInt("perfil", 0); // Perfil de usuario
  Serial.printf("DEBUG (Load): g_perfil cargado desde flash = %d\n", g_perfil); // <-- DEBUG para g_perfil

  // Asegurarse de que aLim y aLim2 estén actualizados después de cargar ang_limite y ang_limite2
  g_aLim = g_ang_limite;
  g_aLim2 = g_ang_limite2;

  configuracion.end();
  Serial.println("Perfil de configuracion cargado.");
}

void config_save_profile() {
  Serial.println("Guardando perfil de configuracion...");
  // Asegurarse de que begin() se abre en modo lectura/escritura (false) y verificar si fue exitoso
  if (!configuracion.begin("my-app", false)) { // 'my-app' es el namespace, false para R/W
    Serial.println("ERROR: No se pudo iniciar Preferences para guardar. Asegurarse de que el namespace sea correcto.");
    return; // Si no se puede iniciar, no intentes guardar.
  }

  // Offsets de calibración del MPU
  configuracion.putFloat("ax_offset", g_ax_offset);
  configuracion.putFloat("ay_offset", g_ay_offset);
  configuracion.putFloat("az_offset", g_az_offset);
  configuracion.putFloat("gx_offset", g_gx_offset);
  configuracion.putFloat("gy_offset", g_gy_offset);
  configuracion.putFloat("gz_offset", g_gz_offset);

  // Parámetros de control y filtro
  configuracion.putFloat("filtro", g_FILTRO);
  configuracion.putFloat("angulo", g_ang_limite);
  configuracion.putFloat("angulo2", g_ang_limite2);
  configuracion.putInt("ciclos_act", g_CICLOS_ACT);
  configuracion.putInt("ciclos_des", g_CICLOS_DES);
  configuracion.putInt("delay", g_ImuDelay);
  configuracion.putFloat("ema_alpha", g_EMA_ALPHA);
  configuracion.putInt("YAW_ALPHA", g_YAW_ALPHA);
  configuracion.putInt("gyro_esc", g_GYRO_ESC);
  configuracion.putString("NombreBT", g_NombreBT);

  // Parámetros de modo y eje
  configuracion.putInt("eje", g_eje);
  Serial.printf("DEBUG (Save): g_eje a guardar en flash = %d\n", g_eje); // <-- DEBUG para g_eje

  configuracion.putInt("modo", g_modo);
  configuracion.putBool("invertir", g_invertir);

  configuracion.putInt("perfil", g_perfil);
  Serial.printf("DEBUG (Save): g_perfil a guardar en flash = %d\n", g_perfil); // <-- DEBUG para g_perfil

  configuracion.end();
  Serial.println("Perfil de configuracion guardado.");

  config_print_all(); // Llama a la función para imprimir todos los parámetros guardados
}

void config_print_all() {
  Serial.println("\n--- Configuracion Actual ---");
  Serial.printf("Offsets Acel: X:%.3f, Y:%.3f, Z:%.3f\n", g_ax_offset, g_ay_offset, g_az_offset);
  Serial.printf("Offsets Giro: X:%.3f, Y:%.3f, Z:%.3f\n", g_gx_offset, g_gy_offset, g_gz_offset);
  Serial.printf("FILTRO: %.4f, Angulo Limite: %.1f, Angulo Limite 2: %.1f\n", g_FILTRO, g_ang_limite, g_ang_limite2);
  Serial.printf("Ciclos ACT: %d, Ciclos DES: %d, Imu Delay: %d\n", g_CICLOS_ACT, g_CICLOS_DES, g_ImuDelay);
  Serial.printf("EMA Alpha: %.2f, YAW Alpha: %d, Gyro ESC: %d, Gyro LB: %.3f\n", g_EMA_ALPHA, g_YAW_ALPHA, g_GYRO_ESC, g_GYRO_LB);
  Serial.printf("Eje: %d, Invertir: %d, Modo: %d\n", g_eje, g_invertir, g_modo);
  Serial.printf("Nombre BT: %s\n",g_NombreBT.c_str());
  Serial.printf("Perfil: %d\n", g_perfil);
  Serial.println("----------------------------\n");
}

// Funciones auxiliares para guardar/cargar tipos específicos
void config_set_float(const char* key, float value) {
  configuracion.putFloat(key, value);
}
float config_get_float(const char* key, float defaultValue) {
  return configuracion.getFloat(key, defaultValue);
}
void config_set_int(const char* key, int value) {
  configuracion.putInt(key, value);
}
int config_get_int(const char* key, int defaultValue) {
  return configuracion.getInt(key, defaultValue);
}
void config_set_string(const char* key, String value) {
  configuracion.putString(key, value);
}
String config_get_string(const char* key, String defaultValue) {
  return configuracion.getString(key, defaultValue);
}