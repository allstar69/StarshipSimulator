package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import com.starshipsim.files.FileIO;
import com.starshipsim.listeners.KeyboardListener;

public class MainMenuState extends State {

	private static Image background = FileIO.loadImage("resources/space.png");
	private static Image menu = FileIO.loadImage("resources/largemenu.png");
	private static Image shipCursor = FileIO.loadImage("resources/smallship1.png");
	
	private int currentOption = 0;
	
	private StateManager states;
	
	private String[] list = new String[] {
			"Play",
			"Load",
			"Exit",
	};
	
	public MainMenuState(StateManager states) {
		this.states = states;
		
		initialize();
	}
	
	@Override
	public void initialize() {
	}

	@Override
	public void update() {
		KeyboardListener keyboard = states.getKeyboard();
		
		if(keyboard.keyDownOnce(KeyEvent.VK_DOWN)) {
			currentOption++;
		} else if(keyboard.keyDownOnce(KeyEvent.VK_UP)) {
			currentOption--;
		}
		
		if(currentOption < 0) {
			currentOption = list.length-1;
		} else if(currentOption > list.length-1) {
			currentOption = 0;
		}
		
		if(keyboard.keyDownOnce(KeyEvent.VK_ENTER)) {
			switch(currentOption) {
			case 0:
				states.setCurrentState(new MapState(states));
				break;
			case 2:
				System.exit(0);
				break;
			}
		}
	}

	@Override
	public void draw(Graphics g, Canvas canvas) {
		g.drawImage(background, 0, 0, null);
		
		int menuX = (canvas.getWidth()/2) - (menu.getWidth(null)/2);
		int menuY = (canvas.getHeight()/2) - (menu.getHeight(null)/2);
		g.drawImage(menu, menuX, menuY, null);
		
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 72));
		FontMetrics metrics = g.getFontMetrics();
		
		int x = (menuX + (menu.getWidth(null)/2)) - metrics.stringWidth(list[0])/2;
		for (int i = 0; i < list.length; i++) {
			int y = menuY + (100 + (i * 90));
			g.drawString(list[i], x, y);
		}
		
		g.drawImage(shipCursor, (menuX + menu.getWidth(null)/2 - metrics.stringWidth(list[0])/2) - shipCursor.getWidth(null), menuY + (50 + (currentOption * 90)), null);
	}

	@Override
	public void end() {
		
	}

}
