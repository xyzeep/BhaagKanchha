package main;

import javax.swing.JFrame;

public class Main {

	public static JFrame window;

	public static void main(String[] args) {

		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Bhaag Kanchha");

		GamePanel gamePanel = new GamePanel();

		window.add(gamePanel); // adding a GamePanel wwwclass to the window
		gamePanel.config.loadConfig();
		if (gamePanel.fullScreenOn == true) {
			window.setUndecorated(true); // clean look
		}

		window.pack();

		window.setLocationRelativeTo(null); // to center window on the screen
		window.setVisible(true);

		gamePanel.setupGame();

		// start the game
		gamePanel.startGameThread();
	}
}
