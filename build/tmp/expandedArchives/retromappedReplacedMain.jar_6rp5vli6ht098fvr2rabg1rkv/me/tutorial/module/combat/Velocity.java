package me.tutorial.module.combat;

import me.tutorial.Tutorial;
import me.tutorial.module.Category;
import me.tutorial.module.Module;
import me.tutorial.settings.Setting;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Velocity extends Module {

	public Velocity() {
		super("Velocity", "Reduces knockback", Category.COMBAT);
		Tutorial.instance.settingsManager.rSetting(new Setting("Horizontal", this, 90, 0, 100, true));
		Tutorial.instance.settingsManager.rSetting(new Setting("Vertical", this, 100, 0, 100, true));
	}
	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent e) {
		float horizontal = (float) Tutorial.instance.settingsManager.getSettingByName(this, "Horizontal").getValDouble();
		float vertical = (float) Tutorial.instance.settingsManager.getSettingByName(this, "Vertical").getValDouble();
		
		if (mc.field_71439_g.field_70737_aN == mc.field_71439_g.field_70738_aO && mc.field_71439_g.field_70738_aO > 0) {
			mc.field_71439_g.field_70159_w *= (float) horizontal / 100;
			mc.field_71439_g.field_70181_x *= (float) vertical / 100;
			mc.field_71439_g.field_70179_y *= (float) horizontal / 100;
		}
	}
}
