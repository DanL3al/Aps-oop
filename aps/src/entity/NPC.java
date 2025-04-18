package entity;

import main.GamePanel;

import java.util.Random;
import java.util.Vector;

public class NPC extends Entity{

    private int[] pointA = new int[]{300,300};

    public NPC(GamePanel gp, String path) {
        super(gp);
        getImage(path);
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
    public void setAction(){

        actionLockCounter++;

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
    }

    public void setCoordinates(int worldX, int worldY){
        this.worldX = worldX;
        this.worldY = worldY;
    }
}
