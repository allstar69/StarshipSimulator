package com.starshipsim.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import com.starshipsim.files.FileIO;

public class BlackHole extends Entity {
	private static Image img = FileIO.loadImage("resources/blackhole.png");
	private int rot;
	private AffineTransform xform = new AffineTransform();
	public BlackHole(int x, int y, int width, int height) {
		super(img, x, y, width, height);
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
		((Graphics2D)g).drawImage(img, xform, null);
		
	}

}
