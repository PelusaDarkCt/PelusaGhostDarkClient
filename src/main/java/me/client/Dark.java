package me.client;

import org.lwjgl.input.Keyboard;

import me.client.autosave.SaveLoad;
import me.client.clickgui.ClickGui;
import me.client.module.Module;
import me.client.module.ModuleManager;
import me.client.settings.SettingsManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class Dark
{
    public static Dark instance;
    public ModuleManager moduleManager;
    public SettingsManager settingsManager;
    public ClickGui clickGui;
    public SaveLoad saveLoad;
    
    public boolean destructed = false;
    public void init() {
    	MinecraftForge.EVENT_BUS.register(this);
    	settingsManager = new SettingsManager();
    	moduleManager = new ModuleManager();
    	clickGui = new ClickGui();
    	saveLoad = new SaveLoad();
    }
    
    @SubscribeEvent
    public void key(KeyInputEvent e) {
    	if (Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer == null)
    		return; 
    	try {
             if (Keyboard.isCreated()) {
                 if (Keyboard.getEventKeyState()) {
                     int keyCode = Keyboard.getEventKey();
                     if (keyCode <= 0)
                    	 return;
                     for (Module m : moduleManager.modules) {
                    	 if (m.getKey() == keyCode && keyCode > 0) {
                    		 m.toggle();
                    	 }
                     }
                 }
             }
         } catch (Exception q) { q.printStackTrace(); }
    }
    
    public void onDestruct() {
    	if (Minecraft.getMinecraft().currentScreen != null && Minecraft.getMinecraft().thePlayer != null) {
    		Minecraft.getMinecraft().thePlayer.closeScreen();
    	}
    	destructed = true;
    	MinecraftForge.EVENT_BUS.unregister(this);
    	for (int k = 0; k < this.moduleManager.modules.size(); k++) {
    		Module m = this.moduleManager.modules.get(k);
    		MinecraftForge.EVENT_BUS.unregister(m);
    		this.moduleManager.getModuleList().remove(m);
    	}
    	this.moduleManager = null;
    	this.clickGui = null;
    }
}
