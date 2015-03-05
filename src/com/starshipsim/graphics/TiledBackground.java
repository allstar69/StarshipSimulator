package com.starshipsim.graphics;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

public class TiledBackground {

	private Image image;
	
	public TiledBackground(Image image) {
		this.image = image;
	}
	
	public void draw(Graphics g, Canvas canvas) {
		int tileHorz = canvas.getWidth()/image.getWidth(null) + 1;
		int tileVert = canvas.getHeight()/image.getHeight(null) + 1;
		
		for (int x = 0; x < tileHorz; x++) {
			for (int y = 0; y < tileVert; y++) {
				g.drawImage(image, x*image.getWidth(null), y*image.getHeight(null), null);
			}
		}
	}
	
}
