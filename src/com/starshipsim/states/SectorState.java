package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import com.starshipsim.combat.CombatData;
import com.starshipsim.combat.EnemyFleet;
import com.starshipsim.entities.Asteroid;
import com.starshipsim.entities.BlackHole;
import com.starshipsim.entities.EnemySpaceStation;
import com.starshipsim.entities.Entity;
import com.starshipsim.entities.Mine;
import com.starshipsim.entities.Player;
import com.starshipsim.entities.Ship;
import com.starshipsim.entities.SpaceStation;
import com.starshipsim.files.FileIO;
import com.starshipsim.graphics.TiledBackground;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.world.Grid;
import com.starshipsim.world.Sector;
import com.sun.glass.events.KeyEvent;

public class SectorState extends State {
	private KeyboardListener keyboard;

	private Canvas canvas;
	
	private TiledBackground bg = new TiledBackground(FileIO.loadImage("resources/spaceBackground.png"), 0, 0);

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

		ship.move(canvas);
	}

	public void shipCollisions() {
		if(checkCollision(ship, SpaceStation.class)) {
			if(keyboard.keyDownOnce(KeyEvent.VK_ENTER)) {
				manager.addState(new StoreState(manager, player));
			}
		}
		
		if(checkCollision(ship, EnemySpaceStation.class)) {
			ship.setX(ship.getX()+ship.getWidth()+10);
			ship.setY(ship.getY()+ship.getHeight()/2);
			manager.addState(new CombatState(manager, new CombatData(ship, new EnemyFleet())));
		}
		
		if(checkCollision(ship, Asteroid.class)){
			ship.setDurability(ship.getDurability()-1);
		}
		
		if(checkCollision(ship, Mine.class)) {
			ship.setDurability(ship.getDurability()-5);
		}

		if(checkCollision(ship, BlackHole.class)) {
			ship.setSecX(new Random().nextInt(11));
			ship.setSecY(new Random().nextInt(11));
		}
		
		sector.getEntities().remove(this.getOneIntersectingEntity(ship, Asteroid.class));
		sector.getEntities().remove(this.getOneIntersectingEntity(ship, Mine.class));
	}
	
	public Entity getOneIntersectingEntity(Entity entity, Class<?> c) {
		for (Entity e : sector.getEntities()) {
			if(e.getClass().equals(c)) {
				if(entity.isIntersecting(e)) {
					return e;
				}
			}
		}
		
		return null;
	}
	
	public boolean checkCollision(Entity entity, Class<?> c) {
		for (Entity e : sector.getEntities()) {
			if(e.getClass().equals(c)) {
				if(entity.isIntersecting(e)) {
					return true;
				}
			}
		}
		
		return false;
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
	}

	@Override
	public void end() {

	}
	
}
