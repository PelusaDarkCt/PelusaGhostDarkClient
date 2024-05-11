package me.tutorial.module.render;

import me.tutorial.Tutorial;
import me.tutorial.module.Category;
import me.tutorial.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HUD extends Module {

	public HUD() {
		super("HUD", "Draws the module list on your screen", Category.RENDER);
	}
	
	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent egoe) {
		if (!egoe.type.equals(egoe.type.CROSSHAIRS)) {
			return;
		}
		ScaledResolution sr = new ScaledResolution(Minecraft.func_71410_x());
		int y = 2;
		for (Module mod : Tutorial.instance.moduleManager.getModuleList()) {
			if (!mod.getName().equalsIgnoreCase("HUD") && mod.isToggled() && mod.visible) {
				FontRenderer fr = Minecraft.func_71410_x().field_71466_p;
				fr.func_78276_b(mod.getName(), sr.func_78326_a() - fr.func_78256_a(mod.getName()) - 1, y, 0xFFFFFF);
				y += fr.field_78288_b;
			}
		}
	}
}
