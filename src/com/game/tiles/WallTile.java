package com.game.tiles;

import com.game.gfx.ImageLoader;

public class WallTile extends Tile{
	public WallTile() {
		tileImage = ImageLoader.loadImage("/images/tiles/wall.png");
	}

}
