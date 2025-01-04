package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

	// this player class will use GamePanel and KeyHandler classes
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public int screenY; // this is not final because we NEED to chaange this value to be able to
						// JUMP!!!!
	
	public boolean bluePotion;
	public int stars;
	
	
	
	public BufferedImage right1, right2, left1, left2;
	public int jumpSpeed = 6;
	public boolean sideCollision;
	private boolean isJumping = false;
	public boolean canJump; // if this is true the player is touching the ground
	private int jumpCounter = 0;



	public Player(GamePanel gp, KeyHandler keyH) {

		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.tileSize * 3;
		screenY = (gp.tileSize * 5);

		solidArea = new Rectangle(6, 2, 30, 42); // when we instantiate this rectangle, we can
															// add 4 parameters x, y, width, height
		
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		setDefaultValues();
		getPlayerImage();
	}

	// fucntion to set default values
	public void setDefaultValues() {
		worldX = gp.tileSize * 4;
		worldY = screenY;
		speed = 3;
		type = "player";
		verticalDirection = "down";
		horizontalDirection = "right";
	}

	// function to get the player images
	public void getPlayerImage() {

		try {
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/character_right.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/character_left.png"));
			

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	// the update function that updates values like players position on the map,
	// jump variables
	public void update() {
		sideCollision = false;
		upDownCollision = false;
		canJump = false;

		// CHECK FOR sideCollision, collisionOn, and canJump
		gp.cCheckerPlayer.checkTile(this); // checks for collisionOn and canJump
		// CHECK OBJECTS COLLISION
		int objIndex = gp.cCheckerPlayer.checkObject(this);
		pickUpObject(objIndex);
		
		if (keyH.leftPressed == true) {
			if (horizontalDirection == "right" && sideCollision) {
				sideCollision = false;
			}
			
			if (!sideCollision) {
				horizontalDirection = "left";
				worldX -= speed;
			}
			
		}
		
		else if (keyH.rightPressed == true) {
			if (horizontalDirection == "left" && sideCollision) {
				sideCollision = false;
			}
			
			if (!sideCollision) {
				horizontalDirection = "right";
				worldX += speed;
			}
			
		}
		

		if (!isJumping && canJump) {
			if (keyH.upPressed == true) {
				gp.playSoundEffect(1);
				verticalDirection = "up";
				isJumping = true;
				canJump = false;
			}
		}
		
	
		if (isJumping) { // if the status is jumping then.... well do something
			// if collsion is false, player can move
			if (!upDownCollision) {
				screenY -= jumpSpeed;
			}

			jumpCounter++; // increase the jump counter by 1

			if (jumpCounter >= 20) {
				jumpCounter = 0;
//				gp.playSoundEffect(2);
				isJumping = false;
				verticalDirection = "down";
			}
		}

		else { // if not do this(fall down)
			if (!upDownCollision) {
				screenY += jumpSpeed;
			}
		}
		
		if (worldX >= 3600) {
			gp.ui.gameFinished = true;
			gp.stopMusic();
			gp.playSoundEffect(4);
//			worldX = gp.tileSize * 4;
		}

		// for the animation of the sprites
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
	
	
	public void pickUpObject(int i) {
		
		if (i != 999) {
			
			String objectName = gp.obj[i].name;
			
			switch (objectName) {
			case "Blue_Potion":
				gp.playSoundEffect(3);
				bluePotion = true;
				gp.ui.showMessage("Wow you got a potion");
				gp.obj[i] = null;
				
				speed += 1;
				
				break;
				
			case "Star":
				stars ++;
				gp.obj[i] = null;
				gp.ui.showMessage("star +1");
				break;
			}
		}
		
	}
	
	public void draw(Graphics2D g2) {

		BufferedImage image = null;
		
		
//		################## FIX THIS ANIMATION LATER
//		switch (verticalDirection) {
//		// depending on the direction(jumping or not) change the sprites + its animation
//		case "up":
//			if (spriteNum == 1) {
//				image = jump1;
//			} else if (spriteNum == 2) {
//				image = jump2;
//			}
//
//			break;
//
//		case "down":
//			if (spriteNum == 1) {
//				image = run1;
//			}
//
//			else if (spriteNum == 2) {
//				image = run2;
//			}
//			
//			break;
//		}
		
		switch (horizontalDirection) {
		// depending on the direction(jumping or not) change the sprites + its animation
		case "left":
			if (spriteNum == 1) {
				image = left1;
			}

			else if (spriteNum == 2) {
				image = left1;
			}
			break;

		case "right":
			if (spriteNum == 1) {
				image = right1;
			}

			else if (spriteNum == 2) {
				image = right1;
			}
			
			break;
		}

		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null); // this(null) is called an image
																				// observer, we can
		// just type null here
		// drawing image, at x and y positions, of size gp.tileSize(width) and
		// gp.tileSize(height)
		
	
		g2.drawRect(screenX +  solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
		g2.drawRect(screenX + gp.tileSize, screenY + solidArea.y, gp.tileSize, gp.tileSize);
	}
}