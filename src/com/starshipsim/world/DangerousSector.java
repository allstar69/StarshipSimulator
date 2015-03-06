package com.starshipsim.world;

import java.util.Random;

import com.starshipsim.entities.BlackHole;
import com.starshipsim.entities.Mine;

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
		
		int dangerType = random.nextInt(2)+1;
		//1=BlackHole - warp ship
		//2=MeteorShower - damage Ship
		//3=SpaceMines - damage Ship
		//4=MapJammer - jam Map
		//5=HugeSun - High Gravity/ temporary warp disable
		if(dangerType==1){
			this.getEntities().add(new BlackHole(500, 300));
		}
		if(dangerType==2){
			int stationAmount = random.nextInt(10)+31;
			for (int i = 0; i < stationAmount; i++) {
				int x = random.nextInt(1800);
				int y = random.nextInt(1000);
				
				Mine mine = new Mine(x, y);
				this.getEntities().add(mine);
			}
		}
	}
}
