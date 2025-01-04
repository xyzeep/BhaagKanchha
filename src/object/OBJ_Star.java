package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Star extends SuperObject{
	GamePanel gp;
	public OBJ_Star(GamePanel gp) {
	
		name = "Star";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/object/star.png"));
			uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
