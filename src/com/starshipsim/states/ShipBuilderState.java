package com.starshipsim.states;

import java.awt.Graphics;

import com.starshipsim.entities.Ship;
import com.starshipsim.graphics.StarBackgroundFx;
import com.starshipsim.panels.ShipSelectorPanel;
import com.sun.glass.events.KeyEvent;

public class ShipBuilderState extends State {
	
	private StarBackgroundFx bg = new StarBackgroundFx(100, 1920, 1080);
	private ShipSelectorPanel selectorPanel;
	
	public ShipBuilderState(StateManager manager, Ship ship) {
		super(manager);
		this.selectorPanel = new ShipSelectorPanel(ship);
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
		
		if(this.getManager().getKeyboard().keyDownOnce(KeyEvent.VK_ESCAPE)) {
			this.getManager().popState();
		}
	}
	
	public void draw(Graphics g) {
		bg.draw(g);
		selectorPanel.draw(g);
	}
	
}
