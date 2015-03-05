package com.starshipsim.combat;

import com.starshipsim.objects.Ship;

public class CombatData {

	private EnemyFleet enemies;
	private Ship player;
	
	public CombatData(Ship player, EnemyFleet enemies) {
		this.player = player;
		this.enemies = enemies;
	}
	
}
