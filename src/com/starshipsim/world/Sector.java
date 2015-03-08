package com.starshipsim.world;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import com.starshipsim.entities.Entity;
import com.starshipsim.enums.SectorStateType;
import com.starshipsim.files.FileIO;

public abstract class Sector {
	private boolean known = false;
	private boolean mysterious = false;
	private boolean hostile = false;
	SectorStateType state;
	
	private static Image imgUnknown = FileIO.loadImage("resources/unknown.png");
	private static Image imgMysterious = FileIO.loadImage("resources/Mysterious.png");
	private static Image imgHostile = FileIO.loadImage("resources/hostile.png");
	private static Image imgNeutral = FileIO.loadImage("resources/neutral.png");
	private static Image imgFriendly = FileIO.loadImage("resources/freindly.png");
	private static Image imgExplorable = FileIO.loadImage("resources/explorable.png");
	private static Image imgDangerous = FileIO.loadImage("resources/dangerous.png");
	private static Image imgEnemy = FileIO.loadImage("resources/enemy station.png");

	protected ArrayList<Entity> entities;

	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
	public boolean isKnown() {
		return known;
	}

	public void setKnown(boolean known) {
		this.known = known;
	}
	
	public boolean isMysterious() {
		return mysterious;
	}

	public void setMysterious(boolean mysterious) {
		this.mysterious = mysterious;
	}

	public boolean isHostile() {
		return hostile;
	}

	public void setHostile(boolean hostile) {
		this.hostile = hostile;
	}

	public SectorStateType getState() {
		return state;
	}
	
	public void setState(SectorStateType state) {
		this.state = state;
	}
	
	public Sector(SectorStateType state) {
		setState(state);
		entities = new ArrayList<Entity>();
	}
	
	public void update() {
		for (Entity entity : entities) {
			entity.update();
		}
	}
	
	public void draw(Graphics g, Canvas canvas) {
		for (Entity entity : entities) {
			entity.draw(g, canvas);
		}
	}
	
	public Entity getOneIntersectingEntity(Entity entity, Class<?> c) {
		for (Entity e : getEntities()) {
			if(e.getClass().equals(c)) {
				if(entity.isIntersecting(e)) {
					return e;
				}
			}
		}
		
		return null;
	}
	
	public boolean checkCollision(Entity entity, Class<?> c) {
		for (Entity e : getEntities()) {
			if(e.getClass().equals(c)) {
				if(entity.isIntersecting(e)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void paint(Graphics g, Canvas c, int x, int y) {
		if (!isKnown()) {
			g.drawImage(imgUnknown, x, y, c);
		} else {
			if (isMysterious()) {
				g.drawImage(imgMysterious, x, y, c);
			} else {
				if (isHostile()) {
					g.drawImage(imgHostile, x, y, c);
				} else {
					g.drawImage(imgNeutral, x, y, c);
				}
				
				switch(this.getState()) {
				case FRIENDLY:
					g.drawImage(imgFriendly, x, y, c);
					break;
				case EXPLORABLE:
					g.drawImage(imgExplorable, x, y, c);
					break;
				case DANGEROUS:
					g.drawImage(imgDangerous, x, y, c);
					break;
				case ENEMY:
					g.drawImage(imgEnemy, x, y, 64, 64, c);
					break;
				default:
					break;
				}
			}
		}
	}
}
