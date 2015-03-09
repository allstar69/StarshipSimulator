package com.starshipsim.states;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.Graphics;

import com.starshipsim.graphics.Button;
import com.starshipsim.graphics.StarBackgroundFx;

public class MainMenuState extends State {
	private StarBackgroundFx bg = new StarBackgroundFx(100, 1920, 1080);
	private Button playButton;
	private Button loadButton;
	private Button exitButton;
	
	public MainMenuState(StateManager manager) {
		super(manager);
		initialize();
	}
	
	@Override
	public void initialize() {
		playButton = new Button("Play", 50, 300, 300, 100, manager.getMouse()) {
			@Override
			public void clicked() {
				manager.addState(new SectorState(manager));
			}
		};
		
		loadButton = new Button("Load", 50, 425, 300, 100, manager.getMouse()) {
			@Override
			public void clicked() {
				
			}
		};
		
		exitButton = new Button("Exit", 50, 550, 300, 100, manager.getMouse()) {
			@Override
			public void clicked() {
				System.exit(0);
			}	
		};
	}

	@Override
	public void update() {
		bg.update();
		playButton.Update();
		loadButton.Update();
		exitButton.Update();
	}

	@Override
	public void draw(Graphics g, Canvas canvas) {
		bg.draw(g, canvas);
	
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 128));
		
		g.drawString("Starship Sim", 50, 200);
		
		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 72));
		
		playButton.Draw(g, canvas);
		loadButton.Draw(g, canvas);
		exitButton.Draw(g, canvas);
	}

	@Override
	public void end() {
		
	}

}
