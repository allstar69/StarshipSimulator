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
		
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
		@Override
	public void update() {
		// TODO Auto-generated method stub
			setX((int) (getX()+Math.cos(rot*Math.PI/180)*20));
			setY((int) (getY()+Math.sin(rot*Math.PI/180)*20));
			xform.setToTranslation(getX(), getY());
			xform.rotate((rot) * Math.PI / 180, 16, 16);
	}
		@Override
	public void draw(Graphics g, Canvas canvas) {
		((Graphics2D)g).drawImage(this.getImage(), xform, null);
	}
}