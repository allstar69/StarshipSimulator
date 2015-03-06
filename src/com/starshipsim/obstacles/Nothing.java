package com.starshipsim.obstacles;

public class Nothing extends Obstacle {
	private boolean getsNothing;
	public Nothing(int amount){
		
		if(amount < 5){
			setGetsNothing(true);
		}
		
	}
	public void run(){
		
		System.out.println("YOU GET NOTHING");
		
	}
	public boolean isGetsNothing() {
		return getsNothing;
	}
	public void setGetsNothing(boolean getsNothing) {
		this.getsNothing = getsNothing;
	}

}
