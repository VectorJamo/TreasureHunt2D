package com.game.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.game.Game;
import com.game.gfx.ImageLoader;
import com.game.networking.Client;
import com.game.ui.ButtonComponent;
import com.game.util.KeyManager;
import com.game.util.MouseManager;

public class JoinState extends StateManager {
	
	public static boolean isIpFocused = false, isPortFocused = false;
	public static String ip = "";
	public static String port = "";
	
	private BufferedImage joinButtonImage, backButtonImage;
	private ButtonComponent joinButton, backButton;
	
	public static boolean connectionSent = false;
	public static boolean connectionAcknowleged = false;
	
	public static Client client;

	public JoinState(Game game) {
		super(game);
		
		joinButtonImage = ImageLoader.loadImage("/images/ui/join.png");
		joinButton = new ButtonComponent(joinButtonImage, Game.SCREEN_WIDTH/2 - 50, Game.SCREEN_HEIGHT/2 - 20 - 80, 100, 40);
		
		backButtonImage = ImageLoader.loadImage("/images/ui/back.png");
		backButton = new ButtonComponent(backButtonImage, Game.SCREEN_WIDTH/2 - 50, Game.SCREEN_HEIGHT/2 - 20 - 20, 100, 40);
		
		MouseManager.addButtonToTrack(joinButton);
		MouseManager.addButtonToTrack(backButton);
	}
	
	@Override
	public void tick() {
		
		if(joinButton.isClicked) {
			System.out.println("IP: " + ip);
			System.out.println("PORT: " + port);
			ip = "";
			port = "";
			
			connectionSent = true;
			joinButton.isClicked = false;
			
			client = new Client(game);
			client.sendConnectionRequestMessage();
		}
		if(backButton.isClicked) {			
			MouseManager.cleanUp();
			game.setCurrentState(new MenuState(game));
			
			backButton.isClicked = false;
		}
		if(KeyManager.I) {
			isIpFocused = true;
			isPortFocused = false;
		}
		if(KeyManager.P) {
			isPortFocused = true;
			isIpFocused = false;
		}
	}

	@Override
	public void render(Graphics2D g2d) {
		joinButton.render(g2d);
		backButton.render(g2d);
		
		g2d.setFont(new Font("Courier New", Font.PLAIN, 22));
		g2d.setColor(Color.WHITE);

		g2d.drawString("IP Address: ", 20, 40);
		g2d.drawString("Port Number: ", 20, 100);
		
		g2d.setFont(new Font("Courier New", Font.PLAIN, 15));
		g2d.drawString("Press the letter I and start typing the IP address of the server.", 20, 300);
		g2d.drawString("Press the letter P and start typing the Port number of the server.", 20, 330);
		g2d.drawString("Once done typing both, click the join button.", 20, 360);

		g2d.setFont(new Font("Courier New", Font.PLAIN, 22));
		
		if(connectionSent) {
			g2d.setColor(Color.GREEN);
			g2d.drawString("Waiting for remote server to respond.", 20, 400);
			
		}else {
			if(isIpFocused) {
				g2d.drawString("Type the IP address", Game.SCREEN_WIDTH/2 - 120, 400);
			}
			if(isPortFocused) {
				g2d.drawString("Type the Port number", Game.SCREEN_WIDTH/2 - 120, 400);
			}
		}
		
		if(isIpFocused || isPortFocused) {
			g2d.drawString(ip, 20 + 180, 40);
			g2d.drawString(port, 20 + 180, 100);
		}
	}
}
