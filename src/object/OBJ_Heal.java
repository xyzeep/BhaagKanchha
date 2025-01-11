package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Heal extends SuperObject{
	
	GamePanel gp;
	
	public OBJ_Heal(GamePanel gp) {
	
		name = "Heal";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/object/fullHeart.png"));
			uTool.scaleImage(image, gp.tileSize - 10 , gp.tileSize - 10 );
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
