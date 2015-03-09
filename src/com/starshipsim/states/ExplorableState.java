package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.starshipsim.entities.Player;
import com.starshipsim.files.FileIO;
import com.starshipsim.graphics.TiledBackground;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.world.ExplorableSector;

public class ExplorableState extends State{
	
	private TiledBackground bg = new TiledBackground(FileIO.loadImage("resources/spaceBackground.png"), 0, 0);
	private ExplorableSector es;
	public ExplorableState(StateManager manager, Player player, ExplorableSector tempes) {
		super(manager);
		setEs(tempes);
		// TODO Auto-generated constructor stub
	}

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
		
		for(int i = 0; i < es.getUpdate().size(); i++){
			g.drawString(es.getUpdate().get(i), 100, 100*(i + 1));
		}
		
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

	public ExplorableSector getEs() {
		return es;
	}
	public void setEs(ExplorableSector es) {
		this.es = es;
	}
}
