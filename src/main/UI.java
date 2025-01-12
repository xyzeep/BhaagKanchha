package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream.GetField;
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
	public String notificationMsg = "";
	public int commandNum = 0;
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	int subState = 0;

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
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);


		// TITLE STATE
		if (gp.gameState == gp.titleState) {
			drawTitleScreen();
		}

		// PLAY STATE
		if (gp.gameState == gp.playState) {
			drawPlayerLife();
			if (messageOn == true) {
				messageCounter++;
				showMessage();
				if (messageCounter >= 60) {
					messageCounter = 0;
					messageOn = false;
				}
			}
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

	public void showMessage() {

		g2.setFont(maruMonica);

		// window
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
		g2.drawString(message, gp.tileSize / 2, gp.tileSize * 2);
	}

	public void drawTitleScreen() {
		// to fix overlapping bug (thanks FrankLeRouxe)
		g2.setColor(new Color(0, 0, 0));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
//		#########################

		// GAME NAME
		g2.setFont(qaaxee.deriveFont(Font.BOLD, 96F));
		String text = "Bhaag Kanchha";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight / 5;

		// SHADOW COLOR
		g2.setColor(Color.gray);
		g2.drawString(text, x + 4, y + 5);

		// MAIN COLOR
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		// MENU
		g2.setFont(maruMonica);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
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

	}

	public void drawPauseScreen() {

		// options menu
		g2.setColor(Color.white);
		

		// options sub window
		int frameX = gp.tileSize * 6;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize * 8;
		int frameHeight = gp.tileSize * 10;

		drawSubWindow(frameX, frameY, frameWidth, frameHeight);

		switch (subState) {
		case 0:
			optionsTop(frameX, frameY);
			break;
		case 1: options_fullscreenNotification(frameX, frameY);
			break;
		case 2: options_control(frameX, frameY);
			break;
		}

		gp.keyH.enterPressed = false;
	}
	
	
	public void options_control(int frameX, int frameY) {
		int textX;
		int textY;
		g2.setFont(maruMonica.deriveFont(Font.PLAIN, 40F));
		//title
		String text = "Control";
		textX = getXforCenteredText(text);
		textY= frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		textX = frameX + gp.tileSize;
		textY += gp.tileSize* 2;
		g2.drawString("Move", textX, textY); textY += gp.tileSize;
	
		g2.drawString("Confirm", textX, textY); textY += gp.tileSize;
		g2.drawString("Pause", textX, textY); textY += gp.tileSize;
		
		
		textY += gp.tileSize * 2;
		// back
		g2.drawString("Back", textX, textY);
		
		textX = frameX + gp.tileSize * 5;
		textY = frameY + gp.tileSize * 3;
		g2.drawString("W A D", textX, textY); textY += gp.tileSize;
		g2.drawString("Enter", textX, textY); textY += gp.tileSize;
		g2.drawString("ESC", textX, textY); textY += gp.tileSize;
		
		
		
		
	}
	
	public void optionsTop(int frameX, int frameY) {

		int textX;
		int textY;
		g2.setFont(maruMonica.deriveFont(Font.PLAIN, 40F));
		
		// TITLE
		String text = "Paused";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		
		g2.drawString(text, textX, textY);

		// FULL SCREEN on/off
		textX = frameX + gp.tileSize;
		textY += gp.tileSize * 2;
		g2.drawString("Full Screen", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - gp.tileSize / 2, textY);
			if (gp.keyH.enterPressed == true) {
				if (gp.fullScreenOn == false) {
					gp.fullScreenOn = true;
				}

				else if (gp.fullScreenOn == true) {
					gp.fullScreenOn = false;
					options_fullscreenNotification(frameX, frameY);

				}
				
				subState = 1;
			}
		}

			// Music
			textY += gp.tileSize;
			g2.drawString("Music", textX, textY);
			if (commandNum == 1) {
				g2.drawString(">", textX - gp.tileSize / 2, textY);
			}

			// SE
			textY += gp.tileSize;
			g2.drawString("SE", textX, textY);
			if (commandNum == 2) {
				g2.drawString(">", textX - gp.tileSize / 2, textY);
			}

			// Controls
			textY += gp.tileSize;
			g2.drawString("Controls", textX, textY);
			if (commandNum == 3) {
				g2.drawString(">", textX - gp.tileSize / 2, textY);
				if(gp.keyH.enterPressed == true) {
					subState = 2;
					commandNum = 0;
				}
			}

			// End game
			textY += gp.tileSize;
			g2.drawString("End Game", textX, textY);
			if (commandNum == 4) {
				g2.drawString(">", textX - gp.tileSize / 2, textY);
			}

			// full screen checkbox
			textX = frameX + (int) (gp.tileSize * 4.8);
			textY = frameY + gp.tileSize * 2 + 24;
			g2.setStroke(new BasicStroke(2));
			g2.drawRect(textX, textY, 24, 24);
			if (gp.fullScreenOn) {
				g2.fillRect(textX + 4, textY + 4, 16, 16);
			}

			// music bar
			textY += gp.tileSize;
			g2.drawRect(textX, textY, 120, gp.tileSize / 2); // 120/5 = 24 cause 5 levels of volume
	
			int volumeWidth = gp.music.volumeScale * 24;
			g2.fillRect(textX, textY, volumeWidth, 24);

			// sound bar
			textY += gp.tileSize;
			g2.drawRect(textX, textY, 120, gp.tileSize / 2);
			volumeWidth = gp.se.volumeScale * 24;
			g2.fillRect(textX, textY, volumeWidth, 24);

		}
	

	// just a handy method to get the center X of a text2
	public int getXforCenteredText(String text) {
		int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - textLength / 2;
		return x;
	}
	
	
	public void options_fullscreenNotification(int frameX, int frameY) {
		g2.setFont(maruMonica.deriveFont(Font.PLAIN, 40F));
		int textX  = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize* 3;
		notificationMsg = "The changes will take\neffect after a restart";
		
		for(String line: notificationMsg.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		
	
		//back
		textY += frameY + gp.tileSize;
		g2.drawString("Back", textX, textY);
		
			g2.drawString(">", textX - gp.tileSize / 2, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
			}
	
		
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {

		Color c = new Color(0, 0, 0, 210); // R,G,B, alfa(opacity)
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);

		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5)); // 5 = width of outlines of graphics
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

	}
	

}
