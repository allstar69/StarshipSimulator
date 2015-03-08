package com.starshipsim.entities;

import java.awt.Canvas;
import java.awt.Graphics;

import com.starshipsim.graphics.ImageManager;

public class Mine extends Entity {
	public Mine(int x, int y, int width, int height) {
		super(ImageManager.mine, x, y, width, height);
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
		g.drawImage(this.getImage(), x, y, width, height, null);
	}

}
