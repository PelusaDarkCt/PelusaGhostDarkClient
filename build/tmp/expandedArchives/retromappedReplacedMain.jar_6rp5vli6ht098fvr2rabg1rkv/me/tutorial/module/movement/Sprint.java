package me.tutorial.module.movement;

import me.tutorial.module.Category;
import me.tutorial.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Sprint extends Module {

	public Sprint() {
		super("Sprint", "Always holds down the sprint key", Category.MOVEMENT);
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent e) {
		KeyBinding.func_74510_a(mc.field_71474_y.field_151444_V.func_151463_i(), true);
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		KeyBinding.func_74510_a(mc.field_71474_y.field_151444_V.func_151463_i(), false);
	}
}
