package com.starshipsim.states;

import java.util.Stack;

import com.starshipsim.listeners.KeyboardListener;

public class StateManager {
	
	private KeyboardListener keyboard;
	
	private Stack<State> states = new Stack<>();
	
	public KeyboardListener getKeyboard() {
		return keyboard;
	}

	public StateManager(KeyboardListener keyboard) {		
		this.keyboard = keyboard;
		states.push(new MainMenuState(this));
	}

	public State getCurrentState() {
		return states.peek();
	}

	public void addState(State state) {
		states.push(state);
	}
	
	public void popState() {
		states.pop();
	}
}
