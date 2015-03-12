package com.starshipsim.panels;

import java.awt.Canvas;
import java.awt.Graphics;
import java.util.ArrayList;

import com.starshipsim.entities.Player;
import com.starshipsim.graphics.ButtonUI;
import com.starshipsim.interfaces.Drawable;
import com.starshipsim.items.Item;
import com.starshipsim.shipmodules.ShipModule;
import com.starshipsim.states.StateManager;

public class ShipModuleMenuPanel implements Drawable {

	private ArrayList<ShipModule> modules;
	private ArrayList<ButtonUI> buttons;
	
	public ShipModuleMenuPanel(StateManager manager, Player player) {		
		fetchModulesInventory(player);
		initializeButtons(manager);
	}
	
	public void initializeButtons(StateManager manager) {
		buttons = new ArrayList<>();
		
		int x = 800;
		int y = 50;
		for (int i = 0; i < modules.size(); i++) {
			ShipModuleButton button = new ShipModuleButton(x, y + (i*125), 300, 100, manager.getMouse(), modules.get(i)) {
				@Override
				public void clicked() {
				}
			};
			
			buttons.add(button);
		}
	}
	
	private void fetchModulesInventory(Player player) {
		this.modules = new ArrayList<>();
		
		for (Item item : player.getInventory()) {
			if(item instanceof ShipModule) {
				modules.add((ShipModule) item);
			}
		}
	}
	
	@Override
	public void update(Canvas canvas) {
		for (ButtonUI buttonUI : buttons) {
			buttonUI.update(canvas);
		}
	}

	@Override
	public void draw(Graphics g) {
		for (ButtonUI buttonUI : buttons) {
			buttonUI.draw(g);
		}
	}

}
