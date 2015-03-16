package com.starshipsim.combat;

import com.starshipsim.entities.Player;

public class CombatData {

	private EnemyFleet enemies;
	private Player player;
	
	public EnemyFleet getEnemies() {
		return enemies;
	}

	public void setEnemies(EnemyFleet enemies) {
		this.enemies = enemies;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public CombatData(Player player, EnemyFleet enemies) {
		this.player = player;
		this.enemies = enemies;
	}
	
}
