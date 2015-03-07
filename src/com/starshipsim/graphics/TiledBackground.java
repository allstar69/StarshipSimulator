package com.starshipsim.graphics;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

public class TiledBackground {

	private Image image;
	private int x, y;
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public TiledBackground(Image image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g, Canvas canvas) {
		int tileHorz = canvas.getWidth()/image.getWidth(null) + 1;
		int tileVert = canvas.getHeight()/image.getHeight(null) + 1;
		
		this.x = x % canvas.getWidth();
		this.y = y % canvas.getHeight();
		
		for (int i = 0; i < tileHorz; i++) {
			for (int j = 0; j < tileVert; j++) {
				g.drawImage(image, x + i*image.getWidth(null), y + j*image.getHeight(null), null);
			}
		}
	}
	
}
