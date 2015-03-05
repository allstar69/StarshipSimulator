package com.starshipsim.combat;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import com.starshipsim.files.FileIO;
import com.starshipsim.objects.EnemyShip;

public class EnemyFleet {

	private final int MAX_SHIPS = 4;
	
	private ArrayList<EnemyShip> ships;
	
	//Temp
	private static Image ship = FileIO.loadImage("resources/smallship1.png");
	
	public EnemyFleet() {
		generateShips();
	}
	
	private void generateShips() {
		Random random = new Random();
		int amount = random.nextInt(MAX_SHIPS)+1;
		
		for (int i = 0; i < amount; i++) {
			ships.add(new EnemyShip(ship));
		}
	}
	
}
