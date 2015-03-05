package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import com.starshipsim.files.FileIO;
import com.starshipsim.listeners.KeyboardListener;
import com.sun.glass.events.KeyEvent;

public class CombatState extends State {

	private static Image background = FileIO.loadImage("resources/space.png");
	
	private KeyboardListener keyboard;
	
	public CombatState(StateManager manager) {
		super(manager);
		this.keyboard = manager.getKeyboard();
	}

	@Override
	public void initialize() {
		
	}

	@Override
	public void update() {
		if(keyboard.keyDownOnce(KeyEvent.VK_ESCAPE)) {
			manager.popState();
		}
	}

	@Override
	public void draw(Graphics g, Canvas canvas) {
		g.drawImage(background, 0, 0, null);
		
		g.setColor(Color.white);
		g.drawString("Press Escape to return the Map.", 32, 32);
	}

	@Override
	public void end() {
		
	}

}
