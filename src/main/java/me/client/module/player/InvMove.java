package me.client.module.player;

import me.client.module.Category;
import me.client.module.Module;
import me.client.module.util.utilities.StringRegistry;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class InvMove extends Module
{
    private int left;
    private int right;
    private int backkey;
    private int forward;

    public InvMove() {
        super("InvMove","Permite El Movimiento Teniendo el Ivn Abierto",Category.PLAYER);
        this.left = 30;
        this.right = 32;
        this.backkey = Minecraft.getMinecraft().gameSettings.keyBindBack.getKeyCode();
        this.forward = Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode();
    }

    /*@Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
    }*/
}
