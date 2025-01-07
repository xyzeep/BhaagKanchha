package main;

import entity.Entity;
import entity.Player;
//import entity.Entity;

public class CollisionChecker { 

	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	// function to check if the tile around the player has collision
	public void checkTile(Entity entity) {

		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
	    int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
	    int entityTopScreenY = entity.screenY + entity.solidArea.y;
	    int entityBottomScreenY = entity.screenY + entity.solidArea.y + entity.solidArea.height;
	    

	    
	    
	    int entityLeftCol = entityLeftWorldX / gp.tileSize;
	    int entityRightCol = (entityRightWorldX - 1) / gp.tileSize;  // Subtract 1 to avoid crossing into next tile
	    int entityTopRow = entityTopScreenY / gp.tileSize;
	    int entityBottomRow = (entityBottomScreenY - 1) / gp.tileSize;  // Subtract 1 to avoid crossing into next tile
	


		int tileNum1, tileNum2, tileNum3, tileNum4; // tileNum1 and tileNum2 are for up and down directions. tileNum3
		// and tileNum4 are for left right side tiles

		switch (entity.verticalDirection) {
		case "up":
			entityTopRow = (entityTopScreenY - entity.jumpSpeed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.upDownCollision = true; // if, when going up, the entity hits any one of the two tiles,
												// collisionOn
												// is true
			}
			if (gp.tileM.tile[tileNum2].deadly) {
				// die function
			}

			break;
		case "down":
			
			entityBottomRow = (entityBottomScreenY + entity.jumpSpeed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				 entity.upDownCollision = true;
				 entity.canJump = true;
			}
			if (gp.tileM.tile[tileNum2].deadly) {
				// die function
			}
			break;

		}
		
		
		
		// Re-assign values
		entityLeftWorldX = entity.worldX + entity.solidArea.x;
	    entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
	    entityTopScreenY = entity.screenY + entity.solidArea.y;
	    entityBottomScreenY = entity.screenY + entity.solidArea.y + entity.solidArea.height;
	    
	    entityLeftCol = entityLeftWorldX / gp.tileSize;
	    entityRightCol = (entityRightWorldX - 1) / gp.tileSize;  // Subtract 1 to avoid crossing into next tile
	    entityTopRow = entityTopScreenY / gp.tileSize;
	    entityBottomRow = (entityBottomScreenY - 1) / gp.tileSize;  // Subtract 1 to avoid crossing into next tile
		
		switch (entity.horizontalDirection) {
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
			tileNum3 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow]; // Check Left Hand
			tileNum4 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow]; // Check Right Hand
			if (gp.tileM.tile[tileNum3].collision == true || gp.tileM.tile[tileNum4].collision == true) {
				entity.sideCollision = true;
			}
			break;

		case "right":
			entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
			tileNum3 = gp.tileM.mapTileNum[entityRightCol][entityTopRow]; // Check Left Hand
			tileNum4 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow]; // Check Right Hand
			if (gp.tileM.tile[tileNum3].collision == true || gp.tileM.tile[tileNum4].collision == true) {
				entity.sideCollision = true;
			}
			break;
		}
		// resetting these values
		entityLeftCol = entityLeftWorldX / gp.tileSize;
		entityRightCol = entityRightWorldX / gp.tileSize;

	}
	
	
	

	public int checkObject(Entity entity) {

		int index = 999;

		for (int i = 0; i < gp.obj.length; i++) {

			if (gp.obj[i] != null) {

				// get entity's/entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidAreaDefaultX;
				entity.solidArea.y = entity.screenY + entity.solidAreaDefaultY;
				// get the object solid area position
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidAreaDefaultY;

				switch (entity.verticalDirection) {
				// simulating entitys movement and checking where it will be after it moves
				case "up":
					entity.solidArea.y -= entity.jumpSpeed / 2;
					if (entity.solidArea.intersects(gp.obj[i].solidArea)) {

						if (gp.obj[i].collision) {
							entity.upDownCollision = true;
						}

						index = i;
					}
					break;

				case "down":
					entity.solidArea.y += entity.jumpSpeed / 2;
					if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if (gp.obj[i].collision) {
							entity.upDownCollision = true;
						}

						index = i;
					}

					break;
				}

				// resetting to default solid area x and y
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;

				// get entity's/entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidAreaDefaultX;
				entity.solidArea.y = entity.screenY + entity.solidAreaDefaultY;
				// get the object solid area position
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidAreaDefaultY;

				switch (entity.horizontalDirection) {
				// simulating entitys movement and checking where it will be after it moves
				case "left":
					entity.solidArea.x -= entity.speed;
					if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if (gp.obj[i].collision) {
							entity.sideCollision = true;
						}
						index = i;
					}
					break;

				case "right":
					entity.solidArea.x += entity.speed;
					if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if (gp.obj[i].collision) {
							entity.sideCollision = true;
						}

						index = i;
					}

					break;
				}

				// resetting to default solid area x and y
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;

			}

		}

		return index;
	}
	
	// NPC OR MONSTER COLLISION
	public int checkEntity(Entity entity, Entity[] target) {
		return 1;
	}
	
}