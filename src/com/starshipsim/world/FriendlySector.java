package com.starshipsim.world;

import com.starshipsim.objects.SpaceStation;

public class FriendlySector extends Sector {
	private SpaceStation station;
	public FriendlySector(){
		setState(2);
		setStation(new SpaceStation());
	}
	public SpaceStation getStation() {
		return station;
	}
	public void setStation(SpaceStation station) {
		this.station = station;
	}
}
