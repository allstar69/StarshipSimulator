package com.starshipsim.shipmodules;

import com.starshipsim.entities.Ship;
import com.starshipsim.enums.Quality;

public class WarpCoreModule extends ShipModule {
	private int maxWarp = 3;

	public int getMaxWarp() {
		return maxWarp;
	}

	public void setMaxWarp(int maxWarp) {
		this.maxWarp = maxWarp;
	}
	
	public WarpCoreModule(Quality quality) {
		super(quality);
		// TODO Auto-generated constructor stub
	}

	public void warp(Ship ship, int x, int y) {
		ship.setSecX(x);
		ship.setSecY(y);
	}
}
