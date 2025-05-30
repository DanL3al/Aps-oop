package main;

import entity.Entity;
import entity.NPC;
import entity.NpcManager;
import entity.Player;
import keyhandler.KeyHandler;
import objects.Object;
import objects.ObjectManager;
import tile.Tile;
import tile.TileManager;
import trashcan.PlasticCan;
import trashcan.TrashCan;
import trashcan.TrashCanManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
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
    private TrashCanManager trashCanManager = new TrashCanManager(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    private NpcManager npcManager = new NpcManager(this);
    private UI ui = new UI(this);
    private Rain rain = new Rain(this);
    private final int FPS = 60;


    private boolean raining = false;
    private int rainingStage = 0;
    private int climateChange = 1;
    private int climateTotallyChanged = 2;
    private int weakRain = 3;
    private int tempest = 4;

    private final int MAXWORLDCOL = 50;
    private final int MAXWORLDROW = 50;
    private final int WORLDWIDTH = tileSize * MAXWORLDCOL;
    private final int WORLDHEIGHT = tileSize * MAXWORLDROW;

    private int gameState;
    private int menuState = 0;
    private int playState = 1;
    private int pauseState = 2;
    private int dialogueState = 3;
    private int tutorialStateOne = 4;
    private int tutorialStateTwo = 5;
    private int gameWonState = 6;
    private int spectatingState = 7;
    private int talkingDoneState = 8;
    private int endingState = 9;



    public GamePanel(){
        keyH = new KeyHandler(this, ui);
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setFocusable(true);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setBackground(Color.black);
        player = new Player(this);
        tileM = new TileManager(this);
        objectManager = new ObjectManager(this);
        gameState = menuState;
        raining = false;
    }

    public void startGameThread(){
        thread = new Thread(this);
        thread.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if(gameState == menuState){
            ui.draw(g2);
        }else{
            tileM.draw(g2);
            objectManager.draw(g2);
            player.drawShadow(g2);
            npcManager.drawShadow(g2);
            npcManager.draw(g2);
            if(trashCanManager.checkPosition(player)){
                trashCanManager.draw(g2);
                player.draw(g2);
            }else{
                player.draw(g2);
                trashCanManager.draw(g2);
            }if(raining){
                rain.draw(g2);
            }
            ui.draw(g2);
        }
        g2.dispose();
    }

    public void update(){
        if(gameState == playState || gameState == spectatingState || gameState == talkingDoneState || gameState == endingState){
            player.update(keyH.isUpPressed(),keyH.isDownPressed(),keyH.isLeftPressed(),keyH.isRightPressed(),keyH.isePressed(),keyH.istPressed());
            rain.update();
            npcManager.update();
        }
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


    public int getPlasticRemaining(){
        return objectManager.getPlasticRemaining();
    }

    public int getGlassRemaining(){
        return objectManager.getGlassRemaining();
    }

    public void setPlasticRemaining(){
        objectManager.setPlasticRemaining();
    }

    public void setGlassRemaining(){
        objectManager.setGlassRemaining();
    }

    public void setPaperRemaining(){
        objectManager.setPaperRemaining();
    }
    public void setMetalRemaining(){
        objectManager.setMetalRemaining();
    }

    public int getMetalCollected(){
        return player.getMetalCollected();
    }

    public int getMetalRemaining(){
        return objectManager.getMetalRemaining();
    }

    public int getPaperCollected(){
        return player.getPaperCollected();
    }
    public int getPaperRemaining(){
        return objectManager.getPaperRemaining();
    }

    public int getGlassCollected(){
        return player.getGlassCollected();
    }

    public BufferedImage getTargetImage(){
        return npcManager.getTargetImage();
    }

    public int getPlayState() {
        return playState;
    }

    public int getMenuState() {
        return menuState;
    }

    public void setCurrentDialogue(String dialogue){
        ui.setCurrentDialogue(dialogue);
    }

    public int getMiddleOfTheScreen(){
        return this.getWORLDDHEIGHT() / 2;
    }

    public int getDialogueState() {
        return dialogueState;
    }

    public ArrayList<NPC> getNpcs(){
        return npcManager.getNpcs();
    }

    public void setHasTarget(boolean condition){
        npcManager.setHasTarget(condition);
    }


    public Player getPlayer() {
        return player;
    }


    public boolean getCollidingWithNPC(){
        return player.isCollidingWithNpc();
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
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
    public int getPlasticCollected(){
        return player.getPlasticCollected();
    }
    public boolean getInventoryFull(){
        return player.isInventoryFull();
    }

    public TrashCanManager getTrashCanManager() {
        return trashCanManager;
    }

    public ArrayList<TrashCan> getTrashCans(){
        return trashCanManager.getTrashCans();
    }

    public int getGameState() {
        return gameState;
    }
    public int getPauseState(){
        return this.pauseState;
    }
    public int getTutorialStateOne() { return tutorialStateOne; }

    public int getTutorialStateTwo() {
        return tutorialStateTwo;
    }

    public int getGameWonState() {
        return gameWonState;
    }

    public int getSpectatingState() {
        return spectatingState;
    }

    public int getTalkingDoneState() {
        return talkingDoneState;
    }

    public int getEndingState() {
        return endingState;
    }

    public NPC getTarget(){
        return npcManager.getTarget();
    }

    public boolean getTalkingDone(){
        return player.isTalkingDone();
    }

    public BufferedImage getPlasticImage(){
        return objectManager.getPlasticImage();
    }

    public BufferedImage getMetalImage(){
        return objectManager.getMetalImage();
    }

    public BufferedImage getGlassImage(){
        return objectManager.getGlassImage();
    }

    public BufferedImage getPaperImage(){
        return objectManager.getPaperImage();
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public void setRainingStage(int rainingStage) {
        this.rainingStage = rainingStage;
    }

    public int getRainingStage() {
        return rainingStage;
    }

    public int getClimateChange() {
        return climateChange;
    }

    public int getClimateTotallyChanged() {
        return climateTotallyChanged;
    }

    public int getWeakRain() {
        return weakRain;
    }

    public int getTempest() {
        return tempest;
    }
}

