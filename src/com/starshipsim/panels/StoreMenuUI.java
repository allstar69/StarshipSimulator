package com.starshipsim.panels;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import com.starshipsim.listeners.KeyboardListener;

public class StoreMenuUI {

	private int x, y;
	
	private Image imgMenu;
	private Image imgCursor;
	
	private KeyboardListener keyboard;
	private String[] list;
	private String[] displayList;
	
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

	public StoreMenuUI(KeyboardListener keyboard, Image imgMenu, Image cursor, String[] list, String[] displayList) {
		this.keyboard = keyboard;
		this.imgMenu = imgMenu;
		this.imgCursor = cursor;
		this.list = list;
		this.displayList = displayList;
		this.x = 0;
		this.y = 0;
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
		for (int i = 0; i < list.length; i++) {
			int stringY = y + metrics.getHeight() + (i * (metrics.getHeight()));
			g.drawString(list[i], stringX, stringY);
		}
		
		int cursorX = stringX - (imgCursor.getWidth(null)+5);
		int cursorY = y + (currentOption * (metrics.getHeight())) + metrics.getHeight()/3;
		g.drawImage(imgCursor, cursorX, cursorY, null);
		
		stringX = (950);
		for (int i = 0; i < displayList.length; i++) {
			int stringY = y + metrics.getHeight() + (i * (metrics.getHeight()));
			g.drawString(displayList[i], stringX, stringY);
		}
	}
	
}
