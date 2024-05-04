package com.ruby2d.graphics;

import java.awt.image.BufferedImage;

// Load all the required images
public class Assets {
	
	private static String tilePath, itemsPath;

	public static BufferedImage earthTile, grassTile, sandTile, treeTile, wallTile, waterTile;
	public static BufferedImage playerSprite, oldManSprite;
	public static BufferedImage keyItemImage, chestItemImage, doorItemImage;
	
	public Assets() {
		tilePath  = "/assets/Blue Boy Adventure/Tiles/";
		itemsPath = "/assets/Blue Boy Adventure/Items/";
		
		// Tile assets
		earthTile = ImageLoader.loadImage(tilePath + "earth.png");
		grassTile = ImageLoader.loadImage(tilePath + "grass.png");
		sandTile = ImageLoader.loadImage(tilePath + "sand.png");
		treeTile = ImageLoader.loadImage(tilePath + "tree.png");
		wallTile = ImageLoader.loadImage(tilePath + "wall.png");
		waterTile = ImageLoader.loadImage(tilePath + "water.png");
		
		// Player assets
		playerSprite = ImageLoader.loadImage("/assets/Blue Boy Adventure/Player/Walking sprites/player-sprite.png");
		oldManSprite = ImageLoader.loadImage("/assets/Blue Boy Adventure/NPC/old_man.png");
		
		// Items
		keyItemImage = ImageLoader.loadImage(itemsPath + "key.png");
		chestItemImage = ImageLoader.loadImage(itemsPath + "chest.png");
		doorItemImage = ImageLoader.loadImage(itemsPath + "door.png");
	}
}
