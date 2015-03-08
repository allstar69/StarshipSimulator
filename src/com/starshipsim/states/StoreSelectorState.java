package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import com.starshipsim.entities.Player;
import com.starshipsim.files.FileIO;
import com.starshipsim.graphics.TiledBackground;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.panels.MenuUI;

public class StoreSelectorState extends State {
	private static Image largeMenu = FileIO.loadImage("resources/mapscreen.png");
	private static Image shipCursor = FileIO.loadImage("resources/smallship1.png");
	private static Image station = FileIO.loadImage("resources/FriendlyStation.png");
	
	private TiledBackground bg = new TiledBackground(FileIO.loadImage("resources/spaceBackground.png"), 0, 0);
	
	private int currentOption = 0;
	private Player p;
	
	private String[] list = new String[] {
			"Upgrade Systems",
			"Buy Items",
			"Sell Items",
			"Return",
	};
	
	private MenuUI menu;
	
	public StoreSelectorState(StateManager manager, Player p) {
		super(manager);
		this.p = p;
		initialize();
	}
	
	@Override
	public void initialize() {
		menu = new MenuUI(manager.getKeyboard(), largeMenu, shipCursor, list);
	}

	@Override
	public void update() {
		KeyboardListener keyboard = manager.getKeyboard();
		
		menu.update();
		this.currentOption = menu.getCurrentOption();
		
		if(keyboard.keyDownOnce(KeyEvent.VK_ESCAPE)) {
			manager.popState();
		}
		
		if(keyboard.keyDownOnce(KeyEvent.VK_ENTER)) {
			switch(currentOption) {
			case 0:
				//Upgrade
				
				break;
			case 1:
				//Buy
				manager.addState(new StoreState(manager, p, true));
				break;
			case 2:
				//Sell?
				manager.addState(new StoreState(manager, p, false));
				break;
			case 3:
				//Exit
				manager.popState();
				break;
			}
		}
		
	}

	@Override
	public void draw(Graphics g, Canvas canvas) {
		bg.draw(g, canvas);
		g.setColor(Color.white);
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 72));
		
		int menuX = (canvas.getWidth()/2) - ((largeMenu.getWidth(null)/2) + (station.getWidth(null)/4));
		int menuY = (canvas.getHeight()/2) - (largeMenu.getHeight(null)/2);
		
		int stationX = menuX + largeMenu.getWidth(null) - station.getHeight(null)/2;
		int stationY = menuY + largeMenu.getWidth(null) - station.getHeight(null) + 175;
		
		menu.setX(menuX);
		menu.setY(menuY);
		menu.draw(g);
		
		g.drawImage(station, stationX, stationY, null);
		
		g.drawString("Welcome to the store!", 500, 80);
		g.drawString("Press Escape to return.", 32, 1050);
	}

	@Override
	public void end() {
		
	}

}
