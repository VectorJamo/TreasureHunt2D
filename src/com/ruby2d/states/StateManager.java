package com.ruby2d.states;

import java.awt.Graphics;

public abstract class StateManager {
	
	public static StateManager currentState = null;
	
	public static void setState(StateManager gameState) {
		currentState = gameState;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
}
