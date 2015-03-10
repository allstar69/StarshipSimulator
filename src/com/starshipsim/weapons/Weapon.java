package com.starshipsim.weapons;

import java.util.Random;

import com.starshipsim.enums.Quality;
import com.starshipsim.items.WeaponItem;

public class Weapon extends WeaponItem {

	private int baseDamage;
	
	public Weapon(Quality quality) {
		this.baseDamage = (quality.ordinal()*5)+2;
	}
	
	public int shoot() {
		Random random = new Random();
		return random.nextInt(baseDamage)+3;
	}

}
