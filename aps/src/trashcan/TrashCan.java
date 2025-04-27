package trashcan;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class TrashCan {

    GamePanel gp;
    TrashCanManager trashCanManager;
    int worldX,worldY;
    Color color;
    BufferedImage image;
    Rectangle solidArea;
    int solidAreaDefaultX, solidAreaDefaultY;
    String type;
    boolean collision;

    public TrashCan(GamePanel gp, TrashCanManager trashCanManager,String type,int worldX, int worldY){
        this.trashCanManager = trashCanManager;
        this.gp = gp;
        this.worldX = worldX * gp.getTileSize();
        this.worldY = worldY * gp.getTileSize();
        this.type = type;
        this.collision = false;
        setTrashCanType(type);
        this.solidAreaDefaultX = 10;
        this.solidAreaDefaultY = 10;
        this.solidArea = new Rectangle(solidAreaDefaultX,solidAreaDefaultY,32,32);
        trashCanManager.add(this);
    }


    private void setTrashCanType(String type){
        switch (type){
            case "plastic":
                color = Color.RED;
                break;
            case "metal":
                color = Color.YELLOW;
                break;
            case "glass":
                color = Color.GREEN;
                break;
            case "paper":
                color = Color.BLUE;
                break;
        }
        this.image = setImage(type);
    }

    private BufferedImage setImage(String type){
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("trashcanassets/"+type+"-can.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }

    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
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

    public BufferedImage getImage() {
        return image;
    }

    public String getType() {
        return type;
    }
}
