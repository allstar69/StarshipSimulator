package application;

import java.awt.event.KeyEvent;
import java.util.Random;

import objects.Sector;
import objects.Ship;
import shipModules.WarpCore;

public class Engine {
	private GUI g = new GUI();
	private Ship ship = new Ship();
	private Sector[][] sectors = new Sector[12][12];
	private int level = 0;
	private int scienceLevel = 0;
	private int selX = 0;
	private int selY = 0;
	private int curY = 0;
	private int probeCount = 80;
	private int maxProbeCount = 100;

	public Engine() {
		run();
	}

	public void run() {
		ship.setImage(FileIO.loadImage("resources/smallship1.png"));
		for (int i = 0; i < sectors.length; i++) {
			for (int j = 0; j < sectors.length; j++) {
				sectors[j][i] = new Sector();
			}
		}

		sectors[ship.getSecX()][ship.getSecY()].setKnown(true);

		g.setVisible(true);
		while (true) {
			g.paint(ship, sectors);
			input();
			g.setGraphics(g.getBuffer().getDrawGraphics());
			g.getGraphics().drawImage(g.getBi(), 0, 0, null);
			g.repaint();
		}
	}

	private void input() {
		g.getKey().poll();
		if (level == -0) {
			mainMenu();
		} else if (level == 1) {
			moveShip();
		} else if (level == 2) {
			getData();
		} else if (level == 3) {

			scienceStation();
		} else if (level == 4) {
			setStandards();
		} else if (level == 5) {
			resetUniverse();
		}
		g.getGraphics2d().drawImage(ship.getImage(), 820, 75 + (curY * 32),
				g.getCanvas());
	}

	private void mainMenu() {
		// g.changeLog("Do the thing!");
		if (g.getKey().keyDownOnce(KeyEvent.VK_UP)) {
			if (curY == 0) {
				curY = 4;
			} else {
				curY -= 1;
			}
		} else if (g.getKey().keyDownOnce(KeyEvent.VK_DOWN)) {
			if (curY == 4) {
				curY = 0;
			} else {
				curY += 1;
			}
		}
		if (g.getKey().keyDownOnce(KeyEvent.VK_Z)) {
			level = curY + 1;
			if (level == 3) {
				g.changeLog("For Science!");
			}
			selX = ship.getSecX();
			selY = ship.getSecY();
		}
	}

	private void moveShip() {
		g.changeLog("Coordinates: " + ((char) selX + 96) + (selY + 1));
		g.getGraphics2d().drawImage(FileIO.loadImage("resources/cursor.png"),
				32 + (selX * 64), 32 + (selY * 64), g.getCanvas());
		if (g.getKey().keyDownOnce(KeyEvent.VK_UP)) {
			if (selY != 0
					&& selY != ship.getSecY()
							- ((WarpCore) ship.getWarp()).getMaxWarp()) {
				selY--;
			}
		} else if (g.getKey().keyDownOnce(KeyEvent.VK_DOWN)) {
			if (selY != 11
					&& selY != ship.getSecY()
							+ ((WarpCore) ship.getWarp()).getMaxWarp()) {
				selY++;
			}
		} else if (g.getKey().keyDownOnce(KeyEvent.VK_LEFT)) {
			if (selX != 0
					&& selX != ship.getSecX()
							- ((WarpCore) ship.getWarp()).getMaxWarp()) {
				selX--;
			}
		} else if (g.getKey().keyDownOnce(KeyEvent.VK_RIGHT)) {
			if (selX != 11
					&& selX != ship.getSecX()
							+ ((WarpCore) ship.getWarp()).getMaxWarp()) {
				selX++;
			}
		}
		if (g.getKey().keyDownOnce(KeyEvent.VK_Z)) {
			ship.setSecX(selX);
			ship.setSecY(selY);
			sectors[selX][selY].setKnown(true);
			if (sectors[selX][selY].isHostile()) {
				g.changeLog("Enemies Detected");
				g.changeLog("Enemies Fled");
				sectors[selX][selY].setHostile(false);
			}
			level = 0;
		} else if (g.getKey().keyDownOnce(KeyEvent.VK_X)) {
			level = 0;
		}
	}

	private void getData() {
		g.changeLog("Coordinates: " + ((char) selX + 96) + (selY + 1));
		g.getGraphics2d().drawImage(FileIO.loadImage("resources/cursor.png"),
				32 + (selX * 64), 32 + (selY * 64), g.getCanvas());
		if (g.getKey().keyDownOnce(KeyEvent.VK_UP)) {
			if (selY != 0) {
				selY--;
			}
		} else if (g.getKey().keyDownOnce(KeyEvent.VK_DOWN)) {
			if (selY != 11) {
				selY++;
			}
		} else if (g.getKey().keyDownOnce(KeyEvent.VK_LEFT)) {
			if (selX != 0) {
				selX--;
			}
		} else if (g.getKey().keyDownOnce(KeyEvent.VK_RIGHT)) {
			if (selX != 11) {
				selX++;
			}
		}
		if (g.getKey().keyDownOnce(KeyEvent.VK_Z)) {
			if (sectors[selX][selY].isKnown()) {
				if (sectors[selX][selY].isMysterious()) {
					g.changeLog("That region is mysterious");
				} else {
					if (sectors[selX][selY].isHostile()) {
						g.changeLog("That region is hostile");
					} else {
						g.changeLog("That region is neutral");
					}
					if (sectors[selX][selY].getState() == 2) {
						g.setLog1(g.getLog1() + " and friendly");
					} else if (sectors[selX][selY].getState() == 3) {
						g.setLog1(g.getLog1() + " and explorable");
					} else if (sectors[selX][selY].getState() == 4) {
						g.setLog1(g.getLog1() + " and dangerous");
					}
				}
			} else {
				g.changeLog("That region is unknown.");
			}
		} else if (g.getKey().keyDownOnce(KeyEvent.VK_X)) {
			level = 0;
		}
	}

	private void scienceStation() {
		g.getGraphics2d().drawImage(
				FileIO.loadImage("resources/smallmenu.png"), 860, 60,
				g.getCanvas());
		int i = 0;
		g.getGraphics2d().drawString(("Probe Count"), 890, (100 + (i * 32)));
		i++;
		g.getGraphics2d().drawString(("Launch Probe"), 890, (100 + (i * 32)));
		i++;
		g.getGraphics2d().drawString(("Explore Sector"), 890, (100 + (i * 32)));
		i++;
		g.getGraphics2d().drawString(("Replenish Probes"), 890,
				(100 + (i * 32)));
		i++;
		g.getGraphics2d().drawString(("Dump Probes"), 890, (100 + (i * 32)));
		if (scienceLevel == 0) {

			if (g.getKey().keyDownOnce(KeyEvent.VK_UP)) {
				if (curY == 0) {
					curY = 4;
				} else {
					curY -= 1;
				}
			} else if (g.getKey().keyDownOnce(KeyEvent.VK_DOWN)) {
				if (curY == 4) {
					curY = 0;
				} else {
					curY += 1;
				}
			}
			if (g.getKey().keyDownOnce(KeyEvent.VK_Z)) {
				if (curY == 0) {
					g.changeLog("Current Probe Count: " + probeCount);
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
			} else if (g.getKey().keyDownOnce(KeyEvent.VK_X)) {
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
		g.changeLog("How would you like to generate your universe?");
		// g.p1.setVisible(true);
		// g.f1.setVisible(true);
		// graphics2d.drawImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/mapscreen.png")),
		// 16, 16, canvas);
		g.getGraphics2d().drawImage(
				FileIO.loadImage("resources/smallmenu.png"), 860, 60,
				g.getCanvas());
		if (g.getKey().keyDownOnce(KeyEvent.VK_Z)) {

		} else if (g.getKey().keyDownOnce(KeyEvent.VK_X)) {
			level = 0;
		}
	}

	private void resetUniverse() {
		g.changeLog("Welcome to a new Universe!");
		Random rand = new Random();
		ship.setSecY(rand.nextInt(12));
		ship.setSecX(rand.nextInt(12));
		for (int i = 0; i < sectors.length; i++) {
			for (int j = 0; j < sectors.length; j++) {
				sectors[j][i] = new Sector();
			}
		}
		sectors[ship.getSecX()][ship.getSecY()].setKnown(true);
		level = 0;
	}

	private void launchProbe() {
		g.getGraphics2d().drawImage(FileIO.loadImage("resources/cursor.png"),
				32 + (selX * 64), 32 + (selY * 64), g.getCanvas());
		if (g.getKey().keyDownOnce(KeyEvent.VK_UP)) {
			g.changeLog("Coordinates: " + ((char) selX + 96) + (selY + 1));

			if (selY != 0) {
				selY--;
			}
		} else if (g.getKey().keyDownOnce(KeyEvent.VK_DOWN)) {
			g.changeLog("Coordinates: " + ((char) selX + 96) + (selY + 1));

			if (selY != 11) {
				selY++;
			}
		} else if (g.getKey().keyDownOnce(KeyEvent.VK_LEFT)) {
			g.changeLog("Coordinates: " + ((char) selX + 96) + (selY + 1));

			if (selX != 0) {
				selX--;
			}
		} else if (g.getKey().keyDownOnce(KeyEvent.VK_RIGHT)) {
			g.changeLog("Coordinates: " + ((char) selX + 96) + (selY + 1));

			if (selX != 11) {
				selX++;
			}
		}
		if (g.getKey().keyDownOnce(KeyEvent.VK_Z)) {
			if (!sectors[selX][selY].isKnown()) {
				if (probeCount > 0) {
					sectors[selX][selY].setKnown(true);
					if (sectors[selX][selY].isMysterious()) {
						g.changeLog("That region is mysterious");
					} else {
						if (sectors[selX][selY].isHostile()) {
							g.changeLog("That region is hostile");
						} else {
							g.changeLog("That region is neutral");
						}
						if (sectors[selX][selY].getState() == 2) {
							g.setLog1(g.getLog1() + " and friendly");
						} else if (sectors[selX][selY].getState() == 3) {
							g.setLog1(g.getLog1() + " and explorable");
						} else if (sectors[selX][selY].getState() == 4) {
							g.setLog1(g.getLog1() + " and dangerous");
						}
					}
					probeCount -= 1;
					scienceLevel = 0;
				} else {
					g.changeLog("Sorry, you're out of probes.");
				}
			} else {
				g.changeLog("That sector is already known");
			}
		} else if (g.getKey().keyDownOnce(KeyEvent.VK_X)) {
			scienceLevel = 0;
		}
	}

	private void exploreSector() {
		if (sectors[ship.getSecX()][ship.getSecY()].getState() == 3) {
			Random rand = new Random();
			int r = rand.nextInt(2) + 1;
			if (r == 1) {
				g.changeLog("They found nothing!");
			} else if (r == 2) {
				g.changeLog("They found something!");
			}
		}
		scienceLevel = 0;
	}

	private void refillProbes() {
		probeCount = maxProbeCount;
		g.changeLog("Full Probes!");
	}

	private void dumpProbes() {
		probeCount = 0;
		g.changeLog("Empty Probes!");
	}
}
