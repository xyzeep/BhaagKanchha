package main;

import entity.NPC_Bob;
import object.OBJ_Heal;
import object.OBJ_Heart;
import object.OBJ_Potion;
import object.OBJ_Star;

public class AssetSetter {

	GamePanel gp;

	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}

	public void setObject() {
		gp.obj[0] = new OBJ_Potion(gp);
		gp.obj[0].worldX = 77 * gp.tileSize;
		gp.obj[0].worldY = 5 * gp.tileSize;

		gp.obj[2] = new OBJ_Star(gp);
		gp.obj[2].worldX = 24 * gp.tileSize;
		gp.obj[2].worldY = 6 * gp.tileSize;

		gp.obj[3] = new OBJ_Star(gp);
		gp.obj[3].worldX = 33 * gp.tileSize;
		gp.obj[3].worldY = 5 * gp.tileSize;

		gp.obj[4] = new OBJ_Star(gp);
		gp.obj[4].worldX = 140 * gp.tileSize;
		gp.obj[4].worldY = 4 * gp.tileSize;

		gp.obj[5] = new OBJ_Heal(gp);
		gp.obj[5].worldX = 45 * gp.tileSize;
		gp.obj[5].worldY = 5 * gp.tileSize;
	}

	public void setNPC() {



		gp.npc[0] = new NPC_Bob(gp);
		gp.npc[0].worldX = 100 * gp.tileSize;
		gp.npc[0].worldY = 3 * gp.tileSize;
		gp.npc[0].screenY = gp.npc[0].worldY;
		
		gp.npc[1] = new NPC_Bob(gp);
		gp.npc[1].worldX = 20 * gp.tileSize;
		gp.npc[1].worldY = 6 * gp.tileSize;
		gp.npc[1].screenY = gp.npc[1].worldY;
		
	}

}
