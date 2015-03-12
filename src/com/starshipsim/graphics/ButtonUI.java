package com.starshipsim.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import com.starshipsim.listeners.GameMouseListener;
import com.starshipsim.ui.UIComponent;

public abstract class ButtonUI extends UIComponent {

	private final int startX, startY, startWidth, startHeight;
	private String text;
	private GameMouseListener mouse;
	
	public ButtonUI(String str, int x, int y, int width, int height, GameMouseListener mouse) {
		super(x, y, width, height);
		
		this.startX = x;
		this.startY = y;
		this.startWidth = width;
		this.startHeight = height;
		
		this.mouse = mouse;
		this.text = str;
	}
	
	@Override
	public void update(Canvas canvas) {
		if(this.mouseInside(canvas)) {
			if(getX() > startX-10 || getY() > startY-10 || getWidth() < startWidth+20 || getHeight() < startHeight+20) 
				expand();
			
			if(mouse.isLeftPressed()) {
				clicked();
			}
		} else {
			if(getX() > startX || getY() > startY || getWidth() > startWidth || getHeight() > startHeight) 
				shrink();
		}
		
	}
	
	public void expand() {
		setX(getX()-1);
		setY(getY()-1);
		setWidth(getWidth()+2);
		setHeight(getHeight()+2);
	}
	
	public void shrink() {
		setX(getX()+1);
		setY(getY()+1);
		setWidth(getWidth()-2);
		setHeight(getHeight()-2);
	}

	public abstract void clicked();
	
	@Override
	public void draw(Graphics g) {
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		
		Color color = new Color(.5f, .5f, .5f, 0.75f);
		g.setColor(color);
		g.fillRect(getX()+1, getY()+1, getWidth()-1, getHeight()-1);
		
		g.setColor(Color.white);
		FontMetrics metrics = g.getFontMetrics();
		g.drawString(text, getX()+getWidth()/2 - (metrics.stringWidth(text)/2), getY()+getHeight()/2 + (metrics.getHeight()/4));
	}
	
}
