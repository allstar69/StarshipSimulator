package com.starshipsim.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.Random;

import com.starshipsim.files.FileIO;
import com.starshipsim.listeners.KeyboardListener;

public class EnemyShip extends Ship {
	
	private static Image imgEShip = FileIO.loadImage("resources/esmallship1.png");
	private static Image imgEShip2 = FileIO.loadImage("resources/esmallship2.png");
	private static Random random = new Random();
	private AffineTransform xform = new AffineTransform();
	private double rot;
	private double nextrot;
	private long deltaTime=0;
	public EnemyShip(int x, int y, KeyboardListener keyboard) {
		super(x, y, keyboard);
		rot=random.nextInt(360);
		deltaTime=System.currentTimeMillis();
	}
	@Override
	public void draw(Graphics g, Canvas canvas) {
		
		// TODO Auto-generated method stub
		((Graphics2D) g).drawImage(imgEShip2, xform, canvas);
	}
	@Override
	public void update() {
		move();
		this.updateBoxes();
	}
	public void move(){
		if(System.currentTimeMillis()>deltaTime+800){
			nextrot=rot+15*(random.nextInt(3)-1);
			deltaTime=System.currentTimeMillis();
		}
		if(rot>=nextrot){
			rot--;
		}
		if(rot<=nextrot){
			rot++;
		}
		setX((int) (getX()+Math.cos(rot*Math.PI/180)*3));
		setY((int) (getY()+Math.sin(rot*Math.PI/180)*3));
		xform.setToTranslation(getX(), getY());
		xform.rotate((rot) * Math.PI / 180, 16, 16);
		
	}
	public double getRot1() {
		return rot;
	}
	public void setRot(double rot) {
		this.rot = rot;
	}
}
