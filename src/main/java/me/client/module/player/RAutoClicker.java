package me.client.module.player;

import io.netty.util.internal.ThreadLocalRandom;
import me.client.Dark;
import me.client.module.Category;
import me.client.module.Module;
import me.client.settings.Setting;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class RAutoClicker extends Module {

    private long lastClick;
    private long hold;
    private double speed;
    private double holdLength;
    private int min;
    private int max;
    private boolean ConBloqueP;
public RAutoClicker(){
    super("RAutoclicker(unsafe)","Ten Cuidado con este (usalo bajo tu propio riesgo)", Category.PLAYER);
    Dark.instance.settingsManager.rSetting(new Setting("MinRCPS", this, 8, 1, 25, true));
    Dark.instance.settingsManager.rSetting(new Setting("MaxRCPS", this, 12, 1, 25, true));
    Dark.instance.settingsManager.rSetting(new Setting("OnlyBlocks",this,true));
}
@SubscribeEvent
public void onTick(TickEvent.RenderTickEvent e) {
    if (Dark.instance.destructed) {
        return;
    }
    boolean OnlyBlockAct = Dark.instance.settingsManager.getSettingByName(this,"OnlyBlocks").getValBoolean();
    if (OnlyBlockAct != ConBloqueP) {
        ConBloqueP = OnlyBlockAct;
    }
    if (Mouse.isButtonDown(1)) {
        if (!ConBloqueP || isBlockHand()) {
            if (System.currentTimeMillis() - lastClick > speed * 1000) {
                lastClick = System.currentTimeMillis();
                if (hold < lastClick) {
                    hold = lastClick;
                }
                int key = mc.gameSettings.keyBindUseItem.getKeyCode();
                KeyBinding.setKeyBindState(key, true);
                KeyBinding.onTick(key);
                this.updateVals();
            } else if (System.currentTimeMillis() - hold > holdLength * 1000) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                this.updateVals();
            }
        }
    }
}
@Override
public void onEnable() {
    super.onEnable();
    this.updateVals();
}

private void updateVals() {
    min = (int) Dark.instance.settingsManager.getSettingByName(this, "MinRCPS").getValDouble();
    max = (int) Dark.instance.settingsManager.getSettingByName(this, "MaxRCPS").getValDouble();

    if (min >= max) {
        max = min + 1;
    }

    speed = 1.0 / ThreadLocalRandom.current().nextDouble(min - 0.2, max);
    holdLength = speed / ThreadLocalRandom.current().nextDouble(min, max);
}
    private boolean isBlockHand(){
        if (mc.thePlayer == null){
            return false;
        }
        if (mc.thePlayer.getHeldItem() == null){
            return false;
        }
        return this.mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock;
    }
}
