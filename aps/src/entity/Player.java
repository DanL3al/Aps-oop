package entity;

import main.GamePanel;
import main.UtilityTool;
import objects.Object;
import trashcan.TrashCan;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Player extends Entity{

    private int worldX,worldY,width,height;
    private int speed;
    private final int SCALE = 3;
    private String direction;
    private BufferedImage up1,up2, up_interacting_1,down1,down2,left1,left2,left_interacting_1,right1,right2,right_interacting_1;
    private int spriteCounter;
    private int spriteNum;
    private final int SCREENX;
    private final int SCREENY;
    private Rectangle solidArea;
    private int solidAreaDefaultX,solidAreaDefaultY;
    private boolean collisionOn = false;
    private boolean collidingWithObject = false;
    private boolean collidingWithNpc = false;
    private boolean collidingWithTrashCan = false;
    private boolean pickingObject = false;
    private final long PICKUP_OBJECT_TIMER = 1000000000L;
    private long initialTime;
    private int plasticCollected,glassCollected,paperCollected,metalCollected;
    private int itensStored;
    private boolean inventoryFull;
    private NPC entityTalking;

    public Player(GamePanel gp){
        super(gp);
        setDefaultValues();
        getPlayerImage();
        SCREENX = gp.getScreenWidth() / 2 - gp.getTileSize() / 2;
        SCREENY = gp.getScreenHeight() / 2 - gp.getTileSize() / 2;
        solidAreaDefaultX = 12;
        solidAreaDefaultY = 12;
        solidArea = new Rectangle(solidAreaDefaultX,solidAreaDefaultY,24,24);
        setDialogues();
    }


    public void draw(Graphics2D g2){
        BufferedImage image = null;

        switch (direction){
            case "up":
                if(pickingObject){
                    image = up_interacting_1;
                }else{
                    if(spriteNum == 1){
                        image = up1;
                    }else{
                        image = up2;
                    }
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }else{
                    image = down2;
                }
                break;
            case "left":
                if(pickingObject){
                    image = left_interacting_1;
                }else{
                    if(spriteNum == 1){
                        image = left1;
                    }else{
                        image = left2;
                    }
                }
                break;
            case "right":
                if(pickingObject){
                    image = right_interacting_1;
                }else{
                    if(spriteNum == 1){
                        image = right1;
                    }else{
                        image = right2;
                    }
                }
                break;
        }
        g2.drawImage(image,SCREENX,SCREENY,width,height,null);
    }

    public void update(boolean up,boolean down, boolean left,boolean right,boolean ePressed, boolean tPressed){

        //System.out.println("Player X: " + worldX + solidAreaDefaultX);

        if(!ePressed){
            if(up || down || left || right){
                if(up){
                    this.direction = "up";
                }else if(down){
                    this.direction = "down";
                }else if(left){
                    this.direction = "left";
                }else{
                    this.direction = "right";
                }

                collisionOn = false;
                gp.cChecker.checkTile(this);
                gp.cChecker.checkPlayerEntityCollision(this,gp.getNpcs());
                gp.cChecker.checkEntityTrashCanCollision(this,gp.getTrashCans());

                if(collidingWithTrashCan){
                    cleanBackpack();
                }

                if(!collisionOn){
                    switch(direction){
                        case "up":
                            this.worldY -= speed;
                            break;
                        case "down":
                            this.worldY += speed;
                            break;
                        case "left":
                            this.worldX -= speed;
                            break;
                        case "right":
                            this.worldX += speed;
                            break;
                    }
                }

                spriteCounter++;
                if(spriteCounter > 12){
                    if(spriteNum == 1){
                        spriteNum = 2;
                    }else{
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
            }
        }

        itensStored = plasticCollected + metalCollected + paperCollected + glassCollected;

        inventoryFull = itensStored >= 10;

        gp.cChecker.checkObjectCollision(this,gp.getObjects());
        if(collidingWithObject){
            if(ePressed){
                pickingObject = true;
                if(!inventoryFull){
                    getObject();
                }
            }else{
                pickingObject = false;
            }
        }

        if(teste()){
            if(tPressed){
                talk();
            }
        }

        /*if(collidingWithNpc){
            if(tPressed){
                switch (direction){
                    case "up":
                        entityTalking.setDirection("down");
                        break;
                    case "down":
                        entityTalking.setDirection("up");
                        break;
                    case "left":
                        entityTalking.setDirection("right");
                        break;
                    case "right":
                        entityTalking.setDirection("left");
                        break;
                }
                entityTalking.setTalking(true);
                talkToNpc(entityTalking);
                gp.setCurrentDialogue(dialogues[0]);
                gp.setGameState(gp.getDialogueState());
            }else{
                entityTalking.setTalking(false);
            }
        }*/


    }

    private void setDefaultValues(){
        this.worldX = gp.getTileSize() * 23;
        this.worldY = gp.getTileSize() * 21;
        this.speed = 3;
        this.width = 16 * SCALE;
        this.height = 16 * SCALE;
        direction = "down";
        spriteNum = 1;
    }

    private void getPlayerImage(){
        up1 = setup("assets/player_up_1");
        up2 = setup("assets/player_up_2");
        up_interacting_1 = setup("assets/player_interacting_top_1");
        down1 = setup("assets/player_down_1");
        down2 = setup("assets/player_down_2");
        left1 = setup("assets/player_left_1");
        left2 = setup("assets/player_left_2");
        left_interacting_1 = setup("assets/player_interacting_left_1");
        right1 = setup("assets/player_right_1");
        right2 = setup("assets/player_right_2");
        right_interacting_1 = setup("assets/player_interacting_right_1");
    }


    private void getObject(){
        ArrayList<Object> objects = gp.getObjects();
        for (int i = 0; i < objects.size(); i++) {
            if(objects.get(i).isCollision()){
                switch (objects.get(i).getType()){
                    case "plastic":
                        if(!inventoryFull){
                            plasticCollected++;
                        }
                        break;
                    case "metal":
                        if(!inventoryFull){
                            metalCollected++;
                        }
                        break;
                    case "paper":
                        if(!inventoryFull){
                            paperCollected++;
                        }
                        break;
                    case "glass":
                        if(!inventoryFull){
                            glassCollected++;
                        }
                        break;
                }
                objects.remove(i);
            }
        }
    }

    private void cleanBackpack(){
        ArrayList<TrashCan> trashCans = gp.getTrashCans();
        for (TrashCan trashCan : trashCans) {
            if(trashCan.isCollision()){
                discard(trashCan.getType());
            }
        }
    }

    private boolean teste(){
        for (NPC npc : gp.getNpcs()){
            if(npc.isCollidingWithEntity()){
                return true;
            }
        }return false;
    }

    private void talk() {
        for (NPC npc : gp.getNpcs()) {
            if (npc != null) {
                npc.setTalking(true);
                npc.setState("conscious");
                gp.setCurrentDialogue(getDialogue());
                gp.setGameState(gp.getDialogueState());
                npc.setTalking(false);
                npc.setTarget(false);
                gp.setTarget();
            }
        }
    }
                /*switch (direction) {
                    case "up":
                        entity.setDirection("down");
                        break;
                    case "down":
                        entity.setDirection("up");
                        break;
                    case "left":
                        entity.setDirection("right");
                        break;
                    case "right":
                        entity.setDirection("left");
                        break;
                }*/




            /*if(npc.collidingWithEntity){
                switch (direction){
                    case "up":
                        npc.setDirection("down");
                        break;
                    case "down":
                        npc.setDirection("up");
                        break;
                    case "left":
                        npc.setDirection("right");
                        break;
                    case "right":
                        npc.setDirection("left");
                        break;
                }
                npc.setTalking(true);
                gp.setCurrentDialogue(dialogues[0]);
                gp.setGameState(gp.getDialogueState());
            }else{
                npc.setTalking(false);
            }*/



    private void setDialogues(){
        this.dialogues[0] = "Você é ateu? cê ta tirando no bagulho tio";
        this.dialogues[1] = "Baniram meu boneco? vou trollar a partida";
    }

    private String getDialogue(){
        return dialogues[1];
    }

    public boolean isCollidingWithTrashCan() {
        return collidingWithTrashCan;
    }

    public void setCollidingWithTrashCan(boolean collidingWithTrashCan) {
        this.collidingWithTrashCan = collidingWithTrashCan;
    }

    private void discard(String type){
        switch (type){
            case "plastic":
                plasticCollected = 0;
                break;
            case "metal":
                metalCollected = 0;
                break;
            case "paper":
                paperCollected = 0;
                break;
            case "glass":
                glassCollected = 0;
                break;
        }
    }


    public void setGlassCollected(int glassCollected) {
        this.glassCollected = glassCollected;
    }

    public void setPaperCollected(int paperCollected) {
        this.paperCollected = paperCollected;
    }

    public void setMetalCollected(int metalCollected) {
        this.metalCollected = metalCollected;
    }

    private void talkToNpc(NPC entity){
        entity.setState("conscious");
    }

    public void getEntity(NPC entity){
        this.entityTalking = entity;
    }

    public void setCollidingWithNpc(boolean collidingWithNpc) {
        this.collidingWithNpc = collidingWithNpc;
    }

    public boolean isCollidingWithNpc(){
        return this.collidingWithNpc;
    }

    public boolean isCollidingWithObject() {
        return collidingWithObject;
    }

    public void setCollidingWithObject(boolean collidingWithObject) {
        this.collidingWithObject = collidingWithObject;
    }

    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }

    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public int getSCREENX() {
        return SCREENX;
    }

    public int getSCREENY() {
        return SCREENY;
    }

    public BufferedImage getDown1(){
        return this.down1;
    }

    public String getDirection(){
        return this.direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setCollisionOn(boolean collision){
        this.collisionOn = collision;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public int getMetalCollected() {
        return metalCollected;
    }

    public int getPaperCollected() {
        return paperCollected;
    }

    public int getGlassCollected() {
        return glassCollected;
    }

    public int getPlasticCollected() {
        return plasticCollected;
    }

    public boolean isInventoryFull() {
        return inventoryFull;
    }

    public void setPlasticCollected(int plasticCollected) {
        this.plasticCollected = plasticCollected;
    }
}
