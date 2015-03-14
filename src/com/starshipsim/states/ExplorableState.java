package com.starshipsim.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.starshipsim.entities.Player;
import com.starshipsim.files.FileIO;
import com.starshipsim.graphics.StarBackgroundFx;
import com.starshipsim.graphics.TiledBackground;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.world.ExplorableSector;

public class ExplorableState extends State{
	
	private StarBackgroundFx bg = new StarBackgroundFx(100, 1920, 1080);
	private ExplorableSector es;
	public ExplorableState(StateManager manager, Player player, ExplorableSector tempes) {
		super(manager);
		setEs(tempes);
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		bg.update();
		KeyboardListener keyboard = manager.getKeyboard();
		if(keyboard.keyDownOnce(KeyEvent.VK_ESCAPE)) {
			manager.popState();
		}
		
	}

	@Override
	public void draw(Graphics g) {
		bg.draw(g);
		
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 58));
		
		g.setColor(Color.white);
		
		for(int i = 0; i < es.getUpdate().size(); i++){
			g.drawString(es.getUpdate().get(i), 50, 100*(i + 1) + 200);
		}
		g.drawString("Press Escape to Exit", 50, 900);
		
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
