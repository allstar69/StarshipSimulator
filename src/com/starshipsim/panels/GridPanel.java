package com.starshipsim.panels;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import com.starshipsim.files.FileIO;
import com.starshipsim.objects.Ship;
import com.starshipsim.states.MapState;
import com.starshipsim.world.Grid;
import com.starshipsim.world.Sector;

public class GridPanel {
	
	private MapState state;
	
	int x, y;
	
	private static Image mapScreen = FileIO.loadImage("resources/mapscreen.png");
	
	public GridPanel(MapState state, int x, int y) {
		this.x = x;
		this.y = y;
		this.state = state;
	}
	
	public void draw(Graphics g) {
		Grid grid = state.getGrid();
		Ship ship = state.getShip();
		
		g.drawImage(mapScreen, this.x, this.y, null);
		
		Sector[][] secs = grid.getSectors();
		for (int i = 0; i < secs.length; i++) {
			for (int j = 0; j < secs[i].length; j++) {
				secs[j][i].paint(g, null, this.x + 32 + j * 64, this.y + 32 + i * 64);
			}
		}
		
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 16));
		for (int i = 1; i < 13; i++) {
			g.drawString("" + i, this.x + 12, this.y + i * 64 + 4);
			g.drawString(((char) (i + 96)) + "", this.x + i * 64 - 4, this.y + 24);
		}
		
		g.drawImage(ship.getImage(),this.x + 48 + 64 * ship.getSecX(), this.y + 48 + 64 * ship.getSecY(), null);
	}
}
