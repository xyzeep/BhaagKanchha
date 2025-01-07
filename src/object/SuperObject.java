package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.UtilityTool;

public class SuperObject {

	public BufferedImage image, image2, image3;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(10, 10, 38, 38);
	public int solidAreaDefaultX = solidArea.x;
	public int solidAreaDefaultY = solidArea.y;
	
	UtilityTool uTool = new UtilityTool();
	
	// draw the object
	public void draw(Graphics2D g2, GamePanel gp) {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.screenY + gp.player.screenY;
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
				&& worldX < gp.player.worldX + (gp.screenWidth - gp.player.screenX)) {
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
	}
}
