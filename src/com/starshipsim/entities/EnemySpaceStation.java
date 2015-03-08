package com.starshipsim.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

import com.starshipsim.files.FileIO;

public class EnemySpaceStation extends Entity implements Enemy{

	private static Image img = FileIO.loadImage("resources/enemy station.png");
	private int durability = 500;
	private int maxDurability = 500;
	public EnemySpaceStation(int x, int y) {
		super(img, x, y,400,400);
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
		g.drawImage(img, x, y, 400, 400, null);
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

	@Override
	public void takeDamage(int damage) {
		// TODO Auto-generated method stub
		setDurability(getDurability()-damage);
	}

	@Override
	public int dealDamage() {
		return 20;
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
