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
		if (gp.gameState == gp.pauseState) {
			maxCommandNum = 4;
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
			maxCommandNum = 3;
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

				case 1: // options
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