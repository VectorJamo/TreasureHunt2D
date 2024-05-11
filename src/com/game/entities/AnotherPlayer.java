package com.game.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.game.gfx.ImageLoader;

public class AnotherPlayer extends Entity {

	// Another player's animation
	private BufferedImage[] playerLeft, playerRight, playerUp, playerDown;
	private float animationSpeed, animationCounter;
	
	public static String playerAnimation = "D";
	
	private Player player;

	public AnotherPlayer(int worldX, int worldY, int width, int height, int speed, String path, Player player) {
		super(worldX, worldY, width, height, speed, path);
		this.player = player;
		
		initPlayerResources();
		animationCounter = 0.0f;
		animationSpeed = 0.1f;
	}
	
	public void initPlayerResources() {
		playerLeft = new BufferedImage[2];
		playerRight = new BufferedImage[2];
		playerUp = new BufferedImage[2];
		playerDown = new BufferedImage[2];
		
		playerLeft[0] = ImageLoader.loadImage("/images/player/Walking sprites/boy2_left_1.png");
		playerLeft[1] = ImageLoader.loadImage("/images/player/Walking sprites/boy2_left_2.png");

		playerRight[0] = ImageLoader.loadImage("/images/player/Walking sprites/boy2_right_1.png");
		playerRight[1] = ImageLoader.loadImage("/images/player/Walking sprites/boy2_right_2.png");

		playerUp[0] = ImageLoader.loadImage("/images/player/Walking sprites/boy2_up_1.png");
		playerUp[1] = ImageLoader.loadImage("/images/player/Walking sprites/boy2_up_2.png");

		playerDown[0] = ImageLoader.loadImage("/images/player/Walking sprites/boy2_down_1.png");
		playerDown[1] = ImageLoader.loadImage("/images/player/Walking sprites/boy2_down_2.png");
	}

	@Override
	public void tick() {
		if(playerAnimation.equals("L")) {
			entityImage = playAnimation(playerLeft);
		}
		if(playerAnimation.equals("R")) {
			entityImage = playAnimation(playerRight);
		}
		if(playerAnimation.equals("U")) {
			entityImage = playAnimation(playerUp);
		}
		if(playerAnimation.equals("D")) {
			entityImage = playAnimation(playerDown);
		}
	}

	@Override
	public void render(Graphics2D g2d) {
		int anotherPlayerWorldX = worldX;
		int anotherPlayerWorldY = worldY;
		int anotherPlayerScreenX = anotherPlayerWorldX - (player.getWorldX() - player.getScreenX());
		int anotherPlayerScreenY = anotherPlayerWorldY - (player.getWorldY() - player.getScreenY());

		g2d.drawImage(entityImage, anotherPlayerScreenX, anotherPlayerScreenY, width, height, null);		
	}
	
	private BufferedImage playAnimation(BufferedImage[] images) {
		animationCounter += animationSpeed;
		
		if(animationCounter >= 2) {
			animationCounter = 0;
		}
		
		return images[(int)Math.floor(animationCounter)];
	}

}
