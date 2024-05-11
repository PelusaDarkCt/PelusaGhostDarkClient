package me.tutorial.module.render;

import org.lwjgl.input.Keyboard;

import me.tutorial.Tutorial;
import me.tutorial.module.Category;
import me.tutorial.module.Module;

public class ClickGUI extends Module {

	public ClickGUI() {
		super("ClickGUI", "Allows you to enable and disable modules", Category.RENDER);
		this.setKey(Keyboard.KEY_RSHIFT);
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		mc.func_147108_a(Tutorial.instance.clickGui);
		this.setToggled(false);
	}
}
