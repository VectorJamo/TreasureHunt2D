package com.ruby2d.entities;

import java.awt.Graphics;

import com.ruby2d.graphics.Assets;
import com.ruby2d.util.Camera;

public class Key extends Item {

	public Key(float x, float y, float width, float height, Camera camera) {
		super(x, y, width, height, camera);
	}

	@Override
	public void tick() {
		if(isActive) {
			this.x = this.initialX - camera.getXOffset();
			this.y = this.initialY - camera.getYOffset();
		}
	}

	@Override
	public void render(Graphics g) {
		if(isActive) {
			g.drawImage(Assets.keyItemImage,(int)x, (int)y, (int)width, (int)height, null);
		}
	}

}
