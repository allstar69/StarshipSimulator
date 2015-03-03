package application;

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

import objects.Sector;
import objects.Ship;

public class GUI extends JFrame {
	private final int HEIGHT = 1000;

	private final int WIDTH = 1200;
	private Canvas canvas = new Canvas();

	private Graphics2D graphics2d = null;
	private Graphics graphics = null;
	private BufferStrategy buffer = null;
	private BufferedImage bi = null;

	private String log1 = "Do the thing!";

	private String log2 = "";
	private Listener key = new Listener();
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

	public Listener getKey() {
		return key;
	}

	public void setKey(Listener key) {
		this.key = key;
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

	public GUI() {
		setSize(WIDTH, HEIGHT);

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas = new Canvas();
		canvas.setIgnoreRepaint(true);
		canvas.setSize(WIDTH, HEIGHT);
		tBox();
		add(canvas);
		pack();
		addKeyListener(key);
		setIgnoreRepaint(true);
		canvas.createBufferStrategy(2);

		buffer = canvas.getBufferStrategy();

		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();

		GraphicsDevice gd = ge.getDefaultScreenDevice();

		GraphicsConfiguration gc = gd.getDefaultConfiguration();

		bi = gc.createCompatibleImage(WIDTH, HEIGHT);

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
		graphics2d.setFont(new Font("Showcard Gothic", Font.ITALIC, 24));
		int i = 0;
		graphics2d.drawString(("Move the Ship"), 890, (100 + (i * 32)));
		i++;
		graphics2d.drawString(("Get Sector Data"), 890, (100 + (i * 32)));
		i++;
		graphics2d.drawString(("Open Science Station"), 890, (100 + (i * 32)));
		i++;
		graphics2d.drawString(("Set Map Standards"), 890, (100 + (i * 32)));
		i++;
		graphics2d.drawString(("Reset Map"), 890, (100 + (i * 32)));
		graphics2d.drawImage(s.getImage(), 48 + 64 * s.getSecX(),
				48 + 64 * s.getSecY(), canvas);

	}

	public void repaint() {

		buffer.show();
	}

	public void tBox() {
		p1.setSize(120, 30);
		add(p1, BorderLayout.CENTER);
		f1.addKeyListener(key);
		f1.setVisible(false);
		p1.setVisible(false);
		p1.add(f1);
		p1.setLocation(890, 100);
		this.add(canvas);

	}
}
