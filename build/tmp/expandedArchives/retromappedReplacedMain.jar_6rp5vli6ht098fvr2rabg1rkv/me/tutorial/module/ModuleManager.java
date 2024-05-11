package me.tutorial.module;

import java.util.ArrayList;

import me.tutorial.module.combat.AntiBot;
import me.tutorial.module.combat.AutoClicker;
import me.tutorial.module.combat.Velocity;
import me.tutorial.module.movement.Sprint;
import me.tutorial.module.render.ClickGUI;
import me.tutorial.module.render.HUD;

public class ModuleManager {

	public ArrayList<Module> modules;
	
	public ModuleManager() {
		(modules = new ArrayList<Module>()).clear();
		this.modules.add(new ClickGUI());
		this.modules.add(new HUD());
		this.modules.add(new Sprint());
		this.modules.add(new AutoClicker());
		this.modules.add(new Velocity());
		this.modules.add(new AntiBot());
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
