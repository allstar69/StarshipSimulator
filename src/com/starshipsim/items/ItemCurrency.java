package com.starshipsim.items;

public class ItemCurrency extends Item {

	public ItemCurrency(int amount){
		setPrice(1);
		setAmount(amount);
		setName("Currency");
		setDescription("People can't buy this.");
		setMoney(true);
		setIndex(-1);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//sets money to getMoney() + getAmount();
		
	}
	public String toString(){
		
		return "You have received $" + getAmount() + ".";
		
	}
}
