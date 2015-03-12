package com.starshipsim.states;

import java.awt.Font;
import java.awt.Graphics;

import com.starshipsim.graphics.ButtonUI;
import com.starshipsim.graphics.StarBackgroundFx;

public class MainMenuState extends State {
	private StarBackgroundFx bg = new StarBackgroundFx(100, 1920, 1080);
	private ButtonUI playButton;
	private ButtonUI loadButton;
	private ButtonUI exitButton;

	public MainMenuState(StateManager manager) {
		super(manager);
		initialize();
	}

	@Override
	public void initialize() {
		playButton = new ButtonUI("Play", 50, 300, 300, 100, manager.getMouse()) {
			@Override
			public void clicked() {
				manager.addState(new SectorState(manager));
			}
		};

		loadButton = new ButtonUI("Load", 50, 425, 300, 100, manager.getMouse()) {
			@Override
			public void clicked() {

			}
		};

		exitButton = new ButtonUI("Exit", 50, 550, 300, 100, manager.getMouse()) {
			@Override
			public void clicked() {
				System.exit(0);
			}
		};
	}

	@Override
	public void update() {
		bg.update();
		playButton.update(this.getCanvas());
		loadButton.update(this.getCanvas());
		exitButton.update(this.getCanvas());
	}

	@Override
	public void draw(Graphics g) {
		bg.draw(g);

		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 128));

		g.drawString("Starship Sim", 50, 200);

		g.setFont(new Font("Showcard Gothic", Font.ITALIC, 72));

		playButton.draw(g);
		loadButton.draw(g);
		exitButton.draw(g);
	}

	@Override
	public void end() {

	}

}
