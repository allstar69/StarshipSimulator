package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import com.starshipsim.files.FileIO;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.panels.MenuUI;
import com.sun.glass.events.KeyEvent;

public class SectorState extends State {

	private static Image imgBackground = FileIO.loadImage("resources/space.png");
	private static Image imgShip = FileIO.loadImage("resources/smallship1.png");
	private static Image imgShip2 = FileIO.loadImage("resources/smallship2.png");
	private static Image imgEStation = FileIO.loadImage("resources/enemy station.png");
	
	private static int shipX=960;
	private static int shipY=540;
	private static int shipRot=0;
	private static int speed=1;
	private static AffineTransform xform= new AffineTransform();
	private static boolean isFlying=false;
	private static Canvas canvas=null;
	
	private KeyboardListener keyboard;
	
	private MenuUI menu;
	
	private String[] list;
	
	int currentOption = 0;
	
	public SectorState(StateManager manager) {
		super(manager);
		this.keyboard = manager.getKeyboard();
		initialize();
	}

	@Override
	public void initialize() {
		
	}

	@Override
	public void update() {
		if(keyboard.keyDown(KeyEvent.VK_ESCAPE)) {
			manager.popState();
		}
		move();
	}

	@Override
	public void draw(Graphics g, Canvas canvas) {
		this.canvas=canvas;
		g.drawImage(imgBackground, 0, 0, null);
		xform.setToTranslation(shipX, shipY);
		xform.rotate(shipRot * Math.PI/4, 16, 16);
		g.setColor(Color.white);
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 24));
		g.drawImage(imgEStation, 600, 400, 200, 200, null);
		if(isFlying){
			((Graphics2D)g).drawImage(imgShip2, xform, canvas);
		}
		else{
			((Graphics2D)g).drawImage(imgShip, xform, canvas);
		}
		
	}

	@Override
	public void end() {
		
	}
	public void move(){
		if(keyboard.keyDown(KeyEvent.VK_W)) {
			if(keyboard.keyDown(KeyEvent.VK_A)) {
				shipRot=5;
				if(shipX-1>canvas.getX()){
					shipX-=speed;
				}
			}
			else if(keyboard.keyDown(KeyEvent.VK_D)){
				shipRot=7;
				if(shipX+1+imgShip.getWidth(null)<canvas.getX()+canvas.getWidth()){
					shipX+=speed;
				}
			}
			else{
				shipRot=6;
				
			}
			if(shipY-1>canvas.getY()){
				shipY-=speed;
			}
			isFlying=true;
		}
		else if(keyboard.keyDown(KeyEvent.VK_S)){
			if(keyboard.keyDown(KeyEvent.VK_A)) {
				shipRot=3;
				if(shipX-1>canvas.getX()){
					shipX-=speed;
				}
			}
			else if(keyboard.keyDown(KeyEvent.VK_D)){
				shipRot=1;
				if(shipX+1+imgShip.getWidth(null)<canvas.getX()+canvas.getWidth()){
					shipX+=speed;
				}
			}
			else{
				shipRot=2;
				
			}
			if(shipY+1+imgShip.getHeight(null)<canvas.getY()+canvas.getHeight()){
				shipY+=speed;
			}
			isFlying=true;
		}
		else if(keyboard.keyDown(KeyEvent.VK_A)){
			shipRot=4;
			if(shipX-1>canvas.getX()){
				shipX-=speed;
			}
			isFlying=true;
		}
		else if(keyboard.keyDown(KeyEvent.VK_D)){
			shipRot=0;
			if(shipX+1+imgShip.getWidth(null)<canvas.getX()+canvas.getWidth()){
				shipX+=speed;
			}
			isFlying=true;
		}
		else{
			isFlying=false;
		}
	}
}
