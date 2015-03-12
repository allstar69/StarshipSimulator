package com.starshipsim.items;

public class ItemRepairDrone extends Item {

	public ItemRepairDrone(int amount) {
		setPrice(600);
		setAmount(amount);
		setName("Repair Drone");
		setDescription("A repair drone can help to patch up your ship even when you're not at a friendly station.");
		setIndex(1);
		setBattleItem(true);
	}
	
	@Override
	public void run() {
		
	}
	

}
