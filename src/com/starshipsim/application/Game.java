package com.starshipsim.application;

import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.states.MapState;

public class Game {
	private boolean isRunning;

	private KeyboardListener keyboard;
	
	private MapState mapState;
	
	public KeyboardListener getKeyboard() {
		return keyboard;
	}

	public void setKeyboard(KeyboardListener key) {
		this.keyboard = key;
	}

	public Game() {
		isRunning = true;
		
		keyboard = new KeyboardListener();
		mapState = new MapState(keyboard);
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
		mapState.initialize();
	}
	
	//For later use
	public void update() {
		getKeyboard().poll();
		mapState.update();
	}
	
	public void draw() {
		mapState.draw();
	}
	
	//For later use
	public void end() {
		mapState.end();
	}
}
