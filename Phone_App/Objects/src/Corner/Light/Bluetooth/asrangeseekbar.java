package Corner.Light.Bluetooth;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class asrangeseekbar extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "Corner.Light.Bluetooth.asrangeseekbar");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", Corner.Light.Bluetooth.asrangeseekbar.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public String _meventname = "";
public Object _mcallback = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _mbase = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public int _reachedlinecolor = 0;
public int _unreachedlinecolor = 0;
public int _thumbcolor = 0;
public anywheresoftware.b4a.objects.B4XCanvas _cvs = null;
public Object _tag = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _touchpanel = null;
public int _mvalue1 = 0;
public int _mvalue2 = 0;
public int _minvalue = 0;
public int _maxvalue = 0;
public int _interval = 0;
public boolean _vertical = false;
public int _reachedlinesize = 0;
public int _unreachedlinesize = 0;
public int _radius1 = 0;
public int _radius2 = 0;
public boolean _pressed = false;
public boolean _pressedleftthumb = false;
public boolean _pressedtopthumb = false;
public int _size = 0;
public int _s1 = 0;
public int _s2 = 0;
public b4a.example.dateutils _dateutils = null;
public Corner.Light.Bluetooth.main _main = null;
public Corner.Light.Bluetooth.chatactivity _chatactivity = null;
public Corner.Light.Bluetooth.configactivity _configactivity = null;
public Corner.Light.Bluetooth.diagnosticoactivity _diagnosticoactivity = null;
public Corner.Light.Bluetooth.starter _starter = null;
public Corner.Light.Bluetooth.xuiviewsutils _xuiviewsutils = null;
public String  _base_resize(double _width,double _height) throws Exception{
 //BA.debugLineNum = 66;BA.debugLine="Public Sub Base_Resize (Width As Double, Height As";
 //BA.debugLineNum = 67;BA.debugLine="cvs.Resize(Width, Height)";
_cvs.Resize((float) (_width),(float) (_height));
 //BA.debugLineNum = 68;BA.debugLine="TouchPanel.SetLayoutAnimated(0, 0, 0, Width, Heig";
_touchpanel.SetLayoutAnimated((int) (0),(int) (0),(int) (0),(int) (_width),(int) (_height));
 //BA.debugLineNum = 69;BA.debugLine="Vertical = mBase.Height > mBase.Width";
_vertical = _mbase.getHeight()>_mbase.getWidth();
 //BA.debugLineNum = 70;BA.debugLine="size = Max(mBase.Height, mBase.Width) - 2 * Radiu";
_size = (int) (__c.Max(_mbase.getHeight(),_mbase.getWidth())-2*_radius2);
 //BA.debugLineNum = 71;BA.debugLine="Update";
_update();
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 22;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 23;BA.debugLine="Private mEventName As String 'ignore";
_meventname = "";
 //BA.debugLineNum = 24;BA.debugLine="Private mCallBack As Object 'ignore";
_mcallback = new Object();
 //BA.debugLineNum = 25;BA.debugLine="Public mBase As B4XView 'ignore";
_mbase = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private xui As XUI 'ignore";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 27;BA.debugLine="Public ReachedLineColor , UnreachedLineColor, Thu";
_reachedlinecolor = 0;
_unreachedlinecolor = 0;
_thumbcolor = 0;
 //BA.debugLineNum = 28;BA.debugLine="Private cvs As B4XCanvas";
_cvs = new anywheresoftware.b4a.objects.B4XCanvas();
 //BA.debugLineNum = 29;BA.debugLine="Public Tag As Object";
_tag = new Object();
 //BA.debugLineNum = 30;BA.debugLine="Private TouchPanel As B4XView";
_touchpanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private mValue1,mValue2 As Int";
_mvalue1 = 0;
_mvalue2 = 0;
 //BA.debugLineNum = 32;BA.debugLine="Public MinValue, MaxValue As Int";
_minvalue = 0;
_maxvalue = 0;
 //BA.debugLineNum = 33;BA.debugLine="Public Interval As Int = 1";
_interval = (int) (1);
 //BA.debugLineNum = 34;BA.debugLine="Private Vertical As Boolean";
_vertical = false;
 //BA.debugLineNum = 35;BA.debugLine="Public ReachedLineSize = 4dip, UnreachedLineSize";
_reachedlinesize = __c.DipToCurrent((int) (4));
_unreachedlinesize = __c.DipToCurrent((int) (2));
_radius1 = __c.DipToCurrent((int) (6));
_radius2 = __c.DipToCurrent((int) (20));
 //BA.debugLineNum = 36;BA.debugLine="Private Pressed,PressedLeftThumb,PressedTopThumb";
_pressed = false;
_pressedleftthumb = false;
_pressedtopthumb = false;
 //BA.debugLineNum = 37;BA.debugLine="Private size As Int";
_size = 0;
 //BA.debugLineNum = 38;BA.debugLine="Private s1,s2 As Int";
_s1 = 0;
_s2 = 0;
 //BA.debugLineNum = 39;BA.debugLine="End Sub";
return "";
}
public String  _designercreateview(Object _base,anywheresoftware.b4a.objects.LabelWrapper _lbl,anywheresoftware.b4a.objects.collections.Map _props) throws Exception{
 //BA.debugLineNum = 47;BA.debugLine="Public Sub DesignerCreateView (Base As Object, Lbl";
 //BA.debugLineNum = 48;BA.debugLine="mBase = Base";
_mbase = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_base));
 //BA.debugLineNum = 49;BA.debugLine="Tag = mBase.Tag : mBase.Tag = Me";
_tag = _mbase.getTag();
 //BA.debugLineNum = 49;BA.debugLine="Tag = mBase.Tag : mBase.Tag = Me";
_mbase.setTag(this);
 //BA.debugLineNum = 50;BA.debugLine="ReachedLineColor = xui.PaintOrColorToColor(Props.";
_reachedlinecolor = _xui.PaintOrColorToColor(_props.Get((Object)("ReachedLineColor")));
 //BA.debugLineNum = 51;BA.debugLine="UnreachedLineColor = xui.PaintOrColorToColor(Prop";
_unreachedlinecolor = _xui.PaintOrColorToColor(_props.Get((Object)("UnreachedLineColor")));
 //BA.debugLineNum = 52;BA.debugLine="ThumbColor = xui.PaintOrColorToColor(Props.Get(\"T";
_thumbcolor = _xui.PaintOrColorToColor(_props.Get((Object)("ThumbColor")));
 //BA.debugLineNum = 53;BA.debugLine="Interval = Max(1, Props.GetDefault(\"Interval\", 1)";
_interval = (int) (__c.Max(1,(double)(BA.ObjectToNumber(_props.GetDefault((Object)("Interval"),(Object)(1))))));
 //BA.debugLineNum = 54;BA.debugLine="MinValue = Props.Get(\"Min\")";
_minvalue = (int)(BA.ObjectToNumber(_props.Get((Object)("Min"))));
 //BA.debugLineNum = 55;BA.debugLine="MaxValue = Props.Get(\"Max\")";
_maxvalue = (int)(BA.ObjectToNumber(_props.Get((Object)("Max"))));
 //BA.debugLineNum = 56;BA.debugLine="mValue1 = Max(MinValue, Min(MaxValue, Props.Get(\"";
_mvalue1 = (int) (__c.Max(_minvalue,__c.Min(_maxvalue,(double)(BA.ObjectToNumber(_props.Get((Object)("Value1")))))));
 //BA.debugLineNum = 57;BA.debugLine="mValue2 = Max(MinValue, Min(MaxValue, Props.Get(\"";
_mvalue2 = (int) (__c.Max(_minvalue,__c.Min(_maxvalue,(double)(BA.ObjectToNumber(_props.Get((Object)("Value2")))))));
 //BA.debugLineNum = 58;BA.debugLine="cvs.Initialize(mBase)";
_cvs.Initialize(_mbase);
 //BA.debugLineNum = 59;BA.debugLine="TouchPanel = xui.CreatePanel(\"TouchPanel\")";
_touchpanel = _xui.CreatePanel(ba,"TouchPanel");
 //BA.debugLineNum = 60;BA.debugLine="mBase.AddView(TouchPanel, 0, 0, mBase.Width, mBas";
_mbase.AddView((android.view.View)(_touchpanel.getObject()),(int) (0),(int) (0),_mbase.getWidth(),_mbase.getHeight());
 //BA.debugLineNum = 62;BA.debugLine="Base_Resize(mBase.Width, mBase.Height)";
_base_resize(_mbase.getWidth(),_mbase.getHeight());
 //BA.debugLineNum = 64;BA.debugLine="End Sub";
return "";
}
public int  _getvalue1() throws Exception{
 //BA.debugLineNum = 189;BA.debugLine="Public Sub getValue1 As Int";
 //BA.debugLineNum = 190;BA.debugLine="Return mValue1";
if (true) return _mvalue1;
 //BA.debugLineNum = 191;BA.debugLine="End Sub";
return 0;
}
public int  _getvalue2() throws Exception{
 //BA.debugLineNum = 193;BA.debugLine="Public Sub getValue2 As Int";
 //BA.debugLineNum = 194;BA.debugLine="Return mValue2";
if (true) return _mvalue2;
 //BA.debugLineNum = 195;BA.debugLine="End Sub";
return 0;
}
public String  _initialize(anywheresoftware.b4a.BA _ba,Object _callback,String _eventname) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 41;BA.debugLine="Public Sub Initialize (Callback As Object, EventNa";
 //BA.debugLineNum = 42;BA.debugLine="mEventName = EventName";
_meventname = _eventname;
 //BA.debugLineNum = 43;BA.debugLine="mCallBack = Callback";
_mcallback = _callback;
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
return "";
}
public String  _raisetouchstateevent() throws Exception{
 //BA.debugLineNum = 152;BA.debugLine="Private Sub RaiseTouchStateEvent";
 //BA.debugLineNum = 153;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_TouchS";
if (_xui.SubExists(ba,_mcallback,_meventname+"_TouchStateChanged",(int) (1))) { 
 //BA.debugLineNum = 154;BA.debugLine="CallSubDelayed2(mCallBack, mEventName & \"_TouchS";
__c.CallSubDelayed2(ba,_mcallback,_meventname+"_TouchStateChanged",(Object)(_pressed));
 };
 //BA.debugLineNum = 156;BA.debugLine="End Sub";
return "";
}
public String  _setvalue1(int _v) throws Exception{
 //BA.debugLineNum = 179;BA.debugLine="Public Sub setValue1(v As Int)";
 //BA.debugLineNum = 180;BA.debugLine="mValue1 = Max(MinValue, Min(MaxValue, v))";
_mvalue1 = (int) (__c.Max(_minvalue,__c.Min(_maxvalue,_v)));
 //BA.debugLineNum = 181;BA.debugLine="Update";
_update();
 //BA.debugLineNum = 182;BA.debugLine="End Sub";
return "";
}
public String  _setvalue2(int _v) throws Exception{
 //BA.debugLineNum = 184;BA.debugLine="Public Sub setValue2(v As Int)";
 //BA.debugLineNum = 185;BA.debugLine="mValue2 = Max(MinValue, Min(MaxValue, v))";
_mvalue2 = (int) (__c.Max(_minvalue,__c.Min(_maxvalue,_v)));
 //BA.debugLineNum = 186;BA.debugLine="Update";
_update();
 //BA.debugLineNum = 187;BA.debugLine="End Sub";
return "";
}
public String  _setvaluebasedontouch(int _x,int _y) throws Exception{
int _v = 0;
int _newvalue = 0;
 //BA.debugLineNum = 158;BA.debugLine="Private Sub SetValueBasedOnTouch(x As Int, y As In";
 //BA.debugLineNum = 159;BA.debugLine="Dim v As Int";
_v = 0;
 //BA.debugLineNum = 160;BA.debugLine="If Vertical Then";
if (_vertical) { 
 //BA.debugLineNum = 161;BA.debugLine="v = (mBase.Height - Radius2 - y) / size * (MaxVa";
_v = (int) ((_mbase.getHeight()-_radius2-_y)/(double)_size*(_maxvalue-_minvalue)+_minvalue);
 }else {
 //BA.debugLineNum = 163;BA.debugLine="v = (x - Radius2) / size * (MaxValue - MinValue)";
_v = (int) ((_x-_radius2)/(double)_size*(_maxvalue-_minvalue)+_minvalue);
 };
 //BA.debugLineNum = 165;BA.debugLine="v = Round (v / Interval) * Interval";
_v = (int) (__c.Round(_v/(double)_interval)*_interval);
 //BA.debugLineNum = 166;BA.debugLine="Dim NewValue As Int = Max(MinValue, Min(MaxValue,";
_newvalue = (int) (__c.Max(_minvalue,__c.Min(_maxvalue,_v)));
 //BA.debugLineNum = 167;BA.debugLine="If NewValue <> mValue1 Or NewValue <> mValue2 The";
if (_newvalue!=_mvalue1 || _newvalue!=_mvalue2) { 
 //BA.debugLineNum = 168;BA.debugLine="If Vertical = False Then";
if (_vertical==__c.False) { 
 //BA.debugLineNum = 169;BA.debugLine="If PressedLeftThumb = True Then mValue1 = NewVa";
if (_pressedleftthumb==__c.True) { 
_mvalue1 = _newvalue;}
else {
_mvalue2 = _newvalue;};
 }else {
 //BA.debugLineNum = 171;BA.debugLine="If PressedTopThumb = True Then mValue1 = NewVal";
if (_pressedtopthumb==__c.True) { 
_mvalue1 = _newvalue;}
else {
_mvalue2 = _newvalue;};
 };
 //BA.debugLineNum = 173;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_Value";
if (_xui.SubExists(ba,_mcallback,_meventname+"_ValueChanged",(int) (2))) { 
 //BA.debugLineNum = 174;BA.debugLine="CallSubDelayed3(mCallBack, mEventName & \"_Value";
__c.CallSubDelayed3(ba,_mcallback,_meventname+"_ValueChanged",(Object)(_mvalue1),(Object)(_mvalue2));
 };
 };
 //BA.debugLineNum = 177;BA.debugLine="End Sub";
return "";
}
public String  _touchpanel_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 113;BA.debugLine="Private Sub TouchPanel_Touch (Action As Int, X As";
 //BA.debugLineNum = 114;BA.debugLine="If Action = TouchPanel.TOUCH_ACTION_DOWN Then";
if (_action==_touchpanel.TOUCH_ACTION_DOWN) { 
 //BA.debugLineNum = 115;BA.debugLine="If Vertical = False Then";
if (_vertical==__c.False) { 
 //BA.debugLineNum = 116;BA.debugLine="If x <= (s1 + Radius1) And x <= (s2 + Radius1)";
if (_x<=(_s1+_radius1) && _x<=(_s2+_radius1)) { 
 //BA.debugLineNum = 117;BA.debugLine="PressedLeftThumb = True";
_pressedleftthumb = __c.True;
 }else {
 //BA.debugLineNum = 119;BA.debugLine="PressedLeftThumb = False";
_pressedleftthumb = __c.False;
 };
 }else {
 //BA.debugLineNum = 122;BA.debugLine="If y >= (s1 - Radius1) And y >= (s2 - Radius1)";
if (_y>=(_s1-_radius1) && _y>=(_s2-_radius1)) { 
 //BA.debugLineNum = 123;BA.debugLine="PressedTopThumb = True";
_pressedtopthumb = __c.True;
 }else {
 //BA.debugLineNum = 125;BA.debugLine="PressedTopThumb = False";
_pressedtopthumb = __c.False;
 };
 };
 //BA.debugLineNum = 128;BA.debugLine="Pressed = True";
_pressed = __c.True;
 //BA.debugLineNum = 129;BA.debugLine="RaiseTouchStateEvent";
_raisetouchstateevent();
 //BA.debugLineNum = 130;BA.debugLine="SetValueBasedOnTouch(X, Y)";
_setvaluebasedontouch((int) (_x),(int) (_y));
 }else if(_action==_touchpanel.TOUCH_ACTION_MOVE) { 
 //BA.debugLineNum = 132;BA.debugLine="If Vertical = False Then";
if (_vertical==__c.False) { 
 //BA.debugLineNum = 133;BA.debugLine="If PressedLeftThumb = True Then";
if (_pressedleftthumb==__c.True) { 
 //BA.debugLineNum = 134;BA.debugLine="SetValueBasedOnTouch(Min(X,s2 + Radius1/2), Y)";
_setvaluebasedontouch((int) (__c.Min(_x,_s2+_radius1/(double)2)),(int) (_y));
 }else {
 //BA.debugLineNum = 136;BA.debugLine="SetValueBasedOnTouch(Max(X,s1 + Radius1/2), Y)";
_setvaluebasedontouch((int) (__c.Max(_x,_s1+_radius1/(double)2)),(int) (_y));
 };
 }else {
 //BA.debugLineNum = 139;BA.debugLine="If PressedTopThumb = True Then";
if (_pressedtopthumb==__c.True) { 
 //BA.debugLineNum = 140;BA.debugLine="SetValueBasedOnTouch(x, Max(y,s2 - Radius1/2))";
_setvaluebasedontouch((int) (_x),(int) (__c.Max(_y,_s2-_radius1/(double)2)));
 }else {
 //BA.debugLineNum = 142;BA.debugLine="SetValueBasedOnTouch(x, Min(y,s1 - Radius1/2))";
_setvaluebasedontouch((int) (_x),(int) (__c.Min(_y,_s1-_radius1/(double)2)));
 };
 };
 }else if(_action==_touchpanel.TOUCH_ACTION_UP) { 
 //BA.debugLineNum = 146;BA.debugLine="Pressed = False";
_pressed = __c.False;
 //BA.debugLineNum = 147;BA.debugLine="RaiseTouchStateEvent";
_raisetouchstateevent();
 };
 //BA.debugLineNum = 149;BA.debugLine="If Action <> TouchPanel.TOUCH_ACTION_MOVE_NOTOUCH";
if (_action!=_touchpanel.TOUCH_ACTION_MOVE_NOTOUCH) { 
_update();};
 //BA.debugLineNum = 150;BA.debugLine="End Sub";
return "";
}
public String  _update() throws Exception{
int _y = 0;
int _x = 0;
 //BA.debugLineNum = 75;BA.debugLine="Public Sub Update";
 //BA.debugLineNum = 77;BA.debugLine="cvs.ClearRect(cvs.TargetRect)";
_cvs.ClearRect(_cvs.getTargetRect());
 //BA.debugLineNum = 78;BA.debugLine="If size > 0 Then";
if (_size>0) { 
 //BA.debugLineNum = 79;BA.debugLine="If Vertical = False Then";
if (_vertical==__c.False) { 
 //BA.debugLineNum = 80;BA.debugLine="s1 = Radius2 + (mValue1 - MinValue) / (MaxValue";
_s1 = (int) (_radius2+(_mvalue1-_minvalue)/(double)(_maxvalue-_minvalue)*_size);
 //BA.debugLineNum = 81;BA.debugLine="s2 = Radius2 + (mValue2 - MinValue) / (MaxValue";
_s2 = (int) (_radius2+(_mvalue2-_minvalue)/(double)(_maxvalue-_minvalue)*_size);
 //BA.debugLineNum = 83;BA.debugLine="Dim y As Int = mBase.Height / 2";
_y = (int) (_mbase.getHeight()/(double)2);
 //BA.debugLineNum = 84;BA.debugLine="cvs.DrawLine(Radius2, y, mBase.Width - Radius2,";
_cvs.DrawLine((float) (_radius2),(float) (_y),(float) (_mbase.getWidth()-_radius2),(float) (_y),_unreachedlinecolor,(float) (_unreachedlinesize));
 //BA.debugLineNum = 85;BA.debugLine="cvs.DrawLine(s1, y, s2, y, ReachedLineColor , R";
_cvs.DrawLine((float) (_s1),(float) (_y),(float) (_s2),(float) (_y),_reachedlinecolor,(float) (_reachedlinesize));
 //BA.debugLineNum = 87;BA.debugLine="cvs.DrawCircle(s1, y, Radius1, ReachedLineColor";
_cvs.DrawCircle((float) (_s1),(float) (_y),(float) (_radius1),_reachedlinecolor,__c.True,(float) (0));
 //BA.debugLineNum = 88;BA.debugLine="cvs.DrawCircle(s2, y, Radius1, ReachedLineColor";
_cvs.DrawCircle((float) (_s2),(float) (_y),(float) (_radius1),_reachedlinecolor,__c.True,(float) (0));
 //BA.debugLineNum = 89;BA.debugLine="If Pressed = True And PressedLeftThumb = True T";
if (_pressed==__c.True && _pressedleftthumb==__c.True) { 
 //BA.debugLineNum = 90;BA.debugLine="cvs.DrawCircle(s1, y, Radius2, ThumbColor, Tru";
_cvs.DrawCircle((float) (_s1),(float) (_y),(float) (_radius2),_thumbcolor,__c.True,(float) (0));
 }else if(_pressed==__c.True && _pressedleftthumb==__c.False) { 
 //BA.debugLineNum = 92;BA.debugLine="cvs.DrawCircle(s2, y, Radius2, ThumbColor, Tru";
_cvs.DrawCircle((float) (_s2),(float) (_y),(float) (_radius2),_thumbcolor,__c.True,(float) (0));
 };
 }else {
 //BA.debugLineNum = 95;BA.debugLine="s1 = Radius2 + (MaxValue - mValue1 - MinValue)";
_s1 = (int) (_radius2+(_maxvalue-_mvalue1-_minvalue)/(double)(_maxvalue-_minvalue)*_size);
 //BA.debugLineNum = 96;BA.debugLine="s2 = Radius2 + (MaxValue - mValue2 - MinValue)";
_s2 = (int) (_radius2+(_maxvalue-_mvalue2-_minvalue)/(double)(_maxvalue-_minvalue)*_size);
 //BA.debugLineNum = 97;BA.debugLine="Dim x As Int = mBase.Width / 2";
_x = (int) (_mbase.getWidth()/(double)2);
 //BA.debugLineNum = 98;BA.debugLine="cvs.DrawLine(x, Radius2, x, mBase.height - Radi";
_cvs.DrawLine((float) (_x),(float) (_radius2),(float) (_x),(float) (_mbase.getHeight()-_radius2),_unreachedlinecolor,(float) (_unreachedlinesize));
 //BA.debugLineNum = 99;BA.debugLine="cvs.DrawLine(x, s1, x,s2, ReachedLineColor , Re";
_cvs.DrawLine((float) (_x),(float) (_s1),(float) (_x),(float) (_s2),_reachedlinecolor,(float) (_reachedlinesize));
 //BA.debugLineNum = 101;BA.debugLine="cvs.DrawCircle(x, s1, Radius1, ReachedLineColor";
_cvs.DrawCircle((float) (_x),(float) (_s1),(float) (_radius1),_reachedlinecolor,__c.True,(float) (0));
 //BA.debugLineNum = 102;BA.debugLine="cvs.DrawCircle(x, s2, Radius1, ReachedLineColor";
_cvs.DrawCircle((float) (_x),(float) (_s2),(float) (_radius1),_reachedlinecolor,__c.True,(float) (0));
 //BA.debugLineNum = 103;BA.debugLine="If Pressed And PressedTopThumb = True Then";
if (_pressed && _pressedtopthumb==__c.True) { 
 //BA.debugLineNum = 104;BA.debugLine="cvs.DrawCircle(x, s1, Radius2, ThumbColor, Tru";
_cvs.DrawCircle((float) (_x),(float) (_s1),(float) (_radius2),_thumbcolor,__c.True,(float) (0));
 }else if(_pressed==__c.True && _pressedtopthumb==__c.False) { 
 //BA.debugLineNum = 106;BA.debugLine="cvs.DrawCircle(x, s2, Radius2, ThumbColor, Tru";
_cvs.DrawCircle((float) (_x),(float) (_s2),(float) (_radius2),_thumbcolor,__c.True,(float) (0));
 };
 };
 };
 //BA.debugLineNum = 110;BA.debugLine="cvs.Invalidate";
_cvs.Invalidate();
 //BA.debugLineNum = 111;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
