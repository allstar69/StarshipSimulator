package com.starshipsim.objects;

import java.awt.Image;
import java.util.Random;

import com.starshipsim.shipmodules.*;

public class Ship {
	// Starship class

	private Image image;
	private int durability = 100;
	private int maxDurability = 100;
	private int secX;
	private int secY;

	private ShipModule power = new PowerSystem();
	private ShipModule shield = new ShieldSystem();
	private ShipModule weapon = new WeaponSystem();
	private ShipModule propulsion = new PropulsionSystem();
	private ShipModule warp = new WarpCore();

	public Ship(Image image) {
		this.image = image;
		Random rand = new Random();
		secX = rand.nextInt(11);
		secY = rand.nextInt(11);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
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
}
