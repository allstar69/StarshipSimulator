package com.starshipsim.panels;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import com.starshipsim.entities.Ship;
import com.starshipsim.graphics.ImageManager;
import com.starshipsim.states.MapState;
import com.starshipsim.world.Grid;
import com.starshipsim.world.Sector;

public class GridPanel {
	
	private MapState state;
	
	private int x, y;
	
	public GridPanel(MapState state, int x, int y) {
		this.x = x;
		this.y = y;
		this.state = state;
	}
	
	public void draw(Graphics g) {
		Grid grid = state.getGrid();
		Ship ship = state.getShip();
		
		g.drawImage(ImageManager.mapScreen, this.x, this.y, null);
		
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
		AffineTransform xform= new AffineTransform();
		xform.setToTranslation(this.x + 48 + 64 * ship.getSecX(), this.y + 48 + 64 * ship.getSecY());
		xform.rotate(ship.getRot() * Math.PI / 180, 16, 16);
		((Graphics2D) g).drawImage(ship.getImage(), xform, null);
	}
}
