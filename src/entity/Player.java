package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


import main.GamePanel;
import main.KeyHandler;


public class Player extends Entity {

	// this player class will use GamePanel and KeyHandler classes
	KeyHandler keyH;

	public final int screenX;
	public int screenY; // this is not final because we NEED to chaange this value to be able to
						// JUMP!!!!


	
	public int jumpSpeed = 6;
	public boolean sideCollision;
	private boolean isJumping = false;
	public boolean canJump; // if this is true the player is touching the ground
	private int jumpCounter = 0;

	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp);
		
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
		speed = 5;
		type = "player";
		verticalDirection = "down";
		horizontalDirection = "right";
	}

	// function to get the player images
	public void getPlayerImage() {

		right1 = setup("/player/boy_right_1");
		right2 = setup("/player/boy_right_2");
		left1 = setup("/player/boy_left_1");
		left2 = setup("/player/boy_left_2");
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

		if (keyH.upPressed || keyH.rightPressed || keyH.leftPressed) {
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
			gp.stopMusic();
			gp.ui.gameFinished = true;

			
		}

	}

	public void pickUpObject(int i) {

		if (i != 999) {

			String objectName = gp.obj[i].name;

			switch (objectName) {
			case "Blue_Potion":
				gp.playSoundEffect(3);
				gp.ui.messageOn = true;
				gp.ui.showMessage("Speed++");
				gp.obj[i] = null;

				speed ++;

				break;

			case "Star":
				gp.obj[i] = null;
				gp.ui.messageOn = true;
				gp.ui.showMessage("star +1");
				break;
			}
		}

	}

	public void draw(Graphics2D g2) {

		BufferedImage image = null;


		switch (horizontalDirection) {
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

		g2.drawImage(image, screenX, screenY, null); // this(null) is called an image
														// observer, we can
		// just type null here
		// drawing image, at x and y positions, of size gp.tileSize(width) and
		// gp.tileSize(height)

		g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
		g2.drawRect(screenX + gp.tileSize, screenY + solidArea.y, gp.tileSize, gp.tileSize);
	}
}