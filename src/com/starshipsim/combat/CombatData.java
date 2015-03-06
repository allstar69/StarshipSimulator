package com.starshipsim.combat;

import com.starshipsim.entities.Ship;

public class CombatData {

	private EnemyFleet enemies;
	private Ship player;
	
	public EnemyFleet getEnemies() {
		return enemies;
	}

	public void setEnemies(EnemyFleet enemies) {
		this.enemies = enemies;
	}

	public Ship getPlayer() {
		return player;
	}

	public void setPlayer(Ship player) {
		this.player = player;
	}

	public CombatData(Ship player, EnemyFleet enemies) {
		this.player = player;
		this.enemies = enemies;
	}
	
}
