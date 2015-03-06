package com.starshipsim.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

import com.starshipsim.files.FileIO;

public class SpaceStation extends Entity {

	//NEED A FRIENDLY SPACE STATION IMAGE
	private static Image img = FileIO.loadImage("resources/FriendlyStation.png");
	
	public SpaceStation(int x, int y) {
		super(img, x, y);
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g, Canvas canvas) {
		// TODO Auto-generated method stub
		g.drawImage(img, x, y, 600, 600, null);
	}

}
