package me.client.module.combat;

import me.client.Dark;
import me.client.module.Category;
import me.client.module.Module;
import me.client.settings.Setting;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ButtonExperimento extends Module {
    private boolean activatem;

    public ButtonExperimento() {
        super("BUTTONtest", "Prueba de funcionamiento", Category.COMBAT);
        Dark.instance.settingsManager.rSetting(new Setting("BOTONtest", this, false));
        Dark.instance.settingsManager.rSetting(new Setting("Horizontal", this, 90, 0, 100, true));
        Dark.instance.settingsManager.rSetting(new Setting("Vertical", this, 100, 0, 100, true));
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {
        if (Dark.instance.destructed || mc.thePlayer == null) {
            return;
        }

        // Obtener el estado actual del botón
        boolean botonActivado = Dark.instance.settingsManager.getSettingByName(this, "BOTONtest").getValBoolean();

        // Verificar si el estado del botón ha cambiado
        if (botonActivado != activatem) {
            activatem = botonActivado;
        }

        // Verificar si el botón está activado para aplicar la velocidad
        if (activatem) {
            float horizontal = (float) Dark.instance.settingsManager.getSettingByName(this, "Horizontal").getValDouble();
            float vertical = (float) Dark.instance.settingsManager.getSettingByName(this, "Vertical").getValDouble();

            if (mc.thePlayer.hurtTime == mc.thePlayer.maxHurtTime && mc.thePlayer.maxHurtTime > 0) {
                mc.thePlayer.motionX *= (float) horizontal / 100;
                mc.thePlayer.motionY *= (float) vertical / 100;
                mc.thePlayer.motionZ *= (float) horizontal / 100;
            }
        }
    }
}
/*na mas pa probar*/