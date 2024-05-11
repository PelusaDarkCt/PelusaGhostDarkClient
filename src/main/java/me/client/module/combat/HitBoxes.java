 package me.client.module.combat;
 
 import me.client.Dark;
 import me.client.module.Category;
 import me.client.module.Module;
 import me.client.module.util.utilities.*;
 import me.client.module.values.*;
 import me.client.settings.Setting;
 import me.client.module.util.ClientUtil;
 import net.minecraft.client.Minecraft;
 import net.minecraft.util.MovingObjectPosition;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

 public class HitBoxes extends Module
{
    private AimAssist.NumberValue ExpansV;
    private Minecraft mc;
    private NumberValue expandVal;
    
    public HitBoxes() {
        super("HitBoxes","Expande Las Hitbox",Category.COMBAT);
        this.expandVal = new NumberValue("Expand", 1.0, 0.0, 1.0);
        Dark.instance.settingsManager.rSetting(new Setting("Expand",this,1.0,0.0,1.0,false));

    }
    //TODO:Proximamente
    @SubscribeEvent
    public void onMouseOver(final float memes) {
        if (this.mc.thePlayer == null || this.mc.theWorld == null || this.mc.currentScreen != null) {
            return;
        }
        if (this.mc.thePlayer.isRiding()) {
            return;
        }
        if (this.mc.thePlayer.isDead) {
            return;
        }
        final double expand = this.expandVal.getValue();
        final MovingObjectPosition object = ClientUtil.getMouseOver(3.0, expand,memes);
        if (object != null) {
            this.mc.objectMouseOver = object;
        }
    }
}
