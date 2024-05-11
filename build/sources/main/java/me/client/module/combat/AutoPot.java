package me.client.module.combat;
/*Proximamente*/
import me.client.module.Category;
import me.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoPot extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();
    private boolean isUsingPotion = false;

    public AutoPot() {
        super("AutoPot", "Automatisa La Accion de Curarte", Category.COMBAT);
    }
/*Proximamente*/
    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START || mc.thePlayer == null) {
            return;
        }

        if (shouldUsePotion()) {
            usePotion();
        }
    }

    private boolean shouldUsePotion() {
        EntityPlayerSP player = mc.thePlayer;
        if (player.getHealth() <= 6.0f) { // Si el jugador tiene 3 corazones o menos
            for (int slot = 0; slot < player.inventory.getSizeInventory(); slot++) {
                ItemStack stack = player.inventory.getStackInSlot(slot);
                if (stack != null && stack.getItem() == Items.potionitem) {
                    // Verificar si la poción tiene efectos de curación instantánea
                    for (PotionEffect effect : ((ItemPotion) stack.getItem()).getEffects(stack)) {
                        if (effect.getPotionID() == Potion.heal.getId() && effect.getAmplifier() >= 1) {
                            return true; // Devuelve true si se encontró una poción adecuada
                        }
                    }
                }
            }
        }
        return false; // Devuelve false si no se encontró ninguna poción adecuada
    }

    private void usePotion() {
        if (!isUsingPotion) {
            isUsingPotion = true;

            // Cambiar al slot de la poción en el inventario (si no está ya seleccionado)
            int potionSlot = getPotionSlot();
            if (potionSlot != -1) {
                mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(potionSlot));
            }

            // Usar la poción (simular clic derecho)
            mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));

            // Esperar un poco antes de permitir usar otra poción
            new Thread(() -> {
                try {
                    Thread.sleep(1000); // Espera 1 segundo (ajusta según sea necesario)
                    isUsingPotion = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private int getPotionSlot() {
        EntityPlayerSP player = mc.thePlayer;
        for (int slot = 0; slot < player.inventory.getSizeInventory(); slot++) {
            ItemStack stack = player.inventory.getStackInSlot(slot);
            if (stack != null && stack.getItem() == Items.potionitem) {
                for (PotionEffect effect : ((ItemPotion) stack.getItem()).getEffects(stack)) {
                    if (effect.getPotionID() == Potion.heal.getId() && effect.getAmplifier() >= 1) {
                        return slot; // Devuelve el slot de la primera poción adecuada encontrada
                    }
                }
            }
        }
        return -1; // Si no se encuentra ninguna poción adecuada, devuelve -1
    }
}
/**/