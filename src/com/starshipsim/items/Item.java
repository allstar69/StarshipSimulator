package com.starshipsim.items;

public abstract class Item {

	int amount;
	int price;
	
	public abstract void run();

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	
}
