package com.starshipsim.items;

public class ItemFuel extends Item {

	@Override
	public void run() {

	}
	
	public ItemFuel(int amount) {
		setPrice(10);
		setAmount(amount);
	}
	
}
