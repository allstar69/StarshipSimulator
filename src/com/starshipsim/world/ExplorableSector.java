package com.starshipsim.world;

import java.util.ArrayList;
import java.util.Random;

import com.starshipsim.obstacles.*;
import com.starshipsim.rewards.*;

public class ExplorableSector extends Sector {

	private ArrayList<Reward> rewards;
	private ArrayList<Obstacle> obstacles;
	
	public ExplorableSector(){//randomizes the obstacles and rewards
		setState(3);
		generateRewards();
		generateObstacles();	
	}
	public void generateRewards(){
		ArrayList<Reward> tempRewards = new ArrayList<Reward>();
		// TODO Generate Rewards
		setRewards(tempRewards);//set as blank until item class is made
	}
	//every 'amount' is currently set to 1, most likely will be changed later
	public void generateObstacles(){
		ArrayList<Obstacle> tempObstacles = new ArrayList<Obstacle>();
		
		Random ran = new Random();
		int chance = ran.nextInt(2);		//50% chance of Sabotage
		if(chance == 0){
			//gets Sabotaged
			Sabotage sab = new Sabotage(1);
			tempObstacles.add(sab);
			chance = ran.nextInt(5); 		//20% chance of Losing Items IF you were sabotaged
			if(chance == 0){
				//loses Items
				LoseItems li = new LoseItems(1);
				tempObstacles.add(li);
				chance = ran.nextInt(10);	//10% chance of getting no rewards IF you were sabotaged and lost items
				if(chance == 0){
					//clears out all rewards
					setRewards(new ArrayList<Reward>());
				}
			}
		}else{//They weren't sabotaged
			chance = ran.nextInt(2);		//50% chance of losing items
			if(chance == 0){
				//loses Items
				LoseItems li = new LoseItems(1);
				tempObstacles.add(li);
				chance = ran.nextInt(5);	//20% chance of getting no rewards IF you lost items
				if(chance == 0){
					setRewards(new ArrayList<Reward>());
				}
			}else{							//they weren't sabotaged and did not lose any items
				chance = ran.nextInt(5);	//20% chance of getting no rewards 
				if(chance == 0){
					setRewards(new ArrayList<Reward>());
				}
			}
		}
		
		
		setObstacles(tempObstacles);
	}
	public void run(){
		faceObstacles();
		receiveRewards();
	}
	public void faceObstacles(){
		
		for(Obstacle o : getObstacles()){
			o.run();
		}
		
	}
	public void receiveRewards(){
		
		//TODO basically going to be enhanced for loop that gives all rewards
		
	}
	public ArrayList<Reward> getRewards() {
		return rewards;
	}
	public void setRewards(ArrayList<Reward> rewards) {
		this.rewards = rewards;
	}
	public ArrayList<Obstacle> getObstacles() {
		return obstacles;
	}
	public void setObstacles(ArrayList<Obstacle> obstacles) {
		this.obstacles = obstacles;
	}	
}
