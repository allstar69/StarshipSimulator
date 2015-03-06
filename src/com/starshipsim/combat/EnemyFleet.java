package com.starshipsim.combat;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import com.starshipsim.entities.EnemyShip;
import com.starshipsim.files.FileIO;

public class EnemyFleet {

	private final int MAX_SHIPS = 4;
	
	private ArrayList<EnemyShip> ships;
	
	public ArrayList<EnemyShip> getShips() {
		return ships;
	}

	public void setShips(ArrayList<EnemyShip> ships) {
		this.ships = ships;
	}

	//Temp
	private static Image ship = FileIO.loadImage("resources/eship1.png");
	
	public EnemyFleet() {
		ships = new ArrayList<>();
		generateShips();
	}
	
	private void generateShips() {
		Random random = new Random();
		int amount = random.nextInt(MAX_SHIPS)+1;
		
		for (int i = 0; i < amount; i++) {
			ships.add(new EnemyShip(ship, 0, 0, null));
		}
	}
	
}
