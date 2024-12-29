package main;

import entity.Player;

public class CollisionCheckerPlayer {

	GamePanel gp;

	public CollisionCheckerPlayer(GamePanel gp) {
		this.gp = gp;
	}

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

		switch (player.direction) {
		case "up":
			playerTopRow = (playerTopScreenY - player.jumpSpeed + 5) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[playerLeftCol][playerTopRow];
			tileNum2 = gp.tileM.mapTileNum[playerRightCol][playerTopRow];
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				player.collisionOn = true; // if, when going up, the entity hits any one of the two tiles, collisionOn
											// is true
			}

			break;
		case "down":
			playerBottomRow = (playerBottomScreenY + player.jumpSpeed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[playerLeftCol][playerBottomRow];
			tileNum2 = gp.tileM.mapTileNum[playerRightCol][playerBottomRow];
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				player.collisionOn = true; // if, when going down, the entity hits any one of the two tiles, collisionOn
											// is true
				player.canJump = true;
				break;

			}

		}

	}
}
