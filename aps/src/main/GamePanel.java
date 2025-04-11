package main;

import entity.Player;
import keyhandler.KeyHandler;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    private final int originalTileSize = 16;
    private final int scale = 3;
    private final int tileSize = originalTileSize * scale;
    private final int maxScreenCol = 16;
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;


    private KeyHandler keyH;
    private Thread thread;
    private Player player;
    private final int FPS = 60;


    public GamePanel(){
        keyH = new KeyHandler();
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setFocusable(true);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        player = new Player();

    }

    public void startGameThread(){
        thread = new Thread(this);
        thread.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
    }

    public void update(){
        player.update(keyH.isUpPressed(),keyH.isDownPressed(),keyH.isLeftPressed(),keyH.isRightPressed());
    }


    @Override
    public void run() {
        double drawInterval = 1000000000f/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(thread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
            }
        }
    }
}
