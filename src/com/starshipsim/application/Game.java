package com.starshipsim.application;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.states.StateManager;

public class Game extends JFrame {
	private static final long serialVersionUID = -3705761607281945291L;

	private final int WIDTH = 1920;
	private final int HEIGHT = 1000;
	
	private boolean isRunning;
	
	private StateManager states;

	private KeyboardListener keyboard;
	
	private Canvas canvas;
	
	public JTextField f1 = new JTextField();
	public JPanel p1 = new JPanel();
	
	public Game() {
		this.setTitle("Starship Simulator");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.canvas = new Canvas();
		this.canvas.setSize(new Dimension(WIDTH, HEIGHT));
		this.add(canvas);
		this.pack();
		this.setVisible(true);
		canvas.createBufferStrategy(2);

		isRunning = true;
		
		this.keyboard = new KeyboardListener();
		this.addKeyListener(keyboard);
		
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
		BufferStrategy bf = canvas.getBufferStrategy();
		Graphics g = bf.getDrawGraphics();
		
		try {
			states.getCurrentState().draw(g);
		} finally {
			g.dispose();
		}
		
		bf.show();
		Toolkit.getDefaultToolkit().sync();
	}
	
	//For later use
	public void end() {
		states.getCurrentState().end();
	}
	
	public void tBox() {
		p1.setSize(120, 30);
		this.add(p1, BorderLayout.CENTER);
		f1.addKeyListener(keyboard);
		f1.setVisible(false);
		p1.setVisible(false);
		p1.add(f1);
		p1.setLocation(890, 100);
	}
}
