package me.client.module.combat.backtrackv2.asm;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
public class FMLLoadingPlugin implements IFMLLoadingPlugin {
    public String[] getASMTransformerClass() {
        return new String[]{ASMTransformerClass.class.getName()};
    }
    public String getModContainerClass() {
        return null;
    }
    public String getSetupClass() {
        return null;
    }
    public void injectData(Map<String, Object> data) {}
    public String getAccessTransformerClass() {
        return null;
    }

}
