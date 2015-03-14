package com.starshipsim.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.Random;

import com.starshipsim.data.ShipData;
import com.starshipsim.graphics.ImageManager;
import com.starshipsim.listeners.KeyboardListener;
import com.starshipsim.shipmodules.WeaponModule;
import com.sun.glass.events.KeyEvent;

public class Ship extends Entity {
	private int durability = 200;
	private int maxDurability = 200;
	private int secX;
	private int secY;
	private int distanceTravelled;
	protected ShipData data;
	private boolean isDestroyed = false;
	

	private int rot = 0;
	private int drot = 0;
	private int speed = 8;

	private boolean isFlying = false;

	private KeyboardListener keyboard;
	private AffineTransform xform = new AffineTransform();
	private Player player;

	public ShipData getData() {
		return data;
	}

	public void setData(ShipData data) {
		this.data = data;
	}

	public boolean isFlying() {
		return isFlying;
	}

	public int dealDamage() {
		WeaponModule weapons = this.data.getWeapon();
		int damage = weapons.shootAll();
		return damage;
	}

	public Ship(Player p, int x, int y, ShipData data, KeyboardListener keyboard) {
		super(ImageManager.ship, x, y, 32, 32);

		this.data = data;
		Random rand = new Random();
		secX = rand.nextInt(11);
		secY = rand.nextInt(11);
		this.keyboard = keyboard;
		player = p;
	}

	public Ship(Image img, int x, int y, ShipData data,
			KeyboardListener keyboard) {
		super(img, x, y, 32, 32);

		this.data = data;
		Random rand = new Random();
		secX = rand.nextInt(11);
		secY = rand.nextInt(11);
		this.keyboard = keyboard;
	}

	@Override
	public void initialize() {

	}

	@Override
	public void update() {
		if (isFlying) {
			this.setImage(ImageManager.ship2);
		} else {
			this.setImage(ImageManager.ship);
		}
		if(getDurability()<=0){
			isDestroyed=true;
			setDurability(0);
		}
	}

	@Override
	public void draw(Graphics g, Canvas canvas) {
		xform.setToTranslation(getX(), getY());
		xform.rotate(getRot() * Math.PI / 180, 16, 16);

		((Graphics2D) g).drawImage(this.getImage(), xform, canvas);
	}

	public void move(Canvas canvas) {
		if (!isDestroyed) {
			if (rot >= 270 && drot < 90) {
				rot -= 360;
			} else if (rot < 90 && drot >= 270) {
				rot += 360;
			}
			if (rot > drot) {
				rot -= 15;

			} else if (rot < drot) {

				rot += 15;
			}
			if (keyboard.keyDown(KeyEvent.VK_SHIFT)
					&& player.getInventory().get(0).getAmount() > 0 && isFlying) {
				setSpeed(16);
				if (distanceTravelled % 5 == 0) {
					player.getInventory()
							.get(0)
							.setAmount(
									player.getInventory().get(0).getAmount() - 1);
				}
			} else {
				setSpeed(6);
			}
			if (keyboard.keyDown(KeyEvent.VK_W)) {
				if (keyboard.keyDown(KeyEvent.VK_A)) {
					setDrot(225);
					if (getX() - 1 > canvas.getX()) {
						decelerateX();
					} else if (getSecX() - 1 >= 0) {
						setX(1880);
						setSecX(getSecX() - 1);
					}
				} else if (keyboard.keyDown(KeyEvent.VK_D)) {
					setDrot(315);
					if (getX() + 1 + this.getImage().getWidth(null) < canvas
							.getX() + canvas.getWidth()) {
						accelerateX();
					} else if (getSecX() + 1 <= 11) {
						setX(0);
						setSecX(getSecX() + 1);
					}
				} else {
					setDrot(270);
				}

				if (getY() - 1 > canvas.getY()) {
					decelerateY();
				} else if (getSecY() - 1 >= 0) {
					setY(1050);
					setSecY(getSecY() - 1);
				}
				setDistanceTravelled(getDistanceTravelled() + 1);
				isFlying = true;
			} else if (keyboard.keyDown(KeyEvent.VK_S)) {
				if (keyboard.keyDown(KeyEvent.VK_A)) {
					setDrot(135);
					if (getX() - 1 > canvas.getX()) {
						decelerateX();
					} else if (getSecX() - 1 >= 0) {
						setX(1880);
						setSecX(getSecX() - 1);
					}
				} else if (keyboard.keyDown(KeyEvent.VK_D)) {
					setDrot(45);
					if (getX() + 1 + this.getImage().getWidth(null) < canvas
							.getX() + canvas.getWidth()) {
						accelerateX();
					} else if (getSecX() + 1 <= 11) {
						setX(0);
						setSecX(getSecX() + 1);
					}
				} else {
					setDrot(90);
				}

				if (getY() + 1 + this.getImage().getHeight(null) < canvas
						.getY() + canvas.getHeight()) {
					accelerateY();
				} else if (getSecY() + 1 <= 11) {
					setY(0);
					setSecY(getSecY() + 1);
				}
				setDistanceTravelled(getDistanceTravelled() + 1);
				isFlying = true;
			} else if (keyboard.keyDown(KeyEvent.VK_A)) {
				setDrot(180);

				if (getX() - 1 > canvas.getX()) {
					decelerateX();
				} else if (getSecX() - 1 >= 0) {
					setX(1880);
					setSecX(getSecX() - 1);
				}
				setDistanceTravelled(getDistanceTravelled() + 1);
				isFlying = true;
			} else if (keyboard.keyDown(KeyEvent.VK_D)) {
				setDrot(0);

				if (getX() + 1 + this.getImage().getWidth(null) < canvas.getX()
						+ canvas.getWidth()) {
					accelerateX();
				} else if (getSecX() + 1 <= 11) {
					setX(0);
					setSecX(getSecX() + 1);
				}

				setDistanceTravelled(getDistanceTravelled() + 1);
				isFlying = true;
			} else {
				isFlying = false;
			}
			
		}
	}

	public Bullet shootBullet() {
		return new Bullet(getRot(), getX(), getY(), 32, 32);
	}

	public void decelerateX() {
		setX(getX() - getSpeed());

	}

	public void accelerateX() {
		setX(getX() + getSpeed());
	}

	public void decelerateY() {
		setY(getY() - getSpeed());
	}

	public void accelerateY() {
		setY(getY() + getSpeed());
	}

	public int getDurability() {
		return durability;
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}

	public int getMaxDurability() {
		return maxDurability;
	}

	public void setMaxDurability(int maxDurability) {
		this.maxDurability = maxDurability;
	}

	public int getSecX() {
		return secX;
	}

	public void setSecX(int secX) {
		this.secX = secX;
	}

	public int getSecY() {
		return secY;
	}

	public void setSecY(int secY) {
		this.secY = secY;
	}

	public int getDistanceTravelled() {
		return distanceTravelled;
	}

	public void setDistanceTravelled(int distanceTravelled) {
		this.distanceTravelled = distanceTravelled;
	}

	public int getRot() {
		return rot;
	}

	public void setRot(int rot) {
		this.rot = rot;
	}

	public int getDrot() {
		return drot;
	}

	public void setDrot(int drot) {
		this.drot = drot;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public boolean isDestroyed() {
		return isDestroyed;
	}

	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}
	
	public ShipData getShipData() {
		return data;
	}
}
