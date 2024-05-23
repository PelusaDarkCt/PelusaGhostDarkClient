package me.client.module.combat;

import me.client.Dark;
import me.client.module.Category;
import me.client.module.Module;
import me.client.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Wtap extends Module {
    private boolean mouseButtonDown = false;
    private long lastActivationTime = 0;
    private long lastColdownACT = 0;
    private boolean isWDeactivated = false;
    private boolean isDelayOver = false;

    private static final Minecraft mc = Minecraft.getMinecraft();

    public Wtap() {
        super("WTap", "Auto w Tap sep", Category.COMBAT);
        Dark.instance.settingsManager.rSetting(new Setting("Duracion (ms)", this, 150, 5, 200, true));
        Dark.instance.settingsManager.rSetting(new Setting("Cooldown (ms)", this, 300, 10, 400, true));
        Dark.instance.settingsManager.rSetting(new Setting("delay (ms)", this, 2, 0, 100, true));
        Dark.instance.settingsManager.rSetting(new Setting("MinDist", this, 1.5, 0, 10, false));
        Dark.instance.settingsManager.rSetting(new Setting("MaxDist", this, 3.5, 0, 10, false));

    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event == null || event.player == null || event.player.worldObj == null) return; // Verificar nulidad

        float Duracion = (float) Dark.instance.settingsManager.getSettingByName(this, "Duracion (ms)").getValDouble();
        float Cooldown = (float) Dark.instance.settingsManager.getSettingByName(this, "Cooldown (ms)").getValDouble();
        int Delay = (int) Dark.instance.settingsManager.getSettingByName(this, "delay (ms)").getValDouble();
        float MinDist = (float) Dark.instance.settingsManager.getSettingByName(this, "MinDist").getValDouble();
        float MaxDist = (float) Dark.instance.settingsManager.getSettingByName(this, "MaxDist").getValDouble();

        if (mc.thePlayer.worldObj.isRemote) {
            boolean isMouseButtonDown = Mouse.isButtonDown(0);
            Entity target = mc.objectMouseOver.entityHit;

                    if (isMouseButtonDown && !mouseButtonDown && mc.gameSettings.keyBindForward.isKeyDown() && target != null) {
                        double distance = event.player.getDistanceToEntity(target);
                        if (distance >= MinDist && distance <= MaxDist && System.currentTimeMillis() - lastColdownACT >= Cooldown) {
                            lastActivationTime = System.currentTimeMillis();
                            isDelayOver = false;
                        }
                    }
            mouseButtonDown = isMouseButtonDown;

            if (!isDelayOver && System.currentTimeMillis() - lastActivationTime >= Delay) { // Espera X ms después de cada golpe
                isWDeactivated = true;
                lastActivationTime = System.currentTimeMillis();
                isDelayOver = true;
            }
            if (isWDeactivated && System.currentTimeMillis() - lastActivationTime >= Duracion) { // Desactiva la 'W' durante 50ms
                isWDeactivated = false;
            }

            if (isWDeactivated) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
            } else {
                if (!(mc.currentScreen instanceof GuiScreen)) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()));
                } else {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
                }
            }
        }
    }
}
