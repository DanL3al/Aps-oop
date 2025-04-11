package entity;

import java.awt.*;

public class Player {

    private int x,y,width,height;
    private int speed;
    private final int SCALE = 3;

    public Player(){
        initializeCoordinates();
    }


    public void draw(Graphics2D g2){
        g2.setColor(Color.red);
        g2.fillRect(x,y,width,height);
    }

    public void update(boolean up,boolean down, boolean left,boolean right){
        if(up){
            this.y -= speed;
        }else if(down){
            this.y += speed;
        }else if(left){
            this.x -= speed;
        }else if(right){
            this.x += speed;
        }
    }

    private void initializeCoordinates(){
        this.x = 0;
        this.y = 0;
        this.speed = 3;
        this.width = 16 * SCALE;
        this.height = 16 * SCALE;
    }

}
