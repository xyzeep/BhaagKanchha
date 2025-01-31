package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {

	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	BufferedImage bgImage;

	public TileManager(GamePanel gp) {
		this.gp = gp;

		tile = new Tile[10];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

		// get level bg image
		try {
			bgImage = ImageIO.read(getClass().getResourceAsStream("/maps/level1_bg.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		getTileImage();
		loadMap("/maps/level1.txt");

	}

	public void getTileImage() {

		// WE SCALED THE TILE IMAGES BEFOREHAND (and not during the game loop) to
		// improve perfomance

		setup(0, "Empty", false, false);

		setup(1, "platformTop", true, false);

		setup(2, "spike1", false, true);

		setup(3, "lava", true, true);

		setup(4, "platformBlock", true, false);

		setup(5, "Empty", false, false);

		setup(6, "crate", true, false);
		
		setup(7, "platformRight", true, false);
		
		setup(8, "platformLeft", true, false);

	}

	public void setup(int index, String imageName, boolean collision, boolean deadly) {
		UtilityTool uTool = new UtilityTool();

		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
			tile[index].deadly = deadly;
		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	// function to load the map data
	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath); // we use this to import the text file
			BufferedReader br = new BufferedReader(new InputStreamReader(is)); // and this to read the content of the
																				// text file
			// this is just a format so no need to be scared

			int col = 0;
			int row = 0;

			// a loop to read every line
			while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

				String line = br.readLine();

				while (col < gp.maxWorldCol) {
					String numbers[] = line.split(" "); // what we are doing here is storing every number of the row in
														// an array

					int num = Integer.parseInt(numbers[col]);

					// we store the extracted number(num) in our mapTileNum array
					mapTileNum[col][row] = num; // this array will store our map tiles data
					col++;
				}
				if (col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}

			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2) {

		// draw the bg first
		g2.drawImage(bgImage, 0, 0, gp.screenWidth, gp.screenHeight, null);

		int worldCol = 0;
		int worldRow = 0;

		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

			int tileNum = mapTileNum[worldCol][worldRow];

			int worldX = worldCol * gp.tileSize; // first we check the tile's worldX
			int worldY = worldRow * gp.tileSize; // then we check the tile's worldY
			int screenX = worldX - gp.player.worldX + gp.player.screenX; // where ot draw the tile with respect to
																			// player's position on the map
			int screenY = worldY; // this means keep the maps screenY regardless of the player's screenY

			// WORDS OF WISDOM: worldX is the position on the map(this col and that row)
			// WHILE screenX and screenY is where on the screen we draw it! (unit is in
			// pixels)

			// ATTENTION PLEASE!!!!!!!! FIX THIS BECAUSE YES 12/26/2024 IAM GOING INSANE
			// the problem in is the right side
			if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
					&& worldX < gp.player.worldX + (gp.screenWidth - gp.player.screenX)) {
				g2.drawImage(tile[tileNum].image, screenX, screenY, null); // no need to add width and height cause
																			// already scaled
			}

			worldCol++; // we increase this worldCol by 1 everytime after drawing a tile this is so easy

			if (worldCol == gp.maxWorldCol) { // when the col hits max no. of worldCol then we reset it to zero and then
												// increase row by one 😆😆
				worldCol = 0;
				worldRow++;
			}
		}

	}

}
