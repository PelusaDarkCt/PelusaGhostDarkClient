package me.client.module.combat;

import me.client.Dark;
import me.client.module.Category;
import me.client.module.Module;
import me.client.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;
import java.util.Random;

public class Reach extends Module {
    private static MovingObjectPosition moving;
    private static float theReach = 3.0F;
    private static final Random random = new Random();
    private float ReachMax;
    private float ReachMin;
    private double Chanse;
    private int TickDelay;
    private int tickCounter;
    private boolean sePuedeTra;
    private boolean sePuedeCom;
    private boolean sePuedeGho;
    private boolean sePuedeOsprint;

    public Reach() {
        super("Reach", "Un Reach Muy Op, y Pronto Lo mejorare mucho mas", Category.COMBAT);
        Dark.instance.settingsManager.rSetting(new Setting("ReachMax", this, 3.0, 3.0, 6.0, false));
        Dark.instance.settingsManager.rSetting(new Setting("ReachMin", this, 3.0, 3.0, 6.0, false));
        Dark.instance.settingsManager.rSetting(new Setting("Chanse", this, 0.2, 0.1, 1.0, false));
        Dark.instance.settingsManager.rSetting(new Setting("DelayTicks", this, 0, 0, 10, true));
        Dark.instance.settingsManager.rSetting(new Setting("Trade Mode", this, false));
        Dark.instance.settingsManager.rSetting(new Setting("Combo Mode", this, false));
        Dark.instance.settingsManager.rSetting(new Setting("GhostTap Mode", this, false));
        Dark.instance.settingsManager.rSetting(new Setting("Only Sprint", this, false));

    }

    @SubscribeEvent
    public void onMouse(MouseEvent e) {
        try {
            if (moving != null && e.button == 0 && e.buttonstate) {
                Minecraft.getMinecraft().objectMouseOver = moving;
            }
        } catch (Exception er) {
            er.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (e.phase == TickEvent.Phase.START) {
            ReachMax = (float) Dark.instance.settingsManager.getSettingByName(this, "ReachMax").getValDouble();
            ReachMin = (float) Dark.instance.settingsManager.getSettingByName(this, "ReachMin").getValDouble();
            Chanse = Dark.instance.settingsManager.getSettingByName(this, "Chanse").getValDouble();
            TickDelay = (int) Dark.instance.settingsManager.getSettingByName(this, "DelayTicks").getValDouble();

            if (Minecraft.getMinecraft().theWorld != null) {
                boolean isMoving = Minecraft.getMinecraft().thePlayer.moveForward != 0 || Minecraft.getMinecraft().thePlayer.moveStrafing != 0;
                boolean isSprinting = Minecraft.getMinecraft().thePlayer.isSprinting();
                boolean Foward = Minecraft.getMinecraft().thePlayer.moveForward != 0;
                boolean isSneaking = Minecraft.getMinecraft().thePlayer.isSneaking();
                boolean Strafing = Minecraft.getMinecraft().thePlayer.moveStrafing != 0;

                boolean isHurt = Minecraft.getMinecraft().thePlayer.hurtResistantTime > 0;
                boolean onGround = mc.thePlayer.onGround;

                boolean tradeMode = Dark.instance.settingsManager.getSettingByName(this, "Trade Mode").getValBoolean();
                boolean comboMode = Dark.instance.settingsManager.getSettingByName(this, "Combo Mode").getValBoolean();
                boolean ghostMode = Dark.instance.settingsManager.getSettingByName(this, "GhostTap Mode").getValBoolean();
                boolean OnlysprintMode = Dark.instance.settingsManager.getSettingByName(this, "Only Sprint").getValBoolean();

                if (tradeMode != sePuedeTra) {
                    sePuedeTra = tradeMode;
                }

                if (comboMode != sePuedeCom) {
                    sePuedeCom = comboMode;
                }
                if (ghostMode != sePuedeGho) {
                    sePuedeGho = ghostMode;
                }
                if (OnlysprintMode != sePuedeOsprint) {
                    sePuedeOsprint = OnlysprintMode;
                }

                if (tickCounter < TickDelay) {
                    tickCounter++;
                    return;
                } else {
                    tickCounter = 0;
                }

                if (sePuedeTra) {
                    theReach = isHurt || !onGround ? (random.nextFloat() < Chanse ? ReachMax : ReachMin) : ReachMin;
                }

                if (sePuedeCom) {
                    theReach = !isHurt && isMoving ? (random.nextFloat() < Chanse ? ReachMax : ReachMin) : ReachMin;
                }

                if (sePuedeGho) {
                    theReach = !Foward ? (random.nextFloat() < Chanse ? ReachMax : ReachMin) : ReachMin;
                }
                if (sePuedeOsprint) {
                    theReach = isSprinting ? (random.nextFloat() < Chanse ? ReachMax : ReachMin) : ReachMin;
                }

                // Modo normal
                else {
                    theReach = isMoving ? (random.nextFloat() < Chanse ? ReachMax : ReachMin) : ReachMin;
                }

                getMouseOver(1.0F);
            }
        }
    }

    public void getMouseOver(float partialTicks) {
        if (Minecraft.getMinecraft().getRenderViewEntity() != null && Minecraft.getMinecraft().theWorld != null) {
            Minecraft.getMinecraft().pointedEntity = null;
            double d0 = (double) theReach;
            moving = Minecraft.getMinecraft().getRenderViewEntity().rayTrace(d0, partialTicks);
            double d1 = d0;
            Vec3 vec3 = Minecraft.getMinecraft().getRenderViewEntity().getPositionEyes(partialTicks);
            if (moving != null) {
                d1 = moving.hitVec.distanceTo(vec3);
            }

            Vec3 vec31 = Minecraft.getMinecraft().getRenderViewEntity().getLook(partialTicks);
            Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
            Entity pointedEntity = null;
            Vec3 vec33 = null;
            float f1 = 1.0F;
            List<Entity> list = Minecraft.getMinecraft().theWorld.getEntitiesWithinAABBExcludingEntity(Minecraft.getMinecraft().getRenderViewEntity(), Minecraft.getMinecraft().getRenderViewEntity().getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double) f1, (double) f1, (double) f1));
            double d2 = d1;

            for (int i = 0; i < list.size(); ++i) {
                Entity entity = list.get(i);
                if (entity.canBeCollidedWith()) {
                    float f2 = 0.13F;
                    AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand((double) f2, (double) f2, (double) f2);
                    MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
                    if (axisalignedbb.isVecInside(vec3)) {
                        if (0.0D < d2 || d2 == 0.0D) {
                            pointedEntity = entity;
                            vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                            d2 = 0.0D;
                        }
                    } else if (movingobjectposition != null) {
                        double d3 = vec3.distanceTo(movingobjectposition.hitVec);
                        if (d3 < d2 || d2 == 0.0D) {
                            if (entity == Minecraft.getMinecraft().getRenderViewEntity().ridingEntity && !entity.canRiderInteract()) {
                                if (d2 == 0.0D) {
                                    pointedEntity = entity;
                                    vec33 = movingobjectposition.hitVec;
                                }
                            } else {
                                pointedEntity = entity;
                                vec33 = movingobjectposition.hitVec;
                                d2 = d3;
                            }
                        }
                    }
                }
            }

            if (pointedEntity != null && (d2 < d1 || moving == null)) {
                moving = new MovingObjectPosition(pointedEntity, vec33);
                if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame) {
                    Minecraft.getMinecraft().pointedEntity = pointedEntity;
                }
            }
        }
    }
}
