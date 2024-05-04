package com.ruby2d.util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import com.ruby2d.ui.Button;

public class MouseManager extends MouseAdapter implements MouseMotionListener{

	public static int mouseX, mouseY;
	private static ArrayList<Button> buttons = new ArrayList<Button>(); // All the buttons whose state we have to track
	
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
				
		for(Button button: buttons) {
			int buttonX = (int)button.getX();
			int buttonY = (int)button.getY();
			int buttonWidth = button.getWidth();
			int buttonHeight = button.getHeight();
			
			if(mouseX > buttonX && mouseX < buttonX + buttonWidth && mouseY > buttonY && mouseY < buttonY + buttonHeight)
				button.isOver = true;
			else 
				button.isOver = false;
		}
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		for(Button button: buttons) {
			int buttonX = (int)button.getX();
			int buttonY = (int)button.getY();
			int buttonWidth = button.getWidth();
			int buttonHeight = button.getHeight();
			
			
			if(mouseX > buttonX && mouseX < buttonX + buttonWidth && mouseY > buttonY && mouseY < buttonY + buttonHeight)
				button.isClicked = true;
			else
				button.isClicked = false;
		}
	}
	
	public static void addButtonsToListen(Button button) {
		buttons.add(button);
	}
}
