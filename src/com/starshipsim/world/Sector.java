package com.starshipsim.world;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

import com.starshipsim.enums.SectorState;
import com.starshipsim.files.FileIO;

public class Sector {
	private boolean known = false;
	private boolean mysterious = false;
	private boolean hostile = false;
	SectorState state = SectorState.NEUTRAL;
	
	private static Image imgUnknown = FileIO.loadImage("resources/unknown.png");
	private static Image imgMysterious = FileIO.loadImage("resources/Mysterious.png");
	private static Image imgHostile = FileIO.loadImage("resources/hostile.png");
	private static Image imgNeutral = FileIO.loadImage("resources/neutral.png");
	private static Image imgFriendly = FileIO.loadImage("resources/freindly.png");
	private static Image imgExplorable = FileIO.loadImage("resources/explorable.png");
	private static Image imgDangerous = FileIO.loadImage("resources/dangerous.png");

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
			g.drawImage(imgUnknown, x, y, c);
		} else {
			if (isMysterious()) {
				g.drawImage(imgMysterious, x, y,
						c);
			} else {
				if (isHostile()) {
					g.drawImage(imgHostile, x,
							y, c);
				} else {
					g.drawImage(imgNeutral, x,
							y, c);
				}
				if (getState() == SectorState.FRIENDLY) {
					g.drawImage(imgFriendly, x,
							y, c);
				} else if (getState() == SectorState.EXPLORABLE) {
					g.drawImage(imgExplorable,
							x, y, c);
				} else if (getState() == SectorState.DANGEROUS) {
					g.drawImage(imgDangerous, x,
							y, c);
				}
			}
		}
	}
}
