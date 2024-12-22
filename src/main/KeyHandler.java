package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener { // KeyListener is the interface for receiving keyboard
                                                 // events(keystrokes)

    public boolean upPressed;
    public boolean downPressed;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // returns the int keyCode associated with the pressed key

        if (code == KeyEvent.VK_W) {
            upPressed = true;
            System.out.println("Up key was pressed!");
        }

        if (code == KeyEvent.VK_S) {
            downPressed = true;
            System.out.println("Down key was pressed!");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
    }

}
