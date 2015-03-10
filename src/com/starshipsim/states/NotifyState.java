package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.starshipsim.files.FileIO;
import com.starshipsim.graphics.TiledBackground;
import com.starshipsim.listeners.KeyboardListener;

public class NotifyState extends State{
	
	public NotifyState(StateManager manager, ArrayList<String> msgs) {
		super(manager);
		setMessages(msgs);
		
	}
	private TiledBackground bg = new TiledBackground(FileIO.loadImage("resources/spaceBackground.png"), 0, 0);
	private ArrayList<String> messages;
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		KeyboardListener keyboard = manager.getKeyboard();
		if(keyboard.keyDownOnce(KeyEvent.VK_ESCAPE)) {
			manager.popState();
		}
		if(keyboard.keyDownOnce(KeyEvent.VK_ENTER)){
			manager.popState();
		}
		
	}

	@Override
	public void draw(Graphics g, Canvas canvas) {
		bg.draw(g, canvas);
		
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 58));
		
		g.setColor(Color.white);
		
		
		for(int i = 0; i < getMessages().size(); i++){
			
			g.drawString(getMessages().get(i), 50, i * 100 + 250);
			
		}
		g.drawString("Press Enter or Escape to Exit", 50, 900);
		
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
