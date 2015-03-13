package com.starshipsim.listeners;

import java.awt.Color;
import java.awt.Graphics;

import com.starshipsim.enums.SectorStateType;
import com.starshipsim.interfaces.AchievementListenerInterface;
import com.starshipsim.world.Grid;
import com.starshipsim.world.Sector;

public class EnemyListener implements AchievementListenerInterface{

	private long systemTime;
	private long timer;
	
	@Override
	public void update() {
	}
	
	public void draw(Graphics g, Grid grid) {
		int counter = 0;
		for(Sector[] x : grid.getSectors()){
			for(Sector y : x){
				if(y.getState() == SectorStateType.ENEMY)
				{
					counter++;
				}
			}
		}
		if(counter == 0){
			if(systemTime == 0){
				systemTime = System.currentTimeMillis();
			}
			timer = System.currentTimeMillis();
			if(timer < systemTime + 3000){
				g.setColor(Color.DARK_GRAY);
				g.fillRect(0, 0, 500, 300);
				g.setColor(Color.WHITE);
				g.drawString("You got the: ", 150, 100);
				g.drawString("IT'S A TRA..... oh, you got them all...", 0, 150);
				g.drawString("Achievement.", 150, 200);
			}
		}
	}
	
}
