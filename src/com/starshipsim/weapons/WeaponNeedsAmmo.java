package com.starshipsim.weapons;

public class WeaponNeedsAmmo extends Weapon{
	private int currentAmmo;
	private int maxAmmo;
	
	public void shoot(){
		
		//TODO 
		setCurrentAmmo(getCurrentAmmo() - 1);
		
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
