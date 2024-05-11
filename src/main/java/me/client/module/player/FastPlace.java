package me.client.module.player;

import me.client.Dark;
import me.client.module.Category;
import me.client.module.Module;
import me.client.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.input.Mouse;

import java.lang.reflect.Field;

public class FastPlace extends Module {
    private final Field rightClickDelayTimerField;
    private boolean ConBloqueP;
    public FastPlace() {
        super("FastPlace", "Coloca bloques rapidamente sin el retraso de Minecraft", Category.PLAYER);
        Dark.instance.settingsManager.rSetting(new Setting("OnlyBlocks",this,false));
        rightClickDelayTimerField = ReflectionHelper.findField(Minecraft.class, "rightClickDelayTimer", "field_71467_ac");
        if (rightClickDelayTimerField != null) {
            rightClickDelayTimerField.setAccessible(true);
        }
        Dark.instance.settingsManager.rSetting(new Setting("Delay", this, 0.10, 0.1, 4.0, false));
    }

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event) {
        boolean OnlyBlockAct = Dark.instance.settingsManager.getSettingByName(this,"OnlyBlocks").getValBoolean();
        if (OnlyBlockAct != ConBloqueP) {
            ConBloqueP = OnlyBlockAct;
        }
        if (ConBloqueP && isBlockHand() || !ConBloqueP){
        if (event.phase == TickEvent.Phase.END && Mouse.isButtonDown(1) && rightClickDelayTimerField != null) {
            try {
                int delay = (int) Dark.instance.settingsManager.getSettingByName(this, "Delay").getValDouble();
                rightClickDelayTimerField.setInt(mc, delay);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }
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