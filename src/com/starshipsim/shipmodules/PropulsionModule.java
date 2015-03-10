package com.starshipsim.shipmodules;

import com.starshipsim.entities.Ship;
import com.starshipsim.enums.Quality;

public class PropulsionModule extends ShipModule {
	private int distance = 10;
	private int manuverablity = 1;

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getManuverablity() {
		return manuverablity;
	}

	public void setManuverablity(int manuverablity) {
		this.manuverablity = manuverablity;
	}

	public void moveShip(Ship ship) {

	}

	public PropulsionModule(Quality quality) {
		super(quality);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		String s = super.toString();
		s += "\n        Current Coordinates: ";
		return s;
	}
}
