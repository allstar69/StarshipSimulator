package com.starshipsim.states;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.starshipsim.files.FileIO;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.objects.Ship;
import com.starshipsim.shipmodules.WarpCore;
import com.starshipsim.world.Grid;
import com.starshipsim.world.Sector;

public class MapState extends JFrame {
	private static final long serialVersionUID = -3611025872685697162L;
	private final int WIDTH = 1200;
	private final int HEIGHT = 1000;
	public final int maxProbeCount = 100;
	
	private int level = 0;
	private int scienceLevel = 0;
	private int selX = 0;
	private int selY = 0;
	private int curY = 0;
	private int probeCount;
	
	private Canvas canvas;

	private Graphics2D graphics2d;
	private Graphics graphics;
	private BufferStrategy buffer;
	private BufferedImage bi;

	private String log1 = "Do the thing!";

	private String log2 = "";
	
	private KeyboardListener keyboard;
	public JTextField f1 = new JTextField();
	public JPanel p1 = new JPanel();
	
	private Grid grid;
	private Ship ship;

	private static Image space = FileIO.loadImage("resources/space.png");
	private static Image mapScreen = FileIO.loadImage("resources/mapscreen.png");
	private static Image smallMenu = FileIO.loadImage("resources/smallmenu.png");
	private static Image keyImg = FileIO.loadImage("resources/key.png");
	private static Image dialogueBox = FileIO.loadImage("resources/dialogueBox.png");
	private static Image cursor = FileIO.loadImage("resources/cursor.png");

	public String getLog1() {
		return log1;
	}

	public void setLog1(String log1) {
		this.log1 = log1;
	}

	public Graphics2D getGraphics2d() {
		return graphics2d;
	}

	public void setGraphics2d(Graphics2D graphics2d) {
		this.graphics2d = graphics2d;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public Graphics getGraphics() {
		return graphics;
	}

	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}

	public BufferStrategy getBuffer() {
		return buffer;
	}

	public void setBuffer(BufferStrategy buffer) {
		this.buffer = buffer;
	}

	public BufferedImage getBi() {
		return bi;
	}

	public void setBi(BufferedImage bi) {
		this.bi = bi;
	}

	public MapState(KeyboardListener keyboard) {
		setSize(WIDTH, HEIGHT);

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas = new Canvas();
		canvas.setIgnoreRepaint(true);
		canvas.setSize(WIDTH, HEIGHT);
		tBox();
		add(canvas);
		pack();
		addKeyListener(keyboard);
		setIgnoreRepaint(true);
		canvas.createBufferStrategy(2);

		buffer = canvas.getBufferStrategy();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		GraphicsDevice gd = ge.getDefaultScreenDevice();

		GraphicsConfiguration gc = gd.getDefaultConfiguration();

		bi = gc.createCompatibleImage(WIDTH, HEIGHT);
		
		this.probeCount = 80;
		
		this.keyboard = keyboard;
		this.grid = new Grid();
		this.ship = new Ship(FileIO.loadImage("resources/smallship1.png"));
	}
	
	public void initialize() {
		this.setVisible(true);
	}
	
	public void update() {
		
	}
	
	public void draw() {
		graphics2d = bi.createGraphics();
		graphics2d.setFont(new Font("Showcard Gothic", Font.ITALIC, 24));
		
		graphics2d.drawImage(space, 0, 0, canvas);
		graphics2d.drawImage(mapScreen, 0, 0, canvas);
		graphics2d.drawImage(smallMenu, 860, 60, canvas);
		graphics2d.drawImage(keyImg, 860, 320, canvas);
		graphics2d.drawImage(dialogueBox, 16, 832, canvas);
		
		graphics2d.drawString(log1, 32, 872);
		graphics2d.setColor(Color.decode("#EEEEEE"));
		graphics2d.drawString(log2, 32, 908);
		graphics2d.setColor(Color.WHITE);
		
		Sector[][] secs = grid.getSectors();
		for (int i = 0; i < secs.length; i++) {
			for (int j = 0; j < secs[i].length; j++) {
				secs[j][i].paint(graphics2d, canvas, 32 + j * 64, 32 + i * 64);
			}
		}
		
		graphics2d.setFont(new Font("Showcard Gothic", Font.ITALIC, 16));
		for (int i = 1; i < 13; i++) {
			graphics2d.drawString("" + i, 12, i * 64 + 4);
			graphics2d.drawString(((char) (i + 96)) + "", i * 64 - 4, 24);
		}
		
		String[] menu = new String[] {
				"Move the Ship",
				"Get Sector Data",
				"Open Science Station",
				"Set Map Standards",
				"Reset Map"
		};
		
		graphics2d.setFont(new Font("Showcard Gothic", Font.ITALIC, 24));
		for (int i = 0; i < menu.length; i++) {
			graphics2d.drawString(menu[i], 890, (100 + (i * 32)));
		}
		
		graphics2d.drawImage(ship.getImage(), 48 + 64 * ship.getSecX(), 48 + 64 * ship.getSecY(), canvas);
		
		input();
		
		setGraphics(getBuffer().getDrawGraphics());
		getGraphics().drawImage(getBi(), 0, 0, null);
		repaint();
	}

	public void end() {
	}
	
	private void input() {
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
		
		graphics2d.drawImage(ship.getImage(), 820, 75 + (curY * 32), getCanvas());
	}
	
	private void mainMenu() {
		changeLog("Do the thing!");
		
		if (keyboard.keyDownOnce(KeyEvent.VK_UP)) {
			if (curY == 0) {
				curY = 4;
			} else {
				curY -= 1;
			}
		} else if (keyboard.keyDownOnce(KeyEvent.VK_DOWN)) {
			if (curY == 4) {
				curY = 0;
			} else {
				curY += 1;
			}
		}
		if (keyboard.keyDownOnce(KeyEvent.VK_Z)) {
			level = curY + 1;
			if (level == 3) {
				changeLog("For Science!");
			}
			
			selX = ship.getSecX();
			selY = ship.getSecY();
		}
	}
	
	private void moveShip() {
		changeLog("Coordinates: " + ((char) selX + 96) + (selY + 1));
		getGraphics2d().drawImage(cursor, 32 + (selX * 64), 32 + (selY * 64), getCanvas());
		
		if (keyboard.keyDownOnce(KeyEvent.VK_UP)) {
			if (selY != 0
					&& selY != ship.getSecY()
							- ((WarpCore) ship.getWarp()).getMaxWarp()) {
				selY--;
			}
		} else if (keyboard.keyDownOnce(KeyEvent.VK_DOWN)) {
			if (selY != 11
					&& selY != ship.getSecY()
							+ ((WarpCore) ship.getWarp()).getMaxWarp()) {
				selY++;
			}
		} else if (keyboard.keyDownOnce(KeyEvent.VK_LEFT)) {
			if (selX != 0
					&& selX != ship.getSecX()
							- ((WarpCore) ship.getWarp()).getMaxWarp()) {
				selX--;
			}
		} else if (keyboard.keyDownOnce(KeyEvent.VK_RIGHT)) {
			if (selX != 11
					&& selX != ship.getSecX()
							+ ((WarpCore) ship.getWarp()).getMaxWarp()) {
				selX++;
			}
		}
		
		if (keyboard.keyDownOnce(KeyEvent.VK_Z)) {
			ship.setSecX(selX);
			ship.setSecY(selY);
			
			Sector sector = grid.getSector(selX, selY);
			
			sector.setKnown(true);
			if (sector.isHostile()) {
				changeLog("Enemies Detected");
				changeLog("Enemies Fled");
				sector.setHostile(false);
			}
			level = 0;
		} else if (keyboard.keyDownOnce(KeyEvent.VK_X)) {
			level = 0;
		}
	}

	private void getData() {
		changeLog("Coordinates: " + ((char) selX + 96) + (selY + 1));
		getGraphics2d().drawImage(cursor, 32 + (selX * 64), 32 + (selY * 64), getCanvas());
		if (keyboard.keyDownOnce(KeyEvent.VK_UP)) {
			if (selY != 0) {
				selY--;
			}
		} else if (keyboard.keyDownOnce(KeyEvent.VK_DOWN)) {
			if (selY != 11) {
				selY++;
			}
		} else if (keyboard.keyDownOnce(KeyEvent.VK_LEFT)) {
			if (selX != 0) {
				selX--;
			}
		} else if (keyboard.keyDownOnce(KeyEvent.VK_RIGHT)) {
			if (selX != 11) {
				selX++;
			}
		}
		if (keyboard.keyDownOnce(KeyEvent.VK_Z)) {
			Sector sector = grid.getSector(selX, selY);
			
			if (sector.isKnown()) {
				if (sector.isMysterious()) {
					changeLog("That region is mysterious");
				} else {
					if (sector.isHostile()) {
						changeLog("That region is hostile");
					} else {
						changeLog("That region is neutral");
					}
					if (sector.getState() == 2) {
						setLog1(getLog1() + " and friendly");
					} else if (sector.getState() == 3) {
						setLog1(getLog1() + " and explorable");
					} else if (sector.getState() == 4) {
						setLog1(getLog1() + " and dangerous");
					}
				}
			} else {
				changeLog("That region is unknown.");
			}
		} else if (keyboard.keyDownOnce(KeyEvent.VK_X)) {
			level = 0;
		}
	}

	private void scienceStation() {
		getGraphics2d().drawImage(smallMenu, 860, 60, getCanvas());
		int i = 0;
		getGraphics2d().drawString(("Probe Count"), 890, (100 + (i * 32)));
		i++;
		getGraphics2d().drawString(("Launch Probe"), 890, (100 + (i * 32)));
		i++;
		getGraphics2d().drawString(("Explore Sector"), 890, (100 + (i * 32)));
		i++;
		getGraphics2d().drawString(("Replenish Probes"), 890,
				(100 + (i * 32)));
		i++;
		getGraphics2d().drawString(("Dump Probes"), 890, (100 + (i * 32)));
		if (scienceLevel == 0) {

			if (keyboard.keyDownOnce(KeyEvent.VK_UP)) {
				if (curY == 0) {
					curY = 4;
				} else {
					curY -= 1;
				}
			} else if (keyboard.keyDownOnce(KeyEvent.VK_DOWN)) {
				if (curY == 4) {
					curY = 0;
				} else {
					curY += 1;
				}
			}
			if (keyboard.keyDownOnce(KeyEvent.VK_Z)) {
				if (curY == 0) {
					changeLog("Current Probe Count: " + probeCount);
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
			} else if (keyboard.keyDownOnce(KeyEvent.VK_X)) {
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
		changeLog("How would you like to generate your universe?");
		// p1.setVisible(true);
		// f1.setVisible(true);
		// graphics2d.drawImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/mapscreen.png")),
		// 16, 16, canvas);
		getGraphics2d().drawImage(smallMenu, 860, 60, getCanvas());
		if (keyboard.keyDownOnce(KeyEvent.VK_Z)) {

		} else if (keyboard.keyDownOnce(KeyEvent.VK_X)) {
			level = 0;
		}
	}

	private void resetUniverse() {
		changeLog("Welcome to a new Universe!");
		Random rand = new Random();
		ship.setSecY(rand.nextInt(12));
		ship.setSecX(rand.nextInt(12));
		grid.reset();
		grid.setShipLocation(ship, ship.getSecX(), ship.getSecY());
		level = 0;
	}

	private void launchProbe() {
		getGraphics2d().drawImage(cursor, 32 + (selX * 64), 32 + (selY * 64), getCanvas());
		if (keyboard.keyDownOnce(KeyEvent.VK_UP)) {
			changeLog("Coordinates: " + ((char) selX + 96) + (selY + 1));

			if (selY != 0) {
				selY--;
			}
		} else if (keyboard.keyDownOnce(KeyEvent.VK_DOWN)) {
			changeLog("Coordinates: " + ((char) selX + 96) + (selY + 1));

			if (selY != 11) {
				selY++;
			}
		} else if (keyboard.keyDownOnce(KeyEvent.VK_LEFT)) {
			changeLog("Coordinates: " + ((char) selX + 96) + (selY + 1));

			if (selX != 0) {
				selX--;
			}
		} else if (keyboard.keyDownOnce(KeyEvent.VK_RIGHT)) {
			changeLog("Coordinates: " + ((char) selX + 96) + (selY + 1));

			if (selX != 11) {
				selX++;
			}
		}
		if (keyboard.keyDownOnce(KeyEvent.VK_Z)) {
			Sector sector = grid.getSector(selX, selY);
			
			if (!sector.isKnown()) {
				if (probeCount > 0) {
					sector.setKnown(true);
					if (sector.isMysterious()) {
						changeLog("That region is mysterious");
					} else {
						if (sector.isHostile()) {
							changeLog("That region is hostile");
						} else {
							changeLog("That region is neutral");
						}
						if (sector.getState() == 2) {
							setLog1(getLog1() + " and friendly");
						} else if (sector.getState() == 3) {
							setLog1(getLog1() + " and explorable");
						} else if (sector.getState() == 4) {
							setLog1(getLog1() + " and dangerous");
						}
					}
					probeCount -= 1;
					scienceLevel = 0;
				} else {
					changeLog("Sorry, you're out of probes.");
				}
			} else {
				changeLog("That sector is already known");
			}
		} else if (keyboard.keyDownOnce(KeyEvent.VK_X)) {
			scienceLevel = 0;
		}
	}

	private void exploreSector() {
		Sector sector = grid.getSector(ship.getSecX(), ship.getSecY());
		
		if (sector.getState() == 3) {
			Random rand = new Random();
			int r = rand.nextInt(2) + 1;
			if (r == 1) {
				changeLog("They found nothing!");
			} else if (r == 2) {
				changeLog("They found something!");
			}
		}
		scienceLevel = 0;
	}

	private void refillProbes() {
		probeCount = maxProbeCount;
		changeLog("Full Probes!");
	}

	private void dumpProbes() {
		probeCount = 0;
		changeLog("Empty Probes!");
	}

	public void changeLog(String log1) {
		if (!this.log1.equals(log1)) {
			this.log2 = this.log1;
			this.log1 = log1;
		}
	}

	public void repaint() {
		buffer.show();
	}

	public void tBox() {
		p1.setSize(120, 30);
		add(p1, BorderLayout.CENTER);
		f1.addKeyListener(keyboard);
		f1.setVisible(false);
		p1.setVisible(false);
		p1.add(f1);
		p1.setLocation(890, 100);
		this.add(canvas);

	}
}
