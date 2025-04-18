package entity;

import main.GamePanel;
import main.UtilityTool;
import objects.Object;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
    private int solidAreaDefaultX,solidAreaDefaultY;
    private boolean collisionOn = false;
    private boolean collidingWithObject = false;
    private boolean pickingObject = false;
    private final long PICKUP_OBJECT_TIMER = 1000000000L;
    private long initialTime;
    private int plasticCollected,glassCollected,paperCollected,metalCollected;
    private int itensStored;
    private boolean inventoryFull;

    public Player(GamePanel gp){
        super(gp);
        setDefaultValues();
        getPlayerImage();
        SCREENX = gp.getScreenWidth() / 2 - gp.getTileSize() / 2;
        SCREENY = gp.getScreenHeight() / 2 - gp.getTileSize() / 2;
        solidAreaDefaultX = 14;
        solidAreaDefaultY = 22;
        solidArea = new Rectangle(solidAreaDefaultX + SCREENX,solidAreaDefaultY + SCREENY,20,26);
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

    public void update(boolean up,boolean down, boolean left,boolean right,boolean ePressed, long currentTime){

        itensStored = plasticCollected + metalCollected + paperCollected + glassCollected;

        inventoryFull = itensStored >= 2;

        if(itensStored > 0){
            gp.cChecker.checkTrashCanCollision(this,gp.getTrashCans());
        }

        gp.cChecker.checkPlayerObject(this,gp.getObjects());
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

        if(!ePressed){
            if(up || down || left || right){
                if(up){
                    this.direction = "up";
                }else if(down){
                    this.direction = "down";
                }else if(left){
                    this.direction = "left";
                }else if(right){
                    this.direction = "right";
                }

                collisionOn = false;
                gp.cChecker.checkTile(this);
                gp.cChecker.checkPlayerEntityCollision(this,gp.getNpcs());

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
        ArrayList<Object> obj = gp.getObjects();
        for (int i = 0; i < obj.size(); i++) {
            Object object = obj.get(i);
            int screenX = object.getWorldX() - worldX + SCREENX;
            int screenY = object.getWorldY() - worldY + SCREENY;

            Rectangle objectRect = new Rectangle(screenX, screenY, 48, 48);

            if(getSolidArea().intersects(objectRect)){
                if(!inventoryFull){
                    switch (object.getType()){
                        case "plastic":
                            plasticCollected++;
                            break;
                        case "metal":
                            metalCollected++;
                            break;
                        case "paper":
                            paperCollected++;
                            break;
                        case "glass":
                            glassCollected++;
                            break;
                    }
                    obj.remove(i);
                }
            }
        }
    }


    private void setSolidArea(){
        this.solidArea.x = this.SCREENX + solidAreaDefaultX;
        this.solidArea.y = this.SCREENY + solidAreaDefaultY;
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


    public int getSolidAreaX() {
        return solidArea.x;
    }
    public int getSolidAreaY() {
        return solidArea.y;
    }
    public int getSolidAreaWidth() {
        return solidArea.width;
    }
    public int getSolidAreaHeight() {
        return solidArea.height;
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
