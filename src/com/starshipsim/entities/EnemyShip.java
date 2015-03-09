package com.starshipsim.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;

import com.starshipsim.graphics.ImageManager;
import com.starshipsim.interfaces.Enemy;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.world.Grid;

public class EnemyShip extends Ship implements Enemy {
	
	private static Random random = new Random();
	private AffineTransform xform = new AffineTransform();
	private double rot;
	private double nextrot;
	private long deltaTime=0;
	Grid grid;
	public EnemyShip(Grid grid, int x, int y,int secX, int secY, KeyboardListener keyboard) {
		super(ImageManager.enemyShip2, x, y, keyboard);
		rot=random.nextInt(360);
		deltaTime=System.currentTimeMillis();
		this.grid=grid;
		setSecX(secX);
		setSecY(secY);
	}
	public EnemyShip(int x, int y, KeyboardListener keyboard) {
		super(ImageManager.enemyShip2, x, y, keyboard);
		rot=random.nextInt(360);
		deltaTime=System.currentTimeMillis();
	}
	
	@Override
	public void draw(Graphics g, Canvas canvas) {
		
		// TODO Auto-generated method stub
		((Graphics2D) g).drawImage(this.getImage(), xform, canvas);
	}
	@Override
	public void update() {
		move();
		this.updateBoxes();
	}
	public void move(){
		if(System.currentTimeMillis()>deltaTime+100){
			nextrot=rot+15*(random.nextInt(3)-1);
			deltaTime=System.currentTimeMillis();
		}
		if(rot>=nextrot){
			rot--;
		}
		if(rot<=nextrot){
			rot++;
		}
		setX((int) (getX()+Math.cos(rot*Math.PI/180)*5));
		setY((int) (getY()+Math.sin(rot*Math.PI/180)*5));
		xform.setToTranslation(getX(), getY());
		xform.rotate((rot) * Math.PI / 180, 16, 16);
		if(getX()<0 && getSecX()>0){
			grid.getSector(getSecX()-1, getSecY()).getEntities().add(this);
			setX(1900);
			
			grid.getSector(getSecX(), getSecY()).getEntities().remove(this);
			setSecX(getSecX()-1);
		}
		if(getY()<0 && getSecY()>0){
			grid.getSector(getSecX(), getSecY()-1).getEntities().add(this);
			setY(1000);
			
			grid.getSector(getSecX(), getSecY()).getEntities().remove(this);
			setSecY(getSecY()-1);
		}
		if(getX()>1900 && getSecX()<10){
			grid.getSector(getSecX()+1, getSecY()).getEntities().add(this);
			setX(0);
			
			grid.getSector(getSecX(), getSecY()).getEntities().remove(this);
			setSecX(getSecX()+1);
			
		}
		if(getY()>1000 && getSecY()<10){
			grid.getSector(getSecX(), getSecY()+1).getEntities().add(this);
			setY(0);
			
			grid.getSector(getSecX(), getSecY()).getEntities().remove(this);
			setSecY(getSecY()+1);
		}
		
	}
	public double getRot1() {
		return rot;
	}
	public void setRot(double rot) {
		this.rot = rot;
	}
	@Override
	public void takeDamage(int damage) {
		// TODO Auto-generated method stub
		setDurability(getDurability()-damage);
	}

	@Override
	public int dealDamage() {
		return 5;
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return getDurability();
	}

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return getMaxDurability();
	}
}
