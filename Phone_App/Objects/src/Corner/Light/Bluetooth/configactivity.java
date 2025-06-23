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

public class configactivity extends Activity implements B4AActivity{
	public static configactivity mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "Corner.Light.Bluetooth", "Corner.Light.Bluetooth.configactivity");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (configactivity).");
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
		activityBA = new BA(this, layout, processBA, "Corner.Light.Bluetooth", "Corner.Light.Bluetooth.configactivity");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "Corner.Light.Bluetooth.configactivity", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (configactivity) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (configactivity) Resume **");
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
		return configactivity.class;
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
            BA.LogInfo("** Activity (configactivity) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (configactivity) Pause event (activity is not paused). **");
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
            configactivity mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (configactivity) Resume **");
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
public static int _yaw = 0;
public static int _gyro_esc = 0;
public anywheresoftware.b4a.objects.SeekBarWrapper _see_sencibilidad = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _see_angulo = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb_sencibilidad = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb_ang_disparo = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tb_modo = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tb_eje = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tb_invertir = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb_delay = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _see_imu = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _see_pasa_bajos = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb_pasa_bajos = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tb_invertir_yaw = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrollview1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pn_contenedor = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _see_yaw = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb_yaw = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_guardarcambios = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _see_gyro_escala = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb_gyro_escala = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _see_angulo2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb_ang_apagado = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txt_nombrebt = null;
public b4a.example.dateutils _dateutils = null;
public Corner.Light.Bluetooth.main _main = null;
public Corner.Light.Bluetooth.chatactivity _chatactivity = null;
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
public static class _perfil_ruta{
public boolean IsInitialized;
public int[] Valores;
public void Initialize() {
IsInitialized = true;
Valores = new int[(int) (7)];
;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _perfil_urbano{
public boolean IsInitialized;
public int[] Valores;
public void Initialize() {
IsInitialized = true;
Valores = new int[(int) (7)];
;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 49;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 51;BA.debugLine="Activity.LoadLayout(\"Config\")";
mostCurrent._activity.LoadLayout("Config",mostCurrent.activityBA);
 //BA.debugLineNum = 52;BA.debugLine="ScrollView1.Panel.LoadLayout(\"3\")";
mostCurrent._scrollview1.getPanel().LoadLayout("3",mostCurrent.activityBA);
 //BA.debugLineNum = 53;BA.debugLine="ScrollView1.Panel.Height = pn_contenedor.Height";
mostCurrent._scrollview1.getPanel().setHeight(mostCurrent._pn_contenedor.getHeight());
 //BA.debugLineNum = 55;BA.debugLine="Activity.AddMenuItem( \"Diagnostico del Sistema\",";
mostCurrent._activity.AddMenuItem(BA.ObjectToCharSequence("Diagnostico del Sistema"),"mnuDiagnostico");
 //BA.debugLineNum = 56;BA.debugLine="Activity.AddMenuItem( \"Recupear Perfiles\", \"mnuRe";
mostCurrent._activity.AddMenuItem(BA.ObjectToCharSequence("Recupear Perfiles"),"mnuRecuperarPerfil");
 //BA.debugLineNum = 57;BA.debugLine="Activity.AddMenuItem( \"Guardar Perfil\", \"mnuGuard";
mostCurrent._activity.AddMenuItem(BA.ObjectToCharSequence("Guardar Perfil"),"mnuGuardarPerfil");
 //BA.debugLineNum = 58;BA.debugLine="Activity.AddMenuItem( \"Cancelar\", \"mnuVolver\")";
mostCurrent._activity.AddMenuItem(BA.ObjectToCharSequence("Cancelar"),"mnuVolver");
 //BA.debugLineNum = 59;BA.debugLine="Activity.Title=\"CONFIGURACION DEL SISTEMA\"";
mostCurrent._activity.setTitle(BA.ObjectToCharSequence("CONFIGURACION DEL SISTEMA"));
 //BA.debugLineNum = 61;BA.debugLine="see_sencibilidad.Value = ArduinoMap((ChatActivity";
mostCurrent._see_sencibilidad.setValue((int) (_arduinomap((int) ((mostCurrent._chatactivity._filtro /*float*/ )),(int) (9500),(int) (9999),(long) (0),(long) (100))));
 //BA.debugLineNum = 63;BA.debugLine="see_angulo.Value = ChatActivity.Ang_Limite*2";
mostCurrent._see_angulo.setValue((int) (mostCurrent._chatactivity._ang_limite /*float*/ *2));
 //BA.debugLineNum = 64;BA.debugLine="see_angulo2.Value = ChatActivity.Ang_Limite2*2";
mostCurrent._see_angulo2.setValue((int) (mostCurrent._chatactivity._ang_limite2 /*float*/ *2));
 //BA.debugLineNum = 66;BA.debugLine="see_imu.Value= ChatActivity.Imu-2";
mostCurrent._see_imu.setValue((int) (mostCurrent._chatactivity._imu /*int*/ -2));
 //BA.debugLineNum = 68;BA.debugLine="see_pasa_bajos.Value= 10-(ChatActivity.pasa_b *50";
mostCurrent._see_pasa_bajos.setValue((int) (10-(mostCurrent._chatactivity._pasa_b /*float*/ *50)));
 //BA.debugLineNum = 69;BA.debugLine="lb_pasa_bajos.Text = 10-(ChatActivity.pasa_b *50)";
mostCurrent._lb_pasa_bajos.setText(BA.ObjectToCharSequence(10-(mostCurrent._chatactivity._pasa_b /*float*/ *50)));
 //BA.debugLineNum = 72;BA.debugLine="If ChatActivity.modo = 1 Then";
if (mostCurrent._chatactivity._modo /*int*/ ==1) { 
 //BA.debugLineNum = 73;BA.debugLine="tb_modo.Checked=True";
mostCurrent._tb_modo.setChecked(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 75;BA.debugLine="If ChatActivity.eje = 1 Then";
if (mostCurrent._chatactivity._eje /*int*/ ==1) { 
 //BA.debugLineNum = 76;BA.debugLine="tb_eje.Checked=True";
mostCurrent._tb_eje.setChecked(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 78;BA.debugLine="If ChatActivity.invertir= 1 Then";
if (mostCurrent._chatactivity._invertir /*int*/ ==1) { 
 //BA.debugLineNum = 79;BA.debugLine="tb_invertir.Checked=True";
mostCurrent._tb_invertir.setChecked(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 81;BA.debugLine="tb_invertir.Checked=False";
mostCurrent._tb_invertir.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 84;BA.debugLine="lb_sencibilidad.Text = see_sencibilidad.Value";
mostCurrent._lb_sencibilidad.setText(BA.ObjectToCharSequence(mostCurrent._see_sencibilidad.getValue()));
 //BA.debugLineNum = 85;BA.debugLine="lb_ang_disparo.Text = (see_angulo.Value / 2)";
mostCurrent._lb_ang_disparo.setText(BA.ObjectToCharSequence((mostCurrent._see_angulo.getValue()/(double)2)));
 //BA.debugLineNum = 86;BA.debugLine="ChatActivity.lim = see_angulo.Value";
mostCurrent._chatactivity._lim /*float*/  = (float) (mostCurrent._see_angulo.getValue());
 //BA.debugLineNum = 88;BA.debugLine="see_yaw.Value=Abs(ChatActivity.yaw)";
mostCurrent._see_yaw.setValue((int) (anywheresoftware.b4a.keywords.Common.Abs(mostCurrent._chatactivity._yaw /*int*/ )));
 //BA.debugLineNum = 89;BA.debugLine="lb_yaw.Text = Abs(ChatActivity.yaw)";
mostCurrent._lb_yaw.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Abs(mostCurrent._chatactivity._yaw /*int*/ )));
 //BA.debugLineNum = 91;BA.debugLine="see_gyro_escala.Value = ChatActivity.gyro_esc - 1";
mostCurrent._see_gyro_escala.setValue((int) (mostCurrent._chatactivity._gyro_esc /*int*/ -1));
 //BA.debugLineNum = 92;BA.debugLine="lb_Gyro_escala.Text = ChatActivity.gyro_esc";
mostCurrent._lb_gyro_escala.setText(BA.ObjectToCharSequence(mostCurrent._chatactivity._gyro_esc /*int*/ ));
 //BA.debugLineNum = 94;BA.debugLine="If ChatActivity.yaw <= 0 Then";
if (mostCurrent._chatactivity._yaw /*int*/ <=0) { 
 //BA.debugLineNum = 95;BA.debugLine="tb_invertir_yaw.Checked=True";
mostCurrent._tb_invertir_yaw.setChecked(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 97;BA.debugLine="tb_invertir_yaw.Checked=False";
mostCurrent._tb_invertir_yaw.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 100;BA.debugLine="txt_nombreBT.Text 	 = ChatActivity.nombre_BT";
mostCurrent._txt_nombrebt.setText(BA.ObjectToCharSequence(mostCurrent._chatactivity._nombre_bt /*String*/ ));
 //BA.debugLineNum = 101;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 107;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 109;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 103;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 105;BA.debugLine="End Sub";
return "";
}
public static long  _arduinomap(int _x,int _in_min,int _in_max,long _out_min,long _out_max) throws Exception{
 //BA.debugLineNum = 45;BA.debugLine="Sub ArduinoMap(x As Int, in_min As Int, in_max As";
 //BA.debugLineNum = 46;BA.debugLine="Return (x - in_min) * (out_max - out_min) / (in_m";
if (true) return (long) ((_x-_in_min)*(_out_max-_out_min)/(double)(_in_max-_in_min)+_out_min);
 //BA.debugLineNum = 47;BA.debugLine="End Sub";
return 0L;
}
public static void  _btn_calibrar_longclick() throws Exception{
ResumableSub_btn_calibrar_LongClick rsub = new ResumableSub_btn_calibrar_LongClick(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btn_calibrar_LongClick extends BA.ResumableSub {
public ResumableSub_btn_calibrar_LongClick(Corner.Light.Bluetooth.configactivity parent) {
this.parent = parent;
}
Corner.Light.Bluetooth.configactivity parent;
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
 //BA.debugLineNum = 328;BA.debugLine="Dim mens As String";
_mens = "";
 //BA.debugLineNum = 329;BA.debugLine="mens = \"Esta por CALIBRAR el dispositivo. \"";
_mens = "Esta por CALIBRAR el dispositivo. ";
 //BA.debugLineNum = 331;BA.debugLine="Msgbox2Async(mens, \"Corner Light\", \"Sí\", \"\", \"No\"";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence(_mens),BA.ObjectToCharSequence("Corner Light"),"Sí","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 333;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 335;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 336;BA.debugLine="ProgressDialogShow2(\"Calibrando....\",False)";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Calibrando...."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 338;BA.debugLine="EnviarDatos(True,ChatActivity.perfil_usuario)";
_enviardatos(anywheresoftware.b4a.keywords.Common.True,parent.mostCurrent._chatactivity._perfil_usuario /*int*/ );
 //BA.debugLineNum = 339;BA.debugLine="Sleep((1000 * see_imu.Value))";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) ((1000*parent.mostCurrent._see_imu.getValue())));
this.state = 6;
return;
case 6:
//C
this.state = 4;
;
 //BA.debugLineNum = 340;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 342;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 343;BA.debugLine="StartActivity(ChatActivity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._chatactivity.getObject()));
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 345;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static String  _btn_guardarcambios_longclick() throws Exception{
 //BA.debugLineNum = 360;BA.debugLine="Private Sub btn_GuardarCambios_LongClick";
 //BA.debugLineNum = 362;BA.debugLine="EnviarDatos(False,ChatActivity.perfil_usuario)";
_enviardatos(anywheresoftware.b4a.keywords.Common.False,mostCurrent._chatactivity._perfil_usuario /*int*/ );
 //BA.debugLineNum = 363;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 364;BA.debugLine="StartActivity(ChatActivity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._chatactivity.getObject()));
 //BA.debugLineNum = 365;BA.debugLine="End Sub";
return "";
}
public static String  _enviardatos(boolean _calib,int _perfil) throws Exception{
String _cadena = "";
int _imudelay = 0;
float _pasa_bajos = 0f;
int _filtro_compl = 0;
 //BA.debugLineNum = 116;BA.debugLine="private Sub EnviarDatos (Calib As Boolean, Perfil";
 //BA.debugLineNum = 121;BA.debugLine="Dim cadena As String";
_cadena = "";
 //BA.debugLineNum = 122;BA.debugLine="Dim ImuDelay As Int";
_imudelay = 0;
 //BA.debugLineNum = 123;BA.debugLine="Dim pasa_bajos As Float";
_pasa_bajos = 0f;
 //BA.debugLineNum = 124;BA.debugLine="Dim filtro_compl As Int";
_filtro_compl = 0;
 //BA.debugLineNum = 126;BA.debugLine="filtro_compl = see_sencibilidad.Value+1";
_filtro_compl = (int) (mostCurrent._see_sencibilidad.getValue()+1);
 //BA.debugLineNum = 127;BA.debugLine="ImuDelay = lb_delay.Text";
_imudelay = (int)(Double.parseDouble(mostCurrent._lb_delay.getText()));
 //BA.debugLineNum = 128;BA.debugLine="pasa_bajos = 11 - lb_pasa_bajos.Text";
_pasa_bajos = (float) (11-(double)(Double.parseDouble(mostCurrent._lb_pasa_bajos.getText())));
 //BA.debugLineNum = 131;BA.debugLine="cadena = \"CL,\" & filtro_compl & \",\" & lb_ang_disp";
_cadena = "CL,"+BA.NumberToString(_filtro_compl)+","+mostCurrent._lb_ang_disparo.getText()+",20,20";
 //BA.debugLineNum = 133;BA.debugLine="If tb_eje.Checked=True Then";
if (mostCurrent._tb_eje.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 134;BA.debugLine="cadena = cadena & \",1\"";
_cadena = _cadena+",1";
 }else {
 //BA.debugLineNum = 136;BA.debugLine="cadena = cadena & \",2\"";
_cadena = _cadena+",2";
 };
 //BA.debugLineNum = 139;BA.debugLine="If tb_invertir.Checked=True Then";
if (mostCurrent._tb_invertir.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 140;BA.debugLine="cadena = cadena & \",1\"";
_cadena = _cadena+",1";
 }else {
 //BA.debugLineNum = 142;BA.debugLine="cadena = cadena & \",0\"";
_cadena = _cadena+",0";
 };
 //BA.debugLineNum = 145;BA.debugLine="If tb_modo.Checked=True Then";
if (mostCurrent._tb_modo.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 146;BA.debugLine="cadena = cadena & \",1\"";
_cadena = _cadena+",1";
 }else {
 //BA.debugLineNum = 148;BA.debugLine="cadena = cadena & \",0\"";
_cadena = _cadena+",0";
 };
 //BA.debugLineNum = 151;BA.debugLine="If tb_invertir_yaw.Checked=True Then";
if (mostCurrent._tb_invertir_yaw.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 152;BA.debugLine="cadena = cadena & \",1\"";
_cadena = _cadena+",1";
 }else {
 //BA.debugLineNum = 154;BA.debugLine="cadena = cadena & \",0\"";
_cadena = _cadena+",0";
 };
 //BA.debugLineNum = 157;BA.debugLine="cadena = cadena & \",\" & ImuDelay";
_cadena = _cadena+","+BA.NumberToString(_imudelay);
 //BA.debugLineNum = 158;BA.debugLine="cadena = cadena & \",\" & (pasa_bajos/50)";
_cadena = _cadena+","+BA.NumberToString((_pasa_bajos/(double)50));
 //BA.debugLineNum = 159;BA.debugLine="cadena = cadena & \",\" & (yaw)";
_cadena = _cadena+","+BA.NumberToString((_yaw));
 //BA.debugLineNum = 160;BA.debugLine="cadena = cadena & \",\" & (gyro_esc)";
_cadena = _cadena+","+BA.NumberToString((_gyro_esc));
 //BA.debugLineNum = 161;BA.debugLine="cadena = cadena & \",\" & (lb_ang_apagado.Text)";
_cadena = _cadena+","+(mostCurrent._lb_ang_apagado.getText());
 //BA.debugLineNum = 162;BA.debugLine="cadena = cadena & \",\" & Perfil";
_cadena = _cadena+","+BA.NumberToString(_perfil);
 //BA.debugLineNum = 163;BA.debugLine="cadena = cadena & \",\" & txt_nombreBT.Text";
_cadena = _cadena+","+mostCurrent._txt_nombrebt.getText();
 //BA.debugLineNum = 165;BA.debugLine="If Calib = True Then";
if (_calib==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 166;BA.debugLine="cadena = \"CALIB\"";
_cadena = "CALIB";
 };
 //BA.debugLineNum = 169;BA.debugLine="Starter.Manager.SendMessage(cadena)";
mostCurrent._starter._manager /*Corner.Light.Bluetooth.bluetoothmanager*/ ._sendmessage /*String*/ (_cadena);
 //BA.debugLineNum = 170;BA.debugLine="Log(cadena)";
anywheresoftware.b4a.keywords.Common.LogImpl("33932214",_cadena,0);
 //BA.debugLineNum = 171;BA.debugLine="ToastMessageShow(\"Enviando Datos y Guardandolos..";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Enviando Datos y Guardandolos...."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 172;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 17;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 20;BA.debugLine="Private see_sencibilidad As SeekBar";
mostCurrent._see_sencibilidad = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private see_angulo As SeekBar";
mostCurrent._see_angulo = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private lb_sencibilidad As Label";
mostCurrent._lb_sencibilidad = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private lb_ang_disparo As Label";
mostCurrent._lb_ang_disparo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private tb_modo As ToggleButton";
mostCurrent._tb_modo = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private tb_eje As ToggleButton";
mostCurrent._tb_eje = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private tb_invertir As ToggleButton";
mostCurrent._tb_invertir = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private lb_delay As Label";
mostCurrent._lb_delay = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private see_imu As SeekBar";
mostCurrent._see_imu = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private see_pasa_bajos As SeekBar";
mostCurrent._see_pasa_bajos = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private lb_pasa_bajos As Label";
mostCurrent._lb_pasa_bajos = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private tb_invertir_yaw As ToggleButton";
mostCurrent._tb_invertir_yaw = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private ScrollView1 As ScrollView";
mostCurrent._scrollview1 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private pn_contenedor As Panel";
mostCurrent._pn_contenedor = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private see_yaw As SeekBar";
mostCurrent._see_yaw = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private lb_yaw As Label";
mostCurrent._lb_yaw = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private btn_GuardarCambios As Button";
mostCurrent._btn_guardarcambios = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private see_gyro_escala As SeekBar";
mostCurrent._see_gyro_escala = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private lb_Gyro_escala As Label";
mostCurrent._lb_gyro_escala = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private see_angulo2 As SeekBar";
mostCurrent._see_angulo2 = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private lb_ang_apagado As Label";
mostCurrent._lb_ang_apagado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private txt_nombreBT As EditText";
mostCurrent._txt_nombrebt = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 43;BA.debugLine="End Sub";
return "";
}
public static String  _mnudiagnostico_click() throws Exception{
 //BA.debugLineNum = 174;BA.debugLine="Private Sub mnuDiagnostico_Click()";
 //BA.debugLineNum = 175;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 176;BA.debugLine="StartActivity(DiagnosticoActivity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._diagnosticoactivity.getObject()));
 //BA.debugLineNum = 177;BA.debugLine="End Sub";
return "";
}
public static void  _mnuguardarperfil_click() throws Exception{
ResumableSub_mnuGuardarPerfil_click rsub = new ResumableSub_mnuGuardarPerfil_click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_mnuGuardarPerfil_click extends BA.ResumableSub {
public ResumableSub_mnuGuardarPerfil_click(Corner.Light.Bluetooth.configactivity parent) {
this.parent = parent;
}
Corner.Light.Bluetooth.configactivity parent;
anywheresoftware.b4a.randomaccessfile.RandomAccessFile _rm1 = null;
anywheresoftware.b4a.randomaccessfile.RandomAccessFile _rm2 = null;
Corner.Light.Bluetooth.configactivity._perfil_ruta _perfil1 = null;
Corner.Light.Bluetooth.configactivity._perfil_urbano _perfil2 = null;
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
 //BA.debugLineNum = 240;BA.debugLine="Dim rm1 As RandomAccessFile";
_rm1 = new anywheresoftware.b4a.randomaccessfile.RandomAccessFile();
 //BA.debugLineNum = 241;BA.debugLine="Dim rm2 As RandomAccessFile";
_rm2 = new anywheresoftware.b4a.randomaccessfile.RandomAccessFile();
 //BA.debugLineNum = 242;BA.debugLine="Dim perfil1 As Perfil_Ruta";
_perfil1 = new Corner.Light.Bluetooth.configactivity._perfil_ruta();
 //BA.debugLineNum = 243;BA.debugLine="Dim perfil2 As Perfil_Urbano";
_perfil2 = new Corner.Light.Bluetooth.configactivity._perfil_urbano();
 //BA.debugLineNum = 245;BA.debugLine="perfil1.Initialize";
_perfil1.Initialize();
 //BA.debugLineNum = 246;BA.debugLine="perfil2.Initialize";
_perfil2.Initialize();
 //BA.debugLineNum = 248;BA.debugLine="rm1.Initialize(File.DirInternal,\"perfilRuta.conf\"";
_rm1.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"perfilRuta.conf",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 249;BA.debugLine="rm2.Initialize(File.DirInternal,\"perfilUrbano.con";
_rm2.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"perfilUrbano.conf",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 251;BA.debugLine="Msgbox2Async(\"Elija PERFIL....\", \"Corner Light\",";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Elija PERFIL...."),BA.ObjectToCharSequence("Corner Light"),"URBANO","CACENLAR","RUTA",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 252;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 9;
return;
case 9:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 253;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 1:
//if
this.state = 8;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 3;
}else if(_result==anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL) { 
this.state = 5;
}else if(_result==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
this.state = 7;
}if (true) break;

case 3:
//C
this.state = 8;
 //BA.debugLineNum = 255;BA.debugLine="perfil2.Valores(0) 	= see_sencibilidad.Value";
_perfil2.Valores /*int[]*/ [(int) (0)] = parent.mostCurrent._see_sencibilidad.getValue();
 //BA.debugLineNum = 256;BA.debugLine="perfil2.Valores(1) 	= see_pasa_bajos.Value";
_perfil2.Valores /*int[]*/ [(int) (1)] = parent.mostCurrent._see_pasa_bajos.getValue();
 //BA.debugLineNum = 257;BA.debugLine="perfil2.Valores(2) 	= see_angulo.Value";
_perfil2.Valores /*int[]*/ [(int) (2)] = parent.mostCurrent._see_angulo.getValue();
 //BA.debugLineNum = 258;BA.debugLine="perfil2.Valores(3) 	= see_angulo2.Value";
_perfil2.Valores /*int[]*/ [(int) (3)] = parent.mostCurrent._see_angulo2.getValue();
 //BA.debugLineNum = 259;BA.debugLine="perfil2.Valores(4) 	= see_imu.Value";
_perfil2.Valores /*int[]*/ [(int) (4)] = parent.mostCurrent._see_imu.getValue();
 //BA.debugLineNum = 260;BA.debugLine="perfil2.Valores(5) 	= see_yaw.Value";
_perfil2.Valores /*int[]*/ [(int) (5)] = parent.mostCurrent._see_yaw.getValue();
 //BA.debugLineNum = 261;BA.debugLine="perfil2.Valores(6) 	= see_gyro_escala.Value";
_perfil2.Valores /*int[]*/ [(int) (6)] = parent.mostCurrent._see_gyro_escala.getValue();
 //BA.debugLineNum = 263;BA.debugLine="ChatActivity.perfil_usuario	= 1";
parent.mostCurrent._chatactivity._perfil_usuario /*int*/  = (int) (1);
 //BA.debugLineNum = 264;BA.debugLine="ChatActivity.Perfil 		= \"URBANO\"";
parent.mostCurrent._chatactivity._perfil /*String*/  = "URBANO";
 //BA.debugLineNum = 266;BA.debugLine="rm2.WriteEncryptedObject(perfil2,\"123\",0)";
_rm2.WriteEncryptedObject((Object)(_perfil2),"123",(long) (0));
 //BA.debugLineNum = 268;BA.debugLine="ToastMessageShow(\"Perfil URBANO guardado correct";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Perfil URBANO guardado correctamente...."),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 5:
//C
this.state = 8;
 //BA.debugLineNum = 270;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 7:
//C
this.state = 8;
 //BA.debugLineNum = 273;BA.debugLine="perfil1.Valores(0)  = see_sencibilidad.Value";
_perfil1.Valores /*int[]*/ [(int) (0)] = parent.mostCurrent._see_sencibilidad.getValue();
 //BA.debugLineNum = 274;BA.debugLine="perfil1.Valores(1)  = see_pasa_bajos.Value";
_perfil1.Valores /*int[]*/ [(int) (1)] = parent.mostCurrent._see_pasa_bajos.getValue();
 //BA.debugLineNum = 275;BA.debugLine="perfil1.Valores(2)  = see_angulo.Value";
_perfil1.Valores /*int[]*/ [(int) (2)] = parent.mostCurrent._see_angulo.getValue();
 //BA.debugLineNum = 276;BA.debugLine="perfil1.Valores(3)  = see_angulo2.Value";
_perfil1.Valores /*int[]*/ [(int) (3)] = parent.mostCurrent._see_angulo2.getValue();
 //BA.debugLineNum = 277;BA.debugLine="perfil1.Valores(4)  = see_imu.Value";
_perfil1.Valores /*int[]*/ [(int) (4)] = parent.mostCurrent._see_imu.getValue();
 //BA.debugLineNum = 278;BA.debugLine="perfil1.Valores(5)  = see_yaw.Value";
_perfil1.Valores /*int[]*/ [(int) (5)] = parent.mostCurrent._see_yaw.getValue();
 //BA.debugLineNum = 279;BA.debugLine="perfil1.Valores(6)  = see_gyro_escala.Value";
_perfil1.Valores /*int[]*/ [(int) (6)] = parent.mostCurrent._see_gyro_escala.getValue();
 //BA.debugLineNum = 281;BA.debugLine="ChatActivity.perfil_usuario	= 2";
parent.mostCurrent._chatactivity._perfil_usuario /*int*/  = (int) (2);
 //BA.debugLineNum = 282;BA.debugLine="ChatActivity.Perfil 		= \"RUTA\"";
parent.mostCurrent._chatactivity._perfil /*String*/  = "RUTA";
 //BA.debugLineNum = 284;BA.debugLine="rm1.WriteEncryptedObject(perfil1,\"123\",0)";
_rm1.WriteEncryptedObject((Object)(_perfil1),"123",(long) (0));
 //BA.debugLineNum = 286;BA.debugLine="ToastMessageShow(\"Perfil RUTA guardado correctam";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Perfil RUTA guardado correctamente...."),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 8:
//C
this.state = -1;
;
 //BA.debugLineNum = 288;BA.debugLine="rm1.Close";
_rm1.Close();
 //BA.debugLineNum = 289;BA.debugLine="rm2.Close";
_rm2.Close();
 //BA.debugLineNum = 291;BA.debugLine="EnviarDatos(False,ChatActivity.perfil_usuario)";
_enviardatos(anywheresoftware.b4a.keywords.Common.False,parent.mostCurrent._chatactivity._perfil_usuario /*int*/ );
 //BA.debugLineNum = 293;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 294;BA.debugLine="StartActivity(ChatActivity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._chatactivity.getObject()));
 //BA.debugLineNum = 295;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _mnurecuperarperfil_click() throws Exception{
ResumableSub_mnuRecuperarPerfil_click rsub = new ResumableSub_mnuRecuperarPerfil_click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_mnuRecuperarPerfil_click extends BA.ResumableSub {
public ResumableSub_mnuRecuperarPerfil_click(Corner.Light.Bluetooth.configactivity parent) {
this.parent = parent;
}
Corner.Light.Bluetooth.configactivity parent;
int _result = 0;
anywheresoftware.b4a.randomaccessfile.RandomAccessFile _rm1 = null;
anywheresoftware.b4a.randomaccessfile.RandomAccessFile _rm2 = null;
Corner.Light.Bluetooth.configactivity._perfil_ruta _perfil1 = null;
Corner.Light.Bluetooth.configactivity._perfil_urbano _perfil2 = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 180;BA.debugLine="Msgbox2Async(\"Elija PERFIL....\", \"Corner Light\",";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Elija PERFIL...."),BA.ObjectToCharSequence("Corner Light"),"URBANO","CACENLAR","RUTA",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 181;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 13;
return;
case 13:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 183;BA.debugLine="Dim rm1 As RandomAccessFile";
_rm1 = new anywheresoftware.b4a.randomaccessfile.RandomAccessFile();
 //BA.debugLineNum = 184;BA.debugLine="Dim rm2 As RandomAccessFile";
_rm2 = new anywheresoftware.b4a.randomaccessfile.RandomAccessFile();
 //BA.debugLineNum = 186;BA.debugLine="Dim perfil1 As Perfil_Ruta";
_perfil1 = new Corner.Light.Bluetooth.configactivity._perfil_ruta();
 //BA.debugLineNum = 187;BA.debugLine="Dim perfil2 As Perfil_Urbano";
_perfil2 = new Corner.Light.Bluetooth.configactivity._perfil_urbano();
 //BA.debugLineNum = 189;BA.debugLine="rm1.Initialize(File.DirInternal,\"perfilRuta.conf\"";
_rm1.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"perfilRuta.conf",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 190;BA.debugLine="rm2.Initialize(File.DirInternal,\"perfilUrbano.con";
_rm2.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"perfilUrbano.conf",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 192;BA.debugLine="perfil1 = rm1.ReadEncryptedObject(\"123\",0)";
_perfil1 = (Corner.Light.Bluetooth.configactivity._perfil_ruta)(_rm1.ReadEncryptedObject("123",(long) (0)));
 //BA.debugLineNum = 193;BA.debugLine="perfil2 = rm2.ReadEncryptedObject(\"123\",0)";
_perfil2 = (Corner.Light.Bluetooth.configactivity._perfil_urbano)(_rm2.ReadEncryptedObject("123",(long) (0)));
 //BA.debugLineNum = 195;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 1:
//if
this.state = 8;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 3;
}else if(_result==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
this.state = 5;
}else {
this.state = 7;
}if (true) break;

case 3:
//C
this.state = 8;
 //BA.debugLineNum = 196;BA.debugLine="see_sencibilidad.Value	= perfil2.Valores(0)";
parent.mostCurrent._see_sencibilidad.setValue(_perfil2.Valores /*int[]*/ [(int) (0)]);
 //BA.debugLineNum = 197;BA.debugLine="see_pasa_bajos.Value	= perfil2.Valores(1)";
parent.mostCurrent._see_pasa_bajos.setValue(_perfil2.Valores /*int[]*/ [(int) (1)]);
 //BA.debugLineNum = 198;BA.debugLine="see_angulo.Value 		= perfil2.Valores(2)";
parent.mostCurrent._see_angulo.setValue(_perfil2.Valores /*int[]*/ [(int) (2)]);
 //BA.debugLineNum = 199;BA.debugLine="see_angulo2.Value 		= perfil2.Valores(3)";
parent.mostCurrent._see_angulo2.setValue(_perfil2.Valores /*int[]*/ [(int) (3)]);
 //BA.debugLineNum = 200;BA.debugLine="see_imu.Value 			= perfil2.Valores(4)";
parent.mostCurrent._see_imu.setValue(_perfil2.Valores /*int[]*/ [(int) (4)]);
 //BA.debugLineNum = 201;BA.debugLine="see_yaw.Value 			= perfil2.Valores(5)";
parent.mostCurrent._see_yaw.setValue(_perfil2.Valores /*int[]*/ [(int) (5)]);
 //BA.debugLineNum = 202;BA.debugLine="see_gyro_escala.Value 	= perfil2.Valores(6)";
parent.mostCurrent._see_gyro_escala.setValue(_perfil2.Valores /*int[]*/ [(int) (6)]);
 //BA.debugLineNum = 204;BA.debugLine="ChatActivity.perfil_usuario	= 1";
parent.mostCurrent._chatactivity._perfil_usuario /*int*/  = (int) (1);
 //BA.debugLineNum = 205;BA.debugLine="ChatActivity.Perfil 		= \"URBANO\"";
parent.mostCurrent._chatactivity._perfil /*String*/  = "URBANO";
 //BA.debugLineNum = 207;BA.debugLine="Log(\"URBANO activo / \" & see_yaw.Value)";
anywheresoftware.b4a.keywords.Common.LogImpl("34063260","URBANO activo / "+BA.NumberToString(parent.mostCurrent._see_yaw.getValue()),0);
 if (true) break;

case 5:
//C
this.state = 8;
 //BA.debugLineNum = 209;BA.debugLine="see_sencibilidad.Value 	= perfil1.Valores(0)";
parent.mostCurrent._see_sencibilidad.setValue(_perfil1.Valores /*int[]*/ [(int) (0)]);
 //BA.debugLineNum = 210;BA.debugLine="see_pasa_bajos.Value	= perfil1.Valores(1)";
parent.mostCurrent._see_pasa_bajos.setValue(_perfil1.Valores /*int[]*/ [(int) (1)]);
 //BA.debugLineNum = 211;BA.debugLine="see_angulo.Value 		= perfil1.Valores(2)";
parent.mostCurrent._see_angulo.setValue(_perfil1.Valores /*int[]*/ [(int) (2)]);
 //BA.debugLineNum = 212;BA.debugLine="see_angulo2.Value		= perfil1.Valores(3)";
parent.mostCurrent._see_angulo2.setValue(_perfil1.Valores /*int[]*/ [(int) (3)]);
 //BA.debugLineNum = 213;BA.debugLine="see_imu.Value 			= perfil1.Valores(4)";
parent.mostCurrent._see_imu.setValue(_perfil1.Valores /*int[]*/ [(int) (4)]);
 //BA.debugLineNum = 214;BA.debugLine="see_yaw.Value 			= perfil1.Valores(5)";
parent.mostCurrent._see_yaw.setValue(_perfil1.Valores /*int[]*/ [(int) (5)]);
 //BA.debugLineNum = 215;BA.debugLine="see_gyro_escala.Value 	= perfil1.Valores(6)";
parent.mostCurrent._see_gyro_escala.setValue(_perfil1.Valores /*int[]*/ [(int) (6)]);
 //BA.debugLineNum = 217;BA.debugLine="ChatActivity.perfil_usuario	= 2";
parent.mostCurrent._chatactivity._perfil_usuario /*int*/  = (int) (2);
 //BA.debugLineNum = 218;BA.debugLine="ChatActivity.Perfil 		= \"RUTA\"";
parent.mostCurrent._chatactivity._perfil /*String*/  = "RUTA";
 //BA.debugLineNum = 220;BA.debugLine="Log(\"RUTA activo / \" & ChatActivity.perfil_usuar";
anywheresoftware.b4a.keywords.Common.LogImpl("34063273","RUTA activo / "+BA.NumberToString(parent.mostCurrent._chatactivity._perfil_usuario /*int*/ ),0);
 if (true) break;

case 7:
//C
this.state = 8;
 //BA.debugLineNum = 222;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 8:
//C
this.state = 9;
;
 //BA.debugLineNum = 225;BA.debugLine="rm1.Close";
_rm1.Close();
 //BA.debugLineNum = 226;BA.debugLine="rm2.Close";
_rm2.Close();
 //BA.debugLineNum = 228;BA.debugLine="Msgbox2Async(\"Enviar datos a la ECU?\", \"Corner Li";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Enviar datos a la ECU?"),BA.ObjectToCharSequence("Corner Light"),"SI","","NO",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 229;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 14;
return;
case 14:
//C
this.state = 9;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 231;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 9:
//if
this.state = 12;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 11;
}if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 232;BA.debugLine="EnviarDatos(False,ChatActivity.perfil_usuario)";
_enviardatos(anywheresoftware.b4a.keywords.Common.False,parent.mostCurrent._chatactivity._perfil_usuario /*int*/ );
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 235;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 236;BA.debugLine="StartActivity(ChatActivity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._chatactivity.getObject()));
 //BA.debugLineNum = 237;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _mnuvolver_click() throws Exception{
 //BA.debugLineNum = 111;BA.debugLine="Private Sub mnuVolver_Click";
 //BA.debugLineNum = 112;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 113;BA.debugLine="StartActivity(ChatActivity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._chatactivity.getObject()));
 //BA.debugLineNum = 114;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="Type Perfil_Ruta(Valores(7) As Int)";
;
 //BA.debugLineNum = 11;BA.debugLine="Type Perfil_Urbano(Valores(7) As Int)";
;
 //BA.debugLineNum = 13;BA.debugLine="Dim yaw As Int";
_yaw = 0;
 //BA.debugLineNum = 14;BA.debugLine="Dim gyro_esc As Int";
_gyro_esc = 0;
 //BA.debugLineNum = 15;BA.debugLine="End Sub";
return "";
}
public static String  _see_angulo_valuechanged(int _value,boolean _userchanged) throws Exception{
float _val = 0f;
 //BA.debugLineNum = 301;BA.debugLine="Private Sub see_angulo_ValueChanged (Value As Int,";
 //BA.debugLineNum = 302;BA.debugLine="Private val As Float";
_val = 0f;
 //BA.debugLineNum = 303;BA.debugLine="val = Value /2";
_val = (float) (_value/(double)2);
 //BA.debugLineNum = 305;BA.debugLine="lb_ang_disparo.Text 	= val";
mostCurrent._lb_ang_disparo.setText(BA.ObjectToCharSequence(_val));
 //BA.debugLineNum = 306;BA.debugLine="ChatActivity.lim 		= val";
mostCurrent._chatactivity._lim /*float*/  = _val;
 //BA.debugLineNum = 307;BA.debugLine="ChatActivity.Ang_Limite = val";
mostCurrent._chatactivity._ang_limite /*float*/  = _val;
 //BA.debugLineNum = 309;BA.debugLine="If see_angulo2.Value > see_angulo.Value Then";
if (mostCurrent._see_angulo2.getValue()>mostCurrent._see_angulo.getValue()) { 
 //BA.debugLineNum = 310;BA.debugLine="see_angulo2.Value = see_angulo.Value-1";
mostCurrent._see_angulo2.setValue((int) (mostCurrent._see_angulo.getValue()-1));
 };
 //BA.debugLineNum = 312;BA.debugLine="End Sub";
return "";
}
public static String  _see_angulo2_valuechanged(int _value,boolean _userchanged) throws Exception{
float _val = 0f;
 //BA.debugLineNum = 314;BA.debugLine="Private Sub see_angulo2_ValueChanged (Value As Int";
 //BA.debugLineNum = 315;BA.debugLine="Private val As Float";
_val = 0f;
 //BA.debugLineNum = 316;BA.debugLine="val = Value / 2";
_val = (float) (_value/(double)2);
 //BA.debugLineNum = 318;BA.debugLine="If val < lb_ang_disparo.Text Then";
if (_val<(double)(Double.parseDouble(mostCurrent._lb_ang_disparo.getText()))) { 
 //BA.debugLineNum = 319;BA.debugLine="lb_ang_apagado.Text		 = val";
mostCurrent._lb_ang_apagado.setText(BA.ObjectToCharSequence(_val));
 //BA.debugLineNum = 320;BA.debugLine="ChatActivity.lim2		 = val";
mostCurrent._chatactivity._lim2 /*float*/  = _val;
 //BA.debugLineNum = 321;BA.debugLine="ChatActivity.Ang_Limite2 = val";
mostCurrent._chatactivity._ang_limite2 /*float*/  = _val;
 }else {
 //BA.debugLineNum = 323;BA.debugLine="see_angulo2.Value= see_angulo.Value-1";
mostCurrent._see_angulo2.setValue((int) (mostCurrent._see_angulo.getValue()-1));
 };
 //BA.debugLineNum = 325;BA.debugLine="End Sub";
return "";
}
public static String  _see_gyro_escala_valuechanged(int _value,boolean _userchanged) throws Exception{
 //BA.debugLineNum = 367;BA.debugLine="Private Sub see_gyro_escala_ValueChanged (Value As";
 //BA.debugLineNum = 368;BA.debugLine="gyro_esc=Value+1";
_gyro_esc = (int) (_value+1);
 //BA.debugLineNum = 369;BA.debugLine="lb_Gyro_escala.Text = Value+1";
mostCurrent._lb_gyro_escala.setText(BA.ObjectToCharSequence(_value+1));
 //BA.debugLineNum = 370;BA.debugLine="End Sub";
return "";
}
public static String  _see_imu_valuechanged(int _value,boolean _userchanged) throws Exception{
 //BA.debugLineNum = 347;BA.debugLine="Private Sub see_imu_ValueChanged (Value As Int, Us";
 //BA.debugLineNum = 348;BA.debugLine="lb_delay.Text= Value+2";
mostCurrent._lb_delay.setText(BA.ObjectToCharSequence(_value+2));
 //BA.debugLineNum = 349;BA.debugLine="End Sub";
return "";
}
public static String  _see_pasa_bajos_valuechanged(int _value,boolean _userchanged) throws Exception{
 //BA.debugLineNum = 351;BA.debugLine="Private Sub see_pasa_bajos_ValueChanged (Value As";
 //BA.debugLineNum = 352;BA.debugLine="lb_pasa_bajos.Text = Value+1";
mostCurrent._lb_pasa_bajos.setText(BA.ObjectToCharSequence(_value+1));
 //BA.debugLineNum = 353;BA.debugLine="End Sub";
return "";
}
public static String  _see_sencibilidad_valuechanged(int _value,boolean _userchanged) throws Exception{
 //BA.debugLineNum = 297;BA.debugLine="Private Sub see_sencibilidad_ValueChanged (Value A";
 //BA.debugLineNum = 298;BA.debugLine="lb_sencibilidad.Text = Value +1";
mostCurrent._lb_sencibilidad.setText(BA.ObjectToCharSequence(_value+1));
 //BA.debugLineNum = 299;BA.debugLine="End Sub";
return "";
}
public static String  _see_yaw_valuechanged(int _value,boolean _userchanged) throws Exception{
 //BA.debugLineNum = 355;BA.debugLine="Private Sub see_yaw_ValueChanged (Value As Int, Us";
 //BA.debugLineNum = 356;BA.debugLine="yaw=Value";
_yaw = _value;
 //BA.debugLineNum = 357;BA.debugLine="lb_yaw.Text = Value";
mostCurrent._lb_yaw.setText(BA.ObjectToCharSequence(_value));
 //BA.debugLineNum = 358;BA.debugLine="End Sub";
return "";
}
}
