package Corner.Light.Bluetooth.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_config{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("scrollview1").vw.setWidth((int)((100d / 100 * width)));
views.get("scrollview1").vw.setHeight((int)((80d / 100 * height)));
views.get("panel1").vw.setWidth((int)((100d / 100 * width)));
views.get("panel1").vw.setHeight((int)((20d / 100 * height)));
views.get("panel1").vw.setTop((int)((80d / 100 * height)));
views.get("btn_guardarcambios").vw.setTop((int)(((views.get("panel1").vw.getHeight())-(views.get("btn_guardarcambios").vw.getHeight()))/2d));
views.get("btn_guardarcambios").vw.setWidth((int)((70d / 100 * width)));
views.get("btn_guardarcambios").vw.setHeight((int)((15d / 100 * height)));

}
}