package com.starshipsim.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameMouseListener implements MouseListener {
	
	private boolean isLeftPressed = false;
	private boolean isRightPressed = false;
	
	public boolean isLeftPressed() {
		return isLeftPressed;
	}

	public boolean isRightPressed() {
		return isRightPressed;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			this.isLeftPressed = true;
		}
		
		if(e.getButton() == MouseEvent.BUTTON3) {
			this.isRightPressed = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			this.isLeftPressed = false;
		}
		
		if(e.getButton() == MouseEvent.BUTTON3) {
			this.isRightPressed = false;
		}
	}
}
