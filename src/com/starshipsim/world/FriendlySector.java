package com.starshipsim.world;

import java.awt.Image;

import com.starshipsim.entities.SpaceStation;
import com.starshipsim.files.FileIO;

public class FriendlySector extends Sector {
	private SpaceStation station;
	private static Image imgShip = FileIO.loadImage("resources/smallship1.png");
	
	public FriendlySector(){
		setState(2);
		setStation(new SpaceStation(imgShip, 0, 0));
	}
	public SpaceStation getStation() {
		return station;
	}
	public void setStation(SpaceStation station) {
		this.station = station;
	}
}
