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
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.starshipsim.files.FileIO;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.objects.Ship;
import com.starshipsim.panels.MapMenuPanel;
import com.starshipsim.world.Grid;
import com.starshipsim.world.Sector;

public class MapState extends JFrame {
	private static final long serialVersionUID = -3611025872685697162L;
	private final int WIDTH = 1920;
	private final int HEIGHT = 1000;
	public final int maxProbeCount = 100;
	
	private int scienceLevel = 0;
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
	private static Image keyImg = FileIO.loadImage("resources/key.png");
	private static Image dialogueBox = FileIO.loadImage("resources/dialogueBox.png");

	private MapMenuPanel mapMenu;

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
		
		mapMenu = new MapMenuPanel(this, 860, 60);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		GraphicsDevice gd = ge.getDefaultScreenDevice();

		GraphicsConfiguration gc = gd.getDefaultConfiguration();

		bi = gc.createCompatibleImage(WIDTH, HEIGHT);
		
		this.probeCount = 80;
		
		this.keyboard = keyboard;
		this.grid = new Grid();
		this.ship = new Ship(FileIO.loadImage("resources/smallship1.png"));
		grid.setShipLocation(ship, ship.getSecX(), ship.getSecY());
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
		
		graphics2d.drawImage(ship.getImage(), 48 + 64 * ship.getSecX(), 48 + 64 * ship.getSecY(), canvas);
		
		mapMenu.draw(graphics2d, canvas);
		
		setGraphics(getBuffer().getDrawGraphics());
		getGraphics().drawImage(getBi(), 0, 0, null);
		repaint();
	}

	public void end() {
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
