package com.starshipsim.combat;

import java.util.ArrayList;
import java.util.Random;

import com.starshipsim.entities.EnemyShip;
import com.starshipsim.entities.Entity;

public class EnemyFleet {

	private final int MAX_SHIPS = 3;
	
	private ArrayList<Entity> ships;
	
	public ArrayList<Entity> getShips() {
		return ships;
	}

	public void setShips(ArrayList<Entity> ships) {
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
		}
	}
	
}
