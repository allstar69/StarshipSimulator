package com.starshipsim.obstacles;

import java.util.ArrayList;
import java.util.Random;

import com.starshipsim.entities.Player;
import com.starshipsim.items.*;

public class LoseItems extends Obstacle {
	public LoseItems(int amount){
		
		//lose weapons?
		//anything else?
	}
	public void run(Player play){

		Random ran = new Random();
		int num = ran.nextInt(6);
		play.updateInventory(num, getRemovableItem(num));//removes a random item in the inventory
		
	}
	public Item getRemovableItem(int num){
		Item tempItem = new ItemFuel(-1);
		
		switch(num){
		case 0:
			tempItem = new ItemFuel(-1);
			break;
		case 1:
			tempItem = new ItemRepairDrone(-1);
			break;
		case 2:
			tempItem = new ItemStunBomb(-1);
			break;
		case 3:
			tempItem = new ItemExplosiveBomb(-1);
			break;
		case 4:
			tempItem = new ItemSatellite(-1);
			break;
		case 5:
			tempItem = new ItemScanner(-1);
			break;			
		}
		
		return tempItem;
	}
	public String toString(){
		return "You have lost a random item in your inventory.";
	}
}
