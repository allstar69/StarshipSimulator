package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.starshipsim.graphics.ImageManager;
import com.starshipsim.graphics.TiledBackground;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.panels.MenuUI;

public class MainMenuState extends State {
	private TiledBackground bg = new TiledBackground(ImageManager.spaceBg, 0, 0);
	
	private int currentOption = 0;
	
	private String[] list = new String[] {
			"Play",
			"Load",
			"Exit",
	};
	
	private MenuUI menu;
	
	public MainMenuState(StateManager manager) {
		super(manager);
		initialize();
	}
	
	@Override
	public void initialize() {
		menu = new MenuUI(manager.getKeyboard(), ImageManager.largeMenu, ImageManager.ship, list);
	}

	@Override
	public void update() {
		KeyboardListener keyboard = manager.getKeyboard();
		
		menu.update();
		this.currentOption = menu.getCurrentOption();
		
		if(keyboard.keyDownOnce(KeyEvent.VK_ENTER)) {
			switch(currentOption) {
			case 0:
				manager.addState(new SectorState(manager));
				keyboard.flush();
				break;
			case 2:
				System.exit(0);
				break;
			}
		}
	}

	@Override
	public void draw(Graphics g, Canvas canvas) {
		bg.draw(g, canvas);
		
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 72));
		
		int menuX = (canvas.getWidth()/2) - (menu.getImgMenu().getWidth(null)/2);
		int menuY = (canvas.getHeight()/2) - (menu.getImgMenu().getHeight(null)/2);
		
		menu.setX(menuX);
		menu.setY(menuY);
		
		menu.draw(g);
	}

	@Override
	public void end() {
		
	}

}
