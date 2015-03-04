package com.starshipsim.application;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Random;

import com.starshipsim.files.FileIO;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.objects.Ship;
import com.starshipsim.shipmodules.WarpCore;
import com.starshipsim.states.MapState;
import com.starshipsim.world.Grid;
import com.starshipsim.world.Sector;

public class Game {
	public final int maxProbeCount = 100;
	
	private boolean isRunning;
	
	private int level = 0;
	private int scienceLevel = 0;
	private int selX = 0;
	private int selY = 0;
	private int curY = 0;
	private int probeCount;

	private KeyboardListener keyboard;
	
	private MapState mapState;
	private Ship ship;
	private Grid grid;
	
	private static Image cursor = FileIO.loadImage("resources/cursor.png");
	private static Image smallMenu = FileIO.loadImage("resources/smallmenu.png");
	
	public KeyboardListener getKeyboard() {
		return keyboard;
	}

	public void setKeyboard(KeyboardListener key) {
		this.keyboard = key;
	}

	public Game() {
		isRunning = true;
		probeCount = 80;
		
		keyboard = new KeyboardListener();
		
		mapState = new MapState(keyboard);
		ship = new Ship(FileIO.loadImage("resources/smallship1.png"));
		grid = new Grid();
	}
	
	public void run() {
		initialize();
		
		while(isRunning) {
			update();
			draw();
		}
		
		end();
	}
	
	public void initialize() {
		grid.setShipLocation(ship, ship.getSecX(), ship.getSecX());
		mapState.setVisible(true);
	}
	
	//For later use
	public void update() {
	}
	
	public void draw() {
		mapState.paint(ship, grid.getSectors());
		input();
		mapState.setGraphics(mapState.getBuffer().getDrawGraphics());
		mapState.getGraphics().drawImage(mapState.getBi(), 0, 0, null);
		mapState.repaint();
	}
	
	//For later use
	public void end() {
	}
	
	//Methods
	private void input() {
		getKeyboard().poll();
		
		switch(level) {
		case 0:
			mainMenu();
			break;
		case 1:
			moveShip();
			break;
		case 2:
			getData();
			break;
		case 3:
			scienceStation();
			break;
		case 4:
			setStandards();
			break;
		case 5:
			resetUniverse();
			break;
		}
		
		mapState.getGraphics2d().drawImage(ship.getImage(), 820, 75 + (curY * 32),mapState.getCanvas());
	}

	private void mainMenu() {
		mapState.changeLog("Do the thing!");
		if (getKeyboard().keyDownOnce(KeyEvent.VK_UP)) {
			if (curY == 0) {
				curY = 4;
			} else {
				curY -= 1;
			}
		} else if (getKeyboard().keyDownOnce(KeyEvent.VK_DOWN)) {
			if (curY == 4) {
				curY = 0;
			} else {
				curY += 1;
			}
		}
		if (getKeyboard().keyDownOnce(KeyEvent.VK_Z)) {
			level = curY + 1;
			if (level == 3) {
				mapState.changeLog("For Science!");
			}
			
			selX = ship.getSecX();
			selY = ship.getSecY();
		}
	}

	private void moveShip() {
		mapState.changeLog("Coordinates: " + ((char) selX + 96) + (selY + 1));
		mapState.getGraphics2d().drawImage(cursor, 32 + (selX * 64), 32 + (selY * 64), mapState.getCanvas());
		
		if (getKeyboard().keyDownOnce(KeyEvent.VK_UP)) {
			if (selY != 0
					&& selY != ship.getSecY()
							- ((WarpCore) ship.getWarp()).getMaxWarp()) {
				selY--;
			}
		} else if (getKeyboard().keyDownOnce(KeyEvent.VK_DOWN)) {
			if (selY != 11
					&& selY != ship.getSecY()
							+ ((WarpCore) ship.getWarp()).getMaxWarp()) {
				selY++;
			}
		} else if (getKeyboard().keyDownOnce(KeyEvent.VK_LEFT)) {
			if (selX != 0
					&& selX != ship.getSecX()
							- ((WarpCore) ship.getWarp()).getMaxWarp()) {
				selX--;
			}
		} else if (getKeyboard().keyDownOnce(KeyEvent.VK_RIGHT)) {
			if (selX != 11
					&& selX != ship.getSecX()
							+ ((WarpCore) ship.getWarp()).getMaxWarp()) {
				selX++;
			}
		}
		
		if (getKeyboard().keyDownOnce(KeyEvent.VK_Z)) {
			ship.setSecX(selX);
			ship.setSecY(selY);
			
			Sector sector = grid.getSector(selX, selY);
			
			sector.setKnown(true);
			if (sector.isHostile()) {
				mapState.changeLog("Enemies Detected");
				mapState.changeLog("Enemies Fled");
				sector.setHostile(false);
			}
			level = 0;
		} else if (getKeyboard().keyDownOnce(KeyEvent.VK_X)) {
			level = 0;
		}
	}

	private void getData() {
		mapState.changeLog("Coordinates: " + ((char) selX + 96) + (selY + 1));
		mapState.getGraphics2d().drawImage(cursor, 32 + (selX * 64), 32 + (selY * 64), mapState.getCanvas());
		if (getKeyboard().keyDownOnce(KeyEvent.VK_UP)) {
			if (selY != 0) {
				selY--;
			}
		} else if (getKeyboard().keyDownOnce(KeyEvent.VK_DOWN)) {
			if (selY != 11) {
				selY++;
			}
		} else if (getKeyboard().keyDownOnce(KeyEvent.VK_LEFT)) {
			if (selX != 0) {
				selX--;
			}
		} else if (getKeyboard().keyDownOnce(KeyEvent.VK_RIGHT)) {
			if (selX != 11) {
				selX++;
			}
		}
		if (getKeyboard().keyDownOnce(KeyEvent.VK_Z)) {
			Sector sector = grid.getSector(selX, selY);
			
			if (sector.isKnown()) {
				if (sector.isMysterious()) {
					mapState.changeLog("That region is mysterious");
				} else {
					if (sector.isHostile()) {
						mapState.changeLog("That region is hostile");
					} else {
						mapState.changeLog("That region is neutral");
					}
					if (sector.getState() == 2) {
						mapState.setLog1(mapState.getLog1() + " and friendly");
					} else if (sector.getState() == 3) {
						mapState.setLog1(mapState.getLog1() + " and explorable");
					} else if (sector.getState() == 4) {
						mapState.setLog1(mapState.getLog1() + " and dangerous");
					}
				}
			} else {
				mapState.changeLog("That region is unknown.");
			}
		} else if (getKeyboard().keyDownOnce(KeyEvent.VK_X)) {
			level = 0;
		}
	}

	private void scienceStation() {
		mapState.getGraphics2d().drawImage(smallMenu, 860, 60, mapState.getCanvas());
		int i = 0;
		mapState.getGraphics2d().drawString(("Probe Count"), 890, (100 + (i * 32)));
		i++;
		mapState.getGraphics2d().drawString(("Launch Probe"), 890, (100 + (i * 32)));
		i++;
		mapState.getGraphics2d().drawString(("Explore Sector"), 890, (100 + (i * 32)));
		i++;
		mapState.getGraphics2d().drawString(("Replenish Probes"), 890,
				(100 + (i * 32)));
		i++;
		mapState.getGraphics2d().drawString(("Dump Probes"), 890, (100 + (i * 32)));
		if (scienceLevel == 0) {

			if (getKeyboard().keyDownOnce(KeyEvent.VK_UP)) {
				if (curY == 0) {
					curY = 4;
				} else {
					curY -= 1;
				}
			} else if (getKeyboard().keyDownOnce(KeyEvent.VK_DOWN)) {
				if (curY == 4) {
					curY = 0;
				} else {
					curY += 1;
				}
			}
			if (getKeyboard().keyDownOnce(KeyEvent.VK_Z)) {
				if (curY == 0) {
					mapState.changeLog("Current Probe Count: " + probeCount);
				}
				if (curY == 1) {
					scienceLevel = curY + 1;
				}
				if (curY == 2) {
					scienceLevel = curY + 1;
				}
				if (curY == 3) {
					refillProbes();
				}
				if (curY == 4) {
					dumpProbes();
				}
			} else if (getKeyboard().keyDownOnce(KeyEvent.VK_X)) {
				level = 0;
				curY = 0;
			}
		} else if (scienceLevel == 2) {
			launchProbe();
		} else if (scienceLevel == 3) {
			exploreSector();
		}
	}

	private void setStandards() {
		mapState.changeLog("How would you like to generate your universe?");
		// mapState.p1.setVisible(true);
		// mapState.f1.setVisible(true);
		// graphics2d.drawImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/mapscreen.png")),
		// 16, 16, canvas);
		mapState.getGraphics2d().drawImage(smallMenu, 860, 60, mapState.getCanvas());
		if (getKeyboard().keyDownOnce(KeyEvent.VK_Z)) {

		} else if (getKeyboard().keyDownOnce(KeyEvent.VK_X)) {
			level = 0;
		}
	}

	private void resetUniverse() {
		mapState.changeLog("Welcome to a new Universe!");
		Random rand = new Random();
		ship.setSecY(rand.nextInt(12));
		ship.setSecX(rand.nextInt(12));
		grid.reset();
		grid.setShipLocation(ship, ship.getSecX(), ship.getSecY());
		level = 0;
	}

	private void launchProbe() {
		mapState.getGraphics2d().drawImage(cursor, 32 + (selX * 64), 32 + (selY * 64), mapState.getCanvas());
		if (getKeyboard().keyDownOnce(KeyEvent.VK_UP)) {
			mapState.changeLog("Coordinates: " + ((char) selX + 96) + (selY + 1));

			if (selY != 0) {
				selY--;
			}
		} else if (getKeyboard().keyDownOnce(KeyEvent.VK_DOWN)) {
			mapState.changeLog("Coordinates: " + ((char) selX + 96) + (selY + 1));

			if (selY != 11) {
				selY++;
			}
		} else if (getKeyboard().keyDownOnce(KeyEvent.VK_LEFT)) {
			mapState.changeLog("Coordinates: " + ((char) selX + 96) + (selY + 1));

			if (selX != 0) {
				selX--;
			}
		} else if (getKeyboard().keyDownOnce(KeyEvent.VK_RIGHT)) {
			mapState.changeLog("Coordinates: " + ((char) selX + 96) + (selY + 1));

			if (selX != 11) {
				selX++;
			}
		}
		if (getKeyboard().keyDownOnce(KeyEvent.VK_Z)) {
			Sector sector = grid.getSector(selX, selY);
			
			if (!sector.isKnown()) {
				if (probeCount > 0) {
					sector.setKnown(true);
					if (sector.isMysterious()) {
						mapState.changeLog("That region is mysterious");
					} else {
						if (sector.isHostile()) {
							mapState.changeLog("That region is hostile");
						} else {
							mapState.changeLog("That region is neutral");
						}
						if (sector.getState() == 2) {
							mapState.setLog1(mapState.getLog1() + " and friendly");
						} else if (sector.getState() == 3) {
							mapState.setLog1(mapState.getLog1() + " and explorable");
						} else if (sector.getState() == 4) {
							mapState.setLog1(mapState.getLog1() + " and dangerous");
						}
					}
					probeCount -= 1;
					scienceLevel = 0;
				} else {
					mapState.changeLog("Sorry, you're out of probes.");
				}
			} else {
				mapState.changeLog("That sector is already known");
			}
		} else if (getKeyboard().keyDownOnce(KeyEvent.VK_X)) {
			scienceLevel = 0;
		}
	}

	private void exploreSector() {
		Sector sector = grid.getSector(ship.getSecX(), ship.getSecY());
		
		if (sector.getState() == 3) {
			Random rand = new Random();
			int r = rand.nextInt(2) + 1;
			if (r == 1) {
				mapState.changeLog("They found nothing!");
			} else if (r == 2) {
				mapState.changeLog("They found something!");
			}
		}
		scienceLevel = 0;
	}

	private void refillProbes() {
		probeCount = maxProbeCount;
		mapState.changeLog("Full Probes!");
	}

	private void dumpProbes() {
		probeCount = 0;
		mapState.changeLog("Empty Probes!");
	}
}
