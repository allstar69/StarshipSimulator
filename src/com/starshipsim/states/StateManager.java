package com.starshipsim.states;

import com.starshipsim.listeners.KeyboardListener;

public class StateManager {

	private State currentState;
	
	private KeyboardListener keyboard;
	
	public KeyboardListener getKeyboard() {
		return keyboard;
	}

	public StateManager(KeyboardListener keyboard) {		
		this.keyboard = keyboard;
		this.currentState = new MainMenuState(this);
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}
	
}
