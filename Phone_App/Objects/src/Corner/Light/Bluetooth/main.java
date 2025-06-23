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

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "Corner.Light.Bluetooth", "Corner.Light.Bluetooth.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
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
		activityBA = new BA(this, layout, processBA, "Corner.Light.Bluetooth", "Corner.Light.Bluetooth.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "Corner.Light.Bluetooth.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
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
		return main.class;
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
            BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (main) Pause event (activity is not paused). **");
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
            main mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
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
public static anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
public static boolean _simulador = false;
public static Corner.Light.Bluetooth.bluetoothmanager._nameandmac _dispo = null;
public static anywheresoftware.b4a.objects.Serial _serial1 = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public Corner.Light.Bluetooth.asbuttonslider _asbuttonslider1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _img_activado = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_config = null;
public anywheresoftware.b4a.objects.PanelWrapper _pn_menu = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_simulador = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_buscar_dispo = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_olvidar_dispo = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _img_fondo = null;
public b4a.example.dateutils _dateutils = null;
public Corner.Light.Bluetooth.chatactivity _chatactivity = null;
public Corner.Light.Bluetooth.configactivity _configactivity = null;
public Corner.Light.Bluetooth.diagnosticoactivity _diagnosticoactivity = null;
public Corner.Light.Bluetooth.starter _starter = null;
public Corner.Light.Bluetooth.xuiviewsutils _xuiviewsutils = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (chatactivity.mostCurrent != null);
vis = vis | (configactivity.mostCurrent != null);
vis = vis | (diagnosticoactivity.mostCurrent != null);
return vis;}
public static class _configuracion{
public boolean IsInitialized;
public String Nombre_BT;
public String Mac_BT;
public void Initialize() {
IsInitialized = true;
Nombre_BT = "";
Mac_BT = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 37;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 38;BA.debugLine="Activity.LoadLayout(\"1\")";
mostCurrent._activity.LoadLayout("1",mostCurrent.activityBA);
 //BA.debugLineNum = 40;BA.debugLine="CreateLabel(ASButtonSlider1.SliderButtonPnl,Chr(0";
_createlabel(mostCurrent._asbuttonslider1._getsliderbuttonpnl /*anywheresoftware.b4a.objects.B4XViewWrapper*/ (),BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr(((int)0xf07e))),mostCurrent._xui.CreateFontAwesome((float) (30)));
 //BA.debugLineNum = 41;BA.debugLine="CreateLabel(ASButtonSlider1.LeftTopPnl,\"Salir\",xu";
_createlabel(mostCurrent._asbuttonslider1._getlefttoppnl /*anywheresoftware.b4a.objects.B4XViewWrapper*/ (),"Salir",mostCurrent._xui.CreateDefaultBoldFont((float) (30)));
 //BA.debugLineNum = 42;BA.debugLine="CreateLabel(ASButtonSlider1.RightBottomPnl,\"Incia";
_createlabel(mostCurrent._asbuttonslider1._getrightbottompnl /*anywheresoftware.b4a.objects.B4XViewWrapper*/ (),"Inciar",mostCurrent._xui.CreateDefaultBoldFont((float) (30)));
 //BA.debugLineNum = 44;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 45;BA.debugLine="pn_menu.Visible=False";
mostCurrent._pn_menu.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 46;BA.debugLine="Serial1.Initialize(\"Serial1\")";
_serial1.Initialize("Serial1");
 };
 //BA.debugLineNum = 52;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 244;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 245;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 246;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 };
 //BA.debugLineNum = 248;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 134;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 135;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 118;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 120;BA.debugLine="Simulador=False";
_simulador = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 121;BA.debugLine="If pn_menu.Visible=True Then";
if (mostCurrent._pn_menu.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 122;BA.debugLine="pn_menu.Visible=False";
mostCurrent._pn_menu.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 125;BA.debugLine="If img_activado.Visible=True Then";
if (mostCurrent._img_activado.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 126;BA.debugLine="img_activado.Visible=False";
mostCurrent._img_activado.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 128;BA.debugLine="End Sub";
return "";
}
public static String  _afterconnect(boolean _success) throws Exception{
 //BA.debugLineNum = 163;BA.debugLine="Public Sub AfterConnect (Success As Boolean)";
 //BA.debugLineNum = 164;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 165;BA.debugLine="End Sub";
return "";
}
public static String  _asbuttonslider1_reachedlefttop() throws Exception{
 //BA.debugLineNum = 171;BA.debugLine="Private Sub ASButtonSlider1_ReachedLeftTop";
 //BA.debugLineNum = 172;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 //BA.debugLineNum = 173;BA.debugLine="End Sub";
return "";
}
public static void  _asbuttonslider1_reachedrightbottom() throws Exception{
ResumableSub_ASButtonSlider1_ReachedRightBottom rsub = new ResumableSub_ASButtonSlider1_ReachedRightBottom(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_ASButtonSlider1_ReachedRightBottom extends BA.ResumableSub {
public ResumableSub_ASButtonSlider1_ReachedRightBottom(Corner.Light.Bluetooth.main parent) {
this.parent = parent;
}
Corner.Light.Bluetooth.main parent;
String _permission = "";
boolean _result = false;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 176;BA.debugLine="Simulador=False";
parent._simulador = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 178;BA.debugLine="rp.CheckAndRequest(rp.PERMISSION_ACCESS_COARSE_LO";
parent._rp.CheckAndRequest(processBA,parent._rp.PERMISSION_ACCESS_COARSE_LOCATION);
 //BA.debugLineNum = 179;BA.debugLine="Wait For Activity_PermissionResult (Permission As";
anywheresoftware.b4a.keywords.Common.WaitFor("activity_permissionresult", processBA, this, null);
this.state = 11;
return;
case 11:
//C
this.state = 1;
_permission = (String) result[0];
_result = (Boolean) result[1];
;
 //BA.debugLineNum = 180;BA.debugLine="If Result = False Then";
if (true) break;

case 1:
//if
this.state = 4;
if (_result==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 181;BA.debugLine="ToastMessageShow(\"Sin autorización...\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Sin autorización..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 182;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 4:
//C
this.state = 5;
;
 //BA.debugLineNum = 187;BA.debugLine="dispo = BuscoBTguardados(\"\",\"\")";
parent._dispo = _buscobtguardados("","");
 //BA.debugLineNum = 189;BA.debugLine="If dispo.Name <> \"\" Then";
if (true) break;

case 5:
//if
this.state = 10;
if ((parent._dispo.Name /*String*/ ).equals("") == false) { 
this.state = 7;
}else {
this.state = 9;
}if (true) break;

case 7:
//C
this.state = 10;
 //BA.debugLineNum = 190;BA.debugLine="Starter.Manager.ConnectTo(dispo)";
parent.mostCurrent._starter._manager /*Corner.Light.Bluetooth.bluetoothmanager*/ ._connectto /*String*/ (parent._dispo);
 //BA.debugLineNum = 191;BA.debugLine="ProgressDialogShow2(\"Conectando a...: \" & dispo.";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Conectando a...: "+parent._dispo.Name /*String*/ ),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 193;BA.debugLine="Msgbox(\"No hay dispositivos asociados\",\"Corner L";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("No hay dispositivos asociados"),BA.ObjectToCharSequence("Corner Light"),mostCurrent.activityBA);
 //BA.debugLineNum = 194;BA.debugLine="img_activado.Visible=False";
parent.mostCurrent._img_activado.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 196;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _activity_permissionresult(String _permission,boolean _result) throws Exception{
}
public static String  _btn_buscar_dispo_click() throws Exception{
 //BA.debugLineNum = 264;BA.debugLine="Private Sub btn_buscar_dispo_Click";
 //BA.debugLineNum = 265;BA.debugLine="men_Emparejados";
_men_emparejados();
 //BA.debugLineNum = 266;BA.debugLine="End Sub";
return "";
}
public static String  _btn_config_click() throws Exception{
 //BA.debugLineNum = 250;BA.debugLine="Private Sub btn_config_Click";
 //BA.debugLineNum = 251;BA.debugLine="If pn_menu.Visible=False Then";
if (mostCurrent._pn_menu.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 252;BA.debugLine="pn_menu.Visible=True";
mostCurrent._pn_menu.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 254;BA.debugLine="pn_menu.Visible=False";
mostCurrent._pn_menu.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 256;BA.debugLine="End Sub";
return "";
}
public static String  _btn_olvidar_dispo_click() throws Exception{
 //BA.debugLineNum = 268;BA.debugLine="Private Sub btn_olvidar_dispo_Click";
 //BA.debugLineNum = 269;BA.debugLine="men_desemparejar";
_men_desemparejar();
 //BA.debugLineNum = 270;BA.debugLine="End Sub";
return "";
}
public static String  _btn_simulador_click() throws Exception{
 //BA.debugLineNum = 258;BA.debugLine="Private Sub btn_simulador_Click";
 //BA.debugLineNum = 259;BA.debugLine="Simulador=True";
_simulador = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 261;BA.debugLine="StartActivity(ChatActivity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._chatactivity.getObject()));
 //BA.debugLineNum = 262;BA.debugLine="End Sub";
return "";
}
public static String  _btnallowconnection_click() throws Exception{
 //BA.debugLineNum = 167;BA.debugLine="Sub btnAllowConnection_Click";
 //BA.debugLineNum = 168;BA.debugLine="Starter.Manager.ListenForConnections";
mostCurrent._starter._manager /*Corner.Light.Bluetooth.bluetoothmanager*/ ._listenforconnections /*String*/ ();
 //BA.debugLineNum = 169;BA.debugLine="End Sub";
return "";
}
public static Corner.Light.Bluetooth.bluetoothmanager._nameandmac  _buscobtguardados(String _nom,String _mac) throws Exception{
Corner.Light.Bluetooth.bluetoothmanager._nameandmac _dispositivo = null;
anywheresoftware.b4a.randomaccessfile.RandomAccessFile _rm = null;
Corner.Light.Bluetooth.main._configuracion _conf = null;
 //BA.debugLineNum = 198;BA.debugLine="Private Sub BuscoBTguardados(nom As String,mac As";
 //BA.debugLineNum = 199;BA.debugLine="Dim dispositivo As NameAndMac";
_dispositivo = new Corner.Light.Bluetooth.bluetoothmanager._nameandmac();
 //BA.debugLineNum = 201;BA.debugLine="Try";
try { //BA.debugLineNum = 202;BA.debugLine="Dim rm As RandomAccessFile";
_rm = new anywheresoftware.b4a.randomaccessfile.RandomAccessFile();
 //BA.debugLineNum = 203;BA.debugLine="Dim conf As Configuracion";
_conf = new Corner.Light.Bluetooth.main._configuracion();
 //BA.debugLineNum = 204;BA.debugLine="rm.Initialize(File.DirInternal,\"datos.conf\",Fals";
_rm.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"datos.conf",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 205;BA.debugLine="conf = rm.ReadEncryptedObject(\"123\",0)";
_conf = (Corner.Light.Bluetooth.main._configuracion)(_rm.ReadEncryptedObject("123",(long) (0)));
 //BA.debugLineNum = 206;BA.debugLine="rm.Close";
_rm.Close();
 //BA.debugLineNum = 209;BA.debugLine="If nom = \"\" And mac = \"\" Then";
if ((_nom).equals("") && (_mac).equals("")) { 
 //BA.debugLineNum = 210;BA.debugLine="dispositivo.Name= conf.Nombre_BT";
_dispositivo.Name /*String*/  = _conf.Nombre_BT /*String*/ ;
 //BA.debugLineNum = 211;BA.debugLine="dispositivo.Mac=conf.Mac_BT";
_dispositivo.Mac /*String*/  = _conf.Mac_BT /*String*/ ;
 }else {
 //BA.debugLineNum = 213;BA.debugLine="dispositivo.Name= nom";
_dispositivo.Name /*String*/  = _nom;
 //BA.debugLineNum = 214;BA.debugLine="dispositivo.Mac=mac";
_dispositivo.Mac /*String*/  = _mac;
 };
 //BA.debugLineNum = 216;BA.debugLine="Return dispositivo";
if (true) return _dispositivo;
 } 
       catch (Exception e17) {
			processBA.setLastException(e17); //BA.debugLineNum = 218;BA.debugLine="ToastMessageShow(\"Error no hay datos guardados\",";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error no hay datos guardados"),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 219;BA.debugLine="dispositivo.Name= \"\"";
_dispositivo.Name /*String*/  = "";
 //BA.debugLineNum = 220;BA.debugLine="dispositivo.Mac = \"\"";
_dispositivo.Mac /*String*/  = "";
 //BA.debugLineNum = 221;BA.debugLine="Return dispositivo";
if (true) return _dispositivo;
 };
 //BA.debugLineNum = 223;BA.debugLine="End Sub";
return null;
}
public static String  _createlabel(anywheresoftware.b4a.objects.B4XViewWrapper _parent,String _text,anywheresoftware.b4a.objects.B4XViewWrapper.B4XFont _font) throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _tmp_lbl = null;
anywheresoftware.b4a.objects.B4XViewWrapper _xtmp_lbl = null;
 //BA.debugLineNum = 106;BA.debugLine="Private Sub CreateLabel(Parent As B4XView,Text As";
 //BA.debugLineNum = 107;BA.debugLine="Dim tmp_lbl As Label";
_tmp_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 108;BA.debugLine="tmp_lbl.Initialize(\"\")";
_tmp_lbl.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 109;BA.debugLine="Dim xtmp_lbl As B4XView = tmp_lbl";
_xtmp_lbl = new anywheresoftware.b4a.objects.B4XViewWrapper();
_xtmp_lbl = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_tmp_lbl.getObject()));
 //BA.debugLineNum = 111;BA.debugLine="Parent.AddView(xtmp_lbl,0,0,Parent.Width,Parent.H";
_parent.AddView((android.view.View)(_xtmp_lbl.getObject()),(int) (0),(int) (0),_parent.getWidth(),_parent.getHeight());
 //BA.debugLineNum = 112;BA.debugLine="xtmp_lbl.Font = Font";
_xtmp_lbl.setFont(_font);
 //BA.debugLineNum = 113;BA.debugLine="xtmp_lbl.Text = Text";
_xtmp_lbl.setText(BA.ObjectToCharSequence(_text));
 //BA.debugLineNum = 114;BA.debugLine="xtmp_lbl.SetTextAlignment(\"CENTER\",\"CENTER\")";
_xtmp_lbl.SetTextAlignment("CENTER","CENTER");
 //BA.debugLineNum = 115;BA.debugLine="xtmp_lbl.TextColor = xui.Color_White";
_xtmp_lbl.setTextColor(mostCurrent._xui.Color_White);
 //BA.debugLineNum = 116;BA.debugLine="End Sub";
return "";
}
public static void  _discoverfinished() throws Exception{
ResumableSub_DiscoverFinished rsub = new ResumableSub_DiscoverFinished(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_DiscoverFinished extends BA.ResumableSub {
public ResumableSub_DiscoverFinished(Corner.Light.Bluetooth.main parent) {
this.parent = parent;
}
Corner.Light.Bluetooth.main parent;
anywheresoftware.b4a.objects.collections.List _l = null;
Corner.Light.Bluetooth.bluetoothmanager._nameandmac _nm = null;
int _index = 0;
Corner.Light.Bluetooth.bluetoothmanager._nameandmac _device = null;
anywheresoftware.b4a.BA.IterableList group7;
int index7;
int groupLen7;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 139;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 140;BA.debugLine="If Starter.Manager.foundDevices.Size = 0 Then";
if (true) break;

case 1:
//if
this.state = 18;
if (parent.mostCurrent._starter._manager /*Corner.Light.Bluetooth.bluetoothmanager*/ ._founddevices /*anywheresoftware.b4a.objects.collections.List*/ .getSize()==0) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 18;
 //BA.debugLineNum = 141;BA.debugLine="ToastMessageShow(\"Ningún dispositivo encontrado.";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Ningún dispositivo encontrado."),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 143;BA.debugLine="Dim l As List";
_l = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 144;BA.debugLine="l.Initialize";
_l.Initialize();
 //BA.debugLineNum = 145;BA.debugLine="For Each nm As NameAndMac In Starter.Manager.fou";
if (true) break;

case 6:
//for
this.state = 13;
group7 = parent.mostCurrent._starter._manager /*Corner.Light.Bluetooth.bluetoothmanager*/ ._founddevices /*anywheresoftware.b4a.objects.collections.List*/ ;
index7 = 0;
groupLen7 = group7.getSize();
this.state = 19;
if (true) break;

case 19:
//C
this.state = 13;
if (index7 < groupLen7) {
this.state = 8;
_nm = (Corner.Light.Bluetooth.bluetoothmanager._nameandmac)(group7.Get(index7));}
if (true) break;

case 20:
//C
this.state = 19;
index7++;
if (true) break;

case 8:
//C
this.state = 9;
 //BA.debugLineNum = 146;BA.debugLine="If nm.Name <> \"\" Then";
if (true) break;

case 9:
//if
this.state = 12;
if ((_nm.Name /*String*/ ).equals("") == false) { 
this.state = 11;
}if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 147;BA.debugLine="l.Add(nm.Name)";
_l.Add((Object)(_nm.Name /*String*/ ));
 if (true) break;

case 12:
//C
this.state = 20;
;
 if (true) break;
if (true) break;

case 13:
//C
this.state = 14;
;
 //BA.debugLineNum = 150;BA.debugLine="InputListAsync(l, \"Elija el dispositivo para con";
anywheresoftware.b4a.keywords.Common.InputListAsync(_l,BA.ObjectToCharSequence("Elija el dispositivo para conectarse"),(int) (-1),processBA,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 151;BA.debugLine="Wait For InputList_Result (Index As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("inputlist_result", processBA, this, null);
this.state = 21;
return;
case 21:
//C
this.state = 14;
_index = (Integer) result[0];
;
 //BA.debugLineNum = 152;BA.debugLine="If Index <> DialogResponse.CANCEL Then";
if (true) break;

case 14:
//if
this.state = 17;
if (_index!=anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL) { 
this.state = 16;
}if (true) break;

case 16:
//C
this.state = 17;
 //BA.debugLineNum = 153;BA.debugLine="Dim device As NameAndMac = Starter.Manager.foun";
_device = (Corner.Light.Bluetooth.bluetoothmanager._nameandmac)(parent.mostCurrent._starter._manager /*Corner.Light.Bluetooth.bluetoothmanager*/ ._founddevices /*anywheresoftware.b4a.objects.collections.List*/ .Get(_index));
 //BA.debugLineNum = 155;BA.debugLine="GuardoBTemparejado(device.Name,device.Mac)";
_guardobtemparejado(_device.Name /*String*/ ,_device.Mac /*String*/ );
 //BA.debugLineNum = 156;BA.debugLine="Starter.Manager.ConnectTo(device)";
parent.mostCurrent._starter._manager /*Corner.Light.Bluetooth.bluetoothmanager*/ ._connectto /*String*/ (_device);
 //BA.debugLineNum = 158;BA.debugLine="ProgressDialogShow2($\"Intentando conectarme a:";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Intentando conectarme a: "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_device.Name /*String*/ ))+" ("+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_device.Mac /*String*/ ))+")")),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 17:
//C
this.state = 18;
;
 if (true) break;

case 18:
//C
this.state = -1;
;
 //BA.debugLineNum = 161;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _inputlist_result(int _index) throws Exception{
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 25;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 26;BA.debugLine="Private xui As XUI";
mostCurrent._xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 27;BA.debugLine="Private ASButtonSlider1 As ASButtonSlider";
mostCurrent._asbuttonslider1 = new Corner.Light.Bluetooth.asbuttonslider();
 //BA.debugLineNum = 28;BA.debugLine="Private img_activado As ImageView";
mostCurrent._img_activado = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private btn_config As Button";
mostCurrent._btn_config = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private pn_menu As Panel";
mostCurrent._pn_menu = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private btn_simulador As Button";
mostCurrent._btn_simulador = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private btn_buscar_dispo As Button";
mostCurrent._btn_buscar_dispo = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private btn_olvidar_dispo As Button";
mostCurrent._btn_olvidar_dispo = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private img_fondo As ImageView";
mostCurrent._img_fondo = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 35;BA.debugLine="End Sub";
return "";
}
public static String  _guardobtemparejado(String _nombre,String _mac) throws Exception{
anywheresoftware.b4a.randomaccessfile.RandomAccessFile _rm = null;
Corner.Light.Bluetooth.main._configuracion _conf = null;
 //BA.debugLineNum = 225;BA.debugLine="Private Sub GuardoBTemparejado(Nombre As String, M";
 //BA.debugLineNum = 226;BA.debugLine="Try";
try { //BA.debugLineNum = 227;BA.debugLine="Dim rm As RandomAccessFile";
_rm = new anywheresoftware.b4a.randomaccessfile.RandomAccessFile();
 //BA.debugLineNum = 228;BA.debugLine="Dim conf As Configuracion";
_conf = new Corner.Light.Bluetooth.main._configuracion();
 //BA.debugLineNum = 230;BA.debugLine="conf.Initialize";
_conf.Initialize();
 //BA.debugLineNum = 231;BA.debugLine="rm.Initialize(File.DirInternal,\"datos.conf\",Fals";
_rm.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"datos.conf",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 233;BA.debugLine="conf.Nombre_BT= Nombre";
_conf.Nombre_BT /*String*/  = _nombre;
 //BA.debugLineNum = 234;BA.debugLine="conf.Mac_BT=Mac";
_conf.Mac_BT /*String*/  = _mac;
 //BA.debugLineNum = 236;BA.debugLine="rm.WriteEncryptedObject(conf,\"123\",0)";
_rm.WriteEncryptedObject((Object)(_conf),"123",(long) (0));
 //BA.debugLineNum = 237;BA.debugLine="rm.Close";
_rm.Close();
 //BA.debugLineNum = 238;BA.debugLine="ToastMessageShow(\"Datos guardados exitosamente\",";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Datos guardados exitosamente"),anywheresoftware.b4a.keywords.Common.True);
 } 
       catch (Exception e12) {
			processBA.setLastException(e12); //BA.debugLineNum = 240;BA.debugLine="ToastMessageShow(\"Error al intentar guardar dato";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error al intentar guardar datos"),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 242;BA.debugLine="End Sub";
return "";
}
public static String  _img_fondo_click() throws Exception{
 //BA.debugLineNum = 272;BA.debugLine="Private Sub img_fondo_Click";
 //BA.debugLineNum = 273;BA.debugLine="pn_menu.Visible=False";
mostCurrent._pn_menu.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 274;BA.debugLine="End Sub";
return "";
}
public static String  _men_buscar_click() throws Exception{
boolean _success = false;
 //BA.debugLineNum = 96;BA.debugLine="Private Sub men_Buscar_click";
 //BA.debugLineNum = 97;BA.debugLine="Dim success As Boolean = Starter.Manager.SearchFo";
_success = mostCurrent._starter._manager /*Corner.Light.Bluetooth.bluetoothmanager*/ ._searchfordevices /*boolean*/ ();
 //BA.debugLineNum = 99;BA.debugLine="If success = False Then";
if (_success==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 100;BA.debugLine="ToastMessageShow(\"Error al iniciar el proceso de";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error al iniciar el proceso de descubrimiento."),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 102;BA.debugLine="ProgressDialogShow2(\"Buscando Dispositivos...\",";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando Dispositivos..."),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 104;BA.debugLine="End Sub";
return "";
}
public static String  _men_desemparejar() throws Exception{
anywheresoftware.b4a.randomaccessfile.RandomAccessFile _rm = null;
Corner.Light.Bluetooth.main._configuracion _conf = null;
 //BA.debugLineNum = 81;BA.debugLine="Private Sub men_desemparejar";
 //BA.debugLineNum = 82;BA.debugLine="Dim rm As RandomAccessFile";
_rm = new anywheresoftware.b4a.randomaccessfile.RandomAccessFile();
 //BA.debugLineNum = 83;BA.debugLine="Dim conf As Configuracion";
_conf = new Corner.Light.Bluetooth.main._configuracion();
 //BA.debugLineNum = 85;BA.debugLine="conf.Initialize";
_conf.Initialize();
 //BA.debugLineNum = 86;BA.debugLine="rm.Initialize(File.DirInternal,\"datos.conf\",False";
_rm.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"datos.conf",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 88;BA.debugLine="conf.Nombre_BT= \"\"";
_conf.Nombre_BT /*String*/  = "";
 //BA.debugLineNum = 89;BA.debugLine="conf.Mac_BT=\"\"";
_conf.Mac_BT /*String*/  = "";
 //BA.debugLineNum = 91;BA.debugLine="rm.WriteEncryptedObject(conf,\"123\",0)";
_rm.WriteEncryptedObject((Object)(_conf),"123",(long) (0));
 //BA.debugLineNum = 92;BA.debugLine="rm.Close";
_rm.Close();
 //BA.debugLineNum = 93;BA.debugLine="ToastMessageShow(\"Dispositivos olvidados\",True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Dispositivos olvidados"),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 94;BA.debugLine="End Sub";
return "";
}
public static String  _men_emparejados() throws Exception{
anywheresoftware.b4a.objects.collections.Map _paireddevices = null;
anywheresoftware.b4a.objects.collections.List _l = null;
int _i = 0;
int _res = 0;
Corner.Light.Bluetooth.bluetoothmanager._nameandmac _device = null;
 //BA.debugLineNum = 54;BA.debugLine="Private Sub men_Emparejados";
 //BA.debugLineNum = 55;BA.debugLine="Dim PairedDevices As Map";
_paireddevices = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 56;BA.debugLine="PairedDevices = Serial1.GetPairedDevices";
_paireddevices = _serial1.GetPairedDevices();
 //BA.debugLineNum = 57;BA.debugLine="Dim l As List";
_l = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 58;BA.debugLine="l.Initialize";
_l.Initialize();
 //BA.debugLineNum = 59;BA.debugLine="For i = 0 To PairedDevices.Size - 1";
{
final int step5 = 1;
final int limit5 = (int) (_paireddevices.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit5 ;_i = _i + step5 ) {
 //BA.debugLineNum = 60;BA.debugLine="l.Add(PairedDevices.GetKeyAt(i))";
_l.Add(_paireddevices.GetKeyAt(_i));
 }
};
 //BA.debugLineNum = 62;BA.debugLine="Dim res As Int";
_res = 0;
 //BA.debugLineNum = 63;BA.debugLine="res = InputList(l, \"Seleccione un dispositivo emp";
_res = anywheresoftware.b4a.keywords.Common.InputList(_l,BA.ObjectToCharSequence("Seleccione un dispositivo emparejado"),(int) (-1),mostCurrent.activityBA);
 //BA.debugLineNum = 65;BA.debugLine="If res <> DialogResponse.CANCEL Then";
if (_res!=anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL) { 
 //BA.debugLineNum = 66;BA.debugLine="Log(PairedDevices.Get(l.get(res)))";
anywheresoftware.b4a.keywords.Common.LogImpl("3196620",BA.ObjectToString(_paireddevices.Get(_l.Get(_res))),0);
 //BA.debugLineNum = 68;BA.debugLine="Dim device As NameAndMac";
_device = new Corner.Light.Bluetooth.bluetoothmanager._nameandmac();
 //BA.debugLineNum = 69;BA.debugLine="device.Name = \"Corner Light\"";
_device.Name /*String*/  = "Corner Light";
 //BA.debugLineNum = 70;BA.debugLine="device.Mac  = PairedDevices.Get(l.Get(res))";
_device.Mac /*String*/  = BA.ObjectToString(_paireddevices.Get(_l.Get(_res)));
 //BA.debugLineNum = 73;BA.debugLine="GuardoBTemparejado(device.Name,device.Mac)";
_guardobtemparejado(_device.Name /*String*/ ,_device.Mac /*String*/ );
 //BA.debugLineNum = 74;BA.debugLine="Starter.Manager.ConnectTo(device)";
mostCurrent._starter._manager /*Corner.Light.Bluetooth.bluetoothmanager*/ ._connectto /*String*/ (_device);
 //BA.debugLineNum = 76;BA.debugLine="img_activado.Visible=True";
mostCurrent._img_activado.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 77;BA.debugLine="ProgressDialogShow2($\"Intentando conectarme a: $";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Intentando conectarme a: "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_device.Name /*String*/ ))+" ("+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_device.Mac /*String*/ ))+")")),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 79;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        b4a.example.dateutils._process_globals();
main._process_globals();
chatactivity._process_globals();
configactivity._process_globals();
diagnosticoactivity._process_globals();
starter._process_globals();
xuiviewsutils._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private rp As RuntimePermissions";
_rp = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 17;BA.debugLine="Public Simulador As Boolean";
_simulador = false;
 //BA.debugLineNum = 18;BA.debugLine="Dim dispo As NameAndMac";
_dispo = new Corner.Light.Bluetooth.bluetoothmanager._nameandmac();
 //BA.debugLineNum = 20;BA.debugLine="Type Configuracion(Nombre_BT As String, Mac_BT As";
;
 //BA.debugLineNum = 22;BA.debugLine="Dim Serial1 As Serial";
_serial1 = new anywheresoftware.b4a.objects.Serial();
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static String  _updatestate() throws Exception{
 //BA.debugLineNum = 130;BA.debugLine="Public Sub UpdateState";
 //BA.debugLineNum = 132;BA.debugLine="End Sub";
return "";
}
}
