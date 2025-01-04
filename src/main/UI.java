package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_Star;

public class UI {
	
	GamePanel gp;
	Font arial_40;
	BufferedImage keyImage;
	
	
	public UI(GamePanel gp) {
		this.gp = gp;
		// we instantiate outside the gameloop because we dont make a class 60 times a sec
		arial_40 = new Font("Consolas", Font.PLAIN, 30); // this is how to setup a font using graphics2D
		OBJ_Star key = new OBJ_Star();
		keyImage = key.image;
	}
	
	public void draw(Graphics2D g2) {
		
		g2.setFont(arial_40); // and the just set it like this here
		g2.setColor(Color.white);
		g2.drawImage(keyImage, 10, 5, gp.tileSize - 5 , gp.tileSize - 5, null);
		
		g2.drawString("x" + gp.player.stars, 55, 38);
		
		g2.drawString("FPS:" + gp.currentFPS, 840, 38);
		
		
	}
	
}
