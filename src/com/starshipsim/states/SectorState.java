package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import com.starshipsim.entities.Ship;
import com.starshipsim.files.FileIO;
import com.starshipsim.graphics.TiledBackground;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.panels.MenuUI;
import com.sun.glass.events.KeyEvent;

public class SectorState extends State {

	private static Image imgEStation = FileIO.loadImage("resources/enemy station.png");
	
	private KeyboardListener keyboard;

	private Canvas canvas;
	
	private TiledBackground bg = new TiledBackground(
			FileIO.loadImage("resources/spaceBackground.png"));

	int currentOption = 0;

	private Ship ship;

	public SectorState(StateManager manager, Ship ship) {
		super(manager);
		this.keyboard = manager.getKeyboard();
		this.ship = ship;

		initialize();
	}

	@Override
	public void initialize() {

	}

	@Override
	public void update() {
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
		g.drawImage(imgEStation, 600, 400, 200, 200, null);
		
		ship.draw(g, canvas);
	}

	@Override
	public void end() {

	}
	
}
