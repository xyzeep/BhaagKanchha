package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Potion extends SuperObject{
	
	public OBJ_Potion() {
	
		name = "Blue_Potion";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/object/bluePotion.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
