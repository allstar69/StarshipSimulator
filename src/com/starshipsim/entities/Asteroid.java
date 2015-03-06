package com.starshipsim.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import com.starshipsim.files.FileIO;

public class Asteroid extends Entity {
	private static Image img = FileIO.loadImage("resources/asteroid.png");
	private int xDir;
	private int yDir;
	private int rot;
	private AffineTransform xform = new AffineTransform();
	public Asteroid(int x, int y, int xDir, int yDir) {
		super(img, x, y);
		this.xDir=xDir;
		this.yDir=yDir;
		
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		x+=xDir;
		y+=yDir;
		if(x>2000){
			x=-64;
		}else if(x<-64){
			x=2000;
		}
		if(y>1100){
			y=-64;
		}else if(y<-64){
			y=1100;
		}
		rot++;
		xform.setToTranslation(x, y);
		xform.rotate(rot * Math.PI/180, 32, 32);
	}

	@Override
	public void draw(Graphics g, Canvas canvas) {
		((Graphics2D)g).drawImage(img, xform, null);
	}

}
