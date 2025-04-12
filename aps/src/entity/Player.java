package entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {

    private int x,y,width,height;
    private int speed;
    private final int SCALE = 3;
    private String direction;
    private BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
    private int spriteCounter;
    private int spriteNum;

    public Player(){
        setDefaultValues();
        getPlayerImage();
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

        g2.drawImage(image,x,y,width,height,null);


    }

    public void update(boolean up,boolean down, boolean left,boolean right){
        if(up){
            this.y -= speed;
            this.direction = "up";
        }else if(down){
            this.y += speed;
            this.direction = "down";
        }else if(left){
            this.x -= speed;
            this.direction = "left";
        }else if(right){
            this.x += speed;
            this.direction = "right";
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

    private void setDefaultValues(){
        this.x = 0;
        this.y = 0;
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

}
