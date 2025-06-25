package Corner.Light.Bluetooth;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class diagnosticoactivity extends Activity implements B4AActivity{
	public static diagnosticoactivity mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "Corner.Light.Bluetooth", "Corner.Light.Bluetooth.diagnosticoactivity");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (diagnosticoactivity).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(this, processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "Corner.Light.Bluetooth", "Corner.Light.Bluetooth.diagnosticoactivity");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "Corner.Light.Bluetooth.diagnosticoactivity", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (diagnosticoactivity) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (diagnosticoactivity) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return diagnosticoactivity.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        if (!dontPause)
            BA.LogInfo("** Activity (diagnosticoactivity) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (diagnosticoactivity) Pause event (activity is not paused). **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        if (!dontPause) {
            processBA.setActivityPaused(true);
            mostCurrent = null;
        }

        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
            diagnosticoactivity mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (diagnosticoactivity) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static int _eje = 0;
public static int _accx = 0;
public static int _accy = 0;
public static String[] _cadena2 = null;
public Corner.Light.Bluetooth.asrangeseekbar _range_pich = null;
public Corner.Light.Bluetooth.asrangeseekbar _range_roll = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb_roll_izq = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb_pich_del = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb_roll_der = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb_pich_atras = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_calibrar = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb_roll = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb_xyaw = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb_yaw = null;
public anywheresoftware.b4a.objects.LabelWrapper _ln_xgyro = null;
public b4a.example.dateutils _dateutils = null;
public Corner.Light.Bluetooth.main _main = null;
public Corner.Light.Bluetooth.chatactivity _chatactivity = null;
public Corner.Light.Bluetooth.configactivity _configactivity = null;
public Corner.Light.Bluetooth.starter _starter = null;
public Corner.Light.Bluetooth.xuiviewsutils _xuiviewsutils = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 31;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 33;BA.debugLine="Activity.LoadLayout(\"4\")";
mostCurrent._activity.LoadLayout("4",mostCurrent.activityBA);
 //BA.debugLineNum = 34;BA.debugLine="Activity.Title=\"DIAGNOSTICO DEL SISTEMA\"";
mostCurrent._activity.setTitle(BA.ObjectToCharSequence("DIAGNOSTICO DEL SISTEMA"));
 //BA.debugLineNum = 35;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 45;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 46;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 47;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 48;BA.debugLine="StartActivity(ChatActivity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._chatactivity.getObject()));
 };
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 41;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 43;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 37;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 39;BA.debugLine="End Sub";
return "";
}
public static void  _btn_calibrar_longclick() throws Exception{
ResumableSub_btn_calibrar_LongClick rsub = new ResumableSub_btn_calibrar_LongClick(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btn_calibrar_LongClick extends BA.ResumableSub {
public ResumableSub_btn_calibrar_LongClick(Corner.Light.Bluetooth.diagnosticoactivity parent) {
this.parent = parent;
}
Corner.Light.Bluetooth.diagnosticoactivity parent;
String _mens = "";
int _result = 0;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 124;BA.debugLine="Dim mens As String";
_mens = "";
 //BA.debugLineNum = 125;BA.debugLine="mens = \"Esta por CALIBRAR el dispositivo. \"";
_mens = "Esta por CALIBRAR el dispositivo. ";
 //BA.debugLineNum = 127;BA.debugLine="Msgbox2Async(mens, \"Corner Light\", \"Sí\", \"\", \"No\"";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence(_mens),BA.ObjectToCharSequence("Corner Light"),"Sí","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 129;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 131;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 1:
//if
this.state = 4;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 132;BA.debugLine="ProgressDialogShow2(\"Calibrando....\",False)";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Calibrando...."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 134;BA.debugLine="EnviarDatos(True)";
_enviardatos(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 136;BA.debugLine="Sleep(600 * ChatActivity.Imu)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (600*parent.mostCurrent._chatactivity._imu /*int*/ ));
this.state = 6;
return;
case 6:
//C
this.state = 4;
;
 //BA.debugLineNum = 137;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 139;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static String  _enviardatos(boolean _calib) throws Exception{
String _cadena = "";
 //BA.debugLineNum = 141;BA.debugLine="private Sub EnviarDatos (Calib As Boolean)";
 //BA.debugLineNum = 146;BA.debugLine="Dim cadena As String";
_cadena = "";
 //BA.debugLineNum = 147;BA.debugLine="cadena = \"CALIB\"";
_cadena = "CALIB";
 //BA.debugLineNum = 149;BA.debugLine="Starter.Manager.SendMessage(cadena)";
mostCurrent._starter._manager /*Corner.Light.Bluetooth.bluetoothmanager*/ ._sendmessage /*String*/ (_cadena);
 //BA.debugLineNum = 150;BA.debugLine="Log(cadena)";
anywheresoftware.b4a.keywords.Common.LogImpl("330146569",_cadena,0);
 //BA.debugLineNum = 151;BA.debugLine="ToastMessageShow(\"Enviando Datos y Guardandolos..";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Enviando Datos y Guardandolos...."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 152;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 17;BA.debugLine="Dim     cadena2()	  As String";
mostCurrent._cadena2 = new String[(int) (0)];
java.util.Arrays.fill(mostCurrent._cadena2,"");
 //BA.debugLineNum = 18;BA.debugLine="Private Range_Pich    As ASRangeSeekBar";
mostCurrent._range_pich = new Corner.Light.Bluetooth.asrangeseekbar();
 //BA.debugLineNum = 19;BA.debugLine="Private Range_Roll    As ASRangeSeekBar";
mostCurrent._range_roll = new Corner.Light.Bluetooth.asrangeseekbar();
 //BA.debugLineNum = 20;BA.debugLine="Private lb_roll_Izq   As Label";
mostCurrent._lb_roll_izq = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private lb_pich_del   As Label";
mostCurrent._lb_pich_del = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private lb_roll_der   As Label";
mostCurrent._lb_roll_der = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private lb_pich_atras As Label";
mostCurrent._lb_pich_atras = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private btn_calibrar  As Button";
mostCurrent._btn_calibrar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private lb_roll       As Label";
mostCurrent._lb_roll = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private lb_xyaw       As Label";
mostCurrent._lb_xyaw = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private lb_yaw        As Label";
mostCurrent._lb_yaw = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private ln_xgyro      As Label";
mostCurrent._ln_xgyro = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="End Sub";
return "";
}
public static String  _logmessage(String _msg) throws Exception{
 //BA.debugLineNum = 56;BA.debugLine="Sub LogMessage(Msg As String)";
 //BA.debugLineNum = 57;BA.debugLine="cadena2 = Regex.Split(\",\",Msg)	'Separo el mesaje";
mostCurrent._cadena2 = anywheresoftware.b4a.keywords.Common.Regex.Split(",",_msg);
 //BA.debugLineNum = 59;BA.debugLine="If cadena2(0) <> \"CL\" Then";
if ((mostCurrent._cadena2[(int) (0)]).equals("CL") == false) { 
 //BA.debugLineNum = 60;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 63;BA.debugLine="Try";
try { //BA.debugLineNum = 64;BA.debugLine="If cadena2(0) = \"CL\" Then";
if ((mostCurrent._cadena2[(int) (0)]).equals("CL")) { 
 //BA.debugLineNum = 66;BA.debugLine="lb_roll.Text= cadena2(1)";
mostCurrent._lb_roll.setText(BA.ObjectToCharSequence(mostCurrent._cadena2[(int) (1)]));
 //BA.debugLineNum = 67;BA.debugLine="lb_xyaw.Text= cadena2(15)";
mostCurrent._lb_xyaw.setText(BA.ObjectToCharSequence(mostCurrent._cadena2[(int) (15)]));
 //BA.debugLineNum = 68;BA.debugLine="lb_yaw.Text	= cadena2(16)";
mostCurrent._lb_yaw.setText(BA.ObjectToCharSequence(mostCurrent._cadena2[(int) (16)]));
 //BA.debugLineNum = 70;BA.debugLine="eje 		= cadena2(6)";
_eje = (int)(Double.parseDouble(mostCurrent._cadena2[(int) (6)]));
 //BA.debugLineNum = 72;BA.debugLine="If eje = 2 Then";
if (_eje==2) { 
 //BA.debugLineNum = 73;BA.debugLine="accY 	= cadena2(13) * -100";
_accy = (int) ((double)(Double.parseDouble(mostCurrent._cadena2[(int) (13)]))*-100);
 //BA.debugLineNum = 74;BA.debugLine="accX 	= cadena2(14) * -100";
_accx = (int) ((double)(Double.parseDouble(mostCurrent._cadena2[(int) (14)]))*-100);
 }else {
 //BA.debugLineNum = 76;BA.debugLine="accY	= cadena2(14) * -100";
_accy = (int) ((double)(Double.parseDouble(mostCurrent._cadena2[(int) (14)]))*-100);
 //BA.debugLineNum = 77;BA.debugLine="accX	= cadena2(13) * -100";
_accx = (int) ((double)(Double.parseDouble(mostCurrent._cadena2[(int) (13)]))*-100);
 };
 //BA.debugLineNum = 80;BA.debugLine="ln_xgyro.Text = Round2(131 / cadena2(17),2)";
mostCurrent._ln_xgyro.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Round2(131/(double)(double)(Double.parseDouble(mostCurrent._cadena2[(int) (17)])),(int) (2))));
 //BA.debugLineNum = 82;BA.debugLine="If accX < -1 Then";
if (_accx<-1) { 
 //BA.debugLineNum = 83;BA.debugLine="lb_roll_Izq.Text  = Abs(accX)";
mostCurrent._lb_roll_izq.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Abs(_accx)));
 //BA.debugLineNum = 84;BA.debugLine="lb_roll_der.Text  = 0";
mostCurrent._lb_roll_der.setText(BA.ObjectToCharSequence(0));
 //BA.debugLineNum = 85;BA.debugLine="Range_Roll.Value1 = 1000+accX";
mostCurrent._range_roll._setvalue1 /*int*/ ((int) (1000+_accx));
 //BA.debugLineNum = 86;BA.debugLine="Range_Roll.Value2 = 1000";
mostCurrent._range_roll._setvalue2 /*int*/ ((int) (1000));
 }else if(_accx>-2 && _accx<2) { 
 //BA.debugLineNum = 88;BA.debugLine="Range_Roll.Value1 = 1000";
mostCurrent._range_roll._setvalue1 /*int*/ ((int) (1000));
 //BA.debugLineNum = 89;BA.debugLine="Range_Roll.Value2 = 1000";
mostCurrent._range_roll._setvalue2 /*int*/ ((int) (1000));
 //BA.debugLineNum = 90;BA.debugLine="lb_roll_Izq.Text  = 0";
mostCurrent._lb_roll_izq.setText(BA.ObjectToCharSequence(0));
 //BA.debugLineNum = 91;BA.debugLine="lb_roll_der.Text  = 0";
mostCurrent._lb_roll_der.setText(BA.ObjectToCharSequence(0));
 }else if(_accx>1) { 
 //BA.debugLineNum = 93;BA.debugLine="lb_roll_der.Text  = Abs(accX)";
mostCurrent._lb_roll_der.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Abs(_accx)));
 //BA.debugLineNum = 94;BA.debugLine="lb_roll_Izq.Text  = 0";
mostCurrent._lb_roll_izq.setText(BA.ObjectToCharSequence(0));
 //BA.debugLineNum = 95;BA.debugLine="Range_Roll.Value2 = 1000+accX";
mostCurrent._range_roll._setvalue2 /*int*/ ((int) (1000+_accx));
 //BA.debugLineNum = 96;BA.debugLine="Range_Roll.Value1 = 1000";
mostCurrent._range_roll._setvalue1 /*int*/ ((int) (1000));
 };
 //BA.debugLineNum = 100;BA.debugLine="If accY < -1 Then";
if (_accy<-1) { 
 //BA.debugLineNum = 101;BA.debugLine="lb_pich_del.Text = Abs(accY)";
mostCurrent._lb_pich_del.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Abs(_accy)));
 //BA.debugLineNum = 102;BA.debugLine="lb_pich_atras.Text = 0";
mostCurrent._lb_pich_atras.setText(BA.ObjectToCharSequence(0));
 //BA.debugLineNum = 103;BA.debugLine="Range_Pich.Value1=1000+accY";
mostCurrent._range_pich._setvalue1 /*int*/ ((int) (1000+_accy));
 //BA.debugLineNum = 104;BA.debugLine="Range_Pich.Value2=1000";
mostCurrent._range_pich._setvalue2 /*int*/ ((int) (1000));
 }else if(_accy>-1 && _accy<1) { 
 //BA.debugLineNum = 106;BA.debugLine="Range_Pich.Value1 =1000";
mostCurrent._range_pich._setvalue1 /*int*/ ((int) (1000));
 //BA.debugLineNum = 107;BA.debugLine="Range_Pich.Value2 =1000";
mostCurrent._range_pich._setvalue2 /*int*/ ((int) (1000));
 }else if(_accy>1) { 
 //BA.debugLineNum = 109;BA.debugLine="lb_pich_atras.Text = Abs(accY)";
mostCurrent._lb_pich_atras.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Abs(_accy)));
 //BA.debugLineNum = 110;BA.debugLine="lb_pich_del.Text =0";
mostCurrent._lb_pich_del.setText(BA.ObjectToCharSequence(0));
 //BA.debugLineNum = 111;BA.debugLine="Range_Pich.Value2=1000+accY";
mostCurrent._range_pich._setvalue2 /*int*/ ((int) (1000+_accy));
 //BA.debugLineNum = 112;BA.debugLine="Range_Pich.Value1 =1000";
mostCurrent._range_pich._setvalue1 /*int*/ ((int) (1000));
 };
 }else {
 //BA.debugLineNum = 115;BA.debugLine="Return";
if (true) return "";
 };
 } 
       catch (Exception e53) {
			processBA.setLastException(e53); //BA.debugLineNum = 118;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("330015550",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 121;BA.debugLine="End Sub";
return "";
}
public static String  _newmessage2(String _msg) throws Exception{
 //BA.debugLineNum = 52;BA.debugLine="Public Sub NewMessage2 (msg As String)";
 //BA.debugLineNum = 53;BA.debugLine="LogMessage(msg)";
_logmessage(_msg);
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim eje,accX, accY As Int";
_eje = 0;
_accx = 0;
_accy = 0;
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
}
