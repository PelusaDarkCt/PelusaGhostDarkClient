package me.client.module.render;

import org.lwjgl.input.Keyboard;

import me.client.Dark;
import me.client.module.Category;
import me.client.module.Module;

public class ClickGUI extends Module {

	public ClickGUI() {
		super("ClickGUI", "Allows you to enable and disable modules", Category.RENDER);
		this.setKey(Keyboard.KEY_RSHIFT);
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		mc.displayGuiScreen(Dark.instance.clickGui);
		this.setToggled(false);
	}
}
