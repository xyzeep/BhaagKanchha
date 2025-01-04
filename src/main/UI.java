package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import entity.Player;
import object.OBJ_Star;

public class UI {

	Player player;
	GamePanel gp;
	Font Consolas_30, Consolas_80B;

	BufferedImage starImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public int score;
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");

	public UI(GamePanel gp) {
		this.gp = gp;
		// we instantiate outside the gameloop because we dont make a class 60 times a
		// sec
		Consolas_30 = new Font("Consolas", Font.PLAIN, 30); // this is how to setup a font using graphics2D
		Consolas_80B = new Font("Consolas", Font.BOLD, 80);
		OBJ_Star star = new OBJ_Star(gp);
		starImage = star.image;
	}

	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}

	public void draw(Graphics2D g2) {

		if (gameFinished == true) {

			g2.setFont(Consolas_30); // and the just set it like this here
			g2.setColor(Color.black);

			String text;
			int textLength;
			int x, y;

			text = "You completed this level!";
			textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // looks weird but this returns
																							// // the length of the text
			x = gp.screenWidth / 2 - textLength / 2;
			y = gp.screenHeight / 2 - (gp.tileSize * 3);
			g2.drawString(text, x, y);

			text = "Your score is " + dFormat.format(1000 / playTime);
			textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // looks weird but this returns
																							// // the length of the text
			x = gp.screenWidth / 2 - textLength / 2;
			y = gp.screenHeight / 2 - (gp.tileSize * 2);
			g2.drawString(text, x, y);

			g2.setFont(Consolas_80B);
			g2.setColor(Color.green);
			text = "Congratulations!";
			textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // looks weird but this returns
																							// the length of the text
			x = gp.screenWidth / 2 - textLength / 2;
			y = gp.screenHeight / 2 + (gp.tileSize);
			g2.drawString(text, x, y);

			gp.gameThread = null;

		}

		else {
			g2.setFont(Consolas_30); // and the just set it like this here
			g2.setColor(Color.yellow);
			g2.drawImage(starImage, 10, 5, gp.tileSize - 5, gp.tileSize - 5, null);

			g2.drawString("x" + gp.player.stars, 55, 38);

			g2.drawString("FPS:" + gp.currentFPS, 840, 38);

			// play time
			playTime += (double) 1 / 60;

			g2.drawString("Time: " + dFormat.format(playTime), gp.tileSize * 16, gp.tileSize + 24);

			// MESSAGE
			if (messageOn) {

				g2.drawString(message, gp.tileSize / 2, gp.tileSize * 2);
				messageCounter++;

				if (messageCounter >= 120) {
					messageCounter = 0;
					messageOn = false;
				}
			}
		}

	}

}
