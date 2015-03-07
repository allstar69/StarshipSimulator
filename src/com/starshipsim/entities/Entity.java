package com.starshipsim.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public abstract class Entity {

	protected int x, y, width, height;
	protected Image image;
	protected Rectangle bounds;
	
	public Entity(Image image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.bounds = new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
	}
	public Entity(Image image, int x, int y, int width, int height) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.width=width;
		this.height=height;
		this.bounds = new Rectangle(x, y, width, height);
	}
	
	public abstract void initialize();
	public abstract void update();
	public abstract void draw(Graphics g, Canvas canvas);
	
	public boolean isIntersecting(Entity entity) {
		bounds = new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
		return bounds.intersects(entity.bounds);
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	public  int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public  int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
