package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable { // this class inherits JPanel class. The JPanel class works
                                                            // as a game screen
    // our class has to implement runnable for us to be able to use a Thread. It has
    // a single method, run(), where we write the code for the task.
    // Screen settigns
    final int originalTileSize = 16; // 16 x 16 tiles
    final int scale = 3; // 3x zoom for the tiles

    final int tileSize = originalTileSize * scale; // tile size that gets displayed (16 x 3 = 48) 48 x 48
    final int maxScreenCol = 20;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; // 1056 px
    final int screenHeight = tileSize * maxScreenRow; // 576 px

    // GAME FPS
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();

    // The most important thing in a 2D/3D game is the existence of time.
    Thread gameThread; // thread is something you can start and stop. Once the thread starts, it keeps
                       // the game running(repeat a set a task) until we stop it.
    // basically when we start this gameThread, it automatically calls the run()
    // method (separately from the main program).

    // Set players default position
    int playerX = 50;
    int playerY = 50;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // enabling this improves rendering perfomance
        this.addKeyListener(keyH); // so that the game panel can recognise the key strokes
        this.setFocusable(true);
    }

    public void startGameThread() {

        gameThread = new Thread(this); // we are passing this GamePanel class the thread's constructor. this is how we
                                       // instantiate a thread
        gameThread.start(); // starting the thread... this will automatically call the run method too
    }

    @Override
    // public void run() {

    // double drawInterval = 1000000000/FPS; // 1 bn nanosecs = 1 sec.. this means
    // we draw the screen every 0.016666 secs so we can draw the screen 60 times/sec
    // double nextDrawTime = System.nanoTime() + drawInterval;

    // while (gameThread != null) {

    // long currentTime = System.nanoTime(); // Returns the current value of the
    // JVM's high-res time, in nano secs
    // // we could've also used milisecs but nano is more precise

    // // 1. UPDATE: update information such as payer position
    // update();
    // // 2. DRAW: draw the screen with the updated information
    // repaint(); // it might be a bit confusing but this is how we call the
    // paintComponent()
    // // method
    // }
    // }

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
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {

        if (keyH.upPressed == true) {
            playerY -= playerSpeed;
        }

        if (keyH.downPressed == true) {
            playerY += playerSpeed;
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

        g2.setColor(Color.WHITE);

        g2.fillRect(playerX, playerY, 48, 48); // we can draw stuff using this Graphics2D like a rectangle

        g2.dispose(); // the program still works without this line but this is a good practice to save
                      // some memory
    }
}
