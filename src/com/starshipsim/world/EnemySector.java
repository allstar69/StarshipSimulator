package com.starshipsim.world;

import java.util.Random;

import com.starshipsim.entities.EnemyShip;
import com.starshipsim.entities.EnemySpaceStation;
import com.starshipsim.enums.SectorStateType;

public class EnemySector extends Sector {

	public EnemySector()
	{
		super(SectorStateType.ENEMY);
		generateContent();
	}

	//for later use
	public void generateContent() {
		Random random = new Random();
		
		int stationAmount = random.nextInt(4)+1;
		
		for (int i = 0; i < stationAmount; i++) {
			int x = random.nextInt(1000);
			int y = random.nextInt(1000);
			
			EnemySpaceStation station = new EnemySpaceStation(x, y);
			
			while(this.checkCollision(station, EnemySpaceStation.class)) {
				x = random.nextInt(1000);
				y = random.nextInt(1000);
				
				station.setX(x);
				station.setY(y);
			}
			
			this.getEntities().add(station);
		}
	}
	
	public void update()
	{
		super.update();
		int counter = 0;
		
		for (int i =0; i<entities.size();i++) {
			if(entities.get(i) instanceof EnemySpaceStation){
				counter++;
			}
		}
		
		if(counter == 0)
		{
			this.setState(SectorStateType.NEUTRAL);
		}
	}
	
}
