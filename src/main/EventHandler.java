package main;

import java.awt.Rectangle;

import entity.NPC_Bob;

public class EventHandler {

	GamePanel gp;

	Rectangle eventRect;

	int eventRectDefaultX, eventRectDefaultY;
	
	public int damageInterval = 35;
	public int damageCounter = 0;
	public boolean takeDamage = true;

	public EventHandler(GamePanel gp) {
		this.gp = gp;

		eventRect = new Rectangle(); // 2px rectangle because we dont want player to die that easily ofc
										// ahahhahah
		eventRect.x = 23;
		eventRect.y = 23;
		eventRect.width = 2;
		eventRect.height = 2;
		eventRectDefaultX = eventRect.x;
		eventRectDefaultY = eventRect.y;

	}

	public void checkEvent() {

		// when you hit npc bob1
		if (hit(gp.npc[0].worldX / gp.tileSize, gp.npc[0].worldY / gp.tileSize, "any") == true) {
			damagePit();
		}
	}

	public boolean hit(int eventCol, int eventRow, String reqDirection) {
		boolean hit = false;
		damageCounter++;
		
		
		// getting player's current solid area position
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.screenY + gp.player.solidArea.y;

		// getting event rect's solid area position too
		eventRect.x = eventCol * gp.tileSize + eventRect.x;
		eventRect.y = eventRow * gp.tileSize + eventRect.y;

		// using the intersect method to check if player and eventRect are touching each
		// other no homo
		if (gp.player.solidArea.intersects(eventRect)) {
			hit = true;
		}

		// Reset Values to Defaults
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect.x = eventRectDefaultX;
		eventRect.y = eventRectDefaultY;
		
		if(damageCounter > damageInterval) {
			takeDamage = true;
			damageCounter = 0;
		}
		
		return hit;

	}

	public void damagePit() {
		
		if(takeDamage) {
			gp.playSoundEffect(6);
			gp.player.life--;
			takeDamage = false;
		}
		
		
		
		
		
	}

	public void healing() {
		//
	}

}
