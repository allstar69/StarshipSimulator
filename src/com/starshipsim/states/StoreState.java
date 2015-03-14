package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.util.ArrayList;

import com.starshipsim.entities.Player;
import com.starshipsim.enums.Quality;
import com.starshipsim.files.FileIO;
import com.starshipsim.graphics.TiledBackground;
import com.starshipsim.items.Item;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.panels.StoreMenuUI;
import com.starshipsim.shipmodules.PowerModule;
import com.starshipsim.shipmodules.PropulsionModule;
import com.starshipsim.shipmodules.ShieldModule;
import com.starshipsim.shipmodules.ShipModule;
import com.starshipsim.shipmodules.WarpCoreModule;
import com.starshipsim.shipmodules.WeaponModule;

public class StoreState extends State {
	private static Image storeLeft = FileIO.loadImage("resources/storeLeft.png");
	private static Image storeRight = FileIO.loadImage("resources/storeRight.png");
	private static Image shipCursor = FileIO.loadImage("resources/smallship1.png");
	
	private int currentOption = 0;
	private Player player;
	private ArrayList<Item> inventory;
	private boolean isBuying;
	private final int consumableItems = 6;
	private int indexMod = 0;
	
	private TiledBackground bg = new TiledBackground(FileIO.loadImage("resources/spaceBackground.png"), 0, 0);
	
	private String[] itemList;
	private String[] priceList;

	private StoreMenuUI menu;
	
	public StoreState(StateManager manager, Player p, boolean isBuying, int indexMod) {
		super(manager);
		this.isBuying = isBuying;
		inventory = p.getInventory();
		
		player = p;
		this.indexMod = indexMod;
		int counter = 0;
		
		if (isBuying) {
			itemList = new String[5];
			priceList = new String[5];
			int newPowerQual = p.getShip().getData().getPower().getQuality().ordinal() + 1;
			int newShieldQual = p.getShip().getData().getShield().getQuality().ordinal() + 1;
			int newPropQual = p.getShip().getData().getWeapon().getQuality().ordinal() + 1;
			int newWeaponQual = p.getShip().getData().getWeapon().getQuality().ordinal() + 1;
			int newWarpQual = p.getShip().getData().getWarp().getQuality().ordinal() + 1;
			
			itemList[0] = Quality.values()[newPowerQual].toString().replace("_", " ") + " Power";
			itemList[1] = Quality.values()[newShieldQual].toString().replace("_", " ") + " Shields";
			itemList[2] = Quality.values()[newPropQual].toString().replace("_", " ") + " Propulsion";
			itemList[3] = Quality.values()[newWeaponQual].toString().replace("_", " ") + " Weapons";
			itemList[4] = Quality.values()[newWarpQual].toString().replace("_", " ") + " Warp Core";
			
			priceList[0] = " $" + Integer.toString(new PowerModule(Quality.values()[newPowerQual]).getPrice());
			priceList[1] = " $" + Integer.toString(new ShieldModule(Quality.values()[newShieldQual]).getPrice());
			priceList[2] = " $" + Integer.toString(new PropulsionModule(Quality.values()[newPropQual]).getPrice());
			priceList[3] = " $" + Integer.toString(new WeaponModule(Quality.values()[newWeaponQual], null).getPrice());
			priceList[4] = " $" + Integer.toString(new WarpCoreModule(Quality.values()[newWarpQual]).getPrice());
			
		} else {
			if (player.getInventory().size() > consumableItems) {
				itemList = new String[p.getInventory().size() - consumableItems];
				priceList = new String[p.getInventory().size() - consumableItems];
				for (int ii = indexMod; ii < p.getInventory().size(); ii++) {
					
					itemList[counter] = p.getInventory().get(ii).getName();
					priceList[counter] = " $";
					priceList[counter] += (int) (inventory.get(ii).getPrice() * .8);
					counter++;
				}
			}
		}
		initialize();
	}
	
	public StoreState(StateManager manager, Player p, boolean isBuying) {
		super(manager);
		this.isBuying = isBuying;
		inventory = p.getInventory();
		itemList = new String[consumableItems];
		priceList = new String[consumableItems];
		player=p;
		for (int ii = 0; ii < consumableItems; ii++) {
			itemList[ii] = inventory.get(ii).getName();
			priceList[ii] = " $";
			priceList[ii] += (isBuying) ? inventory.get(ii).getPrice() : (int) (inventory.get(ii).getPrice() * .8);
		}
		initialize();
	}
	

	@Override
	public void initialize() {
		menu = new StoreMenuUI(manager.getKeyboard(), storeLeft, shipCursor, itemList, priceList, player, isBuying, indexMod);
	}

	@Override
	public void update() {
		KeyboardListener keyboard = manager.getKeyboard();
				
		menu.update();
		this.currentOption = menu.getCurrentOption();
		
		if(keyboard.keyDownOnce(KeyEvent.VK_ESCAPE)) {
			for(int ii = 0; ii < player.getInventory().size(); ii++) {
				if (player.getInventory().get(ii) instanceof ShipModule && player.getInventory().get(ii).getAmount() == 0) {
					System.out.println("I ran too: " + ii);
					player.getInventory().remove(ii);
					ii--;
				}
			}
			manager.popState();
		}
		
		if(keyboard.keyDownOnce(KeyEvent.VK_ENTER)) {
			currentOption = menu.getCurrentOption();
			if (isBuying) {
				if (indexMod == 0) {
					//buying items
					if (player.getMoney() >= inventory.get(currentOption + indexMod).getPrice()) {
						player.setMoney(player.getMoney()-inventory.get(currentOption + indexMod).getPrice());
						inventory.get(currentOption + indexMod).setAmount(inventory.get(currentOption + indexMod).getAmount() + 1);
					}
				} else {
					//buying modules
					int cost = Integer.parseInt(priceList[currentOption].substring(2));
					if (player.getMoney() >= cost) {
						player.setMoney(player.getMoney() - cost);
						switch (currentOption) {
						case 0:
							player.getInventory().add(new PowerModule(Quality.values()[player.getShip().getData().getPower().getQuality().ordinal() + 1]));
							break;
						case 1:
							player.getInventory().add(new ShieldModule(Quality.values()[player.getShip().getData().getShield().getQuality().ordinal() + 1]));
							break;
						case 2:
							player.getInventory().add(new PropulsionModule(Quality.values()[player.getShip().getData().getPropulsion().getQuality().ordinal() + 1]));
							break;
						case 3:
							player.getInventory().add(new WeaponModule(Quality.values()[player.getShip().getData().getWeapon().getQuality().ordinal() + 1], null));
							break;
						case 4:
							player.getInventory().add(new WarpCoreModule(Quality.values()[player.getShip().getData().getWarp().getQuality().ordinal() + 1]));
							break;
						}
					}
				}
			} else {
				if (inventory.get(currentOption + indexMod).getAmount() > 0) {
					player.setMoney(player.getMoney()+ (int)(inventory.get(currentOption + indexMod).getPrice() * .8));
					inventory.get(currentOption + indexMod).setAmount(inventory.get(currentOption + indexMod).getAmount() - 1);
				}
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		Canvas canvas = this.getCanvas();
		
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
		if (indexMod == 0) {
			g.drawString("Currently Owned", 1275, 825);
			g.drawString(Integer.toString(inventory.get(currentOption + indexMod).getAmount()), 1550, 900);
		}
		g.drawString("Press Escape to return.", 32, 1050);
	}
	
	private void drawDescription(Graphics g, int containerX, int containerY) {
		String[] words = {};
		if (indexMod == 0 || !isBuying) {
			words = inventory.get(currentOption + indexMod).getDescription().split(" ");
		} else {
			switch (currentOption) {
			case 0: 
				words = player.getShip().getShipData().getPower().getDescription().split(" ");
				break;
			case 1:
				words = player.getShip().getShipData().getShield().getDescription().split(" ");
				break;
			case 2:
				words = player.getShip().getShipData().getPropulsion().getDescription().split(" ");
				break;
			case 3:
				words = player.getShip().getShipData().getWeapon().getDescription().split(" ");
				break;
			case 4:
				words = player.getShip().getShipData().getWarp().getDescription().split(" ");
				break;
			}
		}
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
