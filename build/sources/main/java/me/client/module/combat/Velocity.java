package me.client.module.combat;

import me.client.Dark;
import me.client.module.Category;
import me.client.module.Module;
import me.client.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

import java.util.Random;

public class Velocity extends Module {
	boolean groundAct;
	boolean moveAct;
	boolean sprintAct;
	boolean targetAct;
	boolean clickAct;
	private int TickDelay;
	private int tickCounter;
	private static final Random random = new Random();
	public Velocity() {
		super("Velocity", "Reduce el knockback", Category.COMBAT);
		Dark.instance.settingsManager.rSetting(new Setting("Horizontal", this, 90, 0, 100, true));
		Dark.instance.settingsManager.rSetting(new Setting("Vertical", this, 100, 0, 100, true));
		Dark.instance.settingsManager.rSetting(new Setting("Chanse", this, 0.7, 0.1, 1.0, false));
		Dark.instance.settingsManager.rSetting(new Setting("DelayTicks",this,0,0,10,true));
		Dark.instance.settingsManager.rSetting(new Setting("OnlyGround", this,false));
		Dark.instance.settingsManager.rSetting(new Setting("OnlyAir", this,true));
		Dark.instance.settingsManager.rSetting(new Setting("OnlyMove", this,true));
		Dark.instance.settingsManager.rSetting(new Setting("OnlySprint", this,false));
		Dark.instance.settingsManager.rSetting(new Setting("OnlyTarget", this,false));
		Dark.instance.settingsManager.rSetting(new Setting("OnlyClick", this,true));
	}

	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent e) {
		if (Dark.instance.destructed) {
			return;
		}
		if (mc.thePlayer != null  && mc.theWorld != null) {


		boolean OnlygrunCOnf = Dark.instance.settingsManager.getSettingByName(this,"OnlyGround").getValBoolean();
		boolean OnlyairCOnf = Dark.instance.settingsManager.getSettingByName(this,"OnlyAir").getValBoolean();
		boolean OnlymoveCOnf = Dark.instance.settingsManager.getSettingByName(this,"OnlyMove").getValBoolean();
		boolean OnlysprintCOnf = Dark.instance.settingsManager.getSettingByName(this,"OnlySprint").getValBoolean();
		boolean OnlytargetCOnf = Dark.instance.settingsManager.getSettingByName(this,"OnlyTarget").getValBoolean();
		boolean OnlyclickCOnf = Dark.instance.settingsManager.getSettingByName(this,"OnlyClick").getValBoolean();
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
		if (OnlytargetCOnf != targetAct){
            targetAct = OnlytargetCOnf;
        }
		if (OnlyclickCOnf != clickAct){
            clickAct = OnlyclickCOnf;
        }
		boolean onGround = mc.thePlayer.onGround;
		boolean isMoving = Minecraft.getMinecraft().thePlayer.moveForward != 0 || Minecraft.getMinecraft().thePlayer.moveStrafing != 0;
		boolean isSprinting = Minecraft.getMinecraft().thePlayer.isSprinting();
		Entity target = mc.objectMouseOver.entityHit;
			if (tickCounter < TickDelay) {
				tickCounter++;
				return;
			} else {
				tickCounter = 0;
			}
		float horizontal = (float) Dark.instance.settingsManager.getSettingByName(this, "Horizontal").getValDouble();
		float vertical = (float) Dark.instance.settingsManager.getSettingByName(this, "Vertical").getValDouble();

		//TODO: se que se puede simplificar.... pero es mi CODIGO Y SON MIS REGLAS >:I (tengo alzaimer)
		if (clickAct && Mouse.isButtonDown(0) || !clickAct){
		if(targetAct && target != null || !targetAct) {
			if (sprintAct && isSprinting || !sprintAct) {
				if (moveAct && isMoving || !moveAct) {
					if (groundAct && onGround || !groundAct) {
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
	}
}
}
