package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.starshipsim.entities.Player;
import com.starshipsim.files.FileIO;
import com.starshipsim.graphics.TiledBackground;
import com.starshipsim.items.Item;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.panels.MenuUI;
import com.starshipsim.panels.StoreMenuUI;

public class StoreState extends State {
	private static Image storeLeft = FileIO.loadImage("resources/storeLeft.png");
	private static Image storeRight = FileIO.loadImage("resources/storeRight.png");
	private static Image shipCursor = FileIO.loadImage("resources/smallship1.png");
	
	private int currentOption = 0;
	private String itemDesc = "Place Holder";
	private int currentMoney = 5600;
	private ArrayList<Item> inventory;
	
	private TiledBackground bg = new TiledBackground(FileIO.loadImage("resources/spaceBackground.png"), 0, 0);
	
	private String[] itemList;
	private String[] priceList;

	private StoreMenuUI menu;
	
	public StoreState(StateManager manager, Player p) {
		super(manager);
		inventory = p.getInventory();
		itemList = new String[inventory.size()];
		priceList = new String[inventory.size()];
		for (int ii = 0; ii < inventory.size(); ii++) {
			itemList[ii] = inventory.get(ii).getName();
			priceList[ii] = " $" + inventory.get(ii).getPrice();
		}
		initialize();
	}
	

	@Override
	public void initialize() {
		menu = new StoreMenuUI(manager.getKeyboard(), storeLeft, shipCursor, itemList, priceList);
		
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
			currentOption = menu.getCurrentOption();
			if (currentMoney >= inventory.get(currentOption).getPrice()) {
				currentMoney -= inventory.get(currentOption).getPrice();
				inventory.get(currentOption).setAmount(inventory.get(currentOption).getAmount() + 1);
			}
		}
	}

	@Override
	public void draw(Graphics g, Canvas canvas) {
		bg.draw(g, canvas);
		
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 58));
		
		g.setColor(Color.white);
		g.drawString("ITEM", 130, 100);
		g.drawString("PRICE", 950, 100);
		g.drawString("MONEY: $" + currentMoney, 1370, 100);

		int menuY = (canvas.getHeight()/2) - (storeLeft.getHeight(null)/2);
		
		menu.setX(50);
		menu.setY(menuY);
		menu.draw(g);
		
		int rightX = menuY + storeLeft.getWidth(null) - 50;
		g.drawImage(storeRight, rightX, menuY, null);
		g.drawString(itemDesc, 1300, 220);
		g.drawString("Currently Owned", 1275, 825);
		g.drawString(Integer.toString(inventory.get(currentOption).getAmount()), 1550, 900);
		g.drawString("Press Escape to return to the Star Map.", 32, 1050);
	}

	@Override
	public void end() {
		
	}
}
