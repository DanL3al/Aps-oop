package main;

import entity.Player;
import keyhandler.KeyHandler;
import objects.Object;
import objects.ObjectManager;
import tile.Tile;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
    private TileManager tileM;
    private ObjectManager objectManager;
    public CollisionChecker cChecker = new CollisionChecker(this);
    private final int FPS = 60;

    private final int MAXWORLDCOL = 50;
    private final int MAXWORLDROW = 50;
    private final int WORLDWIDTH = tileSize * MAXWORLDCOL;
    private final int WORLDHEIGHT = tileSize * MAXWORLDROW;



    public GamePanel(){
        keyH = new KeyHandler();
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setFocusable(true);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setBackground(Color.black);
        player = new Player(this);
        tileM = new TileManager(this);
        objectManager = new ObjectManager(this);
    }

    public void startGameThread(){
        thread = new Thread(this);
        thread.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileM.draw(g2);
        objectManager.draw(g2);
        player.draw(g2);
    }

    public void update(){
        player.update(keyH.isUpPressed(),keyH.isDownPressed(),keyH.isLeftPressed(),keyH.isRightPressed(),keyH.isePressed());
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

    public int getMaxScreenCol() {
        return maxScreenCol;
    }

    public int getMaxScreenRow() {
        return maxScreenRow;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getMAXWORLDCOL() {
        return MAXWORLDCOL;
    }

    public int getMAXWORLDROW() {
        return MAXWORLDROW;
    }

    public int getWORLDWIDTH() {
        return WORLDWIDTH;
    }

    public int getWORLDDHEIGHT() {
        return WORLDHEIGHT;
    }

    public int getPlayerWorldX(){
        return player.getWorldX();
    }

    public int getPlayerWorldY(){
        return player.getWorldY();
    }

    public int getPlayerSCREENX(){
        return player.getSCREENX();
    }
    public int getPlayerSCREENY(){
        return player.getSCREENY();
    }
    public int getMapTileNum(int col, int row){
        return tileM.getMapTileNum(col,row);
    }
    public Tile getTile(int index){
        return tileM.getTile(index);
    }
    public void addObject(Object object){
        objectManager.addObject(object);
    }
    public ArrayList<Object> getObjects(){
        return objectManager.getObjects();
    }
}
