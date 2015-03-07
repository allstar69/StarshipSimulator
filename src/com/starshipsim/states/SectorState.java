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
import com.starshipsim.entities.Ship;
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
	
	private Sector sector;
	private Grid grid;
	public SectorState(StateManager manager, Ship ship, Grid grid) {
		super(manager);
		this.keyboard = manager.getKeyboard();
		this.ship = ship;
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
		if (keyboard.keyDown(KeyEvent.VK_ESCAPE)) {
			manager.popState();
		}

		ship.move(canvas);
	}

	public void shipCollisions() {
		for (Entity entity : sector.getEntities()) {
			if(ship.isIntersecting(entity)) {
				if (entity instanceof EnemySpaceStation) {
					ship.setX(ship.getX()+ship.getWidth()+10);
					ship.setY(ship.getY()+ship.getHeight()/2);
					manager.addState(new CombatState(manager, new CombatData(ship, new EnemyFleet())));
				}
			}
			
			if(entity.isIntersecting(ship) && entity instanceof Asteroid){
				ship.setDurability(ship.getDurability()-1);
				sector.getEntities().remove(entity);
				break;
			}
			
			if(ship.isIntersecting(entity) && entity instanceof Mine){
				
				ship.setDurability(ship.getDurability()-5);
				sector.getEntities().remove(entity);
				break;
			}
			
			if(ship.isIntersecting(entity) && entity instanceof BlackHole){
				ship.setSecX(new Random().nextInt(11));
				ship.setSecY(new Random().nextInt(11));
			}
		}
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
