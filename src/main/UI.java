package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import entity.Player;
import object.SuperObject;
import object.OBJ_Heart;

public class UI {
	// for login and signup
	String username = "placeholderUsername"; // hopefully nobody chooses this as their username
	String password = "placeholderPassword";
	String passwordAgain = "placeholderPasswordAgain";
	String security = "placeholderSecurity";
	String errorMessage = null;
	public BufferedImage right1, right2, torch1, torch2, kanchhaImage, yellowGlow, torchImage, image;
	int kanchhaImageY;

	int errorMsgCounter = 0;
	int uiImageCounter = 0;
	boolean showPassword = false;

	Player player;
	GamePanel gp;

	Graphics2D g2;

	Font maruMonica, qaaxee, temp, miscFont;

	BufferedImage fullHeart, halfHeart, emptyHeart;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
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
			is = getClass().getResourceAsStream("/font/qaaxee.ttf");
			qaaxee = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/font/temp.ttf");
			temp = Font.createFont(Font.TRUETYPE_FONT, is);
			miscFont = new Font("Calibri", Font.BOLD, 18);
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
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right1 = UtilityTool.resizeImage(image, 80, 80);
			image = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
			right2 = UtilityTool.resizeImage(image, 80, 80);
			image = ImageIO.read(getClass().getResourceAsStream("/object/torch1.png"));
			torch1 = UtilityTool.resizeImage(image, 200, 200);
			image = ImageIO.read(getClass().getResourceAsStream("/object/torch2.png"));
			torch2 = UtilityTool.resizeImage(image, 200, 200);
			image = ImageIO.read(getClass().getResourceAsStream("/object/yellowGlow.png"));
			yellowGlow = UtilityTool.resizeImage(image, 200, 200);
			kanchhaImage = right1;
			torchImage = torch1;

		} catch (IOException e) {
			e.printStackTrace();
		}
		kanchhaImage = right1;
	}

	public void draw(Graphics2D g2) {

		this.g2 = g2;

		// this makes out texts smooth and pretty not applicable if the fotn is
		// pixelated(like retro 8 bit)
//		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		// TITLE STATE
		if (gp.gameState == gp.titleState) {
			drawTitleScreen();
		}

		// signup STATE
		if (gp.gameState == gp.signupState) {
			drawSignupScreen();
		}

		// login state
		if (gp.gameState == gp.loginState) {
			drawLoginScreen();
		}

		// PLAY STATE
		if (gp.gameState == gp.playState) {
			drawPlayerLife();
			drawFPS();
			if (messageOn == true) {
				messageCounter++;
				showMessage();
				if (messageCounter >= 60) {
					messageCounter = 0;
					messageOn = false;
				}
			}
		}
		// GAME FINISHED STATE
		if (gp.gameState == gp.gameFinishedState) {
			drawGameFinishedScreen();

		}

		// GAME OVER STATE
		if (gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
		}
		// PAUSE STATE
		if (gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawFPS();
			drawPauseMenu();
		}
	}

	public void drawFPS() {
		int x = gp.tileSize * 19 + 5;
		int y = gp.tileSize - 10;
		String text = "" + gp.currentFPS;

		if (gp.currentFPS < 60 && gp.currentFPS > 55) {
			g2.setColor(Color.yellow);
		} else if (gp.currentFPS <= 55) {
			g2.setColor(Color.RED);
		}

		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 38F));
		g2.drawString(text, x, y);
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
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
		g2.drawString(message, gp.tileSize / 2, gp.tileSize * 3);
	}

	public void drawLoginScreen() {
		// i have mentioned why this is here somewhere in the code and i am not doing it
		// again
		g2.setColor(new Color(0, 0, 0));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		drawUIImages();
		// if errors, display
		// If errors, display inside a speech bubble

		g2.setColor(Color.white);

		g2.setStroke(new BasicStroke(4));
		g2.drawRoundRect(gp.screenWidth / 2 - 200, gp.screenHeight / 2 - 250, 400, 500, 50, 50);

		// LOGIN
		g2.setFont(maruMonica.deriveFont(Font.BOLD, 50F)); // keep this line before the line that calls
															// getXforCenteredText() method
		String text = "Login";
		int x = getXforCenteredText(text);
		int y = gp.tileSize * 2;

		g2.drawString(text, x, y);

		g2.setFont(maruMonica.deriveFont(Font.BOLD, 37F));
		// USERNAME

		g2.setColor(Color.GRAY);
		y += gp.tileSize + 30;
		if (commandNum == 0) {
			g2.setColor(Color.WHITE);
			kanchhaImageY = y;
		}
		text = (username == "placeholderUsername") ? "Username" : username;
		x = 308;
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(x, y - 40, 340, 50, 8, 8);
		if (username == "placeholderUsername") {
			g2.setColor(Color.GRAY);
			g2.drawString(text, x + 10, y);
		} else {
			g2.drawString(text, x + 10, y);
		}

		// PASSWORD
		g2.setColor(Color.GRAY);
		y += gp.tileSize + 24;
		if (commandNum == 1) {
			g2.setColor(Color.WHITE);
			kanchhaImageY = y;
		}
		text = (password == "placeholderPassword") ? "Password" : password;
		/// HIDE PASSWORD

		if (password == "placeholderPassword") {
			text = "Password";
		} else {
			text = "";
			for (int i = 0; i < password.length(); i++) {
				text += "*";
			}
		}
		g2.drawRoundRect(x, y - 40, 340, 50, 8, 8);
		g2.drawRoundRect(x, y - 40, 340, 50, 8, 8);
		if (password == "placeholderPassword") {
			g2.setColor(Color.GRAY);
			g2.drawString(text, x + 10, y);
		} else {
			g2.drawString(text, x + 10, y);
		}

		// Login btn
		g2.setColor(Color.WHITE);
		text = "Login";
		x = getXforCenteredText(text) - 10;
		y += gp.tileSize * 3 + 24;
		if (commandNum == 2) {
			g2.drawString(">", x - 20, y);
			if (gp.keyH.enterPressed) {
				gp.db.login();

			}
		}
		g2.drawString(text, x, y);

		// Regster btn
		text = "Register";
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 3) {
			g2.drawString(">", x - 20, y);
			if (gp.keyH.enterPressed) {
				resetInputFields();
				gp.gameState = gp.signupState;
				commandNum = 0;

			}
		}

		// Regster btn
		text = "Quit";
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 4) {
			g2.drawString(">", x - 20, y);
			if (gp.keyH.enterPressed) {
				System.exit(0);

			}
		}
		drawGameVersion();
		if (errorMessage != null && errorMsgCounter < 120) {
			drawErrorSpeechBubble();
		}
		// reset
		gp.keyH.enterPressed = false;

		System.out.println("In login");
	}

	public void drawSignupScreen() {
		// i have mentioned why this is here somewhere in the code and i am not doing it
		// again
		g2.setColor(new Color(0, 0, 0));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		drawUIImages();

		g2.setColor(Color.white);
		g2.setFont(maruMonica.deriveFont(Font.BOLD, 50F));
		g2.setStroke(new BasicStroke(4));
		g2.drawRoundRect(gp.screenWidth / 2 - 200, gp.screenHeight / 2 - 250, 400, 500, 50, 50);

		// REGISTER
		g2.setFont(maruMonica.deriveFont(Font.BOLD, 50F)); // keep this line before the line that calls
															// getXforCenteredText() method
		String text = "Register";
		int x = getXforCenteredText("Register");
		int y = gp.tileSize * 2;
		g2.drawString(text, x, y);

		g2.setFont(maruMonica.deriveFont(Font.BOLD, 37F));
		// USERNAME

		g2.setColor(Color.GRAY);
		y += gp.tileSize + 30;
		if (commandNum == 0) {
			g2.setColor(Color.WHITE);
			kanchhaImageY = y;
		}
		text = (username == "placeholderUsername") ? "Username" : username;
		x = 308;

		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(x, y - 40, 340, 50, 8, 8);
		if (username == "placeholderUsername") {
			g2.setColor(Color.GRAY);
			g2.drawString(text, x + 10, y);
		} else {
			g2.drawString(text, x + 10, y);
		}

		// PASSWORD
		g2.setColor(Color.GRAY);
		y += gp.tileSize + 24;
		if (commandNum == 1) {
			g2.setColor(Color.WHITE);
			kanchhaImageY = y;
		}

		/// HIDE PASSWORD

		if (password == "placeholderPassword") {
			text = "Password";
		} else {
			text = "";
			for (int i = 0; i < password.length(); i++) {
				text += "*";
			}
		}
		g2.drawRoundRect(x, y - 40, 340, 50, 8, 8);
		if (password == "placeholderPassword") {
			g2.setColor(Color.GRAY);
			g2.drawString(text, x + 10, y);
		} else {
			g2.drawString(text, x + 10, y);
		}

		// PASSWORD(AGAIN)
		g2.setColor(Color.GRAY);
		y += gp.tileSize + 24;
		if (commandNum == 2) {
			g2.setColor(Color.WHITE);
			kanchhaImageY = y;
		}
		// HIDE PASSWORD RETYPE
		if (passwordAgain == "placeholderPasswordAgain") {
			text = "Password (Again)";
		} else {
			text = "";
			for (int i = 0; i < passwordAgain.length(); i++) {
				text += "*";
			}
		}
		g2.drawRoundRect(x, y - 40, 340, 50, 8, 8);
		if (passwordAgain == "placeholderPasswordAgain") {
			g2.setColor(Color.GRAY);
			g2.drawString(text, x + 10, y);
		} else {
			g2.drawString(text, x + 10, y);
		}

		// security
		y += gp.tileSize + 24;
		g2.setColor(Color.GRAY);
		if (commandNum == 3) {
			g2.setColor(Color.WHITE);
			kanchhaImageY = y;
		}
		text = (security == "placeholderSecurity") ? "Your mom's number" : security;
		g2.drawRoundRect(x, y - 40, 340, 50, 8, 8);
		if (security == "placeholderSecurity") {
			g2.setColor(Color.GRAY);
			g2.drawString(text, x + 10, y);
		} else {
			g2.drawString(text, x + 10, y);
		}

		// Register btn
		g2.setColor(Color.WHITE);
		text = "Register";
		x = getXforCenteredText(text);
		y += gp.tileSize + 24;
		g2.drawString(text, x + 10, y);
		if (commandNum == 4) {
			g2.drawString(">", x - 10, y);
			if (gp.keyH.enterPressed) {
				gp.db.signUP(); // hmm signup func in Database.java
			}
		}

		// back
		g2.setColor(Color.WHITE);
		text = "Back";
		y += gp.tileSize;
		if (commandNum == 5) {
			g2.drawString(">", x - 10, y);
			if (gp.keyH.enterPressed) {
				resetInputFields();
				gp.gameState = gp.loginState;
				commandNum = 0;
			}
		}
		g2.drawString("Back", x + 10, y);
		drawGameVersion(); // game version
		// If errors, display inside a speech bubble
		if (errorMessage != null && errorMsgCounter < 120) {
			drawErrorSpeechBubble();
		}
		// reset
		gp.keyH.enterPressed = false;
	}

	public void drawGameFinishedScreen() {
		g2.setColor(new Color(0, 0, 0, 170));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

		text = "Congratulations";
		// shadow part
		g2.setColor(Color.black);
		x = getXforCenteredText(text);
		y = gp.tileSize * 4;
		g2.drawString(text, x + 4, y + 4);

		// main part
		g2.setColor(Color.white);
		x = getXforCenteredText(text);
		y = gp.tileSize * 4;
		g2.drawString(text, x, y);

		// Score
		score = (int) gp.timeToComplete / 10;
		text = "Your score was " + score;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40f));
		g2.setColor(Color.white);
		x = getXforCenteredText(text);
		y += gp.tileSize + 20;
		g2.drawString(text, x, y);

		// replay
		g2.setFont(g2.getFont().deriveFont(50f));
		text = "Replay";
		x = getXforCenteredText(text);
		y += gp.tileSize * 4;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - 25, y);
		}

		// quit
		text = "Quit";
		y += 55;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - 25, y);
		}
	}

	public void drawGameOverScreen() {
		g2.setColor(new Color(0, 0, 0, 170));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

		text = "Game Over";
		// shadow part
		g2.setColor(Color.black);
		x = getXforCenteredText(text);
		y = gp.tileSize * 4;
		g2.drawString(text, x + 4, y + 4);

		// main part
		g2.setColor(Color.white);
		x = getXforCenteredText(text);
		y = gp.tileSize * 4;
		g2.drawString(text, x, y);

		// retry
		g2.setFont(g2.getFont().deriveFont(50f));
		text = "Retry";
		x = getXforCenteredText(text);
		y += gp.tileSize * 4;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - 25, y);
		}

		// quit
		text = "Quit";
		y += 55;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - 25, y);
		}
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

		// maps
		text = "MAPS";
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
//		if (cursorZoom == 1) {
//			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 52F));
//		}
		y += gp.tileSize * 1.1;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 52F));
			g2.drawString(">", x - gp.tileSize / 2, y);
			if (gp.keyH.enterPressed == true) {
				// to be implemented later
			}
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28F));
			g2.drawString("(NOT CURRENTLY AVAILABLE)", x + 90, y - 5);

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

		drawGameVersion(); // game version
		drawUserInfo();

	}

	public void drawOptions() {

		// options menu
		g2.setColor(Color.white);
		g2.setFont(maruMonica.deriveFont(Font.BOLD, 38F));

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
		case 1:
			options_fullscreenNotification(frameX, frameY);
			break;
		case 2:
			options_control(frameX, frameY);
			break;
		case 3:
			options_endgame(frameX, frameY);
			break;
		}

		gp.keyH.enterPressed = false;
	}

	public void drawPauseMenu() {

		// options menu
		g2.setColor(Color.white);
		g2.setFont(maruMonica.deriveFont(Font.BOLD, 38F));

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
		case 1:
			options_fullscreenNotification(frameX, frameY);
			break;
		case 2:
			options_control(frameX, frameY);
			break;
		case 3:
			options_endgame(frameX, frameY);
			break;
		}

		gp.keyH.enterPressed = false;
	}

	public void options_control(int frameX, int frameY) {
		int textX;
		int textY;
		// title
		String text = "Control";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);

		textX = frameX + gp.tileSize;
		textY += (int) (gp.tileSize * 1.6);

		g2.drawString("Move", textX, textY);
		textY += gp.tileSize;

		g2.drawString("Confirm", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Pause", textX, textY);
		textY += gp.tileSize;

		// back
		textY = frameY + gp.tileSize * 9;
		g2.drawString("Back", textX, textY);

		g2.drawString(">", textX - gp.tileSize / 2, textY);
		if (gp.keyH.enterPressed == true) {
			subState = 0;
			commandNum = 3;
		}

		textX = frameX + gp.tileSize * 5;
		textY = frameY + (int) (gp.tileSize * 2.6);

		g2.drawString("W A D", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Enter", textX, textY);
		textY += gp.tileSize;
		g2.drawString("ESC", textX, textY);
		textY += gp.tileSize;

	}

	public void optionsTop(int frameX, int frameY) {

		int textX;
		int textY;

		// TITLE
		String text = "Paused";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);

		// FULL SCREEN on/off
		textX = frameX + gp.tileSize;
		textY += (int) (gp.tileSize * 1.6);

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
			if (gp.keyH.enterPressed == true) {
				subState = 2;
				commandNum = 0;
			}
		}

		// End game
		textY += gp.tileSize;
		g2.drawString("End Game", textX, textY);
		if (commandNum == 4) {
			g2.drawString(">", textX - gp.tileSize / 2, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 3;
				commandNum = 0;
			}
		}

		// back
		textY = frameY + gp.tileSize * 9;
		g2.drawString("Back", textX, textY);
		if (commandNum == 5) {
			g2.drawString(">", textX - gp.tileSize / 2, textY);
			if (gp.keyH.enterPressed == true) {
				gp.gameState = gp.playState;
				commandNum = 4;
			}
		}

		// full screen checkbox
		textX = frameX + (int) (gp.tileSize * 4.8);
//			 ##########################
		textY = frameY + gp.tileSize + (int) (gp.tileSize * 1.1);

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

		gp.config.saveConfig(); // everytime we open the options menu, config is saved
	}

	// just a handy method to get the center X of a text2
	public int getXforCenteredText(String text) {

		int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = (gp.screenWidth / 2) - (textLength / 2);
		return x;
	}

	public void options_endgame(int frameX, int frameY) {

		g2.setFont(maruMonica.deriveFont(Font.BOLD, 35F));
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize * 3;
		notificationMsg = "Quit the game and go to\nthe title screen?";

		for (String line : notificationMsg.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}

		// yes no options
		g2.setFont(maruMonica.deriveFont(Font.BOLD, 38F));
		String text = "Yes. Please";
		textY = frameY + gp.tileSize * 8;
		g2.drawString(text, textX, textY);

		if (commandNum == 0) {
			g2.drawString(">", textX - gp.tileSize / 2, textY);
			if (gp.keyH.enterPressed == true) {

				gp.gameState = gp.titleState;
				gp.player.setDefaultValues();
				subState = 0;
				commandNum = 0;
				gp.stopMusic();
			}
		}

		text = "Opps! No";
		textY = frameY + gp.tileSize * 9;
		g2.drawString(text, textX, textY);
		if (commandNum == 1) {

			g2.drawString(">", textX - gp.tileSize / 2, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 4;
			}
		}

	}

	public void options_fullscreenNotification(int frameX, int frameY) {
		g2.setFont(maruMonica.deriveFont(Font.PLAIN, 38F));
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize * 3;
		notificationMsg = "The changes will take\neffect after a restart";

		for (String line : notificationMsg.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}

		// back
		g2.setFont(maruMonica.deriveFont(Font.BOLD, 38F));
		textY = frameY + gp.tileSize * 9;
		g2.drawString("Back", textX, textY);

		g2.drawString(">", textX - gp.tileSize / 2, textY);
		if (gp.keyH.enterPressed == true) {
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

	public void resetInputFields() {
		this.username = "placeholderUsername";
		this.password = "placeholderPassword";
		this.passwordAgain = "placeholderPasswordAgain";
		this.security = "placeholderSecurity";
	}

	public void drawUIImages() {

		uiImageCounter++;
		if (uiImageCounter > 50) {
			kanchhaImage = (kanchhaImage == right1) ? right2 : right1;
			torchImage = (torchImage == torch1) ? torch2 : torch1;
			uiImageCounter = 0;
		}

		g2.drawImage(kanchhaImage, gp.tileSize * 2, kanchhaImageY - 60, null);
		g2.drawImage(yellowGlow, gp.tileSize * 15 - 5, 315, null);
		g2.drawImage(torchImage, gp.tileSize * 15, 350, null);

	}

	public void drawGameVersion() {
		g2.setFont(maruMonica.deriveFont(Font.BOLD, 28F));
		g2.setColor(Color.white);
		g2.drawString("Version 1.1.0", gp.tileSize * 17 - 10, 40);

		g2.setFont(maruMonica.deriveFont(Font.BOLD, 30F));
		if (gp.gameState == gp.loginState || gp.gameState == gp.signupState) {
			g2.drawString("Use TAB to navigate.", 20, gp.tileSize * 11 + 30);
		} else if (gp.gameState == gp.titleState) {
			g2.drawString("Use W, S, and ENTER to navigate.", gp.tileSize * 12, gp.tileSize * 11 + 30);
		}
	}

	public void drawUserInfo() {
		g2.setFont(maruMonica.deriveFont(Font.BOLD, 28F));
		g2.setColor(Color.white);
		g2.drawString("Username: " + gp.currentUsername, 20, gp.tileSize * 11 + 30);
	}

	public void drawErrorSpeechBubble() {
		errorMsgCounter++;

//		int textWidth = errorMessage.length() * 10;
//		System.out.println("Width: " +  textWidth);
//		System.out.println("Length: " +  errorMessage.length());

		// dimensions
//	    int padding = 5;
		int x = 10;
		int y = kanchhaImageY - 120; // Y-position
		int width = 300;
		int height = 60;
		int tailSize = 20;

		// speech bubble
		g2.setColor(new Color(255, 220, 220));
		g2.fillRoundRect(x, y, width, height, 20, 20);

		// pointy stuff
		int[] xPoints = { x + width / 2 - tailSize, x + width / 2 + tailSize, x + width / 2 };
		int[] yPoints = { y + height, y + height, y + height + tailSize };
		g2.fillPolygon(xPoints, yPoints, 3);

		// bubble border
		g2.setColor(new Color(180, 0, 0));
		g2.setStroke(new BasicStroke(4));
		g2.drawRoundRect(x, y, width, height, 20, 20);
		g2.drawPolygon(xPoints, yPoints, 3);

		// error message inside
		g2.setColor(Color.BLACK);
		g2.setFont(miscFont.deriveFont(Font.BOLD, 26F));
		g2.drawString(errorMessage, x + 20, y + 35);

		// reset after 3.333 secs
		if (errorMsgCounter >= 120) {
			errorMsgCounter = 0;
			errorMessage = null;
		}
	}
}
