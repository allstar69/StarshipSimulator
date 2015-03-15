package com.starshipsim.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import com.starshipsim.graphics.ImageManager;

public class Bullet extends Entity{
	public int rot;
	public AffineTransform xform = new AffineTransform();
	
	public Bullet(int rot,int x, int y, int width, int height) {
		super(ImageManager.proj1, x, y, width, height);
		this.rot=rot;
		this.setX(x);
		this.setY(y);
		this.xform.setToTranslation(x, y);
		xform.setToScale(width/32, height/32);
		this.xform.rotate((rot) * Math.PI / 180, width/2, height/2);
	}

	@Override
	public void initialize() {

	}
	
	@Override
	public void update() {
		setX(getX() + (int)(Math.cos(rot*Math.PI/180)*20));
		setY(getY() + (int)(Math.sin(rot*Math.PI/180)*20));
		xform.setToTranslation(getX(), getY());
		
		xform.rotate((rot) * Math.PI / 180, width/2, height/2);
		
	}
	
	@Override
	public void draw(Graphics g, Canvas canvas) {
		((Graphics2D)g).drawImage(this.getImage(), xform, null);
	}

	public int getRot() {
		return rot;
	}

	public void setRot(int rot) {
		this.rot = rot;
	}
}