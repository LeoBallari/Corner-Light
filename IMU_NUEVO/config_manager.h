// config_manager.h

#ifndef CONFIG_MANAGER_H
#define CONFIG_MANAGER_H

#include <Preferences.h>
#include "shared_data.h" // Para acceder a las variables globales

extern Preferences configuracion; // Declarar la instancia de Preferences

bool config_init();
void config_load_profile();
void config_save_profile();
void config_print_all(); // Función para depuración

// Funciones auxiliares para guardar/cargar tipos específicos
void config_set_float(const char* key, float value);
float config_get_float(const char* key, float defaultValue = 0.0);
void config_set_int(const char* key, int value);
int config_get_int(const char* key, int defaultValue = 0);
void config_set_string(const char* key, String value);
String config_get_string(const char* key, String defaultValue = "");

#endif // CONFIG_MANAGER_H