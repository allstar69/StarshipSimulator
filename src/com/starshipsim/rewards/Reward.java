package com.starshipsim.rewards;

import java.util.Random;

import com.starshipsim.items.*;

public class Reward {
	private Item rewardItem;
	
	public Reward(){
		Random ran = new Random();
		int num = ran.nextInt(6);
		switch(num){
		case 0:
			setRewardItem(new ItemFuel(genNum(1,10)));
			break;
		case 1:
			setRewardItem(new ItemRepairDrone(genNum(1,5)));
			break;
		case 2:
			setRewardItem(new ItemStunBomb(genNum(1,3)));
			break;		
		case 3:
			setRewardItem(new ItemSatellite(genNum(1, 3)));
			break;
		case 4:
			setRewardItem(new ItemExplosiveBomb(genNum(1, 10)));
			break;
		case 5:
			setRewardItem(new ItemScanner(genNum(2, 7)));
			break;
		}
	}

	public int genNum(int low, int high){ //generates random number for amount of reward
		Random ran = new Random();
		int num = ran.nextInt(high) + low;
		
		return num;
	}
	
	public Item getRewardItem() {
		return rewardItem;
	}
	public void setRewardItem(Item rewardItem) {
		this.rewardItem = rewardItem;
	}
	
}
