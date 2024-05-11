/*package me.client.module.misc;

import com.google.common.eventbus.Subscribe;
import me.client.module.Category;
import me.client.module.Module;
import me.client.module.util.PacketEvent;
import me.client.module.values.UpdateEvent;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.LinkedList;
import java.util.Queue;

public class Blink extends Module {

    private final Queue<Packet<?>> packets = new LinkedList<>();
    private EntityOtherPlayerMP fakePlayer = null;
    private final LinkedList<double[]> positions = new LinkedList<>();

    public Blink() {
        super("Blink", "Suspends all movement packets.", Category.MISC);
    }

    @Override
    public void onEnable() {
        if (mc.thePlayer == null)
            return;

        fakePlayer = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
        fakePlayer.clonePlayer(mc.thePlayer, true);
        fakePlayer.copyLocationAndAnglesFrom(mc.thePlayer);
        fakePlayer.rotationYawHead = mc.thePlayer.rotationYawHead;
        mc.theWorld.addEntityToWorld(-1337, fakePlayer);

        synchronized (positions) {
            positions.add(new double[]{mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY + (mc.thePlayer.getEyeHeight() / 2), mc.thePlayer.posZ});
            positions.add(new double[]{mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY, mc.thePlayer.posZ});
        }
    }

    @Override/*TODO: eta vaina ta tryhard

    public void onDisable() {
        if (mc.thePlayer == null)
            return;

        blink();
        if (fakePlayer != null) {
            mc.theWorld.removeEntityFromWorld(fakePlayer.getEntityId());
            fakePlayer = null;
        }
    }

    @Subscribe
    public void onPacket(PacketEvent event) {
        Packet<?> packet = event.getPacket();

        if (mc.thePlayer == null)
            return;

        if (packet instanceof S08PacketPlayerPosLook || packet instanceof S14PacketEntity || packet instanceof S18PacketEntityTeleport) {
           // event.cancelEvent();
            packets.add(packet);

        } else if (packet instanceof C03PacketPlayer || packet instanceof C0BPacketEntityAction || packet instanceof C0APacketAnimation ||
                packet instanceof C0FPacketConfirmTransaction || packet instanceof C08PacketPlayerBlockPlacement ||
                packet instanceof C02PacketUseEntity) {
           // event.cancelEvent();
            packets.add(packet);
        }
    }

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        if (fakePlayer != null) {
            C03PacketPlayer fakePacket = new C03PacketPlayer.C04PacketPlayerPosition(
                    fakePlayer.posX, fakePlayer.posY, fakePlayer.posZ, false
            );
            mc.getNetHandler().addToSendQueue(fakePacket);
            mc.thePlayer.setPositionAndRotation(fakePlayer.posX, fakePlayer.posY, fakePlayer.posZ, fakePlayer.rotationYaw, fakePlayer.rotationPitch);
        }
    }

    private void blink() {
        try {
            while (!packets.isEmpty()) {
                mc.getNetHandler().getNetworkManager().sendPacket(packets.poll());
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        synchronized (positions) {
            positions.clear();
        }
    }
}
*/