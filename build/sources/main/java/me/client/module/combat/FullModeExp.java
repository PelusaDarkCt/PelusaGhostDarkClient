package me.client.module.combat;

import me.client.Dark;
import me.client.module.Category;
import me.client.module.Module;
import me.client.settings.Setting;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class FullModeExp extends Module {

    private boolean playerHurtRecently;
    private boolean canAttack; // Nuevo flag para controlar si se puede atacar
  //  private final int DISABLE_ATTACK_TIME = 50; // Duración en ticks para deshabilitar los ataques después de recibir daño
  //  private final int COOLDOWN_TIME = 100; // Duración en ticks para el cooldown después de que se haya desactivado la acción de ataque
    private int disableAttackTimer = 0;
    private int cooldownTimer = 0;

    public FullModeExp() {
        super("FullModeExp", "hitselectv2", Category.COMBAT);
        Dark.instance.settingsManager.rSetting(new Setting("Duracion",this,100,10,500,true));
        Dark.instance.settingsManager.rSetting(new Setting("Cooldown",this,100,10,500,true));
    }
    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (event.entityLiving != null && event.entityLiving.equals(mc.thePlayer))
        { int Duracion = (int)Dark.instance.settingsManager.getSettingByName(this,"Duracion").getValDouble();
            int Cooldown = (int)Dark.instance.settingsManager.getSettingByName(this,"Cooldown").getValDouble();
            playerHurtRecently = true;
            disableAttackTimer = Duracion;
            canAttack = false; // Deshabilita la capacidad de atacar después de recibir daño
        }
    }
    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {
        if (playerHurtRecently && canAttack) {
            int Cooldown = (int)Dark.instance.settingsManager.getSettingByName(this,"Cooldown").getValDouble();
            event.setCanceled(true);
            cooldownTimer = Cooldown;

        }
    }
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        int Duracion = (int)Dark.instance.settingsManager.getSettingByName(this,"Duracion").getValDouble();
        int Cooldown = (int)Dark.instance.settingsManager.getSettingByName(this,"Cooldown").getValDouble();
        if (event.phase == TickEvent.Phase.START) {
            if (disableAttackTimer > 0) {
                disableAttackTimer--;

                if (disableAttackTimer == 0) {
                    playerHurtRecently = false;
                    canAttack = true; // Permite atacar después de que el tiempo de desactivación haya terminado
                }
            }
            if (cooldownTimer > 0) {
                cooldownTimer--;

                if (cooldownTimer == 0) {
                    canAttack = true; // Permite atacar después de que el tiempo de cooldown haya terminado
                }
            }
        }
    }
}


/*package me.client.module.combat;

import me.client.Dark;
import me.client.module.Category;
import me.client.module.Module;
import me.client.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Timer;
import java.util.TimerTask;

public class FullModeExp extends Module {

    private boolean wasHurt = false;
    private long lastDamageTime = 0;
    private boolean canAttack = true; // Bandera para controlar si el jugador puede atacar
    private int disableTicks = 1; // Número de ticks para desactivar los ataques
    private int CLICKS;
    private int PERIODO;
    private int cooldown;

    public FullModeExp() {
        super("HitSelectFULL", "Das El siguiente Hit Haciendo Que toques primero", Category.COMBAT);
        Dark.instance.settingsManager.rSetting(new Setting("Periodo", this, 100, 10, 500, true));
        Dark.instance.settingsManager.rSetting(new Setting("CLicks", this, 5, 1, 10, true));
        Dark.instance.settingsManager.rSetting(new Setting("Cooldown", this, 100, 0, 500, true));
    }

    @SubscribeEvent
    public void onLivingHurt(TickEvent.PlayerTickEvent event) {
        PERIODO = (int) Dark.instance.settingsManager.getSettingByName(this, "Periodo").getValDouble();
        CLICKS = (int) Dark.instance.settingsManager.getSettingByName(this, "CLicks").getValDouble();
        // Verifica si el jugador del evento es el jugador local
        if (mc.thePlayer != null) {
            cooldown = (int) Dark.instance.settingsManager.getSettingByName(this, "Cooldown").getValDouble();
            if (event.player == Minecraft.getMinecraft().thePlayer) {

                if (event.player.hurtTime > 0 && !wasHurt && canAttack) {
                    wasHurt = true;
                    canAttack = false;

                    if (event.player.worldObj.isRemote) { // Asegúrate de que este código se ejecute solo en el lado del cliente
                        KeyBinding keyBindAttack = Minecraft.getMinecraft().gameSettings.keyBindAttack;
                        final int keyCode = keyBindAttack.getKeyCode(); // Declara keyCode como final
                        final Timer[] timer = new Timer[1]; // Usa un arreglo de un solo elemento para almacenar el temporizador
                        timer[0] = new Timer();
                        timer[0].schedule(new TimerTask() {
                            int count = 0;

                            @Override
                            public void run() {
                                if (count < CLICKS) { // X clicks en menos de 1 segundo
                                    if (Minecraft.getMinecraft().currentScreen == null) { // Verifica si no hay ninguna pantalla abierta
                                        KeyBinding.setKeyBindState(keyCode, true);
                                        KeyBinding.onTick(keyCode);
                                        KeyBinding.setKeyBindState(keyCode, false);
                                    }
                                    count++;
                                } else {
                                    timer[0].cancel(); // Cancela el temporizador
                                }
                            }
                        }, 0, PERIODO); // X ms es el tiempo entre cada click X clicks por segundo
                    }
                }else if (event.player.hurtTime == 0) {
                    wasHurt = false;
                    if (!canAttack) {
                        disableTicks++;
                        if (disableTicks >= cooldown) {
                            canAttack = true;
                            disableTicks = 0;
                        }
                    }
                }else if (event.player.hurtTime > 0 && !canAttack) {

                    wasHurt = true;
                }
            }
        }
    }
}
*/
