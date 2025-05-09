package entity;

import buttons.Button;
import buttons.Event;
import main.GamePanel;
import objects.Soda;
import objects.Wine;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class NPC extends Entity{

    private String state = "unconscious";
    private String trashDropType;
    private int dropTrash = 0;
    private Button talkButton = new Button("t");
    private Event eventButton = new Event();
    private boolean target = false;



    public NPC(GamePanel gp, String path, String type) {
        super(gp);
        getImage(path);
        this.trashDropType = type;
    }

    private void getImage(String imagePath){
        up1 = setup(imagePath + "-up-1");
        up2 = setup(imagePath + "-up-2");
        down1 = setup(imagePath + "-down-1");
        down2 = setup(imagePath + "-down-2");
        left1 = setup(imagePath + "-left-1");
        left2 = setup(imagePath + "-left-2");
        right1 = setup(imagePath + "-right-1");
        right2 = setup(imagePath + "-right-2");
    }

    @Override
    public void draw(Graphics2D g2){
        int screenX = worldX - gp.getPlayerWorldX() + gp.getPlayerSCREENX();
        int screenY = worldY - gp.getPlayerWorldY() + gp.getPlayerSCREENY();

        if(worldX + gp.getTileSize()> gp.getPlayerWorldX() - gp.getPlayerSCREENX()&&
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
                if(target){
                    if(collidingWithEntity){
                        talkButton.draw(g2,screenX,screenY,gp.getTileSize() * 2 / 3, gp.getTileSize() * 2 / 3);
                    }else{
                        eventButton.draw(g2,screenX,screenY,gp.getTileSize() * 2 / 3,gp.getTileSize() * 2 / 3);
                    }
                }
        }
    }

    @Override
    public void setAction(){

        if(!talking){
            actionLockCounter++;
            dropTrash ++;

            if(actionLockCounter == 120){
                Random random = new Random();
                int i = random.nextInt(100)+1;
                if(i <= 25){
                    this.direction = "up";
                }else if(i > 25 && i <= 50){
                    this.direction = "down";
                }else if(i > 50 && i <= 75){
                    this.direction = "left";
                }else{
                    this.direction = "right";
                }
                actionLockCounter = 0;
            }

            if(dropTrash == 180){
                if(state.equals("unconscious")){
                    switch (trashDropType){
                        case "plastic":
                            Soda soda = new Soda(this.worldX,this.worldY);
                            gp.addObject(soda);
                            break;
                        case "metal":
                            break;
                        case "glass":
                            Wine wine = new Wine(this.worldX,this.worldY);
                            gp.addObject(wine);
                            break;
                    }
                }
                dropTrash = 0;
            }
        }
    }



    public boolean isTarget() {
        return target;
    }

    public void setTarget(boolean target) {
        this.target = target;
    }

    public void setCoordinates(int worldX, int worldY){
        this.worldX = worldX;
        this.worldY = worldY;
    }

    public void setState(String stateChange){
        this.state = stateChange;
    }

    public boolean isCollidingWithEntity() {
        return collidingWithEntity;
    }

    public void setCollidingWithEntity(boolean collidingWithEntity) {
        this.collidingWithEntity = collidingWithEntity;
    }
}
