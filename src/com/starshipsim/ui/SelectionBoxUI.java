package com.starshipsim.ui;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import com.starshipsim.listeners.GameMouseListener;

public abstract class SelectionBoxUI extends UIComponent {
	
	private int startX, startY, startWidth, startHeight;
	private GameMouseListener mouse;
	private boolean isPressed = false;
	
	public SelectionBoxUI(int x, int y, int width, int height, GameMouseListener mouse) {
		super(x, y, width, height);
		this.startX = x;
		this.startY = y;
		this.startWidth = width;
		this.startHeight = height;
		this.mouse = mouse;
	}
	
	public abstract void clicked();
	
	public void update(Canvas canvas) {
		if(this.mouseInside(canvas)) {
			if(mouse.isLeftPressed() && isPressed  == false) {
				clicked();
				isPressed = true;
			}
			
			if(!mouse.isLeftPressed()) {
				isPressed  = false;
			}
			
			if(getX() > startX-10 || getY() > startY-10 || getWidth() < startWidth+20 || getWidth() < startHeight+20) {
				expand();
			}
		} else {
			if(getX() > startX || getY() > startY || getWidth() > startWidth || getHeight() > startHeight) 
				shrink();
		}
	}
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(2));
		
		g.setColor(Color.GREEN);
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		
		g2.setStroke(oldStroke);
	}
	
	public void expand() {
		setX(getX() - 1);
		setY(getY() - 1);
		setWidth(getWidth() + 2);
		setHeight(getHeight() + 2);
	}

	public void shrink() {
		setX(getX() + 1);
		setY(getY() + 1);
		setWidth(getWidth() - 2);
		setHeight(getHeight() - 2);
	}
	
}
