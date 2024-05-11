 package me.client.module.misc;
 
 import java.lang.reflect.Field;

 import me.client.module.Category;
 import me.client.module.Module;
 import me.client.module.util.utilities.StringRegistry;
 import net.minecraft.client.Minecraft;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 import net.minecraftforge.fml.common.gameevent.TickEvent;
 
public class Delay17 extends Module
{
    private Field leftClickCounter;
    private Minecraft mc;
    
    public Delay17() {
        super(StringRegistry.register(new String(new char[] { 'D', 'e', 'l', 'a', 'y', ' ', 'F', 'i', 'x'})),"quita el delay de la 1.8", Category.MISC);
        this.mc = Minecraft.getMinecraft();
        try {
            this.leftClickCounter = Minecraft.class.getDeclaredField("field_71429_W");
        }
        catch (Exception var4) {
            try {
                this.leftClickCounter = Minecraft.class.getDeclaredField("leftClickCounter");
            }
            catch (Exception ex) {}
        }
        if (this.leftClickCounter != null) {
            this.leftClickCounter.setAccessible(true);
        }
    }
    
    @SubscribeEvent
    public void onPlayerTick(final TickEvent.PlayerTickEvent e) {
        if (this.mc.thePlayer != null && this.mc.theWorld != null) {
            try {
                this.leftClickCounter.set(this.mc, 0);
            }
            catch (IllegalAccessException ex) {}
        }
    }
}
