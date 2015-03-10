package com.starshipsim.shipmodules;

import java.util.ArrayList;

import com.starshipsim.enums.Quality;
import com.starshipsim.weapons.Weapon;

public class WeaponModule extends ShipModule {
	private int weaponMax = 10;
	private ArrayList<Weapon> weapons = new ArrayList();

	public WeaponModule(Quality quality) {
		super(quality);
		// TODO Auto-generated constructor stub
	}
	
	public void addWeapon(Weapon wep) {
		weapons.add(wep);
	}

	public void removeWeapon(int i) {
		weapons.remove(i);
	}
}
