package com.starshipsim.world;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import com.starshipsim.entities.EnemyShip;
import com.starshipsim.entities.Entity;
import com.starshipsim.enums.SectorStateType;
import com.starshipsim.files.FileIO;
import com.starshipsim.graphics.ImageManager;

public abstract class Sector {
	private boolean known = true;
	private boolean mysterious = false;
	private boolean hostile = false;
	SectorStateType state;
	private boolean explored;

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
	
	public boolean isExplored() {
		return explored;
	}

	public void setExplored(boolean explored) {
		this.explored = explored;
	}

	public void update() {
		boolean isHostile=false;
		for (int i =0; i<entities.size();i++) {
			if(entities.get(i) instanceof EnemyShip){
				isHostile=true;
			}
			entities.get(i).update();
			
		}
		hostile=isHostile;
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
	
	public Entity getOneIntersectingEntity(Class<?> entity, Class<?> c) {
		for (Entity e : getEntities()) {
			for (Entity ent : getEntities()) {
				if( ent.getClass().equals(entity) &&e.getClass().equals(c)) {
					if(ent.isIntersecting(e)) {
						return e;
					}
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
	public boolean checkCollision(Class<?> entity, Class<?> c) {
		for (Entity e : getEntities()) {
			for (Entity ent : getEntities()) {
				if( ent.getClass().equals(entity) &&e.getClass().equals(c)) {
					if(ent.isIntersecting(e)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	public void paint(Graphics g, Canvas c, int x, int y) {
		if (!isKnown()) {
			g.drawImage(ImageManager.unknown, x, y, c);
		} else {
			if (isMysterious()) {
				g.drawImage(ImageManager.mysterious, x, y, c);
			} else {
				if (isHostile()) {
					g.drawImage(ImageManager.hostile, x, y, c);
				} else {
					g.drawImage(ImageManager.neutral, x, y, c);
				}
				
				switch(this.getState()) {
				case FRIENDLY:
					g.drawImage(ImageManager.friendly, x, y, c);
					break;
				case EXPLORABLE:
					g.drawImage(ImageManager.explorable, x, y, c);
					break;
				case DANGEROUS:
					g.drawImage(ImageManager.dangerous, x, y, c);
					break;
				case ENEMY:
					g.drawImage(ImageManager.enemy, x, y, 64, 64, c);
					break;
				default:
					break;
				}
			}
		}
	}
}
