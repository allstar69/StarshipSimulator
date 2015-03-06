package com.starshipsim.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

import com.starshipsim.files.FileIO;

public class Mine extends Entity {
	private static Image img = FileIO.loadImage("resources/mine.png");
	
	public Mine(int x, int y) {
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
		g.drawImage(img, x, y, 64, 64, null);
	}

}
