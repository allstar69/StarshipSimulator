package com.starshipsim.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.starshipsim.graphics.ButtonUI;
import com.starshipsim.listeners.GameMouseListener;
import com.starshipsim.panels.ModuleSwapper;
import com.starshipsim.panels.ShipModuleButton;
import com.starshipsim.shipmodules.ShipModule;

public class ScrollbarUI extends UIComponent {

	private final int MAX_SHOWN;
	
	private ShipModuleButton[] buttons;
	private ArrayList<ShipModule> modules;
	
	private ButtonUI upButton;
	private ButtonUI downButton;
	
	private int start;
	private int end;

	public ArrayList<ShipModule> getModules() {
		modules.clear();
		
		for (ShipModuleButton button : buttons) {
			modules.add(button.getModule());
		}
		
		return modules;
	}

	public ShipModuleButton[] getButtons() {
		return buttons;
	}

	public void setButtons(ShipModuleButton[] buttons) {
		this.buttons = buttons;
	}

	public ScrollbarUI(ArrayList<ShipModule> modules, int x, int y, int width, int height, int maxShown, GameMouseListener mouse, DescriptionBoxUI desc, ModuleSwapper swapper) {
		super(x, y, width, height);
		this.modules = modules;
		
		this.setHeight((125 * maxShown) + 75);
	
		initializeButtons(mouse);
		
		this.start = 0;
		
		if(modules.size() > maxShown)
			this.end = (start+maxShown);
		else
			this.end = modules.size();
		
		if(modules.size() < maxShown) {
			this.MAX_SHOWN = modules.size();
			this.buttons = new ShipModuleButton[modules.size()];
		} else {
			this.MAX_SHOWN = maxShown;
			this.buttons = new ShipModuleButton[MAX_SHOWN];
		}
		
		for (int i = start; i < end; i++) {
			buttons[i] = new ShipModuleButton(this.getX() + 25, (this.getY() + 50) + (i*125), 300, 100, 10, mouse, modules.get(i), desc, swapper);
		}
		
		for (int i = start; i < end; i++) {
			buttons[i-start].setModule(modules.get(i));
			buttons[i-start].setText(modules.get(i).getName());
		}
	}
	
	public void initializeButtons(GameMouseListener mouse) {
		this.upButton = new ButtonUI("", getX() + 165, getY() + 10, 20, 20, 3, mouse) {
			@Override
			public void clicked() {
				start--;
				end--;
				
				if(start < 0) {
					start = 0;
					end = start+MAX_SHOWN;
				}
				
				for (int i = start; i < end; i++) {
					buttons[i-start].setModule(modules.get(i));
					buttons[i-start].setText(modules.get(i).getName());
				}
			}
		};
		
		this.downButton = new ButtonUI("", getX() + 165, (getY() + getHeight()) - 30, 20, 20, 3, mouse) {
			@Override
			public void clicked() {
				start++;
				end++;
				
				if(end > modules.size()-1) {
					end = modules.size();
					start = end-MAX_SHOWN;
				}
				
				for (int i = start; i < end; i++) {
					buttons[i-start].setModule(modules.get(i));
					buttons[i-start].setText(modules.get(i).getName());
				}
			}
		};
	}
	
	public int getMouseInsideAmount(Canvas canvas) {
		int count = 0;
		for (ButtonUI buttonUI : buttons) {
			if(!buttonUI.mouseInside(canvas)) {
				count++;
			}
		}
		
		return count;
	}

	@Override
	public void update(Canvas canvas) {
		for (ButtonUI buttonUI : buttons) {
			buttonUI.update(canvas);
		}
		
		this.upButton.update(canvas);
		this.downButton.update(canvas);
	}

	@Override
	public void draw(Graphics g) {
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		
		g.setColor(Color.WHITE);
		for (ButtonUI buttonUI : buttons) {
			buttonUI.draw(g);
		}
		
		this.upButton.draw(g);
		this.downButton.draw(g);
	}

}
