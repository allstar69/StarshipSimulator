package com.starshipsim.world;

import java.util.Random;

import com.starshipsim.entities.EnemySpaceStation;
import com.starshipsim.entities.Entity;

public class EnemySector extends Sector{
	
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
	
	public EnemySector()
	{
		setState(5);
		setHealth(100); //random num, subject to change
		setDestroyed(false);
		setKnown(false);
		generateContent();
	}
	
	//for later use
	public void generateContent() {
		Random random = new Random();
		
		int stationAmount = random.nextInt(4)+1;
		for (int i = 0; i < stationAmount; i++) {
			int x = random.nextInt(1000);
			int y = random.nextInt(1000);
			
			EnemySpaceStation station = new EnemySpaceStation(x, y);
			this.getEntities().add(station);
		}
	}
	
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
