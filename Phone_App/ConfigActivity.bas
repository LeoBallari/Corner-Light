B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=11
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	
	Type Perfil_Ruta(Valores(7) As Int)
	Type Perfil_Urbano(Valores(7) As Int)
	
	Dim yaw As Int
	Dim gyro_esc As Int
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Private see_sencibilidad As SeekBar
	Private see_angulo As SeekBar
	Private lb_sencibilidad As Label
	Private lb_ang_disparo As Label
	Private tb_modo As ToggleButton
	Private tb_eje As ToggleButton
	Private tb_invertir As ToggleButton
	Private lb_delay As Label
	Private see_imu As SeekBar
	Private see_pasa_bajos As SeekBar
	Private lb_pasa_bajos As Label
	Private tb_invertir_yaw As ToggleButton
	Private ScrollView1 As ScrollView
	Private pn_contenedor As Panel
	Private see_yaw As SeekBar
	Private lb_yaw As Label
	Private btn_GuardarCambios As Button
	Private see_gyro_escala As SeekBar
	Private lb_Gyro_escala As Label
	Private see_angulo2 As SeekBar
	Private lb_ang_apagado As Label
	Private txt_nombreBT As EditText

End Sub

Sub ArduinoMap(x As Int, in_min As Int, in_max As Int, out_min As Long, out_max As Long) As Long
	Return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("Config")
	ScrollView1.Panel.LoadLayout("3")
	ScrollView1.Panel.Height = pn_contenedor.Height
	
	Activity.AddMenuItem( "Diagnostico del Sistema", "mnuDiagnostico")
	Activity.AddMenuItem( "Recupear Perfiles", "mnuRecuperarPerfil")
	Activity.AddMenuItem( "Guardar Perfil", "mnuGuardarPerfil")
	Activity.AddMenuItem( "Cancelar", "mnuVolver")
	Activity.Title="CONFIGURACION DEL SISTEMA"

	see_sencibilidad.Value = ArduinoMap((ChatActivity.Filtro),9500,9999,0,100)
	
	see_angulo.Value = ChatActivity.Ang_Limite*2
	see_angulo2.Value = ChatActivity.Ang_Limite2*2
	
	see_imu.Value= ChatActivity.Imu-2

	see_pasa_bajos.Value= 10-(ChatActivity.pasa_b *50)
	lb_pasa_bajos.Text = 10-(ChatActivity.pasa_b *50)


	If ChatActivity.modo = 1 Then
		tb_modo.Checked=True
	End If
	If ChatActivity.eje = 1 Then
		tb_eje.Checked=True
	End If
	If ChatActivity.invertir= 1 Then
		tb_invertir.Checked=True
	Else
		tb_invertir.Checked=False
	End If
		
	lb_sencibilidad.Text = see_sencibilidad.Value
	lb_ang_disparo.Text = (see_angulo.Value / 2)
	ChatActivity.lim = see_angulo.Value
	
	see_yaw.Value=Abs(ChatActivity.yaw)
	lb_yaw.Text = Abs(ChatActivity.yaw)
	
	see_gyro_escala.Value = ChatActivity.gyro_esc - 1
	lb_Gyro_escala.Text = ChatActivity.gyro_esc
	
	If ChatActivity.yaw <= 0 Then
		tb_invertir_yaw.Checked=True
	Else
		tb_invertir_yaw.Checked=False
	End If
	
	txt_nombreBT.Text 	 = ChatActivity.nombre_BT
End Sub

Sub Activity_Resume
	
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Private Sub mnuVolver_Click
	Activity.Finish
	StartActivity(ChatActivity)
End Sub

private Sub EnviarDatos (Calib As Boolean, Perfil As Int)
	'----------------------------------------------------------------------------------------------------
	'TRAMA-->CL,filtro,angulo_limite,eje,invertir,modo,calibracion,delay
	'----------------------------------------------------------------------------------------------------
	
	Dim cadena As String
	Dim ImuDelay As Int
	Dim pasa_bajos As Float
	Dim filtro_compl As Int
	
	filtro_compl = see_sencibilidad.Value+1
	ImuDelay = lb_delay.Text
	pasa_bajos = 11 - lb_pasa_bajos.Text
	
	'el ,20,20 son las histeresis que ya tendria que borrar y acomodar toda la trama
	cadena = "CL," & filtro_compl & "," & lb_ang_disparo.Text & ",20,20"
	
	If tb_eje.Checked=True Then
		cadena = cadena & ",1"
	Else
		cadena = cadena & ",2"
	End If
	
	If tb_invertir.Checked=True Then
		cadena = cadena & ",1"
	Else
		cadena = cadena & ",0"
	End If
	
	If tb_modo.Checked=True Then
		cadena = cadena & ",1"
	Else
		cadena = cadena & ",0"
	End If
	
	If tb_invertir_yaw.Checked=True Then
		cadena = cadena & ",1"
	Else
		cadena = cadena & ",0"	
	End If
	
	cadena = cadena & "," & ImuDelay
	cadena = cadena & "," & (pasa_bajos/50)
	cadena = cadena & "," & (yaw)
	cadena = cadena & "," & (gyro_esc)
	cadena = cadena & "," & (lb_ang_apagado.Text)
	cadena = cadena & "," & Perfil
	cadena = cadena & "," & txt_nombreBT.Text
	
	If Calib = True Then
		cadena = "CALIB"
	End If
	
	Starter.Manager.SendMessage(cadena)
	Log(cadena)
	ToastMessageShow("Enviando Datos y Guardandolos....", False)
End Sub

Private Sub mnuDiagnostico_Click()
	Activity.Finish
	StartActivity(DiagnosticoActivity)
End Sub

Private Sub mnuRecuperarPerfil_click()
	Msgbox2Async("Elija PERFIL....", "Corner Light", "URBANO", "CACENLAR", "RUTA", Null, False)
	Wait For Msgbox_Result (Result As Int)
	
	Dim rm1 As RandomAccessFile
	Dim rm2 As RandomAccessFile
	
	Dim perfil1 As Perfil_Ruta
	Dim perfil2 As Perfil_Urbano
	
	rm1.Initialize(File.DirInternal,"perfilRuta.conf",False)
	rm2.Initialize(File.DirInternal,"perfilUrbano.conf",False)
	
	perfil1 = rm1.ReadEncryptedObject("123",0)
	perfil2 = rm2.ReadEncryptedObject("123",0)
	
	If Result = DialogResponse.POSITIVE Then
		see_sencibilidad.Value	= perfil2.Valores(0)
		see_pasa_bajos.Value	= perfil2.Valores(1)
		see_angulo.Value 		= perfil2.Valores(2)
		see_angulo2.Value 		= perfil2.Valores(3)
		see_imu.Value 			= perfil2.Valores(4)
		see_yaw.Value 			= perfil2.Valores(5)
		see_gyro_escala.Value 	= perfil2.Valores(6)
		
		ChatActivity.perfil_usuario	= 1
		ChatActivity.Perfil 		= "URBANO"
		
		Log("URBANO activo / " & see_yaw.Value)
	else if Result = DialogResponse.NEGATIVE Then
		see_sencibilidad.Value 	= perfil1.Valores(0)
		see_pasa_bajos.Value	= perfil1.Valores(1)
		see_angulo.Value 		= perfil1.Valores(2)
		see_angulo2.Value		= perfil1.Valores(3)
		see_imu.Value 			= perfil1.Valores(4)
		see_yaw.Value 			= perfil1.Valores(5)
		see_gyro_escala.Value 	= perfil1.Valores(6)
		
		ChatActivity.perfil_usuario	= 2
		ChatActivity.Perfil 		= "RUTA"
		
		Log("RUTA activo / " & ChatActivity.perfil_usuario)
	Else
		Return
	End If

	rm1.Close
	rm2.Close
	
	Msgbox2Async("Enviar datos a la ECU?", "Corner Light", "SI","", "NO", Null, False)
	Wait For Msgbox_Result (Result As Int)
	
	If Result = DialogResponse.POSITIVE Then
		EnviarDatos(False,ChatActivity.perfil_usuario)
	End If
	
	Activity.Finish
	StartActivity(ChatActivity)
End Sub

Private Sub mnuGuardarPerfil_click()
	Dim rm1 As RandomAccessFile
	Dim rm2 As RandomAccessFile
	Dim perfil1 As Perfil_Ruta
	Dim perfil2 As Perfil_Urbano
		
	perfil1.Initialize
	perfil2.Initialize
	
	rm1.Initialize(File.DirInternal,"perfilRuta.conf",False)
	rm2.Initialize(File.DirInternal,"perfilUrbano.conf",False)
	
	Msgbox2Async("Elija PERFIL....", "Corner Light", "URBANO", "CACENLAR", "RUTA", Null, False)
	Wait For Msgbox_Result (Result As Int)
	If Result = DialogResponse.POSITIVE Then
		
		perfil2.Valores(0) 	= see_sencibilidad.Value
		perfil2.Valores(1) 	= see_pasa_bajos.Value
		perfil2.Valores(2) 	= see_angulo.Value
		perfil2.Valores(3) 	= see_angulo2.Value
		perfil2.Valores(4) 	= see_imu.Value
		perfil2.Valores(5) 	= see_yaw.Value
		perfil2.Valores(6) 	= see_gyro_escala.Value
		
		ChatActivity.perfil_usuario	= 1
		ChatActivity.Perfil 		= "URBANO"
		
		rm2.WriteEncryptedObject(perfil2,"123",0)
		
		ToastMessageShow("Perfil URBANO guardado correctamente....",True)
	else if Result = DialogResponse.CANCEL Then
		Return
	else if Result = DialogResponse.NEGATIVE Then
		
		perfil1.Valores(0)  = see_sencibilidad.Value
		perfil1.Valores(1)  = see_pasa_bajos.Value
		perfil1.Valores(2)  = see_angulo.Value
		perfil1.Valores(3)  = see_angulo2.Value
		perfil1.Valores(4)  = see_imu.Value
		perfil1.Valores(5)  = see_yaw.Value
		perfil1.Valores(6)  = see_gyro_escala.Value
		
		ChatActivity.perfil_usuario	= 2
		ChatActivity.Perfil 		= "RUTA"
		
		rm1.WriteEncryptedObject(perfil1,"123",0)
		
		ToastMessageShow("Perfil RUTA guardado correctamente....",True)
	End If
	rm1.Close
	rm2.Close
	
	EnviarDatos(False,ChatActivity.perfil_usuario)
	
	Activity.Finish
	StartActivity(ChatActivity)
End Sub

Private Sub see_sencibilidad_ValueChanged (Value As Int, UserChanged As Boolean)
	lb_sencibilidad.Text = Value +1
End Sub

Private Sub see_angulo_ValueChanged (Value As Int, UserChanged As Boolean)
	Private val As Float
	val = Value /2
		
	lb_ang_disparo.Text 	= val
	ChatActivity.lim 		= val
	ChatActivity.Ang_Limite = val
	
	If see_angulo2.Value > see_angulo.Value Then
		see_angulo2.Value = see_angulo.Value-1
	End If
End Sub

Private Sub see_angulo2_ValueChanged (Value As Int, UserChanged As Boolean)
	Private val As Float
	val = Value / 2
	
	If val < lb_ang_disparo.Text Then
		lb_ang_apagado.Text		 = val
		ChatActivity.lim2		 = val
		ChatActivity.Ang_Limite2 = val
	Else
		see_angulo2.Value= see_angulo.Value-1
	End If
End Sub

Private Sub btn_calibrar_LongClick
	Dim mens As String
	mens = "Esta por CALIBRAR el dispositivo. "
	
	Msgbox2Async(mens, "Corner Light", "Sí", "", "No", Null, False)
	
	Wait For Msgbox_Result (Result As Int)
	
	If Result = DialogResponse.POSITIVE Then
		ProgressDialogShow2("Calibrando....",False)
				
		EnviarDatos(True,ChatActivity.perfil_usuario)
		Sleep((1000 * see_imu.Value))
		ProgressDialogHide
		
		Activity.Finish
		StartActivity(ChatActivity)
	End If
End Sub

Private Sub see_imu_ValueChanged (Value As Int, UserChanged As Boolean)
	lb_delay.Text= Value+2
End Sub

Private Sub see_pasa_bajos_ValueChanged (Value As Int, UserChanged As Boolean)
	lb_pasa_bajos.Text = Value+1
End Sub

Private Sub see_yaw_ValueChanged (Value As Int, UserChanged As Boolean)
	yaw=Value
	lb_yaw.Text = Value
End Sub

Private Sub btn_GuardarCambios_LongClick
	'EnviarDatos(False,0)
	EnviarDatos(False,ChatActivity.perfil_usuario)
	Activity.Finish
	StartActivity(ChatActivity)
End Sub

Private Sub see_gyro_escala_ValueChanged (Value As Int, UserChanged As Boolean)
	gyro_esc=Value+1
	lb_Gyro_escala.Text = Value+1
End Sub