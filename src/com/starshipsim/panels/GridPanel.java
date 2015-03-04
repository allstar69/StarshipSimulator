package com.starshipsim.panels;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

import com.starshipsim.files.FileIO;
import com.starshipsim.objects.Ship;
import com.starshipsim.states.MapState;
import com.starshipsim.world.Grid;
import com.starshipsim.world.Sector;

public class GridPanel extends JPanel {

	private static final long serialVersionUID = -211533608456417324L;
	
	private MapState state;
	
	private static Image mapScreen = FileIO.loadImage("resources/mapscreen.png");
	
	public GridPanel(MapState state, int x, int y) {
		this.setLocation(x, y);
		this.state = state;
	}
	
	public void draw() {
		Graphics2D g = state.getGraphics2d();
		Canvas canvas = state.getCanvas();
		Grid grid = state.getGrid();
		Ship ship = state.getShip();
		
		g.drawImage(mapScreen, this.getX(), this.getY(), canvas);
		
		Sector[][] secs = grid.getSectors();
		for (int i = 0; i < secs.length; i++) {
			for (int j = 0; j < secs[i].length; j++) {
				secs[j][i].paint(g, canvas, this.getX() + 32 + j * 64, this.getY() + 32 + i * 64);
			}
		}
		
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 16));
		for (int i = 1; i < 13; i++) {
			g.drawString("" + i, this.getX() + 12, this.getY() + i * 64 + 4);
			g.drawString(((char) (i + 96)) + "", this.getX() + i * 64 - 4, this.getY() + 24);
		}
		
		g.drawImage(ship.getImage(),this.getX() + 48 + 64 * ship.getSecX(), this.getY() + 48 + 64 * ship.getSecY(), canvas);
	}
}
