package com.starshipsim.world;

import java.awt.Canvas;
import java.awt.Graphics;

import com.starshipsim.files.FileIO;

public class Sector {
	private boolean known = true;
	private boolean mysterious = false;
	private boolean hostile = false;
	int state = 1;

	/*
	 * 1=neutral 2=friendly 3=explorable 4=dangerous
	 */

	public boolean isKnown() {
		return known;
	}

	public void setKnown(boolean known) {
		this.known = known;
	}

	public boolean isMysterious() {
		return mysterious;
	}

	public void setMysterious(boolean mysterious) {
		this.mysterious = mysterious;
	}

	public boolean isHostile() {
		return hostile;
	}

	public void setHostile(boolean hostile) {
		this.hostile = hostile;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Sector() {
	}

	public void paint(Graphics g, Canvas c, int x, int y) {
		if (!isKnown()) {
			g.drawImage(FileIO.loadImage("resources/unknown.png"), x, y, c);
		} else {
			if (isMysterious()) {
				g.drawImage(FileIO.loadImage("resources/Mysterious.png"), x, y,
						c);
			} else {
				if (isHostile()) {
					g.drawImage(FileIO.loadImage("resources/hostile.png"), x,
							y, c);
				} else {
					g.drawImage(FileIO.loadImage("resources/neutral.png"), x,
							y, c);
				}
				if (getState() == 2) {
					g.drawImage(FileIO.loadImage("resources/freindly.png"), x,
							y, c);
				} else if (getState() == 3) {
					g.drawImage(FileIO.loadImage("resources/explorable.png"),
							x, y, c);
				} else if (getState() == 4) {
					g.drawImage(FileIO.loadImage("resources/dangerous.png"), x,
							y, c);
				}
			}
		}
	}
}
