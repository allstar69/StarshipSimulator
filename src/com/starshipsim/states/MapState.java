package com.starshipsim.states;

import java.awt.Color;
import java.awt.Graphics;

import com.starshipsim.combat.CombatData;
import com.starshipsim.combat.EnemyFleet;
import com.starshipsim.entities.Player;
import com.starshipsim.entities.Ship;
import com.starshipsim.graphics.ImageManager;
import com.starshipsim.graphics.StarBackgroundFx;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.panels.GridPanel;
import com.starshipsim.panels.MapMenuPanel;
import com.starshipsim.world.Grid;
import com.sun.glass.events.KeyEvent;

public class MapState extends State {
	public final int maxProbeCount = 100;
	
	private int scienceLevel = 0;
	private int probeCount;

	private String log1 = "";
	private String log2 = "";
	
	private KeyboardListener keyboard;
	
	private StarBackgroundFx bg = new StarBackgroundFx(100, manager.getCanvas().getWidth(), manager.getCanvas().getHeight());

	private MapMenuPanel mapMenu;
	private GridPanel gridDisplay;

	public String getLog1() {
		return log1;
	}

	public void setLog1(String log1) {
		this.log1 = log1;
	}

	public Player getPlayer() {
		return manager.getPlayer();
	}
	
	public Ship getShip() {
		return getPlayer().getShip();
	}

	public void setShip(Ship ship) {
		getPlayer().setShip(ship);
	}

	public Grid getGrid() {
		return manager.getGrid();
	}

	public void setGrid(Grid grid) {
		manager.setGrid(grid);
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
		mapMenu = new MapMenuPanel(this, 860, 60);
		gridDisplay = new GridPanel(this, 0, 0);
		
		this.probeCount = 80;
	}
	
	@Override
	public void update() {
		bg.update();
		
		if(keyboard.keyDownOnce(KeyEvent.VK_ESCAPE)) {
			manager.popState();
		}
		
		if(keyboard.keyDownOnce(KeyEvent.VK_E)) {
			EnemyFleet enemies = new EnemyFleet();
			CombatData combatData = new CombatData(getPlayer(), enemies);
			manager.addState(new CombatState(manager, combatData));
		}
		
		if(keyboard.keyDownOnce(KeyEvent.VK_R)) {
			manager.addState(new StoreSelectorState(manager, getPlayer()));
		}
		
		if(keyboard.keyDownOnce(KeyEvent.VK_F)) {
			manager.addState(new ShipBuilderState(manager, this.getPlayer()));
		}
	}
	
	@Override
	public void draw(Graphics g) {
		bg.draw(g);
		g.drawImage(ImageManager.key, 860, 320, null);
		g.drawImage(ImageManager.dialogueBox, 16, 832, null);
		
		g.drawString(log1, 32, 872);
		g.setColor(Color.decode("#EEEEEE"));
		g.drawString(log2, 32, 908);
		g.setColor(Color.WHITE);

		gridDisplay.draw(g);
		mapMenu.draw(g);
		getPlayer().getMapListener().draw(g, getGrid());
		getPlayer().getMoenyListener().draw(g, getPlayer().getMoney());
		getPlayer().getEnemyListener().draw(g, getGrid());
		
		//Temp
		g.drawString("Press Escape to exit the map.", 32, 950);
		g.drawString("Press E to enter Combat.", 32,  1000);
		g.drawString("Press R to view the store", 32,  1050);
		g.drawString("Press F to enter Ship Builder", 500, 950);
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
