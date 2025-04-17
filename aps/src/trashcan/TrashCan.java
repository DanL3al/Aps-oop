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
    Rectangle collision;
    String type;
    int trashCollected;

    public TrashCan(GamePanel gp, TrashCanManager trashCanManager,String type){
        this.trashCanManager = trashCanManager;
        this.gp = gp;
        worldX = 24 * gp.getTileSize();
        worldY = 21 * gp.getTileSize();
        this.type = type;
        setTrashCanType(type);
        this.collision = new Rectangle(worldX,worldY,gp.getTileSize(),gp.getTileSize());
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
