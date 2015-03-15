package com.starshipsim.states;

import java.applet.AudioClip;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import com.starshipsim.combat.CombatData;
import com.starshipsim.combat.EnemyFleet;
import com.starshipsim.entities.Bullet;
import com.starshipsim.entities.EnemyShip;
import com.starshipsim.entities.Entity;
import com.starshipsim.entities.Player;
import com.starshipsim.entities.Ship;
import com.starshipsim.files.FileIO;
import com.starshipsim.graphics.ImageManager;
import com.starshipsim.graphics.StarBackgroundFx;
import com.starshipsim.interfaces.Enemy;
import com.starshipsim.items.Item;
import com.starshipsim.items.ItemExplosiveBomb;
import com.starshipsim.items.ItemRepairDrone;
import com.starshipsim.items.ItemScanner;
import com.starshipsim.items.ItemStunBomb;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.rewards.Reward;
import com.starshipsim.shipmodules.ShieldModule;
import com.sun.glass.events.KeyEvent;

public class CombatState extends State {
	private int cursorX=800;
	private int cursorY=760;
	private int curpos=1;
	private int selectedship=1;
	private int currentMenu=0;
	private int attacker=0;
	private int xshift;
	private int dxshift;
	private int currentItem=0;
	private boolean shieldsUp=false;
	private boolean hasBeenScanned=false;
	private int enemiesStunned=0;
	private long deltaTime=0;
	private AudioClip bgmusic;
	private AudioClip lasers;
	private AudioClip explosion;
	private AudioClip laser;
	private ArrayList<Bullet> bullets= new ArrayList<Bullet>();
	/*Menu Options:
	 * 0=main combat menu
	 * 1=weapon choice menu
	 * 2=enemy selection
	 * 3=item selection
	 * 4=win notification
	 * 5=enemy attack
	 * 6=notify
	 * 7=player attack
	 */
	
	
	private KeyboardListener keyboard;
	
	private CombatData data;
	private EnemyFleet enemies;
	private ArrayList<Enemy> ships;
	private Player player;
	private Ship ship;
	private int currentOption = 0;
	private ArrayList<Item> items=new ArrayList<>();
	private ArrayList<Entity> effects=new ArrayList<>();
	private StarBackgroundFx bg = new StarBackgroundFx(100, 1920, 1080);
	public CombatState(StateManager manager, CombatData data) {
		super(manager);
		this.keyboard = manager.getKeyboard();
		this.data = data;
		this.enemies = data.getEnemies();
		this.ships = enemies.getShips();
		player=data.getPlayer();
		ship=data.getPlayer().getShip();
		initialize();
		explosion=FileIO.loadSound("/sounds/Explosion.wav");
		lasers=FileIO.loadSound("/sounds/ShootMany.wav");
		laser=FileIO.loadSound("/sounds/Laser_Shoot.wav");
		bgmusic=FileIO.loadSound("/sounds/Corneria.wav");
		bgmusic.play();
	}

	@Override
	public void initialize() {
		for(Item i : player.getInventory()){
			if(i.getAmount()>0 &&  i.isBattleItem()){
				items.add(i);
			}
		}
		player.getShip().getData().getShield().setCurrentDurability(player.getShip().getData().getShield().getMaxDurability());
	}

	@Override
	public void update() {
		bg.update();
		if(xshift>dxshift){
			xshift-=15;
		}
		else if(xshift<dxshift){
			xshift+=15;
		}
		if(ship.getDurability()<=0) {
			bgmusic.stop();
			((SectorState)manager.getPreviousState()).getBgmusic().play();
			manager.popState();
		}
		if(currentMenu==0){
			mainCombatMenu();
		}
		else if(currentMenu==2){
			enemyChoiceMenu();
		}
		else if(currentMenu==3){
			itemMenu();
		}
		else if(currentMenu==4){
			win();
		}
		else if(currentMenu==5){
			enemyAttack();
		}
		else if(currentMenu==7){
			playerAttack();
		}
		for(int i=0;i<bullets.size();i++){
			if(bullets.get(i).getRot()==90){
				bullets.get(i).setImage(ImageManager.elaser);
			}
			else{
				bullets.get(i).setImage(ImageManager.laser);
			}
			bullets.get(i).update();
		}
	}

	@Override
	public void draw(Graphics g) {
		Canvas canvas = this.getCanvas();
		
		bg.draw(g);
		drawExit(g);
		for(Bullet b: bullets){
			if(b.getY()>470){
				b.draw(g, canvas);
			}
		}
		drawEnemyShips(g, canvas);
		drawHUD(g, canvas);
		drawBattleMenu(g, canvas);
		
	}

	@Override
	public void end() {
		
	}
	
	private void drawHUD(Graphics g, Canvas canvas) {
		
		g.drawImage(ImageManager.combatHud, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
	}
	
	private void drawExit(Graphics g) {
		g.setColor(Color.white);
		g.drawString("Press Escape to return the Map.", 32, 32);
	}
	
	private void drawBattleMenu(Graphics g, Canvas canvas) {
		Ship ship = this.player.getShip();
		ShieldModule shield = ship.getData().getShield();
		
		int centerX = canvas.getWidth()/2;
		int centerY = canvas.getHeight()/2;
		
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 24));
		
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 40));
		//menu.draw(g);
		if(ships.size()==0){
			currentMenu=4;
			//player.updateInventory(reward.getRewardItem().getIndex(), reward.getRewardItem());
		}
		if(currentMenu==0){
			g.drawString("Attack", centerX-100, centerY+250);
			if(!shieldsUp){
				g.drawString("Use Shield", centerX-650, centerY+360);
			}
			else{
				g.drawString("Remove Shield", centerX-650, centerY+360);
			}
			g.drawString("Items", centerX+600, centerY+360);
			g.drawString("Run", centerX-80, centerY+480);
		}
		else if(currentMenu==2){
			g.drawString("Which Enemy will you Attack?", centerX-360, centerY+360);
		}
		else if(currentMenu==3){
			
			
			String item1;
			if(currentItem==0){
				item1=items.get(items.size()-1).getName()+"   x   "+items.get(items.size()-1).getAmount();
			}
			else{
				item1 = items.get(currentItem-1).getName()+"   x   "+items.get(currentItem-1).getAmount();
			}
			String item2 = items.get(currentItem).getName()+"   x   "+items.get(currentItem).getAmount();
			String item3;
			if(currentItem==items.size()-1){
				item3=items.get(0).getName()+"   x   "+items.get(0).getAmount();
			}
			else{
				item3 = items.get(currentItem+1).getName()+"   x   "+items.get(currentItem+1).getAmount();
			}
			if(items.size()==1){
				item1="";
				item3="";
			}
			g.drawString(item1, centerX-100, centerY+280);
			g.drawString(item2, centerX-100, centerY+360);
			g.drawString(item3, centerX-100, centerY+440);
		}
		else if(currentMenu==4){
			g.drawString("You won $" + data.getEnemies().getReward(), centerX-100, centerY+360);
		}
		else if(currentMenu==5){
			if(this.enemiesStunned>0){
				g.drawString("Enemies are Stunned!", centerX-100, centerY+360);
			}
			else{
				g.drawString("Enemy " +(attacker+1)+ " is Attacking!", centerX-100, centerY+360);
			}
		}
		else if(currentMenu==7){
			g.drawString("Attacking Enemy "+ selectedship + "!", centerX-100, centerY+360);
		}
		g.drawImage(ImageManager.ship, cursorX, cursorY, null);
		g.drawString(("Health: "+ship.getDurability()+"/"+ship.getMaxDurability()), 200, 125);
		g.setColor(Color.green);
		g.fillRoundRect(1600, 96, shield.getCurrentDurability()*2, 50, 10, 10);
		g.setColor(Color.white);
		g.drawRoundRect(1600, 96, shield.getMaxDurability()*2, 50, 10, 10);
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
				if(hasBeenScanned){
					g.drawString(ship.getHealth()+"/"+ ship.getMaxHealth(), enemyX+(i*(450))+50, 350);
				}
				g.drawImage(ImageManager.combatEnemyShip, enemyX+(i*(450)), 400, 200, 130,null);
			}
			else{
				if(hasBeenScanned){
					g.drawString(ship.getHealth()+"/"+ ship.getMaxHealth(), enemyX+(i*(450))+50, 250);
				}
				g.drawImage(ImageManager.enemyStation, enemyX+(i*(450))-100, 300, 400, 360,null);
			}
		}
	}
	public void mainCombatMenu(){
		Ship ship = this.player.getShip();
		ShieldModule shield = ship.getData().getShield();
		
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
				laser.play();
				if(shieldsUp ){
					shieldsUp=false;
					currentMenu=5;
					lasers.loop();
					curpos=1;
				}
				else if(!shieldsUp && shield.getCurrentDurability()>(shield.getMaxDurability()/2)){
					shieldsUp=true;
					currentMenu=5;
					lasers.loop();
					curpos=1;
				}
				
			}
			else if(curpos==3){
				if(items.size()>0){
					currentMenu=3;
				}
			}
			else if(curpos==4){
				bgmusic.stop();
				((SectorState)manager.getPreviousState()).getBgmusic().play();
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
			
			ships.get(selectedship-1).takeDamage(player.getShip().getData().getWeapon().shootAll());
			if((ships.get(selectedship-1)).getHealth()<=0){
				ships.remove(selectedship-1);
				selectedship=1;
				dxshift=450*(ships.size()-selectedship)-(225*(ships.size()-1));
			}
			lasers.loop();
			currentMenu=7;
			curpos=1;
		}
		if(keyboard.keyDownOnce(KeyEvent.VK_SHIFT)){
			dxshift=0;
			currentMenu=0;
			curpos=1;
			cursorY=760;
			cursorX=800;
			attacker=0;
		}
	}
	public void win(){
		cursorY=870;
		cursorX=800;
		if(keyboard.keyDownOnce(KeyEvent.VK_ENTER)){
			bgmusic.stop();
			((SectorState)manager.getPreviousState()).getBgmusic().play();
			manager.popState();
			player.setMoney(player.getMoney()+data.getEnemies().getReward());
		}
	}
	public void playerAttack(){
		cursorY=870;
		cursorX=800;
		if(System.currentTimeMillis()>deltaTime+200 && dxshift==xshift){
			bullets.add(new Bullet(270, 840, 1000, 100, 100));
			deltaTime=System.currentTimeMillis();
		}
		if(keyboard.keyDownOnce(KeyEvent.VK_ENTER)){
			currentMenu=5;
		}
	}
	public void enemyAttack(){
		Ship ship = this.player.getShip();
		ShieldModule shield = ship.getData().getShield();
		
		if(enemiesStunned>0 && keyboard.keyDownOnce(KeyEvent.VK_ENTER)){
			enemiesStunned-=1;
			currentMenu=0;
			curpos=1;
			cursorY=760;
			cursorX=800;
			attacker=0;
			shield.setCurrentDurability(shield.getCurrentDurability()+10);
			if(shield.getCurrentDurability()>shield.getMaxDurability()){
				shield.setCurrentDurability(shield.getMaxDurability());
			}
			lasers.stop();
		}
		else if(enemiesStunned==0){
			if(System.currentTimeMillis()>deltaTime+200 && dxshift==xshift){
				bullets.add(new Bullet(90, 958, 470, 100, 100));
				deltaTime=System.currentTimeMillis();
			}
			
			cursorY=870;
			cursorX=800;
			dxshift=450*(ships.size()-(attacker+1))-(225*(ships.size()-1));
			if(keyboard.keyDownOnce(KeyEvent.VK_ENTER) && xshift==dxshift){
				if(attacker+1<ships.size()){
					
					attacker+=1;
					
				}
				else{
					dxshift=450*(ships.size()-selectedship)-(225*(ships.size()-1));
					lasers.stop();
					currentMenu=0;
					curpos=1;
					cursorY=760;
					cursorX=800;
					attacker=0;
					shield.setCurrentDurability(shield.getCurrentDurability()+10);
					if(shield.getCurrentDurability()>shield.getMaxDurability()){
						shield.setCurrentDurability(shield.getMaxDurability());
					}
				}
				if(shieldsUp){
					shield.setCurrentDurability(shield.getCurrentDurability()-ships.get(attacker).dealDamage()*2);
					if(shield.getCurrentDurability()<0){
						shield.setCurrentDurability(0);
						shieldsUp=false;
					}
				}
				else{
					ship.setDurability(ship.getDurability()-ships.get(attacker).dealDamage());
				}
			}
		}
	}
	public void itemMenu(){
		cursorY=870;
		cursorX=800;
		if(keyboard.keyDownOnce(KeyEvent.VK_W)){
			currentItem--;
			if(currentItem<0){
				currentItem=items.size()-1;
			}
		}
		else if(keyboard.keyDownOnce(KeyEvent.VK_S)){
			currentItem++;
			if(currentItem>items.size()-1){
				currentItem=0;
			}
		}
		if(keyboard.keyDownOnce(KeyEvent.VK_SHIFT)){
			dxshift=0;
			currentMenu=0;
			curpos=1;
			cursorY=760;
			cursorX=800;
			attacker=0;
		}
		if(keyboard.keyDownOnce(KeyEvent.VK_ENTER)){
			useItem();
		}
	}
	public void useItem(){
		if(items.get(currentItem) instanceof ItemRepairDrone){
			ship.setDurability(ship.getDurability()+100);
			if(ship.getDurability()>ship.getMaxDurability()){
				ship.setDurability(ship.getMaxDurability());
			}
			laser.play();
		}
		if(items.get(currentItem) instanceof ItemExplosiveBomb){
			explosion.play();
			for(int i=0;i<ships.size();i++){
				ships.get(i).takeDamage(100);
			}
			int i=0;
			while(i<ships.size()){
				if(ships.get(i).getHealth()<=0){
					ships.remove(i);
					i--;
				}
				i++;
			}
		}
		if(items.get(currentItem) instanceof ItemStunBomb){
			explosion.play();
			enemiesStunned=3;
		}
		if(items.get(currentItem) instanceof ItemScanner){
			this.hasBeenScanned=true;
			laser.play();
		}
		items.get(currentItem).setAmount(items.get(currentItem).getAmount()-1);
		if(items.get(currentItem).getAmount()==0){
			items.remove(currentItem);
			currentItem=0;
		}
		currentMenu=5;
		lasers.loop();
		curpos=1;
	}
}
