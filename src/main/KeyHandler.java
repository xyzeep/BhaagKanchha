package main;

import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener { // KeyListener is the interface for receiving keyboard
													// events(keystrokes)

	public boolean upPressed, leftPressed, rightPressed, enterPressed;
	GamePanel gp;
	int maxCommandNum;
	// debug
	boolean toggleDebug = false;
	// ##########################

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode(); // returns the int keyCode associated with the pressed key

		// PLAY STATE
		if (gp.gameState == gp.playState) {
			if (code == KeyEvent.VK_W) {
				upPressed = true;
			}

			if (code == KeyEvent.VK_A) {
				leftPressed = true;
			}

			if (code == KeyEvent.VK_D) {
				rightPressed = true;
			}

			// ##################### DEBUG
			if (code == KeyEvent.VK_T) {
				if (!toggleDebug) {
					toggleDebug = true;
				}

				else {
					toggleDebug = false;
				}
			}
			// ##########################

		}

		// switch between play state and pause state in game
		if (code == KeyEvent.VK_ESCAPE) {
			if (gp.gameState == gp.playState) {
				gp.gameState = gp.pauseState;
			} else if (gp.gameState == gp.pauseState) {
				gp.gameState = gp.playState;
			}
		}

		// PAUSE STATE
		switch (gp.ui.subState) {
		case 0:
			maxCommandNum = 5;
			break;
		case 1:
			maxCommandNum = 0;
			break;
		case 2:
			maxCommandNum = 0;
			break;
		case 3:
			maxCommandNum = 1;
			break;
		}
		if (gp.gameState == gp.pauseState) {

			if (gp.ui.subState == 0) {

				if (code == KeyEvent.VK_W) {
					gp.playSoundEffect(5);
					gp.ui.commandNum--;
					if (gp.ui.commandNum < 0) {
						gp.ui.commandNum = maxCommandNum;
					}
				}
				if (code == KeyEvent.VK_S) {
					gp.playSoundEffect(5);
					gp.ui.commandNum++;
					if (gp.ui.commandNum > maxCommandNum) {
						gp.ui.commandNum = 0;
					}

				}
			}

			if (gp.ui.subState == 1 || gp.ui.subState == 2) {
				if (code == KeyEvent.VK_W) {
					gp.playSoundEffect(5);
					gp.ui.commandNum--;
					if (gp.ui.commandNum < 0) {
						gp.ui.commandNum = maxCommandNum;
					}
				}
				if (code == KeyEvent.VK_S) {
					gp.playSoundEffect(5);
					gp.ui.commandNum++;
					if (gp.ui.commandNum > maxCommandNum) {
						gp.ui.commandNum = 0;
					}

				}
			}

			if (gp.ui.subState == 3) {

				if (code == KeyEvent.VK_W) {
					gp.playSoundEffect(5);
					gp.ui.commandNum--;
					if (gp.ui.commandNum < 0) {
						gp.ui.commandNum = maxCommandNum;
					}
				}
				if (code == KeyEvent.VK_S) {
					gp.playSoundEffect(5);
					gp.ui.commandNum++;
					if (gp.ui.commandNum > maxCommandNum) {
						gp.ui.commandNum = 0;
					}

				}
			}

			if (code == KeyEvent.VK_ENTER) {
				enterPressed = true;

			}
			if (code == KeyEvent.VK_A) {
				if (gp.ui.subState == 0) {
					if (gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
						gp.music.volumeScale--;
						gp.music.checkVolume();
						gp.playSoundEffect(5);
					}

					if (gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
						gp.se.volumeScale--;
						gp.playSoundEffect(5);
					}
				}
			}

			if (code == KeyEvent.VK_D) {
				if (gp.ui.subState == 0) {
					if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
						gp.music.volumeScale++;
						gp.music.checkVolume();
						gp.playSoundEffect(5);
					}
					if (gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
						gp.se.volumeScale++;
						gp.playSoundEffect(5);
					}

				}

			}

		}

		// TITLE STATE
		if (gp.gameState == gp.titleState) {
			switch (gp.ui.subState) {
			case 0:
				maxCommandNum = 3;
			}
			if (code == KeyEvent.VK_W) {
				gp.playSoundEffect(5);
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = maxCommandNum;
				}
			} else if (code == KeyEvent.VK_S) {
				gp.playSoundEffect(5);
				gp.ui.commandNum++;
				if (gp.ui.commandNum > maxCommandNum) {
					gp.ui.commandNum = 0;
				}
			}

			gp.ui.cursorZoom = gp.ui.commandNum; // for the zoom effect

			// HANDELING MENUS
			if (code == KeyEvent.VK_ENTER) {

				switch (gp.ui.commandNum) {
				case 0: // play
					gp.gameState = gp.playState;
					gp.playMusic(0);
					break;

				case 1: // maps
					// do later
					break;

				case 2: // quit
					System.exit(0); // exit with 0(successfully)
					break;

				case 3: // logout
					// do later

				}

			}
		}

		if (gp.gameState == gp.gameOverState) {
			maxCommandNum = 1;
			if (code == KeyEvent.VK_W) {
				gp.playSoundEffect(5);
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = maxCommandNum;
				}
			} else if (code == KeyEvent.VK_S) {
				gp.playSoundEffect(5);
				gp.ui.commandNum++;
				if (gp.ui.commandNum > maxCommandNum) {
					gp.ui.commandNum = 0;
				}
			}

			if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.commandNum == 0) {
					gp.gameState = gp.playState;
					gp.restart();
					gp.playMusic(0);
				} else if (gp.ui.commandNum == 1) {
					gp.gameState = gp.titleState;
					gp.restart();
					gp.ui.commandNum = 0;
				}
			}

		}
		// LOGIN STATE KEY HANLDING
		if (gp.gameState == gp.loginState) {
			maxCommandNum = 3;

			if (code == KeyEvent.VK_TAB) {
				gp.playSoundEffect(5);
				gp.ui.commandNum++;
				if (gp.ui.commandNum > maxCommandNum) {
					gp.ui.commandNum = 0;
				}
			}

			if (code == KeyEvent.VK_ENTER) {
				enterPressed = true;
			}

			// HANDLING KEY INPUTSSSS
			char keyChar = e.getKeyChar(); // 'e' is your KeyEvent object
			
			if (gp.ui.commandNum == 0) {
				if (Character.isLetterOrDigit(keyChar) || keyChar == '!' || keyChar == '@' || keyChar == '#'
						|| keyChar == '$' || keyChar == '%') {
					if(gp.ui.username == "placeholderUsername") {
						gp.ui.username = "";
					}
					gp.ui.username += keyChar; // append key char

				} else if (code == KeyEvent.VK_BACK_SPACE && gp.ui.username != "placeholderUsername") { // if pressed backspace
					System.out.println("del pressed");
					if (gp.ui.username.length() > 1) {
						// Remove the last character from the username
						gp.ui.username = gp.ui.username.substring(0, gp.ui.username.length() - 1);
					} else {
						gp.ui.username = "placeholderUsername";
					}

				}
			}
			
			if (gp.ui.commandNum == 1) {
				if (Character.isLetterOrDigit(keyChar) || keyChar == '!' || keyChar == '@' || keyChar == '#'
						|| keyChar == '$' || keyChar == '%') {
					if(gp.ui.password == "placeholderPassword") {
						gp.ui.password = "";
					}
					gp.ui.password += keyChar; // append key char

				} else if (code == KeyEvent.VK_BACK_SPACE && gp.ui.password != "placeholderPassword") { // if pressed backspace
					System.out.println("del pressed");
					if (gp.ui.password.length() > 1) {
						// Remove the last character from the username
						gp.ui.password = gp.ui.password.substring(0, gp.ui.password.length() - 1);
					} else {
						gp.ui.password = "placeholderPassword";
					}

				}
			}
			
			if (gp.ui.commandNum == 2) {
				//
			}
			
			if (gp.ui.commandNum == 3) {
			//
			}
			

		}
		
		
		// SIGNUP STATE KEY HANDLING
		if (gp.gameState == gp.signupState) {
			maxCommandNum = 5;

			if (code == KeyEvent.VK_TAB) {
				gp.playSoundEffect(5);
				gp.ui.commandNum++;
				if (gp.ui.commandNum > maxCommandNum) {
					gp.ui.commandNum = 0;
				}
			}

			if (code == KeyEvent.VK_ENTER) {
				enterPressed = true;
			}

			// HANDLING KEY INPUTSSSS
			char keyChar = e.getKeyChar(); // 'e' is your KeyEvent object
			
			if (gp.ui.commandNum == 0) {
				if (Character.isLetterOrDigit(keyChar) || keyChar == '!' || keyChar == '@' || keyChar == '#'
						|| keyChar == '$' || keyChar == '%') {
					if(gp.ui.username == "placeholderUsername") {
						gp.ui.username = "";
					}
					gp.ui.username += keyChar; // append key char

				} else if (code == KeyEvent.VK_BACK_SPACE && gp.ui.username != "placeholderUsername") { // if pressed backspace
					System.out.println("del pressed");
					if (gp.ui.username.length() > 1) {
						// Remove the last character from the username
						gp.ui.username = gp.ui.username.substring(0, gp.ui.username.length() - 1);
					} else {
						gp.ui.username = "placeholderUsername";
					}

				}
			}
			
			if (gp.ui.commandNum == 1) {
				if (Character.isLetterOrDigit(keyChar) || keyChar == '!' || keyChar == '@' || keyChar == '#'
						|| keyChar == '$' || keyChar == '%') {
					if(gp.ui.password == "placeholderPassword") {
						gp.ui.password = "";
					}
					gp.ui.password += keyChar; // append key char

				} else if (code == KeyEvent.VK_BACK_SPACE && gp.ui.password != "placeholderPassword") { // if pressed backspace
					System.out.println("del pressed");
					if (gp.ui.password.length() > 1) {
						// Remove the last character from the username
						gp.ui.password = gp.ui.password.substring(0, gp.ui.password.length() - 1);
					} else {
						gp.ui.password = "placeholderPassword";
					}

				}
			}
			
			if (gp.ui.commandNum == 2) {
				if (Character.isLetterOrDigit(keyChar) || keyChar == '!' || keyChar == '@' || keyChar == '#'
						|| keyChar == '$' || keyChar == '%') {
					if(gp.ui.passwordAgain == "placeholderPasswordAgain") {
						gp.ui.passwordAgain = "";
					}
					gp.ui.passwordAgain += keyChar; // append key char

				} else if (code == KeyEvent.VK_BACK_SPACE && gp.ui.passwordAgain != "placeholderPasswordAgain") { // if pressed backspace
					System.out.println("del pressed");
					if (gp.ui.passwordAgain.length() > 1) {
						// Remove the last character from the username
						gp.ui.passwordAgain = gp.ui.passwordAgain.substring(0, gp.ui.passwordAgain.length() - 1);
					} else {
						gp.ui.passwordAgain = "placeholderPasswordAgain";
					}

				}
			}
			
			if (gp.ui.commandNum == 3) {
				if (Character.isDigit(keyChar)) {
					if(gp.ui.security == "placeholderSecurity") {
						gp.ui.security = "";
					}
					gp.ui.security += keyChar; // append key char

				} else if (code == KeyEvent.VK_BACK_SPACE && gp.ui.security != "placeholderSecurity") { // if pressed backspace
					System.out.println("del pressed");
					if (gp.ui.security.length() > 1) {
						// Remove the last character from the username
						gp.ui.security = gp.ui.security.substring(0, gp.ui.security.length() - 1);
					} else {
						gp.ui.security = "placeholderSecurity";
					}

				}
			}
			

		}



		if (gp.gameState == gp.gameFinishedState) {
			maxCommandNum = 1;
			if (code == KeyEvent.VK_W) {
				gp.playSoundEffect(5);
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = maxCommandNum;
				}
			} else if (code == KeyEvent.VK_S) {
				gp.playSoundEffect(5);
				gp.ui.commandNum++;
				if (gp.ui.commandNum > maxCommandNum) {
					gp.ui.commandNum = 0;
				}
			}

			if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.commandNum == 0) {
					gp.gameState = gp.playState;
					gp.restart();
					gp.playMusic(0);
					gp.ui.commandNum = 0;
				} else if (gp.ui.commandNum == 1) {
					gp.gameState = gp.titleState;
					gp.restart();
					gp.ui.commandNum = 0;
				}
			}

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_W) {
			upPressed = false;
		}

		if (code == KeyEvent.VK_A) {
			leftPressed = false;
		}

		if (code == KeyEvent.VK_D) {
			rightPressed = false;
		}
	}

}