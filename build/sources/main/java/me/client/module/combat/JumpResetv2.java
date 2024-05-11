package me.client.module.combat;

import com.google.common.eventbus.Subscribe;
import me.client.module.util.*;
import me.client.module.Category;
import me.client.module.Module;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import static me.client.module.util.Utils.mc;

public class JumpResetv2 extends Module {
    public JumpResetv2() {
        super("JumpResetv2", "Reduce el kb legit", Category.COMBAT);
    }
    @Subscribe
    public void onPacket(PacketEvent e) {
        if (e.isIncoming()) {
            if (e.getPacket() instanceof S12PacketEntityVelocity) {
                if (((S12PacketEntityVelocity) e.getPacket()).getEntityID() == mc.thePlayer.getEntityId()) {
                    if(mc.thePlayer.onGround) {
                        mc.thePlayer.jump();
                    }
                }
            }
        }
    }
}
