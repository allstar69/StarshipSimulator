package com.starshipsim.combat;

import java.util.ArrayList;
import java.util.Random;

import com.starshipsim.entities.EnemyShip;
import com.starshipsim.entities.EnemySpaceStation;
import com.starshipsim.entities.Entity;

public class StationFleet extends EnemyFleet{
	
	private ArrayList<Entity> ships;
	
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
		ships.add(new EnemySpaceStation(0, 0));
		ships.add(new EnemyShip(0, 0, null));
		
	}
}
