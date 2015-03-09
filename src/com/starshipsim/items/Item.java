package com.starshipsim.items;

public abstract class Item {

	int amount;
	int price;
	String name;
	String description;
	boolean isMoney;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isMoney() {
		return isMoney;
	}
	public void setMoney(boolean isMoney) {
		this.isMoney = isMoney;
	}

	public String toString(){
		String retVal = "You have received " + getAmount() + " of " + getName() + ".";
		return retVal;
	}
	
}
