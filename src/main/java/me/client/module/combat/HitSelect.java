package me.client.module.combat;

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

public class HitSelect extends Module {
    private boolean wasHurt = false;
    private long lastDamageTime = 0;
    private boolean canAttack = true; // Bandera para controlar si el jugador puede atacar
    private int disableTicks = 1; // Número de ticks para desactivar los ataques
    private int CLICKS;
    private int PERIODO;
    public HitSelect() {
        super("HitSelect", "Das El siguiente Hit Haciendo Que toques primero", Category.COMBAT);
        Dark.instance.settingsManager.rSetting(new Setting("Periodo",this,100,10,500,true));
        Dark.instance.settingsManager.rSetting(new Setting("CLicks",this,5,1,10,true));
        //Dark.instance.settingsManager.rSetting(new Setting("FullMode", this, false));
    }

    @SubscribeEvent
    public void onLivingHurt(TickEvent.PlayerTickEvent event) {
         PERIODO = (int) Dark.instance.settingsManager.getSettingByName(this,"Periodo").getValDouble();
         CLICKS = (int) Dark.instance.settingsManager.getSettingByName(this,"CLicks").getValDouble();
        // Verifica si el jugador del evento es el jugador local
        if(mc.thePlayer != null){
        if (event.player == Minecraft.getMinecraft().thePlayer) {
            if (event.player.hurtTime > 0 && !wasHurt) {
                wasHurt = true;
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
            } else if (event.player.hurtTime == 0) {
                wasHurt = false;
            }
            }
        }
    }
}