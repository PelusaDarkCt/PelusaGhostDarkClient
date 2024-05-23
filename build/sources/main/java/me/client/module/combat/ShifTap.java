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

public class ShifTap extends Module {
    private boolean mouseButtonDown = false;
    private long lastActivationTime = 0;
    private long lastColdownACT = 0;
    private boolean isSHDeactivated = false;
    private boolean isDelayOver = false;
    private boolean GroundCONFI;
    private boolean sePuedeOsprint;
    private static final Minecraft mc = Minecraft.getMinecraft();

    public ShifTap() {
        super("ShifTap(caution)", "Variante del W tap (no recomendable)", Category.COMBAT);
        Dark.instance.settingsManager.rSetting(new Setting("Duracion", this, 150, 5, 250, true));
        Dark.instance.settingsManager.rSetting(new Setting("Cooldown", this, 300, 10, 400, true));
        Dark.instance.settingsManager.rSetting(new Setting("delay", this, 2, 0, 100, true));
        Dark.instance.settingsManager.rSetting(new Setting("MinDist", this, 1.5, 0, 10, false));
        Dark.instance.settingsManager.rSetting(new Setting("MaxDist", this, 3.5, 0, 10, false));

    }
// ya se yase.... vaya mier## de modulo...
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event == null || event.player == null || event.player.worldObj == null) return; // Verificar nulidad

        float Duracion = (float) Dark.instance.settingsManager.getSettingByName(this, "Duracion").getValDouble();
        float Cooldown = (float) Dark.instance.settingsManager.getSettingByName(this, "Cooldown").getValDouble();
        int Delay = (int) Dark.instance.settingsManager.getSettingByName(this, "delay").getValDouble();
        float MinDist = (float) Dark.instance.settingsManager.getSettingByName(this, "MinDist").getValDouble();
        float MaxDist = (float) Dark.instance.settingsManager.getSettingByName(this, "MaxDist").getValDouble();

        if (mc.thePlayer.worldObj.isRemote) {
            boolean isMouseButtonDown = Mouse.isButtonDown(0);
            Entity target = mc.objectMouseOver.entityHit;

            if (isMouseButtonDown && !mouseButtonDown && mc.gameSettings.keyBindForward.isKeyDown() && !mc.gameSettings.keyBindSneak.isKeyDown() && target != null) {
                double distance = event.player.getDistanceToEntity(target);
                if (distance >= MinDist && distance <= MaxDist && System.currentTimeMillis() - lastColdownACT >= Cooldown) {
                    lastActivationTime = System.currentTimeMillis();
                    isDelayOver = false;
                }
            }
            mouseButtonDown = isMouseButtonDown;

            if (!isDelayOver && System.currentTimeMillis() - lastActivationTime >= Delay) { // Espera X ms después de cada golpe
                isSHDeactivated = true;
                lastActivationTime = System.currentTimeMillis();
                isDelayOver = true;
            }
            if (isSHDeactivated && System.currentTimeMillis() - lastActivationTime >= Duracion) {
                isSHDeactivated = false;
            }

            if (isSHDeactivated) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
            } else {
                if (!(mc.currentScreen instanceof GuiScreen)) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()));
                } else {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
                }
            }
        }
    }
}