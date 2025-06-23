// bluetooth_handler.cpp

#include "bluetooth_handler.h"

String MensajeCompleto = ""; // Buffer para el mensaje completo recibido por BT
String cadena_bt_salida = ""; // Cadena para el mensaje a enviar por BT


void bluetooth_task_core1(void *pvParameters) {
  Serial.printf("[Core 1] Bluetooth_Task: Iniciada en el Nucleo %d\n", xPortGetCoreID());

  for (;;) {
    process_bluetooth_data(); // Llamar a la función que maneja RX/TX
    vTaskDelay(g_ImuDelay / portTICK_PERIOD_MS); // Usar el mismo delay que el MPU para sincronizar la lectura
  }
}

void process_bluetooth_data() {
  // Tomar el mutex para leer datos compartidos y luego generar la cadena de salida
  if (xSemaphoreTake(xMutex_MPU_Data, portMAX_DELAY) == pdTRUE) {
    // Generación de la cadena de salida (TU FORMATO ORIGINAL)
    cadena_bt_salida = "CL,";                                     // 00 - CARACTERES PARA DETERMINAR SI LA CADENA ES VALIDA
    cadena_bt_salida += String(g_Roll) + ",";                     // 01 - ANGULO DE INCLINACION FINAL
    cadena_bt_salida += String(g_FILTRO * 10000) + ",";           // 02 - FILTRO COMPLEMENTARIO
    cadena_bt_salida += String(g_ang_limite) + ",";               // 03 - ANGULO LIMITE PARA EL DISPARO DE SALIDAS
    cadena_bt_salida += String(g_CICLOS_ACT) + ",";               // 04 - HISTERESIS PARA LA ACTIVACION (CICLOS * DELAY)
    cadena_bt_salida += String(g_CICLOS_DES) + ",";               // 05 - HISTERESIS PARA LA DESACTIVACION (CICLOS * DELAY)
    cadena_bt_salida += String(g_eje) + ",";                      // 06 - EJE DE ACTUACION (Y O X)
    cadena_bt_salida += String(g_invertir) + ",";                 // 07 - INVERTIR 180 DE LA ECU SOBRE EL EJE Z
    cadena_bt_salida += String(g_modo) + ",";                     // 08 - MODO AUTOMATICO O MANUAL
    cadena_bt_salida += String(!g_salidaLuzAuxIzq) + ",";         // 09 - SALIDA IZQUIERDA (estado final, invertido)
    cadena_bt_salida += String(!g_salidaLuzAuxDer) + ",";         // 10 - SALIDA DERECHA (estado final, invertido)
    cadena_bt_salida += String(g_ImuDelay) + ",";                 // 11 - DELAY EN LAS LECTURAS Y TRANSMISION
    cadena_bt_salida += String(g_EMA_ALPHA) + ",";                // 12 - FACTOR FILTRO PASA BAJOS PARA EL ACELEROMETRO
    cadena_bt_salida += String(g_accelX) + ",";                   // 13 - ACELERACION EN X
    cadena_bt_salida += String(g_accelY) + ",";                   // 14 - ACELERACION EN Y
    cadena_bt_salida += String(g_YAW_ALPHA) + ",";                // 15 - FACTOR PARA COMPENZAR INCLINACION CON EL YAW (VELOCIDAD ANGULAR)
    cadena_bt_salida += String(g_gyroZ * g_YAW_ALPHA) + ",";      // 16 - VELOCIDAD ANGULAR (YA aplicada a YAW_ALPHA)
    cadena_bt_salida += String(g_GYRO_ESC) + ",";                 // 17 - FACTOR EN EL QUE DIVIDO EL DATO CRUDO DEL GIROSCOPIO
    cadena_bt_salida += String(g_ang_limite2) + ",";              // 18 - ANGULO LIMITE PARA EL APAGADO DE LAS SALIDAS
    cadena_bt_salida += String(g_perfil) + ",";                   // 19 - PERFIL DE USUARIO
    cadena_bt_salida += g_NombreBT;                               // 20 - NOMBRE DEL DISPOSITIVO

    SerialBT.print(cadena_bt_salida);
    SerialBT.flush(); // Limpia el buffer de comunicación. Podría causar un poco de lentitud si el ImuDelay es muy bajo.

    xSemaphoreGive(xMutex_MPU_Data); // Liberar el mutex después de usar los datos
  }

  // Recepción y decodificación de comandos Bluetooth
  if (SerialBT.available()) {
    MensajeCompleto = SerialBT.readStringUntil('\n');
    Serial.println("BT Rx: " + MensajeCompleto); // Imprimir en Serial para depuración
    Decodifico(MensajeCompleto); // Decodificar el mensaje
  }
}

int Decodifico(String msg) {
  // No necesitamos mutex aquí porque solo leemos la cadena de entrada
  // y luego modificamos variables globales protegidas por mutex en el bloque if

  // --- DEBUG: Imprime el mensaje completo que recibe Decodifico ---
  Serial.print("Decodifico recibe msg (con comillas): '");
  Serial.print(msg);
  Serial.println("'");
  Serial.printf("Decodifico recibe msg Longitud: %d\n", msg.length());
  // --- FIN DEBUG ---

  String elemento0; // Declaramos elemento0 aquí

  // --- Extracción manual del primer elemento ---
  int firstCommaIndex = msg.indexOf(',');
  if (firstCommaIndex != -1) {
    elemento0 = msg.substring(0, firstCommaIndex);
  } else {
    // Si no hay coma, significa que el mensaje completo es el primer elemento (ej. "CALIB")
    elemento0 = msg;
  }
  // --- Fin de la extracción manual ---

  // --- ¡CRUCIAL! Limpia y normaliza el primer elemento ---
  elemento0.trim();       // Elimina espacios en blanco, \r, \n al principio y al final
  elemento0.toUpperCase(); // Convierte todo a mayúsculas para una comparación robusta

  // CAMBIO LOS PARAMETROS
  if (elemento0.equals("CL")) {
    Serial.println("¡ENTRANDO EN IF 'CL'! Procesando parámetros..."); // Mensaje de confirmación

    if (xSemaphoreTake(xMutex_MPU_Data, portMAX_DELAY) == pdTRUE) { // Tomar mutex para modificar globales
    // --- INICIO: NUEVA LÓGICA DE EXTRACCIÓN DE PARÁMETROS ROBUSTA (Ajustada al orden de la APP) ---
      String currentParam;
      int currentPos = firstCommaIndex; // Empezamos después del "CL,"

      // Helper lambda para extraer el siguiente parámetro
      auto getNextParam = [&](int& pos) {
        int nextComma = msg.indexOf(',', pos + 1);
        String param;
        if (nextComma == -1) { // Si es el último parámetro
            param = msg.substring(pos + 1);
        } else {
            param = msg.substring(pos + 1, nextComma);
        }
        pos = nextComma; // Actualiza la posición a la siguiente coma
        return param;
      };

      // 01: filtro_compl (que se mapea a g_FILTRO)
      currentParam = getNextParam(currentPos);
      g_FILTRO = map(currentParam.toFloat(), 1, 100, 9500, 10000);
      g_MADGWICK_BETA = map(currentParam.toFloat(), 1, 100, 10, 100) / 1000.0f;  // 0.01 a 0.1
      g_MADGWICK_BETA_NORMAL = g_MADGWICK_BETA / 2;
      g_FILTRO = (g_FILTRO - 1) / 10000.0; // Ajuste de rango

      // 02: lb_ang_disparo.Text (que se mapea a g_ang_limite)
      currentParam = getNextParam(currentPos);
      g_ang_limite = currentParam.toFloat();

      // 03: "20" (g_CICLOS_ACT)
      currentParam = getNextParam(currentPos);
      g_CICLOS_ACT = currentParam.toInt();

      // 04: "20" (g_CICLOS_DES)
      currentParam = getNextParam(currentPos);
      g_CICLOS_DES = currentParam.toInt();

      // 05: tb_eje.Checked (g_eje) <-- ¡Este es el que buscamos!
      currentParam = getNextParam(currentPos);
      g_eje = currentParam.toInt();

      // 06: tb_invertir.Checked (g_invertir)
      currentParam = getNextParam(currentPos);
      g_invertir = currentParam.toInt();

      // 07: tb_modo.Checked (g_modo)
      currentParam = getNextParam(currentPos);
      g_modo = currentParam.toInt();

      // 08: tb_invertir_yaw.Checked (flag para invertir g_YAW_ALPHA)
      int yaw_invert_flag;
      currentParam = getNextParam(currentPos);
      yaw_invert_flag = currentParam.toInt();

      // 09: ImuDelay (g_ImuDelay)
      currentParam = getNextParam(currentPos);
      g_ImuDelay = currentParam.toInt();

      // 10: (pasa_bajos/10) (g_EMA_ALPHA)
      currentParam = getNextParam(currentPos);
      g_EMA_ALPHA = currentParam.toFloat();

      // 11: (yaw) (g_YAW_ALPHA)
      currentParam = getNextParam(currentPos);
      g_YAW_ALPHA = currentParam.toInt();

      // 12: (gyro_esc) (g_GYRO_ESC)
      currentParam = getNextParam(currentPos);
      g_GYRO_ESC = currentParam.toInt();
      g_GYRO_LB = 131.0 / g_GYRO_ESC; // Recalcular

      // 13: (lb_ang_apagado.Text) (g_ang_limite2)
      currentParam = getNextParam(currentPos);
      g_ang_limite2 = currentParam.toFloat();

      // 14: Perfil (g_perfil)
      currentParam = getNextParam(currentPos);
      g_perfil = currentParam.toInt();

      // 15: txt_nombreBT.Text (g_NombreBT)
      currentParam = getNextParam(currentPos);
      g_NombreBT = currentParam;

      // Ajuste de YAW_ALPHA si el flag (índice 8) es 1
      if (yaw_invert_flag == 1) {
        g_YAW_ALPHA = g_YAW_ALPHA * -1;
      }

      // Actualizar aLim y aLim2 inmediatamente
      g_aLim = g_ang_limite;
      g_aLim2 = g_ang_limite2;

      // --- FIN: NUEVA LÓGICA DE EXTRACCIÓN DE PARÁMETROS ROBUSTA ---


      config_save_profile(); // Guardar los nuevos parámetros en flash
      xSemaphoreGive(xMutex_MPU_Data); // Liberar mutex
    }
  }

  // CALIBRAR LA IMU
  else if (elemento0.equals("CALIB")) { // Usar else if para optimizar si no es "CL"
    // La solicitud de calibración se pasa a la tarea MPU a través de un flag
    mpu_calibrate_sensor_wrapper(); // Esta función ya maneja el mutex
  }

  // SUPERPONER UN OFFSET TEMPORAL
  else if (elemento0.equals("OFF_SET")) { // Usar else if
    if (xSemaphoreTake(xMutex_MPU_Data, portMAX_DELAY) == pdTRUE) {
      // Acceder a g_Roll (el ángulo procesado)
      if (g_invertir == false) {
        g_calib_y += g_Roll * -1; // Esto aplicaría el offset al eje Y
        g_calib_x += g_Roll * -1; // Esto aplicaría el offset al eje X (si lo usas para otro Roll)
      } else {
        g_calib_y -= g_Roll;
        g_calib_x -= g_Roll;
      }
      Serial.printf("Offset temporal aplicado: calib_x: %.2f, calib_y: %.2f\n", g_calib_x, g_calib_y);
      xSemaphoreGive(xMutex_MPU_Data);
    }
  }

  // FORZAR LA SEÑAL DE SALIDA MANUALMENTE
  else if (elemento0.equals("SAL")) { // Usar else if
    if (xSemaphoreTake(xMutex_MPU_Data, portMAX_DELAY) == pdTRUE) {
      // Usar la helper lambda para estos también
      String message = msg;
      int currentPos = message.indexOf(','); // Después de "SAL,"
      
      auto getNextParam_local = [&](int& pos_local) { // Nueva lambda para evitar conflicto con la anterior
        int nextComma = message.indexOf(',', pos_local + 1);
        String param;
        if (nextComma == -1) {
            param = message.substring(pos_local + 1);
        } else {
            param = message.substring(pos_local + 1, nextComma);
        }
        pos_local = nextComma;
        return param;
      };

      g_modo = getNextParam_local(currentPos).toInt();

      int sal_izq_val = getNextParam_local(currentPos).toInt();
      if (sal_izq_val == 0) {
        g_Sal_IZQ_forzada = 0;
      } else {
        g_Sal_IZQ_forzada = 1;
      }

      int sal_der_val = getNextParam_local(currentPos).toInt();
      if (sal_der_val == 0) {
        g_Sal_DER_forzada = 0;
      } else {
        g_Sal_DER_forzada = 1;
      }
      xSemaphoreGive(xMutex_MPU_Data);
    }
  }

  else if (elemento0.equals("AUTO")) { // Usar else if
    if (xSemaphoreTake(xMutex_MPU_Data, portMAX_DELAY) == pdTRUE) {
      g_modo = MODO_AUTOMATICO;
      
      xSemaphoreGive(xMutex_MPU_Data);
    }
  }
  else if (elemento0.equals("MANUAL")) { // Usar else if
    if (xSemaphoreTake(xMutex_MPU_Data, portMAX_DELAY) == pdTRUE) {
      g_modo = MODO_MANUAL;

      xSemaphoreGive(xMutex_MPU_Data);
    }
  }

  else if (elemento0.equals("ANGULOS")) { // Usar else if
    if (xSemaphoreTake(xMutex_MPU_Data, portMAX_DELAY) == pdTRUE) {
      // Usar la helper lambda para estos también
      String message = msg;
      int currentPos = message.indexOf(','); // Después de "ANGULOS,"

      auto getNextParam_local = [&](int& pos_local) { // Nueva lambda para evitar conflicto con la anterior
        int nextComma = message.indexOf(',', pos_local + 1);
        String param;
        if (nextComma == -1) {
            param = message.substring(pos_local + 1);
        } else {
            param = message.substring(pos_local + 1, nextComma);
        }
        pos_local = nextComma;
        return param;
      };

      g_ang_limite = getNextParam_local(currentPos).toFloat();
      g_ang_limite2 = getNextParam_local(currentPos).toFloat();

      g_perfil = 0; // Perfil de usuario se pone a 0

      // Ajustar aLim y aLim2 inmediatamente
      g_aLim = g_ang_limite;
      g_aLim2 = g_ang_limite2;

      config_save_profile(); // Guardar los nuevos ángulos
      xSemaphoreGive(xMutex_MPU_Data);
    }
  }
  return 0; // Opcional, según la lógica de tu Decodifico
}