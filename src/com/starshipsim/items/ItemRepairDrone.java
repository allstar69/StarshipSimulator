package com.starshipsim.items;

public class ItemRepairDrone extends Item {

	public ItemRepairDrone(int amount) {
		setPrice(600);
		setAmount(amount);
		setName("Repair Drone");
	}
	
	@Override
	public void run() {
		
	}
	

}
