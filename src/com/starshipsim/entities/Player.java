package com.starshipsim.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import com.starshipsim.files.FileIO;
import com.starshipsim.items.Item;
import com.starshipsim.items.ItemFuel;
import com.starshipsim.items.ItemRepairDrone;
import com.starshipsim.items.ItemStunBomb;

public class Player extends Entity {

	private static Image imgShip = FileIO.loadImage("resources/smallship1.png");
	
	private Ship ship;
	
	private int money;
	
	private ArrayList<Item> inventory;
	
	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
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
		this.inventory.add(new ItemFuel(10));
		this.inventory.add(new ItemRepairDrone(0));
		this.inventory.add(new ItemStunBomb(3));
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
