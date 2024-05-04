package com.ruby2d.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import com.ruby2d.display.Display;
import com.ruby2d.entities.Chest;
import com.ruby2d.entities.Door;
import com.ruby2d.entities.Entity;
import com.ruby2d.entities.Item;
import com.ruby2d.entities.Key;
import com.ruby2d.entities.OldMan;
import com.ruby2d.entities.Player;
import com.ruby2d.tiles.CollideableTile;
import com.ruby2d.tiles.CollisionType;
import com.ruby2d.tiles.TileMap;
import com.ruby2d.ui.Button;
import com.ruby2d.util.Camera;
import com.ruby2d.util.KeyManager;
import com.ruby2d.util.MouseManager;
import com.ruby2d.util.Sound;

public class GameState extends StateManager {
	
	// Map
	private TileMap map;
	
	// Camera
	private Camera camera;
	private float cameraDX, cameraDY;
	
	// Player
	private Player player;
	private float playerSpeed;
	private float dx, dy;
	
	// Items
	private Key[] keys;
	private int keyCount;
	private Chest chest;
	private Door door;
	
	// NPCs
	private OldMan oldMan;
	
	// Audio
	private Sound sound;
	
	// UI 
	private int textStartTimer;
	private String currentText;
	
	private boolean pauseMovement;
	private boolean isTalking;
	private String[] dialogues;
	private int dialogueCounter;
	
	private Button button, button2;
		
	public GameState() {
		camera = new Camera(0.0f, 0.0f);
		
		// Set the camera's position in the world
		int mapWidth = 40 * 50;
		int mapHeight = 40 * 50;
		float correctionX = 40 * 1.5f;
		float correctionY = 40 * 3.5f;
		
		camera.setXOffset((mapWidth/2) - (Display.getWidth()/2) - correctionX);
		camera.setYOffset((mapHeight/2) - (Display.getHeight()/2) - correctionY);

		map = new TileMap("res/maps/SampleWorld.txt", camera);

		// Player
		player = new Player(Display.getWidth() / 2 - 15.0f, Display.getHeight() / 2 - 15.0f, 30, 30);
		playerSpeed = 3.0f;
		dx = 0;
		dy = 0;
		cameraDX = 0;
		cameraDY = 0;
		
		// Initialize Items
		keys = new Key[2];
		keys[0] = new Key(10*map.TILEWIDTH, 10*map.TILEHEIGHT, map.TILEWIDTH, map.TILEHEIGHT, camera);
		keys[1] = new Key(23*map.TILEWIDTH, 39*map.TILEHEIGHT, map.TILEWIDTH, map.TILEHEIGHT, camera);
		keyCount = 0;
		
		chest = new Chest(23*map.TILEWIDTH, 3*map.TILEHEIGHT, map.TILEWIDTH, map.TILEHEIGHT, camera);
		
		door = new Door(23*map.TILEWIDTH, 6*map.TILEHEIGHT, map.TILEWIDTH, map.TILEHEIGHT, camera);
		
		// NPCs
		oldMan = new OldMan(36*map.TILEWIDTH + 5, 21*map.TILEHEIGHT + 5, map.TILEWIDTH - 10, map.TILEHEIGHT - 10, camera);

		// Audio
		sound = new Sound();
		sound.backgroundMusic.start();
		
		// UI
		textStartTimer = 0;		

		pauseMovement = false;
		
		dialogues = new String[5];
		dialogues[0] = "You: Hi there old man.";
		dialogues[1] = "Old Man: Hi son. What brings you into this part of the forest?";
		dialogues[2] = "You: I'm feeling down today old man, any wisdom for this clueless child?";
		dialogues[3] = "Old Man: My child, what is there to sweep over parts of life \n when the whole of it calls for tears?";
		dialogues[4] = "You: Insightful! Thank you sir.";
		
		dialogueCounter = 0;
		isTalking = false;
		
		button = new Button("End Game", 100, 100, new Font("Arial", Font.PLAIN, 30));
		button2 = new Button("Start Game", 300, 100, new Font("Arial", Font.PLAIN, 30));
		MouseManager.addButtonsToListen(button);
		MouseManager.addButtonsToListen(button2);
	}
	
	public CollisionType checkCollision(Player p, CollideableTile tile, float dx, float dy, float camDX, float camDY) {
		CollisionType type = new CollisionType();
		type.xCollision = true;
		type.yCollision = true;
		
		// First set the player's X to the new X and check if collision occurs,
		float playerXNew = player.getX() + dx + camDX;
		if(playerXNew > tile.x + map.TILEWIDTH || playerXNew + player.getWidth() < tile.x || player.getY() + player.getHeight() < tile.y || player.getY() > tile.y + map.TILEHEIGHT) {
			type.xCollision = false;
		}
		// Then, set the player's Y to the new Y and check if collision occurs
		float playerYNew = player.getY() + dy + camDY;
		if(player.getX() > tile.x + map.TILEWIDTH || player.getX() + player.getWidth() < tile.x || playerYNew + player.getHeight() < tile.y || playerYNew > tile.y + map.TILEHEIGHT) {
			type.yCollision = false;
		}
		return type;
	}
	
	public CollisionType checkCollision(Player p, Item item, float dx, float dy, float camDX, float camDY) {
		CollisionType type = new CollisionType();
		type.xCollision = true;
		type.yCollision = true;
		
		// First set the player's X to the new X and check if collision occurs,
		float playerXNew = player.getX() + dx + camDX;
		if(playerXNew > item.getX() + map.TILEWIDTH || playerXNew + player.getWidth() < item.getX() || player.getY() + player.getHeight() < item.getY() ||
				player.getY() > item.getY() + map.TILEHEIGHT) {
			type.xCollision = false;
		}
		// Then, set the player's Y to the new Y and check if collision occurs
		float playerYNew = player.getY() + dy + camDY;
		if(player.getX() > item.getX()+ map.TILEWIDTH || player.getX() + player.getWidth() < item.getX() || playerYNew + player.getHeight() < item.getY() || 
				playerYNew > item.getY() + map.TILEHEIGHT) {
			type.yCollision = false;
		}
		return type;
	}
	public CollisionType checkCollision(Player p, Entity e, float dx, float dy, float camDX, float camDY) {
		CollisionType type = new CollisionType();
		type.xCollision = true;
		type.yCollision = true;
		
		// First set the player's X to the new X and check if collision occurs,
		float playerXNew = player.getX() + dx + camDX;
		if(playerXNew > e.getX() + map.TILEWIDTH || playerXNew + player.getWidth() < e.getX() || player.getY() + player.getHeight() < e.getY() ||
				player.getY() > e.getY() + map.TILEHEIGHT) {
			type.xCollision = false;
		}
		// Then, set the player's Y to the new Y and check if collision occurs
		float playerYNew = player.getY() + dy + camDY;
		if(player.getX() > e.getX()+ map.TILEWIDTH || player.getX() + player.getWidth() < e.getX() || playerYNew + player.getHeight() < e.getY() || 
				playerYNew > e.getY() + map.TILEHEIGHT) {
			type.yCollision = false;
		}
		return type;
	}
	
	@Override
	public void tick() {
		// TODO: Check for collision with collideable tiles in X or Y direction
		map.tick();
		player.tick();
		for(int i = 0; i < keys.length; i++) {
			keys[i].tick();
		}
		chest.tick();
		door.tick();
		oldMan.tick();
		
		if(!pauseMovement) {
			if(KeyManager.UP) {
				if(map.getWorldBoundsTop() < 0) {
					cameraDY -= camera.cameraSpeed;
				}else {
					dy -= playerSpeed;
				}
			}
			if(KeyManager.DOWN) {
				if(map.getWorldBoundsBottom() > Display.getHeight()) {
					cameraDY += camera.cameraSpeed;
				}else {
					dy += playerSpeed;
				}
			}
			if(KeyManager.LEFT) {
				if(map.getWorldBoundsLeft() < 0) {
					cameraDX -= camera.cameraSpeed;
				}else {
					dx -= playerSpeed;
				}
			}
			if(KeyManager.RIGHT) {
				if(map.getWorldBoundsRight() > Display.getWidth()) {
					cameraDX += camera.cameraSpeed;
				}else {
					dx += playerSpeed;
				}
			}
		}
		
		// Check if any collision will happen
		for(CollideableTile tile: TileMap.collideableTiles) {
			// Check for collision in the X-axis
			// Check for collision in the Y-axis
			CollisionType type = checkCollision(player, tile, dx, dy, cameraDX, cameraDY);
			if(type.xCollision) {
				cameraDX = 0;
				dx = 0;
			}
			if(type.yCollision) {
				dy = 0;
				cameraDY = 0;
			}
		}
		
		// Check for player and keys collision
		for(int i = 0; i < keys.length; i++) {
			if(keys[i].isActive) {
				CollisionType type = checkCollision(player, keys[i], dx, dy, cameraDX, cameraDY);
				if(type.xCollision || type.yCollision) {
					sound.keyCollectSound.setMicrosecondPosition(0);
					sound.keyCollectSound.start();
					
					currentText = "Key Collected!";
					textStartTimer = 1;
					
					keyCount++;
					keys[i].isActive = false;
				}
			}
		}
		// Check for player and door collision
		if(door.isActive) {
			CollisionType type = checkCollision(player, door, dx, dy, cameraDX, cameraDY);
			if(type.xCollision || type.yCollision) {
				if(keyCount > 0) {
					sound.doorUnlockSound.start();
					keyCount--;
					
					currentText = "Door opened!";
					textStartTimer = 1;
					
					door.isActive = false;
				}else {
					dx = 0;
					dy = 0;
					cameraDX = 0;
					cameraDY = 0;

					currentText = "You need a key to open the door.";
					textStartTimer = 1;
				}
			}
		}
		// Check for player and chest collision
		if(chest.isActive) {
			CollisionType type = checkCollision(player, chest, dx, dy, cameraDX, cameraDY);
			if(type.xCollision || type.yCollision) {
				if(keyCount > 0) {
					keyCount--;
					chest.isActive = false;
					
					currentText = "Chest opened!";
					textStartTimer = 1;
					
					pauseMovement = true;
				}else {
					currentText = "You need a key to open the chest.";
					textStartTimer = 1;
					
					dx = 0;
					dy = 0;
					cameraDX = 0;
					cameraDY = 0;
				}
			}
		}
		// Check for player and oldMan collision
		if(oldMan.isVisible) {
			CollisionType type = checkCollision(player, oldMan, dx, dy, cameraDX, cameraDY);
			if(type.xCollision || type.yCollision) {
				pauseMovement = true;
				isTalking = true;
			}
		}
		
		if(KeyManager.SPACE && isTalking) {
			dialogueCounter++;
			try {
				Thread.sleep(500); // Quick fix hack.
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if(dialogueCounter > 4) {
				dialogueCounter = 0;
				pauseMovement = false;
				isTalking = false;
				oldMan.isVisible = false;
			}
		}
		
		if(textStartTimer != 0) {
			textStartTimer++;
			if(textStartTimer > 60*2) { // approximately 2 seconds
				textStartTimer = 0;
				if(currentText.equals("Chest opened!")) {
					System.exit(0);
				}
			}
		}
		
		if(button.isClicked){
			System.out.println(1);
			button.isClicked = false;
		}
		if(button2.isClicked){
			System.out.println(2);
			button2.isClicked = false;
		}

		player.setX(player.getX() + dx);
		player.setY(player.getY() + dy);
		camera.setXOffset(camera.getXOffset() + cameraDX);
		camera.setYOffset(camera.getYOffset() + cameraDY);

		map.setWorldBoundsLeft(map.getWorldBoundsLeft() - cameraDX);
		map.setWorldBoundsRight(map.getWorldBoundsRight() - cameraDX);
		map.setWorldBoundsTop(map.getWorldBoundsTop() - cameraDY);
		map.setWorldBoundsBottom(map.getWorldBoundsBottom() - cameraDY);

		dx = 0;
		dy = 0;
		cameraDX = 0;
		cameraDY = 0;
	}

	@Override
	public void render(Graphics g) {
		map.render(g);
		player.render(g);
		for(int i = 0; i < keys.length; i++) {
			keys[i].render(g);
		}
		chest.render(g);
		door.render(g);
		oldMan.render(g);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		if(pauseMovement && isTalking) {
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("Arial", Font.PLAIN, 18));
			g2d.drawString(dialogues[dialogueCounter], 20, Display.getHeight() - 200);
		}
		if(textStartTimer != 0) {
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("Arial", Font.PLAIN, 32));
			g2d.drawString(currentText, 20, Display.getHeight() - 200);
		}
		
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial", Font.PLAIN, 40));
		g2d.drawString("Keys: " + keyCount, 20, 40);
		
		button.render(g2d);
		button2.render(g2d);
	}
}
