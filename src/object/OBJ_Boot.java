package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Boot extends SuperObject{
	GamePanel gp;
	public OBJ_Boot(GamePanel gp) {
	
		name = "Boot";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/object/boot.png"));
			uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
