package com.ruby2d.tiles;

import java.awt.image.BufferedImage;

import com.ruby2d.graphics.Assets;

public class TreeTile extends CollideableTile{

	public static int tileID;
	public static BufferedImage tileImage = Assets.grassTile;
	
	public TreeTile(float x, float y) {
		super(x, y);
	}
	

}
