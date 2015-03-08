package com.starshipsim.states;

import java.util.Stack;

import com.starshipsim.listeners.KeyboardListener;

public class StateManager {
	
	private static KeyboardListener keyboard;
	
	private static Stack<State> states = new Stack<>();
	
	public static KeyboardListener getKeyboard() {
		return keyboard;
	}

	public StateManager(KeyboardListener keyboard) {		
		StateManager.keyboard = keyboard;
		states.push(new MainMenuState(this));
	}

	public static State getCurrentState() {
		return states.peek();
	}

	public static void addState(State state) {
		states.push(state);
	}
	
	public static void popState() {
		states.pop();
	}
}
