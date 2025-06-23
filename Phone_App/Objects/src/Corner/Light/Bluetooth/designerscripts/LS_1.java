package Corner.Light.Bluetooth.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_1{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[1/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 4;BA.debugLine="img_fondo.Width = 100%x"[1/General script]
views.get("img_fondo").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 5;BA.debugLine="img_fondo.Height = 100%y"[1/General script]
views.get("img_fondo").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 7;BA.debugLine="img_activado.Width = 100%x"[1/General script]
views.get("img_activado").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 8;BA.debugLine="img_activado.Height = 100%y"[1/General script]
views.get("img_activado").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 10;BA.debugLine="ASButtonSlider1.Top=50%y"[1/General script]
views.get("asbuttonslider1").vw.setTop((int)((50d / 100 * height)));
//BA.debugLineNum = 11;BA.debugLine="ASButtonSlider1.Left=(img_fondo.Width - ASButtonSlider1.Width)/2"[1/General script]
views.get("asbuttonslider1").vw.setLeft((int)(((views.get("img_fondo").vw.getWidth())-(views.get("asbuttonslider1").vw.getWidth()))/2d));
//BA.debugLineNum = 18;BA.debugLine="pn_menu.Height=100%y"[1/General script]
views.get("pn_menu").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 19;BA.debugLine="pn_menu.Width=25%x"[1/General script]
views.get("pn_menu").vw.setWidth((int)((25d / 100 * width)));
//BA.debugLineNum = 21;BA.debugLine="pn_menu.Top=0"[1/General script]
views.get("pn_menu").vw.setTop((int)(0d));
//BA.debugLineNum = 22;BA.debugLine="pn_menu.Left=0"[1/General script]
views.get("pn_menu").vw.setLeft((int)(0d));
//BA.debugLineNum = 24;BA.debugLine="btn_config.Top=-2%y"[1/General script]
views.get("btn_config").vw.setTop((int)(0-(2d / 100 * height)));
//BA.debugLineNum = 25;BA.debugLine="btn_config.Left=0"[1/General script]
views.get("btn_config").vw.setLeft((int)(0d));
//BA.debugLineNum = 27;BA.debugLine="btn_simulador.Width=pn_menu.Width"[1/General script]
views.get("btn_simulador").vw.setWidth((int)((views.get("pn_menu").vw.getWidth())));

}
}