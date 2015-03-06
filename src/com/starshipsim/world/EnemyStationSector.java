package com.starshipsim.world;

public class EnemyStationSector extends Sector{
	
	private boolean isDestroyed;
	private int health;
	
	public boolean isDestroyed() {
		return isDestroyed;
	}
	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	
	public EnemyStationSector()
	{
		setHealth(100); //random num, subject to change
		setDestroyed(false);
	}
	
	//for later use
	public void createEnemyShips(){
		
	}
	
	public void checkIfDestroyed()
	{
		if(getHealth() <= 0)
		{
			setDestroyed(true);
		}
	}
}
