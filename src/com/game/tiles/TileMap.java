package com.game.tiles;

import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.game.entities.Player;

public class TileMap {
	
	public static int tileSize;
	public static int numScreenTilesRow = 20, numScreenTilesCol = 15;
	public static int numWorldTilesRow, numWorldTilesCol; // numWorldTilesRow = number of rows(height), numWorldTilesCol = number of columns(width)
	public static int worldWidth, worldHeight; //  tile size * number of tiles in that axes
	
	private static final int MAX_TILE_VARIETY = 10;
	private Tile[] tiles;
	public static ArrayList<CollideableTile> collideableTiles;
	
	public static int tileMap[][];
	
	public TileMap() {
		initTiles();
		loadMap("res/maps/world.txt");
	}
	
	private void initTiles() {
		tiles = new Tile[MAX_TILE_VARIETY];
		collideableTiles = new ArrayList<CollideableTile>();
		
		tiles[0] = new GrassTile();
		
		tiles[1] = new SandTile();
		
		tiles[2] = new WallTile();
		tiles[2].isCollideable = true;
		
		tiles[3] = new WaterTile();
		tiles[3].isCollideable = true;
		
		tiles[4] = new TreeTile();
		tiles[4].isCollideable = true;
		
		tiles[5] = new EarthTile();
	}
	
	private void loadMap(String path) {
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
			numWorldTilesCol = Integer.parseInt(headerData[0]);
			numWorldTilesRow = Integer.parseInt(headerData[1]);
			tileSize = Integer.parseInt(headerData[2]); // In case that an individual tile's X and Y are different, but in most of the cases we will be using square tiles
			tileSize = Integer.parseInt(headerData[3]);
			worldWidth = numWorldTilesRow * tileSize;
			worldHeight = numWorldTilesCol * tileSize;

			// Parse the map data into the array
			String[] mapData = map.split("\\s+");
			tileMap = new int[numWorldTilesRow][numWorldTilesCol];
			
			for(int i = 0; i < numWorldTilesRow; i++) {
				for(int j = 0; j < numWorldTilesCol; j++) {
					int tileID = Integer.parseInt(mapData[i*numWorldTilesRow + j]);
					tileMap[i][j] = tileID;
					
					if(tiles[tileID].isCollideable) {
						collideableTiles.add(new CollideableTile(tileID, j*tileSize, i*tileSize));
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics2D g2d, Player player) {
		for(int i = 0; i < numWorldTilesRow; i++) {
			for(int j = 0; j < numWorldTilesCol; j++) {
				int tileIndex = tileMap[i][j];
				
				int worldX = j*tileSize;
				int worldY = i*tileSize;
				int screenX = (int) (worldX - (player.getWorldX() - player.getScreenX()));
				int screenY = (int) (worldY - (player.getWorldY() - player.getScreenY()));
				
				g2d.drawImage(tiles[tileIndex].tileImage, screenX, screenY, tileSize, tileSize, null);
			}
		}
	}
}
