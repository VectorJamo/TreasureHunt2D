package com.game.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.game.Game;
import com.game.gfx.ImageLoader;
import com.game.tiles.CollideableTile;
import com.game.tiles.TileMap;
import com.game.util.KeyManager;

public class Player extends Entity {
	
	public int dx, dy;
	
	// Player's animation
	private BufferedImage[] playerLeft, playerRight, playerUp, playerDown;
	private float animationSpeed, animationCounter;
	
	public static String direction = "D";

	public Player(int worldX, int worldY, int width, int height, int speed, String path) {
		super(worldX, worldY, width, height, speed, path);
		
		screenX = Game.SCREEN_WIDTH / 2 - TileMap.tileSize/2;
		screenY = Game.SCREEN_HEIGHT / 2 - TileMap.tileSize/2;
		
		super.setCollisionRect(8, 8, width - 16, height - 16);
		
		dx = 0;
		dy = 0;
		
		initPlayerResources();
		animationCounter = 0.0f;
		animationSpeed = 0.1f;
	}
	
	public void initPlayerResources() {
		playerLeft = new BufferedImage[2];
		playerRight = new BufferedImage[2];
		playerUp = new BufferedImage[2];
		playerDown = new BufferedImage[2];
		
		
		playerLeft[0] = ImageLoader.loadImage("/images/player/Walking sprites/boy_left_1.png");
		playerLeft[1] = ImageLoader.loadImage("/images/player/Walking sprites/boy_left_2.png");

		playerRight[0] = ImageLoader.loadImage("/images/player/Walking sprites/boy_right_1.png");
		playerRight[1] = ImageLoader.loadImage("/images/player/Walking sprites/boy_right_2.png");

		playerUp[0] = ImageLoader.loadImage("/images/player/Walking sprites/boy_up_1.png");
		playerUp[1] = ImageLoader.loadImage("/images/player/Walking sprites/boy_up_2.png");

		playerDown[0] = ImageLoader.loadImage("/images/player/Walking sprites/boy_down_1.png");
		playerDown[1] = ImageLoader.loadImage("/images/player/Walking sprites/boy_down_2.png");
	}

	@Override
	public void tick() {
		if(KeyManager.UP) {
			dy -= speed;
			direction = "U";
			entityImage = playAnimation(playerUp);
		}
		if(KeyManager.DOWN) {
			dy += speed;
			direction = "D";
			entityImage = playAnimation(playerDown);
		}
		if(KeyManager.LEFT) {
			dx -= speed;
			direction = "L";
			entityImage = playAnimation(playerLeft);
		}
		if(KeyManager.RIGHT) {
			dx += speed;
			direction = "R";
			entityImage = playAnimation(playerRight);
		}
		
		// Check for collisions
		// If collision happens if any axes, set the delta of that axis to be 0
		for(CollideableTile tile: TileMap.collideableTiles) {
			CollisionAxes axes = CollisionHandler.checkCollision(this, tile, dx, dy);
			if(axes.xCollision) {
				dx = 0;
			}
			if(axes.yCollision) {
				dy = 0;
			}
		}
		
		worldX += dx;
		worldY += dy;
		
		dx = 0;
		dy = 0;
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.drawImage(entityImage, screenX, screenY, width, height, null);		
	}
	
	private BufferedImage playAnimation(BufferedImage[] images) {
		animationCounter += animationSpeed;
		
		if(animationCounter >= 2) {
			animationCounter = 0;
		}
		
		return images[(int)Math.floor(animationCounter)];
	}
}
