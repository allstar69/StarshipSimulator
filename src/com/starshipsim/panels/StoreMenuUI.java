package com.starshipsim.panels;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import com.starshipsim.entities.Player;
import com.starshipsim.listeners.KeyboardListener;

public class StoreMenuUI {

	private int x, y;
	private int indexMod = 0;
	private Image imgMenu;
	private Image imgCursor;
	
	private KeyboardListener keyboard;
	private String[] list;
	private String[] displayList;
	
	private Player p;
	private boolean buy;
	
	private int currentOption = 0;
	
	public int getCurrentOption() {
		return currentOption;
	}

	public void setCurrentOption(int currentOption) {
		this.currentOption = currentOption;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public StoreMenuUI(KeyboardListener keyboard, Image imgMenu, Image cursor, String[] list, String[] displayList, Player p, boolean buying, int indexMod) {
		this.keyboard = keyboard;
		this.imgMenu = imgMenu;
		this.imgCursor = cursor;
		this.x = 0;
		this.y = 0;
		this.p = p;
		this.buy = buying;
		this.list = list;
		this.displayList = displayList;
		this.indexMod = indexMod;
	}
	
	public void update() {
		if(keyboard.keyDownOnce(KeyEvent.VK_S)) {
			currentOption++;
		} else if(keyboard.keyDownOnce(KeyEvent.VK_W)) {
			currentOption--;
		}
		
		if(currentOption < 0) {
			currentOption = list.length-1;
		} else if(currentOption > list.length-1) {
			currentOption = 0;
		}
	}
	
	public void draw(Graphics g) {
		g.drawImage(imgMenu, x, y, null);
		
		FontMetrics metrics = g.getFontMetrics();
		
		int stringX = (120);
		int stringX2 = (950);
		for (int i = 0; i < list.length; i++) {
			if (buy) {
				if (Integer.parseInt(displayList[i].substring(2)) > p.getMoney()) { 
					g.setColor(Color.gray);
				}
				else {
					g.setColor(Color.decode("#3ACD48"));
				}
			} else {
				if (p.getInventory().get(i + indexMod).getAmount() > 0) {
					g.setColor(Color.decode("#0066FF"));
				} else {
					g.setColor(Color.gray);
				}
			}
			int stringY = y + metrics.getHeight() + (i * (metrics.getHeight()));
			g.drawString(list[i], stringX, stringY);
			g.drawString(displayList[i], stringX2, stringY);
		}
		
		g.setColor(Color.white);
		
		int cursorX = stringX - (imgCursor.getWidth(null)+5);
		int cursorY = y + (currentOption * (metrics.getHeight())) + metrics.getHeight()/3;
		g.drawImage(imgCursor, cursorX, cursorY, null);
		
	}
	
}
