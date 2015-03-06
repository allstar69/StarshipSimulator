package com.starshipsim.items;

public class ItemExplosiveBomb extends Item {
	public void run() {

	}
	
	public ItemExplosiveBomb(int amount) {
		setPrice(200);
		setAmount(amount);
		setName("Explosive Bomb");
	}
}
