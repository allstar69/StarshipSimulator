package com.starshipsim.shipmodules;

import com.starshipsim.entities.Ship;
import com.starshipsim.enums.Quality;

public class WarpCoreModule extends ShipModule {
	public final int MAX_WARP;
	
	public WarpCoreModule(Quality quality) {
		super("Warp Core Module", quality);
		setPrice(400 + quality.ordinal() * 125);
		setDescription("The warp core allows your ship to traverse the galaxy in great leaps");
		MAX_WARP = quality.ordinal() + 2;
	}

	public void warp(Ship ship, int x, int y) {
		ship.setSecX(x);
		ship.setSecY(y);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
