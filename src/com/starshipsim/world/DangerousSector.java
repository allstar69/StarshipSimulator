package com.starshipsim.world;

public class DangerousSector extends Sector {
	
	private int dangerType;
	
	public DangerousSector(){
		setState(4);
	}
	public void randomWarp(){
		//warps you to a random sector?
	}
	public int getDangerType() {
		return dangerType;
	}
	public void setDangerType(int dangerType) {
		this.dangerType = dangerType;
	}
}
