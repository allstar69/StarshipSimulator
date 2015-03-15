package com.starshipsim.ui;

import java.awt.Canvas;
import java.awt.Graphics;

import com.starshipsim.interfaces.Swappable;
import com.starshipsim.listeners.GameMouseListener;
import com.starshipsim.panels.ModuleSwapper;
import com.starshipsim.shipmodules.ShipModule;

public class ShipSelector extends SelectionBoxUI implements Swappable<ShipModule>{

	private ShipModule module;
	private DescriptionBoxUI desc;
	private ModuleSwapper swapper;
	
	public ShipModule getModule() {
		return module;
	}

	public void setModule(ShipModule module) {
		this.module = module;
	}

	public ShipSelector(ShipModule module, DescriptionBoxUI desc, int x, int y, int width, int height, ModuleSwapper swapper, GameMouseListener mouse) {
		super(x, y, width, height, mouse);
		this.module = module;
		this.desc = desc;
		this.swapper = swapper;
	}

	@Override
	public void clicked() {
		if(swapper.getSource() == null) {
			swapper.setSource(this);
		} else {
			if(swapper.getDest() == null) {
				swapper.setDest(this);
			}
		}
		
		swapper.activate();
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
	public void draw(Graphics g) {
		super.draw(g);
	}

	@Override
	public ShipModule getObject() {
		return this.module;
	}

	@Override
	public void setObject(ShipModule obj) {
		module = obj;
	}
	
}
