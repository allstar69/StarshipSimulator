package com.starshipsim.items;

public class ItemStunBomb extends Item {
	
	public ItemStunBomb(int amount) {
		setPrice(150);
		setAmount(amount);
		setName("Stun Bomb");
	}
	
	@Override
	public void run() {
	}

}
