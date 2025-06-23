package Corner.Light.Bluetooth;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class bluetoothmanager extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "Corner.Light.Bluetooth.bluetoothmanager");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", Corner.Light.Bluetooth.bluetoothmanager.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.randomaccessfile.AsyncStreams _astream = null;
public anywheresoftware.b4a.objects.Serial _serial = null;
public anywheresoftware.b4a.objects.Serial.BluetoothAdmin _admin = null;
public anywheresoftware.b4a.objects.collections.List _founddevices = null;
public boolean _bluetoothstate = false;
public boolean _connectionstate = false;
public b4a.example.dateutils _dateutils = null;
public Corner.Light.Bluetooth.main _main = null;
public Corner.Light.Bluetooth.chatactivity _chatactivity = null;
public Corner.Light.Bluetooth.configactivity _configactivity = null;
public Corner.Light.Bluetooth.diagnosticoactivity _diagnosticoactivity = null;
public Corner.Light.Bluetooth.starter _starter = null;
public Corner.Light.Bluetooth.xuiviewsutils _xuiviewsutils = null;
public static class _nameandmac{
public boolean IsInitialized;
public String Name;
public String Mac;
public void Initialize() {
IsInitialized = true;
Name = "";
Mac = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public String  _admin_devicefound(String _name,String _macaddress) throws Exception{
Corner.Light.Bluetooth.bluetoothmanager._nameandmac _nm = null;
 //BA.debugLineNum = 86;BA.debugLine="Private Sub Admin_DeviceFound (Name As String, Mac";
 //BA.debugLineNum = 87;BA.debugLine="Log(Name & \":\" & MacAddress)";
__c.LogImpl("35570561",_name+":"+_macaddress,0);
 //BA.debugLineNum = 88;BA.debugLine="Dim nm As NameAndMac";
_nm = new Corner.Light.Bluetooth.bluetoothmanager._nameandmac();
 //BA.debugLineNum = 89;BA.debugLine="nm.Name = Name";
_nm.Name /*String*/  = _name;
 //BA.debugLineNum = 90;BA.debugLine="nm.Mac = MacAddress";
_nm.Mac /*String*/  = _macaddress;
 //BA.debugLineNum = 91;BA.debugLine="foundDevices.Add(nm)";
_founddevices.Add((Object)(_nm));
 //BA.debugLineNum = 92;BA.debugLine="End Sub";
return "";
}
public String  _admin_discoveryfinished() throws Exception{
 //BA.debugLineNum = 82;BA.debugLine="Private Sub Admin_DiscoveryFinished";
 //BA.debugLineNum = 83;BA.debugLine="CallSub(Main, \"DiscoverFinished\")";
__c.CallSubNew(ba,(Object)(_main.getObject()),"DiscoverFinished");
 //BA.debugLineNum = 84;BA.debugLine="End Sub";
return "";
}
public String  _admin_statechanged(int _newstate,int _oldstate) throws Exception{
 //BA.debugLineNum = 24;BA.debugLine="Private Sub Admin_StateChanged (NewState As Int, O";
 //BA.debugLineNum = 25;BA.debugLine="Log(\"Cambio de Estado: \" & NewState)";
__c.LogImpl("34915201","Cambio de Estado: "+BA.NumberToString(_newstate),0);
 //BA.debugLineNum = 26;BA.debugLine="BluetoothState = NewState = admin.STATE_ON";
_bluetoothstate = _newstate==_admin.STATE_ON;
 //BA.debugLineNum = 27;BA.debugLine="NotifyOfStateChanged";
_notifyofstatechanged();
 //BA.debugLineNum = 28;BA.debugLine="End Sub";
return "";
}
public String  _astream_error() throws Exception{
 //BA.debugLineNum = 61;BA.debugLine="Private Sub AStream_Error";
 //BA.debugLineNum = 62;BA.debugLine="ToastMessageShow(\"La conexión está rota.....Reint";
__c.ToastMessageShow(BA.ObjectToCharSequence("La conexión está rota.....Reintentado..."),__c.True);
 //BA.debugLineNum = 63;BA.debugLine="Starter.Manager.ConnectTo(Main.dispo)";
_starter._manager /*Corner.Light.Bluetooth.bluetoothmanager*/ ._connectto /*String*/ (_main._dispo /*Corner.Light.Bluetooth.bluetoothmanager._nameandmac*/ );
 //BA.debugLineNum = 64;BA.debugLine="ConnectionState = False";
_connectionstate = __c.False;
 //BA.debugLineNum = 65;BA.debugLine="NotifyOfStateChanged";
_notifyofstatechanged();
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
public String  _astream_newdata(byte[] _buffer) throws Exception{
 //BA.debugLineNum = 56;BA.debugLine="Private Sub AStream_NewData (Buffer() As Byte)";
 //BA.debugLineNum = 57;BA.debugLine="CallSub2(ChatActivity, \"NewMessage\", BytesToStrin";
__c.CallSubNew2(ba,(Object)(_chatactivity.getObject()),"NewMessage",(Object)(__c.BytesToString(_buffer,(int) (0),_buffer.length,"UTF8")));
 //BA.debugLineNum = 58;BA.debugLine="CallSub2(DiagnosticoActivity, \"NewMessage2\", Byte";
__c.CallSubNew2(ba,(Object)(_diagnosticoactivity.getObject()),"NewMessage2",(Object)(__c.BytesToString(_buffer,(int) (0),_buffer.length,"UTF8")));
 //BA.debugLineNum = 59;BA.debugLine="End Sub";
return "";
}
public String  _astream_terminated() throws Exception{
 //BA.debugLineNum = 68;BA.debugLine="Private Sub AStream_Terminated";
 //BA.debugLineNum = 69;BA.debugLine="AStream_Error";
_astream_error();
 //BA.debugLineNum = 70;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 1;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 2;BA.debugLine="Public AStream As AsyncStreams";
_astream = new anywheresoftware.b4a.randomaccessfile.AsyncStreams();
 //BA.debugLineNum = 3;BA.debugLine="Private serial As Serial";
_serial = new anywheresoftware.b4a.objects.Serial();
 //BA.debugLineNum = 4;BA.debugLine="Private admin As BluetoothAdmin";
_admin = new anywheresoftware.b4a.objects.Serial.BluetoothAdmin();
 //BA.debugLineNum = 5;BA.debugLine="Public foundDevices As List";
_founddevices = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 6;BA.debugLine="Type NameAndMac (Name As String, Mac As String)";
;
 //BA.debugLineNum = 7;BA.debugLine="Public BluetoothState, ConnectionState As Boolean";
_bluetoothstate = false;
_connectionstate = false;
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
public String  _connectto(Corner.Light.Bluetooth.bluetoothmanager._nameandmac _device) throws Exception{
 //BA.debugLineNum = 30;BA.debugLine="Public Sub ConnectTo (Device As NameAndMac)";
 //BA.debugLineNum = 31;BA.debugLine="serial.Connect(Device.Mac)";
_serial.Connect(ba,_device.Mac /*String*/ );
 //BA.debugLineNum = 32;BA.debugLine="End Sub";
return "";
}
public String  _disconnect() throws Exception{
 //BA.debugLineNum = 72;BA.debugLine="Public Sub Disconnect";
 //BA.debugLineNum = 73;BA.debugLine="If AStream.IsInitialized Then AStream.Close";
if (_astream.IsInitialized()) { 
_astream.Close();};
 //BA.debugLineNum = 74;BA.debugLine="serial.Disconnect";
_serial.Disconnect();
 //BA.debugLineNum = 75;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 10;BA.debugLine="Public Sub Initialize";
 //BA.debugLineNum = 11;BA.debugLine="admin.Initialize(\"admin\")";
_admin.Initialize(ba,"admin");
 //BA.debugLineNum = 12;BA.debugLine="serial.Initialize(\"serial\")";
_serial.Initialize("serial");
 //BA.debugLineNum = 13;BA.debugLine="If admin.IsEnabled = False Then";
if (_admin.IsEnabled()==__c.False) { 
 //BA.debugLineNum = 14;BA.debugLine="If admin.Enable = False Then";
if (_admin.Enable()==__c.False) { 
 //BA.debugLineNum = 15;BA.debugLine="ToastMessageShow(\"Error al habilitar el adaptad";
__c.ToastMessageShow(BA.ObjectToCharSequence("Error al habilitar el adaptador Bluetooth."),__c.True);
 }else {
 //BA.debugLineNum = 17;BA.debugLine="ToastMessageShow(\"Habilitando el adaptador Blue";
__c.ToastMessageShow(BA.ObjectToCharSequence("Habilitando el adaptador Bluetooth..."),__c.False);
 };
 }else {
 //BA.debugLineNum = 20;BA.debugLine="BluetoothState = True";
_bluetoothstate = __c.True;
 };
 //BA.debugLineNum = 22;BA.debugLine="End Sub";
return "";
}
public String  _listenforconnections() throws Exception{
anywheresoftware.b4a.objects.IntentWrapper _i = null;
 //BA.debugLineNum = 94;BA.debugLine="Public Sub ListenForConnections";
 //BA.debugLineNum = 96;BA.debugLine="Dim i As Intent";
_i = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 97;BA.debugLine="i.Initialize(\"android.bluetooth.adapter.action.RE";
_i.Initialize("android.bluetooth.adapter.action.REQUEST_DISCOVERABLE","");
 //BA.debugLineNum = 98;BA.debugLine="i.PutExtra(\"android.bluetooth.adapter.extra.DISCO";
_i.PutExtra("android.bluetooth.adapter.extra.DISCOVERABLE_DURATION",(Object)(300));
 //BA.debugLineNum = 100;BA.debugLine="StartActivity(i)";
__c.StartActivity(ba,(Object)(_i.getObject()));
 //BA.debugLineNum = 101;BA.debugLine="serial.Listen";
_serial.Listen(ba);
 //BA.debugLineNum = 102;BA.debugLine="End Sub";
return "";
}
public String  _notifyofstatechanged() throws Exception{
Object _target = null;
 //BA.debugLineNum = 104;BA.debugLine="Private Sub NotifyOfStateChanged";
 //BA.debugLineNum = 105;BA.debugLine="For Each Target In Array(Main, ChatActivity)";
{
final Object[] group1 = new Object[]{(Object)(_main.getObject()),(Object)(_chatactivity.getObject())};
final int groupLen1 = group1.length
;int index1 = 0;
;
for (; index1 < groupLen1;index1++){
_target = group1[index1];
 //BA.debugLineNum = 106;BA.debugLine="CallSub(Target, \"UpdateState\")";
__c.CallSubNew(ba,_target,"UpdateState");
 }
};
 //BA.debugLineNum = 108;BA.debugLine="End Sub";
return "";
}
public boolean  _searchfordevices() throws Exception{
 //BA.debugLineNum = 77;BA.debugLine="Public Sub SearchForDevices As Boolean";
 //BA.debugLineNum = 78;BA.debugLine="foundDevices.Initialize";
_founddevices.Initialize();
 //BA.debugLineNum = 79;BA.debugLine="Return admin.StartDiscovery";
if (true) return _admin.StartDiscovery();
 //BA.debugLineNum = 80;BA.debugLine="End Sub";
return false;
}
public String  _sendmessage(String _msg) throws Exception{
 //BA.debugLineNum = 52;BA.debugLine="Public Sub SendMessage (msg As String)";
 //BA.debugLineNum = 53;BA.debugLine="AStream.Write(msg.GetBytes(\"utf8\"))";
_astream.Write(_msg.getBytes("utf8"));
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return "";
}
public String  _serial_connected(boolean _success) throws Exception{
 //BA.debugLineNum = 34;BA.debugLine="Private Sub Serial_Connected (Success As Boolean)";
 //BA.debugLineNum = 35;BA.debugLine="Log(\"Conectado: \" & Success)";
__c.LogImpl("35046273","Conectado: "+BA.ObjectToString(_success),0);
 //BA.debugLineNum = 37;BA.debugLine="CallSub2(Main, \"AfterConnect\", Success) 'allow th";
__c.CallSubNew2(ba,(Object)(_main.getObject()),"AfterConnect",(Object)(_success));
 //BA.debugLineNum = 38;BA.debugLine="ConnectionState = Success";
_connectionstate = _success;
 //BA.debugLineNum = 39;BA.debugLine="If Success = False Then";
if (_success==__c.False) { 
 //BA.debugLineNum = 40;BA.debugLine="Log(LastException.Message)";
__c.LogImpl("35046278",__c.LastException(getActivityBA()).getMessage(),0);
 }else {
 //BA.debugLineNum = 43;BA.debugLine="If AStream.IsInitialized Then AStream.Close";
if (_astream.IsInitialized()) { 
_astream.Close();};
 //BA.debugLineNum = 46;BA.debugLine="AStream.Initialize(serial.InputStream, serial.Ou";
_astream.Initialize(ba,_serial.getInputStream(),_serial.getOutputStream(),"AStream");
 //BA.debugLineNum = 47;BA.debugLine="StartActivity(ChatActivity)";
__c.StartActivity(ba,(Object)(_chatactivity.getObject()));
 };
 //BA.debugLineNum = 49;BA.debugLine="NotifyOfStateChanged";
_notifyofstatechanged();
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
