package com.game.ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ButtonComponent {
	private int x, y, width, height;
	public BufferedImage buttonImage;
	
	public boolean isOver = false;
	public boolean isClicked = false;
	
	public ButtonComponent(BufferedImage image, int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		buttonImage = image;
	}
	
	public void tick() {
	}
	
	public void render(Graphics2D g2d) {
		g2d.drawImage(buttonImage, x, y, width, height, null);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
