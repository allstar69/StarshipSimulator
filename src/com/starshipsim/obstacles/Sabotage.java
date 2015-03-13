package com.starshipsim.obstacles;

import java.util.Random;

import com.starshipsim.entities.Player;

public class Sabotage extends Obstacle {
	private int realDamage;
	
	public Sabotage(int amount){
		
		
	}
	
	public void run(Player play){
		Random ran = new Random();
		double percentToBeSabotaged = ran.nextInt(20);
			percentToBeSabotaged = percentToBeSabotaged / 100;
		double damageAmount = percentToBeSabotaged * (play.getShip().getDurability());
		setRealDamage((int)damageAmount);
		
		play.getShip().setDurability(play.getShip().getDurability() - getRealDamage()); //sets the ships durability to 5 below what it is.

	}
	public String toString(){
		return "You have been sabotaged and your hull lost " + getRealDamage() + " durability.";
	}

	public int getRealDamage() {
		return realDamage;
	}

	public void setRealDamage(int realDamage) {
		this.realDamage = realDamage;
	}
	

}
