package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Graphics;

public abstract class State {

	protected StateManager manager;
	
	public State(StateManager manager) {
		this.manager = manager;
	}
	
	public abstract void initialize();
	public abstract void update();
	public abstract void draw(Graphics g, Canvas canvas);
	public abstract void end();
	
}
