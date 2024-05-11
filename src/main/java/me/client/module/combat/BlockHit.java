package me.client.module.combat;

import me.client.Dark;
import me.client.module.Category;
import me.client.module.Module;
import me.client.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemSword;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;


public class BlockHit extends Module {
    private long lastHitTime = 0;
    private long lastActivationTime = 0;
    private boolean isBlockActivated = false;
    boolean ACTATTACK;
    private static final Minecraft mc = Minecraft.getMinecraft();
   private boolean BotonPres;
    private boolean BotonPresANT = false;
    public BlockHit() {
        super("BlockHit(Beta)", "Auto Block Hit", Category.COMBAT);
        Dark.instance.settingsManager.rSetting(new Setting("Duracion", this, 150, 10, 500, true));
        Dark.instance.settingsManager.rSetting(new Setting("Cooldown", this, 250, 10, 500, true));
        Dark.instance.settingsManager.rSetting(new Setting("MinDist", this, 1.3, 0, 10, false));
        Dark.instance.settingsManager.rSetting(new Setting("MaxDist", this, 3.5, 0, 10, false));
        Dark.instance.settingsManager.rSetting(new Setting("AttkOnly", this, true));
    }
//TODO: Tengo que mejorar esto ....
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event == null || event.player == null || event.player.worldObj == null) return;

        float duracion = (float) Dark.instance.settingsManager.getSettingByName(this, "Duracion").getValDouble();
        float cooldown = (float) Dark.instance.settingsManager.getSettingByName(this, "Cooldown").getValDouble();
        float minDist = (float) Dark.instance.settingsManager.getSettingByName(this, "MinDist").getValDouble();
        float maxDist = (float) Dark.instance.settingsManager.getSettingByName(this, "MaxDist").getValDouble();
        boolean ATACKONLY = Dark.instance.settingsManager.getSettingByName(this,"AttkOnly").getValBoolean();

        if(ATACKONLY != ACTATTACK){
            ACTATTACK = ATACKONLY;
        }
        if (mc.thePlayer.worldObj.isRemote) {
            Entity target = mc.objectMouseOver != null ? mc.objectMouseOver.entityHit : null;
            if (isSwordInHand()) {
            if (target != null && mc.gameSettings.keyBindAttack.isKeyDown()) {
                double distance = event.player.getDistanceToEntity(target);

                    if (distance >= minDist && distance <= maxDist && System.currentTimeMillis() - lastActivationTime >= cooldown) {
                            lastHitTime = System.currentTimeMillis();
                            isBlockActivated = true;
                    }
            }
            if (isBlockActivated && System.currentTimeMillis() - lastHitTime >= duracion) {
                isBlockActivated = false;
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
            }
                if (isBlockActivated && System.currentTimeMillis() - lastHitTime <= duracion) {
                    if (System.currentTimeMillis() - lastHitTime <= duracion) {
                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                    }
                    else {
                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                        isBlockActivated = false;
                         }
                }else {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                    isBlockActivated = false;
                }
            }
        }

    }

    private boolean isSwordInHand() {
        return this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
    }
}
