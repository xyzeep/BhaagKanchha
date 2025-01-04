package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
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
	public final int maxWorldCol = 80; // Please change this later as you make bigger maps pawan
	public final int maxWorldRow = 12; // We wont have to change this ig because this will be fixed

	// dont think these two are necessary (until now at least)
	// public final int worldWidth = tileSize * maxWorldCol;
	// public final int worldHeight = tileSize * maxWorldRow; // self-explanatory
	// because the total worldWidth is each
	// // tile's size * no. of cols in world

	// GAME FPS
	int FPS = 60;

	TileManager tileM = new TileManager(this);

	KeyHandler keyH = new KeyHandler(this);

	// the reason behind making different classes for music and sound effects is to
	// avoid a weird bug that occurs when we do two things at the same time like
	// playing SE and stopping music
	public Sound music = new Sound();
	public Sound se = new Sound();

	public CollisionCheckerPlayer cCheckerPlayer = new CollisionCheckerPlayer(this);

	public AssetSetter aSetter = new AssetSetter(this);

	public UI ui = new UI(this);

	// The most important thing in a 2D/3D game is the existence of time.
	public Thread gameThread; // thread is something you can start and stop. Once the thread starts, it keeps
	// the game running(repeat a set a task) until we stop it.
	// basically when we start this gameThread, it automatically calls the run()
	// method (separately from the main program).

	// PLAYER AND OBJECT
	public Player player = new Player(this, keyH);
	public SuperObject obj[] = new SuperObject[10]; // we can replace the content of each slot during the game. [10]
													// means we can only have 10 objects at the same time.

	// GAME STATE
	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true); // enabling this improves rendering perfomance
		this.addKeyListener(keyH); // so that the game panel can recognise the key strokes
		this.setFocusable(true);
	}

	public void setupGame() {
		aSetter.setObject(); // we call this setupGame() method before the game starts(in Main.java)
		playMusic(0);

		gameState = playState;

	}

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
				repaint();
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

	public void update() {

		if (gameState == playState) {
			player.update();
			
		} else if (gameState == pauseState) {
			// nothing
		}
	}

	public void paintComponent(Graphics g) {
		// paintComponent() is a part of the java swing framework
		// Graphics is a class that has many helpful functions to draw objects on the
		// screen
		super.paintComponent(g); // super here means the parent (JPanel) class of this (GamePanel) class

		// Graphics2D class extends the Graphics class to providemore sophisticated
		// control over geometry, coordinate transformations, color management, and text
		// layout

		Graphics2D g2 = (Graphics2D) g; // so this means we changed the Graphics g to this Graphics2D class

		// DEBUGG
		long drawStart = 0;

		if (keyH.toggleDebug) {
			drawStart = System.nanoTime();
		}

		// ##########################

		// TILES
		tileM.draw(g2);

		// OBJECT
		for (int i = 0; i < obj.length; i++) {
			if (obj[i] != null) { // if we don't do this we might get a NullPointer error
				obj[i].draw(g2, this);
			}
		}

		// PLAYER
		player.draw(g2);

		// UI
		ui.draw(g2);

		// debug
		if (keyH.toggleDebug) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;

			g2.setColor(Color.red);
			g2.drawString("Draw time: " + passed, 50, 200);
			System.out.println("Total draw time :" + passed);
		}

		// ##########################

		g2.dispose(); // the program still works without this line but this is a good practice to save
						// some memory
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
