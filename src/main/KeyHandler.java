package main;

import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener { // KeyListener is the interface for receiving keyboard
													// events(keystrokes)

	public boolean upPressed, leftPressed, rightPressed;
	
	//debug
	boolean toggleDebug = false;
	// ##########################
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode(); // returns the int keyCode associated with the pressed key

		if (code == KeyEvent.VK_W) {
			upPressed = true;
		}
		
		if (code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		
		if (code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		
		// DEBUG
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
