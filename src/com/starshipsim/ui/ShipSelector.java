package com.starshipsim.ui;

import java.awt.Canvas;
import java.awt.Graphics;

import com.starshipsim.shipmodules.ShipModule;

public abstract class ShipSelector extends SelectionBoxUI {

	private ShipModule module;
	private DescriptionBoxUI desc;
	
	public ShipModule getModule() {
		return module;
	}

	public void setModule(ShipModule module) {
		this.module = module;
	}

	public ShipSelector(ShipModule module, DescriptionBoxUI desc, int x, int y, int width, int height) {
		super(x, y, width, height);
		this.module = module;
		this.desc = desc;
	}

	@Override
	public abstract void clicked();
	
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
	public void draw(Graphics g) {
		super.draw(g);
	}
	
}
