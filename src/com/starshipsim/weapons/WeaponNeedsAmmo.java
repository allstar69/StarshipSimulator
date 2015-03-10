package com.starshipsim.weapons;

import com.starshipsim.enums.Quality;

public class WeaponNeedsAmmo extends Weapon{
	public WeaponNeedsAmmo(Quality quality) {
		super(quality);
		// TODO Auto-generated constructor stub
	}
	private int currentAmmo;
	private int maxAmmo;
	
	public int shoot(){
		
		//TODO 
		setCurrentAmmo(getCurrentAmmo() - 1);
		return currentAmmo;
		
	}
	public void refillAmmo(Ammo tempAmmo){
		int ammoAmount = tempAmmo.getAmount();
		setCurrentAmmo(getCurrentAmmo() + ammoAmount);
		if(getCurrentAmmo() > getMaxAmmo()){
			setCurrentAmmo(getMaxAmmo());
		}
		
	}
	
	public boolean checkEmpty(){
		boolean isEmpty = false;
		if(getCurrentAmmo() == 0){
			isEmpty = true;
		}
		return isEmpty;
	}
	
	public int getCurrentAmmo() {
		return currentAmmo;
	}
	public void setCurrentAmmo(int currentAmmo) {
		this.currentAmmo = currentAmmo;
	}
	public int getMaxAmmo() {
		return maxAmmo;
	}
	public void setMaxAmmo(int maxAmmo) {
		this.maxAmmo = maxAmmo;
	}
}
