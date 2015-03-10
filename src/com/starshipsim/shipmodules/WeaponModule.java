package com.starshipsim.shipmodules;

import java.util.ArrayList;

import com.starshipsim.enums.Quality;
import com.starshipsim.weapons.Weapon;

public class WeaponModule extends ShipModule {
	public final int MAX_WEAPONS = 4;
	
	private ArrayList<Weapon> weapons = new ArrayList<>();

	public WeaponModule(Quality quality, ArrayList<Weapon> weapons) {
		super(quality);
		this.weapons = weapons;
	}
	
	public void addWeapon(Weapon wep) {
		weapons.add(wep);
	}

	public void removeWeapon(int i) {
		weapons.remove(i);
	}
	
	public int shootAll() {
		int damage = 0;
		
		for(Weapon weapon : weapons) {
			damage += weapon.shoot();
		}
		
		return damage;
	}
}
