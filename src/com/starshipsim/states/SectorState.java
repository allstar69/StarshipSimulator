package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.starshipsim.entities.EnemySpaceStation;
import com.starshipsim.entities.Ship;
import com.starshipsim.files.FileIO;
import com.starshipsim.graphics.TiledBackground;
import com.starshipsim.listeners.KeyboardListener;
import com.sun.glass.events.KeyEvent;

public class SectorState extends State {
	private KeyboardListener keyboard;

	private Canvas canvas;
	
	private TiledBackground bg = new TiledBackground(FileIO.loadImage("resources/spaceBackground.png"));

	int currentOption = 0;

	private Ship ship;
	
	private EnemySpaceStation enemyStation;

	public SectorState(StateManager manager, Ship ship) {
		super(manager);
		this.keyboard = manager.getKeyboard();
		this.ship = ship;
		this.enemyStation = new EnemySpaceStation(600, 400);

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
		
		enemyStation.draw(g, canvas);
		
		ship.draw(g, canvas);
	}

	@Override
	public void end() {

	}
	
}
