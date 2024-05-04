package com.ruby2d.tiles;

import java.awt.image.BufferedImage;

public abstract class CollideableTile {
	public float initialX, initialY;
	public float x, y;

	public CollideableTile(float x, float y) {
		this.x = x;
		this.y = y;
		this.initialX = x;
		this.initialY = y;
	}
}
