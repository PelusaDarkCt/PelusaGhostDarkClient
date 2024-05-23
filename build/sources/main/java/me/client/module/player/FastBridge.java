package me.client.module.player;

import me.client.Dark;
import me.client.module.Category;
import me.client.module.Module;
import me.client.settings.Setting;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import scala.xml.Null;


public class FastBridge extends Module {
    private boolean ConBloqueP;
    private boolean isSneaking = false;
    public FastBridge() {
        super("FastBridge", "Auto Sneak En EL borde Del Bloque", Category.PLAYER);
        Dark.instance.settingsManager.rSetting(new Setting("OnlyBlocks",this,true));
    }

    @Override
    public void onDisable() {
        super.onDisable();
        setSneaking(false);

    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (mc.thePlayer == null || mc.theWorld == null)
            return;

        boolean OnlyBlockAct = Dark.instance.settingsManager.getSettingByName(this,"OnlyBlocks").getValBoolean();
        if (OnlyBlockAct != ConBloqueP) {
            ConBloqueP = OnlyBlockAct;
        }

        // Calcular una posición un poco adelante del jugador
        if (ConBloqueP && isBlockHand() || !ConBloqueP){
        double offsetX = mc.thePlayer.motionX / 0.40D;
        double offsetY = -1.0D;  // Ajustar la altura de detección
        double offsetZ = mc.thePlayer.motionZ / 0.40D;

        // Verificar si hay colisiones en la posición adelantada
        boolean isOnEdge = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(offsetX, offsetY, offsetZ)).isEmpty();

        // Si el jugador está cerca del borde pero no demasiado, agacharse
        if (isOnEdge) {
            setSneaking(true);
        } else { // Si no está en el borde, dejar de agacharse
            setSneaking(false);
        }
    }
}

    private void setSneaking(boolean sneaking) {
        if (this.isSneaking != sneaking) {
            this.isSneaking = sneaking;
            if (mc.thePlayer.onGround) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), sneaking);
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