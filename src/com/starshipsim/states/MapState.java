package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import com.starshipsim.combat.CombatData;
import com.starshipsim.combat.EnemyFleet;
import com.starshipsim.files.FileIO;
import com.starshipsim.graphics.TiledBackground;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.objects.Ship;
import com.starshipsim.panels.GridPanel;
import com.starshipsim.panels.MapMenuPanel;
import com.starshipsim.world.Grid;
import com.sun.glass.events.KeyEvent;

public class MapState extends State {
	public final int maxProbeCount = 100;
	
	private int scienceLevel = 0;
	private int probeCount;

	private String log1 = "Do the thing!";
	private String log2 = "";
	
	private KeyboardListener keyboard;
	
	private Grid grid;
	private Ship ship;

	private static Image imgShip = FileIO.loadImage("resources/smallship1.png");
	private static Image keyImg = FileIO.loadImage("resources/key.png");
	private static Image dialogueBox = FileIO.loadImage("resources/dialogueBox.png");
	
	private TiledBackground bg = new TiledBackground(FileIO.loadImage("resources/spaceBackground.png"));

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

	public MapState(StateManager manager) {
		super(manager);
		
		this.keyboard = manager.getKeyboard();
		keyboard.flush();
		
		initialize();
	}
	
	@Override
	public void initialize() {
		this.ship = new Ship(imgShip, this.keyboard);
		mapMenu = new MapMenuPanel(this, 860, 60);
		gridDisplay = new GridPanel(this, 0, 0);
		
		this.probeCount = 80;
		
		this.grid = new Grid();
		grid.setShipLocation(ship, ship.getSecX(), ship.getSecY());
	}
	
	@Override
	public void update() {
		//testing new state manager code
		if(keyboard.keyDownOnce(KeyEvent.VK_ESCAPE)) {
			manager.popState();
		}
		
		if(keyboard.keyDownOnce(KeyEvent.VK_E)) {
			EnemyFleet enemies = new EnemyFleet();
			CombatData combatData = new CombatData(ship, enemies);
			manager.addState(new CombatState(manager, combatData));
		}
		if(keyboard.keyDownOnce(KeyEvent.VK_Q)) {
			manager.addState(new SectorState(manager, this.ship));
		}
	}
	
	@Override
	public void draw(Graphics g, Canvas canvas) {
		bg.draw(g, canvas);
		g.drawImage(keyImg, 860, 320, null);
		g.drawImage(dialogueBox, 16, 832, null);
		
		g.drawString(log1, 32, 872);
		g.setColor(Color.decode("#EEEEEE"));
		g.drawString(log2, 32, 908);
		g.setColor(Color.WHITE);

		gridDisplay.draw(g);
		mapMenu.draw(g);
		
		//Temp
		g.drawString("Press Escape to return to the Main Menu.", 32, 950);
		g.drawString("Press E to enter Combat.", 32,  1000);
		g.drawString("Press Q to explore Sector", 32,  1050);
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
