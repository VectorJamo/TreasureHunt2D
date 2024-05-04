package com.ruby2d.entities;

import java.awt.Graphics;

import com.ruby2d.graphics.Assets;
import com.ruby2d.graphics.ImageLoader;
import com.ruby2d.util.Camera;

public class OldMan extends Entity{
	
	private Camera camera;
	private float initialX, initialY;
	
	public boolean isVisible;

	public OldMan(float x, float y, float width, float height, Camera camera) {
		super(x, y, width, height);
		initialX = x;
		initialY = y;
		
		this.camera = camera;
		isVisible = true;
	}

	@Override
	public void tick() {
		if(isVisible) {
			this.x = this.initialX - camera.getXOffset();
			this.y = this.initialY - camera.getYOffset();
		}
	}

	@Override
	public void render(Graphics g) {
		if(isVisible) {
			g.drawImage(ImageLoader.getSubImage(Assets.oldManSprite, 0, 0, 16, 16), (int)x, (int)y, (int)width, (int)height, null);
		}
	}

}
