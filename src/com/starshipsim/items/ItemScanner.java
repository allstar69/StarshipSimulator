package com.starshipsim.items;

public class ItemScanner extends Item {
	
	public void run() {

	}
	
	public ItemScanner(int amount) {
		setPrice(250);
		setAmount(amount);
		setName("Scanner");
		setDescription("I assume the scanner scans things?");
		setIndex(5);
		setBattleItem(true);
	}

}
