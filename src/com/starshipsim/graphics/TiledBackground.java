package com.starshipsim.graphics;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

public class TiledBackground {

	private Image image;
	private int x, y;
	
	public TiledBackground(Image image, int x, int y) {
		this.image = image;
	}
	
	public void draw(Graphics g, Canvas canvas) {
		int tileHorz = canvas.getWidth()/image.getWidth(null) + 1;
		int tileVert = canvas.getHeight()/image.getHeight(null) + 1;
		
		for (int i = 0; i < tileHorz; i++) {
			for (int j = 0; j < tileVert; j++) {
				g.drawImage(image, x + i*image.getWidth(null), y + j*image.getHeight(null), null);
			}
		}
	}
	
}
