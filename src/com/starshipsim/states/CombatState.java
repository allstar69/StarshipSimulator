package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import com.starshipsim.combat.CombatData;
import com.starshipsim.combat.EnemyFleet;
import com.starshipsim.entities.EnemyShip;
import com.starshipsim.files.FileIO;
import com.starshipsim.graphics.TiledBackground;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.panels.MenuUI;
import com.sun.glass.events.KeyEvent;

public class CombatState extends State {

	private static Image imgHud = FileIO.loadImage("resources/combat_hud.png");
	private static Image imgMenu = FileIO.loadImage("resources/smallmenu.png");
	private static Image shipCursor = FileIO.loadImage("resources/smallship1.png");
	
	private TiledBackground bg = new TiledBackground(FileIO.loadImage("resources/spaceBackground.png"));
	
	private KeyboardListener keyboard;
	
	private MenuUI menu;
	
	private String[] list;
	
	private CombatData data;
	EnemyFleet enemies;
	ArrayList<EnemyShip> ships;
	
	int currentOption = 0;
	
	public CombatState(StateManager manager, CombatData data) {
		super(manager);
		this.keyboard = manager.getKeyboard();
		this.data = data;
		this.enemies = data.getEnemies();
		this.ships = enemies.getShips();
		
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
		drawHUD(g, canvas);
		drawExit(g);
		drawBattleMenu(g, canvas);
		drawEnemyShips(g, canvas);
	}

	@Override
	public void end() {
		
	}
	
	private void drawHUD(Graphics g, Canvas canvas) {
		bg.draw(g, canvas);
		//g.drawImage(imgHud, 0, 0, null);
	}
	
	private void drawExit(Graphics g) {
		g.setColor(Color.white);
		g.drawString("Press Escape to return the Map.", 32, 32);
	}
	
	private void drawBattleMenu(Graphics g, Canvas canvas) {
		int centerX = canvas.getWidth()/2;
		int centerY = canvas.getHeight()/2;
		
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 24));
		
		int menuX = centerX - (imgMenu.getWidth(null)/2);
		int menuY = centerY;
		
		menu.setX(menuX);
		menu.setY(menuY);
		
		menu.draw(g);
	}
	
	private void drawEnemyShips(Graphics g, Canvas canvas) {
		int centerX = canvas.getWidth()/2;
		
		int totalWidth = 0;
		for (int i = 0; i < ships.size(); i++) {
			totalWidth += ships.get(i).getImage().getWidth(null);
		}
		
		for (int i = 0; i < ships.size(); i++) {
			EnemyShip ship = ships.get(i);
			int enemyX = centerX - totalWidth/2;
			g.drawImage(ship.getImage(), enemyX+(i*(ship.getImage().getWidth(null)+50)), 100, null);
		}
	}
	
	

}
