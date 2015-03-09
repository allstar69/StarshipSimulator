package com.starshipsim.states;

import java.util.Stack;

import com.starshipsim.entities.Player;
import com.starshipsim.listeners.GameMouseListener;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.world.Grid;

public class StateManager {
	
	private KeyboardListener keyboard;
	private GameMouseListener mouse;
	
	private Stack<State> states = new Stack<>();
	
	private Player player;
	private Grid grid;
	
	public GameMouseListener getMouse() {
		return mouse;
	}

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

	public StateManager(KeyboardListener keyboard, GameMouseListener mouse) {		
		this.keyboard = keyboard;
		this.mouse = mouse;
		
		states.push(new MainMenuState(this));
	}

	public State getCurrentState() {
		return states.peek();
	}
	
	public State getPreviousState() {
		return states.get(states.size()-2);
	}

	public void addState(State state) {
		states.push(state);
	}
	
	public void popState() {
		states.pop();
	}
}
