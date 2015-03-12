package com.starshipsim.panels;

import java.awt.Canvas;

import com.starshipsim.graphics.ButtonUI;
import com.starshipsim.listeners.GameMouseListener;
import com.starshipsim.shipmodules.ShipModule;
import com.starshipsim.ui.DescriptionBoxUI;

public class ShipModuleButton extends ButtonUI {

	private ShipModule module;
	private DescriptionBoxUI desc;
	
	public ShipModule getModule() {
		return module;
	}

	public void setModule(ShipModule module) {
		this.module = module;
	}

	public ShipModuleButton(int x, int y, int width, int height, int spread, GameMouseListener mouse, ShipModule module, DescriptionBoxUI desc) {
		super(module.getName(), x, y, width, height, spread, mouse);
		this.module = module;
		this.desc = desc;
	}
	
	@Override
	public void update(Canvas canvas) {
		super.update(canvas);
		
		if(this.mouseInside(canvas)) {
			desc.getDesc().clear();
			
			desc.setVisible(true);
			
			desc.getDesc().add(module.getName());
			desc.getDesc().add("Quality: " + module.getQuality().toString());
		}
	}

	@Override
	public void clicked() {
		// TODO Auto-generated method stub
		
	}

}
