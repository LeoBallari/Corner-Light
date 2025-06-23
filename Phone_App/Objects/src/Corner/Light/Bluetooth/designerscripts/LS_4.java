package Corner.Light.Bluetooth.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_4{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("range_roll").vw.setWidth((int)(((views.get("panel1").vw.getWidth())-(200d * scale))));
views.get("range_roll").vw.setLeft((int)((50d / 100 * width) - (views.get("range_roll").vw.getWidth() / 2)));
views.get("range_roll").vw.setTop((int)((50d / 100 * height) - (views.get("range_roll").vw.getHeight() / 2)));
views.get("range_pich").vw.setLeft((int)((50d / 100 * width) - (views.get("range_pich").vw.getWidth() / 2)));
views.get("range_pich").vw.setTop((int)((50d / 100 * height) - (views.get("range_pich").vw.getHeight() / 2)));
views.get("lb_roll_izq").vw.setTop((int)((50d / 100 * height) - (views.get("lb_roll_izq").vw.getHeight() / 2)));
views.get("lb_roll_der").vw.setTop((int)((50d / 100 * height) - (views.get("lb_roll_der").vw.getHeight() / 2)));
views.get("panel1").vw.setTop((int)((50d / 100 * height) - (views.get("panel1").vw.getHeight() / 2)));

}
}