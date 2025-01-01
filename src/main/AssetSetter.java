package main;

import object.OBJ_Potion;
import object.OBJ_Star;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	
	public void setObject() {
		
		gp.obj[0] = new OBJ_Potion();
		gp.obj[0].worldX = 17 * gp.tileSize;
		gp.obj[0].worldY = 5 * gp.tileSize;
		
		
		gp.obj[1] = new OBJ_Potion();
		gp.obj[1].worldX = 40 * gp.tileSize;
		gp.obj[1].worldY = 6 * gp.tileSize;
		
		gp.obj[2] = new OBJ_Star();
		gp.obj[2].worldX = 20 * gp.tileSize;
		gp.obj[2].worldY = 6 * gp.tileSize;
		
		
		
	}
	
}
