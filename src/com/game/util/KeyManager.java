package com.game.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.game.Game;
import com.game.states.JoinState;

public class KeyManager implements KeyListener{
	
	private static boolean[] keys;
	public static boolean UP, DOWN, LEFT, RIGHT, I, P, Y, N;
	
	private static char lastKeyPressed;
	
	private static boolean wasKeyPressed = false, hasKeyReleased = true;
	
	public KeyManager() {
		keys = new boolean[256];
		UP = false;
		DOWN = false;
		LEFT = false;
		RIGHT = false;
		I = false;
		P = false;
		Y = false;
		N = false;
	}

	public void tick() {
		UP = keys[KeyEvent.VK_W];
		DOWN = keys[KeyEvent.VK_S];
		LEFT = keys[KeyEvent.VK_A];
		RIGHT = keys[KeyEvent.VK_D];
		I = keys[KeyEvent.VK_I];
		P = keys[KeyEvent.VK_P];
		Y = keys[KeyEvent.VK_Y];
		N = keys[KeyEvent.VK_N];
	}
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		
		if(hasKeyReleased && (JoinState.isIpFocused || JoinState.isPortFocused)) {
			wasKeyPressed = true;
			hasKeyReleased = false;
			lastKeyPressed = e.getKeyChar();
			
			if(JoinState.isIpFocused) {
				if(lastKeyPressed == KeyEvent.VK_BACK_SPACE) {
					JoinState.ip = JoinState.ip.substring(0, JoinState.ip.length() - 1);
				}else {
					if(lastKeyPressed != 'p' && lastKeyPressed != 10) { // 10 is the code for new line char
						JoinState.ip += lastKeyPressed;
					}
				}
			}
			else if(JoinState.isPortFocused) {
				if(lastKeyPressed == KeyEvent.VK_BACK_SPACE) {
					JoinState.port = JoinState.port.substring(0, JoinState.port.length() - 1);
				}else {
					if(lastKeyPressed != 'i' && lastKeyPressed != 10) { // 10 is the code for new line char
						JoinState.port += lastKeyPressed;
					}
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		
		hasKeyReleased = true;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public static char getLastKeyPressed() {
		return lastKeyPressed;
	}
}