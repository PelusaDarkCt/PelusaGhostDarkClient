package me.client.module.combat;

import me.client.Dark;
import me.client.module.Category;
import me.client.module.Module;
import me.client.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import me.client.module.combat.AntiBot;

/*Pendejo el que lea esto y pro el que entienda el codigo musaraña
 * */
public class AimAssist extends Module {

    private NumberValue speedValue;
    private NumberValue fovValue;
    private NumberValue distanceValue;

    private Minecraft mc;
    private int tru;
    private boolean tre = true;
    private boolean activadoTT;
    private boolean swordActivado;
    private boolean clickactivado;
  //  boolean targetAct;
    public AimAssist() {
        super("AimAssist", "Ayuda a Apuntar Mejor", Category.COMBAT);
        this.mc = Minecraft.getMinecraft();

        this.speedValue = new NumberValue("Speed", 45.0, 10.0, 50.0);
        this.fovValue = new NumberValue("FOV", 55.0, 0.0, 180.0);
        this.distanceValue = new NumberValue("Distance", 4.3, 1.0, 10.0);

        Dark.instance.settingsManager.rSetting(new Setting("Speed", this, 10.0, 10.0, 50.0, false));
        Dark.instance.settingsManager.rSetting(new Setting("FOV", this, 90, 10.0, 360.0, false));
        Dark.instance.settingsManager.rSetting(new Setting("Distance", this, 4.3, 1.0, 10.0, false));
        Dark.instance.settingsManager.rSetting(new Setting("Teamscheck", this ,false));
        Dark.instance.settingsManager.rSetting(new Setting("OnlySworld", this ,false));
      //  Dark.instance.settingsManager.rSetting(new Setting("OnlyTarget", this ,false));

         Dark.instance.settingsManager.rSetting(new Setting("OnlyClick", this ,false));
    }
    //TODO: Vaya, recuerda si vas a usar cualquier recurso de este src, darme los creditos jeje ya le sabes supongo :)
    public double entityPosCompare(final Entity ent) {
        return ((this.mc.thePlayer.rotationYaw - rotationUntilTarget(ent)) % 360.0 + 540.0) % 360.0 - 180.0;
    }

    private float rotationUntilTarget(Entity ent) {
        double diffX = ent.posX - this.mc.thePlayer.posX;
        double diffZ = ent.posZ - this.mc.thePlayer.posZ;
        return (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
    }

    public boolean isFovLargeEnough(final Entity en, float a) {
        a *= 0.5;
        final double v = ((this.mc.thePlayer.rotationYaw - rotationUntilTarget(en)) % 360.0 + 540.0) % 360.0 - 180.0;
        return (v > 0.0 && v < a) || (-a < v && v < 0.0);
    }

    @SubscribeEvent
    public void tickEvent(final TickEvent.RenderTickEvent event) {
        float speed = (float) Dark.instance.settingsManager.getSettingByName(this, "Speed").getValDouble();
        float fov = (float) Dark.instance.settingsManager.getSettingByName(this, "FOV").getValDouble();
        float distance = (float) Dark.instance.settingsManager.getSettingByName(this, "Distance").getValDouble();
        boolean onlySworldConfig = Dark.instance.settingsManager.getSettingByName(this, "OnlySworld").getValBoolean();
       // boolean OnlytargetCOnf = Dark.instance.settingsManager.getSettingByName(this,"OnlyTarget").getValBoolean();
        boolean teamsActivated = Dark.instance.settingsManager.getSettingByName(this, "Teamscheck").getValBoolean();
        boolean clickonlyoption = Dark.instance.settingsManager.getSettingByName(this,"OnlyClick").getValBoolean();
        //Nose tu pero a mi me marea ver el codigo desordenado . tengo flojera por ahora jeje
        if (teamsActivated != activadoTT){
            activadoTT = teamsActivated;
        }
        if (onlySworldConfig != swordActivado) {
            swordActivado = onlySworldConfig;
        }
        if (clickonlyoption != clickactivado){
            clickactivado = clickonlyoption;
        }
     //   if (OnlytargetCOnf != targetAct){
         //   targetAct = OnlytargetCOnf;
       // }

       // Entity targetV = mc.objectMouseOver.entityHit;

        if ((!swordActivado || isSwordInHand()) && (!clickactivado || Mouse.isButtonDown(0))) {
            if (this.mc.thePlayer != null && this.tre) {
                if (this.mc.gameSettings.keyBindAttack.isKeyDown()) {
                    this.tre = true;
                }
                if (this.tre) {
                    ++this.tru;
                }

                if (this.tru >= 200) {
                    this.tru = 0;
                    return;
                }
                final Entity h = this.ent();
                if (h != null) {
                    final float distanceTo = h.getDistanceToEntity((Entity) this.mc.thePlayer);
                    if (h != null && (entityPosCompare(h) > 1.0 || entityPosCompare(h) < -1.0)) {
                        final boolean i = entityPosCompare(h) > 0.0;
                        final EntityPlayerSP thePlayer = this.mc.thePlayer;
                        thePlayer.setAngles((float) (i ? (-(Math.abs(entityPosCompare(h)) * (speed / 50.0))) : (Math.abs(entityPosCompare(h)) * speed / 50.0)), 0.0f);
                    }
                }
            }
        }
    }
    private Entity ent() {
        Entity closestEntity = null;
        double closestDistance = Double.MAX_VALUE;
        float fov = (float) Dark.instance.settingsManager.getSettingByName(this, "FOV").getValDouble();

        for (Object obj : this.mc.theWorld.loadedEntityList) {
            if (obj instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) obj;

                if (entity.equals(this.mc.thePlayer)) {
                    continue;
                }

                // Verifica si el módulo AntiBot está activado
                if (Dark.instance.moduleManager.getModule("AntiBot").isToggled()) {
                    // Si AntiBot está activado, verifica si la entidad es un bot
                    if (AntiBot.bots.contains(entity)) {
                        // Si la entidad es un bot, ignorarla
                        continue;
                    }
                }

                double distance = this.mc.thePlayer.getDistanceToEntity(entity);

                if (distance < this.distanceValue.getValue() && isFovLargeEnough(entity, fov)) {
                    if (isSameTeam(entity) && activadoTT) {
                        continue;
                    }

                    if (distance < closestDistance) {
                        closestEntity = entity;
                        closestDistance = distance;
                    }
                }
            }
        }

        return closestEntity;
    }
    private boolean shouldTargetEntity(EntityLivingBase entity) {
        if (!activadoTT) {
            return true;
        } else {
            return !isSameTeam(entity) && !entity.equals(this.mc.thePlayer);
        }
    }

    private boolean isSwordInHand() {
        if (mc.thePlayer == null){
            return false;
        }
        if (this.mc.thePlayer.getHeldItem() == null) {
            return false;
        }
        return this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
    }

    private boolean isSameTeam(Entity entity) {
        if (!(entity instanceof EntityPlayer)) {
            return false;
        }
        EntityPlayer otherPlayer = (EntityPlayer) entity;
        ScorePlayerTeam otherTeam = (ScorePlayerTeam) otherPlayer.getTeam();
        ScorePlayerTeam myTeam = (ScorePlayerTeam) this.mc.thePlayer.getTeam();

        if (myTeam != null && otherTeam != null) {
            return myTeam.equals(otherTeam);
        }
        return false;
    }

    public static class NumberValue {
        private String name;
        private double value;
        private double min;
        private double max;

        public NumberValue(String name, double value, double min, double max) {
            this.name = name;
            this.value = value;
            this.min = min;
            this.max = max;
        }

        public double getValue() {
            return this.value;
        }

        public void setValue(double value) {
            if (value < this.min) {
                this.value = this.min;
            } else if (value > this.max) {
                this.value = this.max;
            } else {
                this.value = value;
            }
        }

        public boolean getState() {
            return this.value >= this.min && this.value <= this.max;
        }
    }
}
