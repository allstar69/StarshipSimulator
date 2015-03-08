package com.starshipsim.states;

import java.util.Stack;

import com.starshipsim.listeners.KeyboardListener;

public class StateManager {
	
	private static KeyboardListener keyboard;
	
	private static Stack<State> states = new Stack<>();
	
	public KeyboardListener getKeyboard() {
		return keyboard;
	}

	public StateManager(KeyboardListener keyboard) {		
		StateManager.keyboard = keyboard;
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
