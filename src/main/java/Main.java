import me.client.Dark;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "Pelusa Dark Client", version = "b1")
public class Main {
//TODO: Jeje No es Por Molestar pero.... Recuerda Quien Creo esto jeje bueno ya sabes jeje dame los creditos si vas a usar algun modulo mio jeje
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	Dark.instance = new Dark();
    	Dark.instance.init();
    }
}
