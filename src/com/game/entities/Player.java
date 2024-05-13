package com.game.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.game.Game;
import com.game.gfx.ImageLoader;
import com.game.tiles.CollideableTile;
import com.game.tiles.TileMap;
import com.game.util.ClipRect;
import com.game.util.KeyManager;
import com.game.util.MouseManager;

public class Player extends Entity {
	
	public int dx, dy;
	
	// Player's animation
	private BufferedImage[] playerLeft, playerRight, playerUp, playerDown;
	private BufferedImage[] playerAttackLeft, playerAttackRight, playerAttackUp, playerAttackDown;
	
	private float animationSpeed, animationCounter;
	
	public static String direction = "D";

	public ClipRect attackHitBoxRect;
	private boolean isAttacking = false;

	public Player(int worldX, int worldY, int width, int height, int speed, String path) {
		super(worldX, worldY, width, height, speed, path);
		
		screenX = Game.SCREEN_WIDTH / 2 - TileMap.tileSize/2;
		screenY = Game.SCREEN_HEIGHT / 2 - TileMap.tileSize/2;
		
		super.setCollisionRect(8, 8, width - 16, height - 16);
		attackHitBoxRect = new ClipRect();
		
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
		playerAttackLeft = new BufferedImage[2];
		playerAttackRight = new BufferedImage[2];
		playerAttackUp = new BufferedImage[2];
		playerAttackDown = new BufferedImage[2];
		
		playerLeft[0] = ImageLoader.loadImage("/images/player/Walking sprites/boy_left_1.png");
		playerLeft[1] = ImageLoader.loadImage("/images/player/Walking sprites/boy_left_2.png");

		playerRight[0] = ImageLoader.loadImage("/images/player/Walking sprites/boy_right_1.png");
		playerRight[1] = ImageLoader.loadImage("/images/player/Walking sprites/boy_right_2.png");

		playerUp[0] = ImageLoader.loadImage("/images/player/Walking sprites/boy_up_1.png");
		playerUp[1] = ImageLoader.loadImage("/images/player/Walking sprites/boy_up_2.png");

		playerDown[0] = ImageLoader.loadImage("/images/player/Walking sprites/boy_down_1.png");
		playerDown[1] = ImageLoader.loadImage("/images/player/Walking sprites/boy_down_2.png");
		
		
		playerAttackLeft[0] = ImageLoader.loadImage("/images/player/Attacking sprites/boy_attack_left_1.png");
		playerAttackLeft[1] = ImageLoader.loadImage("/images/player/Attacking sprites/boy_attack_left_2.png");

		playerAttackRight[0] = ImageLoader.loadImage("/images/player/Attacking sprites/boy_attack_right_1.png");
		playerAttackRight[1] = ImageLoader.loadImage("/images/player/Attacking sprites/boy_attack_right_2.png");

		playerAttackUp[0] = ImageLoader.loadImage("/images/player/Attacking sprites/boy_attack_up_1.png");
		playerAttackUp[1] = ImageLoader.loadImage("/images/player/Attacking sprites/boy_attack_up_2.png");

		playerAttackDown[0] = ImageLoader.loadImage("/images/player/Attacking sprites/boy_attack_down_1.png");
		playerAttackDown[1] = ImageLoader.loadImage("/images/player/Attacking sprites/boy_attack_down_2.png");
	}

	@Override
	public void tick() {
		if(MouseManager.LEFT) {
			isAttacking = true;
			if(direction.equals("L")) {
				entityImage = playAnimation(playerAttackLeft);
			}
			if(direction.equals("R")) {
				entityImage = playAnimation(playerAttackRight);
			}
			if(direction.equals("U")) {
				entityImage = playAnimation(playerAttackUp);
			}
			if(direction.equals("D")) {
				entityImage = playAnimation(playerAttackDown);
			}
		}else {
			isAttacking = false;
			if(direction.equals("L")) {
				entityImage = playerLeft[0];
			}
			if(direction.equals("R")) {
				entityImage = playerRight[0];
			}
			if(direction.equals("U")) {
				entityImage = playerUp[0];
			}
			if(direction.equals("D")) {
				entityImage = playerDown[0];
			}
		}
		
		if(!isAttacking) {
			if(KeyManager.UP) {
				dy -= speed;
				direction = "U";
				entityImage = playAnimation(playerUp);
				
				// Attack hit box rect is relative to the player's world position
				attackHitBoxRect.x = 0;
				attackHitBoxRect.y = 0;
				attackHitBoxRect.width = width;
				attackHitBoxRect.height = -height;
			}
			if(KeyManager.DOWN) {
				dy += speed;
				direction = "D";
				entityImage = playAnimation(playerDown);
				
				// Attack hit box rect is relative to the player's world position
				attackHitBoxRect.x = 0;
				attackHitBoxRect.y = height;
				attackHitBoxRect.width = width;
				attackHitBoxRect.height = height;
			}
			if(KeyManager.LEFT) {
				dx -= speed;
				direction = "L";
				entityImage = playAnimation(playerLeft);
				
				// Attack hit box rect is relative to the player's world position
				attackHitBoxRect.x = 0;
				attackHitBoxRect.y = 0;
				attackHitBoxRect.width = -width;
				attackHitBoxRect.height = height;
			}		
			if(KeyManager.RIGHT) {
				dx += speed;
				direction = "R";
				entityImage = playAnimation(playerRight);
				
				// Attack hit box rect is relative to the player's world position
				attackHitBoxRect.x = width;
				attackHitBoxRect.y = 0;
				attackHitBoxRect.width = width;
				attackHitBoxRect.height = height;
			}
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
		if(isAttacking) {
			if(direction.equals("U")) {
				g2d.drawImage(entityImage, screenX, screenY - height, width, height*2, null);		
			}
			if(direction.equals("D")) {
				g2d.drawImage(entityImage, screenX, screenY, width, height*2, null);		
			}
			if(direction.equals("L")) {
				g2d.drawImage(entityImage, screenX - width, screenY, width*2, height, null);		
			}
			if(direction.equals("R")) {
				g2d.drawImage(entityImage, screenX, screenY, width*2, height, null);		
			}
		}else {
			g2d.drawImage(entityImage, screenX, screenY, width, height, null);		
		}
	}
	
	private BufferedImage playAnimation(BufferedImage[] images) {
		animationCounter += animationSpeed;
		
		if(animationCounter >= 2) {
			animationCounter = 0;
		}
		
		return images[(int)Math.floor(animationCounter)];
	}
}
