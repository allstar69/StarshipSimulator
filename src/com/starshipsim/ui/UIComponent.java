package com.starshipsim.ui;

import java.awt.Canvas;
import java.awt.Point;
import java.awt.Rectangle;

import com.starshipsim.interfaces.Drawable;

public abstract class UIComponent implements Drawable {

	private Rectangle rect;
	private boolean visible = true;
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getX() {
		return rect.x;
	}
	
	public int getY() {
		return rect.y;
	}
	
	public int getWidth() {
		return rect.width;
	}
	
	public int getHeight() {
		return rect.height;
	}
	
	public void setX(int x) {
		rect.x = x;
	}
	
	public void setY(int y) {
		rect.y = y;
	}
	
	public void setWidth(int width) {
		rect.width = width;
	}
	
	public void setHeight(int height) {
		rect.height = height;
	}
	
	public UIComponent(int x, int y, int width, int height) {
		this.rect = new Rectangle(x, y, width, height);
	}
	
	public UIComponent(Rectangle rect) {
		this.rect = rect;
	}
	
	public boolean mouseInside(Canvas canvas) {
		Point point = canvas.getMousePosition();
		
		if(point != null) {
			int mouseX = point.x;
			int mouseY = point.y;
		
			if(mouseX >= getX() && mouseY > getY() && mouseX < getX()+getWidth() && mouseY < getY()+getHeight()) {
				return true;
			} else {
				return false;
			}
		}
		
		return false;
	}
}
