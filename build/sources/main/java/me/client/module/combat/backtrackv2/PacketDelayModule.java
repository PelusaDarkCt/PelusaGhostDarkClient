package me.client.module.combat.backtrackv2;

import me.client.Dark;
import me.client.module.Category;
import me.client.module.Module;
import me.client.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PacketDelayModule extends Module {
    private int delayTicks = 0;
    private int currentDelay = 0;
    private boolean isActive = false;
    private int additionalPing = 100; // Valor predeterminado

    public PacketDelayModule() {
        super("PacketDelay", "Suspends incoming movement packets", Category.COMBAT);
        Dark.instance.settingsManager.rSetting(new Setting("AdditionalPing", this, additionalPing, 0, 1000, true));
    }

    @Override
    public void onEnable() {
        isActive = true;
        additionalPing = (int) Dark.instance.settingsManager.getSettingByName(this, "AdditionalPing").getValDouble();
    }

    @Override
    public void onDisable() {
        isActive = false;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (isActive) {
            if (currentDelay >= delayTicks) {
                PingChanger.setEnabled(true);
            } else {
                PingChanger.setEnabled(false);
                currentDelay++;
            }
        } else {
            PingChanger.setEnabled(false);
        }
    }

    public void updateAdditionalPing(int value) {
        additionalPing = value;
        Dark.instance.settingsManager.getSettingByName(this, "AdditionalPing").setValDouble(value);
    }
}