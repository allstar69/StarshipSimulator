package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import com.starshipsim.files.FileIO;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.objects.Ship;
import com.starshipsim.panels.GridPanel;
import com.starshipsim.panels.MapMenuPanel;
import com.starshipsim.world.Grid;

public class MapState extends State {
	public final int maxProbeCount = 100;
	
	private int scienceLevel = 0;
	private int probeCount;

	private String log1 = "Do the thing!";
	private String log2 = "";
	
	private KeyboardListener keyboard;
	
	private Grid grid;
	private Ship ship;

	private static Image space = FileIO.loadImage("resources/space.png");
	private static Image keyImg = FileIO.loadImage("resources/key.png");
	private static Image dialogueBox = FileIO.loadImage("resources/dialogueBox.png");

	private MapMenuPanel mapMenu;
	private GridPanel gridDisplay;

	public String getLog1() {
		return log1;
	}

	public void setLog1(String log1) {
		this.log1 = log1;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public int getProbeCount() {
		return probeCount;
	}

	public void setProbeCount(int probeCount) {
		this.probeCount = probeCount;
	}

	public KeyboardListener getKeyboard() {
		return keyboard;
	}

	public void setKeyboard(KeyboardListener keyboard) {
		this.keyboard = keyboard;
	}

	public int getScienceLevel() {
		return scienceLevel;
	}

	public void setScienceLevel(int scienceLevel) {
		this.scienceLevel = scienceLevel;
	}

	public MapState(StateManager states) {
		mapMenu = new MapMenuPanel(this, 860, 60);
		gridDisplay = new GridPanel(this, 0, 0);
		
		this.probeCount = 80;
		
		this.keyboard = states.getKeyboard();
		this.grid = new Grid();
		this.ship = new Ship(FileIO.loadImage("resources/smallship1.png"));
		grid.setShipLocation(ship, ship.getSecX(), ship.getSecY());
	}
	
	@Override
	public void initialize() {

	}
	
	@Override
	public void update() {
	}
	
	@Override
	public void draw(Graphics g, Canvas canvas) {
		g.drawImage(space, 0, 0, null);
		g.drawImage(keyImg, 860, 320, null);
		g.drawImage(dialogueBox, 16, 832, null);
		
		g.drawString(log1, 32, 872);
		g.setColor(Color.decode("#EEEEEE"));
		g.drawString(log2, 32, 908);
		g.setColor(Color.WHITE);

		gridDisplay.draw(g);
		mapMenu.draw(g);
	}

	public void end() {
	}

	public void changeLog(String log1) {
		if (!this.log1.equals(log1)) {
			this.log2 = this.log1;
			this.log1 = log1;
		}
	}
}
