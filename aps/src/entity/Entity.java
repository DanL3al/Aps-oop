package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Entity {

    GamePanel gp;
    int worldX, worldY;
    int speed;
    BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    String direction;
    int spriteCounter;
    int spriteNum;
    Rectangle solidArea;
    int solidAreaDefaultX, solidAreaDefaultY;
    boolean collisionOn = false;
    public int actionLockCounter = 0;

    public Entity(GamePanel gp){
        this.gp = gp;
        this.direction = "down";
        spriteCounter = 0;
        spriteNum = 1;
        this.speed = 3;
        solidArea = new Rectangle(0,0,gp.getTileSize(),gp.getTileSize());
        solidAreaDefaultX = 14;
        solidAreaDefaultY = 22;
    }

    void setAction(){

    }

    void update(){
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkEntityPlayerCollision(this);

        if(!collisionOn){
            switch (direction){
                case "up": worldY -= speed;break;
                case "down": worldY += speed;break;
                case "left": worldX -= speed;break;
                case "right":worldX += speed;break;
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


    BufferedImage setup(String imagePath){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaledImage(image,gp.getTileSize(),gp.getTileSize());

        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public void draw(Graphics2D g2){
        int screenX = worldX - gp.getPlayerWorldX() + gp.getPlayerSCREENX();
        int screenY = worldY - gp.getPlayerWorldY() + gp.getPlayerSCREENY();

        if( worldX + gp.getTileSize()> gp.getPlayerWorldX() - gp.getPlayerSCREENX()&&
            worldX - gp.getTileSize()< gp.getPlayerWorldX() + gp.getPlayerSCREENX()&&
            worldY + gp.getTileSize()> gp.getPlayerWorldY() - gp.getPlayerSCREENY()&&
            worldY - gp.getTileSize()< gp.getPlayerWorldY() + gp.getPlayerSCREENY()){


            BufferedImage image = null;

            switch (direction){
                case "up":
                    if(spriteNum == 1){
                        image = up1;
                    }else{
                        image = up2;
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
                    if(spriteNum == 1){
                        image = left1;
                    }else{
                        image = left2;
                    }
                    break;
                case "right":
                    if(spriteNum == 1){
                        image = right1;
                    }else{
                        image = right2;
                    }
                    break;
            }
            g2.drawImage(image,screenX,screenY,gp.getTileSize(),gp.getTileSize(),null);
        }
    }


    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public int getSpeed() {
        return speed;
    }

    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }

    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }

    public boolean isCollisionOn() {
        return collisionOn;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public String getDirection() {
        return direction;
    }
}
