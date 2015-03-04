package com.starshipsim.application;

import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.states.StateManager;

public class Game {
	private boolean isRunning;
	
	private StateManager states;

	private KeyboardListener keyboard;
	
	public Game() {
		isRunning = true;
		
		this.keyboard = new KeyboardListener();
		states = new StateManager(keyboard);
	}
	
	public void run() {
		initialize();
		
		while(isRunning) {
			update();
			draw();
		}
		
		end();
	}
	
	public void initialize() {
		states.getCurrentState().initialize();
	}
	
	//For later use
	public void update() {
		states.getCurrentState().update();
	}
	
	public void draw() {
		states.getCurrentState().draw();
	}
	
	//For later use
	public void end() {
		states.getCurrentState().end();
	}
}
