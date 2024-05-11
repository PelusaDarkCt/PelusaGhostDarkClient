package me.client.module.player;

import com.google.common.eventbus.Subscribe;

import me.client.Dark;
import me.client.module.Category;
import me.client.module.Module;
import me.client.module.util.Render2DEvent;
import me.client.module.util.Utils;
import me.client.settings.Setting;
import org.lwjgl.input.Mouse;

import static me.client.module.Module.mc;

public class AutoWeapon extends Module {
    //public static TickSetting onlyWhenHoldingDown;
    // public static TickSetting goBackToPrevSlot;
    private boolean onWeapon;
    private int prevSlot;

    public AutoWeapon() {
        super("AutoWeapon", "Selecciona la mejor Espada", Category.PLAYER);
        Dark.instance.settingsManager.rSetting(new Setting("Revert to old slot", this, true));
        double onlyWhenHoldingDown = Dark.instance.settingsManager.getSettingByName(this, "Revert to old slot").getValDouble();
        //  this.registerSetting(onlyWhenHoldingDown = new TickSetting("Only when holding lmb", true));
        //this.registerSetting(goBackToPrevSlot = new TickSetting("Revert to old slot", true));
    }

    @Subscribe
    public void onRender2D(Render2DEvent ev) {
        if (mc.thePlayer == null || mc.theWorld == null)
            return;
        boolean onlyWhenHoldingDown = Dark.instance.settingsManager.getSettingByName(this, "Revert to old slot").getValBoolean();
        if (mc.objectMouseOver == null || mc.objectMouseOver.entityHit == null && !Mouse.isButtonDown(0)) {
            if (onWeapon) {
                onWeapon = false;
                mc.thePlayer.inventory.currentItem = prevSlot;
            }
        } else {
            if (onlyWhenHoldingDown) {
                if (!Mouse.isButtonDown(0))
                    return;
            }

            if (!onWeapon) {
                prevSlot = mc.thePlayer.inventory.currentItem;
                onWeapon = true;

                int maxDamageSlot = Utils.Player.getMaxDamageSlot();

                if (maxDamageSlot > 0 && Utils.Player.getSlotDamage(maxDamageSlot) > Utils.Player
                        .getSlotDamage(mc.thePlayer.inventory.currentItem)) {
                    mc.thePlayer.inventory.currentItem = maxDamageSlot;
                }
            }
        }
    }
}//TODO: Sep, Mas Adelante Arreglare Todo