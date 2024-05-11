package me.client.module.util;

import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.settings.*;

public class Wrapper
{
    public static Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }
    
    public static EntityPlayerSP getPlayer() {
        return getMinecraft().thePlayer;
    }
    
    public static WorldClient getWorld() {
        return getMinecraft().theWorld;
    }
    
    public static PlayerControllerMP getPlayerController() {
        return getMinecraft().playerController;
    }
    
    public static GameSettings getGameSettings() {
        return getMinecraft().gameSettings;
    }
}