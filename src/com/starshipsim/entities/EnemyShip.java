package com.starshipsim.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

import com.starshipsim.combat.EnemyFleet;
import com.starshipsim.data.ShipData;
import com.starshipsim.enums.Quality;
import com.starshipsim.graphics.ImageManager;
import com.starshipsim.interfaces.Enemy;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.shipmodules.PowerModule;
import com.starshipsim.shipmodules.PropulsionModule;
import com.starshipsim.shipmodules.ShieldModule;
import com.starshipsim.shipmodules.WarpCoreModule;
import com.starshipsim.shipmodules.WeaponModule;
import com.starshipsim.weapons.Weapon;
import com.starshipsim.world.Grid;

public class EnemyShip extends Ship implements Enemy {

	private static Random random = new Random();
	private AffineTransform xform = new AffineTransform();
	private double rot;
	private double nextrot;
	private long deltaTime = 0;
	private Grid grid;
	private EnemyFleet fleet=null;


	public EnemyShip(Grid grid, int x, int y, int secX, int secY, KeyboardListener keyboard) {
		super(ImageManager.enemyShip2, x, y, createShip(), keyboard);
		rot = random.nextInt(360);
		deltaTime = System.currentTimeMillis();
		this.grid = grid;
		setSecX(secX);
		setSecY(secY);
		fleet=new EnemyFleet();
	}

	public EnemyShip(int x, int y, KeyboardListener keyboard) {
		super(ImageManager.enemyShip2, x, y, createShip(), keyboard);
		rot = random.nextInt(360);
		deltaTime = System.currentTimeMillis();
	}

	private static ShipData createShip() {
		Weapon phaser = new Weapon("Phaser", Quality.LOW);
		ArrayList<Weapon> weapons = new ArrayList<>();
		weapons.add(phaser);

		PowerModule power = new PowerModule(Quality.LOW);
		ShieldModule shield = new ShieldModule(Quality.LOW);
		WeaponModule weapon = new WeaponModule(Quality.LOW, weapons);
		PropulsionModule propulsion = new PropulsionModule(Quality.LOW);
		WarpCoreModule warp = new WarpCoreModule(Quality.LOW);

		ShipData data = new ShipData(power, shield, weapon, propulsion, warp);

		return data;
	}

	@Override
	public void draw(Graphics g, Canvas canvas) {

		// TODO Auto-generated method stub
		((Graphics2D) g).drawImage(this.getImage(), xform, canvas);
	}

	@Override
	public void update() {
		move();
		this.updateBoxes();
	}

	public void move() {
		
		if (System.currentTimeMillis() > deltaTime + 500) {
			nextrot = rot + 90 * (random.nextInt(3) - 1);
			deltaTime = System.currentTimeMillis();
		}
		if (rot >= nextrot) {
			rot--;
		}
		if (rot <= nextrot) {
			rot++;
		}
		setX((int) (getX() + Math.cos(rot * Math.PI / 180) * 6));
		setY((int) (getY() + Math.sin(rot * Math.PI / 180) * 6));
		xform.setToTranslation(getX(), getY());
		xform.rotate((rot) * Math.PI / 180, 16, 16);
		if (getX() < 0) {
			if (getSecX() > 0) {
				grid.getSector(getSecX() - 1, getSecY()).getEntities()
						.add(this);
				setX(1900+getX());

				grid.getSector(getSecX(), getSecY()).getEntities().remove(this);
				setSecX(getSecX() - 1);
			} else {
				rot += 180;
			}
		}
		if (getY() < 0) {
			
			if (getSecY() > 0) {
				grid.getSector(getSecX(), getSecY() - 1).getEntities().add(this);
				setY(1000);

				grid.getSector(getSecX(), getSecY()).getEntities().remove(this);
				setSecY(getSecY() - 1);
			} else {
				rot += 180;
			}
			
		}
		if (getX() > 1900) {
			if (getSecX() < 11) {
				grid.getSector(getSecX() + 1, getSecY()).getEntities()
						.add(this);
				setX(0);

				grid.getSector(getSecX(), getSecY()).getEntities().remove(this);
				setSecX(getSecX() + 1);
			} else {
				rot += 180;
			}
		}
		if (getY() > 1000) {
			if (getSecY() < 11) {
				grid.getSector(getSecX(), getSecY() + 1).getEntities()
						.add(this);
				setY(0);

				grid.getSector(getSecX(), getSecY()).getEntities().remove(this);
				setSecY(getSecY() + 1);
			} else {
				rot += 180;
			}
		}
		
	}

	public double getRot1() {
		return rot;
	}

	public void setRot(double rot) {
		this.rot = rot;
	}

	@Override
	public void takeDamage(int damage) {
		// TODO Auto-generated method stub
		setDurability(getDurability() - damage);
	}

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return getDurability();
	}

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return getMaxDurability();
	}
	public EnemyFleet getFleet() {
		return fleet;
	}

	public void setFleet(EnemyFleet fleet) {
		this.fleet = fleet;
	}
}
