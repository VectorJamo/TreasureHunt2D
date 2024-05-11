package com.game.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.game.Game;
import com.game.entities.AnotherPlayer;
import com.game.entities.Player;
import com.game.networking.Client;
import com.game.networking.Server;
import com.game.tiles.TileMap;
import com.game.util.KeyManager;

public class GameState extends StateManager {
	// Tile-map
	private TileMap map;
	
	// Server specific
	public static boolean connectionRequestArrived = false;
	public static boolean connectionEstablishedWithClient = false;
	
	private boolean pauseGame;
	private Server server;
	
	// Client specific
	public static boolean connectionEstablishedWithServer = false;
	public static boolean isClient = false, isServer = false;
	
	// Player
	private Player player;
	
	private AnotherPlayer anotherPlayer;
	
	public GameState(Game game) {
		super(game);
		map = new TileMap();
		pauseGame = false;
		
		player = new Player(23*TileMap.tileSize, 21*TileMap.tileSize, TileMap.tileSize, TileMap.tileSize, 3, "/images/player/Walking sprites/boy_down_1.png");
		if(isClient) {
			anotherPlayer = new AnotherPlayer(Client.serverX, Client.serverY, TileMap.tileSize, TileMap.tileSize, 3, "", player);
		}
		if(isServer) {
			server = new Server();
			anotherPlayer = new AnotherPlayer(Server.clientX, Server.clientY, TileMap.tileSize, TileMap.tileSize, 0, "", player);
		}
	}
	
	@Override
	public void tick() {
		// Server logic
		if(isServer) {
			if(!pauseGame) {
				if(connectionEstablishedWithClient) {
					// Here, we have a valid connection. So, take the received X and Y coordinate data and render the other player on the screen
					map.tick();
					player.tick();
					
					// TODO: Tick the another player (entity)
					AnotherPlayer.playerAnimation = Server.clientDirection;
					anotherPlayer.tick();
					anotherPlayer.setWorldX(Server.clientX);
					anotherPlayer.setWorldY(Server.clientY);
					
					Server.sendPositionInfoToClient(player.getWorldX(), player.getWorldY(), Player.direction);
				}else {
					map.tick();
					player.tick();
				}
			}
			
			if(connectionRequestArrived && !connectionEstablishedWithClient) {
				pauseGame = true;
				
				if(KeyManager.Y) {
					pauseGame = false;
					connectionEstablishedWithClient = true;
					
					Server.sendConnectionResponseMessage();
				}
				if(KeyManager.N) {
					pauseGame = false;
					connectionRequestArrived = false;
					connectionEstablishedWithClient = false;
				}
			}
		}
		
		// Client logic
		if(isClient) {
			if(connectionEstablishedWithServer) {
				map.tick();
				player.tick();
				
				// TODO: Tick the another player (entity)
				AnotherPlayer.playerAnimation = Client.serverDirection;
				anotherPlayer.tick();
				
				anotherPlayer.setWorldX(Client.serverX);
				anotherPlayer.setWorldY(Client.serverY);
				
				Client.sendPositionInfoToServer(player.getWorldX(), player.getWorldY(), Player.direction);
			}
		}
	}

	@Override
	public void render(Graphics2D g2d) {
		
		if(isServer) {
			map.render(g2d, player);
			player.render(g2d);
			
			if(connectionRequestArrived && !connectionEstablishedWithClient) {
				g2d.setColor(Color.WHITE);
				g2d.setFont(new Font("Courier New", Font.BOLD, 24));
				g2d.drawString("A client is requesting connection.", 100, 100);
				g2d.drawString("Press Y to accept.", 100, 200);
				g2d.drawString("Press N to discard.", 100, 250);
			}else {
				// TODO: Draw the another player (entity)
				anotherPlayer.render(g2d);
			}
		}
		if(isClient) {
			map.render(g2d, player);
			player.render(g2d);
			
			// TODO: Draw the another player (entity)
			anotherPlayer.render(g2d);
		}
	}

}
