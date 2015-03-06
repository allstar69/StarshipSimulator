package com.starshipsim.shipmodules;

import com.starshipsim.entities.Ship;

public class WarpCore extends ShipModule {
	private int maxWarp = 3;

	public int getMaxWarp() {
		return maxWarp;
	}

	public void setMaxWarp(int maxWarp) {
		this.maxWarp = maxWarp;
	}

	public void warp(Ship ship, int x, int y) {
		ship.setSecX(x);
		ship.setSecY(y);
	}
}
