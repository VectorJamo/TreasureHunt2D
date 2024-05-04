package com.ruby2d.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener{
	
	private static boolean[] keys;
	public static boolean UP, DOWN, LEFT, RIGHT, SPACE;
	
	public KeyManager() {
		keys = new boolean[256];
		UP = false;
		DOWN = false;
		LEFT = false;
		RIGHT = false;
	}

	public void tick() {
		UP = keys[KeyEvent.VK_W];
		DOWN = keys[KeyEvent.VK_S];
		LEFT = keys[KeyEvent.VK_A];
		RIGHT = keys[KeyEvent.VK_D];
		SPACE = keys[KeyEvent.VK_SPACE];
	}
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
