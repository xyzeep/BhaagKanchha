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

	public BufferedImage run1, run2, jump1, jump2;
	public int jumpSpeed = 7;
	public boolean leftCollision;
	private boolean isJumping = false;
	public boolean canJump; // if this is true the player is touching the ground
	private int jumpCounter = 0;

	public final int screenX;
	public int screenY; // this is not final because we NEED to chaange this value to be able to
						// JUMP!!!!

	public Player(GamePanel gp, KeyHandler keyH) {

		this.gp = gp;
		this.keyH = keyH;

		screenX = gp.tileSize * 3;
		screenY = (gp.tileSize * 5) + 15;

		solidArea = new Rectangle(7, 0, 34, gp.tileSize); // when we instantiate this rectangle, we can
															// add 4 parameters x, y, width, height

		setDefaultValues();
		getPlayerImage();
	}

	// fucntion to set default values
	public void setDefaultValues() {
		worldX = gp.tileSize * 3;
		worldY = screenY;
		speed = 4;
		type = "player";
		direction = "down";
	}

	// function to get the player images
	public void getPlayerImage() {

		try {
			run1 = ImageIO.read(getClass().getResourceAsStream("/player/character.png"));
			run2 = ImageIO.read(getClass().getResourceAsStream("/player/character.png"));
			jump1 = ImageIO.read(getClass().getResourceAsStream("/player/character.png"));
			jump2 = ImageIO.read(getClass().getResourceAsStream("/player/character.png"));
			// jump3 = ImageIO.read(getClass().getResourceAsStream("/player/jump3.png"));

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	// the update function that updates values like players position on the map,
	// jump variables
	public void update() {
		leftCollision = false;
		collisionOn = false;
		canJump = false;

		// CHECK FOR leftCollision, collisionOn, and canJump
		gp.cCheckerPlayer.checkTile(this); // checks for collisionOn and canJump

		if (!leftCollision) {
			worldX += speed; // always moving forward
			if (worldX > 3750) { // this if statement is just for now because i dont want the game to just stop
									// when the map ends
				worldX = gp.tileSize * 3;
			}
		}

		if (isJumping == false && canJump) {
			if (keyH.upPressed == true) {
				System.out.println("JUMP PRESSED");
				direction = "up";
				isJumping = true;
				canJump = false;
			}
		}

		if (isJumping) { // if the status is jumping then.... well do something
			// if collsion is false, player can move
			if (!collisionOn) {
				screenY -= jumpSpeed;
			}

			jumpCounter++; // increase the jump counter by 1

			if (jumpCounter >= 20) {
				jumpCounter = 0;
				isJumping = false;
				direction = "down";
			}
		}

		else { // if not do this(fall down)
			if (!collisionOn) {
				screenY += jumpSpeed;
			}
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

	public void draw(Graphics2D g2) {

		BufferedImage image = null;

		switch (direction) {
		// depending on the direction(jumping or not) change the sprites + its animation
		case "up":
			if (spriteNum == 1) {
				image = jump1;
			} else if (spriteNum == 2) {
				image = jump2;
			}

			break;

		case "down":
			if (spriteNum == 1) {
				image = run1;
			}

			else if (spriteNum == 2) {
				image = run2;
			}
			
			break;
		}

		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null); // this(null) is called an image
																				// observer, we can
		// just type null here
		// drawing image, at x and y positions, of size gp.tileSize(width) and
		// gp.tileSize(height)

	}
}