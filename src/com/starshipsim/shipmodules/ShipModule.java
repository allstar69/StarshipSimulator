package com.starshipsim.shipmodules;

import com.starshipsim.enums.Quality;
import com.starshipsim.items.Item;

public abstract class ShipModule extends Item {
	private Quality quality;
	
	private String name;
	private int price = 100;

	private boolean isDestroyed = false;
	private int powerCost = 1;
	private int maxDurability = 100;
	private int currentDurability = 100;

	public Quality getQuality() {
		return quality;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	public int getPower() {
		return powerCost;
	}

	public void setPower(int power) {
		this.powerCost = power;
	}

	public int getMaxDurability() {
		return maxDurability;
	}

	public void setMaxDurability(int maxDurability) {
		this.maxDurability = maxDurability;
	}

	public int getCurrentDurability() {
		return currentDurability;
	}

	public void setCurrentDurability(int currentDurability) {
		this.currentDurability = currentDurability;
	}
	
	public ShipModule(String name, Quality quality) {
		this.quality = quality;
		this.name = name;
		setAmount(1);
	}

	public void damage(int damageNum) {
		setCurrentDurability(getCurrentDurability() - damageNum);
	}

	public void repair(int repairNum) {
		setCurrentDurability(getCurrentDurability() + repairNum);
		if (getCurrentDurability() > maxDurability) {
			setCurrentDurability(getMaxDurability());
		}
	}

	@Override
	public String toString() {
		String s = getName() + getCurrentDurability() + " / "
				+ getMaxDurability() + " Durability";
		return s;
	}

	public void setDefaults(int durability) {
		setMaxDurability(durability);
		setCurrentDurability(durability);
	}
}
