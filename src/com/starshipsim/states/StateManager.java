package com.starshipsim.states;

import java.awt.Canvas;
import java.util.Stack;

import com.starshipsim.entities.Player;
import com.starshipsim.listeners.GameMouseListener;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.world.Grid;

public class StateManager {
	
	private Canvas canvas;
	
	private Stack<State> states = new Stack<>();
	
	private Player player;
	private Grid grid;
	
	public Canvas getCanvas() {
		return canvas;
	}

	public GameMouseListener getMouse() {
		return (GameMouseListener) canvas.getMouseListeners()[0];
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
		return (KeyboardListener) canvas.getKeyListeners()[0];
	}

	public StateManager(Canvas canvas) {
		this.canvas = canvas;
		
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
