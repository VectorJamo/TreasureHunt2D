package com.game.entities;

import com.game.tiles.CollideableTile;
import com.game.tiles.TileMap;

public class CollisionHandler {
	
	public static CollisionAxes checkCollision(Entity entity, CollideableTile tile, int dx, int dy) {
		CollisionAxes type = new CollisionAxes();
		
		type.xCollision = true;
		type.yCollision = true;
		
		// Get Entity's Collision Rect's world coordinates
		int entityX = entity.getWorldX() + entity.collisionRect.x;
		int entityY = entity.getWorldY() + entity.collisionRect.y;
		int entityWidth = entity.collisionRect.width;
		int entityHeight = entity.collisionRect.height;
		
		// First set the player's X to the new X and check if collision occurs,
		float entityXNew = entityX + dx;
		if(entityXNew > tile.x + TileMap.tileSize || entityXNew + entityWidth < tile.x || entityY + entityHeight < tile.y || 
				entityY > tile.y + TileMap.tileSize) {
			type.xCollision = false;
		}
		// Then, set the player's Y to the new Y and check if collision occurs
		float entityYNew = entityY + dy;
		if(entityX > tile.x + TileMap.tileSize || entityX + entityWidth < tile.x || 
				entityYNew + entityHeight < tile.y || entityYNew > tile.y + TileMap.tileSize) {
			type.yCollision = false;
		}
		return type;
	}
}
