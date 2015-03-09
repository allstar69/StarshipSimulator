package com.starshipsim.graphics;

import java.awt.Canvas;
import java.awt.Graphics;
import java.util.Random;

public class StarBackgroundFx {

	private int amount;
	private int width, height;
	
	private StarFx[] stars;
	
	public StarBackgroundFx(int amount, int width, int height) {
		this.amount = amount;
		this.width = width;
		this.height = height;
		
		initializeStars();
	}
	
	public void initializeStars() {
		this.stars = new StarFx[amount*2];
		
		for (int i = 0; i < stars.length/2; i++) {
			Random random = new Random();
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			
			stars[i] = new StarFx(x, y);
		}
		
		for (int i = stars.length/2; i < stars.length; i++) {
			Random random = new Random();
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			
			stars[i] = new SmallStarFx(x, y);
		}
	}

	public void update() {
		for (StarFx starFx : stars) {
			starFx.update();
			
			if(starFx.getX() > width) {
				starFx.setX(1);
			}
			
			if(starFx.getY() < 1) {
				starFx.setY(height);
			}
			
			if(starFx.getX() < 1) {
				starFx.setX(width);
			}
			
			if(starFx.getY() > height) {
				starFx.setY(1);
			}
		}

	}
	
	public void draw(Graphics g, Canvas canvas) {
		
		for (StarFx starFx : stars) {
			starFx.draw(g, canvas);
		}
		
	}
	
}
