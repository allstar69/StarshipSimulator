package com.starshipsim.panels;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import com.starshipsim.entities.Player;
import com.starshipsim.interfaces.Drawable;
import com.starshipsim.items.Item;
import com.starshipsim.shipmodules.ShipModule;
import com.starshipsim.states.StateManager;
import com.starshipsim.ui.DescriptionBoxUI;
import com.starshipsim.ui.ScrollbarUI;

public class ShipModuleMenuPanel implements Drawable {

	private ArrayList<ShipModule> modules;
	private DescriptionBoxUI desc;
	private ScrollbarUI shipModules;
	
	private int x, y;
	
	public ShipModuleMenuPanel(StateManager manager, Player player, int x, int y, ModuleSwapper swapper) {		
		fetchModulesInventory(player);
		this.desc = new DescriptionBoxUI(500, 500, 250, 150);
		this.desc.setVisible(false);
		this.x = x;
		this.y = y;
		initializeButtons(manager, swapper);
	}
	
	public void initializeButtons(StateManager manager, ModuleSwapper swapper) {		
		shipModules = new ScrollbarUI(modules, x, y, 350, 650, 5, manager.getMouse(), this.desc, swapper);
	}
	
	private void fetchModulesInventory(Player player) {
		this.modules = new ArrayList<>();
		
		for (Item item : player.getInventory()) {
			if(item instanceof ShipModule) {
				modules.add((ShipModule) item);
			}
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
	
	@Override
	public void update(Canvas canvas) {
		this.shipModules.update(canvas);
		
		if(this.shipModules.getMouseInsideAmount(canvas) == this.shipModules.getButtons().length) {
			desc.setVisible(false);
		}
		
		this.updateDescBox(canvas);
	}

	@Override
	public void draw(Graphics g) {
		this.shipModules.draw(g);
		desc.draw(g);
	}

}
