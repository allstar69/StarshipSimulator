package com.starshipsim.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.starshipsim.files.FileIO;
import com.starshipsim.graphics.ButtonUI;
import com.starshipsim.graphics.StarBackgroundFx;
import com.starshipsim.graphics.TiledBackground;
import com.starshipsim.listeners.KeyboardListener;

public class CreditsState extends State{
	private ButtonUI exitButton;
	public CreditsState(StateManager manager) {
		super(manager);
		initialize();
	}
	private StarBackgroundFx bg = new StarBackgroundFx(100, 1920, 1080);
	private ArrayList<String> messages;
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		exitButton = new ButtonUI("Exit", 700, 700, 300, 100, 10, manager.getMouse()) {
			@Override
			public void clicked() {
				manager.popState();
			}
		};
	}

	@Override
	public void update() {
		KeyboardListener keyboard = manager.getKeyboard();
		if(keyboard.keyDownOnce(KeyEvent.VK_ESCAPE)) {
			manager.popState();
		}
		bg.update();
		exitButton.update(this.getCanvas());
	}

	@Override
	public void draw(Graphics g) {
		bg.draw(g);
		
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 58));
		
		g.setColor(Color.white);
		
		exitButton.draw(g);
		g.drawString("Captain - Roger Schoellgen", 400, 200);
		g.drawString("Pilot - William Walls", 400, 300);
		g.drawString("Engineer - Joshua Stephens", 400, 400);
		g.drawString("Foreign Affairs - Rachel Strasdin", 400, 500);
		g.drawString("Inventory Manager - Carver Anglin", 400, 600);
		
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<String> getMessages() {
		return messages;
	}
	public void setMessages(ArrayList<String> messages) {
		this.messages = messages;
	}
}
