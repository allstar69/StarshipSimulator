package com.starshipsim.items;

public class ItemStunBomb extends Item {
	
	public ItemStunBomb(int amount) {
		setPrice(150);
		setAmount(amount);
		setName("Stun Bomb");
		setDescription("Stun bombs can temporarily stun an enemy vessel.");
		setIndex(2);
	}
	
	@Override
	public void run() {
	}

}
