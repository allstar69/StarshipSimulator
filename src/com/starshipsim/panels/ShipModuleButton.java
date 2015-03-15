package com.starshipsim.panels;

import java.awt.Canvas;

import com.starshipsim.graphics.ButtonUI;
import com.starshipsim.interfaces.Swappable;
import com.starshipsim.listeners.GameMouseListener;
import com.starshipsim.shipmodules.ShipModule;
import com.starshipsim.ui.DescriptionBoxUI;

public class ShipModuleButton extends ButtonUI implements Swappable<ShipModule> {

	private ShipModule module;
	private DescriptionBoxUI desc;
	private ModuleSwapper swapper;
	
	public ShipModule getModule() {
		return module;
	}

	public void setModule(ShipModule module) {
		this.module = module;
	}

	public ShipModuleButton(int x, int y, int width, int height, int spread, GameMouseListener mouse, ShipModule module, DescriptionBoxUI desc, ModuleSwapper swapper) {
		super(module.getName(), x, y, width, height, spread, mouse);
		this.module = module;
		this.desc = desc;
		this.swapper = swapper;
	}
	
	@Override
	public void update(Canvas canvas) {
		super.update(canvas);
		
		if(this.mouseInside(canvas)) {
			desc.getDesc().clear();
			
			desc.setVisible(true);
			
			desc.getDesc().add(module.getName());
			this.setText(this.module.getName());
			desc.getDesc().add("Quality: " + module.getQuality().toString());
		}
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
	public ShipModule getObject() {
		return this.module;
	}

	@Override
	public void setObject(ShipModule obj) {
		this.module = obj;
		
	}

}
