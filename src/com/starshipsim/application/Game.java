package com.starshipsim.application;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.starshipsim.listeners.GameMouseListener;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.states.StateManager;

public class Game extends JFrame {
	private static final long serialVersionUID = -3705761607281945291L;

	private final int WIDTH = 1920;
	private final int HEIGHT = 1080;

	private boolean isRunning;
	private boolean fullscreen = true;

	private StateManager states;

	private KeyboardListener keyboard;
	private GameMouseListener mouse;

	private Canvas canvas;

	public JTextField f1 = new JTextField();
	public JPanel p1 = new JPanel();
	public ImageIcon icon = new ImageIcon("resources/smallship1.png");

	private Font font = new Font("Showcard Gothic", Font.ITALIC, 24);

	public Game() {
		this.setTitle("Starship Simulator");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.canvas = new Canvas();
		this.canvas.setSize(new Dimension(WIDTH, HEIGHT));
		this.add(canvas);
		this.pack();
		this.setVisible(true);
		this.setIgnoreRepaint(true);
		this.setIconImage(icon.getImage());
		canvas.createBufferStrategy(2);
		canvas.setBackground(Color.black);

		isRunning = true;

		this.keyboard = new KeyboardListener();
		this.mouse = new GameMouseListener();
		this.addKeyListener(keyboard);
		canvas.addKeyListener(keyboard);
		canvas.addMouseListener(mouse);

		states = new StateManager(this.canvas);
	}

	public void run() {
		initialize();
		double next_game_tick = System.currentTimeMillis();
		int loops;

		while (isRunning) {
			loops = 0;
	        while (System.currentTimeMillis() > next_game_tick && loops < 5) {

	            update();
	            draw();

	            next_game_tick += 1000 / 60;
	            loops++;
	        }
			
			Toolkit.getDefaultToolkit().sync();
		}

		end();
	}

	// For later use
	public void initialize() {
	}

	public void update() {
		keyboard.poll();
		states.getCurrentState().update();

		switchFullScreen();
	}

	public void draw() {
		BufferStrategy bf = canvas.getBufferStrategy();
		Graphics2D g = (Graphics2D) bf.getDrawGraphics();

		g.setFont(font);

		g.clearRect(0, 0, this.WIDTH, this.HEIGHT);
		states.getCurrentState().draw(g);

		bf.show();
	}

	// For later use
	public void end() {
	}

	public void switchFullScreen() {
		if (keyboard.keyDownOnce(KeyEvent.VK_F5)) {
			if (this.fullscreen == true) {
				this.dispose();
				this.setExtendedState(JFrame.NORMAL);
				this.setUndecorated(false);
				this.setVisible(true);
				this.fullscreen = false;
			} else {
				this.dispose();
				this.setExtendedState(JFrame.MAXIMIZED_BOTH);
				this.setUndecorated(true);
				this.setVisible(true);
				this.fullscreen = true;
			}
		}
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
