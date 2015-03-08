package com.starshipsim.entities;

public interface Enemy {
	public void takeDamage(int damage);
	public int dealDamage();
	public int getHealth();
	public int getMaxHealth();
	public void setX(int x);
	public void setY(int y);
	public int getX();
	public int getY();
}
