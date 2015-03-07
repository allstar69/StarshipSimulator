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
	private int cursorX=800;
	private int cursorY=760;
	private int curpos=1;
	private int currentMenu=0;
	/*Menu Options:
	 * 0=main combat menu
	 * 1=weapon choice menu
	 * 2=enemy selection
	 * 3=item selection
	 * 4=win notification
	 */
	private TiledBackground bg = new TiledBackground(FileIO.loadImage("resources/space.png"), 0, 0);
	
	private KeyboardListener keyboard;
	
	private String[] list;
	
	private CombatData data;
	EnemyFleet enemies;
	ArrayList<EnemyShip> ships;
	Ship ship;
	int currentOption = 0;
	
	public CombatState(StateManager manager, CombatData data) {
		super(manager);
		this.keyboard = StateManager.getKeyboard();
		this.data = data;
		this.enemies = data.getEnemies();
		this.ships = enemies.getShips();
		ship=data.getPlayer();
		initialize();
	}

	@Override
	public void initialize() {
	}

	@Override
	public void update() {
		if(keyboard.keyDownOnce(KeyEvent.VK_ESCAPE)) {
			StateManager.popState();
		}
		if(currentMenu==0){
			mainCombatMenu();
		}
		else if(currentMenu==2){
			enemyChoiceMenu();
		}
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
		
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 40));
		//menu.draw(g);
		if(ships.size()==0){
			manager.popState();
		}
		if(currentMenu==0){
			g.drawString("Attack", centerX-100, centerY+250);
			g.drawString("Defend", centerX-650, centerY+360);
			g.drawString("Items", centerX+600, centerY+360);
			g.drawString("Run", centerX-80, centerY+480);
		}
		else if(currentMenu==2){
			g.drawString("Which Enemy will you Attack?", centerX-360, centerY+360);
		}
		
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
			ship.setX(enemyX+(i*(450)));
			ship.setY(200);
			g.drawImage(imgEnemy, enemyX+(i*(450)), 200, 400, 260,null);
		}
	}
	public void mainCombatMenu(){
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
				currentMenu=2;
				cursorX=ships.get(0).getX()-32;
				cursorY=ships.get(0).getY()+130;
			}
			else if(curpos==2){
							
			}
			else if(curpos==3){
				
			}
			else if(curpos==4){
				manager.popState();
			}
		}
	}
	public void weaponChoiceMenu(){
			
		}
	public void enemyChoiceMenu(){
		if(curpos==1){
			cursorX=ships.get(0).getX()-32;
			cursorY=ships.get(0).getY()+130;
		}
		else if(curpos==2){
			cursorX=ships.get(1).getX()-32;
			cursorY=ships.get(1).getY()+130;
		}
		else if(curpos==3){
			cursorX=ships.get(2).getX()-32;
			cursorY=ships.get(2).getY()+130;
		}
		if(keyboard.keyDownOnce(KeyEvent.VK_A) && curpos>1){
			curpos--;
		}
		else if(keyboard.keyDownOnce(KeyEvent.VK_D) && curpos<ships.size()){
			curpos++;
		}
		else if(keyboard.keyDownOnce(KeyEvent.VK_ENTER)){
			currentMenu=0;
			ships.remove(curpos-1);
			curpos=1;
			cursorX=800;
			cursorY=760;
		}
	}

}
