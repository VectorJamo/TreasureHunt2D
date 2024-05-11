package com.game.states;

import java.awt.Graphics2D;

import com.game.Game;

public abstract class StateManager {
	
	public Game game;
	
	public StateManager(Game game) {
		this.game = game;
	}
	
	public abstract void tick();
	public abstract void render(Graphics2D g2d);
}
