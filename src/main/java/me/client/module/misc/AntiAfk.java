package me.client.module.misc;

import me.client.Dark;
import me.client.module.Category;
import me.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AntiAfk extends Module {
    private final KeyBinding jumpKey = Minecraft.getMinecraft().gameSettings.keyBindJump;

    public AntiAfk() {
        super("AntiAfk", "Evita que te Quedes Afk", Category.MISC);
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        if (mc.thePlayer == null || mc.theWorld == null || (Dark.instance.destructed))
            return;

        if (mc.thePlayer.onGround) {
            KeyBinding.setKeyBindState(jumpKey.getKeyCode(), true);
        } else {
            KeyBinding.setKeyBindState(jumpKey.getKeyCode(), false);
        }
    }
}