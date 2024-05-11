package com.game.util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import com.game.ui.ButtonComponent;

public class MouseManager implements MouseListener, MouseMotionListener {
	
	public static ArrayList<ButtonComponent> buttons = new ArrayList<ButtonComponent>();
	
	public static void addButtonToTrack(ButtonComponent button) {
		buttons.add(button);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();

		for(ButtonComponent button: buttons) {
			int buttonX = button.getX();
			int buttonY = button.getY();
			int buttonWidth = button.getWidth();
			int buttonHeight = button.getHeight();
			
			
			if(mouseX > buttonX && mouseX < buttonX + buttonWidth && mouseY > buttonY && mouseY < buttonY + buttonHeight)
				button.isClicked = true;
			else
				button.isClicked = false;
		}
	}
	
	public static void cleanUp() {
		// clear all the buttons from the array list
		buttons.clear();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		
		for(ButtonComponent button: buttons) {
			int buttonX = button.getX();
			int buttonY = button.getY();
			int buttonWidth = button.getWidth();
			int buttonHeight = button.getHeight();
			
			if(mouseX > buttonX && mouseX < buttonX + buttonWidth && mouseY > buttonY && mouseY < buttonY + buttonHeight)
				button.isOver = true;
			else 
				button.isOver = false;
		}
	}
}
