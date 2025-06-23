B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=StaticCode
Version=11
@EndOfDesignText@
'Code module
'Subs in this code module will be accessible from all modules.
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Type Perfil_R(Valores(10) As Int)
	Type Perfil_U(Valores(10) As Int)
	
	Dim p_ID As Int
	Dim p_Nombre As String
	Dim p_FiltroComplementario As Float
	Dim p_FiltroPasaBajos As Int
	Dim p_AnguloDisparo As Float
	Dim p_AnguloApagado As Float
	Dim p_Yaw As Int
	Dim p_Gyro As Int
	Dim p_FrenadoG As Float
	Dim p_FrenadoT As Int
	
	Dim perfil1 As Perfil_Ruta
	Dim perfil2 As Perfil_Urbano
	
End Sub

Sub GuardoPerfil
	perfil2.Valores(0) 	= p_ID
	perfil2.Valores(1) 	= p_Nombre
	perfil2.Valores(2) 	= p_FiltroComplementario
	perfil2.Valores(3) 	= p_FiltroPasaBajos
	perfil2.Valores(4) 	= p_AnguloDisparo
	perfil2.Valores(5) 	= p_AnguloApagado
	perfil2.Valores(6) 	= p_Yaw
	perfil2.Valores(7)	= p_Gyro
	perfil2.Valores(8)	= p_FrenadoG
	perfil2.Valores(9)	= p_FrenadoT
End Sub

Private Sub RecuperoPerfil
	
End Sub