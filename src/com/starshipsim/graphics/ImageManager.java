package com.starshipsim.graphics;

import java.awt.Image;

import com.starshipsim.files.FileIO;

public class ImageManager {

	//Backgrounds
	public static Image spaceBg = FileIO.loadImage("resources/spaceBackground.png");
	public static Image spaceBg2 =FileIO.loadImage("resources/space.png");
	
	//Entities
	public static Image asteroid = FileIO.loadImage("resources/asteroid.png");
	public static Image blackhole = FileIO.loadImage("resources/blackhole.png");
	public static Image enemyShip = FileIO.loadImage("resources/esmallship1.png");
	public static Image enemyShip2 = FileIO.loadImage("resources/esmallship2.png");
	public static Image ship = FileIO.loadImage("resources/smallship1.png");
	public static Image ship2 = FileIO.loadImage("resources/smallship2.png");
	public static Image enemyStation = FileIO.loadImage("resources/enemy station.png");
	public static Image explosion = FileIO.loadImage("resources/Explosion.png");
	public static Image mine = FileIO.loadImage("resources/mine.png");
	public static Image friendlyStation = FileIO.loadImage("resources/FriendlyStation.png");
	public static Image proj1=FileIO.loadImage("resources/proj1.png");
	public static Image elephant=FileIO.loadImage("resources/elephant.png");
	
	//GUI
	public static Image combatHud = FileIO.loadImage("resources/combat_hud.png");
	public static Image shield = FileIO.loadImage("resources/shield.png");
	public static Image laser = FileIO.loadImage("resources/laser1.png");
	public static Image elaser = FileIO.loadImage("resources/laser2.png");
	public static Image smallMenu = FileIO.loadImage("resources/smallmenu.png");
	public static Image largeMenu = FileIO.loadImage("resources/largemenu.png");
	public static Image mapScreen = FileIO.loadImage("resources/mapscreen.png");
	public static Image cursor = FileIO.loadImage("resources/cursor.png");
	public static Image key = FileIO.loadImage("resources/key.png");
	public static Image dialogueBox = FileIO.loadImage("resources/dialogueBox.png");
	public static Image combatEnemyShip = FileIO.loadImage("resources/eship1.png");
	public static Image controls = FileIO.loadImage("resources/Controls.png");
	
	//Sectors
	public static Image unknown = FileIO.loadImage("resources/unknown.png");
	public static Image mysterious = FileIO.loadImage("resources/Mysterious.png");
	public static Image hostile = FileIO.loadImage("resources/hostile.png");
	public static Image neutral = FileIO.loadImage("resources/neutral.png");
	public static Image friendly = FileIO.loadImage("resources/freindly.png");
	public static Image explorable = FileIO.loadImage("resources/explorable.png");
	public static Image dangerous = FileIO.loadImage("resources/dangerous.png");
	public static Image enemy = FileIO.loadImage("resources/enemy station.png");
	
	//Stars
	public static Image redStar = FileIO.loadImage("resources/stars/redStar.png");
	public static Image whiteStar = FileIO.loadImage("resources/stars/whiteStar.png");
	public static Image blueStar = FileIO.loadImage("resources/stars/blueStar.png");
	
	
	
}
