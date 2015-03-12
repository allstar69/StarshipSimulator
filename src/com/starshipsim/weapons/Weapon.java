package com.starshipsim.weapons;

import java.util.Random;

import com.starshipsim.enums.Quality;
import com.starshipsim.shipmodules.ShipModule;

public class Weapon extends ShipModule {

	private int baseDamage;
	
	public Weapon(String name, Quality quality) {
		super(name, quality);
		this.baseDamage = (quality.ordinal()*5)+2;
	}
	
	public int shoot() {
		Random random = new Random();
		return random.nextInt(5)+this.baseDamage;
	}

}
