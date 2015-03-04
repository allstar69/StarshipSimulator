package com.starshipsim.states;

import com.starshipsim.listeners.KeyboardListener;

public class StateManager {

	private MapState mapState;
	private State currentState;
	
	public StateManager(KeyboardListener keyboard) {
		this.mapState = new MapState(keyboard);
		
		this.currentState = this.mapState;
	}

	public State getCurrentState() {
		return currentState;
	}
	
}
