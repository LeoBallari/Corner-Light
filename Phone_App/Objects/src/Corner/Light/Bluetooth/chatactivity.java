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

public class chatactivity extends Activity implements B4AActivity{
	public static chatactivity mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "Corner.Light.Bluetooth", "Corner.Light.Bluetooth.chatactivity");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (chatactivity).");
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
		activityBA = new BA(this, layout, processBA, "Corner.Light.Bluetooth", "Corner.Light.Bluetooth.chatactivity");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "Corner.Light.Bluetooth.chatactivity", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (chatactivity) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (chatactivity) Resume **");
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
		return chatactivity.class;
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
            BA.LogInfo("** Activity (chatactivity) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (chatactivity) Pause event (activity is not paused). **");
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
            chatactivity mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (chatactivity) Resume **");
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
public static Object _color1 = null;
public static Object _color2 = null;
public static Object _color3 = null;
public static boolean _son = false;
public static float _valor = 0f;
public static float _ang_limite = 0f;
public static float _ang_limite2 = 0f;
public static float _filtro = 0f;
public static int _eje = 0;
public static int _ciclos_act = 0;
public static int _ciclos_des = 0;
public static int _invertir = 0;
public static int _modo = 0;
public static int _sal_izq = 0;
public static int _sal_der = 0;
public static int _imu = 0;
public static float _pasa_b = 0f;
public static int _yaw = 0;
public static int _gyro_esc = 0;
public static int _perfil_usuario = 0;
public static float _acel_limite = 0f;
public static String _nombre_bt = "";
public static int _tipo_filtro = 0;
public static float _lim = 0f;
public static float _lim2 = 0f;
public static anywheresoftware.b4a.phone.Phone.PhoneSensors _ps = null;
public static anywheresoftware.b4a.objects.Timer _timer1 = null;
public static anywheresoftware.b4a.objects.Timer _timer2 = null;
public static anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public static String _perfil = "";
public static float _ema_lp = 0f;
public static float _ema_alpha = 0f;
public static String _angulo_acce = "";
public static String _angulo_gyro = "";
public Corner.Light.Bluetooth.b4xdialog _dialog = null;
public static boolean _btn_izq_state = false;
public static boolean _btn_der_state = false;
public static boolean _primera_vez = false;
public static String[] _cadena = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb_sensor = null;
public Corner.Light.Bluetooth.gauge _gauge1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb_angmax_izq = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb_angmax_der = null;
public static float _max_ang_der = 0f;
public static float _max_ang_izq = 0f;
public anywheresoftware.b4a.objects.ImageViewWrapper _img_led_izq_on = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _img_led_der_on = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_config = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_reset = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb_limite1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_reset_inclina = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb_hora = null;
public static String _m = "";
public static String _h = "";
public static boolean _dospuntos = false;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_forzar_salida_izq = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_forzar_salida_der = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb_sensor2 = null;
public static int _parte_entera = 0;
public static int _parte_decimal = 0;
public anywheresoftware.b4a.objects.LabelWrapper _lb_perfil = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb_limite2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb_salida_forzada = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_on_off = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _img_auto_on = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _img_auto_off = null;
public anywheresoftware.b4a.objects.PanelWrapper _pn_cal_ang = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_angulos = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _img_fondo = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _see_angulo_p = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _see_angulo2_p = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb_ang1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb_ang2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_aceptar = null;
public anywheresoftware.b4a.objects.WebViewWrapper _webview1 = null;
public b4a.example.dateutils _dateutils = null;
public Corner.Light.Bluetooth.main _main = null;
public Corner.Light.Bluetooth.configactivity _configactivity = null;
public Corner.Light.Bluetooth.diagnosticoactivity _diagnosticoactivity = null;
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
 //BA.debugLineNum = 108;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 109;BA.debugLine="Activity.LoadLayout(\"2\")";
mostCurrent._activity.LoadLayout("2",mostCurrent.activityBA);
 //BA.debugLineNum = 111;BA.debugLine="If Starter.Manager.ConnectionState=True Then";
if (mostCurrent._starter._manager /*Corner.Light.Bluetooth.bluetoothmanager*/ ._connectionstate /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 112;BA.debugLine="Log(\"Dejo de leer sensores del telefono\")";
anywheresoftware.b4a.keywords.Common.LogImpl("3458756","Dejo de leer sensores del telefono",0);
 //BA.debugLineNum = 113;BA.debugLine="ps.StopListening";
_ps.StopListening(processBA);
 }else {
 //BA.debugLineNum = 116;BA.debugLine="Starter.Manager.Disconnect";
mostCurrent._starter._manager /*Corner.Light.Bluetooth.bluetoothmanager*/ ._disconnect /*String*/ ();
 //BA.debugLineNum = 117;BA.debugLine="If Main.Simulador=True Then";
if (mostCurrent._main._simulador /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 118;BA.debugLine="Log(\"Simulador Activo\")";
anywheresoftware.b4a.keywords.Common.LogImpl("3458762","Simulador Activo",0);
 //BA.debugLineNum = 120;BA.debugLine="ps.Initialize(ps.TYPE_ACCELEROMETER)";
_ps.Initialize(_ps.TYPE_ACCELEROMETER);
 };
 };
 //BA.debugLineNum = 130;BA.debugLine="Son=False";
_son = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 132;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 133;BA.debugLine="color1 = xui.Color_ARGB(255,29, 233, 182)";
_color1 = (Object)(_xui.Color_ARGB((int) (255),(int) (29),(int) (233),(int) (182)));
 //BA.debugLineNum = 134;BA.debugLine="color2 = xui.Color_ARGB(255,2, 136, 209)";
_color2 = (Object)(_xui.Color_ARGB((int) (255),(int) (2),(int) (136),(int) (209)));
 //BA.debugLineNum = 135;BA.debugLine="color3 = xui.Color_ARGB(255,6, 46, 66)";
_color3 = (Object)(_xui.Color_ARGB((int) (255),(int) (6),(int) (46),(int) (66)));
 //BA.debugLineNum = 137;BA.debugLine="EMA_ALPHA = 0.5";
_ema_alpha = (float) (0.5);
 //BA.debugLineNum = 138;BA.debugLine="sal_izq = 1";
_sal_izq = (int) (1);
 //BA.debugLineNum = 139;BA.debugLine="sal_der = 1";
_sal_der = (int) (1);
 //BA.debugLineNum = 141;BA.debugLine="If Main.Simulador=True Then";
if (mostCurrent._main._simulador /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 142;BA.debugLine="lim  = 8 'angulo limite en el simulador";
_lim = (float) (8);
 //BA.debugLineNum = 143;BA.debugLine="lim2 =  4 'angulo limite en el simulador";
_lim2 = (float) (4);
 //BA.debugLineNum = 144;BA.debugLine="lb_ang1.Text = lim";
mostCurrent._lb_ang1.setText(BA.ObjectToCharSequence(_lim));
 //BA.debugLineNum = 145;BA.debugLine="lb_ang2.Text = lim2";
mostCurrent._lb_ang2.setText(BA.ObjectToCharSequence(_lim2));
 //BA.debugLineNum = 146;BA.debugLine="Tipo_Filtro  = 2";
_tipo_filtro = (int) (2);
 };
 //BA.debugLineNum = 149;BA.debugLine="Timer1.Initialize(\"timer1\",500)";
_timer1.Initialize(processBA,"timer1",(long) (500));
 //BA.debugLineNum = 150;BA.debugLine="Timer1.Enabled=True";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 152;BA.debugLine="btn_izq_state=False";
_btn_izq_state = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 153;BA.debugLine="btn_der_state=False";
_btn_der_state = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 154;BA.debugLine="lb_salida_forzada.Visible=False";
mostCurrent._lb_salida_forzada.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 158;BA.debugLine="primera_vez=True";
_primera_vez = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 159;BA.debugLine="dospuntos=True";
_dospuntos = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 160;BA.debugLine="perfil = \"-\"";
_perfil = "-";
 };
 //BA.debugLineNum = 162;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 322;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 323;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 324;BA.debugLine="Main.Simulador	= False";
mostCurrent._main._simulador /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 325;BA.debugLine="Timer2.Enabled	= False";
_timer2.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 327;BA.debugLine="ps.StopListening";
_ps.StopListening(processBA);
 //BA.debugLineNum = 330;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 331;BA.debugLine="StartActivity(Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._main.getObject()));
 //BA.debugLineNum = 332;BA.debugLine="Log(\"Simulador Desactivado\")";
anywheresoftware.b4a.keywords.Common.LogImpl("3983050","Simulador Desactivado",0);
 };
 //BA.debugLineNum = 334;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 196;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 197;BA.debugLine="If UserClosed Then";
if (_userclosed) { 
 //BA.debugLineNum = 198;BA.debugLine="Starter.Manager.Disconnect";
mostCurrent._starter._manager /*Corner.Light.Bluetooth.bluetoothmanager*/ ._disconnect /*String*/ ();
 };
 //BA.debugLineNum = 200;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 164;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 165;BA.debugLine="Try";
try { //BA.debugLineNum = 166;BA.debugLine="If Main.Simulador=False Then";
if (mostCurrent._main._simulador /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 167;BA.debugLine="Timer2.Enabled=False";
_timer2.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 169;BA.debugLine="ps.StopListening";
_ps.StopListening(processBA);
 }else {
 //BA.debugLineNum = 172;BA.debugLine="Timer2.Initialize(\"timer2\",20)";
_timer2.Initialize(processBA,"timer2",(long) (20));
 //BA.debugLineNum = 173;BA.debugLine="Timer2.Enabled=True";
_timer2.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 176;BA.debugLine="ps.StartListening(\"Orientacion\")";
_ps.StartListening(processBA,"Orientacion");
 };
 //BA.debugLineNum = 180;BA.debugLine="Gauge1.SetMinAndMax(Ang_Limite*-3,Ang_Limite*3)";
mostCurrent._gauge1._setminandmax /*String*/ ((float) (_ang_limite*-3),(float) (_ang_limite*3));
 //BA.debugLineNum = 182;BA.debugLine="Gauge1.SetRanges(Array(Gauge1.CreateRange(Ang_Li";
mostCurrent._gauge1._setranges /*String*/ (anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._gauge1._createrange /*Corner.Light.Bluetooth.gauge._gaugerange*/ (_ang_limite,(float) (150),(int)(BA.ObjectToNumber(_color3)))),(Object)(mostCurrent._gauge1._createrange /*Corner.Light.Bluetooth.gauge._gaugerange*/ ((float) (-150),(float) (_ang_limite*-1),(int)(BA.ObjectToNumber(_color3)))),(Object)(mostCurrent._gauge1._createrange /*Corner.Light.Bluetooth.gauge._gaugerange*/ (_ang_limite,_ang_limite2,(int)(BA.ObjectToNumber(_color2)))),(Object)(mostCurrent._gauge1._createrange /*Corner.Light.Bluetooth.gauge._gaugerange*/ ((float) (_ang_limite*-1),(float) (_ang_limite2*-1),(int)(BA.ObjectToNumber(_color2)))),(Object)(mostCurrent._gauge1._createrange /*Corner.Light.Bluetooth.gauge._gaugerange*/ ((float) (_ang_limite2*-1),_ang_limite2,(int)(BA.ObjectToNumber(_color1))))}));
 //BA.debugLineNum = 188;BA.debugLine="lb_perfil.Text = perfil";
mostCurrent._lb_perfil.setText(BA.ObjectToCharSequence(_perfil));
 //BA.debugLineNum = 189;BA.debugLine="lb_Limite1.Text= Round2(Ang_Limite,1)";
mostCurrent._lb_limite1.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Round2(_ang_limite,(int) (1))));
 //BA.debugLineNum = 190;BA.debugLine="lb_Limite2.Text= Round2(Ang_Limite2,1)";
mostCurrent._lb_limite2.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Round2(_ang_limite2,(int) (1))));
 } 
       catch (Exception e16) {
			processBA.setLastException(e16); //BA.debugLineNum = 192;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("3524316",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 194;BA.debugLine="End Sub";
return "";
}
public static String  _ang_maximos(float _angulo) throws Exception{
 //BA.debugLineNum = 450;BA.debugLine="Private Sub Ang_Maximos(angulo As Float)";
 //BA.debugLineNum = 452;BA.debugLine="If (angulo < -0.5 And angulo*-1 > max_ang_izq) Th";
if ((_angulo<-0.5 && _angulo*-1>_max_ang_izq)) { 
 //BA.debugLineNum = 453;BA.debugLine="max_ang_izq = angulo*-1";
_max_ang_izq = (float) (_angulo*-1);
 };
 //BA.debugLineNum = 456;BA.debugLine="If (angulo > 0.5 And angulo > max_ang_der) Then";
if ((_angulo>0.5 && _angulo>_max_ang_der)) { 
 //BA.debugLineNum = 457;BA.debugLine="max_ang_der = angulo";
_max_ang_der = _angulo;
 };
 //BA.debugLineNum = 460;BA.debugLine="lb_angMAX_izq.Text = Round2(max_ang_izq,1)";
mostCurrent._lb_angmax_izq.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Round2(_max_ang_izq,(int) (1))));
 //BA.debugLineNum = 461;BA.debugLine="lb_angMAX_der.Text = Round2(max_ang_der,1)";
mostCurrent._lb_angmax_der.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Round2(_max_ang_der,(int) (1))));
 //BA.debugLineNum = 462;BA.debugLine="End Sub";
return "";
}
public static String  _btn_aceptar_click() throws Exception{
String _mens = "";
 //BA.debugLineNum = 653;BA.debugLine="Private Sub btn_aceptar_Click";
 //BA.debugLineNum = 654;BA.debugLine="If pn_cal_ang.Visible=False Then";
if (mostCurrent._pn_cal_ang.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 655;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 658;BA.debugLine="If (lb_ang1.Text = lb_Limite1.Text) And (lb_ang2.";
if (((mostCurrent._lb_ang1.getText()).equals(mostCurrent._lb_limite1.getText())) && ((mostCurrent._lb_ang2.getText()).equals(mostCurrent._lb_limite2.getText()))) { 
 //BA.debugLineNum = 659;BA.debugLine="pn_cal_ang.Visible	= False";
mostCurrent._pn_cal_ang.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 660;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 663;BA.debugLine="lim=lb_ang1.Text";
_lim = (float)(Double.parseDouble(mostCurrent._lb_ang1.getText()));
 //BA.debugLineNum = 664;BA.debugLine="lim2=lb_ang2.Text";
_lim2 = (float)(Double.parseDouble(mostCurrent._lb_ang2.getText()));
 //BA.debugLineNum = 666;BA.debugLine="lb_Limite1.Text		= lb_ang1.Text";
mostCurrent._lb_limite1.setText(BA.ObjectToCharSequence(mostCurrent._lb_ang1.getText()));
 //BA.debugLineNum = 667;BA.debugLine="lb_Limite2.Text		= lb_ang2.Text";
mostCurrent._lb_limite2.setText(BA.ObjectToCharSequence(mostCurrent._lb_ang2.getText()));
 //BA.debugLineNum = 668;BA.debugLine="Ang_Limite			= lb_ang1.Text";
_ang_limite = (float)(Double.parseDouble(mostCurrent._lb_ang1.getText()));
 //BA.debugLineNum = 669;BA.debugLine="Ang_Limite2			= lb_ang2.Text";
_ang_limite2 = (float)(Double.parseDouble(mostCurrent._lb_ang2.getText()));
 //BA.debugLineNum = 671;BA.debugLine="pn_cal_ang.Visible	= False";
mostCurrent._pn_cal_ang.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 673;BA.debugLine="Gauge1.SetMinAndMax(Ang_Limite*-3,Ang_Limite*3)";
mostCurrent._gauge1._setminandmax /*String*/ ((float) (_ang_limite*-3),(float) (_ang_limite*3));
 //BA.debugLineNum = 675;BA.debugLine="Gauge1.SetRanges(Array(Gauge1.CreateRange(Ang_Lim";
mostCurrent._gauge1._setranges /*String*/ (anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._gauge1._createrange /*Corner.Light.Bluetooth.gauge._gaugerange*/ (_ang_limite,(float) (150),(int)(BA.ObjectToNumber(_color3)))),(Object)(mostCurrent._gauge1._createrange /*Corner.Light.Bluetooth.gauge._gaugerange*/ ((float) (-150),(float) (_ang_limite*-1),(int)(BA.ObjectToNumber(_color3)))),(Object)(mostCurrent._gauge1._createrange /*Corner.Light.Bluetooth.gauge._gaugerange*/ (_ang_limite,_ang_limite2,(int)(BA.ObjectToNumber(_color2)))),(Object)(mostCurrent._gauge1._createrange /*Corner.Light.Bluetooth.gauge._gaugerange*/ ((float) (_ang_limite*-1),(float) (_ang_limite2*-1),(int)(BA.ObjectToNumber(_color2)))),(Object)(mostCurrent._gauge1._createrange /*Corner.Light.Bluetooth.gauge._gaugerange*/ ((float) (_ang_limite2*-1),_ang_limite2,(int)(BA.ObjectToNumber(_color1))))}));
 //BA.debugLineNum = 681;BA.debugLine="If Main.Simulador=False Then";
if (mostCurrent._main._simulador /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 682;BA.debugLine="Dim mens As String";
_mens = "";
 //BA.debugLineNum = 684;BA.debugLine="mens = \"ANGULOS,\"";
_mens = "ANGULOS,";
 //BA.debugLineNum = 685;BA.debugLine="mens = mens & lb_ang1.Text & \",\"";
_mens = _mens+mostCurrent._lb_ang1.getText()+",";
 //BA.debugLineNum = 686;BA.debugLine="mens = mens & lb_ang2.Text";
_mens = _mens+mostCurrent._lb_ang2.getText();
 //BA.debugLineNum = 688;BA.debugLine="Log(mens)";
anywheresoftware.b4a.keywords.Common.LogImpl("32162723",_mens,0);
 //BA.debugLineNum = 689;BA.debugLine="If Ang_Limite <> 0 And Ang_Limite2 <> 0 Then";
if (_ang_limite!=0 && _ang_limite2!=0) { 
 //BA.debugLineNum = 690;BA.debugLine="ToastMessageShow(\"Enviando Datos y Guardandolos";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Enviando Datos y Guardandolos...."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 692;BA.debugLine="Starter.Manager.SendMessage(mens)";
mostCurrent._starter._manager /*Corner.Light.Bluetooth.bluetoothmanager*/ ._sendmessage /*String*/ (_mens);
 };
 };
 //BA.debugLineNum = 695;BA.debugLine="End Sub";
return "";
}
public static String  _btn_angulos_click() throws Exception{
 //BA.debugLineNum = 623;BA.debugLine="Private Sub btn_angulos_Click";
 //BA.debugLineNum = 624;BA.debugLine="see_angulo_P.Value=Ang_Limite*2";
mostCurrent._see_angulo_p.setValue((int) (_ang_limite*2));
 //BA.debugLineNum = 625;BA.debugLine="see_angulo2_P.Value=Ang_Limite2*2";
mostCurrent._see_angulo2_p.setValue((int) (_ang_limite2*2));
 //BA.debugLineNum = 627;BA.debugLine="pn_cal_ang.Visible=True";
mostCurrent._pn_cal_ang.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 628;BA.debugLine="End Sub";
return "";
}
public static String  _btn_config_click() throws Exception{
 //BA.debugLineNum = 535;BA.debugLine="Private Sub btn_config_Click";
 //BA.debugLineNum = 536;BA.debugLine="StartActivity(ConfigActivity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._configactivity.getObject()));
 //BA.debugLineNum = 538;BA.debugLine="primera_vez=True";
_primera_vez = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 541;BA.debugLine="End Sub";
return "";
}
public static String  _btn_forzar_salida_der_click() throws Exception{
 //BA.debugLineNum = 594;BA.debugLine="Private Sub btn_forzar_salida_der_Click";
 //BA.debugLineNum = 595;BA.debugLine="If pn_cal_ang.Visible=False Then";
if (mostCurrent._pn_cal_ang.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 596;BA.debugLine="If btn_der_state = False Then";
if (_btn_der_state==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 597;BA.debugLine="btn_der_state=True";
_btn_der_state = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 599;BA.debugLine="btn_der_state=False";
_btn_der_state = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 602;BA.debugLine="Salidas_manual";
_salidas_manual();
 }else {
 //BA.debugLineNum = 604;BA.debugLine="pn_cal_ang.Visible=False";
mostCurrent._pn_cal_ang.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 606;BA.debugLine="End Sub";
return "";
}
public static String  _btn_forzar_salida_izq_click() throws Exception{
 //BA.debugLineNum = 580;BA.debugLine="Private Sub btn_forzar_salida_izq_Click";
 //BA.debugLineNum = 581;BA.debugLine="If pn_cal_ang.Visible=False Then";
if (mostCurrent._pn_cal_ang.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 582;BA.debugLine="If btn_izq_state = False Then";
if (_btn_izq_state==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 583;BA.debugLine="btn_izq_state=True";
_btn_izq_state = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 585;BA.debugLine="btn_izq_state=False";
_btn_izq_state = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 588;BA.debugLine="Salidas_manual";
_salidas_manual();
 }else {
 //BA.debugLineNum = 590;BA.debugLine="pn_cal_ang.Visible=False";
mostCurrent._pn_cal_ang.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 592;BA.debugLine="End Sub";
return "";
}
public static String  _btn_menu_click() throws Exception{
 //BA.debugLineNum = 465;BA.debugLine="Private Sub btn_Menu_Click";
 //BA.debugLineNum = 532;BA.debugLine="End Sub";
return "";
}
public static String  _btn_on_off_click() throws Exception{
String _mens = "";
 //BA.debugLineNum = 609;BA.debugLine="Private Sub btn_on_off_Click";
 //BA.debugLineNum = 610;BA.debugLine="Dim mens As String";
_mens = "";
 //BA.debugLineNum = 612;BA.debugLine="If modo = 1 Then";
if (_modo==1) { 
 //BA.debugLineNum = 613;BA.debugLine="mens = \"MANUAL\"";
_mens = "MANUAL";
 }else {
 //BA.debugLineNum = 615;BA.debugLine="mens = \"AUTO\"";
_mens = "AUTO";
 };
 //BA.debugLineNum = 618;BA.debugLine="Log(mens)";
anywheresoftware.b4a.keywords.Common.LogImpl("31769481",_mens,0);
 //BA.debugLineNum = 620;BA.debugLine="Starter.Manager.SendMessage(mens)";
mostCurrent._starter._manager /*Corner.Light.Bluetooth.bluetoothmanager*/ ._sendmessage /*String*/ (_mens);
 //BA.debugLineNum = 621;BA.debugLine="End Sub";
return "";
}
public static String  _btn_reset_click() throws Exception{
 //BA.debugLineNum = 543;BA.debugLine="Private Sub btn_reset_Click";
 //BA.debugLineNum = 544;BA.debugLine="lb_angMAX_izq.Text = \"0.0 º\"";
mostCurrent._lb_angmax_izq.setText(BA.ObjectToCharSequence("0.0 º"));
 //BA.debugLineNum = 545;BA.debugLine="lb_angMAX_der.Text = \"0.0 º\"";
mostCurrent._lb_angmax_der.setText(BA.ObjectToCharSequence("0.0 º"));
 //BA.debugLineNum = 547;BA.debugLine="max_ang_izq=0";
_max_ang_izq = (float) (0);
 //BA.debugLineNum = 548;BA.debugLine="max_ang_der=0";
_max_ang_der = (float) (0);
 //BA.debugLineNum = 549;BA.debugLine="End Sub";
return "";
}
public static String  _btn_reset_inclina_click() throws Exception{
 //BA.debugLineNum = 551;BA.debugLine="Private Sub btn_reset_inclina_Click";
 //BA.debugLineNum = 552;BA.debugLine="Starter.Manager.SendMessage(\"OFF_SET\")";
mostCurrent._starter._manager /*Corner.Light.Bluetooth.bluetoothmanager*/ ._sendmessage /*String*/ ("OFF_SET");
 //BA.debugLineNum = 553;BA.debugLine="End Sub";
return "";
}
public static String  _fusion_sensores(float _val_acc,float _val_gyro) throws Exception{
String _mens = "";
float _ang = 0f;
 //BA.debugLineNum = 300;BA.debugLine="Private Sub fusion_sensores(val_acc As Float, val_";
 //BA.debugLineNum = 301;BA.debugLine="Dim mens As String";
_mens = "";
 //BA.debugLineNum = 302;BA.debugLine="Dim ang As Float";
_ang = 0f;
 //BA.debugLineNum = 303;BA.debugLine="Dim Filtro As Float";
_filtro = 0f;
 //BA.debugLineNum = 305;BA.debugLine="Filtro = 0.99";
_filtro = (float) (0.99);
 //BA.debugLineNum = 311;BA.debugLine="ang = val_acc";
_ang = _val_acc;
 //BA.debugLineNum = 314;BA.debugLine="mens = \"CL,\" & ang & \",0.99,\"";
_mens = "CL,"+BA.NumberToString(_ang)+",0.99,";
 //BA.debugLineNum = 315;BA.debugLine="mens = mens & lim & \",5,5,1,0,1,\"";
_mens = _mens+BA.NumberToString(_lim)+",5,5,1,0,1,";
 //BA.debugLineNum = 316;BA.debugLine="mens = mens & sal_izq & \",\" & sal_der";
_mens = _mens+BA.NumberToString(_sal_izq)+","+BA.NumberToString(_sal_der);
 //BA.debugLineNum = 317;BA.debugLine="mens = mens & \",20,0.5,0,0,0,0,0,\" & lim2 & \",3,1";
_mens = _mens+",20,0.5,0,0,0,0,0,"+BA.NumberToString(_lim2)+",3,1,SIMULADOR,2 ";
 //BA.debugLineNum = 319;BA.debugLine="NewMessage(mens)";
_newmessage(_mens);
 //BA.debugLineNum = 320;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 49;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 50;BA.debugLine="Private angulo_Acce, angulo_gyro";
mostCurrent._angulo_acce = "";
mostCurrent._angulo_gyro = "";
 //BA.debugLineNum = 52;BA.debugLine="Private dialog As B4XDialog";
mostCurrent._dialog = new Corner.Light.Bluetooth.b4xdialog();
 //BA.debugLineNum = 54;BA.debugLine="Private btn_izq_state As Boolean";
_btn_izq_state = false;
 //BA.debugLineNum = 55;BA.debugLine="Private btn_der_state As Boolean";
_btn_der_state = false;
 //BA.debugLineNum = 57;BA.debugLine="Private primera_vez As Boolean";
_primera_vez = false;
 //BA.debugLineNum = 59;BA.debugLine="Dim cadena() As String";
mostCurrent._cadena = new String[(int) (0)];
java.util.Arrays.fill(mostCurrent._cadena,"");
 //BA.debugLineNum = 60;BA.debugLine="Private lb_sensor As Label";
mostCurrent._lb_sensor = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Private Gauge1 As Gauge";
mostCurrent._gauge1 = new Corner.Light.Bluetooth.gauge();
 //BA.debugLineNum = 63;BA.debugLine="Public lb_angMAX_izq As Label";
mostCurrent._lb_angmax_izq = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Public lb_angMAX_der As Label";
mostCurrent._lb_angmax_der = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 66;BA.debugLine="Dim max_ang_der As Float";
_max_ang_der = 0f;
 //BA.debugLineNum = 67;BA.debugLine="Dim max_ang_izq As Float";
_max_ang_izq = 0f;
 //BA.debugLineNum = 69;BA.debugLine="Private img_Led_izq_on As ImageView";
mostCurrent._img_led_izq_on = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 70;BA.debugLine="Private img_Led_der_on As ImageView";
mostCurrent._img_led_der_on = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 72;BA.debugLine="Private btn_config As Button";
mostCurrent._btn_config = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 73;BA.debugLine="Private btn_reset As Button";
mostCurrent._btn_reset = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 75;BA.debugLine="Private lb_Limite1 As Label";
mostCurrent._lb_limite1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 76;BA.debugLine="Private btn_reset_inclina As Button";
mostCurrent._btn_reset_inclina = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 77;BA.debugLine="Private lb_hora As Label";
mostCurrent._lb_hora = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 79;BA.debugLine="Dim M, H As String";
mostCurrent._m = "";
mostCurrent._h = "";
 //BA.debugLineNum = 80;BA.debugLine="Dim dospuntos As Boolean";
_dospuntos = false;
 //BA.debugLineNum = 81;BA.debugLine="Private btn_forzar_salida_izq As Button";
mostCurrent._btn_forzar_salida_izq = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 82;BA.debugLine="Private btn_forzar_salida_der As Button";
mostCurrent._btn_forzar_salida_der = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 83;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 84;BA.debugLine="Private lb_sensor2 As Label";
mostCurrent._lb_sensor2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 86;BA.debugLine="Private parte_entera As Int";
_parte_entera = 0;
 //BA.debugLineNum = 87;BA.debugLine="Private parte_decimal As Int";
_parte_decimal = 0;
 //BA.debugLineNum = 88;BA.debugLine="Private lb_perfil As Label";
mostCurrent._lb_perfil = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 90;BA.debugLine="Private lb_Limite2 As Label";
mostCurrent._lb_limite2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 91;BA.debugLine="Private lb_salida_forzada As Label";
mostCurrent._lb_salida_forzada = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 93;BA.debugLine="Private btn_on_off As Button";
mostCurrent._btn_on_off = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 94;BA.debugLine="Private img_Auto_on As ImageView";
mostCurrent._img_auto_on = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 95;BA.debugLine="Private img_Auto_off As ImageView";
mostCurrent._img_auto_off = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 96;BA.debugLine="Private pn_cal_ang As Panel";
mostCurrent._pn_cal_ang = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 97;BA.debugLine="Private btn_angulos As Button";
mostCurrent._btn_angulos = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 98;BA.debugLine="Private img_fondo As ImageView";
mostCurrent._img_fondo = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 99;BA.debugLine="Private see_angulo_P As SeekBar";
mostCurrent._see_angulo_p = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 100;BA.debugLine="Private see_angulo2_P As SeekBar";
mostCurrent._see_angulo2_p = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 101;BA.debugLine="Private lb_ang1 As Label";
mostCurrent._lb_ang1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 102;BA.debugLine="Private lb_ang2 As Label";
mostCurrent._lb_ang2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 103;BA.debugLine="Private btn_aceptar As Button";
mostCurrent._btn_aceptar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 105;BA.debugLine="Private WebView1 As WebView";
mostCurrent._webview1 = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 106;BA.debugLine="End Sub";
return "";
}
public static String  _img_fondo_click() throws Exception{
 //BA.debugLineNum = 630;BA.debugLine="Private Sub img_fondo_Click";
 //BA.debugLineNum = 632;BA.debugLine="End Sub";
return "";
}
public static String  _leds(int _led_izq,int _led_der) throws Exception{
String _mens = "";
 //BA.debugLineNum = 432;BA.debugLine="Private Sub Leds(led_izq As Int, led_der As Int)";
 //BA.debugLineNum = 433;BA.debugLine="Dim mens As String";
_mens = "";
 //BA.debugLineNum = 434;BA.debugLine="Log(led_izq)";
anywheresoftware.b4a.keywords.Common.LogImpl("31179650",BA.NumberToString(_led_izq),0);
 //BA.debugLineNum = 435;BA.debugLine="Log(led_der)";
anywheresoftware.b4a.keywords.Common.LogImpl("31179651",BA.NumberToString(_led_der),0);
 //BA.debugLineNum = 437;BA.debugLine="If led_izq=0 Then";
if (_led_izq==0) { 
 //BA.debugLineNum = 438;BA.debugLine="img_Led_izq_on.Visible=True";
mostCurrent._img_led_izq_on.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 440;BA.debugLine="img_Led_izq_on.Visible=False";
mostCurrent._img_led_izq_on.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 443;BA.debugLine="If led_der = 0 Then";
if (_led_der==0) { 
 //BA.debugLineNum = 444;BA.debugLine="img_Led_der_on.Visible=True";
mostCurrent._img_led_der_on.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 446;BA.debugLine="img_Led_der_on.Visible=False";
mostCurrent._img_led_der_on.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 448;BA.debugLine="End Sub";
return "";
}
public static String  _logmessage(String _msg) throws Exception{
 //BA.debugLineNum = 340;BA.debugLine="Sub LogMessage(Msg As String)";
 //BA.debugLineNum = 341;BA.debugLine="cadena = Regex.Split(\",\",Msg)	'Separo el mesaje";
mostCurrent._cadena = anywheresoftware.b4a.keywords.Common.Regex.Split(",",_msg);
 //BA.debugLineNum = 343;BA.debugLine="If cadena(0) <> \"CL\" Or cadena(0).Length <> 2 The";
if ((mostCurrent._cadena[(int) (0)]).equals("CL") == false || mostCurrent._cadena[(int) (0)].length()!=2) { 
 //BA.debugLineNum = 344;BA.debugLine="Log(\"ERROR\")";
anywheresoftware.b4a.keywords.Common.LogImpl("31114116","ERROR",0);
 //BA.debugLineNum = 345;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 348;BA.debugLine="Try";
try { //BA.debugLineNum = 349;BA.debugLine="If cadena(0) = \"CL\" Then";
if ((mostCurrent._cadena[(int) (0)]).equals("CL")) { 
 //BA.debugLineNum = 350;BA.debugLine="Valor = cadena(1)";
_valor = (float)(Double.parseDouble(mostCurrent._cadena[(int) (1)]));
 //BA.debugLineNum = 351;BA.debugLine="Valor = Round2(Valor,1)";
_valor = (float) (anywheresoftware.b4a.keywords.Common.Round2(_valor,(int) (1)));
 //BA.debugLineNum = 353;BA.debugLine="If Valor > -0.5 And Valor < 0.5 Then";
if (_valor>-0.5 && _valor<0.5) { 
 //BA.debugLineNum = 354;BA.debugLine="Valor=0";
_valor = (float) (0);
 };
 //BA.debugLineNum = 357;BA.debugLine="parte_entera = Abs(Valor.As(Int))";
_parte_entera = (int) (anywheresoftware.b4a.keywords.Common.Abs(((int) (_valor))));
 //BA.debugLineNum = 358;BA.debugLine="lb_sensor.Text = parte_entera";
mostCurrent._lb_sensor.setText(BA.ObjectToCharSequence(_parte_entera));
 //BA.debugLineNum = 360;BA.debugLine="parte_decimal = (Abs(Valor) - parte_entera)*10";
_parte_decimal = (int) ((anywheresoftware.b4a.keywords.Common.Abs(_valor)-_parte_entera)*10);
 //BA.debugLineNum = 361;BA.debugLine="lb_sensor2.Text = \".\" & parte_decimal";
mostCurrent._lb_sensor2.setText(BA.ObjectToCharSequence("."+BA.NumberToString(_parte_decimal)));
 //BA.debugLineNum = 365;BA.debugLine="Filtro 			= cadena(2)";
_filtro = (float)(Double.parseDouble(mostCurrent._cadena[(int) (2)]));
 //BA.debugLineNum = 366;BA.debugLine="Ang_Limite		= cadena(3)";
_ang_limite = (float)(Double.parseDouble(mostCurrent._cadena[(int) (3)]));
 //BA.debugLineNum = 367;BA.debugLine="Ciclos_Act		= cadena(4)";
_ciclos_act = (int)(Double.parseDouble(mostCurrent._cadena[(int) (4)]));
 //BA.debugLineNum = 368;BA.debugLine="Ciclos_Des		= cadena(5)";
_ciclos_des = (int)(Double.parseDouble(mostCurrent._cadena[(int) (5)]));
 //BA.debugLineNum = 369;BA.debugLine="eje				= cadena(6)";
_eje = (int)(Double.parseDouble(mostCurrent._cadena[(int) (6)]));
 //BA.debugLineNum = 370;BA.debugLine="invertir		= cadena(7)";
_invertir = (int)(Double.parseDouble(mostCurrent._cadena[(int) (7)]));
 //BA.debugLineNum = 371;BA.debugLine="modo			= cadena(8)";
_modo = (int)(Double.parseDouble(mostCurrent._cadena[(int) (8)]));
 //BA.debugLineNum = 372;BA.debugLine="sal_izq			= cadena(9)";
_sal_izq = (int)(Double.parseDouble(mostCurrent._cadena[(int) (9)]));
 //BA.debugLineNum = 373;BA.debugLine="sal_der			= cadena(10)";
_sal_der = (int)(Double.parseDouble(mostCurrent._cadena[(int) (10)]));
 //BA.debugLineNum = 374;BA.debugLine="Imu				= cadena(11)";
_imu = (int)(Double.parseDouble(mostCurrent._cadena[(int) (11)]));
 //BA.debugLineNum = 375;BA.debugLine="pasa_b 			= cadena(12)";
_pasa_b = (float)(Double.parseDouble(mostCurrent._cadena[(int) (12)]));
 //BA.debugLineNum = 376;BA.debugLine="yaw 			= cadena(15)";
_yaw = (int)(Double.parseDouble(mostCurrent._cadena[(int) (15)]));
 //BA.debugLineNum = 377;BA.debugLine="gyro_esc 		= cadena(17)";
_gyro_esc = (int)(Double.parseDouble(mostCurrent._cadena[(int) (17)]));
 //BA.debugLineNum = 378;BA.debugLine="Ang_Limite2		= cadena(18)";
_ang_limite2 = (float)(Double.parseDouble(mostCurrent._cadena[(int) (18)]));
 //BA.debugLineNum = 379;BA.debugLine="perfil_usuario	= cadena(19)";
_perfil_usuario = (int)(Double.parseDouble(mostCurrent._cadena[(int) (19)]));
 //BA.debugLineNum = 380;BA.debugLine="nombre_BT		= cadena(20)";
_nombre_bt = mostCurrent._cadena[(int) (20)];
 //BA.debugLineNum = 382;BA.debugLine="Leds(sal_izq,sal_der)";
_leds(_sal_izq,_sal_der);
 //BA.debugLineNum = 385;BA.debugLine="If primera_vez Then";
if (_primera_vez) { 
 //BA.debugLineNum = 386;BA.debugLine="Gauge1.SetMinAndMax(Ang_Limite*-3,Ang_Limite*3";
mostCurrent._gauge1._setminandmax /*String*/ ((float) (_ang_limite*-3),(float) (_ang_limite*3));
 //BA.debugLineNum = 388;BA.debugLine="Gauge1.SetRanges(Array(Gauge1.CreateRange(Ang_";
mostCurrent._gauge1._setranges /*String*/ (anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._gauge1._createrange /*Corner.Light.Bluetooth.gauge._gaugerange*/ (_ang_limite,(float) (150),(int)(BA.ObjectToNumber(_color3)))),(Object)(mostCurrent._gauge1._createrange /*Corner.Light.Bluetooth.gauge._gaugerange*/ ((float) (-150),(float) (_ang_limite*-1),(int)(BA.ObjectToNumber(_color3)))),(Object)(mostCurrent._gauge1._createrange /*Corner.Light.Bluetooth.gauge._gaugerange*/ (_ang_limite,_ang_limite2,(int)(BA.ObjectToNumber(_color2)))),(Object)(mostCurrent._gauge1._createrange /*Corner.Light.Bluetooth.gauge._gaugerange*/ ((float) (_ang_limite*-1),(float) (_ang_limite2*-1),(int)(BA.ObjectToNumber(_color2)))),(Object)(mostCurrent._gauge1._createrange /*Corner.Light.Bluetooth.gauge._gaugerange*/ ((float) (_ang_limite2*-1),_ang_limite2,(int)(BA.ObjectToNumber(_color1))))}));
 //BA.debugLineNum = 394;BA.debugLine="lb_Limite1.Text= Ang_Limite";
mostCurrent._lb_limite1.setText(BA.ObjectToCharSequence(_ang_limite));
 //BA.debugLineNum = 395;BA.debugLine="lb_Limite2.Text= Ang_Limite2";
mostCurrent._lb_limite2.setText(BA.ObjectToCharSequence(_ang_limite2));
 //BA.debugLineNum = 397;BA.debugLine="img_Led_der_on.Visible=True";
mostCurrent._img_led_der_on.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 398;BA.debugLine="img_Led_izq_on.Visible=True";
mostCurrent._img_led_izq_on.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 400;BA.debugLine="primera_vez=False";
_primera_vez = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 403;BA.debugLine="If perfil_usuario = 1 Then";
if (_perfil_usuario==1) { 
 //BA.debugLineNum = 404;BA.debugLine="lb_perfil.Text = \"URBANO\"";
mostCurrent._lb_perfil.setText(BA.ObjectToCharSequence("URBANO"));
 }else if(_perfil_usuario==2) { 
 //BA.debugLineNum = 406;BA.debugLine="lb_perfil.Text = \"RUTA\"";
mostCurrent._lb_perfil.setText(BA.ObjectToCharSequence("RUTA"));
 }else if(_perfil_usuario==3) { 
 //BA.debugLineNum = 408;BA.debugLine="lb_perfil.Text = \"SIMULADOR\"";
mostCurrent._lb_perfil.setText(BA.ObjectToCharSequence("SIMULADOR"));
 }else {
 //BA.debugLineNum = 410;BA.debugLine="lb_perfil.Text = \" - \"";
mostCurrent._lb_perfil.setText(BA.ObjectToCharSequence(" - "));
 };
 //BA.debugLineNum = 413;BA.debugLine="Gauge1.CurrentValue = Valor";
mostCurrent._gauge1._setcurrentvalue /*float*/ (_valor);
 //BA.debugLineNum = 415;BA.debugLine="Ang_Maximos(Valor)";
_ang_maximos(_valor);
 //BA.debugLineNum = 417;BA.debugLine="If modo = 1 Then";
if (_modo==1) { 
 //BA.debugLineNum = 418;BA.debugLine="img_Auto_on.Visible=True";
mostCurrent._img_auto_on.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 419;BA.debugLine="img_Auto_off.Visible=False";
mostCurrent._img_auto_off.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 421;BA.debugLine="img_Auto_on.Visible=False";
mostCurrent._img_auto_on.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 422;BA.debugLine="img_Auto_off.Visible=True";
mostCurrent._img_auto_off.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 }else {
 //BA.debugLineNum = 425;BA.debugLine="Return";
if (true) return "";
 };
 } 
       catch (Exception e65) {
			processBA.setLastException(e65); //BA.debugLineNum = 428;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("31114200",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 430;BA.debugLine="End Sub";
return "";
}
public static float  _lowpassfilter(float _value) throws Exception{
 //BA.debugLineNum = 240;BA.debugLine="Private Sub LowPassFilter(value As Float) As Float";
 //BA.debugLineNum = 241;BA.debugLine="EMA_LP = EMA_ALPHA * value + (1 - EMA_ALPHA) * EM";
_ema_lp = (float) (_ema_alpha*_value+(1-_ema_alpha)*_ema_lp);
 //BA.debugLineNum = 242;BA.debugLine="Return EMA_LP";
if (true) return _ema_lp;
 //BA.debugLineNum = 243;BA.debugLine="End Sub";
return 0f;
}
public static String  _newmessage(String _msg) throws Exception{
 //BA.debugLineNum = 336;BA.debugLine="Public Sub NewMessage (msg As String)";
 //BA.debugLineNum = 337;BA.debugLine="LogMessage(msg)";
_logmessage(_msg);
 //BA.debugLineNum = 338;BA.debugLine="End Sub";
return "";
}
public static String  _orientacion_sensorchanged(float[] _values) throws Exception{
float _ang = 0f;
 //BA.debugLineNum = 246;BA.debugLine="Sub Orientacion_SensorChanged (Values() As Float)";
 //BA.debugLineNum = 247;BA.debugLine="Dim ang As Float";
_ang = 0f;
 //BA.debugLineNum = 249;BA.debugLine="ang = Round2(Values(1),1)*6.5					'Si defino TYPE";
_ang = (float) (anywheresoftware.b4a.keywords.Common.Round2(_values[(int) (1)],(int) (1))*6.5);
 //BA.debugLineNum = 250;BA.debugLine="ang = LowPassFilter(Round2(Values(1),1)*6.5)	'Si";
_ang = _lowpassfilter((float) (anywheresoftware.b4a.keywords.Common.Round2(_values[(int) (1)],(int) (1))*6.5));
 //BA.debugLineNum = 252;BA.debugLine="If (ang > 90) And (ang < 180) Then";
if ((_ang>90) && (_ang<180)) { 
 //BA.debugLineNum = 253;BA.debugLine="ang = 180 - ang";
_ang = (float) (180-_ang);
 }else if((_ang>-180) && (_ang<-90)) { 
 //BA.debugLineNum = 255;BA.debugLine="ang = (180 + ang) * -1";
_ang = (float) ((180+_ang)*-1);
 };
 //BA.debugLineNum = 258;BA.debugLine="If ang > lim Then";
if (_ang>_lim) { 
 //BA.debugLineNum = 259;BA.debugLine="sal_der=0";
_sal_der = (int) (0);
 };
 //BA.debugLineNum = 261;BA.debugLine="If ang < lim2 Then";
if (_ang<_lim2) { 
 //BA.debugLineNum = 262;BA.debugLine="sal_der=1";
_sal_der = (int) (1);
 };
 //BA.debugLineNum = 264;BA.debugLine="If ang < lim *-1 Then";
if (_ang<_lim*-1) { 
 //BA.debugLineNum = 265;BA.debugLine="sal_izq = 0";
_sal_izq = (int) (0);
 };
 //BA.debugLineNum = 267;BA.debugLine="If ang > lim2*-1 Then";
if (_ang>_lim2*-1) { 
 //BA.debugLineNum = 268;BA.debugLine="sal_izq=1";
_sal_izq = (int) (1);
 };
 //BA.debugLineNum = 270;BA.debugLine="angulo_Acce = ang";
mostCurrent._angulo_acce = BA.NumberToString(_ang);
 //BA.debugLineNum = 271;BA.debugLine="End Sub";
return "";
}
public static String  _pn_cal_ang_click() throws Exception{
 //BA.debugLineNum = 649;BA.debugLine="Private Sub pn_cal_ang_Click";
 //BA.debugLineNum = 650;BA.debugLine="Return";
if (true) return "";
 //BA.debugLineNum = 651;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Public color1,color2,color3 As Object";
_color1 = new Object();
_color2 = new Object();
_color3 = new Object();
 //BA.debugLineNum = 9;BA.debugLine="Dim Son As Boolean";
_son = false;
 //BA.debugLineNum = 11;BA.debugLine="Dim Valor As Float";
_valor = 0f;
 //BA.debugLineNum = 12;BA.debugLine="Dim Ang_Limite As Float";
_ang_limite = 0f;
 //BA.debugLineNum = 13;BA.debugLine="Dim Ang_Limite2 As Float";
_ang_limite2 = 0f;
 //BA.debugLineNum = 15;BA.debugLine="Dim Filtro As Float";
_filtro = 0f;
 //BA.debugLineNum = 16;BA.debugLine="Dim eje As Int";
_eje = 0;
 //BA.debugLineNum = 17;BA.debugLine="Dim Ciclos_Act As Int";
_ciclos_act = 0;
 //BA.debugLineNum = 18;BA.debugLine="Dim Ciclos_Des As Int";
_ciclos_des = 0;
 //BA.debugLineNum = 19;BA.debugLine="Dim invertir As Int";
_invertir = 0;
 //BA.debugLineNum = 20;BA.debugLine="Dim modo As Int";
_modo = 0;
 //BA.debugLineNum = 21;BA.debugLine="Dim sal_izq As Int";
_sal_izq = 0;
 //BA.debugLineNum = 22;BA.debugLine="Dim sal_der As Int";
_sal_der = 0;
 //BA.debugLineNum = 23;BA.debugLine="Dim Imu As Int";
_imu = 0;
 //BA.debugLineNum = 24;BA.debugLine="Dim pasa_b As Float";
_pasa_b = 0f;
 //BA.debugLineNum = 25;BA.debugLine="Dim yaw As Int";
_yaw = 0;
 //BA.debugLineNum = 26;BA.debugLine="Dim gyro_esc As Int";
_gyro_esc = 0;
 //BA.debugLineNum = 27;BA.debugLine="Dim perfil_usuario As Int";
_perfil_usuario = 0;
 //BA.debugLineNum = 28;BA.debugLine="Dim acel_Limite As Float";
_acel_limite = 0f;
 //BA.debugLineNum = 29;BA.debugLine="Dim nombre_BT As String";
_nombre_bt = "";
 //BA.debugLineNum = 30;BA.debugLine="Dim Tipo_Filtro As Int";
_tipo_filtro = 0;
 //BA.debugLineNum = 32;BA.debugLine="Dim lim As Float";
_lim = 0f;
 //BA.debugLineNum = 33;BA.debugLine="Dim lim2 As Float";
_lim2 = 0f;
 //BA.debugLineNum = 35;BA.debugLine="Dim ps As PhoneSensors";
_ps = new anywheresoftware.b4a.phone.Phone.PhoneSensors();
 //BA.debugLineNum = 38;BA.debugLine="Dim Timer1 As Timer";
_timer1 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 39;BA.debugLine="Dim Timer2 As Timer";
_timer2 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 41;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 43;BA.debugLine="Public perfil As String";
_perfil = "";
 //BA.debugLineNum = 45;BA.debugLine="Dim EMA_LP As Float";
_ema_lp = 0f;
 //BA.debugLineNum = 46;BA.debugLine="Dim EMA_ALPHA As Float";
_ema_alpha = 0f;
 //BA.debugLineNum = 47;BA.debugLine="End Sub";
return "";
}
public static String  _salidas_manual() throws Exception{
String _mens = "";
 //BA.debugLineNum = 555;BA.debugLine="Private Sub Salidas_manual()";
 //BA.debugLineNum = 556;BA.debugLine="Dim mens As String";
_mens = "";
 //BA.debugLineNum = 557;BA.debugLine="Dim sal_izq,sal_der As Int";
_sal_izq = 0;
_sal_der = 0;
 //BA.debugLineNum = 559;BA.debugLine="If btn_izq_state = True Then";
if (_btn_izq_state==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 560;BA.debugLine="sal_izq = 0";
_sal_izq = (int) (0);
 }else {
 //BA.debugLineNum = 562;BA.debugLine="sal_izq = 1";
_sal_izq = (int) (1);
 };
 //BA.debugLineNum = 565;BA.debugLine="If btn_der_state = True Then";
if (_btn_der_state==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 566;BA.debugLine="sal_der = 0";
_sal_der = (int) (0);
 }else {
 //BA.debugLineNum = 568;BA.debugLine="sal_der = 1";
_sal_der = (int) (1);
 };
 //BA.debugLineNum = 571;BA.debugLine="modo = 1";
_modo = (int) (1);
 //BA.debugLineNum = 573;BA.debugLine="mens = \"SAL,\"";
_mens = "SAL,";
 //BA.debugLineNum = 574;BA.debugLine="mens = mens & modo & \",\" & sal_izq & \",\" & sal_de";
_mens = _mens+BA.NumberToString(_modo)+","+BA.NumberToString(_sal_izq)+","+BA.NumberToString(_sal_der);
 //BA.debugLineNum = 576;BA.debugLine="Log(mens)";
anywheresoftware.b4a.keywords.Common.LogImpl("31572885",_mens,0);
 //BA.debugLineNum = 577;BA.debugLine="Starter.Manager.SendMessage(mens)";
mostCurrent._starter._manager /*Corner.Light.Bluetooth.bluetoothmanager*/ ._sendmessage /*String*/ (_mens);
 //BA.debugLineNum = 578;BA.debugLine="End Sub";
return "";
}
public static String  _see_angulo_p_valuechanged(int _value,boolean _userchanged) throws Exception{
 //BA.debugLineNum = 634;BA.debugLine="Private Sub see_angulo_P_ValueChanged (Value As In";
 //BA.debugLineNum = 635;BA.debugLine="lb_ang1.Text = (see_angulo_P.Value/2)";
mostCurrent._lb_ang1.setText(BA.ObjectToCharSequence((mostCurrent._see_angulo_p.getValue()/(double)2)));
 //BA.debugLineNum = 636;BA.debugLine="If see_angulo2_P.Value > see_angulo_P.Value Then";
if (mostCurrent._see_angulo2_p.getValue()>mostCurrent._see_angulo_p.getValue()) { 
 //BA.debugLineNum = 637;BA.debugLine="see_angulo2_P.Value = see_angulo_P.Value-0.5";
mostCurrent._see_angulo2_p.setValue((int) (mostCurrent._see_angulo_p.getValue()-0.5));
 };
 //BA.debugLineNum = 639;BA.debugLine="End Sub";
return "";
}
public static String  _see_angulo2_p_valuechanged(int _value,boolean _userchanged) throws Exception{
 //BA.debugLineNum = 641;BA.debugLine="Private Sub see_angulo2_P_ValueChanged (Value As I";
 //BA.debugLineNum = 642;BA.debugLine="If see_angulo2_P.Value < see_angulo_P.Value Then";
if (mostCurrent._see_angulo2_p.getValue()<mostCurrent._see_angulo_p.getValue()) { 
 //BA.debugLineNum = 643;BA.debugLine="lb_ang2.Text = (see_angulo2_P.Value/2)";
mostCurrent._lb_ang2.setText(BA.ObjectToCharSequence((mostCurrent._see_angulo2_p.getValue()/(double)2)));
 }else {
 //BA.debugLineNum = 645;BA.debugLine="see_angulo2_P.Value = see_angulo_P.Value-0.5";
mostCurrent._see_angulo2_p.setValue((int) (mostCurrent._see_angulo_p.getValue()-0.5));
 };
 //BA.debugLineNum = 647;BA.debugLine="End Sub";
return "";
}
public static String  _timer1_tick() throws Exception{
 //BA.debugLineNum = 202;BA.debugLine="Sub timer1_tick";
 //BA.debugLineNum = 203;BA.debugLine="H = NumberFormat(DateTime.GetHour(DateTime.Now),2";
mostCurrent._h = anywheresoftware.b4a.keywords.Common.NumberFormat(anywheresoftware.b4a.keywords.Common.DateTime.GetHour(anywheresoftware.b4a.keywords.Common.DateTime.getNow()),(int) (2),(int) (0));
 //BA.debugLineNum = 204;BA.debugLine="M = NumberFormat(DateTime.GetMinute(DateTime.Now)";
mostCurrent._m = anywheresoftware.b4a.keywords.Common.NumberFormat(anywheresoftware.b4a.keywords.Common.DateTime.GetMinute(anywheresoftware.b4a.keywords.Common.DateTime.getNow()),(int) (2),(int) (0));
 //BA.debugLineNum = 206;BA.debugLine="If dospuntos Then";
if (_dospuntos) { 
 //BA.debugLineNum = 207;BA.debugLine="lb_hora.Text = H & \" : \" & M";
mostCurrent._lb_hora.setText(BA.ObjectToCharSequence(mostCurrent._h+" : "+mostCurrent._m));
 //BA.debugLineNum = 208;BA.debugLine="dospuntos=False";
_dospuntos = anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 210;BA.debugLine="lb_hora.Text = H & \"   \" & M";
mostCurrent._lb_hora.setText(BA.ObjectToCharSequence(mostCurrent._h+"   "+mostCurrent._m));
 //BA.debugLineNum = 211;BA.debugLine="dospuntos=True";
_dospuntos = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 229;BA.debugLine="End Sub";
return "";
}
public static String  _timer2_tick() throws Exception{
 //BA.debugLineNum = 231;BA.debugLine="Sub timer2_tick";
 //BA.debugLineNum = 232;BA.debugLine="Try";
try { //BA.debugLineNum = 234;BA.debugLine="fusion_sensores(angulo_Acce, 0)";
_fusion_sensores((float)(Double.parseDouble(mostCurrent._angulo_acce)),(float) (0));
 } 
       catch (Exception e4) {
			processBA.setLastException(e4); //BA.debugLineNum = 236;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("3720901",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 238;BA.debugLine="End Sub";
return "";
}
}
