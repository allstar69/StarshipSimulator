package com.starshipsim.data;

import com.starshipsim.shipmodules.PowerModule;
import com.starshipsim.shipmodules.PropulsionModule;
import com.starshipsim.shipmodules.ShieldModule;
import com.starshipsim.shipmodules.WarpCoreModule;
import com.starshipsim.shipmodules.WeaponModule;

public class ShipData {

	private PowerModule power;
	private ShieldModule shield;
	private WeaponModule weapon;
	private PropulsionModule propulsion;
	private WarpCoreModule warp;

	public PowerModule getPower() {
		return power;
	}

	public void setPower(PowerModule power) {
		this.power = power;
	}

	public ShieldModule getShield() {
		return shield;
	}

	public void setShield(ShieldModule shield) {
		this.shield = shield;
	}

	public WeaponModule getWeapon() {
		return weapon;
	}

	public void setWeapon(WeaponModule weapon) {
		this.weapon = weapon;
	}

	public PropulsionModule getPropulsion() {
		return propulsion;
	}

	public void setPropulsion(PropulsionModule propulsion) {
		this.propulsion = propulsion;
	}

	public WarpCoreModule getWarp() {
		return warp;
	}

	public void setWarp(WarpCoreModule warp) {
		this.warp = warp;
	}

	public ShipData(PowerModule power, ShieldModule shield,
			WeaponModule weapon, PropulsionModule propulsion,
			WarpCoreModule warp) {
		this.power = power;
		this.shield = shield;
		this.weapon = weapon;
		this.propulsion = propulsion;
		this.warp = warp;
	}
}
