package main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.Color;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable { // this class inherits JPanel class. The JPanel class works
															// as a game screen
	// our class has to implement runnable for us to be able to use a Thread. It has
	// a single method, run(), where we write the code for the task.

	private static final long serialVersionUID = 1L; // to avoid a werid warning (static final serialVersionUID)
	public int currentFPS;
	// Screen settigns
	final int originalTileSize = 16; // 16 x 16 tiles
	final int scale = 3; // 3x zoom for the tiles

	public final int tileSize = originalTileSize * scale; // tile size that gets displayed (16 x 3 = 48) 48 x 48
	public final int maxScreenCol = 20;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 1056 px
	public final int screenHeight = tileSize * maxScreenRow; // 576 px

	// LEVEL MAP SETTINGS
	public final int maxWorldCol = 164; // Please change this later as you make bigger maps pawan
	public final int maxWorldRow = 12; // We wont have to change this ig because this will be fixed

	// dont think these two are necessary (until now at least)
	// public final int worldWidth = tileSize * maxWorldCol;
	// public final int worldHeight = tileSize * maxWorldRow; // self-explanatory
	// because the total worldWidth is each
	// // tile's size * no. of cols in world

	// for full screen
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;

	BufferedImage tempScreen;
	Graphics2D g2;

	public boolean fullScreenOn = false;

	// GAME FPS
	int FPS = 60;

	TileManager tileM = new TileManager(this);

	KeyHandler keyH = new KeyHandler(this);

	// the reason behind making different classes for music and sound effects is to
	// avoid a weird bug that occurs when we do two things at the same time like
	// playing SE and stopping music
	public Sound music = new Sound();
	public Sound se = new Sound();

	public CollisionChecker cChecker = new CollisionChecker(this);

	public AssetSetter aSetter = new AssetSetter(this);

	public UI ui = new UI(this);

	public EventHandler eHandler = new EventHandler(this);

	public Config config = new Config(this);

	// The most important thing in a 2D/3D game is the existence of time.
	public Thread gameThread; // thread is something you can start and stop. Once the thread starts, it keeps
	// the game running(repeat a set a task) until we stop it.
	// basically when we start this gameThread, it automatically calls the run()
	// method (separately from the main program).

	// PLAYER AND OBJECT
	public Player player = new Player(this, keyH);
	public SuperObject obj[] = new SuperObject[10]; // we can replace the content of each slot during the game. [10]
													// means we can only have 10 objects at the same time.

	public Entity npc[] = new Entity[5];
	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int gameOverState = 3;

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true); // enabling this improves rendering perfomance
		this.addKeyListener(keyH); // so that the game panel can recognise the key strokes
		this.setFocusable(true);
	}

	public void setupGame() {
		config.loadConfig();
		aSetter.setObject(); // we call this setupGame() method before the game starts(in Main.java)
		aSetter.setNPC();
//		playMusic(0); // don't want music in titleScreen
		gameState = titleState;

		// full screen
		tempScreen = new BufferedImage(screenWidth2, screenHeight2, BufferedImage.TYPE_INT_ARGB_PRE);
		g2 = (Graphics2D) tempScreen.getGraphics();

		if (fullScreenOn == true) {
			setFullScreen();
		}

	}

	// the full screen logic goes thru 2 steps. first everything will be drawn in
	// tempScreen BTS and then in Jpanel

	public void startGameThread() {

		gameThread = new Thread(this); // we are passing this GamePanel class the thread's constructor. this is how we
										// instantiate a thread
		gameThread.start(); // starting the thread... this will automatically call the run method too
	}

	@Override
	public void run() {
		// usign the delta/accumulator method to implement FPS
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {

			// we check the current time in the beginning of the loop
			currentTime = System.nanoTime();

			// we subtract the lastTime from currentTime so that we can know how much time
			// has passed and divide it by drawInterval
			delta += (currentTime - lastTime) / drawInterval;

			timer += (currentTime - lastTime); // in every loop we add how much time has passed

			// and the currentTime becomes lastTime
			lastTime = currentTime;

			// ########### to be honest, rn i have no idea how this delta method woks. i
			// will figure it out soon i promise
			if (delta >= 1) {
				update();
//				repaint();
				drawToTempScreen();
				drawToScreen();
				delta--;
				drawCount++; // whenever the screen is repainted, the drawCounrter increases by 1
			}

			if (timer >= 1000000000) {
				System.out.println("FPS: " + drawCount); // to check id the function is working properly(i.e. if the
				// game is running in 60 FPS)
				currentFPS = drawCount;
				drawCount = 0;
				timer = 0;
			}
		}
	}

	public void drawToScreen() {
		Graphics g = getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
		g.dispose();
	}

	public void update() {

		if (gameState == playState) {
			// PLAYER
			player.update();
			// NPC
			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) {
					npc[i].update();
				}
			}

		} else if (gameState == pauseState) {
			// nothing
		}

	}

	public void drawToTempScreen() {
		// DEBUGG
		long drawStart = 0;

		if (keyH.toggleDebug) {
			drawStart = System.nanoTime();
		}

		// TITLE SCREEN
		if (gameState == titleState) {
			ui.draw(g2);

		}

		// others
		else {
			// TILES
			tileM.draw(g2);

			// OBJECT
			for (int i = 0; i < obj.length; i++) {
				if (obj[i] != null) { // if we don't do this we might get a NullPointer error
					obj[i].draw(g2, this);
				}
			}
			// NPC
			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) { // if we don't do this we might get a NullPointer error
					npc[i].draw(g2);
				}
			}

			// PLAYER
			player.draw(g2);

			// UI
			ui.draw(g2);

		}

		// debug
		if (keyH.toggleDebug) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setFont(new Font("Arial", Font.BOLD, 40));
			g2.setColor(Color.WHITE);
			g2.drawString("Draw time: " + passed, 50, 200);
			g2.drawString("FPS: " + currentFPS, 50, 200 + tileSize);

			setFullScreen();

		}
		// ##########################
	}

	public void setFullScreen() {
		// GET SCREEN

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice(); // these two lines are to get the local screen's info
		gd.setFullScreenWindow(Main.window);

		// get full screen width and height
		screenWidth2 = Main.window.getWidth();
		screenHeight2 = Main.window.getHeight();

	}

	public void playMusic(int i) {

		music.setFile(i); // set
		music.play(); // play
		music.loop(); // loop

	}

	public void pauseMusic() {

	}

	public void stopMusic() {

		music.stop();

	}

	public void playSoundEffect(int i) {
		se.setFile(i);
		se.play();
	}

}
