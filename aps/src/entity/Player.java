package entity;

import main.GamePanel;
import objects.Object;
import trashcan.TrashCan;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
    private final int solidAreaDefaultX;
    private final int solidAreaDefaultY;
    private boolean collisionOn = false;
    private boolean collidingWithObject = false;
    private boolean collidingWithNpc = false;
    private boolean collidingWithTrashCan = false;
    private boolean pickingObject = false;
    private int plasticCollected,glassCollected,paperCollected,metalCollected;
    private int itensStored;
    private boolean inventoryFull;
    private ArrayList<Integer> dialogueIndexUsed;

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

        if(colliding()){
            if(tPressed){
                talk();
            }
        }
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

    private boolean colliding(){
        for (NPC npc : gp.getNpcs()){
            if(npc.isCollidingWithEntity()){
                return true;
            }
        }
        return false;
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

    private int getDialogueIndex(){
        int randomIndex = 0;
        int numberOfDialogues = getNumberOfDialogues();
        if(dialogueIndexUsed.size() == numberOfDialogues){
            dialogueIndexUsed.clear();
        }
        while(dialogueIndexUsed.contains(randomIndex)){
            randomIndex = (int) Math.round(Math.random() * (getNumberOfDialogues() - 1));
        }
        dialogueIndexUsed.add(randomIndex);

        return randomIndex;
    }

    private void setDialogues(){
        this.dialogues[0] = "Não polua a cidade!";
        this.dialogues[1] = "Descarte seus resíduos nas lixeiras";
        this.dialogues[2] = "Uma chuva forte se aproxima, \nO lixo pode entupir os bueiros";
        this.dialogues[3] = "Cada lixeira tem seu tipo próprio de descarte\nFaça o descarte correto!";
        this.dialogues[4] = "Você receberá uma multa por poluir a cidade!";
        dialogueIndexUsed = new ArrayList<>();
    }

    private String getDialogue(){
        return dialogues[getDialogueIndex()];
    }

    private int getNumberOfDialogues(){
        for (int i = 0; i < dialogues.length; i++) {
            if(dialogues[i] == null){
                return i;
            }
        }
        return dialogues.length;
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

    public boolean isCollidingWithNpc(){
        return this.collidingWithNpc;
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
}
