package com.starshipsim.shipmodules;

import com.starshipsim.enums.Quality;

public class ShieldModule extends ShipModule {
	private boolean isRaised = false;

	public boolean isRaised() {
		return isRaised;
	}

	public void toggleRaised() {
		isRaised = !isRaised;
	}

	public void recharge() {
		setCurrentDurability(getCurrentDurability() + getMaxDurability() / 10);
		if (getCurrentDurability() > getMaxDurability()) {
			setCurrentDurability(0);
		}
	}
	
	public ShieldModule(Quality quality) {
		super("Shield Module", quality);
		setPrice(300 + quality.ordinal() * 100);
		setDescription("The shield module can protect your ship from a limited amount of damage");
	}

	@Override
	public String toString() {
		String s = super.toString();
		if (isRaised) {
			s += "\n        The shields are raised.";
		} else {
			s += "\n        The shields are lowered.";
		}
		return s;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
