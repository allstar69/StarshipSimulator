package com.starshipsim.items;

public class ItemSatellite extends Item{

	public void run() {

	}
	
	public ItemSatellite(int amount) {
		setPrice(750);
		setAmount(amount);
		setName("Satellite");
		setDescription("You know, I'm not really sure what the satellite does...");
		setIndex(4);
	}
	
}
