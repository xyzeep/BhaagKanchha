package main;

//import entity.Entity;
import entity.Player;

public class CollisionCheckerPlayer {

	GamePanel gp;

	public CollisionCheckerPlayer(GamePanel gp) {
		this.gp = gp;
	}

	// function to check if the tile around the player has collision
	public void checkTile(Player player) {

		int playerLeftWorldX = player.worldX + player.solidArea.x;
		int playerRightWorldX = player.worldX + player.solidArea.x + player.solidArea.width;
		int playerTopScreenY = player.screenY + player.solidArea.y;
		int playerBottomScreenY = player.screenY + player.solidArea.y + player.solidArea.height;

		int playerLeftCol = playerLeftWorldX / gp.tileSize;
		int playerRightCol = playerRightWorldX / gp.tileSize;
		int playerTopRow = playerTopScreenY / gp.tileSize;
		int playerBottomRow = playerBottomScreenY / gp.tileSize;

		int tileNum1, tileNum2, tileNum3, tileNum4; // tileNum1 and tileNum2 are for up and down directions. tileNum3
													// and tileNum4 are for right side tiles
		playerRightCol = (playerRightWorldX + player.speed) / gp.tileSize;
		tileNum3 = gp.tileM.mapTileNum[playerRightCol][playerTopRow];
		tileNum4 = gp.tileM.mapTileNum[playerRightCol][playerBottomRow];
		if (gp.tileM.tile[tileNum3].collision == true || gp.tileM.tile[tileNum4].collision == true) {
			player.leftCollision = true; // if, when going down, the entity hits any one of the two tiles, collisionOn
											// is true
		}

		if (gp.tileM.tile[tileNum4].deadly) {
//			player.worldX = gp.tileSize * 3;
			System.out.println("oops you died");
		}

		switch (player.direction) {
		case "up":
			playerRightCol = playerRightWorldX / gp.tileSize;
			playerTopRow = (playerTopScreenY - player.jumpSpeed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[playerLeftCol][playerTopRow];
			tileNum2 = gp.tileM.mapTileNum[playerRightCol][playerTopRow];
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				player.collisionOn = true; // if, when going up, the entity hits any one of the two tiles, collisionOn
											// is true
			}
			if (gp.tileM.tile[tileNum2].deadly) {
//				player.worldX = gp.tileSize * 3;
				System.out.println("oops you died");
			}

			break;
		case "down":
			playerRightCol = playerRightWorldX / gp.tileSize;
			playerBottomRow = (playerBottomScreenY + player.jumpSpeed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[playerLeftCol][playerBottomRow];
			tileNum2 = gp.tileM.mapTileNum[playerRightCol][playerBottomRow];
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				player.collisionOn = true; // if, when going down, the entity hits any one of the two tiles, collisionOn
											// is true
				player.canJump = true;
			}
			if (gp.tileM.tile[tileNum2].deadly) {
//				player.worldX = gp.tileSize * 3;
				System.out.println("oops you died");
			}
			break;

		}

	}

	public int checkObject(Player player) {

		int index = 999;
		
		for (int i = 0; i < gp.obj.length; i++) {
			
			if (gp.obj[i] != null) {
				
				// get entity's/player's solid area position
				player.solidArea.x = player.worldX + player.solidArea.x;
				player.solidArea.y = player.worldY + player.solidArea.y;
				
				// get the object solid area position
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
				
				// if it's going right (Which is always true)
				player.solidArea.x += player.speed;
				if (player.solidArea.intersects(gp.obj[i].solidArea)) {
					if (gp.obj[i].collision) {
						player.leftCollision = true;
					}
					
					index = i;
				}
				
				
				switch (player.direction) {
				// simulating players movement and checking where it will be after it moves
				case "up":
					player.solidArea.y -= player.jumpSpeed;
					if (player.solidArea.intersects(gp.obj[i].solidArea)) {
						if (gp.obj[i].collision) {
							player.collisionOn = true;
						}
						
						index = i;
					}
					break;
				case "down":
					player.solidArea.y += player.jumpSpeed;
					if (player.solidArea.intersects(gp.obj[i].solidArea)) {
						if (gp.obj[i].collision) {
							player.collisionOn = true;
						}
						index = i;
					}
					
					break;
				}
				
				// resetting to default
				
				player.solidArea.x = player.solidAreaDefaultX;
				player.solidArea.y = player.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
				
			}
			
		}
		
		return index;
	}
}
