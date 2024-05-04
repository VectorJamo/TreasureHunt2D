package com.ruby2d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;

import com.ruby2d.display.Display;
import com.ruby2d.graphics.Assets;
import com.ruby2d.states.GameState;
import com.ruby2d.states.StateManager;
import com.ruby2d.util.KeyManager;
import com.ruby2d.util.MouseManager;

public class Game implements Runnable {
	
	private Display window;
	private Thread thread;
	
	private boolean running = false;
	
	private BufferStrategy bs;
	private Graphics g;
	
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	private Assets assets;
	private GameState gameState;
	
	public Game(String title, int width, int height) {
		window = new Display(width, height, title);
		
		keyManager = new KeyManager();
		window.getCanvas().addKeyListener(keyManager);
		mouseManager = new MouseManager();
		window.getCanvas().addMouseMotionListener(mouseManager);
		window.getCanvas().addMouseListener(mouseManager);
		
		assets = new Assets();
		
		gameState = new GameState();
		StateManager.setState(gameState);
	}
	
	public void start() {
		if(!running) {
			running = true;
			
			thread = new Thread(this);
			thread.start();
		}
	}
	
	// Only to be called by main Thread
	public void stop() {
		if(running) {
			running = false;
			
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void tick() {
		keyManager.tick();
		StateManager.currentState.tick();
	}
	
	public void render() {
		bs = window.getCanvas().getBufferStrategy();
		if(bs == null) {
			window.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		// Clear
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Display.getWidth(), Display.getHeight());
		
		// Draw
		StateManager.currentState.render(g);
		
		// Swap buffers
		bs.show();
		g.dispose();
	}
	
	@Override
	public void run() {
		int fps = 60;
		double timePerFrame = 1000000000 / fps;
		long lastTime = System.nanoTime();
		long now = 0;
		double deltaTime = 0;
		while (running) {
			now = System.nanoTime();
			deltaTime += (now - lastTime);
			lastTime = now;

			if (deltaTime >= timePerFrame) {
				tick();
				render();
				
				deltaTime = 0;
			}
		}
	}

}
