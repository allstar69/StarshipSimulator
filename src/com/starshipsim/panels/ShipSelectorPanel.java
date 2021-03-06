package com.starshipsim.panels;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import com.starshipsim.data.ShipData;
import com.starshipsim.entities.Ship;
import com.starshipsim.interfaces.Drawable;
import com.starshipsim.listeners.GameMouseListener;
import com.starshipsim.shipmodules.WarpCoreModule;
import com.starshipsim.shipmodules.WeaponModule;
import com.starshipsim.ui.DescriptionBoxUI;
import com.starshipsim.ui.ShipSelector;
import com.starshipsim.weapons.Weapon;

public class ShipSelectorPanel implements Drawable {

	private Ship ship;
	private ArrayList<ShipSelector> selectors;
	private DescriptionBoxUI desc;
	
	public Ship getShip() {
		ArrayList<Weapon> weapons = new ArrayList<Weapon>();
		for (ShipSelector shipSelector : selectors) {
			if(shipSelector.getModule() instanceof Weapon) {
				weapons.add((Weapon) shipSelector.getModule());
			}
			
			if(shipSelector.getModule() instanceof WarpCoreModule) {
				ship.getData().setWarp((WarpCoreModule) shipSelector.getModule());
			}
		}
		
		ship.getData().setWeapon(new WeaponModule(ship.getData().getWeapon().getQuality(), weapons));
		
		return ship;
	}

	public ShipSelectorPanel(Ship ship, ModuleSwapper swapper, GameMouseListener mouse) {
		this.ship = ship;
		this.desc = new DescriptionBoxUI(500, 500, 250, 150);
		this.desc.setVisible(false);
		initializeSelectors(swapper, mouse);
	}
	
	
	public void update(Canvas canvas) {
		updateSelectors(canvas);
		updateDescBox(canvas);
	}
	
	public void draw(Graphics g) {
		g.drawImage(ship.getImage(), 50, 50, 500, 500, null);

		for (ShipSelector selector : selectors) {
			selector.draw(g);
		}
		
		desc.draw(g);
	}
	
	private void initializeSelectors(ModuleSwapper swapper, GameMouseListener mouse) {
		ShipData data = this.ship.getData();
		WeaponModule weapons = data.getWeapon();
		
		selectors = new ArrayList<>();
		
		selectors.add(new ShipSelector(weapons.getWeapons().get(0), desc, 375, 125, 50, 50, swapper, mouse));
		
		selectors.add(new ShipSelector(weapons.getWeapons().get(1), desc, 450, 200, 50, 50, swapper, mouse));
		
		selectors.add(new ShipSelector(weapons.getWeapons().get(2), desc, 450, 350, 50, 50, swapper, mouse));
		
		selectors.add(new ShipSelector(weapons.getWeapons().get(3), desc, 375, 425, 50, 50, swapper, mouse));
		
		selectors.add(new ShipSelector(data.getWarp(), desc, 100, 225, 150, 150, swapper, mouse));
	}
	
	public void updateSelectors(Canvas canvas) {
		int count = 0;
		
		for (ShipSelector selector : selectors) {
			selector.update(canvas);
			
			if(!selector.mouseInside(canvas)) {
				count++;
			}
		}
		
		if(count == selectors.size()) {
			desc.setVisible(false);
		}
	}
	
	public void updateDescBox(Canvas canvas) {
		Point point = canvas.getMousePosition();
		if(point != null) {
			int mouseX = point.x;
			int mouseY = point.y;
			
			desc.setX(mouseX);
			desc.setY(mouseY);
		}
		
		desc.update(canvas);
	}
	
}
