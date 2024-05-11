package me.client.module;

import java.util.ArrayList;


import me.client.module.combat.*;
import me.client.module.combat.backtrackv2.PacketDelayModule;
import me.client.module.misc.AntiAfk;
import me.client.module.misc.Delay17;
import me.client.module.misc.FastDisconnect;
import me.client.module.misc.SelfDestruct;
//import me.client.module.movement.BlinkMod;
import me.client.module.movement.Parkour;
import me.client.module.movement.Sprint;
import me.client.module.player.AutoTool;
import me.client.module.player.FastBridge;
import me.client.module.player.FastPlace;
import me.client.module.render.ClickGUI;
import me.client.module.render.Fullbright;
import me.client.module.render.HUD;
import me.client.module.render.NameTags;

public class ModuleManager {

	public ArrayList<Module> modules;
	
	public ModuleManager() {
		(modules = new ArrayList<Module>()).clear();
		this.modules.add(new ClickGUI());
		this.modules.add(new HUD());
		this.modules.add(new Sprint());
		//this.modules.add(new AutoClicker());
		this.modules.add(new Velocity());
		this.modules.add(new AntiBot());
		this.modules.add(new SelfDestruct());
		this.modules.add(new AimAssist());
		this.modules.add(new FastPlace());
		this.modules.add(new Wtap());
		this.modules.add(new STap());
		this.modules.add(new HitSelect());
		//this.modules.add(new FullModeExp());
		this.modules.add(new Reach());
		this.modules.add(new JumpReset());
		this.modules.add(new BlockHit());
		this.modules.add(new NameTags());
		this.modules.add(new Parkour());
		this.modules.add(new FastBridge());
		this.modules.add(new FastDisconnect());
		this.modules.add(new AntiAfk());
		this.modules.add(new Delay17());
		this.modules.add(new Fullbright());
		this.modules.add(new AutoTool());
		//this.modules.add(new PacketDelayModule());
		//this.modules.add(new JumpResetv2());
		//this.modules.add(new Blink());
		//this.modules.add(new BlinkMod());
		//this.modules.add(new HitBoxes());
		//this.modules.add(new AutoWeapon());
		//this.modules.add(new KBv2());
		//this.modules.add(new AutoPot());
		//this.modules.add(new FreeCam());
		//this.modules.add(new KeepSprint());
		//this.modules.add(new ButtonExperimento());
		//this.modules.add(new DelayRemover());
		//this.modules.add(new MouseDelayFix());
	}
	
	public Module getModule(String name) {
		for (Module m : this.modules) {
			if (m.getName().equalsIgnoreCase(name)) {
				return m;
			}
		}
		return null;
	}
	
	public ArrayList<Module> getModuleList() {
		return this.modules;
	}
	
	public ArrayList<Module> getModulesInCategory(Category c) {
		ArrayList<Module> mods = new ArrayList<Module>();
		for (Module m : this.modules) {
			if (m.getCategory() == c) {
				mods.add(m);
			}
		}
		return mods;
	}
}
