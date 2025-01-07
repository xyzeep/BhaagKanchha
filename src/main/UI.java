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
import object.SuperObject;
import object.OBJ_Heart;

public class UI {

	Player player;
	GamePanel gp;

	Graphics2D g2;

	Font maruMonica, retro8Bit, qaaxee;

	BufferedImage fullHeart, halfHeart, emptyHeart;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public int score;
	public int commandNum = 0;
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");

	public int cursorZoom = 0;

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

		// Get HUD images

		SuperObject heart = new OBJ_Heart(gp);
		fullHeart = heart.image;
		halfHeart = heart.image2;
		emptyHeart = heart.image3;
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
			drawPlayerLife();
		}

		// PAUSE STATE
		if (gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
		}

		// WHEN THE GAME IS FINISHED
		if (gameFinished == true) {
			// show game finished message
			gp.gameThread = null;
		}

	}

	public void drawPlayerLife() {
		
		int x = gp.tileSize / 2;
		int y = gp.tileSize / 4;

		int i = 0;
//		// Draw Blank hearts first(MAX LIFE)
//		while (i < gp.player.maxLife / 2) {
//			g2.drawImage(emptyHeart, x, y, null);
//			i++;
//			x += gp.tileSize;
//		}

		// reset the values
		x = gp.tileSize / 2;
		y = gp.tileSize / 4;
		i = 0;

		// DRAW CURRENT LIFE
		while (i < gp.player.life) {

			g2.drawImage(halfHeart, x, y, null);
			i++;

			if (i < gp.player.life) {
				g2.drawImage(fullHeart, x, y, null);
			}
			i++;
			x += gp.tileSize;
			if (i < gp.player.life) {
				g2.drawImage(fullHeart, x, y, null);
			}
		}
	}

	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}

	public void drawTitleScreen() {

		// GAME NAME
		g2.setFont(qaaxee.deriveFont(Font.BOLD, 96F));
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
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));

//		x = 415;
		x = 100;

		// play
		text = "PLAY";
		if (cursorZoom == 0) {
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 52F));
		}

		y += gp.tileSize * 3;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - gp.tileSize / 2, y);
		}

		// Options
		text = "OPTIONS";
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
		if (cursorZoom == 1) {
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 52F));
		}
		y += gp.tileSize * 1.1;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - gp.tileSize / 2, y);
		}

		// Quit
		text = "QUIT";
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
		if (cursorZoom == 2) {
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 52F));
		}
		y += gp.tileSize * 1.1;
		g2.drawString(text, x, y);
		if (commandNum == 2) {
			g2.drawString(">", x - gp.tileSize / 2, y);
		}

		// Logout
		text = "LOG OUT";
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
		if (cursorZoom == 3) {
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 52F));
		}
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
		// pause text
		g2.setFont(retro8Bit.deriveFont(Font.PLAIN, 50F));
		String text = "Paused";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight / 9;
		g2.setColor(Color.WHITE);
		g2.drawString(text, x, y);

		// pause symbol
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 70F));
		g2.drawString("||", x - (gp.tileSize), y);

	}

	// just a handy method to get the center X of a text2
	public int getXforCenteredText(String text) {
		int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - textLength / 2;
		return x;
	}
}
