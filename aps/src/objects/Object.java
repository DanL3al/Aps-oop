package objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Object {

    int worldX,worldY;
    BufferedImage image;
    Rectangle solidArea;
    boolean collision;
    int solidAreaDefaultX,solidAreaDefaultY;
    String type;

    //pass x and y position through the constructor
    public Object(int x, int y, String imgPath){
        this.worldX = x;
        this.worldY = y;
        this.collision = true;
        this.image = setImage(imgPath);
        solidAreaDefaultX = x;
        solidAreaDefaultY = y;
        this.solidArea = new Rectangle(x,y,48,48);
    }


    BufferedImage setImage(String imgPath){
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(imgPath));
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public String getType() {
        return type;
    }

    public boolean isCollision() {
        return collision;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getWorldY() {
        return worldY;
    }

    public int getWorldX() {
        return worldX;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }
}
