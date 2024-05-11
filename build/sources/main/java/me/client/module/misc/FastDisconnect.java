package me.client.module.misc;

import me.client.Dark;
import me.client.module.Category;
import me.client.module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FastDisconnect extends Module {

    private boolean originalConnectionState = true;

    public FastDisconnect() {
        super("FastLeave", "Se Desconecta Muy *Op* para trollear", Category.MISC);
    }

    @Override
    public void onEnable() {
        if (mc.thePlayer == null || mc.theWorld == null || (Dark.instance.destructed))
            return;
        // Guardar el estado original de la conexión
        originalConnectionState = mc.isSingleplayer() || mc.getIntegratedServer() != null;

        // Deshabilitar la recepción de paquetes si estamos en un servidor multiplayer
        if (!originalConnectionState && mc.thePlayer != null && mc.getNetHandler() != null) {
            mc.getNetHandler().getNetworkManager().closeChannel(null);
        }
    }

    @Override
    public void onDisable() {
        if (mc.thePlayer == null || mc.theWorld == null || (Dark.instance.destructed))
            return;
        // Restaurar el estado original de la conexión si estamos en un servidor multiplayer
        if (!originalConnectionState && mc.getNetHandler() != null) {
            mc.getNetHandler().getNetworkManager().checkDisconnected();
        }
    }

    @SubscribeEvent
    public void onPacket(TickEvent.PlayerTickEvent event) {
        if (mc.thePlayer != null && event.phase == TickEvent.Phase.START && !originalConnectionState) {
            event.setCanceled(true);
        }
    }
}
