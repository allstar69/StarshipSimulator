package com.starshipsim.shipmodules;

import java.util.ArrayList;

import com.starshipsim.enums.Quality;
import com.starshipsim.weapons.Weapon;

public class WeaponModule extends ShipModule {
	public final int MAX_WEAPONS = 4;
	
	private ArrayList<Weapon> weapons = new ArrayList<>();

	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}

	public void setWeapons(ArrayList<Weapon> weapons) {
		this.weapons = weapons;
	}

	public WeaponModule(Quality quality, ArrayList<Weapon> weapons) {
		super("Weapon Module", quality);
		setPrice(250 + quality.ordinal() * 150);
		setDescription("The weapons module determines which weapons you may mount on your ship");
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
