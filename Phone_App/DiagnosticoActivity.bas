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
	Dim eje,accX, accY As Int
	
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	
	Dim     cadena2()	  As String
	Private Range_Pich    As ASRangeSeekBar
	Private Range_Roll    As ASRangeSeekBar
	Private lb_roll_Izq   As Label
	Private lb_pich_del   As Label
	Private lb_roll_der   As Label
	Private lb_pich_atras As Label
	Private btn_calibrar  As Button
	Private lb_roll       As Label
	Private lb_xyaw       As Label
	Private lb_yaw        As Label
	Private ln_xgyro      As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("4")
	Activity.Title="DIAGNOSTICO DEL SISTEMA"
End Sub

Sub Activity_Resume
	
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean
	If KeyCode = KeyCodes.KEYCODE_BACK Then
		Activity.Finish
		StartActivity(ChatActivity)
	End If
End Sub

Public Sub NewMessage2 (msg As String)
	LogMessage(msg)
End Sub

Sub LogMessage(Msg As String)
	cadena2 = Regex.Split(",",Msg)	'Separo el mesaje
	
	If cadena2(0) <> "CL" Then
		Return
	End If

	Try
		If cadena2(0) = "CL" Then
			
			lb_roll.Text= cadena2(1)
			lb_xyaw.Text= cadena2(15)
			lb_yaw.Text	= cadena2(16)
			
			eje 		= cadena2(6)
			
			If eje = 2 Then
				accY 	= cadena2(13) * -100
				accX 	= cadena2(14) * -100
			Else
				accY	= cadena2(14) * -100
				accX	= cadena2(13) * -100
			End If
			
			ln_xgyro.Text = Round2(131 / cadena2(17),2)
			
			If accX < -1 Then
				lb_roll_Izq.Text  = Abs(accX)
				lb_roll_der.Text  = 0
				Range_Roll.Value1 = 1000+accX
				Range_Roll.Value2 = 1000
			Else if accX > -2 And accX < 2 Then
				Range_Roll.Value1 = 1000
				Range_Roll.Value2 = 1000
				lb_roll_Izq.Text  = 0
				lb_roll_der.Text  = 0				
			Else if accX > 1 Then
				lb_roll_der.Text  = Abs(accX)
				lb_roll_Izq.Text  = 0
				Range_Roll.Value2 = 1000+accX
				Range_Roll.Value1 = 1000
			End If
			
			
			If accY < -1 Then
				lb_pich_del.Text = Abs(accY)
				lb_pich_atras.Text = 0
				Range_Pich.Value1=1000+accY
				Range_Pich.Value2=1000
			Else if accY > -1 And accY < 1 Then
				Range_Pich.Value1 =1000
				Range_Pich.Value2 =1000
			Else if accY > 1 Then
				lb_pich_atras.Text = Abs(accY)
				lb_pich_del.Text =0
				Range_Pich.Value2=1000+accY
				Range_Pich.Value1 =1000
			End If
		Else
			Return
		End If
	Catch
		Log(LastException)
	End Try
	
End Sub

Private Sub btn_calibrar_LongClick
	Dim mens As String
	mens = "Esta por CALIBRAR el dispositivo. "
	
	Msgbox2Async(mens, "Corner Light", "Sí", "", "No", Null, False)
	
	Wait For Msgbox_Result (Result As Int)
	
	If Result = DialogResponse.POSITIVE Then
		ProgressDialogShow2("Calibrando....",False)
				
		EnviarDatos(True)
		
		Sleep(600 * ChatActivity.Imu)
		ProgressDialogHide
	End If
End Sub

private Sub EnviarDatos (Calib As Boolean)
	'----------------------------------------------------------------------------------------------------
	'TRAMA-->CL,filtro,angulo_limite,histeresis_act,histeresis_des,eje,invertir,modo,calibracion,delay
	'----------------------------------------------------------------------------------------------------
	
	Dim cadena As String
	cadena = "CALIB"
	
	Starter.Manager.SendMessage(cadena)
	Log(cadena)
	ToastMessageShow("Enviando Datos y Guardandolos....", False)
End Sub