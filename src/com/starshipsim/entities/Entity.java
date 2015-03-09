package com.starshipsim.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public abstract class Entity {

	protected int x, y;
	int width = 0; 
	int height = 0;
	protected Image image;
	protected Rectangle bounds;
	
	public Entity(Image image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
		
		if(image != null) {
			this.width = image.getWidth(null);
			this.height = image.getHeight(null);
		}
		this.bounds = new Rectangle(x, y, width, height);
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
	
	public void updateBoxes() {
		this.bounds = new Rectangle(x, y, width, height);
	}
	
	public void drawBox(Graphics g) {
		g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
	}
	
	public boolean isIntersecting(Entity entity) {
		bounds = new Rectangle(x, y, width, height);
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
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
}
