package com.ruby2d.tiles;

import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.ruby2d.display.Display;
import com.ruby2d.graphics.Assets;
import com.ruby2d.util.Camera;
import com.ruby2d.tiles.CollideableTile;

public class TileMap {
	
	public int mapWidth, mapHeight; //  mapWidth and mapHeight are in no. of tiles in each direction (X and Y).
	public static int TILEWIDTH, TILEHEIGHT;
	public static int TILESCALE = 1;
	
	private static int[][] tileMap;
	public static ArrayList<CollideableTile> collideableTiles;
	
	private Camera camera;
	private float worldBoundsLeft, worldBoundsRight, worldBoundsTop, worldBoundsBottom;

	public TileMap(String path, Camera camera) {
		this.camera = camera;
		
		loadMap(path);
	}
	public void loadMap(String path) {
		File file = new File(path);
		String header = "";
		String map = "";
		try {
			Scanner scanner = new Scanner(file);
			
			int counter = 1;
			while (scanner.hasNextLine()) {
				if(counter <= 2)
					header += (scanner.nextLine() + "\n");
				else
					map += (scanner.nextLine() + "\n");
				counter++;
			}
			// Parse the map header information
			String[] headerData = header.split("\\s+");
			mapWidth = Integer.parseInt(headerData[0]);
			mapHeight = Integer.parseInt(headerData[1]);
			TILEWIDTH = Integer.parseInt(headerData[2]) * TILESCALE;
			TILEHEIGHT = Integer.parseInt(headerData[3]) * TILESCALE;
			
			int totalMapWidth = this.mapWidth * this.TILEWIDTH;
			int totalMapHeight= this.mapHeight * this.TILEHEIGHT;

			worldBoundsLeft = -camera.getXOffset();
			worldBoundsRight = mapWidth*TILEWIDTH - camera.getXOffset();
			worldBoundsTop = -camera.getYOffset();
			worldBoundsBottom = mapHeight*TILEHEIGHT - camera.getYOffset();
			collideableTiles = new ArrayList<>();
			
			// Parse the map data into the array
			String[] mapData = map.split("\\s+");
			tileMap = new int[mapWidth][mapHeight];
			
			for(int i = 0; i < mapWidth; i++) {
				for(int j = 0; j < mapHeight; j++) {
					tileMap[i][j] = Integer.parseInt(mapData[i*mapWidth + j]);
					// TODO: Populate the collideableTiles array
					if(tileMap[i][j] == 4) {
						TreeTile t = new TreeTile(j*TILEWIDTH, i*TILEHEIGHT);
						
						collideableTiles.add(t);
					}
					if(tileMap[i][j] == 2) {
						StoneTile t = new StoneTile(j*TILEWIDTH, i*TILEHEIGHT);
						
						collideableTiles.add(t);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void tick() {
		// Update the collideable tiles positions in the collideable tiles array
		for(CollideableTile tile: collideableTiles) {
			tile.x = tile.initialX - camera.getXOffset();
			tile.y = tile.initialY - camera.getYOffset();
		}
		
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < mapWidth; i++) {
			for(int j = 0; j < mapHeight; j++) {
				int tileID = tileMap[i][j];
				int tileX = j*TILEWIDTH;
				int tileY = i*TILEHEIGHT;
				
				if(tileID == 0) {
					g.drawImage(Assets.grassTile, (int)(tileX - camera.getXOffset()), (int)(tileY - camera.getYOffset()), TILEWIDTH, TILEHEIGHT, null);
				}
				if(tileID == 1) {
					g.drawImage(Assets.earthTile, (int)(tileX - camera.getXOffset()), (int)(tileY - camera.getYOffset()), TILEWIDTH, TILEHEIGHT, null);
				}
				if(tileID == 2) {
					g.drawImage(Assets.wallTile, (int)(tileX - camera.getXOffset()), (int)(tileY - camera.getYOffset()), TILEWIDTH, TILEHEIGHT, null);
				}
				if(tileID == 3) {
					g.drawImage(Assets.waterTile, (int)(tileX - camera.getXOffset()), (int)(tileY - camera.getYOffset()), TILEWIDTH, TILEHEIGHT, null);
				}
				if(tileID == 4) {
					g.drawImage(Assets.treeTile, (int)(tileX - camera.getXOffset()), (int)(tileY - camera.getYOffset()), TILEWIDTH, TILEHEIGHT, null);
				}
				if(tileID == 5) {
					g.drawImage(Assets.sandTile, (int)(tileX - camera.getXOffset()), (int)(tileY - camera.getYOffset()), TILEWIDTH, TILEHEIGHT, null);
				}
			}
		}
	}
	public float getWorldBoundsLeft() {
		return worldBoundsLeft;
	}

	public void setWorldBoundsLeft(float worldBoundsLeft) {
		this.worldBoundsLeft = worldBoundsLeft;
	}

	public float getWorldBoundsRight() {
		return worldBoundsRight;
	}

	public void setWorldBoundsRight(float worldBoundsRight) {
		this.worldBoundsRight = worldBoundsRight;
	}

	public float getWorldBoundsTop() {
		return worldBoundsTop;
	}

	public void setWorldBoundsTop(float worldBoundsTop) {
		this.worldBoundsTop = worldBoundsTop;
	}

	public float getWorldBoundsBottom() {
		return worldBoundsBottom;
	}

	public void setWorldBoundsBottom(float worldBoundsBottom) {
		this.worldBoundsBottom = worldBoundsBottom;
	}

	int[][] getTileMap() {
		return tileMap;
	}

	
}
