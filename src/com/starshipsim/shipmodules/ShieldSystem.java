package com.starshipsim.shipmodules;

public class ShieldSystem extends ShipModule {
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
}
