package com.starshipsim.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class SmallStarFx extends StarFx {

	private static Random random = new Random();
	private float vel;
	private int angle;
	
	public SmallStarFx(int x, int y) {
		super(x, y);
		
		initialize();
	}

	@Override
	public void initialize() {
		vel = (float) (random.nextFloat()*1.25);
		angle = random.nextInt(10)+1;
	}

	@Override
	public void update() {
		x += (vel * (float) Math.cos(Math.toRadians(angle-180)));
        y += (vel * (float) Math.sin(Math.toRadians(angle-180)));
	}

	@Override
	public void draw(Graphics g, Canvas canvas) {
		g.setColor(Color.white);
		g.drawLine(x, y, x, y);
		
	}

}
