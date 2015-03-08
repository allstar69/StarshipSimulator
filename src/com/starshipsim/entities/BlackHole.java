package com.starshipsim.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import com.starshipsim.graphics.ImageManager;

public class BlackHole extends Entity {
	private int rot;
	private AffineTransform xform = new AffineTransform();
	public BlackHole(int x, int y, int width, int height) {
		super(ImageManager.blackhole, x, y, width, height);
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		this.updateBoxes();
		rot+=1;;
		
		xform.setToTranslation(getX(), getY());
		xform.scale(2, 2);
		xform.rotate(rot * Math.PI/180, 160, 160);
	}

	@Override
	public void draw(Graphics g, Canvas canvas) {
		((Graphics2D)g).drawImage(this.getImage(), xform, null);
		
	}

}
