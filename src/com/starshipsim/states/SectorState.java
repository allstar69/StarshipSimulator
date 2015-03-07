package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.starshipsim.entities.Ship;
import com.starshipsim.files.FileIO;
import com.starshipsim.graphics.TiledBackground;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.world.Grid;
import com.starshipsim.world.Sector;
import com.sun.glass.events.KeyEvent;

public class SectorState extends State {
	private KeyboardListener keyboard;

	private Canvas canvas;
	
	private TiledBackground bg = new TiledBackground(FileIO.loadImage("resources/spaceBackground.png"));

	int currentOption = 0;

	private Ship ship;
	
	private Sector sector;
	private Grid grid;
	public SectorState(StateManager manager, Ship ship, Grid grid) {
		super(manager);
		this.keyboard = manager.getKeyboard();
		this.ship = ship;
		this.grid=grid;
		sector = grid.getSector(ship.getSecX(), ship.getSecY());
		initialize();
	}
	@Override
	public void initialize() {
	}

	@Override
	public void update() {
		sector = grid.getSector(ship.getSecX(), ship.getSecY());
		sector.setKnown(true);
		sector.update();
		sector.checkCollision(ship);
		if(!sector.isKnown()){
			sector.setKnown(true);
		}
		if (keyboard.keyDown(KeyEvent.VK_ESCAPE)) {
			manager.popState();
		}
		ship.move(canvas);
	}

	@Override
	public void draw(Graphics g, Canvas canvas) {
		this.canvas = canvas;
		
		bg.draw(g, canvas);
		
		g.setColor(Color.white);
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 24));
		
		sector.draw(g, canvas);
		
		ship.draw(g, canvas);
		g.drawString(ship.getDurability()+"/"+ship.getMaxDurability(), 32, 32);
	}

	@Override
	public void end() {

	}
	
}
