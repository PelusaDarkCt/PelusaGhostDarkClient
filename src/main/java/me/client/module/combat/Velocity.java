package me.client.module.combat;

import me.client.Dark;
import me.client.module.Category;
import me.client.module.Module;
import me.client.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class Velocity extends Module {
	boolean groundAct;
	boolean moveAct;
	boolean sprintAct;
	private static final Random random = new Random();
	public Velocity() {
		super("Velocity", "Reduce el knockback", Category.COMBAT);
		Dark.instance.settingsManager.rSetting(new Setting("Horizontal", this, 90, 0, 100, true));
		Dark.instance.settingsManager.rSetting(new Setting("Vertical", this, 100, 0, 100, true));
		Dark.instance.settingsManager.rSetting(new Setting("Chanse", this, 0.7, 0.1, 1.0, false));
		Dark.instance.settingsManager.rSetting(new Setting("OnlyGround", this,false));
		Dark.instance.settingsManager.rSetting(new Setting("OnlyMove", this,true));
		Dark.instance.settingsManager.rSetting(new Setting("OnlySprint", this,false));
	}

	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent e) {
		if (Dark.instance.destructed) {
			return;
		}
		if (mc.thePlayer == null) {
			return;
		}
		boolean OnlygrunCOnf = Dark.instance.settingsManager.getSettingByName(this,"OnlyGround").getValBoolean();
		boolean OnlymoveCOnf = Dark.instance.settingsManager.getSettingByName(this,"OnlyMove").getValBoolean();
		boolean OnlysprintCOnf = Dark.instance.settingsManager.getSettingByName(this,"OnlySprint").getValBoolean();
		double Chanse = Dark.instance.settingsManager.getSettingByName(this,"Chanse").getValDouble();

		if (OnlymoveCOnf != moveAct){
            moveAct = OnlymoveCOnf;
        }
		if (OnlysprintCOnf != sprintAct){
            sprintAct = OnlysprintCOnf;
        }
		if (OnlygrunCOnf != groundAct){
            groundAct = OnlygrunCOnf;
        }

		boolean onGround = mc.thePlayer.onGround;
		boolean isMoving = Minecraft.getMinecraft().thePlayer.moveForward != 0 || Minecraft.getMinecraft().thePlayer.moveStrafing != 0;
		boolean isSprinting = Minecraft.getMinecraft().thePlayer.isSprinting();

		float horizontal = (float) Dark.instance.settingsManager.getSettingByName(this, "Horizontal").getValDouble();
		float vertical = (float) Dark.instance.settingsManager.getSettingByName(this, "Vertical").getValDouble();

		//TODO: se que se puede simplificar.... pero es mi CODIGO Y SON MIS REGLAS >:I (tengo alzaimer)


	if (sprintAct && isSprinting ||!sprintAct){
		if (moveAct && isMoving || !moveAct){
		if (groundAct && onGround ||!groundAct){
		if (mc.thePlayer.hurtTime == mc.thePlayer.maxHurtTime && mc.thePlayer.maxHurtTime > 0) {
			if (random.nextDouble() <= Chanse) {
			mc.thePlayer.motionX *= (float) horizontal / 100;
			mc.thePlayer.motionY *= (float) vertical / 100;
			mc.thePlayer.motionZ *= (float) horizontal / 100;
		}
		}
		}
		}
		}
	}

}
