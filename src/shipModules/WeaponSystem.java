package shipModules;

import java.util.ArrayList;

import weapons.Weapon;

public class WeaponSystem extends ShipModule {
	private int weaponMax = 10;
	private ArrayList<Weapon> weapons = new ArrayList();

	public void addWeapon(Weapon wep) {
		weapons.add(wep);
	}

	public void removeWeapon(int i) {
		weapons.remove(i);
	}
}
