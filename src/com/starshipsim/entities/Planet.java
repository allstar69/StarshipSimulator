package com.starshipsim.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import com.starshipsim.files.FileIO;

public class Planet extends Entity {

	//NEED A FRIENDLY SPACE STATION IMAGE
	private static Image img;
	
	public Planet(int x, int y) {
		super(img, x, y, 600, 600);
		Random random = new Random();
		img = FileIO.loadImage("resources/planet"+(random.nextInt(4)+1)+".png");
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
