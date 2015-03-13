package com.starshipsim.panels;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;

import com.starshipsim.entities.Ship;
import com.starshipsim.enums.SectorStateType;
import com.starshipsim.graphics.ImageManager;
import com.starshipsim.listeners.MapListener;
import com.starshipsim.shipmodules.WarpCoreModule;
import com.starshipsim.states.MapState;
import com.starshipsim.world.Sector;

public class MapMenuPanel {
	private int level = 0;
	private int curY = 0;
	private int selX = 0;
	private int selY = 0;

	int x, y;
	
	private MapState state;

	public MapMenuPanel(MapState state, int x, int y) {
		this.x = x;
		this.y = y;
		this.state = state;
	}
	
	public void draw(Graphics g) {
		g.drawImage(ImageManager.smallMenu, this.x, this.y, null);
		
		String[] menu = new String[] {
				"Move the Ship",
				"Get Sector Data",
				"Open Science Station"
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
		}

		g.drawImage(state.getShip().getImage(), this.x - 40, this.y + 15 + (curY * 32), null);
	}

	private void mainMenu() {
		state.changeLog("Do the thing!");

		if (state.getKeyboard().keyDownOnce(KeyEvent.VK_W)) {
			if (curY == 0) {
				curY = 2;
			} else {
				curY -= 1;
			}
		} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_S)) {
			if (curY == 2) {
				curY = 0;
			} else {
				curY += 1;
			}
		}
		if (state.getKeyboard().keyDownOnce(KeyEvent.VK_ENTER)) {
			level = curY + 1;
			if (level == 3) {
				state.changeLog("For Science!");
				curY = 0;
			}

			selX = state.getShip().getSecX();
			selY = state.getShip().getSecY();
		}
	}

	private void moveShip(Graphics g) {
		Ship ship = state.getShip();
		WarpCoreModule warp = ship.getData().getWarp();
		
		state.changeLog("Coordinates: " + ((char) (selX + 97)) + (selY + 1));
		g.drawImage(ImageManager.cursor, 32 + (selX * 64), 32 + (selY * 64), null);
		
		if (state.getKeyboard().keyDownOnce(KeyEvent.VK_W)) {
			if (selY != 0
					&& selY != state.getShip().getSecY()
							- warp.MAX_WARP) {
				selY--;
			}
		} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_S)) {
			if (selY != 11
					&& selY != state.getShip().getSecY()
							+ warp.MAX_WARP) {
				selY++;
			}
		} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_A)) {
			if (selX != 0
					&& selX != state.getShip().getSecX()
							- warp.MAX_WARP) {
				selX--;
			}
		} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_D)) {
			if (selX != 11
					&& selX != state.getShip().getSecX()
							+ warp.MAX_WARP) {
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
			}
			level = 0;
		} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_SHIFT)) {
			level = 0;
		}
	}

	private void getData(Graphics g) {
		state.changeLog("Coordinates: " + ((char) selX + 97) + (selY + 1));
		g.drawImage(ImageManager.cursor, 32 + (selX * 64), 32 + (selY * 64), null);
		
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
					
					if (sector.getState() == SectorStateType.FRIENDLY) {
						state.setLog1("That region is friendly ");
					} else if (sector.getState() == SectorStateType.EXPLORABLE) {
						state.setLog1("That region is explorable ");
					} else if (sector.getState() == SectorStateType.DANGEROUS) {
						state.setLog1("That region is dangerous ");
					}else if (sector.getState() == SectorStateType.NEUTRAL){
						state.setLog1("That region is neutral ");
					}
					else if (sector.getState() == SectorStateType.ENEMY){
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
		g.drawImage(ImageManager.smallMenu, 860, 60, null);
		int i = 0;
		g.drawString(("Scanner Count"), 890, (100 + (i * 32)));
		i++;
		g.drawString(("Launch Scanner"), 890, (100 + (i * 32)));
		if (state.getScienceLevel() == 0) {

			if (state.getKeyboard().keyDownOnce(KeyEvent.VK_W)) {
				if (curY == 0) {
					curY = 1;
				} else {
					curY -= 1;
				}
			} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_S)) {
				if (curY == 1) {
					curY = 0;
				} else {
					curY += 1;
				}
			}
			if (state.getKeyboard().keyDownOnce(KeyEvent.VK_ENTER)) {
				if (curY == 0) {
					state.changeLog("Current Scanner Count: " + state.getPlayer().getInventory().get(5).getAmount());
				}
				if (curY == 1) {
					state.setScienceLevel(curY + 1);
				}
				if (curY == 2) {
					state.setScienceLevel(curY + 1);
				}
			} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_SHIFT)) {
				level = 0;
				curY = 0;
			}
		} else if (state.getScienceLevel() == 2) {
			launchProbe(g);
		}
	}

	private void launchProbe(Graphics g) {
		g.drawImage(ImageManager.cursor, 32 + (selX * 64), 32 + (selY * 64),
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
				if (state.getPlayer().getInventory().get(5).getAmount() > 0) {
					sector.setKnown(true);
					
					if (sector.isMysterious()) {
						state.changeLog("That region is mysterious");
					} else {
						if (sector.isHostile()) {
							state.changeLog("That region is hostile");
						} else {
							state.changeLog("That region is neutral");
						}
						if (sector.getState() == SectorStateType.FRIENDLY) {
							state.setLog1(state.getLog1() + " and friendly");
						} else if (sector.getState() == SectorStateType.EXPLORABLE) {
							state.setLog1(state.getLog1() + " and explorable");
						} else if (sector.getState() == SectorStateType.DANGEROUS) {
							state.setLog1(state.getLog1() + " and dangerous");
						}
					}
					state.getPlayer().getInventory().get(5).setAmount(state.getPlayer().getInventory().get(5).getAmount()-1);
					state.setScienceLevel(0);
				} else {
					state.changeLog("Sorry, you're out of scanners.");
				}
			} else {
				state.changeLog("That sector is already known");
			}
		} else if (state.getKeyboard().keyDownOnce(KeyEvent.VK_SHIFT)) {
			state.setScienceLevel(0);
		}
	}
}
