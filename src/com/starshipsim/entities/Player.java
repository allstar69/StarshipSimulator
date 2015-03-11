package com.starshipsim.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.util.ArrayList;

import com.starshipsim.data.ShipData;
import com.starshipsim.graphics.ImageManager;
import com.starshipsim.items.Item;
import com.starshipsim.items.ItemExplosiveBomb;
import com.starshipsim.items.ItemFuel;
import com.starshipsim.items.ItemRepairDrone;
import com.starshipsim.items.ItemSatellite;
import com.starshipsim.items.ItemScanner;
import com.starshipsim.items.ItemStunBomb;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.listeners.MapListener;
import com.starshipsim.listeners.MoneyListener;

public class Player extends Entity {
	
	private Ship ship;
	
	private int money=1000;
	private MoneyListener ml = new MoneyListener();
	
	private ArrayList<Item> inventory;
	
	private MapListener mp = new MapListener();
	
	public MapListener getMapListener(){
		return mp;
	}
	
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
	
	public MoneyListener getMoenyListener(){
		return ml;
	}
	
	public void updateInventory(int newItemNum, Item newItem){
		int currentAmount = 0;
		
		 //0 = Fuel, 1 = Repair Drone, 2 = Stun Bomb, 3 = ExplosiveBomb, 4 = Satellite, 5 = Scanner
		
			currentAmount = inventory.get(newItemNum).getAmount();
			inventory.remove(newItemNum);
			currentAmount += newItem.getAmount();
			if(currentAmount < 0){
				currentAmount = 0;
			}
			switch(newItemNum){
			case 0:
				inventory.add(0, new ItemFuel(currentAmount));
				break;
			case 1:
				inventory.add(1, new ItemRepairDrone(currentAmount));
				break;
			case 2:
				inventory.add(2, new ItemStunBomb(currentAmount));
				break;
			case 3:
				inventory.add(3, new ItemExplosiveBomb(currentAmount));
				break;
			case 4:
				inventory.add(4, new ItemSatellite(currentAmount));
				break;
			case 5:
				inventory.add(5, new ItemScanner(currentAmount));
				break;			
			}
			
		
		
	}
	public Player(ShipData data, int x, int y, KeyboardListener keyboard) {
		super(ImageManager.ship, x, y);
		this.ship = new Ship(this, 800, 600, data, keyboard);
		this.inventory = new ArrayList<>();
		this.inventory.add(new ItemFuel(100));
		this.inventory.add(new ItemRepairDrone(0));
		this.inventory.add(new ItemStunBomb(0));
		this.inventory.add(new ItemExplosiveBomb(0));
		this.inventory.add(new ItemSatellite(0));
		this.inventory.add(new ItemScanner(0));
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
