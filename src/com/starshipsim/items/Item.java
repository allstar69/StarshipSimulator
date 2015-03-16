package com.starshipsim.items;

public abstract class Item {

	protected int amount;
	protected int price;
	protected String name;
	protected String description = "This is a module meant to upgrade your ship.";
	protected int index;
	protected boolean isMoney;
	protected boolean isBattleItem=false;
	public abstract void run();
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		if(amount>200){
			amount=200;
		}
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
	public boolean isBattleItem() {
		return isBattleItem;
	}

	public void setBattleItem(boolean isBattleItem) {
		this.isBattleItem = isBattleItem;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String toString(){
		String retVal = "You have received " + getAmount() + " of " + getName() + ".";
		return retVal;
	}
	
}
