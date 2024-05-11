package me.client.module.movement;

import me.client.module.Category;
import me.client.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.MouseEvent;

import java.util.Random;

public class KeepSprint extends Module {
   private static final Random RANDOM = new Random();
   private static final double CHANCE_TO_KEEP_SPRINT = 1.0;

   public KeepSprint() {
      super("KeepSprint", "Incrementa Tu Movimiento", Category.MOVEMENT);
   }

   @SubscribeEvent
   public void onMouse(MouseEvent event) {
      if (event.button == 0 && event.buttonstate) {
         Entity entityHit = mc.objectMouseOver != null ? mc.objectMouseOver.entityHit : null;
         if (entityHit instanceof EntityLivingBase) {
            if (RANDOM.nextDouble() < CHANCE_TO_KEEP_SPRINT) {
               mc.thePlayer.setSprinting(true);
            }
         }
      }
   }
/*proximamente*/
}