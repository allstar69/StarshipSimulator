package com.starshipsim.world;

import java.util.Random;

import com.starshipsim.entities.Asteroid;
import com.starshipsim.entities.BlackHole;
import com.starshipsim.entities.Mine;
import com.starshipsim.enums.DangerType;
import com.starshipsim.enums.SectorStateType;

public class DangerousSector extends Sector {
	
	private DangerType type;
	
	public DangerousSector(){
		super(SectorStateType.DANGEROUS);
		
		generateContent();
	}
	public void randomWarp(){
		//warps you to a random sector
		
	}
	public DangerType getType() {
		return type;
	}
	public void setType(DangerType type) {
		this.type = type;
	}

	public void generateContent() {
		Random random = new Random();
		
		int num = random.nextInt(DangerType.values().length);
		DangerType type = DangerType.values()[num];
		
		switch(type) {
		case BLACKHOLE:
			this.getEntities().add(new BlackHole(600, 300, 640, 640));
			break;
		case METEORS:
			int meteorAmount = random.nextInt(10)+31;
			int xDir=random.nextInt(5)-2;
			int yDir=random.nextInt(5)-2;
			
			while(xDir==0 && yDir==0){
				xDir=random.nextInt(5)-2;
				yDir=random.nextInt(5)-2;
			}
			
			for (int i = 0; i < meteorAmount; i++) {
				int x = random.nextInt(1800);
				int y = random.nextInt(1000);
				
				Asteroid asteroid = new Asteroid(x, y, xDir, yDir);
				this.getEntities().add(asteroid);
			}
			break;
		case MINES:
			int mineAmount = random.nextInt(10)+31;
			for (int i = 0; i < mineAmount; i++) {
				int x = random.nextInt(1800);
				int y = random.nextInt(1000);
				
				Mine mine = new Mine(x, y, 64, 64);
				this.getEntities().add(mine);
			}
			break;
		}
	}
}
