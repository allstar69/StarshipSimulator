package com.starshipsim.states;

import java.awt.Graphics;

public abstract class State {

	public State() {
		
	}
	
	public abstract void initialize();
	public abstract void update();
	public abstract void draw(Graphics g);
	public abstract void end();
	
}
