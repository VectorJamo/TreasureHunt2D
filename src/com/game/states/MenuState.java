package com.game.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.game.Game;
import com.game.gfx.ImageLoader;
import com.game.ui.ButtonComponent;
import com.game.util.MouseManager;

public class MenuState extends StateManager {
	
	// Menu State Assets
	private BufferedImage titleImage;
	private BufferedImage newGameImage, quitGameImage, joinGameImage;
	
	private ButtonComponent newGameButton, joinGameButton, quitGameButton;
	private int buttonYGap;
	
	// Simple effects
	private boolean drawRect = false;
	private int rectX, rectY, rectWidth, rectHeight, rectOffset;
	
	public MenuState(Game game) {
		super(game);
		
		loadAssets();
		
		buttonYGap = 20;
		
		newGameButton = new ButtonComponent(newGameImage, 0, 0, 160, 60);
		newGameButton.setX(Game.SCREEN_WIDTH / 2 - newGameButton.getWidth()/2);
		newGameButton.setY(Game.SCREEN_HEIGHT / 2 - newGameButton.getHeight()/2);
		
		joinGameButton = new ButtonComponent(joinGameImage, 0, 0, 160, 60);
		joinGameButton.setX(Game.SCREEN_WIDTH / 2 - newGameButton.getWidth()/2);
		joinGameButton.setY(Game.SCREEN_HEIGHT / 2 - newGameButton.getHeight()/2 + (buttonYGap + 60));
		
		quitGameButton = new ButtonComponent(quitGameImage, 0, 0, 160, 60);
		quitGameButton.setX(Game.SCREEN_WIDTH / 2 - quitGameButton.getWidth()/2);
		quitGameButton.setY(Game.SCREEN_HEIGHT / 2 - quitGameButton.getHeight()/2 + (buttonYGap*2 + 60*2));
		
		MouseManager.addButtonToTrack(newGameButton);
		MouseManager.addButtonToTrack(joinGameButton);
		MouseManager.addButtonToTrack(quitGameButton);
		
		rectX = 0;
		rectY = 0;
		rectWidth = 20;
		rectHeight = 20;
		
		rectOffset = 20;
	}
	@Override
	public void tick() {
		drawRect = false;
		
		if(newGameButton.isOver) {
			drawRect = true;
			
			rectX = newGameButton.getX() - rectWidth - rectOffset;
			rectY = newGameButton.getY() + newGameButton.getHeight()/2 - rectHeight;
		}
		if(joinGameButton.isOver) {
			drawRect = true;
			
			rectX = joinGameButton.getX() - rectWidth - rectOffset;
			rectY = joinGameButton.getY() + joinGameButton.getHeight()/2 - rectHeight;
		}
		if(quitGameButton.isOver) {
			drawRect = true;
			
			rectX = quitGameButton.getX() - rectWidth - rectOffset;
			rectY = quitGameButton.getY() + quitGameButton.getHeight()/2 - rectHeight;
		}
		if(newGameButton.isClicked) {
			MouseManager.cleanUp();
			GameState.isServer = true;
			GameState.isClient = false;

			Game.setCurrentState(new GameState(game));
			
			newGameButton.isClicked = false;
		}
		if(joinGameButton.isClicked) {
			MouseManager.cleanUp();
			GameState.isClient = true;
			GameState.isServer = false;

			Game.setCurrentState(new JoinState(game));

			joinGameButton.isClicked = false;
		}
		if(quitGameButton.isClicked) {
			
			
			quitGameButton.isClicked = false;
		}
	}
	
	public void loadAssets() {
		titleImage = ImageLoader.loadImage("/images/ui/title.png");
		newGameImage = ImageLoader.loadImage("/images/ui/new-game.png");
		quitGameImage = ImageLoader.loadImage("/images/ui/quit.png");
		joinGameImage = ImageLoader.loadImage("/images/ui/join.png");
	}
	
	@Override
	public void render(Graphics2D g2d) {
		g2d.drawImage(titleImage, Game.SCREEN_WIDTH/2 - 160, Game.SCREEN_HEIGHT/2 - 60*3, 320, 120, null);
	
		newGameButton.render(g2d);
		joinGameButton.render(g2d);
		quitGameButton.render(g2d);
		
		if(drawRect) {
			g2d.setColor(Color.WHITE);
			g2d.fillRect(rectX, rectY, rectWidth, rectHeight);
		}
	}

}
