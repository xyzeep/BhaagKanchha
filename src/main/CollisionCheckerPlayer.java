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
		switch (player.horizontalDirection) {
		case "left":
			playerLeftCol = (playerLeftWorldX - player.speed) / gp.tileSize;
			tileNum3 = gp.tileM.mapTileNum[playerLeftCol][playerTopRow];
			tileNum4 = gp.tileM.mapTileNum[playerLeftCol][playerBottomRow];
			if (gp.tileM.tile[tileNum3].collision == true || gp.tileM.tile[tileNum4].collision == true) {
				player.sideCollision = true;
			}

			if (gp.tileM.tile[tileNum4].deadly) {
				// die function
			}
			break;

		case "right":
			playerRightCol = (playerRightWorldX + player.speed) / gp.tileSize;
			tileNum3 = gp.tileM.mapTileNum[playerRightCol][playerTopRow];
			tileNum4 = gp.tileM.mapTileNum[playerRightCol][playerBottomRow];
			if (gp.tileM.tile[tileNum3].collision == true || gp.tileM.tile[tileNum4].collision == true) {
				player.sideCollision = true;
			}

			if (gp.tileM.tile[tileNum4].deadly) {
				// die function
			}
		}
		// resetting these values
		playerLeftCol = playerLeftWorldX / gp.tileSize;
		playerRightCol = playerRightWorldX / gp.tileSize;

		switch (player.verticalDirection) {
		case "up":
			playerTopRow = (playerTopScreenY - player.jumpSpeed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[playerLeftCol][playerTopRow];
			tileNum2 = gp.tileM.mapTileNum[playerRightCol][playerTopRow];
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				player.upDownCollision = true; // if, when going up, the entity hits any one of the two tiles,
												// collisionOn
												// is true
			}
			if (gp.tileM.tile[tileNum2].deadly) {
				// die function
			}

			break;
		case "down":
			playerBottomRow = (playerBottomScreenY + player.jumpSpeed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[playerLeftCol][playerBottomRow];
			tileNum2 = gp.tileM.mapTileNum[playerRightCol][playerBottomRow];
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				player.upDownCollision = true; // if, when going down, the entity hits any one of the two tiles,
												// collisionOn
												// is true
				player.canJump = true;
			}
			if (gp.tileM.tile[tileNum2].deadly) {
				// die function
			}
			break;

		}

	}

	public int checkObject(Player player) {

		int index = 999;

		for (int i = 0; i < gp.obj.length; i++) {

			if (gp.obj[i] != null) {

				// get entity's/player's solid area position
				player.solidArea.x = player.worldX + player.solidAreaDefaultX;
				player.solidArea.y = player.screenY + player.solidAreaDefaultY;
				// get the object solid area position
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidAreaDefaultY;

				switch (player.verticalDirection) {
				// simulating players movement and checking where it will be after it moves
				case "up":
					player.solidArea.y -= player.jumpSpeed / 2;
					if (player.solidArea.intersects(gp.obj[i].solidArea)) {
						System.out.println("up happen");
						if (gp.obj[i].collision) {
							player.upDownCollision = true;
						}

						index = i;
					}
					break;

				case "down":
					player.solidArea.y += player.jumpSpeed / 2;
					if (player.solidArea.intersects(gp.obj[i].solidArea)) {
						System.out.println("down happen");
						if (gp.obj[i].collision) {
							player.upDownCollision = true;
						}

						index = i;
					}

					break;
				}

				// resetting to default solid area x and y
				player.solidArea.x = player.solidAreaDefaultX;
				player.solidArea.y = player.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;

				// get entity's/player's solid area position
				player.solidArea.x = player.worldX + player.solidAreaDefaultX;
				player.solidArea.y = player.screenY + player.solidAreaDefaultY;
				// get the object solid area position
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidAreaDefaultY;

				switch (player.horizontalDirection) {
				// simulating players movement and checking where it will be after it moves
				case "left":
					player.solidArea.x -= player.speed;
					if (player.solidArea.intersects(gp.obj[i].solidArea)) {
						System.out.println("left happen");
						if (gp.obj[i].collision) {
							player.sideCollision = true;
						}
						index = i;
					}
					break;

				case "right":
					player.solidArea.x += player.speed;
					if (player.solidArea.intersects(gp.obj[i].solidArea)) {
						System.out.println("right happen");
						if (gp.obj[i].collision) {
							player.sideCollision = true;
						}

						index = i;
					}

					break;
				}

				// resetting to default solid area x and y
				player.solidArea.x = player.solidAreaDefaultX;
				player.solidArea.y = player.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;

			}

		}

		return index;
	}
}
