package Corner.Light.Bluetooth.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_3{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("pn_contenedor").vw.setWidth((int)((100d / 100 * width)));
views.get("pn_contenedor").vw.setHeight((int)((200d / 100 * height)));
views.get("panel1").vw.setTop((int)((2d / 100 * height)));
views.get("panel1").vw.setLeft((int)((2d / 100 * width)));
views.get("panel1").vw.setWidth((int)((96d / 100 * width)));
views.get("panel1").vw.setHeight((int)((190d / 100 * height)));
views.get("label1").vw.setLeft((int)((2d / 100 * width)));
views.get("label11").vw.setLeft((int)((2d / 100 * width)));
views.get("label2").vw.setLeft((int)((2d / 100 * width)));
views.get("label12").vw.setLeft((int)((2d / 100 * width)));
views.get("label14").vw.setLeft((int)((2d / 100 * width)));
views.get("label13").vw.setLeft((int)((2d / 100 * width)));
views.get("label4").vw.setLeft((int)((2d / 100 * width)));
views.get("label6").vw.setLeft((int)((2d / 100 * width)));
views.get("label8").vw.setLeft((int)((2d / 100 * width)));
views.get("label10").vw.setLeft((int)((2d / 100 * width)));
views.get("label15").vw.setLeft((int)((2d / 100 * width)));
views.get("label5").vw.setLeft((int)((2d / 100 * width)));
views.get("see_sencibilidad").vw.setWidth((int)((40d / 100 * width)));
views.get("see_pasa_bajos").vw.setWidth((int)((40d / 100 * width)));
views.get("see_angulo").vw.setWidth((int)((40d / 100 * width)));
views.get("see_imu").vw.setWidth((int)((40d / 100 * width)));
views.get("see_yaw").vw.setWidth((int)((40d / 100 * width)));
views.get("see_angulo2").vw.setWidth((int)((40d / 100 * width)));
views.get("see_sencibilidad").vw.setLeft((int)((40d / 100 * width)));
views.get("see_pasa_bajos").vw.setLeft((int)((40d / 100 * width)));
views.get("see_angulo").vw.setLeft((int)((40d / 100 * width)));
views.get("see_imu").vw.setLeft((int)((40d / 100 * width)));
views.get("see_yaw").vw.setLeft((int)((40d / 100 * width)));
views.get("see_gyro_escala").vw.setLeft((int)((40d / 100 * width)));
views.get("see_angulo2").vw.setLeft((int)((40d / 100 * width)));
views.get("lb_sencibilidad").vw.setLeft((int)((90d / 100 * width) - (views.get("lb_sencibilidad").vw.getWidth())));
views.get("lb_pasa_bajos").vw.setLeft((int)((90d / 100 * width) - (views.get("lb_pasa_bajos").vw.getWidth())));
views.get("lb_ang_disparo").vw.setLeft((int)((90d / 100 * width) - (views.get("lb_ang_disparo").vw.getWidth())));
views.get("lb_ang_apagado").vw.setLeft((int)((90d / 100 * width) - (views.get("lb_ang_apagado").vw.getWidth())));
views.get("lb_delay").vw.setLeft((int)((90d / 100 * width) - (views.get("lb_delay").vw.getWidth())));
views.get("lb_yaw").vw.setLeft((int)((90d / 100 * width) - (views.get("lb_yaw").vw.getWidth())));
views.get("lb_gyro_escala").vw.setLeft((int)((90d / 100 * width) - (views.get("lb_gyro_escala").vw.getWidth())));
views.get("tb_invertir_yaw").vw.setLeft((int)((90d / 100 * width) - (views.get("tb_invertir_yaw").vw.getWidth())));
views.get("tb_modo").vw.setLeft((int)((90d / 100 * width) - (views.get("tb_modo").vw.getWidth())));
views.get("tb_invertir").vw.setLeft((int)((90d / 100 * width) - (views.get("tb_invertir").vw.getWidth())));
views.get("tb_eje").vw.setLeft((int)((90d / 100 * width) - (views.get("tb_eje").vw.getWidth())));

}
}