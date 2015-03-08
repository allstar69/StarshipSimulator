package com.starshipsim.obstacles;

public class Nothing extends Obstacle {
	private boolean getsNothing;
	public Nothing(int amount){
		
		if(amount < 5){
			setGetsNothing(true);
		}
		
	}
	public void run(){
		
		//don't need a run, it's job is done in generation
		//how to notify the user that this was an obstacle they faced?
		
	}
	public boolean isGetsNothing() {
		return getsNothing;
	}
	public void setGetsNothing(boolean getsNothing) {
		this.getsNothing = getsNothing;
	}

}
