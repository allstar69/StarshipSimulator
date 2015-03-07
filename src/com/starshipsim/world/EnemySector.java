package com.starshipsim.world;

import java.util.Random;

import com.starshipsim.combat.CombatData;
import com.starshipsim.combat.EnemyFleet;
import com.starshipsim.entities.Asteroid;
import com.starshipsim.entities.BlackHole;
import com.starshipsim.entities.EnemySpaceStation;
import com.starshipsim.entities.Entity;
import com.starshipsim.entities.Mine;
import com.starshipsim.entities.Ship;
import com.starshipsim.states.CombatState;
import com.starshipsim.states.StateManager;

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
		generateContent();
	}
	public void checkCollision(Ship s){
		for(int i=0; i<this.getEntities().size(); i++){
			Entity e =this.getEntities().get(i);
			if(s.isIntersecting(e) && e instanceof EnemySpaceStation){
				s.setX(e.getX()+e.getWidth()+10);
				s.setY(e.getY()+e.getHeight()/2);
				StateManager.addState(new CombatState(null, new CombatData(s, new EnemyFleet())));
			}
			
		}
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
