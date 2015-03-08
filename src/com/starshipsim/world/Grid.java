package com.starshipsim.world;

import java.util.Random;

import com.starshipsim.entities.EnemyShip;
import com.starshipsim.entities.Ship;
import com.starshipsim.enums.SectorStateType;

public class Grid {
	private Sector[][] sectors;
	
	private int mysteryNum=15;
	private int hostileNum=75;
	private int friendlyNum=25;
	private int dangerNum=20;
	private int exploreNum=20;
	private int enemyNum = 5;
	
	public Sector[][] getSectors() {
		return sectors;
	}

	private void setSectors(Sector[][] sectors) {
		this.sectors = sectors;
	}

	public Grid() {
		setSectors(new Sector[12][12]);
		
		this.initialize();
	}
	
	private void initialize(){
		Random rand = new Random();
		
		for(int i = 0; i<sectors.length;i++){
			for(int j = 0; j<sectors.length;j++){
				sectors[j][i]= new NeutralSector();
			}
		}
		
		for(int i=0; i<friendlyNum;i++){
			int x = rand.nextInt(12);
			int y = rand.nextInt(12);
			
			Sector s=sectors[x][y];
			while(s.getState()!=SectorStateType.NEUTRAL){
				x = rand.nextInt(12);
				y = rand.nextInt(12);
				s=sectors[x][y];
			}
			sectors[x][y] = new FriendlySector();
		}
		
		for(int i=0; i<exploreNum;i++){
			int x = rand.nextInt(12);
			int y = rand.nextInt(12);
			
			Sector s=sectors[x][y];
			while(s.getState()!=SectorStateType.NEUTRAL){
				x = rand.nextInt(12);
				y = rand.nextInt(12);
				s=sectors[x][y];
			}
			sectors[x][y] = new ExplorableSector();
		}
		
		for(int i=0; i<dangerNum;i++){
			int x = rand.nextInt(12);
			int y = rand.nextInt(12);
			
			Sector s=sectors[x][y];
			while(s.getState()!=SectorStateType.NEUTRAL){
				x = rand.nextInt(12);
				y = rand.nextInt(12);
				s=sectors[x][y];
			}
			sectors[x][y] = new DangerousSector();
		}
		
		for(int i=0; i<enemyNum;i++){
			int x = rand.nextInt(12);
			int y = rand.nextInt(12);
			
			Sector s=sectors[x][y];
			while(s.getState()!=SectorStateType.NEUTRAL){
				x = rand.nextInt(12);
				y = rand.nextInt(12);
				s=sectors[x][y];
			}
			sectors[x][y] = new EnemySector();
		}
		
		for(int i=0; i<mysteryNum;i++){
			Sector s=sectors[rand.nextInt(12)][rand.nextInt(12)];
			while(s.isMysterious()){
				s=sectors[rand.nextInt(12)][rand.nextInt(12)];
			}
			s.setMysterious(true);
		}
		Random random=new Random();
		for(int i=0; i<hostileNum;i++){
			Sector s=sectors[rand.nextInt(12)][rand.nextInt(12)];
			while(s.isHostile()){
				s=sectors[rand.nextInt(12)][rand.nextInt(12)];
			}
			s.setHostile(true);
			int enemynum=random.nextInt(2)+1;
			for(int j=0; j<enemynum;j++){
				s.getEntities().add(new EnemyShip(random.nextInt(1600), random.nextInt(800), null));
			}
		}
	}

	public void setShipLocation(Ship ship, int x, int y) {
		sectors[x][y].setKnown(true);
	}
	
	public Sector getSector(int x, int y) {
		return sectors[x][y];
	}
	
	public void reset() {
		initialize();
	}
}
