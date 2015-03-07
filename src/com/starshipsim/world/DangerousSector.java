package com.starshipsim.world;

import java.util.Random;

import com.starshipsim.entities.Asteroid;
import com.starshipsim.entities.BlackHole;
import com.starshipsim.entities.Entity;
import com.starshipsim.entities.Mine;
import com.starshipsim.entities.Ship;

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
	@Override
	public void checkCollision(Ship s){
		for(int i=0; i<this.getEntities().size(); i++){
			Entity e =this.getEntities().get(i);
			if(s.isIntersecting(e) && e instanceof Asteroid){
				s.setDurability(s.getDurability()-1);
				this.getEntities().remove(e);
			}
			if(s.isIntersecting(e) && e instanceof Mine){
				
				s.setDurability(s.getDurability()-5);
				this.getEntities().remove(e);
			}
			if(s.isIntersecting(e) && e instanceof BlackHole){
				s.setSecX(new Random().nextInt(11));
				s.setSecY(new Random().nextInt(11));
			}
			
		}
		
	}
	public void generateContent() {
		Random random = new Random();
		
		dangerType = random.nextInt(3)+1;
		//1=BlackHole - warp ship
		//2=MeteorShower - damage Ship
		//3=SpaceMines - damage Ship
		//4=MapJammer - jam Map
		//5=HugeSun - High Gravity/ temporary warp disable
		if(dangerType==1){
			this.getEntities().add(new BlackHole(500, 300, 600, 600));
		}
		if(dangerType==2){
			int stationAmount = random.nextInt(10)+31;
			for (int i = 0; i < stationAmount; i++) {
				int x = random.nextInt(1800);
				int y = random.nextInt(1000);
				
				Mine mine = new Mine(x, y, 64, 64);
				this.getEntities().add(mine);
			}
		}
		if(dangerType==3){
			int stationAmount = random.nextInt(10)+31;
			int xDir=random.nextInt(3)-1;
			int yDir=random.nextInt(3)-1;
			while(xDir==0&&yDir==0){
				xDir=random.nextInt(3)-1;
				yDir=random.nextInt(3)-1;
			}
			for (int i = 0; i < stationAmount; i++) {
				int x = random.nextInt(1800);
				int y = random.nextInt(1000);
				
				Asteroid asteroid = new Asteroid(x, y, 64, 64, xDir, yDir);
				this.getEntities().add(asteroid);
			}
		}
	}
}
