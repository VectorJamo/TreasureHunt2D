package com.ruby2d.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;

public class Button extends Text{
	
	private Color buttonColor;
	private float padX, padY;
	
	public boolean isOver, isClicked;
	public Button(String text, float x, float y, Font font) {
		super(text, x, y, font);
		
		buttonColor = Color.BLACK;
		padX = 0;
		padY = 0;
		isOver = false;
		isClicked = false;
	}
	
	public void setButtonColor(Color c) {
		buttonColor = c;
	}
	public void setButtonText(String text) {
		super.text = text;
	}
	public void setPaddingX(float padX) {
		this.padX = padX;
	}
	public void setPaddingY(float padY) {
		this.padY = padY;
	}
		
	public void render(Graphics2D g2d) {
		g2d.setColor(buttonColor);
		g2d.fillRect((int)getX() - (int)padX, (int)getY() - (int)padY, width + 2*(int)padX, height + 2*(int)padY);
		super.render(g2d);
	}
}
