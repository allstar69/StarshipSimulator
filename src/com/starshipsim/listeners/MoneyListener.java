package com.starshipsim.listeners;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import com.starshipsim.interfaces.AchievementListenerInterface;
import com.starshipsim.world.Grid;
import com.starshipsim.world.Sector;

public class MoneyListener implements AchievementListenerInterface{

	private long systemTime = 0;
	private boolean achieved = false;
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	public void draw(Graphics g, int money) {

		if(money > 9000){
				if(systemTime == 0){
					systemTime = System.currentTimeMillis();
				}
				
				long timer = System.currentTimeMillis();
				if(timer < systemTime + 3000){
					g.setColor(Color.DARK_GRAY);
					g.fillRect(0, 0, 500, 300);
					g.setColor(Color.WHITE);
					g.drawString("You got the: ", 150, 100);
					g.drawString("YOU HAVE OVER 9000 !....... net worth", 0, 150);
					g.drawString("Achievement.", 150, 200);
				}
				achieved = true;
		}
	}
}
