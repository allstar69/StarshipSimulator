package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

import com.starshipsim.files.FileIO;

public class CombatState extends State {

	private static Image background = FileIO.loadImage("resources/space.png");
	
	public CombatState(StateManager manager) {
		super(manager);
	}

	@Override
	public void initialize() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics g, Canvas canvas) {
		g.drawImage(background, 0, 0, null);
	}

	@Override
	public void end() {
		
	}

}
