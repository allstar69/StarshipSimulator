package com.starshipsim.states;

import java.awt.Graphics;

import com.starshipsim.entities.Player;
import com.starshipsim.graphics.StarBackgroundFx;
import com.starshipsim.panels.ShipModuleMenuPanel;
import com.starshipsim.panels.ShipSelectorPanel;
import com.sun.glass.events.KeyEvent;

public class ShipBuilderState extends State {
	
	private StarBackgroundFx bg = new StarBackgroundFx(100, 1920, 1080);
	private ShipSelectorPanel selectorPanel;
	private ShipModuleMenuPanel shipModuleMenu;
	
	public ShipBuilderState(StateManager manager, Player player) {
		super(manager);
		this.selectorPanel = new ShipSelectorPanel(player.getShip());
		this.shipModuleMenu = new ShipModuleMenuPanel(this.getManager(), player);
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
		selectorPanel.update(this.getCanvas());
		shipModuleMenu.update(this.getCanvas());
		
		if(this.getManager().getKeyboard().keyDownOnce(KeyEvent.VK_ESCAPE)) {
			this.getManager().popState();
		}
	}
	
	public void draw(Graphics g) {
		bg.draw(g);
		shipModuleMenu.draw(g);
		selectorPanel.draw(g);
	}
	
}
