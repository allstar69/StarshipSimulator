package com.starshipsim.weapons;

import com.starshipsim.items.Item;

public class Ammo extends Item{

	public Ammo(int amount) {
		setPrice(50);
		setAmount(amount);
		setName("Ammo");
	}

	public void run() {
		// TODO Auto-generated method stub
	}

}
