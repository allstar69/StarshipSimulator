package com.starshipsim.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.starshipsim.graphics.ImageManager;

public class Explosion extends Entity {
	private long loadTime=System.currentTimeMillis();
	private int animcount=0;
	public Explosion(int x, int y, int width, int height) {
		super(ImageManager.explosion, x, y, width, height);
	}


	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g, Canvas canvas) {
		
		play((Graphics2D) g, canvas, this, 0, 0, 192, 195, 26);
	}
	public boolean play(Graphics2D g, Canvas c, Entity o, int startx, int starty, int width, int height, int imageNum){
		if(System.currentTimeMillis()>loadTime+50){
			if(animcount<imageNum-1){
				animcount++;
			}
			loadTime=System.currentTimeMillis();
		}
		g.drawImage(this.getImage(), o.getX(), o.getY(), o.getX()+o.getWidth(), o.getY()+o.getHeight(), startx + animcount*width, starty, startx+(animcount+1)*width, starty+height, c);
		return(animcount==imageNum-1);
	}
	public void loop(Graphics2D g, Canvas c, Entity o, int startx, int starty, int width, int height, int imageNum){
		if(System.currentTimeMillis()>loadTime+200){
			if(animcount==imageNum-1){
				animcount=0;
			}
			else{
				animcount++;
			}
			loadTime=System.currentTimeMillis();
		}
		g.drawImage(this.getImage(), o.getX(), o.getY(), o.getX()+o.getWidth(), o.getY()+o.getHeight(), startx + animcount*width, starty, startx+(animcount+1)*width, starty+height, c);
		
	}
}
