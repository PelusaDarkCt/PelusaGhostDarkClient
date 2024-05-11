package me.client.module.combat;

import me.client.Dark;
import me.client.module.Category;
import me.client.module.Module;
import me.client.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static me.client.module.util.utilities.MathUtil.random;

public class JumpReset extends Module {

    private boolean recentlyHit;
    private long lastJumpTime;
    private long lastHitTime;
   // private int cooldown;
    private boolean ForwardOpt;
    public JumpReset() {
        super("JumpReset", "Reduce el knockback De Manera Legit", Category.COMBAT);
        //Dark.instance.settingsManager.rSetting(new Setting("Cooldown", this, 100, 0, 500, true));
        Dark.instance.settingsManager.rSetting(new Setting("Chanse", this, 0.5, 0.1, 1.0, false));
        Dark.instance.settingsManager.rSetting(new Setting("OnlyForWard", this, true));

        //Dark.instance.settingsManager.rSetting(new Setting("Jump(warning)", this, 0.42, 0.1, 1.0, false));
    }

    @SubscribeEvent
    public void onLivingHurt(net.minecraftforge.event.entity.living.LivingHurtEvent event) {
        if (event.entityLiving != null && event.entityLiving.equals(mc.thePlayer)) {
                recentlyHit = true;
            lastHitTime = System.currentTimeMillis();
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        if (Dark.instance.destructed) {
            return;
        }
        if (mc.thePlayer != null && mc.thePlayer.isEntityAlive()) {
            boolean Foward = Minecraft.getMinecraft().thePlayer.moveForward != 0;
            boolean OptForwATVIDAO = Dark.instance.settingsManager.getSettingByName(this,"OnlyForward").getValBoolean();
            double Chanse = Dark.instance.settingsManager.getSettingByName(this,"Chanse").getValDouble();

            if (OptForwATVIDAO != ForwardOpt){
                ForwardOpt = OptForwATVIDAO;
            }
            // cooldown = (int) Dark.instance.settingsManager.getSettingByName(this, "Cooldown").getValDouble();
            long currentTime = System.currentTimeMillis();
            long tickDelay = 20; // X tick equivale a X ms

            if (recentlyHit && currentTime - lastHitTime >= tickDelay && mc.thePlayer.onGround && Foward && ForwardOpt ||
                    recentlyHit && currentTime - lastHitTime >= tickDelay && mc.thePlayer.onGround && !ForwardOpt) {

                if (random.nextDouble() <= Chanse) {
                        mc.thePlayer.jump();
                        recentlyHit = false;
                        lastJumpTime = currentTime;

                   }   else {
                    recentlyHit = false;
                }
            }/*else{
                    recentlyHit = false;
                }*/
        }
    }
}