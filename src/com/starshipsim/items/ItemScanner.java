package com.starshipsim.items;

public class ItemScanner extends Item {
	
	public void run() {

	}
	
	public ItemScanner(int amount) {
		setPrice(250);
		setAmount(amount);
		setName("Scanner");
		setDescription("The scanner can scan enemies to see their health and are used to identify unknown sectors.");
		setIndex(5);
		setBattleItem(true);
	}

}
