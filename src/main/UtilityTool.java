package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

// a class to write functions that contribute to the effiency and overall expereince of the game
public class UtilityTool {

	// to scale the images outside the gameLoop for efficeincy
	public BufferedImage scaleImage(BufferedImage original, int width, int height) {
		BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(original, 0, 0, width, height, null);
		g2.dispose();

		return scaledImage;
	}

}
