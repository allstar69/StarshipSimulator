package com.starshipsim.items;

public class ItemFuel extends Item {

	@Override
	public void run() {

	}
	
	public ItemFuel(int amount) {
		setPrice(10);
		setAmount(amount);
		setName("Fuel");
		setDescription("Fuel allows to explore the galaxy. Run out of it and you'll be stranded.");
		setIndex(0);
	}
	
}
