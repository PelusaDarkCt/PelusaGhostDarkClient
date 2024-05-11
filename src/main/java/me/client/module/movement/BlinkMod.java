/*package me.client.module.movement;

import java.util.ArrayList;

import com.google.common.eventbus.Subscribe;
import me.client.module.Category;
import me.client.module.Module;
import me.client.module.util.PacketEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public final class BlinkMod extends Module {
    private final ArrayList<Packet> packets = new ArrayList<>();
    private EntityOtherPlayerMP fakePlayer = null;

    public BlinkMod() {
        super("BlinkMod", "Suspends all movement packets.", Category.MOVEMENT);
    }

    @Override
    public void onEnable() {
        fakePlayer = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
        fakePlayer.clonePlayer(mc.thePlayer, true);
        fakePlayer.copyLocationAndAnglesFrom(mc.thePlayer);
        fakePlayer.rotationYawHead = mc.thePlayer.rotationYawHead;
        mc.theWorld.addEntityToWorld(-1337, fakePlayer);
    }

    @Override
    public void onDisable() {
        if (fakePlayer != null) {
            mc.theWorld.removeEntityFromWorld(fakePlayer.getEntityId());
            fakePlayer = null;
        }
        sendPackets();
        packets.clear();
    }

    @Subscribe
    public void onPacket(PacketEvent event) {
        Packet<?> packet = event.getPacket();

        if (mc.thePlayer == null)
            return;

        /*if (packet instanceof C03PacketPlayer ||
                packet instanceof C03PacketPlayer.C04PacketPlayerPosition ||
                packet instanceof C03PacketPlayer.C06PacketPlayerPosLook ||
                packet instanceof C08PacketPlayerBlockPlacement ||
                packet instanceof C0APacketAnimation ||
                packet instanceof C0BPacketEntityAction ||
                packet instanceof C02PacketUseEntity) {*/
   /*     if(event.getPacket() instanceof C03PacketPlayer ||  packet instanceof C03PacketPlayer.C04PacketPlayerPosition ||
                packet instanceof C03PacketPlayer.C06PacketPlayerPosLook ||
                packet instanceof C08PacketPlayerBlockPlacement ||
                packet instanceof C0APacketAnimation ||
                packet instanceof C0BPacketEntityAction ||
                packet instanceof C02PacketUseEntity){
            event.setCanceled(true);
            packets.add(packet);
        }
    }

    private void sendPackets() {
        for (Packet packet : packets) {
            mc.getNetHandler().addToSendQueue(packet);
        }
        packets.clear();
    }
}
*/