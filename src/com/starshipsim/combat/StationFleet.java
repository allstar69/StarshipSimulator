package com.starshipsim.combat;

import java.util.ArrayList;
import java.util.Random;

import com.starshipsim.entities.EnemyShip;
import com.starshipsim.entities.EnemySpaceStation;
import com.starshipsim.entities.Entity;

public class StationFleet extends EnemyFleet{
	
	private int reward;
	private ArrayList<Entity> ships;
	
	public int getReward() {
		return reward;
	}

	public void setReward(int reward) {
		this.reward = reward;
	}
	public ArrayList<Entity> getShips() {
		return ships;
	}

	public void setShips(ArrayList<Entity> ships) {
		this.ships = ships;
	}

	public StationFleet() {
		ships = new ArrayList<>();
		generateShips();
	}
	
	private void generateShips() {
		Random random = new Random();
		
		ships.add(new EnemyShip(0, 0, null));
		reward+=(random.nextInt(5)*10)+50;
		ships.add(new EnemySpaceStation(0, 0));
		reward+=2000;
		ships.add(new EnemyShip(0, 0, null));
		reward+=(random.nextInt(5)*10)+50;
	}
}
