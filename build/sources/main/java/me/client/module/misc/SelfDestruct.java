package me.client.module.misc;

import me.client.Dark;
import me.client.module.Category;
import me.client.module.Module;

public class SelfDestruct extends Module {

	public SelfDestruct() {
		super("SelfDestruct", "Destructs", Category.MISC);
	}

	@Override
	public void onEnable() {
		Dark.instance.onDestruct();
	}
}
