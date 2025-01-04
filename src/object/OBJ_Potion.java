package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Potion extends SuperObject{
	
	GamePanel gp;
	
	public OBJ_Potion(GamePanel gp) {
	
		name = "Blue_Potion";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/object/bluePotion.png"));
			uTool.scaleImage(image, gp.tileSize, gp.tileSize);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
