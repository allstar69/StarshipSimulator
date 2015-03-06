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
import com.starshipsim.entities.Ship;
import com.starshipsim.files.FileIO;
import com.starshipsim.graphics.TiledBackground;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.panels.MenuUI;
import com.sun.glass.events.KeyEvent;

public class CombatState extends State {

	private static Image imgHud = FileIO.loadImage("resources/combat_hud.png");
	private static Image imgMenu = FileIO.loadImage("resources/smallmenu.png");
	private static Image imgEnemy = FileIO.loadImage("resources/eship1.png");
	private static Image shipCursor = FileIO.loadImage("resources/smallship1.png");
	private static int cursorX=800;
	private static int cursorY=760;
	private static int curpos=1;
	
	private TiledBackground bg = new TiledBackground(FileIO.loadImage("resources/space.png"));
	
	private KeyboardListener keyboard;
	
	private MenuUI menu;
	
	private String[] list;
	
	private CombatData data;
	EnemyFleet enemies;
	ArrayList<EnemyShip> ships;
	Ship ship;
	int currentOption = 0;
	
	public CombatState(StateManager manager, CombatData data) {
		super(manager);
		this.keyboard = manager.getKeyboard();
		this.data = data;
		this.enemies = data.getEnemies();
		this.ships = enemies.getShips();
		ship=data.getPlayer();
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
		if(keyboard.keyDownOnce(KeyEvent.VK_W)){
			cursorX=800;
			cursorY=760;
			curpos=1;
		}
		else if(keyboard.keyDownOnce(KeyEvent.VK_S)){
			cursorX=800;
			cursorY=990;
			curpos=4;
		}
		else if(keyboard.keyDownOnce(KeyEvent.VK_A)){
			cursorX=250;
			cursorY=870;
			curpos=2;
		}
		else if(keyboard.keyDownOnce(KeyEvent.VK_D)){
			cursorX=1500;
			cursorY=870;
			curpos=3;
		}
		if(keyboard.keyDownOnce(KeyEvent.VK_ENTER)) {
			if(curpos==1){
				
			}
			else if(curpos==2){
							
			}
			else if(curpos==3){
				
			}
			else if(curpos==4){
				manager.popState();
			}
		}
		menu.update();
		currentOption = menu.getCurrentOption();
	}

	@Override
	public void draw(Graphics g, Canvas canvas) {
		bg.draw(g, canvas);
		drawExit(g);
		
		drawEnemyShips(g, canvas);
		drawHUD(g, canvas);
		drawBattleMenu(g, canvas);
	}

	@Override
	public void end() {
		
	}
	
	private void drawHUD(Graphics g, Canvas canvas) {
		
		g.drawImage(imgHud, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
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
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 40));
		//menu.draw(g);
		g.drawString("Attack", centerX-100, centerY+250);
		g.drawString("Defend", centerX-650, centerY+360);
		g.drawString("Items", centerX+600, centerY+360);
		g.drawString("Run", centerX-80, centerY+480);
		g.drawImage(shipCursor, cursorX, cursorY, null);
		g.drawString(("Health: "+ship.getDurability()+"/"+ship.getMaxDurability()), 200, 125);
	}
	
	private void drawEnemyShips(Graphics g, Canvas canvas) {
		int centerX = canvas.getWidth()/2;
		
		int totalWidth = 0;
		for (int i = 0; i < ships.size(); i++) {
			totalWidth += 450;
		}
		
		for (int i = 0; i < ships.size(); i++) {
			EnemyShip ship = ships.get(i);
			int enemyX = centerX - totalWidth/2;
			g.drawImage(imgEnemy, enemyX+(i*(450)), 200, 400, 260,null);
		}
	}
	
	

}
