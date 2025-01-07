package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import entity.Player;
import object.OBJ_Star;

public class UI {

	Player player;
	GamePanel gp;

	Graphics2D g2;

	Font maruMonica, retro8Bit, qaaxee;

	BufferedImage starImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public int score;
	public int commandNum = 0;
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");

	public UI(GamePanel gp) {
		this.gp = gp;
		// instantiating fonts
		try {
			InputStream is = getClass().getResourceAsStream("/font/MaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/font/8Bit.ttf");
			retro8Bit = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/font/qaaxee.ttf");
			qaaxee = Font.createFont(Font.TRUETYPE_FONT, is);

		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		OBJ_Star star = new OBJ_Star(gp);
		starImage = star.image;
	}

	public void draw(Graphics2D g2) {

		this.g2 = g2;

		g2.setFont(qaaxee);

		// this makes out texts smooth and pretty not applicable if the fotn is
		// pixelated(like retro 8 bit)
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// TITLE STATE

		if (gp.gameState == gp.titleState) {
			drawTitleScreen();
		}

		// PLAY STATE
		if (gp.gameState == gp.playState) {
			// DO PLAY STATE THINGS LATER
		}

		// PAUSE STATE
		if (gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}

		// WHEN THE GAME IS FINISHED
		if (gameFinished == true) {

			g2.setFont(maruMonica); // and the just set it like this here
			g2.setColor(Color.black);

			String text;
			int textLength;
			int x, y;

			text = "You completed this level!";
			textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // looks weird but this returns
																							// // the length of the text
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

	public void drawTitleScreen() {

		// GAME NAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
		String text = "Bhaag Kanchha";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight / 5;

		// SHADOW COLOR
		g2.setColor(Color.gray);
		g2.drawString(text, x + 4, y + 4);

		// MAIN COLOR
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		// MENU
		g2.setFont(maruMonica);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

		// play
		text = "PLAY";
		x = getXforCenteredText(text) - (gp.tileSize / 2);
		y += gp.tileSize * 5;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - gp.tileSize / 2, y);
		}

		// Options
		text = "Options";
		y += gp.tileSize * 1.1;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - gp.tileSize / 2, y);
		}

		// Quit
		text = "Quit";
		y += gp.tileSize * 1.1;
		g2.drawString(text, x, y);
		if (commandNum == 2) {
			g2.drawString(">", x - gp.tileSize / 2, y);
		}

		// Logout
		text = "Log out";
		y += gp.tileSize * 1.1;
		g2.drawString(text, x, y);
		if (commandNum == 3) {
			g2.drawString(">", x - gp.tileSize / 2, y);
		}

		// KANCHHA CHARACTER IMAGE
		x = 20;
		y += gp.tileSize * 7 + 10;

		g2.drawImage(gp.player.right2, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

	}

	public void drawPauseScreen() {
	
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		String text = "Paused";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight / 4;
		g2.setColor(Color.WHITE);
		g2.drawString(text, x, y);

	}

	// just a handy method to get the center X of a text2
	public int getXforCenteredText(String text) {
		int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - textLength / 2;
		return x;
	}
}
