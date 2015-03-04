package com.starshipsim.world;

import java.awt.Canvas;
import java.awt.Graphics;

import com.starshipsim.enums.SectorState;
import com.starshipsim.files.FileIO;

public class Sector {
	private boolean known = false;
	private boolean mysterious = false;
	private boolean hostile = false;
	SectorState state = SectorState.NEUTRAL;

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

	public SectorState getState() {
		return state;
	}

	public void setState(int state) {
		this.state = SectorState.values()[state-1];
	}
	public void setState(SectorState state) {
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
				if (getState() == SectorState.FRIENDLY) {
					g.drawImage(FileIO.loadImage("resources/freindly.png"), x,
							y, c);
				} else if (getState() == SectorState.EXPLORABLE) {
					g.drawImage(FileIO.loadImage("resources/explorable.png"),
							x, y, c);
				} else if (getState() == SectorState.DANGEROUS) {
					g.drawImage(FileIO.loadImage("resources/dangerous.png"), x,
							y, c);
				}
			}
		}
	}
}