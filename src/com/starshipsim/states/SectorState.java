package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import com.starshipsim.combat.CombatData;
import com.starshipsim.combat.EnemyFleet;
import com.starshipsim.combat.StationFleet;
import com.starshipsim.entities.Asteroid;
import com.starshipsim.entities.BlackHole;
import com.starshipsim.entities.EnemyShip;
import com.starshipsim.entities.EnemySpaceStation;
import com.starshipsim.entities.Entity;
import com.starshipsim.entities.Explosion;
import com.starshipsim.entities.Mine;
import com.starshipsim.entities.Player;
import com.starshipsim.entities.Ship;
import com.starshipsim.entities.SpaceStation;
import com.starshipsim.enums.SectorStateType;
import com.starshipsim.graphics.ImageManager;
import com.starshipsim.graphics.TiledBackground;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.world.Grid;
import com.starshipsim.world.Sector;
import com.sun.glass.events.KeyEvent;

public class SectorState extends State {
	private KeyboardListener keyboard;

	private Canvas canvas;
	
	private TiledBackground bg = new TiledBackground(ImageManager.spaceBg, 0, 0);

	int currentOption = 0;

	private Ship ship;
	private Player player;
	
	private Sector sector;
	private Grid grid;
	
	public SectorState(StateManager manager, Player player, Grid grid) {
		super(manager);
		this.keyboard = manager.getKeyboard();
		this.player = player;
		this.ship = player.getShip();
		this.grid=grid;
		sector = grid.getSector(ship.getSecX(), ship.getSecY());
		initialize();
	}
	
	@Override
	public void initialize() {
	}

	@Override
	public void update() {
		sector = grid.getSector(ship.getSecX(), ship.getSecY());
		sector.setKnown(true);
		sector.update();
		
		this.shipCollisions();
		
		if(!sector.isKnown()){
			sector.setKnown(true);
		}
		if (keyboard.keyDownOnce(KeyEvent.VK_ESCAPE)) {
			manager.popState();
		}
		
		ship.update();
		ship.move(canvas);
		addEnemyShip();
	}

	public void shipCollisions() {
		if(sector.checkCollision(ship, SpaceStation.class)) {
			if(keyboard.keyDownOnce(KeyEvent.VK_ENTER)) {
				manager.addState(new StoreSelectorState(manager, player));
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
			ship.setDurability(ship.getDurability()-1);
		}
		
		if(sector.checkCollision(ship, Mine.class)) {
			ship.setDurability(ship.getDurability()-5);
			sector.getEntities().add(new Explosion(sector.getOneIntersectingEntity(ship, Mine.class).getX()-16, sector.getOneIntersectingEntity(ship, Mine.class).getY()-16, sector.getOneIntersectingEntity(ship, Mine.class).getWidth()*2, sector.getOneIntersectingEntity(ship, Mine.class).getHeight()*2));
		}

		if(sector.checkCollision(ship, BlackHole.class)) {
			ship.setSecX(new Random().nextInt(11));
			ship.setSecY(new Random().nextInt(11));
		}
		
		sector.getEntities().remove(sector.getOneIntersectingEntity(ship, Asteroid.class));
		sector.getEntities().remove(sector.getOneIntersectingEntity(ship, Mine.class));
		sector.getEntities().remove(sector.getOneIntersectingEntity(ship, EnemyShip.class));
	}
	
	@Override
	public void draw(Graphics g, Canvas canvas) {
		this.canvas = canvas;
		
		bg.draw(g, canvas);
		
		g.setColor(Color.white);
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 24));
		
		sector.draw(g, canvas);
		
		ship.draw(g, canvas);
		g.drawString(ship.getDurability()+"/"+ship.getMaxDurability(), 32, 32);
		g.drawString(""+ship.getDistanceTravelled(), 32, 64);
	}

	@Override
	public void end() {

	}
	public void addEnemyShip(){
		if(ship.getDistanceTravelled()>1000){
			Random random = new Random();
			Sector sector = grid.getSector(random.nextInt(11), random.nextInt(11));
			while(sector.getState()!=SectorStateType.ENEMY){
				sector = grid.getSector(random.nextInt(11), random.nextInt(11));
			}
			Entity station = sector.getEntities().get(random.nextInt(sector.getEntities().size()));
			sector.getEntities().add(new EnemyShip(station.getX()+station.getWidth()/2, station.getY()+station.getWidth()/2, keyboard));
			ship.setDistanceTravelled(0);
			sector.setHostile(true);
		}
	}
}
