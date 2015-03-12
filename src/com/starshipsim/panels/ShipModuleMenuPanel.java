package com.starshipsim.panels;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import com.starshipsim.entities.Player;
import com.starshipsim.graphics.ButtonUI;
import com.starshipsim.interfaces.Drawable;
import com.starshipsim.items.Item;
import com.starshipsim.shipmodules.ShipModule;
import com.starshipsim.states.StateManager;
import com.starshipsim.ui.DescriptionBoxUI;

public class ShipModuleMenuPanel implements Drawable {

	private ArrayList<ShipModule> modules;
	private ArrayList<ButtonUI> buttons;
	private DescriptionBoxUI desc;
	
	public ShipModuleMenuPanel(StateManager manager, Player player) {		
		fetchModulesInventory(player);
		this.desc = new DescriptionBoxUI(500, 500, 250, 150);
		this.desc.setVisible(false);
		initializeButtons(manager);
	}
	
	public void initializeButtons(StateManager manager) {
		buttons = new ArrayList<>();
		
		int x = 800;
		int y = 50;
		for (int i = 0; i < modules.size(); i++) {
			ShipModuleButton button = new ShipModuleButton(x, y + (i*125), 300, 100, manager.getMouse(), modules.get(i), desc) {
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
		int count = 0;
		for (ButtonUI buttonUI : buttons) {
			buttonUI.update(canvas);
			
			if(!buttonUI.mouseInside(canvas)) {
				count++;
			}
		}
		
		if(count == buttons.size()) {
			desc.setVisible(false);
		}
		
		this.updateDescBox(canvas);
	}

	@Override
	public void draw(Graphics g) {
		for (ButtonUI buttonUI : buttons) {
			buttonUI.draw(g);
		}
		
		desc.draw(g);
	}

}
