//package me.tutorial.module.misc;
//
//import java.util.*;
//
//import me.tutorial.module.Category;
//import me.tutorial.module.Module;
//import me.tutorial.module.combat.AimAssist;
//import net.minecraft.client.*;
//import java.lang.reflect.*;
//import net.minecraftforge.fml.common.gameevent.*;
//import net.minecraftforge.fml.common.eventhandler.*;
//
//public class Timer extends Module
//{
//    public Random r;
//    public float tsc;
//    public static AimAssist.NumberValue speed;
//    private float srb;
//    public static Minecraft mc;
//
//    public Timer() {
//        super("Timer", "Modifica el Tiempo del Juego", Category.MISC);
//        this.srb = 1.0f;
//        this.r = new Random();
//        Timer.mc = Minecraft.getMinecraft();
//
//    }
//
//    private void setTimerRate(final float tick) {
//        try {
//            final Field timerField = Minecraft.class.getDeclaredField("field_71428_T");
//            final Field tickPSField = net.minecraft.util.Timer.class.getDeclaredField("field_74278_d");
//            if (timerField != null) {
//                timerField.setAccessible(true);
//                final net.minecraft.util.Timer timer = (net.minecraft.util.Timer)timerField.get(Timer.mc);
//                timerField.setAccessible(false);
//                tickPSField.setAccessible(true);
//                tickPSField.set(timer, 1.0f + (50 - 1.0f));
//                tickPSField.setAccessible(false);
//            }
//            else {
//                System.out.println("timerfield is null");
//            }
//        }
//        catch (Throwable e) {
//            e.printStackTrace();
//        }
//    }
//
//    @SubscribeEvent
//    public void a(final TickEvent.RenderTickEvent e) {
//        this.tsc = this.srb;
//        if (Timer.mc == null) {
//            System.out.println("mc is null");
//            return;
//        }
//        this.setTimerRate(this.tsc);
//    }
//}
