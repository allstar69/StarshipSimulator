package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import com.starshipsim.files.FileIO;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.panels.MenuUI;
import com.sun.glass.events.KeyEvent;

public class CombatState extends State {

	private static Image imgBackground = FileIO.loadImage("resources/space.png");
	private static Image imgMenu = FileIO.loadImage("resources/smallmenu.png");
	private static Image shipCursor = FileIO.loadImage("resources/smallship1.png");
	
	private KeyboardListener keyboard;
	
	private MenuUI menu;
	
	private String[] list;
	
	int currentOption = 0;
	
	public CombatState(StateManager manager) {
		super(manager);
		this.keyboard = manager.getKeyboard();
		initialize();
	}

	@Override
	public void initialize() {
		list = new String[] {
			"Attack",
			"Items",
			"Defend",
			"Run"
		};
		
		menu = new MenuUI(manager.getKeyboard(), imgMenu, shipCursor, list);
	}

	@Override
	public void update() {
		if(keyboard.keyDownOnce(KeyEvent.VK_ESCAPE)) {
			manager.popState();
		}
		
		menu.update();
		currentOption = menu.getCurrentOption();
	}

	@Override
	public void draw(Graphics g, Canvas canvas) {
		g.drawImage(imgBackground, 0, 0, null);
		
		g.setColor(Color.white);
		g.drawString("Press Escape to return the Map.", 32, 32);
		
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 24));
		
		int menuX = (canvas.getWidth()/2) - (imgMenu.getWidth(null)/2);
		int menuY = (canvas.getHeight()/2);
		
		menu.setX(menuX);
		menu.setY(menuY);
		
		menu.draw(g);
	}

	@Override
	public void end() {
		
	}

}
