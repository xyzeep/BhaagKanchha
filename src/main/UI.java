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

	Graphics2D g2;

	Font Consolas_30, Consolas_80B, Consolas_50;

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
		Consolas_50 = new Font("Consolas", Font.PLAIN, 50);
		OBJ_Star star = new OBJ_Star(gp);
		starImage = star.image;
	}

	public void draw(Graphics2D g2) {

		this.g2 = g2;
		g2.setFont(Consolas_50);
		g2.setColor(Color.WHITE);

		if (gp.gameState == gp.playState) {
			// DO PLAY STATE THINGS LATER
		}

		if (gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		
		if (gameFinished == true) {

			g2.setFont(Consolas_30); // and the just set it like this here
			g2.setColor(Color.black);

			String text;
			int textLength;
			int x, y;

			text = "You completed this level!";
			textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // looks weird but this returns																	// the length of the text
			textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // looks weird but this returns
																							// // the length of the text
			x = gp.screenWidth / 2 - textLength / 2;
			y = gp.screenHeight / 2 - (gp.tileSize * 3);
			g2.drawString(text, x, y);
			
			gp.gameThread = null;
		}
		
	}

	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}

	public void drawPauseScreen() {

		String text = "Paused";

		int x = getXforCenteredText(text);
		int y = gp.screenHeight / 2;
		
		g2.drawString(text, x, y);

	}

	// just a handy method to get the center X of a text2
	public int getXforCenteredText(String text) {
		int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - textLength / 2;
		return x;
	}
}
