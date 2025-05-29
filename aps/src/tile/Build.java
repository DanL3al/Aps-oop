package tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Build {

    BufferedImage image;
    int worldX, worldY;
    int width,height;
    Rectangle solidArea;


    public Build(){

    }

    public Build(int worldX, int worldY,int width, int height, String name) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.width = width;
        this.height = height;
        setImage(name);
        solidArea = new Rectangle(0,0,width,height);
    }

    public void draw(Graphics2D g2, int screenX, int screenY){
        g2.drawImage(image,screenX,screenY,width,height,null);
    }

    private void setImage(String name){
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("buildingassets/" + name + ".png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        this.image = image;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }
}
