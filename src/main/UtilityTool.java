package main;

import java.awt.Graphics2D;
import java.awt.Image;
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
	
	public static BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
	    // scaling image with smoothing
	    Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

	    // new bufferedimage with new dimensions
	    BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

	    //draw resized image into bufferedimage
	    resizedImage.getGraphics().drawImage(scaledImage, 0, 0, null);

	    return resizedImage; 
	}

}
