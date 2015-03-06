package com.starshipsim.rewards;

import java.util.Random;

import com.starshipsim.items.*;

public class Reward {
	private Item rewardItem;
	
	public Reward(){
		Random ran = new Random();
		int num = ran.nextInt(3);
		switch(num){
		case 0:
			setRewardItem(new ItemFuel());
			break;
		case 1:
			setRewardItem(new ItemRepairDrone());
			break;
			
		case 2:
			setRewardItem(new ItemStunBomb());
			break;		
		}
	}

	public Item getRewardItem() {
		return rewardItem;
	}
	public void setRewardItem(Item rewardItem) {
		this.rewardItem = rewardItem;
	}
	
}
