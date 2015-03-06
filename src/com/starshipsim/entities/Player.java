package com.starshipsim.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import com.starshipsim.files.FileIO;
import com.starshipsim.items.Item;

public class Player extends Entity {

	private static Image imgShip = FileIO.loadImage("resources/smallship1.png");
	
	private Ship ship;
	
	private ArrayList<Item> inventory;
	
	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public ArrayList<Item> getInventory() {
		return inventory;
	}

	public void setInventory(ArrayList<Item> inventory) {
		this.inventory = inventory;
	}

	public Player(int x, int y) {
		super(imgShip, x, y);
		this.ship = new Ship(800, 600, null);
		this.inventory = new ArrayList<>();
	}

	@Override
	public void initialize() {
		ship.initialize();
	}

	@Override
	public void update() {
		ship.update();
	}

	@Override
	public void draw(Graphics g, Canvas canvas) {
		ship.draw(g, canvas);
	}
	
	public void storeItem(Item item) {
		this.inventory.add(item);
	}
	
}
