package com.starshipsim.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

import com.starshipsim.files.FileIO;
import com.starshipsim.listeners.KeyboardListener;

public class EnemyShip extends Ship {
	
	private static Image imgEShip = FileIO.loadImage("resources/esmallship1.png");
	private static Image imgEShip2 = FileIO.loadImage("resources/esmallship2.png");
	
	public EnemyShip(int x, int y, KeyboardListener keyboard) {
		super(x, y, keyboard);
	}
	@Override
	public void draw(Graphics g, Canvas canvas) {
		// TODO Auto-generated method stub
		g.drawImage(imgEShip, x, y, 32, 32, null);
	}

}
