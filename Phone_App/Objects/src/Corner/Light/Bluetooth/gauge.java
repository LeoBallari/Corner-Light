package Corner.Light.Bluetooth;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class gauge extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "Corner.Light.Bluetooth.gauge");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", Corner.Light.Bluetooth.gauge.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public String _meventname = "";
public Object _mcallback = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _mbase = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public anywheresoftware.b4a.objects.B4XCanvas _cvsgauge = null;
public anywheresoftware.b4a.objects.B4XCanvas _cvsindicator = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _mlbl = null;
public anywheresoftware.b4a.objects.collections.List _mranges = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _gaugepanel = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _indicatorpanel = null;
public float _radius = 0f;
public float _x = 0f;
public float _y = 0f;
public int _indicatorlength = 0;
public int _centercolor = 0;
public int _indicatorcolor = 0;
public float _minvalue = 0f;
public float _maxvalue = 0f;
public float _indicatorbasewidth = 0f;
public float _mcurrentvalue = 0f;
public String _prefixtext = "";
public String _suffixtext = "";
public int _durationfromzeroto100 = 0;
public int _half_circle = 0;
public int _full_circle = 0;
public int _gaugetype = 0;
public int _anglerange = 0;
public int _angleoffset = 0;
public int _backgroundcolor = 0;
public b4a.example.dateutils _dateutils = null;
public Corner.Light.Bluetooth.main _main = null;
public Corner.Light.Bluetooth.chatactivity _chatactivity = null;
public Corner.Light.Bluetooth.configactivity _configactivity = null;
public Corner.Light.Bluetooth.diagnosticoactivity _diagnosticoactivity = null;
public Corner.Light.Bluetooth.starter _starter = null;
public Corner.Light.Bluetooth.xuiviewsutils _xuiviewsutils = null;
public static class _gaugerange{
public boolean IsInitialized;
public float LowValue;
public float HighValue;
public int Color;
public void Initialize() {
IsInitialized = true;
LowValue = 0f;
HighValue = 0f;
Color = 0;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public void  _animatevalueto(float _newvalue) throws Exception{
ResumableSub_AnimateValueTo rsub = new ResumableSub_AnimateValueTo(this,_newvalue);
rsub.resume(ba, null);
}
public static class ResumableSub_AnimateValueTo extends BA.ResumableSub {
public ResumableSub_AnimateValueTo(Corner.Light.Bluetooth.gauge parent,float _newvalue) {
this.parent = parent;
this._newvalue = _newvalue;
}
Corner.Light.Bluetooth.gauge parent;
float _newvalue;
long _n = 0L;
int _duration = 0;
float _start = 0f;
float _tempvalue = 0f;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 261;BA.debugLine="Dim n As Long = DateTime.Now";
_n = parent.__c.DateTime.getNow();
 //BA.debugLineNum = 262;BA.debugLine="Dim duration As Int = Abs(mCurrentValue - NewValu";
_duration = (int) (parent.__c.Abs(parent._mcurrentvalue-_newvalue)/(double)100*parent._durationfromzeroto100+1000);
 //BA.debugLineNum = 263;BA.debugLine="Dim start As Float = mCurrentValue";
_start = parent._mcurrentvalue;
 //BA.debugLineNum = 264;BA.debugLine="mCurrentValue = NewValue";
parent._mcurrentvalue = _newvalue;
 //BA.debugLineNum = 265;BA.debugLine="Dim tempValue As Float";
_tempvalue = 0f;
 //BA.debugLineNum = 266;BA.debugLine="Do While DateTime.Now < n + duration";
if (true) break;

case 1:
//do while
this.state = 10;
while (parent.__c.DateTime.getNow()<_n+_duration) {
this.state = 3;
if (true) break;
}
if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 267;BA.debugLine="tempValue = ValueFromTimeEaseInOut(DateTime.Now";
_tempvalue = parent._valuefromtimeeaseinout((float) (parent.__c.DateTime.getNow()-_n),_start,(float) (_newvalue-_start),_duration);
 //BA.debugLineNum = 268;BA.debugLine="DrawIndicator(tempValue)";
parent._drawindicator(_tempvalue);
 //BA.debugLineNum = 269;BA.debugLine="Sleep(10)";
parent.__c.Sleep(ba,this,(int) (10));
this.state = 11;
return;
case 11:
//C
this.state = 4;
;
 //BA.debugLineNum = 270;BA.debugLine="If NewValue <> mCurrentValue Then Return 'will h";
if (true) break;

case 4:
//if
this.state = 9;
if (_newvalue!=parent._mcurrentvalue) { 
this.state = 6;
;}if (true) break;

case 6:
//C
this.state = 9;
if (true) return ;
if (true) break;

case 9:
//C
this.state = 1;
;
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 272;BA.debugLine="DrawIndicator(mCurrentValue)";
parent._drawindicator(parent._mcurrentvalue);
 //BA.debugLineNum = 273;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public String  _base_resize(double _width,double _height) throws Exception{
 //BA.debugLineNum = 89;BA.debugLine="Private Sub Base_Resize (Width As Double, Height A";
 //BA.debugLineNum = 94;BA.debugLine="GaugePanel.SetLayoutAnimated(0, 0, 0, Width, Heig";
_gaugepanel.SetLayoutAnimated((int) (0),(int) (0),(int) (0),(int) (_width),(int) (_height));
 //BA.debugLineNum = 95;BA.debugLine="cvsGauge.Resize(Width, Height)";
_cvsgauge.Resize((float) (_width),(float) (_height));
 //BA.debugLineNum = 96;BA.debugLine="IndicatorPanel.SetLayoutAnimated(0, 0, 0, Width,";
_indicatorpanel.SetLayoutAnimated((int) (0),(int) (0),(int) (0),(int) (_width),(int) (_height));
 //BA.debugLineNum = 97;BA.debugLine="cvsIndicator.Resize(Width, Height)";
_cvsindicator.Resize((float) (_width),(float) (_height));
 //BA.debugLineNum = 99;BA.debugLine="DrawBackground";
_drawbackground();
 //BA.debugLineNum = 100;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 13;BA.debugLine="Private mEventName As String 'ignore";
_meventname = "";
 //BA.debugLineNum = 14;BA.debugLine="Private mCallBack As Object 'ignore";
_mcallback = new Object();
 //BA.debugLineNum = 15;BA.debugLine="Private mBase As B4XView";
_mbase = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 17;BA.debugLine="Private cvsGauge, cvsIndicator As B4XCanvas";
_cvsgauge = new anywheresoftware.b4a.objects.B4XCanvas();
_cvsindicator = new anywheresoftware.b4a.objects.B4XCanvas();
 //BA.debugLineNum = 18;BA.debugLine="Private mlbl As B4XView";
_mlbl = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Type GaugeRange (LowValue As Float, HighValue As";
;
 //BA.debugLineNum = 20;BA.debugLine="Private mRanges As List";
_mranges = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 21;BA.debugLine="Private GaugePanel, IndicatorPanel As B4XView";
_gaugepanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
_indicatorpanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private Radius, x, y As Float";
_radius = 0f;
_x = 0f;
_y = 0f;
 //BA.debugLineNum = 23;BA.debugLine="Private IndicatorLength As Int";
_indicatorlength = 0;
 //BA.debugLineNum = 24;BA.debugLine="Private CenterColor, IndicatorColor As Int";
_centercolor = 0;
_indicatorcolor = 0;
 //BA.debugLineNum = 25;BA.debugLine="Private MinValue, MaxValue As Float";
_minvalue = 0f;
_maxvalue = 0f;
 //BA.debugLineNum = 26;BA.debugLine="Private IndicatorBaseWidth As Float";
_indicatorbasewidth = 0f;
 //BA.debugLineNum = 27;BA.debugLine="Private mCurrentValue As Float = 50";
_mcurrentvalue = (float) (50);
 //BA.debugLineNum = 28;BA.debugLine="Private PrefixText, SuffixText As String";
_prefixtext = "";
_suffixtext = "";
 //BA.debugLineNum = 29;BA.debugLine="Private DurationFromZeroTo100 As Int";
_durationfromzeroto100 = 0;
 //BA.debugLineNum = 30;BA.debugLine="Private HALF_CIRCLE = 1, FULL_CIRCLE = 2 As Int";
_half_circle = (int) (1);
_full_circle = (int) (2);
 //BA.debugLineNum = 31;BA.debugLine="Private GaugeType As Int";
_gaugetype = 0;
 //BA.debugLineNum = 32;BA.debugLine="Private AngleRange As Int";
_anglerange = 0;
 //BA.debugLineNum = 33;BA.debugLine="Private AngleOffset As Int";
_angleoffset = 0;
 //BA.debugLineNum = 34;BA.debugLine="Private BackgroundColor As Int";
_backgroundcolor = 0;
 //BA.debugLineNum = 36;BA.debugLine="End Sub";
return "";
}
public String  _configurefullcircle() throws Exception{
 //BA.debugLineNum = 145;BA.debugLine="Private Sub ConfigureFullCircle";
 //BA.debugLineNum = 146;BA.debugLine="Radius = Min(GaugePanel.Width, GaugePanel.Height)";
_radius = (float) (__c.Min(_gaugepanel.getWidth(),_gaugepanel.getHeight())/(double)2-__c.DipToCurrent((int) (3)));
 //BA.debugLineNum = 147;BA.debugLine="x = cvsGauge.TargetRect.CenterX";
_x = _cvsgauge.getTargetRect().getCenterX();
 //BA.debugLineNum = 148;BA.debugLine="y = cvsGauge.TargetRect.CenterY";
_y = _cvsgauge.getTargetRect().getCenterY();
 //BA.debugLineNum = 149;BA.debugLine="AngleOffset = 135";
_angleoffset = (int) (135);
 //BA.debugLineNum = 150;BA.debugLine="AngleRange = 270";
_anglerange = (int) (270);
 //BA.debugLineNum = 151;BA.debugLine="IndicatorBaseWidth = 6dip";
_indicatorbasewidth = (float) (__c.DipToCurrent((int) (6)));
 //BA.debugLineNum = 152;BA.debugLine="CenterColor = IndicatorColor";
_centercolor = _indicatorcolor;
 //BA.debugLineNum = 154;BA.debugLine="End Sub";
return "";
}
public String  _configurehalfcircle() throws Exception{
 //BA.debugLineNum = 136;BA.debugLine="Private Sub ConfigureHalfCircle";
 //BA.debugLineNum = 137;BA.debugLine="Radius = Min(GaugePanel.Width / 2, GaugePanel.Hei";
_radius = (float) (__c.Min(_gaugepanel.getWidth()/(double)2,_gaugepanel.getHeight()));
 //BA.debugLineNum = 138;BA.debugLine="x = cvsGauge.TargetRect.CenterX";
_x = _cvsgauge.getTargetRect().getCenterX();
 //BA.debugLineNum = 139;BA.debugLine="y = cvsGauge.TargetRect.Height";
_y = _cvsgauge.getTargetRect().getHeight();
 //BA.debugLineNum = 140;BA.debugLine="AngleOffset = 180";
_angleoffset = (int) (180);
 //BA.debugLineNum = 141;BA.debugLine="AngleRange = 180";
_anglerange = (int) (180);
 //BA.debugLineNum = 142;BA.debugLine="IndicatorBaseWidth = 20dip";
_indicatorbasewidth = (float) (__c.DipToCurrent((int) (20)));
 //BA.debugLineNum = 143;BA.debugLine="End Sub";
return "";
}
public Corner.Light.Bluetooth.gauge._gaugerange  _createrange(float _lowvalue,float _highvalue,int _color) throws Exception{
Corner.Light.Bluetooth.gauge._gaugerange _r = null;
 //BA.debugLineNum = 237;BA.debugLine="Public Sub CreateRange(LowValue As Float, HighValu";
 //BA.debugLineNum = 238;BA.debugLine="Dim r As GaugeRange";
_r = new Corner.Light.Bluetooth.gauge._gaugerange();
 //BA.debugLineNum = 239;BA.debugLine="r.Initialize";
_r.Initialize();
 //BA.debugLineNum = 240;BA.debugLine="r.LowValue = LowValue";
_r.LowValue /*float*/  = _lowvalue;
 //BA.debugLineNum = 241;BA.debugLine="r.HighValue = HighValue";
_r.HighValue /*float*/  = _highvalue;
 //BA.debugLineNum = 242;BA.debugLine="r.Color = Color";
_r.Color /*int*/  = _color;
 //BA.debugLineNum = 243;BA.debugLine="Return r";
if (true) return _r;
 //BA.debugLineNum = 244;BA.debugLine="End Sub";
return null;
}
public String  _designercreateview(Object _base,anywheresoftware.b4a.objects.LabelWrapper _lbl,anywheresoftware.b4a.objects.collections.Map _props) throws Exception{
anywheresoftware.b4a.objects.collections.Map _m = null;
Object _nativefont = null;
float _lblheight = 0f;
 //BA.debugLineNum = 45;BA.debugLine="Public Sub DesignerCreateView (Base As Object, Lbl";
 //BA.debugLineNum = 46;BA.debugLine="IndicatorLength = DipToCurrent(Props.Get(\"Indicat";
_indicatorlength = __c.DipToCurrent((int)(BA.ObjectToNumber(_props.Get((Object)("IndicatorLength")))));
 //BA.debugLineNum = 47;BA.debugLine="CenterColor = xui.PaintOrColorToColor(Props.Get(\"";
_centercolor = _xui.PaintOrColorToColor(_props.Get((Object)("CenterColor")));
 //BA.debugLineNum = 48;BA.debugLine="IndicatorColor = xui.PaintOrColorToColor(Props.Ge";
_indicatorcolor = _xui.PaintOrColorToColor(_props.Get((Object)("IndicatorColor")));
 //BA.debugLineNum = 49;BA.debugLine="BackgroundColor = xui.PaintOrColorToColor(Props.G";
_backgroundcolor = _xui.PaintOrColorToColor(_props.GetDefault((Object)("BackgroundColor"),(Object)(_xui.Color_White)));
 //BA.debugLineNum = 50;BA.debugLine="Dim m As Map = CreateMap(\"Half Circle\": HALF_CIRC";
_m = new anywheresoftware.b4a.objects.collections.Map();
_m = __c.createMap(new Object[] {(Object)("Half Circle"),(Object)(_half_circle),(Object)("Full Circle"),(Object)(_full_circle)});
 //BA.debugLineNum = 51;BA.debugLine="GaugeType = m.Get(Props.GetDefault(\"GaugeType\", \"";
_gaugetype = (int)(BA.ObjectToNumber(_m.Get(_props.GetDefault((Object)("GaugeType"),(Object)("Half Circle")))));
 //BA.debugLineNum = 52;BA.debugLine="MinValue = Props.Get(\"MinValue\")";
_minvalue = (float)(BA.ObjectToNumber(_props.Get((Object)("MinValue"))));
 //BA.debugLineNum = 53;BA.debugLine="MaxValue = Props.Get(\"MaxValue\")";
_maxvalue = (float)(BA.ObjectToNumber(_props.Get((Object)("MaxValue"))));
 //BA.debugLineNum = 54;BA.debugLine="PrefixText = Props.Get(\"PrefixText\")";
_prefixtext = BA.ObjectToString(_props.Get((Object)("PrefixText")));
 //BA.debugLineNum = 55;BA.debugLine="SuffixText = Props.Get(\"SuffixText\")";
_suffixtext = BA.ObjectToString(_props.Get((Object)("SuffixText")));
 //BA.debugLineNum = 56;BA.debugLine="DurationFromZeroTo100 = Props.Get(\"Duration\")";
_durationfromzeroto100 = (int)(BA.ObjectToNumber(_props.Get((Object)("Duration"))));
 //BA.debugLineNum = 57;BA.debugLine="mBase = Base";
_mbase = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_base));
 //BA.debugLineNum = 58;BA.debugLine="Dim NativeFont As Object";
_nativefont = new Object();
 //BA.debugLineNum = 64;BA.debugLine="NativeFont = Typeface.LoadFromAssets(\"Crysta.ttf\"";
_nativefont = (Object)(__c.Typeface.LoadFromAssets("Crysta.ttf"));
 //BA.debugLineNum = 68;BA.debugLine="GaugePanel = xui.CreatePanel(\"\")";
_gaugepanel = _xui.CreatePanel(ba,"");
 //BA.debugLineNum = 70;BA.debugLine="mBase.AddView(GaugePanel, 0, 0, mBase.Width, mBas";
_mbase.AddView((android.view.View)(_gaugepanel.getObject()),(int) (0),(int) (0),_mbase.getWidth(),_mbase.getHeight());
 //BA.debugLineNum = 71;BA.debugLine="cvsGauge.Initialize(GaugePanel)";
_cvsgauge.Initialize(_gaugepanel);
 //BA.debugLineNum = 72;BA.debugLine="IndicatorPanel = xui.CreatePanel(\"\")";
_indicatorpanel = _xui.CreatePanel(ba,"");
 //BA.debugLineNum = 73;BA.debugLine="mBase.AddView(IndicatorPanel, 0, 0, mBase.Width,";
_mbase.AddView((android.view.View)(_indicatorpanel.getObject()),(int) (0),(int) (0),_mbase.getWidth(),_mbase.getHeight());
 //BA.debugLineNum = 75;BA.debugLine="cvsIndicator.Initialize(IndicatorPanel)";
_cvsindicator.Initialize(_indicatorpanel);
 //BA.debugLineNum = 76;BA.debugLine="mlbl = Lbl";
_mlbl = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_lbl.getObject()));
 //BA.debugLineNum = 77;BA.debugLine="mlbl.Font = xui.CreateFont(NativeFont, 75)";
_mlbl.setFont(_xui.CreateFont((android.graphics.Typeface)(_nativefont),(float) (75)));
 //BA.debugLineNum = 79;BA.debugLine="mlbl.SetTextAlignment(\"BUTTON\", \"CENTER\")";
_mlbl.SetTextAlignment("BUTTON","CENTER");
 //BA.debugLineNum = 80;BA.debugLine="mlbl.TextColor = xui.Color_Black";
_mlbl.setTextColor(_xui.Color_Black);
 //BA.debugLineNum = 81;BA.debugLine="mlbl.TextColor = xui.Color_White";
_mlbl.setTextColor(_xui.Color_White);
 //BA.debugLineNum = 82;BA.debugLine="Dim lblheight As Float";
_lblheight = 0f;
 //BA.debugLineNum = 83;BA.debugLine="If PrefixText.Contains(CRLF) Then lblheight = 65d";
if (_prefixtext.contains(__c.CRLF)) { 
_lblheight = (float) (__c.DipToCurrent((int) (65)));}
else {
_lblheight = (float) (__c.DipToCurrent((int) (35)));};
 //BA.debugLineNum = 84;BA.debugLine="mBase.AddView(mlbl, 0, 0, 0, lblheight) 'label si";
_mbase.AddView((android.view.View)(_mlbl.getObject()),(int) (0),(int) (0),(int) (0),(int) (_lblheight));
 //BA.debugLineNum = 86;BA.debugLine="Base_Resize(mBase.Width, mBase.Height)";
_base_resize(_mbase.getWidth(),_mbase.getHeight());
 //BA.debugLineNum = 87;BA.debugLine="End Sub";
return "";
}
public String  _drawbackground() throws Exception{
Corner.Light.Bluetooth.gauge._gaugerange _gr = null;
anywheresoftware.b4a.objects.B4XCanvas.B4XPath _p = null;
float _startangle = 0f;
 //BA.debugLineNum = 108;BA.debugLine="Private Sub DrawBackground";
 //BA.debugLineNum = 109;BA.debugLine="cvsGauge.ClearRect(cvsGauge.TargetRect)";
_cvsgauge.ClearRect(_cvsgauge.getTargetRect());
 //BA.debugLineNum = 110;BA.debugLine="Select GaugeType";
switch (BA.switchObjectToInt(_gaugetype,_half_circle,_full_circle)) {
case 0: {
 //BA.debugLineNum = 112;BA.debugLine="ConfigureHalfCircle";
_configurehalfcircle();
 break; }
case 1: {
 //BA.debugLineNum = 114;BA.debugLine="ConfigureFullCircle";
_configurefullcircle();
 break; }
}
;
 //BA.debugLineNum = 116;BA.debugLine="cvsGauge.DrawCircle(x, y, Radius + 1dip, Backgrou";
_cvsgauge.DrawCircle(_x,_y,(float) (_radius+__c.DipToCurrent((int) (1))),_backgroundcolor,__c.True,(float) (0));
 //BA.debugLineNum = 117;BA.debugLine="For Each gr As GaugeRange In mRanges";
{
final anywheresoftware.b4a.BA.IterableList group9 = _mranges;
final int groupLen9 = group9.getSize()
;int index9 = 0;
;
for (; index9 < groupLen9;index9++){
_gr = (Corner.Light.Bluetooth.gauge._gaugerange)(group9.Get(index9));
 //BA.debugLineNum = 118;BA.debugLine="Dim p As B4XPath";
_p = new anywheresoftware.b4a.objects.B4XCanvas.B4XPath();
 //BA.debugLineNum = 119;BA.debugLine="Dim StartAngle As Float = ValueToAngle(gr.LowVal";
_startangle = _valuetoangle(_gr.LowValue /*float*/ );
 //BA.debugLineNum = 120;BA.debugLine="p.InitializeArc(x, y, Radius, StartAngle, ValueT";
_p.InitializeArc(_x,_y,_radius,_startangle,(float) (_valuetoangle(_gr.HighValue /*float*/ )-_startangle));
 //BA.debugLineNum = 121;BA.debugLine="cvsGauge.ClipPath(p)";
_cvsgauge.ClipPath(_p);
 //BA.debugLineNum = 122;BA.debugLine="cvsGauge.DrawCircle(x, y, Radius, gr.Color, True";
_cvsgauge.DrawCircle(_x,_y,_radius,_gr.Color /*int*/ ,__c.True,(float) (0));
 //BA.debugLineNum = 123;BA.debugLine="cvsGauge.RemoveClip";
_cvsgauge.RemoveClip();
 }
};
 //BA.debugLineNum = 125;BA.debugLine="cvsGauge.DrawCircle(x, y, Radius - IndicatorLengt";
_cvsgauge.DrawCircle(_x,_y,(float) (_radius-_indicatorlength),_backgroundcolor,__c.True,(float) (0));
 //BA.debugLineNum = 126;BA.debugLine="If GaugeType = FULL_CIRCLE And PrefixText <> \"\" T";
if (_gaugetype==_full_circle && (_prefixtext).equals("") == false) { 
 //BA.debugLineNum = 127;BA.debugLine="cvsGauge.DrawText(PrefixText, x, y - 30dip, xui.";
_cvsgauge.DrawText(ba,_prefixtext,_x,(float) (_y-__c.DipToCurrent((int) (30))),_xui.CreateDefaultFont((float) (16)),_xui.Color_Black,BA.getEnumFromString(android.graphics.Paint.Align.class,"CENTER"));
 //BA.debugLineNum = 129;BA.debugLine="cvsGauge.DrawCircle(x, y, Radius + 1dip, xui.Col";
_cvsgauge.DrawCircle(_x,_y,(float) (_radius+__c.DipToCurrent((int) (1))),_xui.Color_Gray,__c.False,(float) (__c.DipToCurrent((int) (1))));
 };
 //BA.debugLineNum = 131;BA.debugLine="DrawTicks";
_drawticks();
 //BA.debugLineNum = 132;BA.debugLine="cvsGauge.Invalidate";
_cvsgauge.Invalidate();
 //BA.debugLineNum = 133;BA.debugLine="DrawIndicator(mCurrentValue)";
_drawindicator(_mcurrentvalue);
 //BA.debugLineNum = 134;BA.debugLine="End Sub";
return "";
}
public String  _drawindicator(float _value) throws Exception{
float _angle = 0f;
anywheresoftware.b4a.objects.B4XCanvas.B4XPath _p = null;
float _tiplength = 0f;
float _circleradius = 0f;
String _s = "";
 //BA.debugLineNum = 181;BA.debugLine="Private Sub DrawIndicator (Value As Float)";
 //BA.debugLineNum = 182;BA.debugLine="cvsIndicator.ClearRect(cvsIndicator.TargetRect)";
_cvsindicator.ClearRect(_cvsindicator.getTargetRect());
 //BA.debugLineNum = 183;BA.debugLine="Dim angle As Float = ValueToAngle(Value) ' Ángulo";
_angle = _valuetoangle(_value);
 //BA.debugLineNum = 184;BA.debugLine="Dim p As B4XPath";
_p = new anywheresoftware.b4a.objects.B4XCanvas.B4XPath();
 //BA.debugLineNum = 188;BA.debugLine="p.Initialize(x + IndicatorBaseWidth * SinD(angle)";
_p.Initialize((float) (_x+_indicatorbasewidth*__c.SinD(_angle)),(float) (_y-_indicatorbasewidth*__c.CosD(_angle)));
 //BA.debugLineNum = 191;BA.debugLine="Dim tipLength As Float = Radius - 0.3 * Indicator";
_tiplength = (float) (_radius-0.3*_indicatorlength);
 //BA.debugLineNum = 192;BA.debugLine="p.LineTo(x + tipLength * CosD(angle), y + tipLeng";
_p.LineTo((float) (_x+_tiplength*__c.CosD(_angle)),(float) (_y+_tiplength*__c.SinD(_angle)));
 //BA.debugLineNum = 195;BA.debugLine="p.LineTo(x - IndicatorBaseWidth * SinD(angle), y";
_p.LineTo((float) (_x-_indicatorbasewidth*__c.SinD(_angle)),(float) (_y+_indicatorbasewidth*__c.CosD(_angle)));
 //BA.debugLineNum = 198;BA.debugLine="p.LineTo(x + IndicatorBaseWidth * SinD(angle), y";
_p.LineTo((float) (_x+_indicatorbasewidth*__c.SinD(_angle)),(float) (_y-_indicatorbasewidth*__c.CosD(_angle)));
 //BA.debugLineNum = 201;BA.debugLine="cvsIndicator.DrawPath(p, IndicatorColor, True, 0)";
_cvsindicator.DrawPath(_p,_indicatorcolor,__c.True,(float) (0));
 //BA.debugLineNum = 204;BA.debugLine="Dim CircleRadius As Float";
_circleradius = 0f;
 //BA.debugLineNum = 205;BA.debugLine="Dim s As String";
_s = "";
 //BA.debugLineNum = 206;BA.debugLine="If GaugeType = HALF_CIRCLE Then";
if (_gaugetype==_half_circle) { 
 //BA.debugLineNum = 207;BA.debugLine="s = PrefixText.ToUpperCase";
_s = _prefixtext.toUpperCase();
 //BA.debugLineNum = 208;BA.debugLine="CircleRadius = Radius - IndicatorLength";
_circleradius = (float) (_radius-_indicatorlength);
 }else {
 //BA.debugLineNum = 210;BA.debugLine="CircleRadius = IndicatorBaseWidth";
_circleradius = _indicatorbasewidth;
 };
 //BA.debugLineNum = 212;BA.debugLine="cvsIndicator.DrawCircle(x, y, CircleRadius, Cente";
_cvsindicator.DrawCircle(_x,_y,_circleradius,_centercolor,__c.True,(float) (0));
 //BA.debugLineNum = 213;BA.debugLine="cvsIndicator.Invalidate";
_cvsindicator.Invalidate();
 //BA.debugLineNum = 217;BA.debugLine="If (Value < 0) Then";
if ((_value<0)) { 
 //BA.debugLineNum = 218;BA.debugLine="mlbl.Text = s & NumberFormat2((Value)*-1, 2, 1,";
_mlbl.setText(BA.ObjectToCharSequence(_s+__c.NumberFormat2((_value)*-1,(int) (2),(int) (1),(int) (1),__c.True)+_suffixtext));
 }else {
 //BA.debugLineNum = 220;BA.debugLine="mlbl.Text = s & NumberFormat2(Value, 2, 1, 1, Tr";
_mlbl.setText(BA.ObjectToCharSequence(_s+__c.NumberFormat2(_value,(int) (2),(int) (1),(int) (1),__c.True)+_suffixtext));
 };
 //BA.debugLineNum = 222;BA.debugLine="End Sub";
return "";
}
public String  _drawticks() throws Exception{
int _r1 = 0;
int _longtick = 0;
int _shorttick = 0;
int _numberofticks = 0;
int _i = 0;
int _thickness = 0;
int _r = 0;
float _angle = 0f;
float _tr = 0f;
 //BA.debugLineNum = 156;BA.debugLine="Private Sub DrawTicks";
 //BA.debugLineNum = 157;BA.debugLine="Dim r1 As Int = Radius - 2dip";
_r1 = (int) (_radius-__c.DipToCurrent((int) (2)));
 //BA.debugLineNum = 158;BA.debugLine="Dim LongTick As Int = r1 - 17dip";
_longtick = (int) (_r1-__c.DipToCurrent((int) (17)));
 //BA.debugLineNum = 159;BA.debugLine="Dim ShortTick As Int = r1 - 5dip";
_shorttick = (int) (_r1-__c.DipToCurrent((int) (5)));
 //BA.debugLineNum = 160;BA.debugLine="Dim NumberOfTicks As Int";
_numberofticks = 0;
 //BA.debugLineNum = 161;BA.debugLine="If GaugeType = HALF_CIRCLE Then NumberOfTicks = 4";
if (_gaugetype==_half_circle) { 
_numberofticks = (int) (40);}
else {
_numberofticks = (int) (20);};
 //BA.debugLineNum = 162;BA.debugLine="For i = 0 To NumberOfTicks";
{
final int step6 = 1;
final int limit6 = _numberofticks;
_i = (int) (0) ;
for (;_i <= limit6 ;_i = _i + step6 ) {
 //BA.debugLineNum = 163;BA.debugLine="Dim thickness, r As Int";
_thickness = 0;
_r = 0;
 //BA.debugLineNum = 164;BA.debugLine="Dim angle As Float = i * AngleRange / NumberOfTi";
_angle = (float) (_i*_anglerange/(double)_numberofticks+_angleoffset);
 //BA.debugLineNum = 165;BA.debugLine="If i Mod 5 = 0 Then";
if (_i%5==0) { 
 //BA.debugLineNum = 166;BA.debugLine="thickness = 3dip";
_thickness = __c.DipToCurrent((int) (3));
 //BA.debugLineNum = 167;BA.debugLine="r = LongTick";
_r = _longtick;
 //BA.debugLineNum = 168;BA.debugLine="Dim tr As Float = r - 12dip";
_tr = (float) (_r-__c.DipToCurrent((int) (12)));
 //BA.debugLineNum = 169;BA.debugLine="cvsGauge.DrawTextRotated(NumberFormat(MinValue";
_cvsgauge.DrawTextRotated(ba,__c.NumberFormat(_minvalue+_i/(double)_numberofticks*(_maxvalue-_minvalue),(int) (1),(int) (0)),(float) (_x+_tr*__c.CosD(_angle)),(float) (_y+_tr*__c.SinD(_angle)),_xui.CreateDefaultFont((float) (10)),_xui.Color_Black,BA.getEnumFromString(android.graphics.Paint.Align.class,"CENTER"),(float) (_angle+90));
 }else {
 //BA.debugLineNum = 172;BA.debugLine="thickness = 1dip";
_thickness = __c.DipToCurrent((int) (1));
 //BA.debugLineNum = 173;BA.debugLine="r = ShortTick";
_r = _shorttick;
 };
 //BA.debugLineNum = 175;BA.debugLine="cvsGauge.DrawLine(x + r * CosD(angle), y + r * S";
_cvsgauge.DrawLine((float) (_x+_r*__c.CosD(_angle)),(float) (_y+_r*__c.SinD(_angle)),(float) (_x+_r1*__c.CosD(_angle)),(float) (_y+_r1*__c.SinD(_angle)),_xui.Color_Black,(float) (_thickness));
 }
};
 //BA.debugLineNum = 177;BA.debugLine="End Sub";
return "";
}
public float  _getcurrentvalue() throws Exception{
 //BA.debugLineNum = 256;BA.debugLine="Public Sub getCurrentValue As Float";
 //BA.debugLineNum = 257;BA.debugLine="Return mCurrentValue";
if (true) return _mcurrentvalue;
 //BA.debugLineNum = 258;BA.debugLine="End Sub";
return 0f;
}
public String  _initialize(anywheresoftware.b4a.BA _ba,Object _callback,String _eventname) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 38;BA.debugLine="Public Sub Initialize (Callback As Object, EventNa";
 //BA.debugLineNum = 39;BA.debugLine="mEventName = EventName";
_meventname = _eventname;
 //BA.debugLineNum = 40;BA.debugLine="mCallBack = Callback";
_mcallback = _callback;
 //BA.debugLineNum = 41;BA.debugLine="mRanges.Initialize";
_mranges.Initialize();
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return "";
}
public String  _setcurrentvalue(float _v) throws Exception{
float _newvalue = 0f;
 //BA.debugLineNum = 246;BA.debugLine="Public Sub setCurrentValue (v As Float)";
 //BA.debugLineNum = 247;BA.debugLine="If Main.Simulador = True Then";
if (_main._simulador /*boolean*/ ==__c.True) { 
 //BA.debugLineNum = 248;BA.debugLine="Dim NewValue As Float = Min(MaxValue, Max(MinVal";
_newvalue = (float) (__c.Min(_maxvalue,__c.Max(_minvalue,_v)));
 //BA.debugLineNum = 249;BA.debugLine="AnimateValueTo(NewValue)";
_animatevalueto(_newvalue);
 }else {
 //BA.debugLineNum = 251;BA.debugLine="DrawIndicator(v)";
_drawindicator(_v);
 };
 //BA.debugLineNum = 254;BA.debugLine="End Sub";
return "";
}
public String  _setminandmax(float _newminvalue,float _newmaxvalue) throws Exception{
 //BA.debugLineNum = 102;BA.debugLine="Public Sub SetMinAndMax(NewMinValue As Float, NewM";
 //BA.debugLineNum = 103;BA.debugLine="MinValue = NewMinValue";
_minvalue = _newminvalue;
 //BA.debugLineNum = 104;BA.debugLine="MaxValue = NewMaxValue";
_maxvalue = _newmaxvalue;
 //BA.debugLineNum = 105;BA.debugLine="DrawBackground";
_drawbackground();
 //BA.debugLineNum = 106;BA.debugLine="End Sub";
return "";
}
public String  _setranges(anywheresoftware.b4a.objects.collections.List _ranges) throws Exception{
Corner.Light.Bluetooth.gauge._gaugerange _r = null;
 //BA.debugLineNum = 228;BA.debugLine="Public Sub SetRanges(Ranges As List)";
 //BA.debugLineNum = 229;BA.debugLine="mRanges = Ranges";
_mranges = _ranges;
 //BA.debugLineNum = 230;BA.debugLine="For Each r As GaugeRange In Ranges";
{
final anywheresoftware.b4a.BA.IterableList group2 = _ranges;
final int groupLen2 = group2.getSize()
;int index2 = 0;
;
for (; index2 < groupLen2;index2++){
_r = (Corner.Light.Bluetooth.gauge._gaugerange)(group2.Get(index2));
 //BA.debugLineNum = 231;BA.debugLine="r.Color = Bit.And(0x00ffffff, r.Color)";
_r.Color /*int*/  = __c.Bit.And(((int)0x00ffffff),_r.Color /*int*/ );
 //BA.debugLineNum = 232;BA.debugLine="r.Color = Bit.Or(0x88000000, r.Color)";
_r.Color /*int*/  = __c.Bit.Or(((int)0x88000000),_r.Color /*int*/ );
 }
};
 //BA.debugLineNum = 234;BA.debugLine="DrawBackground";
_drawbackground();
 //BA.debugLineNum = 235;BA.debugLine="End Sub";
return "";
}
public float  _valuefromtimeeaseinout(float _time,float _start,float _changeinvalue,int _duration) throws Exception{
 //BA.debugLineNum = 276;BA.debugLine="Private Sub ValueFromTimeEaseInOut(Time As Float,";
 //BA.debugLineNum = 277;BA.debugLine="Time = Time / (Duration / 2)";
_time = (float) (_time/(double)(_duration/(double)2));
 //BA.debugLineNum = 278;BA.debugLine="If Time < 1 Then";
if (_time<1) { 
 //BA.debugLineNum = 279;BA.debugLine="Return ChangeInValue / 2 * Time * Time * Time *";
if (true) return (float) (_changeinvalue/(double)2*_time*_time*_time*_time+_start);
 }else {
 //BA.debugLineNum = 281;BA.debugLine="Time = Time - 2";
_time = (float) (_time-2);
 //BA.debugLineNum = 282;BA.debugLine="Return -ChangeInValue / 2 * (Time * Time * Time";
if (true) return (float) (-_changeinvalue/(double)2*(_time*_time*_time*_time-2)+_start);
 };
 //BA.debugLineNum = 284;BA.debugLine="End Sub";
return 0f;
}
public float  _valuetoangle(float _value) throws Exception{
 //BA.debugLineNum = 224;BA.debugLine="Private Sub ValueToAngle (Value As Float) As Float";
 //BA.debugLineNum = 225;BA.debugLine="Return (Value - MinValue) / (MaxValue - MinValue)";
if (true) return (float) ((_value-_minvalue)/(double)(_maxvalue-_minvalue)*_anglerange+_angleoffset);
 //BA.debugLineNum = 226;BA.debugLine="End Sub";
return 0f;
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
