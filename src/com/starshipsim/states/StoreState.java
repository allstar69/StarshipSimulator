package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.starshipsim.entities.Player;
import com.starshipsim.files.FileIO;
import com.starshipsim.graphics.TiledBackground;
import com.starshipsim.items.Item;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.panels.StoreMenuUI;

public class StoreState extends State {
	private static Image storeLeft = FileIO.loadImage("resources/storeLeft.png");
	private static Image storeRight = FileIO.loadImage("resources/storeRight.png");
	private static Image shipCursor = FileIO.loadImage("resources/smallship1.png");
	
	private int currentOption = 0;
	private String itemDesc = "Place Holder";
	private Player player;
	private ArrayList<Item> inventory;
	private boolean isBuying;
	
	private TiledBackground bg = new TiledBackground(FileIO.loadImage("resources/spaceBackground.png"), 0, 0);
	
	private String[] itemList;
	private String[] priceList;

	private StoreMenuUI menu;
	
	public StoreState(StateManager manager, Player p, boolean isBuying) {
		super(manager);
		this.isBuying = isBuying;
		inventory = p.getInventory();
		itemList = new String[inventory.size()];
		priceList = new String[inventory.size()];
		player=p;
		for (int ii = 0; ii < inventory.size(); ii++) {
			itemList[ii] = inventory.get(ii).getName();
			priceList[ii] = " $";
			priceList[ii] += (isBuying) ? inventory.get(ii).getPrice() : (int) (inventory.get(ii).getPrice() * .8);
		}
		initialize();
	}
	

	@Override
	public void initialize() {
		menu = new StoreMenuUI(manager.getKeyboard(), storeLeft, shipCursor, itemList, priceList, player, isBuying);
		
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
			if (isBuying) {
				if (player.getMoney() >= inventory.get(currentOption).getPrice()) {
					player.setMoney(player.getMoney()-inventory.get(currentOption).getPrice());
					inventory.get(currentOption).setAmount(inventory.get(currentOption).getAmount() + 1);
				}
			} else {
				if (inventory.get(currentOption).getAmount() > 0) {
					player.setMoney(player.getMoney()+ (int)(inventory.get(currentOption).getPrice() * .8));
					inventory.get(currentOption).setAmount(inventory.get(currentOption).getAmount() - 1);
				}
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
		g.drawString("MONEY: $" + player.getMoney(), 1370, 100);

		int menuY = (canvas.getHeight()/2) - (storeLeft.getHeight(null)/2);
		
		menu.setX(50);
		menu.setY(menuY);
		menu.draw(g);
		
		int rightX = menuY + storeLeft.getWidth(null) - 50;
		g.drawImage(storeRight, rightX, menuY, null);
		drawDescription(g, rightX, menuY);
		g.drawString("Currently Owned", 1275, 825);
		g.drawString(Integer.toString(inventory.get(currentOption).getAmount()), 1550, 900);
		g.drawString("Press Escape to return.", 32, 1050);
	}
	
	private void drawDescription(Graphics g, int containerX, int containerY) {
		String[] words = inventory.get(currentOption).getDescription().split(" ");
		ArrayList<String> display = new ArrayList<String>();
		
		FontMetrics mets = g.getFontMetrics();
		int lineHeight = mets.getHeight();
		int lineLength = storeRight.getWidth(null) - 100;

		String drawString = "";
		for (int i = 0; i < words.length; i++) {
			drawString += words[i] + " ";
			
			if (mets.getStringBounds(drawString, g).getWidth() > lineLength) {
				drawString = drawString.substring(0, drawString.trim().lastIndexOf(" "));
				display.add(drawString);
				drawString = "";
				
				i--;
			}
		}
		
		display.add(drawString);
	
		for (int j = 0; j < display.size(); j++) {
			g.drawString(display.get(j), containerX + 50, containerY + lineHeight*(j+1));
		}
	}

	@Override
	public void end() {
		
	}
}
