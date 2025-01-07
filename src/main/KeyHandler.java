package main;

import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener { // KeyListener is the interface for receiving keyboard
													// events(keystrokes)

	public boolean upPressed, leftPressed, rightPressed;
	GamePanel gp;

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

//			if (code == KeyEvent.VK_ESCAPE) {
//				gp.gameState = gp.pauseState;
////				System.out.println("chiryo ra gameState is now" + ha);
//			}

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

		// PAUSE STATE
		if (code == KeyEvent.VK_ESCAPE) {
			if (gp.gameState == gp.playState) {
				gp.gameState = gp.pauseState;
			}
			else if (gp.gameState == gp.pauseState) {
				gp.gameState = gp.playState;
			}
		}

		// TITLE STATE
		if (gp.gameState == gp.titleState) {
			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 3;
				}

			} else if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 3) {
					gp.ui.commandNum = 0;
				}
			}
			
			if(code == KeyEvent.VK_ENTER) {
				switch (gp.ui.commandNum) {
				case 0: //play
					gp.gameState = gp.playState;
					gp.playMusic(0);
					break;
					
				case 1: //options
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
