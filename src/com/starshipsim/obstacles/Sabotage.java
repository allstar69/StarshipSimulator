package com.starshipsim.obstacles;

import com.starshipsim.entities.Player;

public class Sabotage extends Obstacle {
	
	public Sabotage(int amount){
		
		
	}
	
	public void run(Player play){
		
		play.getShip().setDurability(play.getShip().getDurability() - 5); //sets the ships durability to 5 below what it is.
		
	}
	public String toString(){
		return "You have been sabotaged and your hull lost 5 durability.";
	}

}
