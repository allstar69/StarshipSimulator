package com.starshipsim.states;

import java.awt.Graphics;

import com.starshipsim.entities.Player;
import com.starshipsim.graphics.ButtonUI;
import com.starshipsim.graphics.StarBackgroundFx;
import com.starshipsim.panels.ShipModuleMenuPanel;
import com.starshipsim.panels.ShipSelectorPanel;

public class ShipBuilderState extends State {
	
	private StarBackgroundFx bg = new StarBackgroundFx(100, 1920, 1080);
	private ShipSelectorPanel selectorPanel;
	private ShipModuleMenuPanel shipModuleMenu;
	
	private ButtonUI exitButton;
	
	public ShipBuilderState(StateManager manager, Player player) {
		super(manager);
		this.selectorPanel = new ShipSelectorPanel(player.getShip());
		this.shipModuleMenu = new ShipModuleMenuPanel(this.getManager(), player, 1570, 50);
		
		initializeButtons();
	}
	
	public void initializeButtons() {
		this.exitButton = new ButtonUI("Return", 50, 930, 300, 100, manager.getMouse()) {
			@Override
			public void clicked() {
				manager.popState();
			}
		};
	}
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}
	
	public void update() {
		bg.update();
		exitButton.update(this.getCanvas());
		selectorPanel.update(this.getCanvas());
		shipModuleMenu.update(this.getCanvas());
	}
	
	public void draw(Graphics g) {
		bg.draw(g);
		exitButton.draw(g);
		shipModuleMenu.draw(g);
		selectorPanel.draw(g);
	}
	
}
