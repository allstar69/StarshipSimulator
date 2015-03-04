package com.starshipsim.panels;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;

import com.starshipsim.files.FileIO;
import com.starshipsim.shipmodules.WarpCore;
import com.starshipsim.states.MapState;
import com.starshipsim.world.Sector;

public class MapMenuPanel extends JPanel {

	private static final long serialVersionUID = 2585861607712456621L;

	private static Image smallMenu = FileIO.loadImage("resources/smallmenu.png");
	private static Image cursor = FileIO.loadImage("resources/cursor.png");

	private int level = 0;
	private int curY = 0;
	private int selX = 0;
	private int selY = 0;

	private MapState state;

	public MapMenuPanel(MapState state, int x, int y) {
		this.state = state;
		this.setLocation(x, y);
	}

	public void draw(Graphics2D g, Canvas canvas) {
		g.drawImage(smallMenu, this.getX(), this.getY(), canvas);
		
		String[] menu = new String[] {
				"Move the Ship",
				"Get Sector Data",
				"Open Science Station",
				"Set Map Standards",
				"Reset Map"
		};
		
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 24));
		int x = this.getX() + g.getFont().getSize();
		for (int i = 0; i < menu.length; i++) {
			int y = 100 + (i * 32);
			g.drawString(menu[i], x, y);
		}
		
		input();
	}

	private void input() {
		switch (level) {
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

		state.getGraphics2d().drawImage(state.getShip().getImage(), this.getX() - 40, this.getY() + 15 + (curY * 32), state.getCanvas());
	}

	private void mainMenu() {
		state.changeLog("Do the thing!");

		if (state.getKeyboard().keyDownOnce(KeyEvent.VK_W)) {
			if (curY == 0) {
				curY = 4;
			} else {
				curY -= 1;
			}
		} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_S)) {
			if (curY == 4) {
				curY = 0;
			} else {
				curY += 1;
			}
		}
		if (state.getKeyboard().keyDownOnce(KeyEvent.VK_ENTER)) {
			level = curY + 1;
			if (level == 3) {
				state.changeLog("For Science!");
			}

			selX = state.getShip().getSecX();
			selY = state.getShip().getSecY();
		}
	}

	private void moveShip() {
		state.changeLog("Coordinates: " + ((char) (selX + 97)) + (selY + 1));
		state.getGraphics2d().drawImage(cursor, 32 + (selX * 64), 32 + (selY * 64), state.getCanvas());
		
		if (state.getKeyboard().keyDownOnce(KeyEvent.VK_W)) {
			if (selY != 0
					&& selY != state.getShip().getSecY()
							- ((WarpCore) state.getShip().getWarp()).getMaxWarp()) {
				selY--;
			}
		} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_S)) {
			if (selY != 11
					&& selY != state.getShip().getSecY()
							+ ((WarpCore) state.getShip().getWarp()).getMaxWarp()) {
				selY++;
			}
		} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_A)) {
			if (selX != 0
					&& selX != state.getShip().getSecX()
							- ((WarpCore) state.getShip().getWarp()).getMaxWarp()) {
				selX--;
			}
		} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_D)) {
			if (selX != 11
					&& selX != state.getShip().getSecX()
							+ ((WarpCore) state.getShip().getWarp()).getMaxWarp()) {
				selX++;
			}
		}

		if (state.getKeyboard().keyDownOnce(KeyEvent.VK_ENTER)) {
			state.getShip().setSecX(selX);
			state.getShip().setSecY(selY);

			Sector sector = state.getGrid().getSector(selX, selY);

			sector.setKnown(true);
			if (sector.isHostile()) {
				state.changeLog("Enemies Detected");
				state.changeLog("Enemies Fled");
				sector.setHostile(false);
			}
			level = 0;
		} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_SHIFT)) {
			level = 0;
		}
	}

	private void getData() {
		state.changeLog("Coordinates: " + ((char) selX + 97) + (selY + 1));
		state.getGraphics2d().drawImage(cursor, 32 + (selX * 64), 32 + (selY * 64), state.getCanvas());
		
		if (state.getKeyboard().keyDownOnce(KeyEvent.VK_W)) {
			if (selY != 0) {
				selY--;
			}
		} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_S)) {
			if (selY != 11) {
				selY++;
			}
		} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_A)) {
			if (selX != 0) {
				selX--;
			}
		} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_D)) {
			if (selX != 11) {
				selX++;
			}
		}
		if (state.getKeyboard().keyDownOnce(KeyEvent.VK_ENTER)) {
			Sector sector = state.getGrid().getSector(selX, selY);

			if (sector.isKnown()) {
				if (sector.isMysterious()) {
					state.changeLog("That region is mysterious");
				} else {
					if (sector.isHostile()) {
						state.changeLog("That region is hostile");
					} else {
						state.changeLog("That region is neutral");
					}
					if (sector.getState() == 2) {
						state.setLog1(state.getLog1() + " and friendly");
					} else if (sector.getState() == 3) {
						state.setLog1(state.getLog1() + " and explorable");
					} else if (sector.getState() == 4) {
						state.setLog1(state.getLog1() + " and dangerous");
					}
				}
			} else {
				state.changeLog("That region is unknown.");
			}
		} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_SHIFT)) {
			level = 0;
		}
	}

	private void scienceStation() {
		state.getGraphics2d().drawImage(smallMenu, 860, 60, state.getCanvas());
		int i = 0;
		state.getGraphics2d().drawString(("Probe Count"), 890, (100 + (i * 32)));
		i++;
		state.getGraphics2d().drawString(("Launch Probe"), 890, (100 + (i * 32)));
		i++;
		state.getGraphics2d().drawString(("Explore Sector"), 890, (100 + (i * 32)));
		i++;
		state.getGraphics2d().drawString(("Replenish Probes"), 890, (100 + (i * 32)));
		i++;
		state.getGraphics2d().drawString(("Dump Probes"), 890, (100 + (i * 32)));
		if (state.getScienceLevel() == 0) {

			if (state.getKeyboard().keyDownOnce(KeyEvent.VK_W)) {
				if (curY == 0) {
					curY = 4;
				} else {
					curY -= 1;
				}
			} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_S)) {
				if (curY == 4) {
					curY = 0;
				} else {
					curY += 1;
				}
			}
			if (state.getKeyboard().keyDownOnce(KeyEvent.VK_ENTER)) {
				if (curY == 0) {
					state.changeLog("Current Probe Count: " + state.getProbeCount());
				}
				if (curY == 1) {
					state.setScienceLevel(curY + 1);
				}
				if (curY == 2) {
					state.setScienceLevel(curY + 1);
				}
				if (curY == 3) {
					refillProbes();
				}
				if (curY == 4) {
					dumpProbes();
				}
			} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_SHIFT)) {
				level = 0;
				curY = 0;
			}
		} else if (state.getScienceLevel() == 2) {
			launchProbe();
		} else if (state.getScienceLevel() == 3) {
			exploreSector();
		}
	}

	private void setStandards() {
		state.changeLog("How would you like to generate your universe?");
		// p1.setVisible(true);
		// f1.setVisible(true);
		// graphics2d.drawImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/mapscreen.png")),
		// 16, 16, canvas);
		state.getGraphics2d().drawImage(smallMenu, 860, 60, state.getCanvas());
		if (state.getKeyboard().keyDownOnce(KeyEvent.VK_ENTER)) {

		} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_SHIFT)) {
			level = 0;
		}
	}

	private void resetUniverse() {
		state.changeLog("Welcome to a new Universe!");
		Random rand = new Random();
		state.getShip().setSecY(rand.nextInt(12));
		state.getShip().setSecX(rand.nextInt(12));
		state.getGrid().reset();
		state.getGrid().setShipLocation(state.getShip(), state.getShip().getSecX(), state.getShip().getSecY());
		level = 0;
	}

	private void launchProbe() {
		state.getGraphics2d().drawImage(cursor, 32 + (selX * 64), 32 + (selY * 64),
				state.getCanvas());
		if (state.getKeyboard().keyDownOnce(KeyEvent.VK_W)) {
			state.changeLog("Coordinates: " + ((char) (selX + 97)) + (selY + 1));

			if (selY != 0) {
				selY--;
			}
		} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_S)) {
			state.changeLog("Coordinates: " + ((char) (selX + 97)) + (selY + 1));

			if (selY != 11) {
				selY++;
			}
		} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_A)) {
			state.changeLog("Coordinates: " + ((char) (selX + 97)) + (selY + 1));

			if (selX != 0) {
				selX--;
			}
		} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_D)) {
			state.changeLog("Coordinates: " + ((char) (selX + 97)) + (selY + 1));

			if (selX != 11) {
				selX++;
			}
		}
		if (state.getKeyboard().keyDownOnce(KeyEvent.VK_ENTER)) {
			Sector sector = state.getGrid().getSector(selX, selY);

			if (!sector.isKnown()) {
				if (state.getProbeCount() > 0) {
					sector.setKnown(true);
					if (sector.isMysterious()) {
						state.changeLog("That region is mysterious");
					} else {
						if (sector.isHostile()) {
							state.changeLog("That region is hostile");
						} else {
							state.changeLog("That region is neutral");
						}
						if (sector.getState() == 2) {
							state.setLog1(state.getLog1() + " and friendly");
						} else if (sector.getState() == 3) {
							state.setLog1(state.getLog1() + " and explorable");
						} else if (sector.getState() == 4) {
							state.setLog1(state.getLog1() + " and dangerous");
						}
					}
					state.setProbeCount(state.getProbeCount()-1);
					state.setScienceLevel(0);
				} else {
					state.changeLog("Sorry, you're out of probes.");
				}
			} else {
				state.changeLog("That sector is already known");
			}
		} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_SHIFT)) {
			state.setScienceLevel(0);
		}
	}

	private void exploreSector() {
		Sector sector = state.getGrid().getSector(state.getShip().getSecX(), state.getShip().getSecY());

		if (sector.getState() == 3) {
			Random rand = new Random();
			int r = rand.nextInt(2) + 1;
			if (r == 1) {
				state.changeLog("They found nothing!");
			} else if (r == 2) {
				state.changeLog("They found something!");
			}
		}
		state.setScienceLevel(0);
	}

	private void refillProbes() {
		state.setProbeCount(state.maxProbeCount);
		state.changeLog("Full Probes!");
	}

	private void dumpProbes() {
		state.setProbeCount(0);
		state.changeLog("Empty Probes!");
	}
}
