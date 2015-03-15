package com.starshipsim.graphics;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;

import com.starshipsim.entities.Ship;
import com.starshipsim.listeners.KeyboardListener;

public class SpaceBackgroundFx {
	private int amount;
	private int width, height;
	private int x, y;
	private Ship ship;
	private StarFx[] stars1;
	private StarFx[] stars2;
	
	public SpaceBackgroundFx(int amount, int width, int height, Ship ship) {
		this.amount = amount;
		this.width = width;
		this.height = height;
		this.ship=ship;
		initializeStars();
	}
	
	public void initializeStars() {
		this.stars1 = new StarFx[amount];
		this.stars2 = new StarFx[amount];
		
		for (int i = 0; i < stars1.length; i++) {
			Random random = new Random();
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			
			stars1[i] = new StarFx(x, y);
		}
		
		for (int i =0; i < stars2.length; i++) {
			Random random = new Random();
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			
			stars2[i] = new SmallStarFx(x, y);
		}
	}

	public void update(KeyboardListener key, int speed) {
		if(key.keyDown(KeyEvent.VK_W) && ship.getDurability()>0 && ship.getY()>1){
			for (StarFx starFx : stars1) {
				starFx.setY(starFx.getY()+speed/2);
			}
			for (StarFx starFx : stars2) {
				starFx.setY(starFx.getY()+speed/2);
			}
			
		}
		else if(key.keyDown(KeyEvent.VK_S)&& ship.getDurability()>0 &&ship.getY()-32<1000){
			for (StarFx starFx : stars1) {
				starFx.setY(starFx.getY()-speed/2);
			}
			for (StarFx starFx : stars2) {
				starFx.setY(starFx.getY()-speed/2);
			}	
		}
		if(key.keyDown(KeyEvent.VK_A)&& ship.getDurability()>0 && ship.getX()>1){
			for (StarFx starFx : stars1) {
				starFx.setX(starFx.getX()+speed/2);
			}
			for (StarFx starFx : stars2) {
				starFx.setX(starFx.getX()+speed/2);
			}
		}
		else if(key.keyDown(KeyEvent.VK_D)&& ship.getDurability()>0 &&ship.getX()+32<1920){
			for (StarFx starFx : stars1) {
				starFx.setX(starFx.getX()-speed/2);
			}
			for (StarFx starFx : stars2) {
				starFx.setX(starFx.getX()-speed/2);
			}
		}
		for (StarFx starFx : stars1) {
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
		for (StarFx starFx : stars2) {
			
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
	
	public void draw(Graphics g) {
		
		for (StarFx starFx : stars1) {
			starFx.draw(g, null);
		}
		for (StarFx starFx : stars2) {
			starFx.draw(g, null);
		}
		
	}
}
