package com.starshipsim.listeners;

import java.awt.Color;
import java.awt.Graphics;

import com.starshipsim.interfaces.AchievementListenerInterface;
import com.starshipsim.world.Grid;
import com.starshipsim.world.Sector;

public class MapListener implements AchievementListenerInterface{

	private int numDiscovered;
	private long systemTime;
	private long timer;

//	@Override
	public void update() {
		numDiscovered ++;	
	}
	
		public void draw(Graphics g, Grid grid) {
			int counter = 0;
			for(Sector[] x : grid.getSectors()){
				for(Sector y : x){
					if(y.isKnown())
					{
						counter++;
					}
				}
			}
			if(counter == 144){
				if(systemTime == 0){
					systemTime = System.currentTimeMillis();
				}
				timer = System.currentTimeMillis();
				if(timer < systemTime + 3000){
					g.setColor(Color.DARK_GRAY);
					g.fillRect(0, 0, 500, 300);
					g.setColor(Color.WHITE);
					g.drawString("You got the: ", 150, 100);
					g.drawString("EXPLORER", 150, 150);
					g.drawString("Achievement.", 150, 200);				}
			}
		}
	}

