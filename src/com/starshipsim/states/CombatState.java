package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import com.starshipsim.combat.CombatData;
import com.starshipsim.combat.EnemyFleet;
import com.starshipsim.entities.Enemy;
import com.starshipsim.entities.EnemyShip;
import com.starshipsim.entities.EnemySpaceStation;
import com.starshipsim.entities.Entity;
import com.starshipsim.entities.Player;
import com.starshipsim.entities.Ship;
import com.starshipsim.files.FileIO;
import com.starshipsim.graphics.TiledBackground;
import com.starshipsim.listeners.KeyboardListener;
import com.sun.glass.events.KeyEvent;

public class CombatState extends State {

	private static Image imgHud = FileIO.loadImage("resources/combat_hud.png");
	private static Image imgMenu = FileIO.loadImage("resources/smallmenu.png");
	private static Image imgEnemy = FileIO.loadImage("resources/eship1.png");
	private static Image imgEnemy2 = FileIO.loadImage("resources/enemy station.png");
	private static Image shipCursor = FileIO.loadImage("resources/smallship1.png");
	private int cursorX=800;
	private int cursorY=760;
	private int curpos=1;
	private int selectedship=1;
	private int currentMenu=0;
	private int attacker=0;
	private int xshift;
	private int dxshift;
	/*Menu Options:
	 * 0=main combat menu
	 * 1=weapon choice menu
	 * 2=enemy selection
	 * 3=item selection
	 * 4=win notification
	 * 5=enemy attack
	 */
	private Image bg =FileIO.loadImage("resources/space.png");
	
	private KeyboardListener keyboard;
	
	private String[] list;
	
	private CombatData data;
	EnemyFleet enemies;
	ArrayList<Enemy> ships;
	Player player;
	Ship ship;
	int currentOption = 0;
	
	public CombatState(StateManager manager, CombatData data) {
		super(manager);
		this.keyboard = manager.getKeyboard();
		this.data = data;
		this.enemies = data.getEnemies();
		this.ships = enemies.getShips();
		player=data.getPlayer();
		ship=data.getPlayer().getShip();
		initialize();
	}

	@Override
	public void initialize() {
	}

	@Override
	public void update() {
		if(xshift>dxshift){
			xshift-=15;
		}
		else if(xshift<dxshift){
			xshift+=15;
		}
		if(keyboard.keyDownOnce(KeyEvent.VK_ESCAPE)) {
			manager.popState();
		}
		if(currentMenu==0){
			mainCombatMenu();
		}
		else if(currentMenu==2){
			enemyChoiceMenu();
		}
		else if(currentMenu==4){
			win();
		}
		else if(currentMenu==5){
			enemyAttack();
		}
	}

	@Override
	public void draw(Graphics g, Canvas canvas) {
		g.drawImage(bg, xshift-450, 0, canvas);
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
		
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 40));
		//menu.draw(g);
		if(ships.size()==0){
			currentMenu=4;
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
		else if(currentMenu==4){
			g.drawString("You won $" + data.getEnemies().getReward(), centerX-100, centerY+360);
		}
		else if(currentMenu==5){
			g.drawString("Enemy " +(attacker+1)+ " is Attacking!", centerX-100, centerY+360);
		}
		g.drawImage(shipCursor, cursorX, cursorY, null);
		g.drawString(("Health: "+ship.getDurability()+"/"+ship.getMaxDurability()), 200, 125);
	}
	
	private void drawEnemyShips(Graphics g, Canvas canvas) {
		int centerX = canvas.getWidth()/2+xshift;
		
		int totalWidth = 0;
		for (int i = 0; i < ships.size(); i++) {
			totalWidth += 450;
		}
		
		for (int i = 0; i < ships.size(); i++) {
			Enemy ship = ships.get(i);
			int enemyX = centerX - totalWidth/2+100;
			ship.setX(enemyX+(i*(450)));
			ship.setY(200);
			if(ship instanceof EnemyShip){
				g.drawString(ship.getHealth()+"/"+ ship.getMaxHealth(), enemyX+(i*(450))+50, 350);
				g.drawImage(imgEnemy, enemyX+(i*(450)), 400, 200, 130,null);
			}
			else{
				g.drawString(ship.getHealth()+"/"+ ship.getMaxHealth(), enemyX+(i*(450))+50, 250);
				g.drawImage(imgEnemy2, enemyX+(i*(450))-100, 300, 400, 360,null);
			}
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
				cursorX=ships.get(selectedship-1).getX()-32;
				cursorY=ships.get(selectedship-1).getY()+130;
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
			cursorX=ships.get(selectedship-1).getX()-32;
			cursorY=ships.get(selectedship-1).getY()+130;
			dxshift=450*(ships.size()-selectedship)-(225*(ships.size()-1));
		
		if(keyboard.keyDownOnce(KeyEvent.VK_A) && selectedship>1){
			selectedship--;
		}
		else if(keyboard.keyDownOnce(KeyEvent.VK_D) && selectedship<ships.size()){
			selectedship++;
		}
		else if(keyboard.keyDownOnce(KeyEvent.VK_ENTER)){
			
			ships.get(selectedship-1).takeDamage(20);
			if((ships.get(selectedship-1)).getHealth()<=0){
				ships.remove(selectedship-1);
				selectedship=1;
				dxshift=450*(ships.size()-selectedship)-(225*(ships.size()-1));
			}
			currentMenu=5;
			curpos=1;
		}
	}
	public void win(){
		cursorY=870;
		cursorX=800;
		if(keyboard.keyDownOnce(KeyEvent.VK_ENTER)){
			
			manager.popState();
			player.setMoney(player.getMoney()+data.getEnemies().getReward());
		}
	}
	public void enemyAttack(){
		cursorY=870;
		cursorX=800;
		dxshift=450*(ships.size()-(attacker+1))-(225*(ships.size()-1));
		if(keyboard.keyDownOnce(KeyEvent.VK_ENTER)){
			if(attacker+1<ships.size()){
				
				attacker+=1;
				
			}
			else{
				dxshift=450*(ships.size()-selectedship)-(225*(ships.size()-1));
				currentMenu=0;
				curpos=1;
				cursorY=760;
				cursorX=800;
				attacker=0;
			}
			ship.setDurability(ship.getDurability()-ships.get(attacker).dealDamage());
		}
	}
}
