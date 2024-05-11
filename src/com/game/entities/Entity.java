package com.game.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.game.gfx.ImageLoader;
import com.game.util.ClipRect;

public abstract class Entity {
	protected int worldX, worldY, screenX, screenY;
	protected int width, height;
	protected int speed;
	
	public BufferedImage entityImage;
	
	public ClipRect collisionRect;
	
	public Entity(int worldX, int worldY, int width, int height, int speed, String path){
		this.worldX = worldX;
		this.worldY = worldY;
		this.width = width;
		this.height = height;
		this.speed = speed;
		
		entityImage = ImageLoader.loadImage(path);
		
		collisionRect = new ClipRect();
		collisionRect.x = 0;
		collisionRect.y = 0;
		collisionRect.width = width;
		collisionRect.height = height;
	}
	
	public abstract void tick();
	public abstract void render(Graphics2D g2d);

	public void setCollisionRect(int x, int y, int width, int height) {
		collisionRect = new ClipRect();
		collisionRect.x = x;
		collisionRect.y = y;
		collisionRect.width = width;
		collisionRect.height = height;
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

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getScreenX() {
		return screenX;
	}
	public int getScreenY() {
		return screenY;
	}
}
