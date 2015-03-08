package com.starshipsim.obstacles;

import java.util.ArrayList;
import java.util.Random;

import com.starshipsim.entities.Player;
import com.starshipsim.items.Item;

public class LoseItems extends Obstacle {
	public LoseItems(int amount){
		
		//lose weapons?
		//anything else?
	}
	public void run(Player play){
		
		ArrayList<Item> currentInv = play.getInventory();
		Random ran = new Random();
		int num = ran.nextInt(currentInv.size());
		currentInv.remove(num);//removes a random item in the inventory
		play.setInventory(currentInv);//resets the player's inventory to the new inventory.
	}
}
