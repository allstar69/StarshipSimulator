package com.starshipsim.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;

import com.starshipsim.interfaces.Drawable;
import com.starshipsim.listeners.GameMouseListener;

public abstract class Button implements Drawable {

	private final int startX, startY, startWidth, startHeight;
	private int x, y, width, height;	
	private String text;
	private GameMouseListener mouse;
	
	public Button(String str, int x, int y, int width, int height, GameMouseListener mouse) {
		this.startX = x;
		this.startY = y;
		this.startWidth = width;
		this.startHeight = height;
		
		this.mouse = mouse;
		this.text = str;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void Update() {
		Point point = MouseInfo.getPointerInfo().getLocation(); 
		
		if(point.x >= x && point.y >= y && point.x <= x+width && point.y <= y+height) {
			if(x > startX-10 || y > startY-10 || width < startWidth+20 || height < startHeight+20) 
				expand();
			
			if(mouse.isLeftPressed()) {
				clicked();
			}
			
		} else {
			if(x > startX || y > startY || width > startWidth || height > startHeight) 
				shrink();
		}
		
	}
	
	public void expand() {
		x -= 1;
		y -= 1;
		width += 2;
		height += 2;
	}
	
	public void shrink() {
		x+=1;
		y+=1;
		width-=2;
		height-=2;
	}

	public abstract void clicked();
	
	@Override
	public void Draw(Graphics g, Canvas canvas) {
		g.drawRect(x, y, width, height);
		
		Color color = new Color(.5f, .5f, .5f, 0.75f);
		g.setColor(color);
		g.fillRect(x+1, y+1, width-1, height-1);
		
		g.setColor(Color.white);
		FontMetrics metrics = g.getFontMetrics();
		g.drawString(text, x+width/2 - (metrics.stringWidth(text)/2), y+height/2 + (metrics.getHeight()/4));
	}
	
}
