package main;

import object.OBJ_Boot;
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
		gp.obj[1].worldX = 8 * gp.tileSize;
		gp.obj[1].worldY = 5 * gp.tileSize;
		
		gp.obj[2] = new OBJ_Star();
		gp.obj[2].worldX = 20 * gp.tileSize;
		gp.obj[2].worldY = 6 * gp.tileSize;

		gp.obj[3] = new OBJ_Star();
		gp.obj[3].worldX = 24 * gp.tileSize;
		gp.obj[3].worldY = 6 * gp.tileSize;
		
		gp.obj[4] = new OBJ_Star();
		gp.obj[4].worldX = 28 * gp.tileSize;
		gp.obj[4].worldY = 6 * gp.tileSize;
		
		gp.obj[5] = new OBJ_Potion();
		gp.obj[5].worldX = 14 * gp.tileSize;
		gp.obj[5].worldY = 3 * gp.tileSize;
		
		gp.obj[6] = new OBJ_Potion();
		gp.obj[6].worldX = 10 * gp.tileSize;
		gp.obj[6].worldY = 3 * gp.tileSize;
		
//		gp.obj[5] = new OBJ_Boot();
//		gp.obj[5].worldX = 6 * gp.tileSize;
//		gp.obj[5].worldY = 6 * gp.tileSize;
//		
		
		
	}
	
}
