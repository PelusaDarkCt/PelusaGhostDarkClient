import me.tutorial.Tutorial;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "tutorialclient", version = "b1")
public class Main {

    @EventHandler
    public void init(FMLInitializationEvent event) {
    	Tutorial.instance = new Tutorial();
    	Tutorial.instance.init();
    }
}
