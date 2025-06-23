package Corner.Light.Bluetooth;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class asbuttonslider extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "Corner.Light.Bluetooth.asbuttonslider");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", Corner.Light.Bluetooth.asbuttonslider.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public String _meventname = "";
public Object _mcallback = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _mbase = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _xpnl_leftside = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _xpnl_rightside = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _xpnl_slidebutton = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _tmp_xpnl_leftside = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _tmp_xpnl_rightside = null;
public int _donwx = 0;
public int _downy = 0;
public boolean _breachedlefttop = false;
public boolean _breachedrightbottom = false;
public String _xbuttonorientation = "";
public int _xlefttopcolor = 0;
public int _xrightbottomcolor = 0;
public int _xsliderbuttoncolor = 0;
public b4a.example.dateutils _dateutils = null;
public Corner.Light.Bluetooth.main _main = null;
public Corner.Light.Bluetooth.chatactivity _chatactivity = null;
public Corner.Light.Bluetooth.configactivity _configactivity = null;
public Corner.Light.Bluetooth.diagnosticoactivity _diagnosticoactivity = null;
public Corner.Light.Bluetooth.starter _starter = null;
public Corner.Light.Bluetooth.xuiviewsutils _xuiviewsutils = null;
public String  _base_resize(double _width,double _height) throws Exception{
 //BA.debugLineNum = 167;BA.debugLine="Private Sub Base_Resize (Width As Double, Height A";
 //BA.debugLineNum = 169;BA.debugLine="If xpnl_leftside.IsInitialized = False Then";
if (_xpnl_leftside.IsInitialized()==__c.False) { 
 //BA.debugLineNum = 171;BA.debugLine="ini_views";
_ini_views();
 };
 //BA.debugLineNum = 175;BA.debugLine="If xButtonOrientation = \"Horizontal\" Then xpnl_le";
if ((_xbuttonorientation).equals("Horizontal")) { 
_xpnl_leftside.SetLayoutAnimated((int) (0),(int) (0),(int) (0),(int) (_width/(double)2),(int) (_height));}
else {
_xpnl_leftside.SetLayoutAnimated((int) (0),(int) (0),(int) (0),(int) (_width),(int) (_height/(double)2));};
 //BA.debugLineNum = 176;BA.debugLine="If xButtonOrientation = \"Horizontal\" Then xpnl_ri";
if ((_xbuttonorientation).equals("Horizontal")) { 
_xpnl_rightside.SetLayoutAnimated((int) (0),(int) (_width/(double)2),(int) (0),(int) (_width/(double)2),(int) (_height));}
else {
_xpnl_rightside.SetLayoutAnimated((int) (0),(int) (0),(int) (_height/(double)2),(int) (_width),(int) (_height/(double)2));};
 //BA.debugLineNum = 177;BA.debugLine="If xButtonOrientation = \"Horizontal\" Then xpnl_";
if ((_xbuttonorientation).equals("Horizontal")) { 
_xpnl_slidebutton.SetLayoutAnimated((int) (0),(int) (_width/(double)2-_height/(double)2),(int) (0),(int) (_height),(int) (_height));}
else {
_xpnl_slidebutton.SetLayoutAnimated((int) (0),(int) (0),(int) (_height/(double)2-_width/(double)2),(int) (_width),(int) (_width));};
 //BA.debugLineNum = 179;BA.debugLine="If xButtonOrientation = \"Horizontal\" Then tmp_xpn";
if ((_xbuttonorientation).equals("Horizontal")) { 
_tmp_xpnl_leftside.SetLayoutAnimated((int) (0),(int) (_width/(double)2-(_width/(double)3)/(double)2),(int) (0),(int) (_width/(double)3),(int) (_height));}
else {
_tmp_xpnl_leftside.SetLayoutAnimated((int) (0),(int) (0),(int) (_height/(double)2-(_height/(double)3)/(double)2),(int) (_width),(int) (_height/(double)3));};
 //BA.debugLineNum = 180;BA.debugLine="If xButtonOrientation = \"Horizontal\" Then tmp_xpn";
if ((_xbuttonorientation).equals("Horizontal")) { 
_tmp_xpnl_rightside.SetLayoutAnimated((int) (0),(int) (_width/(double)2),(int) (0),(int) (_width/(double)3),(int) (_height));}
else {
_tmp_xpnl_rightside.SetLayoutAnimated((int) (0),(int) (0),(int) (_height/(double)2),(int) (_width),(int) (_height/(double)3));};
 //BA.debugLineNum = 182;BA.debugLine="xpnl_leftside.SetColorAndBorder(xLeftTopColor,0,x";
_xpnl_leftside.SetColorAndBorder(_xlefttopcolor,(int) (0),_xui.Color_Transparent,(int) (_mbase.getHeight()/(double)2));
 //BA.debugLineNum = 183;BA.debugLine="xpnl_rightside.SetColorAndBorder(xRightBottomColo";
_xpnl_rightside.SetColorAndBorder(_xrightbottomcolor,(int) (0),_xui.Color_Transparent,(int) (_mbase.getHeight()/(double)2));
 //BA.debugLineNum = 184;BA.debugLine="xpnl_slidebutton.SetColorAndBorder(xSliderButtonC";
_xpnl_slidebutton.SetColorAndBorder(_xsliderbuttoncolor,(int) (0),_xui.Color_Transparent,(int) (_mbase.getHeight()/(double)2));
 //BA.debugLineNum = 185;BA.debugLine="tmp_xpnl_leftside.Color = xLeftTopColor";
_tmp_xpnl_leftside.setColor(_xlefttopcolor);
 //BA.debugLineNum = 186;BA.debugLine="tmp_xpnl_rightside.Color = xRightBottomColor";
_tmp_xpnl_rightside.setColor(_xrightbottomcolor);
 //BA.debugLineNum = 188;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 24;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 25;BA.debugLine="Private mEventName As String 'ignore";
_meventname = "";
 //BA.debugLineNum = 26;BA.debugLine="Private mCallBack As Object 'ignore";
_mcallback = new Object();
 //BA.debugLineNum = 27;BA.debugLine="Private mBase As B4XView 'ignore";
_mbase = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private xui As XUI 'ignore";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 30;BA.debugLine="Private xpnl_leftside,xpnl_rightside,xpnl_slidebu";
_xpnl_leftside = new anywheresoftware.b4a.objects.B4XViewWrapper();
_xpnl_rightside = new anywheresoftware.b4a.objects.B4XViewWrapper();
_xpnl_slidebutton = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private tmp_xpnl_leftside,tmp_xpnl_rightside As B";
_tmp_xpnl_leftside = new anywheresoftware.b4a.objects.B4XViewWrapper();
_tmp_xpnl_rightside = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private donwx,downy As Int";
_donwx = 0;
_downy = 0;
 //BA.debugLineNum = 35;BA.debugLine="Private bReachedLeftTop,bReachedRightBottom As Bo";
_breachedlefttop = false;
_breachedrightbottom = __c.False;
 //BA.debugLineNum = 38;BA.debugLine="Private xButtonOrientation As String";
_xbuttonorientation = "";
 //BA.debugLineNum = 39;BA.debugLine="Private xLeftTopColor,xRightBottomColor,xSliderBu";
_xlefttopcolor = 0;
_xrightbottomcolor = 0;
_xsliderbuttoncolor = 0;
 //BA.debugLineNum = 41;BA.debugLine="End Sub";
return "";
}
public String  _designercreateview(Object _base,anywheresoftware.b4a.objects.LabelWrapper _lbl,anywheresoftware.b4a.objects.collections.Map _props) throws Exception{
 //BA.debugLineNum = 49;BA.debugLine="Public Sub DesignerCreateView (Base As Object, Lbl";
 //BA.debugLineNum = 50;BA.debugLine="mBase = Base";
_mbase = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_base));
 //BA.debugLineNum = 52;BA.debugLine="ini_props(Props)";
_ini_props(_props);
 //BA.debugLineNum = 56;BA.debugLine="Base_Resize(mBase.Width,mBase.Height)";
_base_resize(_mbase.getWidth(),_mbase.getHeight());
 //BA.debugLineNum = 60;BA.debugLine="End Sub";
return "";
}
public String  _dropslider(boolean _lefttop) throws Exception{
 //BA.debugLineNum = 335;BA.debugLine="Private Sub DropSlider(LeftTop As Boolean)";
 //BA.debugLineNum = 337;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_DropSl";
if (_xui.SubExists(ba,_mcallback,_meventname+"_DropSlider",(int) (0))) { 
 //BA.debugLineNum = 338;BA.debugLine="CallSub2(mCallBack, mEventName & \"_DropSlider\",L";
__c.CallSubNew2(ba,_mcallback,_meventname+"_DropSlider",(Object)(_lefttop));
 };
 //BA.debugLineNum = 341;BA.debugLine="End Sub";
return "";
}
public String  _getbuttonorientation() throws Exception{
 //BA.debugLineNum = 64;BA.debugLine="Public Sub getButtonOrientation As String";
 //BA.debugLineNum = 66;BA.debugLine="Return xButtonOrientation";
if (true) return _xbuttonorientation;
 //BA.debugLineNum = 68;BA.debugLine="End Sub";
return "";
}
public String  _getbuttonorientation_horizontal() throws Exception{
 //BA.debugLineNum = 84;BA.debugLine="Public Sub getBUTTONORIENTATION_HORIZONTAL As Stri";
 //BA.debugLineNum = 86;BA.debugLine="Return \"Horizontal\"";
if (true) return "Horizontal";
 //BA.debugLineNum = 88;BA.debugLine="End Sub";
return "";
}
public String  _getbuttonorientation_vertical() throws Exception{
 //BA.debugLineNum = 90;BA.debugLine="Public Sub getBUTTONORIENTATION_VERTICAL As String";
 //BA.debugLineNum = 92;BA.debugLine="Return \"Vertical\"";
if (true) return "Vertical";
 //BA.debugLineNum = 94;BA.debugLine="End Sub";
return "";
}
public int  _getlefttopcolor() throws Exception{
 //BA.debugLineNum = 96;BA.debugLine="Public Sub getLeftTopColor As Int";
 //BA.debugLineNum = 98;BA.debugLine="Return xLeftTopColor";
if (true) return _xlefttopcolor;
 //BA.debugLineNum = 100;BA.debugLine="End Sub";
return 0;
}
public anywheresoftware.b4a.objects.B4XViewWrapper  _getlefttoppnl() throws Exception{
 //BA.debugLineNum = 136;BA.debugLine="Public Sub getLeftTopPnl As B4XView";
 //BA.debugLineNum = 138;BA.debugLine="Return xpnl_leftside";
if (true) return _xpnl_leftside;
 //BA.debugLineNum = 140;BA.debugLine="End Sub";
return null;
}
public int  _getrightbottomcolor() throws Exception{
 //BA.debugLineNum = 109;BA.debugLine="Public Sub getRightBottomColor As Int";
 //BA.debugLineNum = 111;BA.debugLine="Return xRightBottomColor";
if (true) return _xrightbottomcolor;
 //BA.debugLineNum = 113;BA.debugLine="End Sub";
return 0;
}
public anywheresoftware.b4a.objects.B4XViewWrapper  _getrightbottompnl() throws Exception{
 //BA.debugLineNum = 143;BA.debugLine="Public Sub getRightBottomPnl As B4XView";
 //BA.debugLineNum = 145;BA.debugLine="Return xpnl_rightside";
if (true) return _xpnl_rightside;
 //BA.debugLineNum = 147;BA.debugLine="End Sub";
return null;
}
public int  _getsliderbuttoncolor() throws Exception{
 //BA.debugLineNum = 122;BA.debugLine="Public Sub getSliderButtonColor As Int";
 //BA.debugLineNum = 124;BA.debugLine="Return xSliderButtonColor";
if (true) return _xsliderbuttoncolor;
 //BA.debugLineNum = 126;BA.debugLine="End Sub";
return 0;
}
public anywheresoftware.b4a.objects.B4XViewWrapper  _getsliderbuttonpnl() throws Exception{
 //BA.debugLineNum = 150;BA.debugLine="Public Sub getSliderButtonPnl As B4XView";
 //BA.debugLineNum = 152;BA.debugLine="Return xpnl_slidebutton";
if (true) return _xpnl_slidebutton;
 //BA.debugLineNum = 154;BA.debugLine="End Sub";
return null;
}
public String  _ini_props(anywheresoftware.b4a.objects.collections.Map _props) throws Exception{
 //BA.debugLineNum = 158;BA.debugLine="Private Sub ini_props(Props As Map)";
 //BA.debugLineNum = 160;BA.debugLine="xButtonOrientation = Props.Get(\"ButtonOrientation";
_xbuttonorientation = BA.ObjectToString(_props.Get((Object)("ButtonOrientation")));
 //BA.debugLineNum = 161;BA.debugLine="xLeftTopColor = xui.PaintOrColorToColor(Props.Get";
_xlefttopcolor = _xui.PaintOrColorToColor(_props.Get((Object)("LeftTopColor")));
 //BA.debugLineNum = 162;BA.debugLine="xRightBottomColor = xui.PaintOrColorToColor( Prop";
_xrightbottomcolor = _xui.PaintOrColorToColor(_props.Get((Object)("RightBottomColor")));
 //BA.debugLineNum = 163;BA.debugLine="xSliderButtonColor = xui.PaintOrColorToColor( Pro";
_xsliderbuttoncolor = _xui.PaintOrColorToColor(_props.Get((Object)("SliderButtonColor")));
 //BA.debugLineNum = 165;BA.debugLine="End Sub";
return "";
}
public String  _ini_views() throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 190;BA.debugLine="Private Sub ini_views";
 //BA.debugLineNum = 192;BA.debugLine="xpnl_leftside = xui.CreatePanel(\"xpnl_leftside\")";
_xpnl_leftside = _xui.CreatePanel(ba,"xpnl_leftside");
 //BA.debugLineNum = 193;BA.debugLine="xpnl_rightside = xui.CreatePanel(\"xpnl_rightside\"";
_xpnl_rightside = _xui.CreatePanel(ba,"xpnl_rightside");
 //BA.debugLineNum = 194;BA.debugLine="xpnl_slidebutton = xui.CreatePanel(\"xpnl_slidebut";
_xpnl_slidebutton = _xui.CreatePanel(ba,"xpnl_slidebutton");
 //BA.debugLineNum = 196;BA.debugLine="tmp_xpnl_leftside = xui.CreatePanel(\"\")";
_tmp_xpnl_leftside = _xui.CreatePanel(ba,"");
 //BA.debugLineNum = 197;BA.debugLine="tmp_xpnl_rightside = xui.CreatePanel(\"\")";
_tmp_xpnl_rightside = _xui.CreatePanel(ba,"");
 //BA.debugLineNum = 201;BA.debugLine="Private r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 202;BA.debugLine="r.Target = xpnl_slidebutton";
_r.Target = (Object)(_xpnl_slidebutton.getObject());
 //BA.debugLineNum = 203;BA.debugLine="r.SetOnTouchListener(\"xpnl_slidebutton_Touch2\")";
_r.SetOnTouchListener(ba,"xpnl_slidebutton_Touch2");
 //BA.debugLineNum = 206;BA.debugLine="mBase.AddView(tmp_xpnl_leftside,0,0,0,0)";
_mbase.AddView((android.view.View)(_tmp_xpnl_leftside.getObject()),(int) (0),(int) (0),(int) (0),(int) (0));
 //BA.debugLineNum = 207;BA.debugLine="mBase.AddView(tmp_xpnl_rightside,0,0,0,0)";
_mbase.AddView((android.view.View)(_tmp_xpnl_rightside.getObject()),(int) (0),(int) (0),(int) (0),(int) (0));
 //BA.debugLineNum = 209;BA.debugLine="mBase.AddView(xpnl_leftside,0,0,0,0)";
_mbase.AddView((android.view.View)(_xpnl_leftside.getObject()),(int) (0),(int) (0),(int) (0),(int) (0));
 //BA.debugLineNum = 210;BA.debugLine="mBase.AddView(xpnl_rightside,0,0,0,0)";
_mbase.AddView((android.view.View)(_xpnl_rightside.getObject()),(int) (0),(int) (0),(int) (0),(int) (0));
 //BA.debugLineNum = 211;BA.debugLine="mBase.AddView(xpnl_slidebutton,0,0,0,0)";
_mbase.AddView((android.view.View)(_xpnl_slidebutton.getObject()),(int) (0),(int) (0),(int) (0),(int) (0));
 //BA.debugLineNum = 213;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba,Object _callback,String _eventname) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 43;BA.debugLine="Public Sub Initialize (Callback As Object, EventNa";
 //BA.debugLineNum = 44;BA.debugLine="mEventName = EventName";
_meventname = _eventname;
 //BA.debugLineNum = 45;BA.debugLine="mCallBack = Callback";
_mcallback = _callback;
 //BA.debugLineNum = 46;BA.debugLine="End Sub";
return "";
}
public String  _lefttopclick() throws Exception{
 //BA.debugLineNum = 359;BA.debugLine="Private Sub LeftTopClick";
 //BA.debugLineNum = 361;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_LeftTo";
if (_xui.SubExists(ba,_mcallback,_meventname+"_LeftTopClick",(int) (0))) { 
 //BA.debugLineNum = 362;BA.debugLine="CallSub(mCallBack, mEventName & \"_LeftTopClick\")";
__c.CallSubNew(ba,_mcallback,_meventname+"_LeftTopClick");
 };
 //BA.debugLineNum = 365;BA.debugLine="End Sub";
return "";
}
public String  _reachedlefttop() throws Exception{
 //BA.debugLineNum = 343;BA.debugLine="Private Sub ReachedLeftTop";
 //BA.debugLineNum = 345;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_Reache";
if (_xui.SubExists(ba,_mcallback,_meventname+"_ReachedLeftTop",(int) (0))) { 
 //BA.debugLineNum = 346;BA.debugLine="CallSub(mCallBack, mEventName & \"_ReachedLeftTop";
__c.CallSubNew(ba,_mcallback,_meventname+"_ReachedLeftTop");
 };
 //BA.debugLineNum = 349;BA.debugLine="End Sub";
return "";
}
public String  _reachedrightbottom() throws Exception{
 //BA.debugLineNum = 351;BA.debugLine="Private Sub ReachedRightBottom";
 //BA.debugLineNum = 353;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_Reache";
if (_xui.SubExists(ba,_mcallback,_meventname+"_ReachedRightBottom",(int) (0))) { 
 //BA.debugLineNum = 354;BA.debugLine="CallSub(mCallBack, mEventName & \"_ReachedRightBo";
__c.CallSubNew(ba,_mcallback,_meventname+"_ReachedRightBottom");
 };
 //BA.debugLineNum = 357;BA.debugLine="End Sub";
return "";
}
public String  _rightbottomclick() throws Exception{
 //BA.debugLineNum = 367;BA.debugLine="Private Sub RightBottomClick";
 //BA.debugLineNum = 369;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_RightB";
if (_xui.SubExists(ba,_mcallback,_meventname+"_RightBottomClick",(int) (0))) { 
 //BA.debugLineNum = 370;BA.debugLine="CallSub(mCallBack, mEventName & \"_RightBottomCli";
__c.CallSubNew(ba,_mcallback,_meventname+"_RightBottomClick");
 };
 //BA.debugLineNum = 373;BA.debugLine="End Sub";
return "";
}
public String  _setbuttonorientation(String _orientation) throws Exception{
 //BA.debugLineNum = 70;BA.debugLine="Public Sub setButtonOrientation(Orientation As Str";
 //BA.debugLineNum = 72;BA.debugLine="If Orientation = \"Horizontal\" Or Orientation = \"V";
if ((_orientation).equals("Horizontal") || (_orientation).equals("Vertical")) { 
 //BA.debugLineNum = 74;BA.debugLine="xButtonOrientation = Orientation";
_xbuttonorientation = _orientation;
 }else {
 //BA.debugLineNum = 78;BA.debugLine="xButtonOrientation = \"Horizontal\"";
_xbuttonorientation = "Horizontal";
 };
 //BA.debugLineNum = 82;BA.debugLine="End Sub";
return "";
}
public String  _setlefttopcolor(int _color) throws Exception{
 //BA.debugLineNum = 102;BA.debugLine="Public Sub setLeftTopColor(Color As Int)";
 //BA.debugLineNum = 104;BA.debugLine="xLeftTopColor = Color";
_xlefttopcolor = _color;
 //BA.debugLineNum = 105;BA.debugLine="Base_Resize(mBase.Width,mBase.Height)";
_base_resize(_mbase.getWidth(),_mbase.getHeight());
 //BA.debugLineNum = 107;BA.debugLine="End Sub";
return "";
}
public String  _setrightbottomcolor(int _color) throws Exception{
 //BA.debugLineNum = 115;BA.debugLine="Public Sub setRightBottomColor(Color As Int)";
 //BA.debugLineNum = 117;BA.debugLine="xRightBottomColor = Color";
_xrightbottomcolor = _color;
 //BA.debugLineNum = 118;BA.debugLine="Base_Resize(mBase.Width,mBase.Height)";
_base_resize(_mbase.getWidth(),_mbase.getHeight());
 //BA.debugLineNum = 120;BA.debugLine="End Sub";
return "";
}
public String  _setsliderbuttoncolor(int _color) throws Exception{
 //BA.debugLineNum = 128;BA.debugLine="Public Sub setSliderButtonColor(Color As Int)";
 //BA.debugLineNum = 130;BA.debugLine="xSliderButtonColor = Color";
_xsliderbuttoncolor = _color;
 //BA.debugLineNum = 131;BA.debugLine="Base_Resize(mBase.Width,mBase.Height)";
_base_resize(_mbase.getWidth(),_mbase.getHeight());
 //BA.debugLineNum = 133;BA.debugLine="End Sub";
return "";
}
public String  _xpnl_leftside_click() throws Exception{
 //BA.debugLineNum = 231;BA.debugLine="Private Sub xpnl_leftside_Click";
 //BA.debugLineNum = 232;BA.debugLine="LeftTopClick";
_lefttopclick();
 //BA.debugLineNum = 233;BA.debugLine="End Sub";
return "";
}
public String  _xpnl_rightside_click() throws Exception{
 //BA.debugLineNum = 235;BA.debugLine="Private Sub xpnl_rightside_Click";
 //BA.debugLineNum = 236;BA.debugLine="RightBottomClick";
_rightbottomclick();
 //BA.debugLineNum = 237;BA.debugLine="End Sub";
return "";
}
public boolean  _xpnl_slidebutton_touch2(Object _o,int _action,float _x,float _y,Object _motion) throws Exception{
 //BA.debugLineNum = 243;BA.debugLine="Private Sub xpnl_slidebutton_Touch2 (o As Object,";
 //BA.debugLineNum = 248;BA.debugLine="If xButtonOrientation = \"Horizontal\" Then";
if ((_xbuttonorientation).equals("Horizontal")) { 
 //BA.debugLineNum = 250;BA.debugLine="If ACTION = xpnl_slidebutton.TOUCH_ACTION_DOWN T";
if (_action==_xpnl_slidebutton.TOUCH_ACTION_DOWN) { 
 //BA.debugLineNum = 252;BA.debugLine="donwx = X";
_donwx = (int) (_x);
 }else if(_action==_xpnl_slidebutton.TOUCH_ACTION_MOVE) { 
 //BA.debugLineNum = 256;BA.debugLine="If xpnl_slidebutton.Left + x - donwx + xpnl_sli";
if (_xpnl_slidebutton.getLeft()+_x-_donwx+_xpnl_slidebutton.getWidth()<_mbase.getWidth()) { 
 //BA.debugLineNum = 257;BA.debugLine="xpnl_slidebutton.Left = Max(0,xpnl_slidebutton";
_xpnl_slidebutton.setLeft((int) (__c.Max(0,_xpnl_slidebutton.getLeft()+_x-_donwx)));
 }else {
 //BA.debugLineNum = 259;BA.debugLine="xpnl_slidebutton.Left = Min(mBase.Width - xpnl";
_xpnl_slidebutton.setLeft((int) (__c.Min(_mbase.getWidth()-_xpnl_slidebutton.getWidth(),_xpnl_slidebutton.getLeft()+_x-_donwx+_xpnl_slidebutton.getWidth())));
 };
 //BA.debugLineNum = 262;BA.debugLine="If xpnl_slidebutton.Left = 0 Then";
if (_xpnl_slidebutton.getLeft()==0) { 
 //BA.debugLineNum = 264;BA.debugLine="If bReachedLeftTop = False Then	ReachedLeftTop";
if (_breachedlefttop==__c.False) { 
_reachedlefttop();};
 //BA.debugLineNum = 265;BA.debugLine="bReachedLeftTop = True";
_breachedlefttop = __c.True;
 }else if(_xpnl_slidebutton.getLeft()+_xpnl_slidebutton.getWidth()==_mbase.getWidth()) { 
 //BA.debugLineNum = 269;BA.debugLine="If bReachedRightBottom = False Then	ReachedRig";
if (_breachedrightbottom==__c.False) { 
_reachedrightbottom();};
 //BA.debugLineNum = 270;BA.debugLine="bReachedRightBottom = True";
_breachedrightbottom = __c.True;
 };
 }else if(_xpnl_slidebutton.TOUCH_ACTION_UP==_action) { 
 //BA.debugLineNum = 276;BA.debugLine="If xpnl_slidebutton.Left + xpnl_slidebutton.Wid";
if (_xpnl_slidebutton.getLeft()+_xpnl_slidebutton.getWidth()/(double)2<_mbase.getWidth()/(double)2) { 
_dropslider(__c.True);}
else {
_dropslider(__c.False);};
 //BA.debugLineNum = 278;BA.debugLine="xpnl_slidebutton.SetLayoutAnimated(0,mBase.Widt";
_xpnl_slidebutton.SetLayoutAnimated((int) (0),(int) (_mbase.getWidth()/(double)2-_mbase.getHeight()/(double)2),(int) (0),_mbase.getHeight(),_mbase.getHeight());
 //BA.debugLineNum = 280;BA.debugLine="bReachedLeftTop = False";
_breachedlefttop = __c.False;
 //BA.debugLineNum = 281;BA.debugLine="bReachedRightBottom = False";
_breachedrightbottom = __c.False;
 };
 }else {
 //BA.debugLineNum = 288;BA.debugLine="If ACTION = xpnl_slidebutton.TOUCH_ACTION_DOWN T";
if (_action==_xpnl_slidebutton.TOUCH_ACTION_DOWN) { 
 //BA.debugLineNum = 290;BA.debugLine="downy  = y";
_downy = (int) (_y);
 }else if(_action==_xpnl_slidebutton.TOUCH_ACTION_MOVE) { 
 //BA.debugLineNum = 294;BA.debugLine="If xpnl_slidebutton.Top + y - downy + xpnl_sli";
if (_xpnl_slidebutton.getTop()+_y-_downy+_xpnl_slidebutton.getHeight()<_mbase.getHeight()) { 
 //BA.debugLineNum = 295;BA.debugLine="xpnl_slidebutton.Top = Max(0,xpnl_slidebutton.";
_xpnl_slidebutton.setTop((int) (__c.Max(0,_xpnl_slidebutton.getTop()+_y-_downy)));
 }else {
 //BA.debugLineNum = 297;BA.debugLine="xpnl_slidebutton.Top = Min(mBase.Height - xpnl";
_xpnl_slidebutton.setTop((int) (__c.Min(_mbase.getHeight()-_xpnl_slidebutton.getHeight(),_xpnl_slidebutton.getTop()+_y-_downy+_xpnl_slidebutton.getHeight())));
 };
 //BA.debugLineNum = 300;BA.debugLine="If xpnl_slidebutton.Top = 0 Then";
if (_xpnl_slidebutton.getTop()==0) { 
 //BA.debugLineNum = 302;BA.debugLine="If bReachedLeftTop = False Then	ReachedLeftTop";
if (_breachedlefttop==__c.False) { 
_reachedlefttop();};
 //BA.debugLineNum = 303;BA.debugLine="bReachedLeftTop = True";
_breachedlefttop = __c.True;
 }else if(_xpnl_slidebutton.getTop()+_xpnl_slidebutton.getHeight()==_mbase.getHeight()) { 
 //BA.debugLineNum = 307;BA.debugLine="If bReachedRightBottom = False Then	ReachedRig";
if (_breachedrightbottom==__c.False) { 
_reachedrightbottom();};
 //BA.debugLineNum = 308;BA.debugLine="bReachedRightBottom = True";
_breachedrightbottom = __c.True;
 };
 }else if(_xpnl_slidebutton.TOUCH_ACTION_UP==_action) { 
 //BA.debugLineNum = 314;BA.debugLine="If xpnl_slidebutton.Top + xpnl_slidebutton.Heig";
if (_xpnl_slidebutton.getTop()+_xpnl_slidebutton.getHeight()/(double)2<_mbase.getHeight()/(double)2) { 
_dropslider(__c.True);}
else {
_dropslider(__c.False);};
 //BA.debugLineNum = 318;BA.debugLine="xpnl_slidebutton.SetLayoutAnimated(0,0,mBase.He";
_xpnl_slidebutton.SetLayoutAnimated((int) (0),(int) (0),(int) (_mbase.getHeight()/(double)2-_mbase.getWidth()/(double)2),_mbase.getWidth(),_mbase.getWidth());
 //BA.debugLineNum = 320;BA.debugLine="bReachedLeftTop = False";
_breachedlefttop = __c.False;
 //BA.debugLineNum = 321;BA.debugLine="bReachedRightBottom = False";
_breachedrightbottom = __c.False;
 };
 };
 //BA.debugLineNum = 329;BA.debugLine="Return True";
if (true) return __c.True;
 //BA.debugLineNum = 331;BA.debugLine="End Sub";
return false;
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
