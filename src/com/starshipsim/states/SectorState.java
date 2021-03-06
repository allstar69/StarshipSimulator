package com.starshipsim.states;

import java.applet.Applet;
import java.applet.AudioClip;
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
import com.starshipsim.files.FileIO;
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
	
	private SpaceBackgroundFx bg;
	private int currentOption = 0;

	private Player player;
	private Ship ship;
	
	private Sector sector;
	private Grid grid;
	private AudioClip laser;
	private AudioClip explosion;
	private AudioClip bgmusic;
	
	
	public SectorState(StateManager manager) {
		super(manager);
		this.keyboard = manager.getKeyboard();
		
		this.player = new Player(createShip(), 500, 500, this.keyboard);
		this.ship = player.getShip();
		bg = new SpaceBackgroundFx(100, 1920, 1080, ship);
		this.grid = new Grid();
		grid.setShipLocation(player.getShip(), player.getShip().getSecX(), player.getShip().getSecY());
		
		manager.setPlayer(player);
		manager.setGrid(grid);
		
		sector = manager.getGrid().getSector(ship.getSecX(), ship.getSecY());
		initialize();
		laser=FileIO.loadSound("/sounds/Laser_Shoot.wav");
		explosion=FileIO.loadSound("/sounds/Explosion.wav");
		bgmusic=FileIO.loadSound("/sounds/Pamgaea.wav");
		bgmusic.loop();
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
				for(int k=0;k<grid.getSector(j, i).getEntities().size();k++){
					if(grid.getSector(j, i).getEntities().get(k) instanceof EnemyShip && ((EnemyShip) grid.getSector(j, i).getEntities().get(k)).getFleet().getShips().size()==0){
						grid.getSector(j, i).getEntities().remove(k);
					}
				}
			}
		}
		
		
		
		sector = grid.getSector(ship.getSecX(), ship.getSecY());
		sector.setKnown(true);
		for(int i=0;i<sector.getEntities().size();i++){
			if(sector.getEntities().get(i) instanceof EnemyShip){
				EnemyShip e=((EnemyShip) sector.getEntities().get(i));
				double distance = Math.sqrt(Math.pow((ship.getX()-e.getX()), 2)+Math.pow((ship.getY()-e.getY()), 2));
				if(distance<400 && !ship.isDestroyed()){
					double xDis = ship.getX()-e.getX();
					double yDis = ship.getY()-e.getY();
					double neg=180;
					if(ship.getX()>=e.getX()){
						neg=0;
					}
					e.setRot(((Math.atan(yDis/xDis))*180/Math.PI)+neg);
				}
				for(int j=0;j<sector.getEntities().size();j++){
					if(sector.getEntities().get(j) instanceof BlackHole){
						if(e.getX()>sector.getEntities().get(j).getX()+sector.getEntities().get(j).getWidth()/2){
							e.setX(e.getX()-e.getSpeed()/2);
						}
						else if(e.getX()+e.getWidth()<sector.getEntities().get(j).getX()+sector.getEntities().get(j).getWidth()/2){
							e.setX(e.getX()+e.getSpeed()/2);
						}
						if(e.getY()>sector.getEntities().get(j).getY()+sector.getEntities().get(j).getHeight()/2){
							e.setY(e.getY()-e.getSpeed()/2);
						}
						else if(e.getY()+e.getHeight()<sector.getEntities().get(j).getY()+sector.getEntities().get(j).getHeight()/2){
							e.setY(e.getY()+e.getSpeed()/2);
						}
					}
				}
			}
			if(sector.getEntities().get(i) instanceof BlackHole){
				if(ship.getX()>sector.getEntities().get(i).getX()+sector.getEntities().get(i).getWidth()/2){
					ship.setX(ship.getX()-ship.getSpeed()/2);
				}
				else if(ship.getX()+ship.getWidth()<sector.getEntities().get(i).getX()+sector.getEntities().get(i).getWidth()/2){
					ship.setX(ship.getX()+ship.getSpeed()/2);
				}
				if(ship.getY()>sector.getEntities().get(i).getY()+sector.getEntities().get(i).getHeight()/2){
					ship.setY(ship.getY()-ship.getSpeed()/2);
				}
				else if(ship.getY()+ship.getHeight()<sector.getEntities().get(i).getY()+sector.getEntities().get(i).getHeight()/2){
					ship.setY(ship.getY()+ship.getSpeed()/2);
				}
			}
		}
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
		if (keyboard.keyDownOnce(KeyEvent.VK_ESCAPE) && ship.isDestroyed()) {
			manager.popState();
		}	
		if(keyboard.keyDownOnce(KeyEvent.VK_SPACE) && !ship.isDestroyed()){
			sector.getEntities().add(ship.shootBullet());
			laser.play();
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
				((EnemyShip)sector.getOneIntersectingEntity(Bullet.class, EnemyShip.class)).getFleet().damageFleet(5);
				bgmusic.stop();
				manager.addState(new CombatState(manager, new CombatData(player, ((EnemyShip)sector.getOneIntersectingEntity(Bullet.class, EnemyShip.class)).getFleet()))); 
				sector.getEntities().remove(sector.getOneIntersectingEntity(EnemyShip.class, Bullet.class));
		}
	}
	
	public void enemyShipColisions(){
		if(sector.checkCollision(EnemyShip.class, Mine.class)){
			((EnemyShip)sector.getOneIntersectingEntity(Mine.class, EnemyShip.class)).getFleet().damageFleet(20);
			sector.getEntities().add(new Explosion(sector.getOneIntersectingEntity(EnemyShip.class, Mine.class).getX()-16, sector.getOneIntersectingEntity(EnemyShip.class, Mine.class).getY()-16, sector.getOneIntersectingEntity(EnemyShip.class, Mine.class).getWidth()*2, sector.getOneIntersectingEntity(EnemyShip.class, Mine.class).getHeight()*2));
			sector.getEntities().remove(sector.getOneIntersectingEntity(EnemyShip.class, Mine.class));
		}
		if(sector.checkCollision(Asteroid.class, EnemyShip.class)){
			((EnemyShip)sector.getOneIntersectingEntity(Asteroid.class, EnemyShip.class)).getFleet().damageFleet(5);
			sector.getEntities().remove(sector.getOneIntersectingEntity(EnemyShip.class, Asteroid.class));
		}
		if(sector.checkCollision(BlackHole.class, EnemyShip.class)){
			Random rand= new Random();
			int newshipx=rand.nextInt(12);
			int newshipy=rand.nextInt(12);
			EnemyShip e=((EnemyShip)sector.getOneIntersectingEntity(BlackHole.class, EnemyShip.class));
			e.setSecX(newshipx);
			e.setSecY(newshipy);
			grid.getSector(newshipx, newshipy).getEntities().add(e);
			sector.getEntities().remove(e);
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
				bgmusic.stop();
				
				manager.addState(new CombatState(manager, new CombatData(player, new StationFleet()))); 
			}
		}
		if(sector.checkCollision(ship, EnemyShip.class)) {
				bgmusic.stop();
				manager.addState(new CombatState(manager, new CombatData(player, ((EnemyShip)sector.getOneIntersectingEntity(ship, EnemyShip.class)).getFleet())));
				((EnemyShip)sector.getOneIntersectingEntity(ship, EnemyShip.class)).setDrot(0);
				((EnemyShip)sector.getOneIntersectingEntity(ship, EnemyShip.class)).setRot(0f);
				ship.setY(((EnemyShip)sector.getOneIntersectingEntity(ship, EnemyShip.class)).getY());
				((EnemyShip)sector.getOneIntersectingEntity(ship, EnemyShip.class)).setX(ship.getX()-400);
				ship.setRot(180);
				ship.setDrot(0);
				
		}
		if(sector.checkCollision(ship, Asteroid.class)){
			ship.setDurability(ship.getDurability()-5);
		}
		
		if(sector.checkCollision(ship, Mine.class)) {
			ship.setDurability(ship.getDurability()-10);
			sector.getEntities().add(new Explosion(sector.getOneIntersectingEntity(ship, Mine.class).getX()-16, sector.getOneIntersectingEntity(ship, Mine.class).getY()-16, sector.getOneIntersectingEntity(ship, Mine.class).getWidth()*2, sector.getOneIntersectingEntity(ship, Mine.class).getHeight()*2));
			explosion.play();
		}

		if(sector.checkCollision(ship, BlackHole.class)) {
			ship.setSecX(new Random().nextInt(11));
			ship.setSecY(new Random().nextInt(11));
		}
		if(sector.checkCollision(ship, Planet.class)){
			if(sector.getState() == SectorStateType.EXPLORABLE || sector.getState() == SectorStateType.NEUTRAL){
				if(keyboard.keyDownOnce(KeyEvent.VK_ENTER)){
					if (!sector.isHostile()) {	
						if(sector.isExplored()){
							ArrayList<String> tempMessage = new ArrayList<String>();
							tempMessage.add("You have already explored this planet!");
							manager.addState(new NotifyState(manager, tempMessage));	
						} else {
							manager.addState(new ExplorableState(manager, player, (ExplorableSector) sector, sector.getOneIntersectingEntity(ship, Planet.class).getImage()));
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
		g.drawString("$"+player.getMoney(), 32, 64);
		g.setColor(Color.green);
		g.fillRect(32, 96, player.getInventory().get(0).getAmount(), 32);
		g.setColor(Color.white);
		g.drawRect(32, 96, 200, 32);
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

	public AudioClip getBgmusic() {
		return bgmusic;
	}

	public void setBgmusic(AudioClip bgmusic) {
		this.bgmusic = bgmusic;
	}
}
