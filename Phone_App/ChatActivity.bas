Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=1.80
@EndOfDesignText@
#Region Module Attributes
	#FullScreen: False
	#IncludeTitle: false
#End Region

'Activity module
Sub Process_Globals
	Public color1,color2,color3 As Object
	Dim Son As Boolean
		
	Dim Valor As Float
	Dim Ang_Limite As Float
	Dim Ang_Limite2 As Float
	
	Dim Filtro As Float
	Dim eje As Int
	Dim Ciclos_Act As Int
	Dim Ciclos_Des As Int
	Dim invertir As Int
	Dim modo As Int
	Dim sal_izq As Int
	Dim sal_der As Int
	Dim Imu As Int
	Dim pasa_b As Float
	Dim yaw As Int
	Dim gyro_esc As Int
	Dim perfil_usuario As Int
	Dim acel_Limite As Float
	Dim nombre_BT As String
	Dim Tipo_Filtro As Int
		
	Dim lim As Float
	Dim lim2 As Float
	
	Dim ps As PhoneSensors
	'Dim ps1 As PhoneSensors
	
	Dim Timer1 As Timer
	Dim Timer2 As Timer
	
	Private xui As XUI
	
	Public perfil As String
	
	Dim EMA_LP As Float
	Dim EMA_ALPHA As Float
End Sub

Sub Globals
	Private angulo_Acce, angulo_gyro
	
	Private dialog As B4XDialog
	
	Private btn_izq_state As Boolean
	Private btn_der_state As Boolean
	
	Private primera_vez As Boolean

	Dim cadena() As String
	Private lb_sensor As Label
	Private Gauge1 As Gauge
	
	Public lb_angMAX_izq As Label
	Public lb_angMAX_der As Label
	
	Dim max_ang_der As Float
	Dim max_ang_izq As Float
	
	Private img_Led_izq_on As ImageView
	Private img_Led_der_on As ImageView

	Private btn_config As Button
	Private btn_reset As Button
	
	Private lb_Limite1 As Label
	Private btn_reset_inclina As Button	
	Private lb_hora As Label
	
	Dim M, H As String
	Dim dospuntos As Boolean
	Private btn_forzar_salida_izq As Button
	Private btn_forzar_salida_der As Button
	Private Panel1 As Panel
	Private lb_sensor2 As Label
	
	Private parte_entera As Int
	Private parte_decimal As Int
	Private lb_perfil As Label

	Private lb_Limite2 As Label
	Private lb_salida_forzada As Label

	Private btn_on_off As Button
	Private img_Auto_on As ImageView
	Private img_Auto_off As ImageView
	Private pn_cal_ang As Panel
	Private btn_angulos As Button
	Private img_fondo As ImageView
	Private see_angulo_P As SeekBar
	Private see_angulo2_P As SeekBar
	Private lb_ang1 As Label
	Private lb_ang2 As Label
	Private btn_aceptar As Button
	
	Private WebView1 As WebView
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("2")	
	
	If Starter.Manager.ConnectionState=True Then
		Log("Dejo de leer sensores del telefono")
		ps.StopListening
		'ps1.StopListening
	Else
		Starter.Manager.Disconnect
		If Main.Simulador=True Then
			Log("Simulador Activo")
			'ps.Initialize(ps.TYPE_ORIENTATION)
			ps.Initialize(ps.TYPE_ACCELEROMETER)
		End If
	End If
	
'	mp_c.Initialize
'	mp_d.Initialize
'	mp_c.Load(File.DirAssets,"Desconectado.mp3")
'	mp_d.Load(File.DirAssets,"Conectado.mp3")
	
'	img_sonido_on.Visible=False
	Son=False
	
	If FirstTime Then
		color1 = xui.Color_ARGB(255,29, 233, 182)
		color2 = xui.Color_ARGB(255,2, 136, 209)
		color3 = xui.Color_ARGB(255,6, 46, 66)
		
		EMA_ALPHA = 0.5
		sal_izq = 1
		sal_der = 1
		
		If Main.Simulador=True Then
			lim  = 8 'angulo limite en el simulador
			lim2 =  4 'angulo limite en el simulador
			lb_ang1.Text = lim
			lb_ang2.Text = lim2
			Tipo_Filtro  = 2
		End If
		
		Timer1.Initialize("timer1",500)
		Timer1.Enabled=True
		
		btn_izq_state=False
		btn_der_state=False
		lb_salida_forzada.Visible=False
		
'		img_Forza_off.Visible=True
		
		primera_vez=True		
		dospuntos=True
		perfil = "-"
	End If
End Sub

Sub Activity_Resume
	Try
		If Main.Simulador=False Then
			Timer2.Enabled=False
			'Log("Timer2 APAGADO")
			ps.StopListening
			'ps1.StopListening
		Else
			Timer2.Initialize("timer2",20)
			Timer2.Enabled=True
			''Log("Timer2 ENCENDIDO")
		
			ps.StartListening("Orientacion")
			'ps1.StartListening("Orientacion1")
		End If
		
		Gauge1.SetMinAndMax(Ang_Limite*-3,Ang_Limite*3)
		
		Gauge1.SetRanges(Array(Gauge1.CreateRange(Ang_Limite, 150, color3), _
					Gauge1.CreateRange(-150,Ang_Limite*-1, color3), _
					Gauge1.CreateRange(Ang_Limite,Ang_Limite2, color2), _
					Gauge1.CreateRange(Ang_Limite*-1,Ang_Limite2*-1, color2), _
					Gauge1.CreateRange(Ang_Limite2*-1,Ang_Limite2, color1)))	
					
		lb_perfil.Text = perfil
		lb_Limite1.Text= Round2(Ang_Limite,1)
		lb_Limite2.Text= Round2(Ang_Limite2,1)
	Catch
		Log(LastException)
	End Try	
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	If UserClosed Then
		Starter.Manager.Disconnect
	End If
End Sub

Sub timer1_tick
	H = NumberFormat(DateTime.GetHour(DateTime.Now),2,0)
	M = NumberFormat(DateTime.GetMinute(DateTime.Now),2,0)

	If dospuntos Then
		lb_hora.Text = H & " : " & M
		dospuntos=False
	Else
		lb_hora.Text = H & "   " & M
		dospuntos=True
	End If
	
'	If (btn_izq_state = True) Or (btn_der_state = True) Then
'		If img_Forza_on.Visible=True Then
'			'lb_salida_forzada.Visible=False
'			img_Forza_off.Visible=True
'			img_Forza_on.Visible=False
'		Else
'			'lb_salida_forzada.Visible=True
'			img_Forza_on.Visible=True
'			img_Forza_off.Visible=False
'		End If
'	Else
'		lb_salida_forzada.Visible=False
'		img_Forza_off.Visible=True
'		img_Forza_on.Visible=False
'	End If
End Sub

Sub timer2_tick
	Try
		'fusion_sensores(angulo_Acce, angulo_gyro)
		fusion_sensores(angulo_Acce, 0)
	Catch
		Log(LastException)
	End Try
End Sub

Private Sub LowPassFilter(value As Float) As Float
	EMA_LP = EMA_ALPHA * value + (1 - EMA_ALPHA) * EMA_LP
	Return EMA_LP
End Sub


Sub Orientacion_SensorChanged (Values() As Float)
	Dim ang As Float
	
	ang = Round2(Values(1),1)*6.5					'Si defino TYPE.ACELERATION
	ang = LowPassFilter(Round2(Values(1),1)*6.5)	'Si defino TYPE.ACELERATION
	
	If (ang > 90) And (ang < 180) Then
		ang = 180 - ang
	else if (ang > -180) And (ang < -90) Then
		ang = (180 + ang) * -1
	End If
	
		If ang > lim Then
			sal_der=0
		End If
		If ang < lim2 Then
			sal_der=1
		End If
		If ang < lim *-1 Then
			sal_izq = 0
		End If
		If ang > lim2*-1 Then
			sal_izq=1
		End If
	angulo_Acce = ang
End Sub

'Sub Orientacion1_SensorChanged (Values() As Float)
'	Dim ang1 As Float
'	
'	ang1 = Round2(Values(1),1)*-1					'Si defino TYPE.ORIENTATION
'	
'	If (ang1 > 90) And (ang1 < 180) Then
'		ang1 = 180 - ang1
'	else if (ang1 > -180) And (ang1 < -90) Then
'		ang1 = (180 + ang1) * -1
'	End If
'	
'	If ang1 > lim Then
'		sal_der=0
'	End If
'	If ang1 < lim2 Then
'		sal_der=1
'	End If
'	If ang1 < lim *-1 Then
'		sal_izq = 0
'	End If
'	If ang1 > lim2*-1 Then
'		sal_izq=1
'	End If
'	
'	angulo_gyro=ang1
'End Sub

Private Sub fusion_sensores(val_acc As Float, val_gyro As Float)
	Dim mens As String
	Dim ang As Float
	Dim Filtro As Float
	
	Filtro = 0.99
	
	'If val_gyro <> 0 Then
		'ang = (val_gyro * Filtro) + (val_acc * (1-Filtro))
		'ang=val_gyro
	'Else
		ang = val_acc
	'End If
	
	mens = "CL," & ang & ",0.99,"
	mens = mens & lim & ",5,5,1,0,1,"
	mens = mens & sal_izq & "," & sal_der
	mens = mens & ",20,0.5,0,0,0,0,0," & lim2 & ",3,1,SIMULADOR,2 "
	
	NewMessage(mens)	
End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean
	If KeyCode = KeyCodes.KEYCODE_BACK Then
		Main.Simulador	= False
		Timer2.Enabled	= False
		
		ps.StopListening
		'ps1.StopListening
		
		Activity.Finish
		StartActivity(Main)
		Log("Simulador Desactivado")
	End If
End Sub

Public Sub NewMessage (msg As String)
	LogMessage(msg)
End Sub

Sub LogMessage(Msg As String)
	cadena = Regex.Split(",",Msg)	'Separo el mesaje
	
	If cadena(0) <> "CL" Or cadena(0).Length <> 2 Then
		Log("ERROR")
		Return
	End If

	Try
		If cadena(0) = "CL" Then
			Valor = cadena(1)
			Valor = Round2(Valor,1)
			
			If Valor > -0.5 And Valor < 0.5 Then
				Valor=0
			End If
			
			parte_entera = Abs(Valor.As(Int))
			lb_sensor.Text = parte_entera
			
			parte_decimal = (Abs(Valor) - parte_entera)*10
			lb_sensor2.Text = "." & parte_decimal
			
			'------------------------------
			
			Filtro 			= cadena(2)
			Ang_Limite		= cadena(3)
			Ciclos_Act		= cadena(4)
			Ciclos_Des		= cadena(5)
			eje				= cadena(6)
			invertir		= cadena(7)
			modo			= cadena(8)
			sal_izq			= cadena(9)
			sal_der			= cadena(10)
			Imu				= cadena(11)
			pasa_b 			= cadena(12)
			yaw 			= cadena(15)
			gyro_esc 		= cadena(17)
			Ang_Limite2		= cadena(18)
			perfil_usuario	= cadena(19)
			nombre_BT		= cadena(20)
						
			Leds(sal_izq,sal_der)
			
			'------------------------------
			If primera_vez Then
				Gauge1.SetMinAndMax(Ang_Limite*-3,Ang_Limite*3)
				
				Gauge1.SetRanges(Array(Gauge1.CreateRange(Ang_Limite, 150, color3), _
					Gauge1.CreateRange(-150,Ang_Limite*-1, color3), _
					Gauge1.CreateRange(Ang_Limite,Ang_Limite2, color2), _
					Gauge1.CreateRange(Ang_Limite*-1,Ang_Limite2*-1, color2), _
					Gauge1.CreateRange(Ang_Limite2*-1,Ang_Limite2, color1)))	
					
				lb_Limite1.Text= Ang_Limite
				lb_Limite2.Text= Ang_Limite2
								
				img_Led_der_on.Visible=True
				img_Led_izq_on.Visible=True
				
				primera_vez=False
			End If
			
			If perfil_usuario = 1 Then
				lb_perfil.Text = "URBANO"
			else if perfil_usuario = 2 Then
				lb_perfil.Text = "RUTA"
			Else if perfil_usuario = 3 Then
				lb_perfil.Text = "SIMULADOR"
			Else
				lb_perfil.Text = " - "
			End If
			
			Gauge1.CurrentValue = Valor
			
			Ang_Maximos(Valor)

			If modo = 1 Then
				img_Auto_on.Visible=True
				img_Auto_off.Visible=False
			Else
				img_Auto_on.Visible=False
				img_Auto_off.Visible=True
			End If
		Else
			Return
		End If
	Catch
		Log(LastException)
	End Try
End Sub

Private Sub Leds(led_izq As Int, led_der As Int)
	Dim aler As String
	Dim mens As String
	
	If led_izq=0 Then
		img_Led_izq_on.Visible=True
		aler = "LUZ IZQUIERDA"
	Else
		img_Led_izq_on.Visible=False
	End If
	
	If led_der = 0 Then
		img_Led_der_on.Visible=True
		aler = "LUZ DERECHA"
	Else
		img_Led_der_on.Visible=False
	End If
End Sub

Private Sub Ang_Maximos(angulo As Float)
	
	If (angulo < -0.5 And angulo*-1 > max_ang_izq) Then
		max_ang_izq = angulo*-1
	End If
	
	If (angulo > 0.5 And angulo > max_ang_der) Then
		max_ang_der = angulo
	End If
	
	lb_angMAX_izq.Text = Round2(max_ang_izq,1)
	lb_angMAX_der.Text = Round2(max_ang_der,1)
End Sub


Private Sub btn_Menu_Click
'	dialog.Initialize(Activity)
'	Dim num As Int
'	num = Rnd(0,9999)
'	
'	Dim input As B4XInputTemplate
'	input.Initialize
'	input.lblTitle.Text = "Contraseña para: " & num
'	input.ConfigureForNumbers(True, False)
'	Wait For (dialog.ShowTemplate(input, "OK", "", "CANCEL")) Complete (Result As Int)
'	
'	If Result = xui.DialogResponse_Positive Then
'		If (num + input.Text) = 9999 Then
'			
'			If panel_menu_state = False Then
'				pn_Menu.Visible=True
'				panel_menu_state=True
'			Else
'				pn_Menu.Visible=False
'				panel_menu_state=False
'			End If
'		Else
'			dialog.Show("Clave incorrecta, vuelva a intentarlo","OK","","")
'			Return
'		End If
'	Else
'		Return
'	End If
	
'	If panel_menu_state = False Then
'		dialog.Initialize(Activity)
'		Dim num As Int
'		num = Rnd(0,9999)
'	
'		Dim input As B4XInputTemplate
'		input.Initialize
'		input.lblTitle.Text = "Contraseña para: " & num
'		input.ConfigureForNumbers(True, False)
'		Wait For (dialog.ShowTemplate(input, "OK", "", "CANCEL")) Complete (Result As Int)
'	
'		If Result = xui.DialogResponse_Positive Then
'			If (num + input.Text) = 9999  Or input.Text = 0 Then
'				'pn_Menu.Visible=True
'				panel_menu_state=True
'			Else
'				dialog.Show("Clave incorrecta,Whatapp: 3476649437","OK","","")
'				Return
'			End If
'		Else
'			Return
'		End If
'	Else
'		'pn_Menu.Visible=False
'		panel_menu_state=False
'	End If
	
	
	
'-----------------------	
'		If panel_menu_state = False Then
'			pn_Menu.Visible=True
'			panel_menu_state=True
'		Else
'			pn_Menu.Visible=False
'			panel_menu_state=False
'		End If
	
End Sub


Private Sub btn_config_Click
	StartActivity(ConfigActivity)
	
	primera_vez=True
	
	'btn_Menu_Click
End Sub

Private Sub btn_reset_Click
	lb_angMAX_izq.Text = "0.0 º"
	lb_angMAX_der.Text = "0.0 º"
	
	max_ang_izq=0
	max_ang_der=0
End Sub

Private Sub btn_reset_inclina_Click
	Starter.Manager.SendMessage("OFF_SET")
End Sub

Private Sub Salidas_manual() 
	Dim mens As String
	Dim sal_izq,sal_der As Int
	
	If btn_izq_state = True Then
		sal_izq = 0
	Else
		sal_izq = 1
	End If
	
	If btn_der_state = True Then
		sal_der = 0
	Else
		sal_der = 1
	End If
	
	modo = 1

	mens = "SAL,"
	mens = mens & modo & "," & sal_izq & "," & sal_der
	
	Log(mens)
	Starter.Manager.SendMessage(mens)
End Sub

Private Sub btn_forzar_salida_izq_Click
	If pn_cal_ang.Visible=False Then
		If btn_izq_state = False Then
			btn_izq_state=True
		Else
			btn_izq_state=False
		End If
		
		Salidas_manual
	Else
		pn_cal_ang.Visible=False
	End If
End Sub

Private Sub btn_forzar_salida_der_Click
	If pn_cal_ang.Visible=False Then
		If btn_der_state = False Then
			btn_der_state=True
		Else
			btn_der_state=False
		End If
		
		Salidas_manual
	Else
		pn_cal_ang.Visible=False
	End If
End Sub


Private Sub btn_on_off_Click
	Dim mens As String
	
	If modo = 1 Then
		mens = "MANUAL"
	Else
		mens = "AUTO"
	End If
	Log(mens)
	
	Starter.Manager.SendMessage(mens)
End Sub

Private Sub btn_angulos_Click
	see_angulo_P.Value=Ang_Limite*2
	see_angulo2_P.Value=Ang_Limite2*2
	
	pn_cal_ang.Visible=True	
End Sub

Private Sub img_fondo_Click
	
End Sub

Private Sub see_angulo_P_ValueChanged (Value As Int, UserChanged As Boolean)
	lb_ang1.Text = (see_angulo_P.Value/2)
	If see_angulo2_P.Value > see_angulo_P.Value Then
		see_angulo2_P.Value = see_angulo_P.Value-0.5 
	End If
End Sub

Private Sub see_angulo2_P_ValueChanged (Value As Int, UserChanged As Boolean)
	If see_angulo2_P.Value < see_angulo_P.Value Then
		lb_ang2.Text = (see_angulo2_P.Value/2)
	Else
		see_angulo2_P.Value = see_angulo_P.Value-0.5
	End If
End Sub

Private Sub pn_cal_ang_Click
	Return
End Sub

Private Sub btn_aceptar_Click
	If pn_cal_ang.Visible=False Then
		Return
	End If
	
	If (lb_ang1.Text = lb_Limite1.Text) And (lb_ang2.Text = lb_Limite2.Text) Then
		pn_cal_ang.Visible	= False
		Return
	End If
	
	lim=lb_ang1.Text
	lim2=lb_ang2.Text
			
	lb_Limite1.Text		= lb_ang1.Text
	lb_Limite2.Text		= lb_ang2.Text
	Ang_Limite			= lb_ang1.Text
	Ang_Limite2			= lb_ang2.Text
	
	pn_cal_ang.Visible	= False
	
	Gauge1.SetMinAndMax(Ang_Limite*-3,Ang_Limite*3)
	
	Gauge1.SetRanges(Array(Gauge1.CreateRange(Ang_Limite, 150, color3), _
					Gauge1.CreateRange(-150,Ang_Limite*-1, color3), _
					Gauge1.CreateRange(Ang_Limite,Ang_Limite2, color2), _
					Gauge1.CreateRange(Ang_Limite*-1,Ang_Limite2*-1, color2), _
					Gauge1.CreateRange(Ang_Limite2*-1,Ang_Limite2, color1)))
					
	If Main.Simulador=False Then
		Dim mens As String
	
		mens = "ANGULOS,"
		mens = mens & lb_ang1.Text & ","
		mens = mens & lb_ang2.Text
		
		Log(mens)
		If Ang_Limite <> 0 And Ang_Limite2 <> 0 Then
			ToastMessageShow("Enviando Datos y Guardandolos....", False)
			
			Starter.Manager.SendMessage(mens)
		End If
	End If
End Sub