package com.starshipsim.panels;

import com.starshipsim.graphics.ButtonUI;
import com.starshipsim.listeners.GameMouseListener;
import com.starshipsim.shipmodules.ShipModule;

public abstract class ShipModuleButton extends ButtonUI {

	private ShipModule module;
	
	public ShipModule getModule() {
		return module;
	}

	public void setModule(ShipModule module) {
		this.module = module;
	}

	public ShipModuleButton(int x, int y, int width, int height, GameMouseListener mouse, ShipModule module) {
		super(module.getName(), x, y, width, height, mouse);
		this.module = module;
	}

}
