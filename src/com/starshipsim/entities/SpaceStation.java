package com.starshipsim.entities;

import java.awt.Canvas;
import java.awt.Graphics;

import com.starshipsim.graphics.ImageManager;

public class SpaceStation extends Entity {
	
	public SpaceStation(int x, int y) {
		super(ImageManager.friendlyStation, x, y);
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
		g.drawImage(this.getImage(), x, y, 600, 600, null);
	}

}
