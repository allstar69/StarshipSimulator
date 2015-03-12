package com.starshipsim.graphics;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import com.starshipsim.entities.Entity;

public class StarFx extends Entity {

	private static Random random = new Random();
	private float vel;
	private int angle;
	
	private static Image[] stars = {ImageManager.redStar, ImageManager.blueStar, ImageManager.whiteStar};
	
	public StarFx(int x, int y) {
		super(stars[random.nextInt(stars.length)], x, y);
		
		initialize();
	}

	@Override
	public void initialize() {
		vel = (float) (random.nextFloat()*1.25);
		angle = random.nextInt(10)+1;
	}
	
	public void update() {
		x += (vel * (float) Math.cos(Math.toRadians(angle-180)));
        y += (vel * (float) Math.sin(Math.toRadians(angle-180)));
	}

	@Override
	public void draw(Graphics g, Canvas canvas) {
		g.drawImage(this.getImage(), x, y, null);
	}
	
}
