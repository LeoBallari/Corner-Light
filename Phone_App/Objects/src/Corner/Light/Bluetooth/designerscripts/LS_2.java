package Corner.Light.Bluetooth.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_2{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("img_fondo").vw.setHeight((int)((100d / 100 * height)));
views.get("img_fondo").vw.setWidth((int)((100d / 100 * width)));
views.get("img_fondo").vw.setTop((int)(0d));
views.get("img_fondo").vw.setLeft((int)(0d));
views.get("gauge1").vw.setTop((int)((32d / 100 * height)));
views.get("gauge1").vw.setLeft((int)(((views.get("img_fondo").vw.getWidth())-(views.get("gauge1").vw.getWidth()))/2d));
views.get("img_gauge").vw.setTop((int)((54d / 100 * height)));
views.get("img_gauge").vw.setLeft((int)(((views.get("img_fondo").vw.getWidth())-(views.get("img_gauge").vw.getWidth()))/2d));
views.get("btn_reset_inclina").vw.setWidth((int)((20d / 100 * width)));
views.get("btn_reset_inclina").vw.setHeight((int)((30d / 100 * height)));
views.get("btn_reset_inclina").vw.setLeft((int)(((views.get("img_fondo").vw.getWidth())-(views.get("btn_reset_inclina").vw.getWidth()))/2d));
views.get("img_indicadores").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 20;BA.debugLine="img_indicadores.Height=25%y"[2/General script]
views.get("img_indicadores").vw.setHeight((int)((25d / 100 * height)));
//BA.debugLineNum = 21;BA.debugLine="img_indicadores.Left=0"[2/General script]
views.get("img_indicadores").vw.setLeft((int)(0d));
//BA.debugLineNum = 22;BA.debugLine="img_indicadores.Top=70%y"[2/General script]
views.get("img_indicadores").vw.setTop((int)((70d / 100 * height)));
//BA.debugLineNum = 24;BA.debugLine="img_Led_der_on.Width=100%x"[2/General script]
views.get("img_led_der_on").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 25;BA.debugLine="img_Led_der_on.Height=25%y"[2/General script]
views.get("img_led_der_on").vw.setHeight((int)((25d / 100 * height)));
//BA.debugLineNum = 26;BA.debugLine="img_Led_der_on.Left=0"[2/General script]
views.get("img_led_der_on").vw.setLeft((int)(0d));
//BA.debugLineNum = 27;BA.debugLine="img_Led_der_on.Top=70%y"[2/General script]
views.get("img_led_der_on").vw.setTop((int)((70d / 100 * height)));
//BA.debugLineNum = 29;BA.debugLine="img_Led_izq_on.Width=100%x"[2/General script]
views.get("img_led_izq_on").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 30;BA.debugLine="img_Led_izq_on.Height=25%y"[2/General script]
views.get("img_led_izq_on").vw.setHeight((int)((25d / 100 * height)));
//BA.debugLineNum = 31;BA.debugLine="img_Led_izq_on.Left=0"[2/General script]
views.get("img_led_izq_on").vw.setLeft((int)(0d));
//BA.debugLineNum = 32;BA.debugLine="img_Led_izq_on.Top=70%y"[2/General script]
views.get("img_led_izq_on").vw.setTop((int)((70d / 100 * height)));
//BA.debugLineNum = 34;BA.debugLine="lb_hora.Height=15%y"[2/General script]
views.get("lb_hora").vw.setHeight((int)((15d / 100 * height)));
//BA.debugLineNum = 36;BA.debugLine="lb_hora.Top=20%y"[2/General script]
views.get("lb_hora").vw.setTop((int)((20d / 100 * height)));
//BA.debugLineNum = 37;BA.debugLine="lb_hora.Width=100%x"[2/General script]
views.get("lb_hora").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 38;BA.debugLine="lb_hora.Top=13%y"[2/General script]
views.get("lb_hora").vw.setTop((int)((13d / 100 * height)));
//BA.debugLineNum = 39;BA.debugLine="lb_hora.Left=0"[2/General script]
views.get("lb_hora").vw.setLeft((int)(0d));
//BA.debugLineNum = 41;BA.debugLine="lb_sensor.Width=100%x"[2/General script]
views.get("lb_sensor").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 42;BA.debugLine="lb_sensor.Height=30%y"[2/General script]
views.get("lb_sensor").vw.setHeight((int)((30d / 100 * height)));
//BA.debugLineNum = 43;BA.debugLine="lb_sensor.Top=72%y"[2/General script]
views.get("lb_sensor").vw.setTop((int)((72d / 100 * height)));
//BA.debugLineNum = 44;BA.debugLine="lb_sensor.Left=-2%x"[2/General script]
views.get("lb_sensor").vw.setLeft((int)(0-(2d / 100 * width)));
//BA.debugLineNum = 46;BA.debugLine="lb_sensor2.Width=100%x"[2/General script]
views.get("lb_sensor2").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 47;BA.debugLine="lb_sensor2.Height=30%y"[2/General script]
views.get("lb_sensor2").vw.setHeight((int)((30d / 100 * height)));
//BA.debugLineNum = 48;BA.debugLine="lb_sensor2.Top=76%y"[2/General script]
views.get("lb_sensor2").vw.setTop((int)((76d / 100 * height)));
//BA.debugLineNum = 49;BA.debugLine="lb_sensor2.Left=6%x"[2/General script]
views.get("lb_sensor2").vw.setLeft((int)((6d / 100 * width)));
//BA.debugLineNum = 51;BA.debugLine="btn_config.Height=15%y"[2/General script]
views.get("btn_config").vw.setHeight((int)((15d / 100 * height)));
//BA.debugLineNum = 52;BA.debugLine="btn_config.Width=8%x"[2/General script]
views.get("btn_config").vw.setWidth((int)((8d / 100 * width)));
//BA.debugLineNum = 53;BA.debugLine="btn_config.Top=-2%y"[2/General script]
views.get("btn_config").vw.setTop((int)(0-(2d / 100 * height)));
//BA.debugLineNum = 54;BA.debugLine="btn_config.Left=0"[2/General script]
views.get("btn_config").vw.setLeft((int)(0d));
//BA.debugLineNum = 56;BA.debugLine="pn_max_min.Top=25%y"[2/General script]
views.get("pn_max_min").vw.setTop((int)((25d / 100 * height)));
//BA.debugLineNum = 57;BA.debugLine="pn_angulos.Top=25%y"[2/General script]
views.get("pn_angulos").vw.setTop((int)((25d / 100 * height)));
//BA.debugLineNum = 59;BA.debugLine="pn_max_min.Left=-2%x"[2/General script]
views.get("pn_max_min").vw.setLeft((int)(0-(2d / 100 * width)));
//BA.debugLineNum = 60;BA.debugLine="pn_angulos.Right=102%x"[2/General script]
views.get("pn_angulos").vw.setLeft((int)((102d / 100 * width) - (views.get("pn_angulos").vw.getWidth())));
//BA.debugLineNum = 62;BA.debugLine="Label1.Left=(pn_max_min.Width-Label1.Width)/2"[2/General script]
views.get("label1").vw.setLeft((int)(((views.get("pn_max_min").vw.getWidth())-(views.get("label1").vw.getWidth()))/2d));
//BA.debugLineNum = 63;BA.debugLine="Label3.Left=(pn_angulos.Width-Label3.Width)/2"[2/General script]
views.get("label3").vw.setLeft((int)(((views.get("pn_angulos").vw.getWidth())-(views.get("label3").vw.getWidth()))/2d));
//BA.debugLineNum = 65;BA.debugLine="btn_reset.Width=pn_max_min.Width"[2/General script]
views.get("btn_reset").vw.setWidth((int)((views.get("pn_max_min").vw.getWidth())));
//BA.debugLineNum = 66;BA.debugLine="btn_reset.Height=pn_max_min.Height"[2/General script]
views.get("btn_reset").vw.setHeight((int)((views.get("pn_max_min").vw.getHeight())));
//BA.debugLineNum = 67;BA.debugLine="btn_reset.Top = pn_max_min.Top"[2/General script]
views.get("btn_reset").vw.setTop((int)((views.get("pn_max_min").vw.getTop())));
//BA.debugLineNum = 68;BA.debugLine="btn_reset.Left = pn_max_min.Left"[2/General script]
views.get("btn_reset").vw.setLeft((int)((views.get("pn_max_min").vw.getLeft())));
//BA.debugLineNum = 70;BA.debugLine="lb_titulo.Top=2%y"[2/General script]
views.get("lb_titulo").vw.setTop((int)((2d / 100 * height)));
//BA.debugLineNum = 71;BA.debugLine="lb_titulo.Left=10%x"[2/General script]
views.get("lb_titulo").vw.setLeft((int)((10d / 100 * width)));
//BA.debugLineNum = 72;BA.debugLine="lb_perfil.Top=2%y"[2/General script]
views.get("lb_perfil").vw.setTop((int)((2d / 100 * height)));
//BA.debugLineNum = 73;BA.debugLine="lb_perfil.Left= (img_fondo.Width-lb_perfil.Width)/2"[2/General script]
views.get("lb_perfil").vw.setLeft((int)(((views.get("img_fondo").vw.getWidth())-(views.get("lb_perfil").vw.getWidth()))/2d));
//BA.debugLineNum = 75;BA.debugLine="img_Auto_on.Right=98%x"[2/General script]
views.get("img_auto_on").vw.setLeft((int)((98d / 100 * width) - (views.get("img_auto_on").vw.getWidth())));
//BA.debugLineNum = 76;BA.debugLine="img_Auto_on.Top=1.2%y"[2/General script]
views.get("img_auto_on").vw.setTop((int)((1.2d / 100 * height)));
//BA.debugLineNum = 77;BA.debugLine="img_Auto_off.Right=98%x"[2/General script]
views.get("img_auto_off").vw.setLeft((int)((98d / 100 * width) - (views.get("img_auto_off").vw.getWidth())));
//BA.debugLineNum = 78;BA.debugLine="img_Auto_off.Top=1.2%y"[2/General script]
views.get("img_auto_off").vw.setTop((int)((1.2d / 100 * height)));
//BA.debugLineNum = 80;BA.debugLine="btn_on_off.Right=98%x"[2/General script]
views.get("btn_on_off").vw.setLeft((int)((98d / 100 * width) - (views.get("btn_on_off").vw.getWidth())));
//BA.debugLineNum = 81;BA.debugLine="btn_on_off.Top=0%y"[2/General script]
views.get("btn_on_off").vw.setTop((int)((0d / 100 * height)));
//BA.debugLineNum = 83;BA.debugLine="pn_cal_ang.Height=60%y"[2/General script]
views.get("pn_cal_ang").vw.setHeight((int)((60d / 100 * height)));
//BA.debugLineNum = 84;BA.debugLine="pn_cal_ang.Width=70%x"[2/General script]
views.get("pn_cal_ang").vw.setWidth((int)((70d / 100 * width)));
//BA.debugLineNum = 86;BA.debugLine="pn_cal_ang.Top = (img_fondo.Height-pn_cal_ang.Height)/2"[2/General script]
views.get("pn_cal_ang").vw.setTop((int)(((views.get("img_fondo").vw.getHeight())-(views.get("pn_cal_ang").vw.getHeight()))/2d));
//BA.debugLineNum = 87;BA.debugLine="pn_cal_ang.Left = (img_fondo.Width-pn_cal_ang.Width)/2"[2/General script]
views.get("pn_cal_ang").vw.setLeft((int)(((views.get("img_fondo").vw.getWidth())-(views.get("pn_cal_ang").vw.getWidth()))/2d));
//BA.debugLineNum = 89;BA.debugLine="btn_aceptar.Left = (pn_cal_ang.Width - btn_aceptar.Width)/2"[2/General script]
views.get("btn_aceptar").vw.setLeft((int)(((views.get("pn_cal_ang").vw.getWidth())-(views.get("btn_aceptar").vw.getWidth()))/2d));
//BA.debugLineNum = 90;BA.debugLine="btn_aceptar.TOP = 48%y"[2/General script]
views.get("btn_aceptar").vw.setTop((int)((48d / 100 * height)));

}
}