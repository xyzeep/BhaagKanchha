package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;

public class NPC_Bob extends Entity {
	
	public NPC_Bob(GamePanel gp) {
		super(gp);
		
		
		direction = "down";
		speed = 1;
		solidArea  = new Rectangle(0, 2, 30, 42);	
		
		getImage();
		setAction();
	}
	
	public void getImage() {
		right1 = setup("/npc/bob_right1");
		right2 = setup("/npc/bob_right2");
		left1 = setup("/npc/bob_left1");
		left2 = setup("/npc/bob_left2");
	}
	
	@Override
	public void setAction() {
		
		actionLockCounter++;
		
		if (actionLockCounter >= 100) {
			Random random = new Random();
			// get a random number from 1 to 100
			int i = random.nextInt(100) + 1;
			
			if(i <= 50) {
				direction = "left";
			}
			
			else if (i >= 50) {
				direction  = "right";
			}
			
			actionLockCounter = 0;
		}
		
		
		
	}
}
