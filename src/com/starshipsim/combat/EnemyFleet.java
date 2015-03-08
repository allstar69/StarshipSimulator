package com.starshipsim.combat;

import java.util.ArrayList;
import java.util.Random;

import com.starshipsim.entities.Enemy;
import com.starshipsim.entities.EnemyShip;
import com.starshipsim.entities.Entity;

public class EnemyFleet {

	private final int MAX_SHIPS = 3;
	private int reward;
	private ArrayList<Enemy> ships;
	
	public int getReward() {
		return reward;
	}

	public void setReward(int reward) {
		this.reward = reward;
	}

	public ArrayList<Enemy> getShips() {
		return ships;
	}

	public void setShips(ArrayList<Enemy> ships) {
		this.ships = ships;
	}

	public EnemyFleet() {
		ships = new ArrayList<>();
		generateShips();
	}
	
	private void generateShips() {
		Random random = new Random();
		int amount = random.nextInt(MAX_SHIPS)+1;
		
		for (int i = 0; i < amount; i++) {
			ships.add(new EnemyShip(0, 0, null));
			reward+=(random.nextInt(5)*10)+50;
		}
	}
	
}
