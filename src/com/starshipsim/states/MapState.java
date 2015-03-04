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
import com.starshipsim.objects.Sector;
import com.starshipsim.objects.Ship;

public class MapState extends JFrame {
	private static final long serialVersionUID = -3611025872685697162L;
	private final int WIDTH = 1200;
	private final int HEIGHT = 1000;
	
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

	private static Image space = FileIO.loadImage("resources/space.png");
	private static Image mapScreen = FileIO.loadImage("resources/mapscreen.png");
	private static Image smallMenu = FileIO.loadImage("resources/smallmenu.png");
	private static Image keyImg = FileIO.loadImage("resources/key.png");
	private static Image dialogueBox = FileIO.loadImage("resources/dialogueBox.png");

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
		
		this.keyboard = keyboard;
	}

	public void changeLog(String log1) {
		if (!this.log1.equals(log1)) {
			this.log2 = this.log1;
			this.log1 = log1;
		}
	}

	public void paint(Ship s, Sector[][] secs) {

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
		
		graphics2d.drawImage(s.getImage(), 48 + 64 * s.getSecX(), 48 + 64 * s.getSecY(), canvas);
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
