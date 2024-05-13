package com.game.items;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.game.gfx.ImageLoader;

public abstract class Item {
	
	protected BufferedImage itemImage;
	protected int worldX, worldY;
	protected int width, height;
	
	public Item(String path, int worldX, int worldY, int width, int height) {
		this.worldX = worldX;
		this.worldY = worldY;
		this.width = width;
		this.height = height;
		
		itemImage = ImageLoader.loadImage(path);
	}
	
	public int getWorldX() {
		return worldX;
	}

	public void setWorldX(int worldX) {
		this.worldX = worldX;
	}

	public int getWorldY() {
		return worldY;
	}

	public void setWorldY(int worldY) {
		this.worldY = worldY;
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

	public abstract void tick();
	public abstract void render(Graphics2D g2d);
}
