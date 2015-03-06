package com.starshipsim.world;

import java.util.Random;

import com.starshipsim.entities.BlackHole;

public class DangerousSector extends Sector {
	
	private int dangerType;
	
	public DangerousSector(){
		setState(4);
		generateContent();
	}
	public void randomWarp(){
		//warps you to a random sector
		
	}
	public int getDangerType() {
		return dangerType;
	}
	public void setDangerType(int dangerType) {
		this.dangerType = dangerType;
	}
	public void generateContent() {
		Random random = new Random();
		
		int dangerType = random.nextInt(5)+1;
		//1=WormHole - warp ship
		//2=MeteorShower - damage Ship
		//3=SpaceMines - damage Ship
		//4=MapJammer - jam Map
		//5=HugeSun - High Gravity/ temporary warp disable
		if(dangerType==1){
			this.getEntities().add(new BlackHole(500, 300));
		}
	}
}
