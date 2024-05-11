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

public class STap extends Module {
    /*
    TODO: Epaleee Compadreee El Codigo Es Libre Pero Recuerda darme los creditos si vas a cualquier modulo B)
    */
    private long lastHitTime = 0;
    private long lastActivationTime = 0;
    private boolean isWDeactivated = false;
    private boolean isDelayOver = false;
    private boolean GroundCONFI;
    private boolean isSDesactivated = false;
    private boolean sePuedeOsprint;
    private static final Minecraft mc = Minecraft.getMinecraft();

    public STap() {
        super("STap", "Auto S Tap", Category.COMBAT);
        Dark.instance.settingsManager.rSetting(new Setting("Duracion", this, 150, 5, 300, true));
        Dark.instance.settingsManager.rSetting(new Setting("Cooldown", this, 300, 10, 400, true));
        Dark.instance.settingsManager.rSetting(new Setting("delay", this, 2, 0, 100, true));
        Dark.instance.settingsManager.rSetting(new Setting("MinDist", this, 1.5, 0, 10, false));
        Dark.instance.settingsManager.rSetting(new Setting("MaxDist", this, 3.5, 0, 10, false));
        Dark.instance.settingsManager.rSetting(new Setting("OnlyGround", this, false));
        Dark.instance.settingsManager.rSetting(new Setting("OnlySprint", this, true));
        boolean OnlysprintMode = Dark.instance.settingsManager.getSettingByName(this, "OnlySprint").getValBoolean();
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event == null || event.player == null || event.player.worldObj == null) return; // Verificar nulidad

        boolean isSprinting = Minecraft.getMinecraft().thePlayer.isSprinting();
        float Duracion = (float) Dark.instance.settingsManager.getSettingByName(this, "Duracion").getValDouble();
        float Cooldown = (float) Dark.instance.settingsManager.getSettingByName(this, "Cooldown").getValDouble();
        int Delay = (int) Dark.instance.settingsManager.getSettingByName(this, "delay").getValDouble();
        float MinDist = (float) Dark.instance.settingsManager.getSettingByName(this, "MinDist").getValDouble();
        float MaxDist = (float) Dark.instance.settingsManager.getSettingByName(this, "MaxDist").getValDouble();
        boolean ActivadoGround = Dark.instance.settingsManager.getSettingByName(this, "OnlyGround").getValBoolean();
        boolean OnlysprintMode = Dark.instance.settingsManager.getSettingByName(this, "OnlySprint").getValBoolean();
        if (ActivadoGround != GroundCONFI) {
            GroundCONFI = ActivadoGround;
        }
        if (OnlysprintMode != sePuedeOsprint){
            sePuedeOsprint = OnlysprintMode;
        }
        if (mc.thePlayer.worldObj.isRemote) {
            Entity target = mc.objectMouseOver != null ? mc.objectMouseOver.entityHit : null; // Verificar si objectMouseOver es nulo

            if (mc.gameSettings.keyBindAttack.isKeyDown() && mc.gameSettings.keyBindForward.isKeyDown()  && !mc.gameSettings.keyBindBack.isKeyDown() && target != null) {
                double distance = event.player.getDistanceToEntity(target);
                if (distance >= MinDist && distance <= MaxDist && System.currentTimeMillis() - lastActivationTime >= Cooldown) {
                    if (!GroundCONFI || event.player.onGround) { // Verifica si no es necesario estar en el suelo o si el jugador está en el suelo
                        if (!sePuedeOsprint || isSprinting) {
                            lastHitTime = System.currentTimeMillis();
                            isDelayOver = false;
                        }
                    }
                }
            }

            if (!isDelayOver && System.currentTimeMillis() - lastHitTime >= Delay) { // Espera X ms después de cada golpe
                isWDeactivated = true;
                isSDesactivated = true;
                lastActivationTime = System.currentTimeMillis();
                isDelayOver = true;
            }

            if (isWDeactivated && System.currentTimeMillis() - lastActivationTime >= Duracion) { // Desactiva la 'W' durante 50ms
                isWDeactivated = false;
                isSDesactivated = false;
            }

            if (isWDeactivated) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
            }else {
                if (!(mc.currentScreen instanceof GuiScreen)) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()));
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode()));
                } else {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), false);
                }
            }
        }
    }
}