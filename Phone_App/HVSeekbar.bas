B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Class
Version=6.8
@EndOfDesignText@
'
'==========================================================================================================
' by Ivan Aldaz
' oct'17
'
'
' Generates horizontal and vertical seekbars, in which you can customize the shape and colors (pressed and 
' not pressed) of the slider, the background color and the width and colors in the two parts of the bar.
'
' You can also put negative values in Vmin, and set Visible and Enabled properties
'
' Hope you find it useful
'
'-------

' Example:
'
'    barV1.Initialize(Me, panelSeeks, "barV1")
'
'    barV1.SetColors(Colors.Gray, Colors.red, Colors.blue, Colors.Gray, Colors.White)  'Normally, cursor_Pressed_Color = line_Color
'    barV1.SetInitValues(-100, 300, 150) 'V min, V max, V init
'    barV1.SetShape(barV1.cursorType_RECTANGLE, 15dip, 40dip, 8dip)
'
'    barV1.ShowBar(5%x, 5%x, 10%x, 45%y, barV1.orientation_VERTICAL)
'
'
' You can skip SetColors, SetInitValues and/or SetShape. You can define in the Sub DefaultValues (Class module) the
' default values that will be set. 
'
' ==========================================================================================================

'Events declaration
#Event: ValueChanged(Value As Int, UserChanged As Boolean)



'Class module
Private Sub Class_Globals
	
	Private vModule As Object
	Private destObject As Activity
	Private panelBase, panelDraw, panelTouch As Panel
	Private barName As String
	
	Private cursor As Label

	Private lineWidth As Int
	Private cornerRadius As Int
	Private orient As String
	Private deltaX, deltaY As Int
	Private canvasSeek As Canvas
	Private lineCol, valueLineCol, cursorCol, cursorPresCol As Int
	Private vMax, vMin, vIni As Int
	Private cursorTop, cursorLeft, cursorWidth, cursorHeight As Int
	
	Private colorsSet As Boolean = False
	Private shapeSet  As Boolean = False
	Private valuesSet As Boolean = False
	
	Private x0Line, y0Line, x1Line, y1Line As Int
	Dim lineLenght As Int
	Private backCol As Int
	Dim seekValue As Int	
	
	Dim cdCursor, cdCursorPressed As ColorDrawable
		
End Sub

			    	'Put "Me"           Activity, panel, ...	
Public Sub Initialize(Module As Object, dest_Object As Object, Eventname As String)
	
	vModule	   = Module
	destObject = dest_Object
	barName    = Eventname
			
End Sub


Public Sub ShowBar(barLeft As Int, barTop As Int, barWidth As Int, barHeight As Int, orientation As String)
	
	orient = orientation
	
	Dim xIniValue, yIniValue As Int	
	
	DefaultValues
	
	If orient = orientation_HORIZONTAL Then 
		
		If barHeight < cursorHeight Then cursorHeight = barHeight 'cursor can't be wider than seekbar
		
		x0Line = cursorWidth/2
		y0Line = barHeight/2
		x1Line = barWidth - cursorWidth/2
		y1Line = y0Line

		lineLenght = x1Line - x0Line
		
		xIniValue = (x1Line - x0Line) * (vIni - vMin) / (vMax - vMin)	 'convert values to distances
		yIniValue = 0
			
		cursorTop = (barHeight - cursorHeight) / 2
		
		
	Else    ' "VERTICAL"
				
		If barWidth < cursorWidth Then cursorWidth = barWidth 'cursor can't be wider than seekbar
				
		x0Line = barWidth / 2
		y0Line = barHeight - cursorHeight / 2
		x1Line = x0Line
		y1Line = cursorHeight / 2
		
		lineLenght = y0Line - y1Line
		
		xIniValue = 0
		yIniValue = (y0Line - y1Line) * (vIni - vMin)  / (vMax - vMin)	 'convert values to distances
			
		cursorTop = barHeight - yIniValue - cursorHeight	
	
		
	End If

	
	panelBase.initialize("panelBase")
	panelDraw.Initialize("panelDraw")
	panelTouch.Initialize("panelTouch")
	
	destObject.AddView(panelBase,  barLeft, barTop, barWidth, barHeight)
	destObject.AddView(panelDraw,  barLeft, barTop, barWidth, barHeight)
	destObject.AddView(panelTouch, barLeft, barTop, barWidth, barHeight)

	panelBase.Elevation  = 0
	panelDraw.Elevation  = 2dip
	panelTouch.Elevation = 4dip
	
	panelBase.Color  = backCol
	panelDraw.Color  = Colors.Transparent
	panelTouch.Color = Colors.Transparent
	
	cdCursor.Initialize(cursorCol, cornerRadius)
	cdCursorPressed.Initialize(cursorPresCol, cornerRadius)
		
	cursor.Initialize("cursor")
	cursor.Background = cdCursor	
	
	cursorLeft = x0Line + xIniValue - cursorWidth / 2
	
	canvasSeek.Initialize(panelDraw)
	
	Dim rect1 As Rect
		rect1.Initialize(0,0,panelBase.Width, panelBase.Height)
		
	canvasSeek.DrawLine(x0Line, y0Line, x1Line,             y1Line,             lineCol,      lineWidth)
	canvasSeek.DrawLine(x0Line, y0Line, x0Line + xIniValue, y0Line - yIniValue, valueLineCol, lineWidth)
	
	panelDraw.AddView(cursor, cursorLeft, cursorTop, cursorWidth, cursorHeight)

	seekValue = vIni
		
End Sub	
	

Private Sub setPosition(dX As Int, dY As Int)
	
	Dim x2Line, y2Line As Int 
	
	If orient = orientation_HORIZONTAL Then 
		
		x2Line = dX
		y2Line = y0Line
		deltaX = x2Line - x0Line
		deltaY = 0
				
	Else
		
		x2Line = x0Line
		y2Line = panelDraw.Height - dY
		deltaX = 0
		deltaY = y0Line - y2Line
		
	End If
	
	
	canvasSeek.DrawLine(x0Line, y0Line, x1Line, y1Line, lineCol, lineWidth)
	canvasSeek.DrawLine(x0Line, y0Line, x2Line, y2Line, valueLineCol, lineWidth)
	panelDraw.Invalidate
			
	cursorLeft = x2Line - cursorWidth  / 2
	cursorTop  = y2Line - cursorHeight / 2
	
	cursor.SetLayout(cursorLeft, cursorTop, cursorWidth, cursorHeight)
		
		
'Just convert distance to value. In HORIZONTAL, deltaY=0; in VERTICAL, deltaX=0
		
	seekValue = vMin + Max(deltaX, deltaY)/lineLenght * (vMax - vMin)
		
	If SubExists(vModule, barName & "_ValueChanged") Then
		
	     CallSub2(vModule,barName & "_ValueChanged", seekValue)
	  
	End If

	
End Sub


Private Sub panelTouch_Touch(Action As Int, X As Float, Y As Float) As Boolean 
	
	Select Action
		
		Case panelTouch.ACTION_DOWN
			cursor.Background = cdCursorPressed
			
		Case panelTouch.ACTION_UP
			cursor.Background = cdCursor
		
	End Select
	
	
	Dim auxX As Int = X
	Dim auxY As Int = Y
			
	If X < x0Line Then auxX = x0Line
	If X > x1Line Then auxX = x1Line
	If Y < y1Line Then auxY = y1Line
	If Y > y0Line Then auxY = y0Line
		
	Dim distX As Int = auxX
	Dim distY As Int = panelDraw.Height - auxY 
	
	setPosition(distX, distY)
			
	Return True
	
End Sub


'========================================================================================


Sub DefaultValues
		
	If Not(shapeSet) Then 'default "ROUNDED" with 20dip diameter
		
		cursorWidth    = 20dip		  '20dip diameter circle
		cursorHeight   = cursorWidth
		cornerRadius   = cursorWidth/2
		lineWidth      = 3dip
		
	End If
	
	If Not(colorsSet) Then 
		
		lineCol       = Colors.Gray
		valueLineCol  = Colors.Cyan
		cursorCol     = Colors.Cyan
		cursorPresCol = lineCol
		backCol       = Colors.Transparent
				
	End If
	
	If Not(valuesSet) Then
		
		vMin = 0
		vMax = 100
		vIni = 0
		
	End If
	
End Sub
	
Sub SetShape(cursor_Type As String, cursor_Width As Int, cursor_Height As Int, line_Width As Int)
		
	cursorHeight = cursor_Height
	cursorWidth	 = cursor_Width
	lineWidth    = line_Width	
		
		If cursor_Type = cursorType_ROUNDED Then
			cornerRadius = Min(cursorHeight, cursorWidth) * .5
		Else ' "RECTANGLE"
			cornerRadius = Min(cursorHeight, cursorWidth) * .2 '20% of the rectangle's short side
		End If

	shapeSet = True


End Sub

Sub SetColors(lineColor As Int, valueLineColor As Int, cursor_Color As Int, cursorPressed_Color As Int, back_Color As Int) 
		
	lineCol       = lineColor
	valueLineCol  = valueLineColor
	cursorCol     = cursor_Color
	backCol       = back_Color
	cursorPresCol = cursorPressed_Color

	colorsSet = True
	
End Sub

Sub SetInitValues(valueMin As Int, valueMax As Int, iniValue As Int)

	vMin = valueMin
	vMax = valueMax
	vIni = iniValue
	
	valuesSet = True
	
End Sub


Sub getSeekValue As Int
		
	Return seekValue
	
End Sub


'Class PROPERTIES

Sub Visible (v As Boolean)
	panelBase.Visible  = v
	panelDraw.Visible  = v
	panelTouch.Visible = v
End Sub

Sub BackColor (c As Int)
	panelBase.Color = c
End Sub

Sub Enabled(e As Boolean)
	panelBase.Enabled  = e
	panelDraw.Enabled  = e
	panelTouch.Enabled = e
End Sub


'Class CONSTANTS)

Sub cursorType_ROUNDED As String
	Return "ROUNDED"
End Sub

Sub cursorType_RECTANGLE As String
	Return "RECTANGLE"
End Sub

Sub orientation_VERTICAL As String
	Return "VERTICAL"	
End Sub

Sub orientation_HORIZONTAL As String
	Return "HORIZONTAL"
End Sub
