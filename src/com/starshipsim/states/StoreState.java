package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import com.starshipsim.files.FileIO;
import com.starshipsim.graphics.TiledBackground;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.panels.MenuUI;

public class StoreState extends State {
	private static Image storeLeft = FileIO.loadImage("resources/storeLeft.png");
	private static Image storeRight = FileIO.loadImage("resources/storeRight.png");
	private static Image shipCursor = FileIO.loadImage("resources/smallship1.png");
	
	private int currentOption = 0;
	private String itemDesc = "Place\nHolder";
	//private SpaceStation station;
	//private int total = 0;
	
	private TiledBackground bg = new TiledBackground(FileIO.loadImage("resources/spaceBackground.png"));
	
	//Buy list - temp - will be based on station inventory
	private String[] buyList = new String[] {
			"Fuel................................$10",
			"Repair Drone................$600",
			"Stun Bomb.....................$150",
			"Missile............................$100"
	};

	private MenuUI menu;
	
	public StoreState(StateManager manager) {
		super(manager);
		initialize();
	}
	
	@Override
	public void initialize() {

		menu = new MenuUI(manager.getKeyboard(), storeLeft, shipCursor, buyList);
		
	}

	@Override
	public void update() {
		KeyboardListener keyboard = manager.getKeyboard();
		
		switch (currentOption) {
		case 0:
			itemDesc = "This is fuel";
			break;
		case 1:
			itemDesc = "Repair drone";
			break;
		case 2:
			itemDesc = "Stun Bomb";
			break;
		case 3:
			itemDesc = "Missile";
			break;
		}
		
		menu.update();
		this.currentOption = menu.getCurrentOption();
		
		if(keyboard.keyDownOnce(KeyEvent.VK_ESCAPE)) {
			manager.popState();
		}
		
		if(keyboard.keyDownOnce(KeyEvent.VK_ENTER)) {
			currentOption = menu.getCurrentOption();
		}

	}

	@Override
	public void draw(Graphics g, Canvas canvas) {
		bg.draw(g, canvas);
		
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 58));
		
		g.setColor(Color.white);
		g.drawString("ITEM", 250, 100);
		g.drawString("PRICE", 950, 100);
		g.drawString("MONEY: $5600", 1370, 100);

		int menuY = (canvas.getHeight()/2) - (storeLeft.getHeight(null)/2);
		
		menu.setX(50);
		menu.setY(menuY);
		menu.draw(g);
		
		int rightX = menuY + storeLeft.getWidth(null) - 50;
		g.drawImage(storeRight, rightX, menuY, null);
		g.drawString(itemDesc, 1300, 220);
		g.drawString("Currently Owned", 1275, 825);
		g.drawString("1", 1550, 900);
		g.drawString("Press Escape to return to the Star Map.", 32, 1050);
	}

	@Override
	public void end() {
		
	}
}
