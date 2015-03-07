package com.starshipsim.panels;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Random;

import com.starshipsim.enums.SectorState;
import com.starshipsim.files.FileIO;
import com.starshipsim.listeners.MapListener;
import com.starshipsim.shipmodules.WarpCore;
import com.starshipsim.states.MapState;
import com.starshipsim.world.Sector;

public class MapMenuPanel {
	private static Image smallMenu = FileIO.loadImage("resources/smallmenu.png");
	private static Image cursor = FileIO.loadImage("resources/cursor.png");

	private int level = 0;
	private int curY = 0;
	private int selX = 0;
	private int selY = 0;
	
	private MapListener mp = new MapListener();

	int x, y;
	
	private MapState state;
	
	public MapListener getMapListener()
	{
		return mp;
	}

	public MapMenuPanel(MapState state, int x, int y) {
		this.x = x;
		this.y = y;
		this.state = state;
	}
	
	public void draw(Graphics g) {
		g.drawImage(smallMenu, this.x, this.y, null);
		
		String[] menu = new String[] {
				"Move the Ship",
				"Get Sector Data",
				"Open Science Station",
				"Set Map Standards",
				"Reset Map"
		};
		
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 24));
		int x = this.x + g.getFont().getSize();
		for (int i = 0; i < menu.length; i++) {
			int y = 100 + (i * 32);
			g.drawString(menu[i], x, y);
		}
		
		input(g);
	}

	private void input(Graphics g) {
		switch (level) {
		case 0:
			mainMenu();
			break;
		case 1:
			moveShip(g);
			break;
		case 2:
			getData(g);
			break;
		case 3:
			scienceStation(g);
			break;
		case 4:
			setStandards(g);
			break;
		case 5:
			resetUniverse();
			break;
		}

		g.drawImage(state.getShip().getImage(), this.x - 40, this.y + 15 + (curY * 32), null);
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

	private void moveShip(Graphics g) {
		state.changeLog("Coordinates: " + ((char) (selX + 97)) + (selY + 1));
		g.drawImage(cursor, 32 + (selX * 64), 32 + (selY * 64), null);
		
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

			if(!sector.isKnown())
			{
				sector.setKnown(true);
			}
			
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

	private void getData(Graphics g) {
		state.changeLog("Coordinates: " + ((char) selX + 97) + (selY + 1));
		g.drawImage(cursor, 32 + (selX * 64), 32 + (selY * 64), null);
		
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
					
					if (sector.getState() == SectorState.FRIENDLY) {
						state.setLog1("That region is friendly ");
					} else if (sector.getState() == SectorState.EXPLORABLE) {
						state.setLog1("That region is explorable ");
					} else if (sector.getState() == SectorState.DANGEROUS) {
						state.setLog1("That region is dangerous ");
					}else if (sector.getState() == SectorState.NEUTRAL){
						state.setLog1("That region is neutral ");
					}
					else if (sector.getState() == SectorState.ENEMY){
						state.setLog1("That region is an Enemy Base ");
					}
					if (sector.isHostile()) {
						state.setLog1(state.getLog1()  + "and hostile");
					} 
				}
			} else {
				state.changeLog("That region is unknown.");
			}
		} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_SHIFT)) {
			level = 0;
		}
	}

	private void scienceStation(Graphics g) {
		g.drawImage(smallMenu, 860, 60, null);
		int i = 0;
		g.drawString(("Probe Count"), 890, (100 + (i * 32)));
		i++;
		g.drawString(("Launch Probe"), 890, (100 + (i * 32)));
		i++;
		g.drawString(("Explore Sector"), 890, (100 + (i * 32)));
		i++;
		g.drawString(("Replenish Probes"), 890, (100 + (i * 32)));
		i++;
		g.drawString(("Dump Probes"), 890, (100 + (i * 32)));
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
			launchProbe(g);
		} else if (state.getScienceLevel() == 3) {
			exploreSector();
		}
	}

	private void setStandards(Graphics g) {
		state.changeLog("How would you like to generate your universe?");
		// p1.setVisible(true);
		// f1.setVisible(true);
		// graphics2d.drawImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/mapscreen.png")),
		// 16, 16, canvas);
		g.drawImage(smallMenu, 860, 60, null);
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

	private void launchProbe(Graphics g) {
		g.drawImage(cursor, 32 + (selX * 64), 32 + (selY * 64),
				null);
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
						if (sector.getState() == SectorState.FRIENDLY) {
							state.setLog1(state.getLog1() + " and friendly");
						} else if (sector.getState() == SectorState.EXPLORABLE) {
							state.setLog1(state.getLog1() + " and explorable");
						} else if (sector.getState() == SectorState.DANGEROUS) {
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

		if (sector.getState() == SectorState.EXPLORABLE) {
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
