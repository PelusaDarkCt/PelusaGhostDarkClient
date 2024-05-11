package me.client.module.render;

import me.client.module.Category;
import me.client.module.Module;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NameTags extends Module {
    public NameTags() {
        super("Nametags", "Muestra el NameTag de todos los jugadores", Category.RENDER);
    }

    @SubscribeEvent
    public void onRenderLiving(RenderLivingEvent.Specials.Pre event) {
        if (event.entity instanceof EntityPlayer && event.entity != mc.thePlayer && event.entity.deathTime == 0) {
            EntityPlayer player = (EntityPlayer) event.entity;
            String name = player.getDisplayName().getFormattedText();
            // Eliminar el nametag existente
            event.setCanceled(true);
            // Renderizar el nametag personalizado
            GlStateManager.pushMatrix();
            GlStateManager.translate((float) event.x + 0.0F, (float) event.y + player.height + 0.5F, (float) event.z);
            GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
            float scale = 0.02666667F;
            GlStateManager.scale(-scale, -scale, scale);
            if (player.isSneaking()) {
                GlStateManager.translate(0.0F, 9.374999F, 0.0F);
            }
            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            // Agregar sombra al texto
            mc.fontRendererObj.drawStringWithShadow(name, -mc.fontRendererObj.getStringWidth(name) / 2, 0, -1);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }
}
