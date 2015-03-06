package com.starshipsim.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.Random;

import com.starshipsim.files.FileIO;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.shipmodules.*;
import com.sun.glass.events.KeyEvent;

public class Ship extends Entity {
	private int durability = 100;
	private int maxDurability = 100;
	private int secX;
	private int secY;

	private ShipModule power = new PowerSystem();
	private ShipModule shield = new ShieldSystem();
	private ShipModule weapon = new WeaponSystem();
	private ShipModule propulsion = new PropulsionSystem();
	private ShipModule warp = new WarpCore();
	
	private int rot = 0;
	private int speed = 2;
	
	private static Image imgShip = FileIO.loadImage("resources/smallship1.png");
	private static Image imgShip2 = FileIO.loadImage("resources/smallship2.png");
	
	private boolean isFlying = false;

	private KeyboardListener keyboard;
	private AffineTransform xform = new AffineTransform();
	
	public Ship(int x, int y, KeyboardListener keyboard) {
		super(imgShip, x, y);
		
		Random rand = new Random();
		secX = rand.nextInt(11);
		secY = rand.nextInt(11);
		this.keyboard = keyboard;
	}
	
	@Override
	public void initialize() {
		
	}

	@Override
	public void update() {
		
	}
	
	@Override
	public void draw(Graphics g, Canvas canvas) {
		xform.setToTranslation(getX(), getY());
		xform.rotate(getRot() * Math.PI / 4, 16, 16);
		
		if (isFlying) {
			((Graphics2D) g).drawImage(imgShip2, xform, canvas);
		} else {
			((Graphics2D) g).drawImage(imgShip, xform, canvas);
		}
	}
	
	
	public void move(Canvas canvas) {
		if (keyboard.keyDown(KeyEvent.VK_W)) {
			if (keyboard.keyDown(KeyEvent.VK_A)) {
				setRot(5);
				if (getX() - 1 > canvas.getX()) {
					decelerateX();
				}
				else if(getSecX()-1>=0){
					setX(1880);
					setSecX(getSecX()-1);
				}
			} else if (keyboard.keyDown(KeyEvent.VK_D)) {
				setRot(7);
				if (getX() + 1 + imgShip.getWidth(null) < canvas.getX()
						+ canvas.getWidth()) {
					accelerateX();
				}
				else if(getSecX()+1<=11){
					setX(0);
					setSecX(getSecX()+1);
				}
			} else {
				setRot(6);
			}
			
			if (getY() - 1 > canvas.getY()) {
				decelerateY();
			}
			else if(getSecY()-1>=0){
				setY(970);
				setSecY(getSecY()-1);
			}
			isFlying = true;
		} else if (keyboard.keyDown(KeyEvent.VK_S)) {
			if (keyboard.keyDown(KeyEvent.VK_A)) {
				setRot(3);
				if (getX() - 1 > canvas.getX()) {
					decelerateX();
				}
				else if(getSecX()-1>=0){
					setX(1880);
					setSecX(getSecX()-1);
				}
			} else if (keyboard.keyDown(KeyEvent.VK_D)) {
				setRot(1);
				if (getX() + 1 + imgShip.getWidth(null) < canvas.getX()
						+ canvas.getWidth()) {
					accelerateX();
				}
				else if(getSecX()+1<=11){
					setX(0);
					setSecX(getSecX()+1);
				}
			} else {
				setRot(2);
			}
			
			if (getY() + 1 + imgShip.getHeight(null) < canvas.getY()+ canvas.getHeight()) {
				accelerateY();
			}
			else if(getSecY()+1<=11){
				setY(0);
				setSecY(getSecY()+1);
			}
			
			isFlying = true;
		} else if (keyboard.keyDown(KeyEvent.VK_A)) {
			setRot(4);
			
			if (getX() - 1 > canvas.getX()) {
				decelerateX();
			}
			else if(getSecX()-1>=0){
				setX(1880);
				setSecX(getSecX()-1);
			}
			isFlying = true;
		} else if (keyboard.keyDown(KeyEvent.VK_D)) {
			setRot(0);
			
			if (getX() + 1 + imgShip.getWidth(null) < canvas.getX() + canvas.getWidth()) {
				accelerateX();
			}
			else if(getSecX()+1<=11){
				setX(0);
				setSecX(getSecX()+1);
			}
			
			
			isFlying = true;
		} else {
			isFlying = false;
		}
	}

	public void decelerateX() {
		setX(getX()-getSpeed());
	}
	
	public void accelerateX() {
		setX(getX()+getSpeed());
	}
	
	public void decelerateY() {
		setY(getY()-getSpeed());
	}
	
	public void accelerateY() {
		setY(getY()+getSpeed());
	}

	public int getDurability() {
		return durability;
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}

	public int getMaxDurability() {
		return maxDurability;
	}

	public void setMaxDurability(int maxDurability) {
		this.maxDurability = maxDurability;
	}

	public int getSecX() {
		return secX;
	}

	public void setSecX(int secX) {
		this.secX = secX;
	}

	public int getSecY() {
		return secY;
	}

	public void setSecY(int secY) {
		this.secY = secY;
	}

	public ShipModule getPower() {
		return power;
	}

	public void setPower(ShipModule power) {
		this.power = power;
	}

	public ShipModule getShield() {
		return shield;
	}

	public void setShield(ShipModule shield) {
		this.shield = shield;
	}

	public ShipModule getWeapon() {
		return weapon;
	}

	public void setWeapon(ShipModule weapon) {
		this.weapon = weapon;
	}

	public ShipModule getPropulsion() {
		return propulsion;
	}

	public void setPropulsion(ShipModule propulsion) {
		this.propulsion = propulsion;
	}

	public ShipModule getWarp() {
		return warp;
	}

	public void setWarp(ShipModule warp) {
		this.warp = warp;
	}

	public int getRot() {
		return rot;
	}

	public void setRot(int rot) {
		this.rot = rot;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
