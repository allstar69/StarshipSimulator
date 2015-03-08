package com.starshipsim.states;

import java.util.Stack;

import com.starshipsim.entities.Player;
import com.starshipsim.entities.Ship;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.world.Grid;

public class StateManager {
	
	private KeyboardListener keyboard;
	
	private Stack<State> states = new Stack<>();
	
	private Player player;
	private Grid grid;
	
	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

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
