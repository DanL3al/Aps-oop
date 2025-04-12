package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {

    private int worldX,worldY,width,height;
    private int speed;
    private final int SCALE = 3;
    private String direction;
    private BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
    private int spriteCounter;
    private int spriteNum;
    private GamePanel gp;
    private final int SCREENX;
    private final int SCREENY;
    private Rectangle solidArea;
    private boolean collisionOn = false;

    public Player(GamePanel gp){
        this.gp = gp;
        setDefaultValues();
        getPlayerImage();
        SCREENX = gp.getScreenWidth() / 2 - gp.getTileSize() / 2;
        SCREENY = gp.getScreenHeight() / 2 - gp.getTileSize() / 2;

        solidArea = new Rectangle(14,22,18,26);
    }


    public void draw(Graphics2D g2){
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

        g2.drawImage(image,SCREENX,SCREENY,width,height,null);

    }

    public void update(boolean up,boolean down, boolean left,boolean right){

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
        try{
            up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/character_up_1.png"));
            up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/character_up_2.png"));
            down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/character_down_1.png"));
            down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/character_down_2.png"));
            left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/character_left_1.png"));
            left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/character_left_2.png"));
            right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/character_right_1.png"));
            right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/character_right_2.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
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
}
