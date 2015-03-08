package com.starshipsim.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;

import com.starshipsim.graphics.ImageManager;

public class Asteroid extends Entity {
	private int xDir;
	private int yDir;
	private int rot;
	private int speed=5;
	private AffineTransform xform = new AffineTransform();
	private double size;
	public Asteroid(int x, int y, int xDir, int yDir) {
		super(ImageManager.asteroid, x, y);
		this.xDir=xDir;
		this.yDir=yDir;
		speed=new Random().nextInt(4)+4;
		size=new Random().nextDouble()+0.5;
		width*=size;
		height*=size;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		this.updateBoxes();
		
		x+=xDir*speed;
		y+=yDir*speed;
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
		rot+=2;
		
		xform.setToTranslation(x, y);
		xform.scale(size, size);
		xform.rotate(rot * Math.PI/180, 32, 32);
		
	}

	@Override
	public void draw(Graphics g, Canvas canvas) {
		((Graphics2D)g).drawImage(this.getImage(), xform, null);
	}

}
