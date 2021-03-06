package com.starshipsim.world;

import java.util.Random;

import com.starshipsim.entities.SpaceStation;
import com.starshipsim.enums.SectorStateType;

public class FriendlySector extends Sector {
	private SpaceStation station;
	
	public FriendlySector(){
		super(SectorStateType.FRIENDLY);
		
		setStation(new SpaceStation(0, 0));
		generateContent();
	}
	public SpaceStation getStation() {
		return station;
	}
	public void setStation(SpaceStation station) {
		this.station = station;
	}
	public void generateContent() {
		Random random = new Random();
		
		int x = random.nextInt(1000);
		int y = random.nextInt(700);
		
		SpaceStation station = new SpaceStation(x, y);
		this.getEntities().add(station);
	}
}
