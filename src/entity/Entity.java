package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    
    public int worldX, worldY; //players/entity's postition in the world map
    public int screenX, screenY; //only applicable for player (until now)
    public int speed;
    public String type;
    public BufferedImage img1, img2, img3, img4;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea; // the actual colliding part
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    
}
