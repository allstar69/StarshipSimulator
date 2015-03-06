package com.starshipsim.world;

import com.starshipsim.entities.SpaceStation;

public class FriendlySector extends Sector {
	private SpaceStation station;
	
	public FriendlySector(){
		setKnown(false);
		setStation(new SpaceStation(0, 0));
	}
	public SpaceStation getStation() {
		return station;
	}
	public void setStation(SpaceStation station) {
		this.station = station;
	}
}
