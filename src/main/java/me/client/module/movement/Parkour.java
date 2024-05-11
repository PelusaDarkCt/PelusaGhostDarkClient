package me.client.module.movement;

import me.client.module.Category;
import me.client.module.Module;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Parkour extends Module {
    private boolean isActive = false;

    public Parkour() {
        super("Parkour", "Salta En EL borde Del Bloque", Category.MOVEMENT);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        setJumping(false);
    }/*TODO: Modo Roba Codigo Activado jeje, tranquilo solo es este... jeje...... pero hey gracias a Raben b++ que me *presto* el codigo y yo lo adapte, asi tu debes darme las putas gracias perro
    */

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (mc.thePlayer == null || mc.theWorld == null)
            return;

        if (mc.thePlayer.onGround && !mc.thePlayer.isSneaking()) {
            if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(mc.thePlayer.motionX / 3.0D, -1.0D, mc.thePlayer.motionZ / 3.0D)).isEmpty()) {
                setJumping(true);
            } else if (isActive) {
                setJumping(false);
            }
        } else if (isActive) {
            setJumping(false);
        }
    }

    private void setJumping(boolean jump) {
        if (this.isActive != jump) {
            this.isActive = jump;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), jump);
        }
    }
}
