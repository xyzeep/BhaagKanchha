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
	private boolean isJumping = false;
	public  boolean canJump; // if this is true the player is touching the ground
	private int jumpCounter = 0;

	public final int screenX;
	public int screenY; // this is not final because we NEED to chaange this value to be able to
						// JUMP!!!!

	public Player(GamePanel gp, KeyHandler keyH) {

		this.gp = gp;
		this.keyH = keyH;

		screenX = gp.tileSize * 3;
		screenY = (gp.tileSize * 5) + 15;

		solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize); // when we instantiate this rectangle, we can
																			// add 4
		// parameters x, y, width, height

		setDefaultValues();
		getPlayerImage();
	}

	public void setDefaultValues() {
		worldX = gp.tileSize * 3;
		worldY = screenY;
		speed = 4;
		type = "player";
		direction = "down";
	}

	public void getPlayerImage() {

		try {
			run1 = ImageIO.read(getClass().getResourceAsStream("/player/character.png"));
			run2 = ImageIO.read(getClass().getResourceAsStream("/player/character.png"));
			jump1 = ImageIO.read(getClass().getResourceAsStream("/player/character.png"));
			jump2 = ImageIO.read(getClass().getResourceAsStream("/player/character.png"));
//            jump3 = ImageIO.read(getClass().getResourceAsStream("/player/jump3.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void update() {

		worldX += speed; // always moving forward 

		// CHECK TILE COLLISION
		collisionOn = false;
		canJump = false;
		
		gp.cCheckerPlayer.checkTile(this); // checks for collisionOn and canJump

		if (isJumping == false && canJump) {
			if (keyH.upPressed == true) {
				System.out.println("JUMP PRESSED");
				direction = "up";
				isJumping = true;
				canJump = false;
			}
		}

		if (isJumping) { // if the status is jumping then.... well do something
//			 if collsion is false, player can move
			if (!collisionOn) {
				screenY -= jumpSpeed;
			}

			jumpCounter++;

			if (jumpCounter >= 20) {
				jumpCounter = 0;
				isJumping = false;
				direction = "down";
			}
		}

		else { // if not do this
			if (!collisionOn) {
				screenY += jumpSpeed;
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
	}

	public void draw(Graphics2D g2) {

		// g2.setColor(Color.WHITE);
		// g2.fillRect(x, y, gp.tileSize, gp.tileSize); // we can draw stuff using this
		// Graphics2D like a rectangle

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