package com.starshipsim.shipmodules;

import com.starshipsim.enums.Quality;

public abstract class ShipModule {
	private Quality quality;
	
	private String pieceName = "";
	private int price = 100;

	private boolean isDestroyed = false;
	private int powerCost = 1;
	private int maxDurability = 100;
	private int currentDurability = 100;

	public String getPieceName() {
		return pieceName;
	}

	public void setPieceName(String pieceName) {
		this.pieceName = pieceName;
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
	
	public ShipModule(Quality quality) {
		this.quality = quality;
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
		String s = getPieceName() + getCurrentDurability() + " / "
				+ getMaxDurability() + " Durability";
		return s;
	}

	public void setDefaults(int durability) {
		setMaxDurability(durability);
		setCurrentDurability(durability);
	}
}
