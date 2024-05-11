package com.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.game.states.GameState;
import com.game.states.JoinState;
import com.game.states.MenuState;
import com.game.states.StateManager;
import com.game.util.KeyManager;
import com.game.util.MouseManager;

public class Game extends JPanel implements Runnable {
	
	private static final long serialVersionUID = -1590237933050627411L;

	public static final int SCREEN_WIDTH = 640; // 20 tiles in X
	public static final int SCREEN_HEIGHT = 480; // 15 tiles in Y
	public static final String TITLE = "Tile Game Multiplayer";
	public static final int FPS = 60;
	
	private static boolean running = true;
	
	// Window
	private JFrame frame;
	private Thread thread;
	
	// Utils
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	// States
	public static StateManager CURRENT_STATE;
	private MenuState menuState;
	
	public Game() {
		// Utilities
		keyManager = new KeyManager();
		mouseManager = new MouseManager();

		initWindow();

		// Create the game state
		menuState = new MenuState(this);
		
		CURRENT_STATE = menuState;
		
		// Create the game thread
		thread = new Thread(this);
		thread.start();
	}
	
	private void initWindow() {
		frame = new JFrame(TITLE);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLayout(null);
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(keyManager);
		this.addMouseListener(mouseManager);
		this.addMouseMotionListener(mouseManager);
		
		frame.add(this);
		frame.pack();
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void tick() {
		keyManager.tick();
		if(CURRENT_STATE != null)
			CURRENT_STATE.tick();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		
		if(CURRENT_STATE != null)
			CURRENT_STATE.render(g2d);
		
		g2d.dispose();
	}
	
	@Override
	public void run() {
		double timePerFrame = 1000000000 / FPS;
		long lastTime = System.nanoTime();
		long now = 0;
		double deltaTime = 0;
		while (running) {
			now = System.nanoTime();
			deltaTime += (now - lastTime);
			lastTime = now;

			if (deltaTime >= timePerFrame) {
				tick();
				repaint();
				
				deltaTime = 0;
			}
		}
	}
	
	public static void setCurrentState(StateManager gameState) {
		CURRENT_STATE = gameState;
	}
}
