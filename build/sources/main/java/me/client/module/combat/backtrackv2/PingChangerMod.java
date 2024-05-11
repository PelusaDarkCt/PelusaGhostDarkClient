package me.client.module.combat.backtrackv2;


import me.client.module.combat.backtrackv2.utils.DelayedPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;
import static me.client.module.combat.backtrackv2.PingChanger.isSpoofing;
import static me.client.module.combat.backtrackv2.PingChanger.getAdditionalPing;
public class PingChangerMod {

    public static final ConcurrentLinkedQueue<DelayedPacket> packets = new ConcurrentLinkedQueue<>();
    private static boolean isPingGuiToggled = false;
    private File saveFile;
    private Minecraft mc = Minecraft.getMinecraft();
    public static void toggleConfigGui() {
        isPingGuiToggled = true;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        if (mc.thePlayer!=null) {
            for (DelayedPacket packet : packets) {
                if (System.currentTimeMillis() > packet.getTime() + (isSpoofing() ? getAdditionalPing() : 0)) {
                    try {
                        Packet p = packet.getPacket();
                        p.processPacket(mc.thePlayer.sendQueue.getNetworkManager().getNetHandler());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    packets.remove(packet);
                }
            }
        }
    }
    public void saveSettings() {
        Configuration config = new Configuration(this.saveFile);
        this.updateSettings(config, true);
        config.save();
    }

    public void loadSettings() {
        Configuration config = new Configuration(this.saveFile);
        config.load();
        this.updateSettings(config, false);
    }

    private void updateSettings(Configuration config, boolean save) {
        Property prop = config.get("PingChanger", "enabled", false);
        if (save) {
            prop.set(PingChanger.enabled);
        } else {
            PingChanger.enabled = prop.getBoolean();
        }

        prop = config.get("PingChanger", "additionalPing", 0);
        if (save) {
            prop.set(PingChanger.additionalping);
        } else {
            PingChanger.additionalping = prop.getInt();
        }
    }
}
