package com.starshipsim.ui;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;

public class DescriptionBoxUI extends UIComponent {

	private int width, height;
	private ArrayList<String> desc;
	private boolean done = false;

	public ArrayList<String> getDesc() {
		return desc;
	}

	public void setDesc(ArrayList<String> desc) {
		this.desc = desc;
	}

	public DescriptionBoxUI(ArrayList<String> desc, int x, int y, int width, int height) {
		super(x, y, 1, 1);
		this.width = width;
		this.height = height;
		this.desc = desc;
	}
	
	public DescriptionBoxUI(int x, int y, int width, int height) {
		super(x, y, 1, 1);
		this.width = width;
		this.height = height;
		this.desc = new ArrayList<String>();
	}

	@Override
	public void update(Canvas canvas) {
		if(this.isVisible()) {
			if(this.getWidth() < width) {
				this.setWidth(getWidth() + 8);
			}
			
			if(this.getHeight() < height) {
				this.setHeight(getHeight() + 8);
			}
			
			if(this.getWidth() >= width && this.getHeight() >= height) {
				this.done = true;
			}
		} else {
			reset();
			this.done = false;
		}
	}

	private void reset() {
		this.setWidth(1);
		this.setHeight(1);
	}

	@Override
	public void draw(Graphics g) {		
		if(this.isVisible()) {
			Graphics2D g2 = (Graphics2D) g;
			
			Stroke oldStroke = g2.getStroke();
			g2.setStroke(new BasicStroke(2));
			
			g.setColor(Color.GREEN);
			g.drawRect(getX(), getY(), getWidth(), getHeight());
			
			g.setColor(new Color(0, 0, 0, 128));
			g.fillRect(getX()+1, getY()+1, getWidth()-1, getHeight()-1);
			
			drawDesc(g);
			
			g2.setStroke(oldStroke);
		}
	}
	
	public void drawDesc(Graphics g) {	
		if(this.done) {
			FontMetrics mets = g.getFontMetrics();
			int lineHeight = mets.getHeight();
			
			g.setColor(Color.GREEN);
			for (int i = 0; i < desc.size(); i++) {
				if(desc.get(i) != null)
					g.drawString(desc.get(i), getX()+5, getY() + lineHeight*(i+1));
			}
		}
	}

}
