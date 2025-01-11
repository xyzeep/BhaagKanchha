package main;

import java.awt.Rectangle;

import entity.NPC_Bob;

public class EventHandler {

	GamePanel gp;

	EventRect eventRect[][];
	
	public int damageInterval = 35;
	public int damageCounter = 0;
	public boolean takeDamage = true;

	public EventHandler(GamePanel gp) {
		this.gp = gp;

		eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow]; // 2px rectangle because we dont want player to die that easily ofc
										// ahahhahah
		int col = 0;
		int row = 0;
		while(col < gp.maxWorldCol && row < gp.maxScreenRow) {
			
			eventRect[col][row] = new EventRect();
			eventRect[col][row].x = 23;
			eventRect[col][row].y = 23;
			eventRect[col][row].width = 2;
			eventRect[col][row].height = 2;
			eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
			
			col++;
			if ( col == gp.maxWorldCol) {
				col = 0;
				row ++;
			}
		}
		
		

	}

	public void checkEvent() {

		// when you hit npc bob1
		if (hit(gp.npc[1].worldX / gp.tileSize, gp.npc[1].worldY / gp.tileSize, "any") == true) {
			takeDamage(gp.npc[1].worldX / gp.tileSize, gp.npc[1].worldY / gp.tileSize);
		}
		
	}

	public boolean hit(int col, int row, String reqDirection) {
		boolean hit = false;
		damageCounter++;
		
		
		// getting player's current solid area position
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.screenY + gp.player.solidArea.y;

		// getting event rect's solid area position too
		eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
		eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

		// using the intersect method to check if player and eventRect are touching each
		// other no homo
		if (gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
			hit = true;
		}

		// Reset Values to Defaults
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
		
		if(damageCounter > damageInterval) {
			takeDamage = true;
			damageCounter = 0;
		}
		
		return hit;

	}

	public void takeDamage(int col, int row) {
		
		if(takeDamage) {
			gp.playSoundEffect(6);
			gp.player.life--;
			takeDamage = false;
		}
	}

//	public void healPlayer(int col, int row) {
//		if (gp.player.life < 6) {
//			gp.player.life ++;
//		}
//		eventRect[col][row].eventDone = true;
//	}

}
