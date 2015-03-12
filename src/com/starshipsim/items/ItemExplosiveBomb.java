package com.starshipsim.items;

public class ItemExplosiveBomb extends Item {
	public void run() {

	}
	
	public ItemExplosiveBomb(int amount) {
		setPrice(200);
		setAmount(amount);
		setName("Explosive Bomb");
		setDescription("Explosive bombs can blow through your enemy vessels in a pinch.");
		setIndex(3);
		setBattleItem(true);
	}
}
