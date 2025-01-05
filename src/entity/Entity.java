package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {

	GamePanel gp;

	public int worldX, worldY; // players/entity's postition in the world map
	public int screenX, screenY; // only applicable for player (until now)
	public int speed;
	public boolean collisionOn;
	public boolean move = true;
	public String type;
	public int moveCounter = 0;
	public String direction = "right";
	public BufferedImage right1, right2, left1, left2;
	public String verticalDirection;
	public String horizontalDirection;
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);; // the actual colliding part
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean upDownCollision = false;

	public int actionLockCounter = 0;

	public Entity(GamePanel gp) {
		this.gp = gp;

	}

	public void setAction() {

	}

	public void update() {
		setAction(); // in NPC_Bob.java

//		collisionOn = false;
////		gp.cChecker.checkTile(this);

		
		
	

			if (collisionOn == false) {
				switch (direction) {
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
				

		}
		spriteCounter++;

		if (spriteCounter > 12) // adjust this value if you want to customize animation speed okay?
		{
			if (spriteNum == 1) {
				spriteNum = 2;
			} else if (spriteNum == 2) {
				spriteNum = 1;
			}

			spriteCounter = 0;
		}
	}

	public void draw(Graphics2D g2) {

		BufferedImage image = left1;
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.screenY + gp.player.screenY;

		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
				&& worldX < gp.player.worldX + (gp.screenWidth - gp.player.screenX)) {

			switch (direction) {
			// depending on the direction(jumping or not) change the sprites + its animation
			case "left":
				if (spriteNum == 1) {
					image = left1;

				}

				else if (spriteNum == 2) {
					image = left2;

				}

				break;

			case "right":
				if (spriteNum == 1) {
					image = right1;
				}

				else if (spriteNum == 2) {
					image = right2;
				}

				break;
			}
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
	}

	public BufferedImage setup(String imagePath) {

		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}

}
