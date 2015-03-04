package com.starshipsim.states;

import javax.swing.JFrame;

public abstract class State extends JFrame {

	public State() {
		
	}
	
	public abstract void initialize();
	public abstract void update();
	public abstract void draw();
	public abstract void end();
	
}
