package com.ruby2d.entities;

import java.awt.Graphics;

import com.ruby2d.graphics.Assets;
import com.ruby2d.graphics.ImageLoader;
import com.ruby2d.util.ClipRect;
import com.ruby2d.util.KeyManager;

public class Player extends Entity {
	// Player animation
	private ClipRect[] leftAnimationRects, rightAnimationRects, upAnimationRects, downAnimationRects;
	private ClipRect currentClipRect;
	
	private float animationSpeed, animationCounter;
	
	public Player(float x, float y, float width, float height) {
		super(x, y, width, height);
		
		leftAnimationRects = new ClipRect[2];
		leftAnimationRects[0] = new ClipRect(16*2, 0, 16, 16);
		leftAnimationRects[1] = new ClipRect(16*3, 0, 16, 16);

		rightAnimationRects = new ClipRect[2];
		rightAnimationRects[0] = new ClipRect(16*4, 0, 16, 16);
		rightAnimationRects[1] = new ClipRect(16*5, 0, 16, 16);

		upAnimationRects = new ClipRect[2];
		upAnimationRects[0] = new ClipRect(16*6, 0, 16, 16);
		upAnimationRects[1] = new ClipRect(16*7, 0, 16, 16);

		downAnimationRects = new ClipRect[2];
		downAnimationRects[0] = new ClipRect(0, 0, 16, 16);
		downAnimationRects[1] = new ClipRect(16, 0, 16, 16);
		
		currentClipRect = downAnimationRects[0];
		
		animationSpeed = 0.1f;
		animationCounter = 0.0f;
	}
	
	private ClipRect chooseAnimationRect(ClipRect[] rects) {
		animationCounter += animationSpeed;

		int index = (int)Math.round(Math.abs(Math.sin(animationCounter))); // Always gives a value between 0 and 1

		if(animationCounter > 1000) {
			animationCounter = 0.0f;
		}
		return rects[index];
	}
	@Override
	public void tick() {
		
		if(KeyManager.UP) {
			currentClipRect = chooseAnimationRect(upAnimationRects);
		}
		if(KeyManager.DOWN) {
			currentClipRect = chooseAnimationRect(downAnimationRects);
		}
		if(KeyManager.LEFT) {
			currentClipRect = chooseAnimationRect(leftAnimationRects);
		}
		if(KeyManager.RIGHT) {
			currentClipRect = chooseAnimationRect(rightAnimationRects);
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(ImageLoader.getSubImage(Assets.playerSprite, currentClipRect.x, currentClipRect.y, 16, 16), (int)x, (int)y, (int)width, (int)height, null);
	}
}
