package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import com.starshipsim.combat.CombatData;
import com.starshipsim.combat.EnemyFleet;
import com.starshipsim.combat.StationFleet;
import com.starshipsim.data.ShipData;
import com.starshipsim.entities.Asteroid;
import com.starshipsim.entities.BlackHole;
import com.starshipsim.entities.Bullet;
import com.starshipsim.entities.EnemyShip;
import com.starshipsim.entities.EnemySpaceStation;
import com.starshipsim.entities.Entity;
import com.starshipsim.entities.Explosion;
import com.starshipsim.entities.Mine;
import com.starshipsim.entities.Planet;
import com.starshipsim.entities.Player;
import com.starshipsim.entities.Ship;
import com.starshipsim.entities.SpaceStation;
import com.starshipsim.enums.Quality;
import com.starshipsim.enums.SectorStateType;
import com.starshipsim.graphics.SpaceBackgroundFx;
import com.starshipsim.graphics.StarBackgroundFx;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.shipmodules.PowerModule;
import com.starshipsim.shipmodules.PropulsionModule;
import com.starshipsim.shipmodules.ShieldModule;
import com.starshipsim.shipmodules.WarpCoreModule;
import com.starshipsim.shipmodules.WeaponModule;
import com.starshipsim.weapons.Weapon;
import com.starshipsim.world.ExplorableSector;
import com.starshipsim.world.Grid;
import com.starshipsim.world.Sector;
import com.sun.glass.events.KeyEvent;

public class SectorState extends State {
	private KeyboardListener keyboard;
	
	private SpaceBackgroundFx bg = new SpaceBackgroundFx(100, 1920, 1080);

	int currentOption = 0;

	private Player player;
	private Ship ship;
	
	private Sector sector;
	private Grid grid;
	
	
	
	public SectorState(StateManager manager) {
		super(manager);
		this.keyboard = manager.getKeyboard();
		
		this.player = new Player(createShip(), 500, 500, this.keyboard);
		this.ship = player.getShip();
		
		this.grid = new Grid();
		grid.setShipLocation(player.getShip(), player.getShip().getSecX(), player.getShip().getSecY());
		
		manager.setPlayer(player);
		manager.setGrid(grid);
		
		sector = manager.getGrid().getSector(ship.getSecX(), ship.getSecY());
		initialize();
	}
	
	public ShipData createShip() {
		Weapon phaser = new Weapon("Phaser", Quality.LOW);
		ArrayList<Weapon> weapons = new ArrayList<>();
		weapons.add(phaser);
		weapons.add(phaser);
		weapons.add(phaser);
		weapons.add(phaser);
		
		PowerModule power = new PowerModule(Quality.LOW);
		ShieldModule shield = new ShieldModule(Quality.LOW);
		WeaponModule weapon = new WeaponModule(Quality.LOW, weapons);
		PropulsionModule propulsion = new PropulsionModule(Quality.LOW);
		WarpCoreModule warp = new WarpCoreModule(Quality.LOW);
		
		ShipData data = new ShipData(power, shield, weapon, propulsion, warp);
		
		return data;
	}
	
	@Override
	public void initialize() {
	}

	@Override
	public void update() {	
		bg.update(keyboard, ship.getSpeed());
		for(int i=0;i<12;i++){
			for(int j=0;j<12;j++){
				grid.getSector(j, i).update();
			}
		}
		sector = grid.getSector(ship.getSecX(), ship.getSecY());
		sector.setKnown(true);
		
		if(!ship.isDestroyed()){
			this.shipCollisions();
			if(ship.getDurability()<=0){
				sector.getEntities().add(new Explosion(ship.getX()-300, ship.getY()-300, ship.getWidth()+600, ship.getHeight()+600));
			}
		}
		this.bulletCollisions();
		this.enemyShipColisions();
		if(!sector.isKnown()){
			sector.setKnown(true);
		}
		if (keyboard.keyDownOnce(KeyEvent.VK_Q) && !ship.isDestroyed()) {
			manager.addState(new MapState(manager));
		}
		if(keyboard.keyDownOnce(KeyEvent.VK_ESCAPE)) {
			manager.popState();
		}		
		if(keyboard.keyDownOnce(KeyEvent.VK_SPACE) && !ship.isDestroyed()){
			sector.getEntities().add(ship.shootBullet());
		}
		ship.update();
		ship.move(this.getCanvas());
		addEnemyShip();
	}
	
	public void bulletCollisions(){
		if(sector.checkCollision(Bullet.class, Asteroid.class)){
			sector.getEntities().remove(sector.getOneIntersectingEntity(Bullet.class, Asteroid.class));
			player.setMoney(player.getMoney()+5);
		}
//		if(sector.checkCollision(Bullet.class, Mine.class)){
//			sector.getEntities().add(new Explosion(sector.getOneIntersectingEntity(Bullet.class, Mine.class).getX()-16, sector.getOneIntersectingEntity(Bullet.class, Mine.class).getY()-16, sector.getOneIntersectingEntity(Bullet.class, Mine.class).getWidth()*2, sector.getOneIntersectingEntity(Bullet.class, Mine.class).getHeight()*2));
//			sector.getEntities().remove(sector.getOneIntersectingEntity(Bullet.class, Mine.class));
//			player.setMoney(player.getMoney()+5);
//		}
		if(sector.checkCollision(Bullet.class, EnemyShip.class)){
			sector.getEntities().remove(sector.getOneIntersectingEntity(EnemyShip.class, Bullet.class));
			manager.addState(new CombatState(manager, new CombatData(player, new EnemyFleet()))); 
		}
	}
	
	public void enemyShipColisions(){
		if(sector.checkCollision(EnemyShip.class, Mine.class)){
			sector.getEntities().add(new Explosion(sector.getOneIntersectingEntity(EnemyShip.class, Mine.class).getX()-16, sector.getOneIntersectingEntity(EnemyShip.class, Mine.class).getY()-16, sector.getOneIntersectingEntity(EnemyShip.class, Mine.class).getWidth()*2, sector.getOneIntersectingEntity(EnemyShip.class, Mine.class).getHeight()*2));
			sector.getEntities().remove(sector.getOneIntersectingEntity(EnemyShip.class, Mine.class));
		}
		if(sector.checkCollision(EnemyShip.class, Asteroid.class)){
			sector.getEntities().remove(sector.getOneIntersectingEntity(EnemyShip.class, Asteroid.class));
		}
	}
	
	public void shipCollisions() {
		if(sector.checkCollision(ship, SpaceStation.class)) {
				if(keyboard.keyDownOnce(KeyEvent.VK_ENTER)) {
					if (!sector.isHostile()) {
						manager.addState(new StoreSelectorState(manager, player));
					} else {
						ArrayList<String> msg = new ArrayList<String>();
						msg.add("You must clear all hostiles form the sector before");
						msg.add("docking with the station!");
						manager.addState(new NotifyState(manager, msg));
					}
				}
		}
		
		if(sector.checkCollision(ship, EnemySpaceStation.class)) {
			if(keyboard.keyDownOnce(KeyEvent.VK_ENTER)) {
				ship.setX(ship.getX()+ship.getWidth()+10);
				ship.setY(ship.getY()+ship.getHeight()/2);
				sector.getEntities().remove(sector.getOneIntersectingEntity(ship, EnemySpaceStation.class));
				manager.addState(new CombatState(manager, new CombatData(player, new StationFleet()))); 
			}
		}
		if(sector.checkCollision(ship, EnemyShip.class)) {
				
				manager.addState(new CombatState(manager, new CombatData(player, new EnemyFleet())));
		}
		if(sector.checkCollision(ship, Asteroid.class)){
			ship.setDurability(ship.getDurability()-5);
		}
		
		if(sector.checkCollision(ship, Mine.class)) {
			ship.setDurability(ship.getDurability()-10);
			sector.getEntities().add(new Explosion(sector.getOneIntersectingEntity(ship, Mine.class).getX()-16, sector.getOneIntersectingEntity(ship, Mine.class).getY()-16, sector.getOneIntersectingEntity(ship, Mine.class).getWidth()*2, sector.getOneIntersectingEntity(ship, Mine.class).getHeight()*2));
		}

		if(sector.checkCollision(ship, BlackHole.class)) {
			ship.setSecX(new Random().nextInt(11));
			ship.setSecY(new Random().nextInt(11));
		}
		if(sector.checkCollision(ship, Planet.class)){
			if(sector.getState() == SectorStateType.EXPLORABLE || sector.getState() == SectorStateType.NEUTRAL){
				if(keyboard.keyDown(KeyEvent.VK_ENTER)){
					if (!sector.isHostile()) {	
						if(sector.isExplored()){
							ArrayList<String> tempMessage = new ArrayList<String>();
							tempMessage.add("You have already explored this planet!");
							manager.addState(new NotifyState(manager, tempMessage));	
						} else {
							manager.addState(new ExplorableState(manager, player, (ExplorableSector) sector));
							((ExplorableSector) sector).run(player);
							sector.setExplored(true);
						}
					} else {
						ArrayList<String> msg = new ArrayList<String>();
						msg.add("You must clear all hostiles form the sector before");
						msg.add("exploring the planet!");
						manager.addState(new NotifyState(manager, msg));
					}
				}
			}
		}
		
		sector.getEntities().remove(sector.getOneIntersectingEntity(ship, Asteroid.class));
		sector.getEntities().remove(sector.getOneIntersectingEntity(ship, Mine.class));
		sector.getEntities().remove(sector.getOneIntersectingEntity(ship, EnemyShip.class));
	}
	
	@Override
	public void draw(Graphics g) {	
		Canvas canvas = this.getCanvas();
		
		bg.draw(g);
		
		sector.draw(g, canvas);
		if(!ship.isDestroyed()){
			ship.draw(g, canvas);
		}
		else{
			g.setColor(Color.red);
			g.setFont(new Font("Showcard Gothic", Font.ITALIC, 80));
			g.drawString("Game Over", canvas.getWidth()/2-260, canvas.getHeight()/2+40);
		}
		g.setColor(Color.white);
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 24));
		g.drawString(ship.getDurability()+"/"+ship.getMaxDurability(), 32, 32);
		g.drawString(""+ship.getDistanceTravelled(), 32, 64);
		g.setColor(Color.green);
		g.fillRect(32, 96, player.getInventory().get(0).getAmount(), 32);
		g.setColor(Color.white);
		g.drawRect(32, 96, 100, 32);
	}

	@Override
	public void end() {

	}
	public boolean checkForEnemySectors(){
		boolean enemiesLeft = false;
		
		for(int i = 0; i < 12; i++){
			for(int j = 0; j < 12; j++){
				if(grid.getSector(i, j).getState() == SectorStateType.ENEMY){
					enemiesLeft = true;
				}
			}
		}
		return enemiesLeft;
	}
	public void addEnemyShip(){
		if(ship.getDistanceTravelled()>2000){
			if(checkForEnemySectors()){
				Random random = new Random();
				int randx=random.nextInt(12);
				int randy=random.nextInt(12);
				Sector s=grid.getSector(randx, randy);
				while(s.getState()!=SectorStateType.ENEMY){
					randx=random.nextInt(12);
					randy=random.nextInt(12);
					s=grid.getSector(randx, randy);
				}
				Entity station = s.getEntities().get(random.nextInt(s.getEntities().size()));
				s.getEntities().add(new EnemyShip(grid,station.getX()+station.getWidth()/2, station.getY()+station.getWidth()/2, randx, randy, keyboard));
				s.setHostile(true);
			}
			
			ship.setDistanceTravelled(0);
			
		}
	}
	public void moveSpace(){
		
	}
}
