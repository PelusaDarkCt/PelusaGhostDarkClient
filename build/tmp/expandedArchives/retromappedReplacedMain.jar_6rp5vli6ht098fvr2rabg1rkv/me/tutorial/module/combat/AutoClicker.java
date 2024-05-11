package me.tutorial.module.combat;

import org.lwjgl.input.Mouse;

import io.netty.util.internal.ThreadLocalRandom;
import me.tutorial.Tutorial;
import me.tutorial.module.Category;
import me.tutorial.module.Module;
import me.tutorial.settings.Setting;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoClicker extends Module {

	private long lastClick;
	private long hold;
	
	private double speed;
	private double holdLength;
	private double min;
	private double max;
	
	public AutoClicker() {
		super("AutoClicker", "Automatically clicks when you hold down left click", Category.COMBAT);
		
		Tutorial.instance.settingsManager.rSetting(new Setting("MinCPS", this, 8, 1, 20, false));
		Tutorial.instance.settingsManager.rSetting(new Setting("MaxCPS", this, 12, 1, 20, false));
	}
	
	@SubscribeEvent
	public void onTick(TickEvent.RenderTickEvent e) {
		if (Mouse.isButtonDown(0)) {
			if (System.currentTimeMillis() - lastClick > speed * 1000) {
				lastClick = System.currentTimeMillis();
				if (hold < lastClick) {
					hold = lastClick;
				}
				int key = mc.field_71474_y.field_74312_F.func_151463_i();
				KeyBinding.func_74510_a(key, true);
				KeyBinding.func_74507_a(key);
				this.updateVals();
			} else if (System.currentTimeMillis() - hold > holdLength * 1000) {
				KeyBinding.func_74510_a(mc.field_71474_y.field_74312_F.func_151463_i(), false);
				this.updateVals();
			}
		}
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		this.updateVals();
	}
	
	private void updateVals() {
		min = Tutorial.instance.settingsManager.getSettingByName(this, "MinCPS").getValDouble();
		max = Tutorial.instance.settingsManager.getSettingByName(this, "MaxCPS").getValDouble();
		
		if (min >= max) {
			max = min + 1;
		}
		
		speed = 1.0 / ThreadLocalRandom.current().nextDouble(min - 0.2, max);
		holdLength = speed / ThreadLocalRandom.current().nextDouble(min, max);
	}
}
