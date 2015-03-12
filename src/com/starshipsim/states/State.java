package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Graphics;

public abstract class State {

	protected int screenWidth, screenHeight;
	
	protected StateManager manager;
	
	public StateManager getManager() {
		return manager;
	}
	
	public Canvas getCanvas() {
		return getManager().getCanvas();
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public State(StateManager manager) {
		this.manager = manager;
	}
	
	public abstract void initialize();
	public abstract void update();
	public abstract void draw(Graphics g);
	public abstract void end();
	
}
