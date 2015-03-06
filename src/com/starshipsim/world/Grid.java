package com.starshipsim.world;

import java.util.Random;

import com.starshipsim.enums.SectorState;
import com.starshipsim.objects.Ship;

public class Grid {
	private Sector[][] sectors;
	
	private int mysteryNum=40;
	private int hostileNum=75;
	private int friendlyNum=50;
	private int dangerNum=30;
	private int exploreNum=20;
	private int enemyNum = 3;
	
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
				sectors[j][i]= new Sector();
			}
		}
		
		for(int i=0; i<friendlyNum;i++){
			Sector s=sectors[rand.nextInt(12)][rand.nextInt(12)];
			while(s.getState()!=SectorState.NEUTRAL){
				s=sectors[rand.nextInt(12)][rand.nextInt(12)];
			}
			s.setState(2);
			s = new FriendlySector();
		}
		
		for(int i=0; i<exploreNum;i++){
			Sector s=sectors[rand.nextInt(12)][rand.nextInt(12)];
			while(s.getState()!=SectorState.NEUTRAL){
				s=sectors[rand.nextInt(12)][rand.nextInt(12)];
			}
			s.setState(3);
			s = new ExplorableSector();
		}
		
		for(int i=0; i<dangerNum;i++){
			Sector s=sectors[rand.nextInt(12)][rand.nextInt(12)];
			while(s.getState()!=SectorState.NEUTRAL){
				s=sectors[rand.nextInt(12)][rand.nextInt(12)];
			}
			s.setState(4);
			s = new DangerousSector();
		}
		
		for(int i=0; i<enemyNum;i++){
			Sector s=sectors[rand.nextInt(12)][rand.nextInt(12)];
			while(s.getState()!=SectorState.NEUTRAL){
				s=sectors[rand.nextInt(12)][rand.nextInt(12)];
			}
			s.setState(5);
			s = new EnemyStationSector();
		}
		
		for(int i=0; i<mysteryNum;i++){
			Sector s=sectors[rand.nextInt(12)][rand.nextInt(12)];
			while(s.isMysterious()){
				s=sectors[rand.nextInt(12)][rand.nextInt(12)];
			}
			s.setMysterious(true);
		}
		
		for(int i=0; i<hostileNum;i++){
			Sector s=sectors[rand.nextInt(12)][rand.nextInt(12)];
			while(s.isHostile()){
				s=sectors[rand.nextInt(12)][rand.nextInt(12)];
			}
			s.setHostile(true);
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
