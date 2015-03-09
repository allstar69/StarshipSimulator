package com.starshipsim.items;

public class ItemCurrency extends Item {

	public ItemCurrency(int amount){
		setPrice(0);
		setAmount(amount);
		setName("Currency");
		setDescription("People can't buy this.");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//sets money to getMoney() + getAmount();
		
	}
	
}
