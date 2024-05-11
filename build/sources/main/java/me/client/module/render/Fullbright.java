 package me.client.module.render;
 import me.client.Dark;
 import me.client.module.Category;
 import me.client.module.Module;
 import me.client.module.util.utilities.StringRegistry;
 import net.minecraft.client.Minecraft;
 
public class Fullbright extends Module
{
    private Minecraft mc;
    
    public Fullbright() {
        super(StringRegistry.register("Fullbright"), "Eimina las Sombras", Category.RENDER);
        this.mc = Minecraft.getMinecraft();
    }
    
    @Override
    public void onEnable() {
        if (mc.theWorld == null || (Dark.instance.destructed))
            return;
        this.mc.gameSettings.gammaSetting = 1000.0f;
    }
    
    @Override
    public void onDisable() {
        this.mc.gameSettings.gammaSetting = 1.0f;
    }
}
