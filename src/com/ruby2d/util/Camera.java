package com.ruby2d.util;

import com.ruby2d.entities.Player;

public class Camera {
	
	private float xOffset, yOffset;
	public static float cameraSpeed = 3.0f;
	
	public Camera(float xOffset, float yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public void setXOffset(float xOffset) {
		this.xOffset = xOffset;
	}
	
	public void setYOffset(float yOffset) {
		this.yOffset = yOffset;
	}

	public float getXOffset() {
		return xOffset;
	}
	
	public float getYOffset() {
		return yOffset;
	}
}
