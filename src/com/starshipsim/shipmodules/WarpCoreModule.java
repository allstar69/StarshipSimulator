package com.starshipsim.shipmodules;

import com.starshipsim.entities.Ship;
import com.starshipsim.enums.Quality;

public class WarpCoreModule extends ShipModule {
	public final int MAX_WARP;
	
	public WarpCoreModule(Quality quality) {
		super(quality);
		MAX_WARP = quality.ordinal() + 2;
	}

	public void warp(Ship ship, int x, int y) {
		ship.setSecX(x);
		ship.setSecY(y);
	}
}
