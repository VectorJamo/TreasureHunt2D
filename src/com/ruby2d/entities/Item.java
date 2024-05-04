package com.ruby2d.entities;

import com.ruby2d.util.Camera;
import com.ruby2d.util.ClipRect;

public abstract class Item extends Entity {

	protected ClipRect[] animationClipRects;
	protected float animationSpeed;
	protected float animationCounter;
	
	protected Camera camera;
	public boolean isActive;
	
	protected float initialX, initialY;
	
	public Item(float x, float y, float width, float height, Camera camera) {
		super(x, y, width, height);
		initialX = x;
		initialY = y;
		
		this.camera = camera;
		isActive = true;
	}
}
