package me.client.module.render;

import me.client.Dark;
import me.client.module.Category;
import me.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HUD extends Module {

	public HUD() {
		super("HUD", "Muestra Los Modulos Que Tienes Activados", Category.RENDER);
	}

	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent egoe) {
		if (!egoe.type.equals(egoe.type.CROSSHAIRS) || Dark.instance.destructed) {
			return;
		}
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		int y = 2;
		for (Module mod : Dark.instance.moduleManager.getModuleList()) {
			if (!mod.getName().equalsIgnoreCase("HUD") && mod.isToggled() && mod.visible) {
				FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
				String moduleName = mod.getName();
				int color = 0x3CCDAC; // Color
				int shadowColor = 0x000000; // Color negro para la sombra
				fr.drawStringWithShadow(moduleName, sr.getScaledWidth() - fr.getStringWidth(moduleName) - 1, y, color);
				y += fr.FONT_HEIGHT + 2; // Aumentar un poco el tamaño
			}
		}
	}
}
