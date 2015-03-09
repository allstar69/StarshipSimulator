package com.starshipsim.world;

import java.util.ArrayList;
import java.util.Random;

import com.starshipsim.entities.Planet;
import com.starshipsim.entities.Player;
import com.starshipsim.enums.SectorStateType;
import com.starshipsim.items.Item;
import com.starshipsim.obstacles.LoseItems;
import com.starshipsim.obstacles.Obstacle;
import com.starshipsim.obstacles.Sabotage;
import com.starshipsim.rewards.Reward;

public class ExplorableSector extends Sector {

	private ArrayList<Reward> rewards;
	private ArrayList<Obstacle> obstacles;
	private Player play; // when player enters explorable, sets this as the player
	
	public ExplorableSector(){//randomizes the obstacles and rewards
		super(SectorStateType.EXPLORABLE);
		
		generateContent();
		generateRewards();
		generateObstacles();	

	}
	public void generateRewards(){
		ArrayList<Reward> tempRewards = new ArrayList<Reward>();
		Random ran = new Random();
		int num = ran.nextInt(5);//up to 5 possible rewards
		for(int i = 0; i < num; i++){
			tempRewards.add(new Reward()); //adds a reward to the list
		}
		
		setRewards(tempRewards);
	}
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
	public void run(Player player){
		setPlay(player);
		faceObstacles();
		receiveRewards();
		setState(SectorStateType.NEUTRAL);//sets to Neutral because it has been exhausted
	}
	public void faceObstacles(){
		
		for(Obstacle o : getObstacles()){
			o.run(getPlay());
		}
		
	}
	public void receiveRewards(){
		ArrayList<Item> tempInventory = getPlay().getInventory();

		for(Reward r : getRewards()){
			tempInventory.add(r.getRewardItem());
		}
		getPlay().setInventory(tempInventory);
		
	}
	
	public void generateContent(){
		Random random = new Random();
		
		int x = random.nextInt(1200);
		int y = random.nextInt(700);
		
		Planet planet = new Planet(x, y);
		this.getEntities().add(planet);
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
	public Player getPlay() {
		return play;
	}
	public void setPlay(Player play) {
		this.play = play;
	}	
}
