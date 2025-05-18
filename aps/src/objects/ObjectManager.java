package objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class ObjectManager {

    GamePanel gp;
    ArrayList<Object> objects;
    BufferedImage plasticImage, metalImage, paperImage, glassImage;
    int plasticRemaining, glassRemaining, paperRemaining, metalRemaining;

    public ObjectManager(GamePanel gp){
        objects = new ArrayList<>();
        this.gp = gp;
        setImages();
    }

    public void addObject(Object object){
        this.objects.add(object);
    }

    public void draw(Graphics2D g2){
        int screenX = 0, screenY = 0;

        if(!objects.isEmpty()){
            for (Object object : objects) {
                if(gp.getGameState() != gp.getSpectatingState()){
                    screenX = object.getWorldX() - gp.getPlayerWorldX() + gp.getPlayerSCREENX();
                    screenY = object.getWorldY() - gp.getPlayerWorldY() + gp.getPlayerSCREENY();

                    if (    object.getWorldX() + gp.getTileSize() > gp.getPlayerWorldX() - gp.getPlayerSCREENX() &&
                            object.getWorldX() - gp.getTileSize() < gp.getPlayerWorldX() + gp.getPlayerSCREENX() &&
                            object.getWorldY() + gp.getTileSize() > gp.getPlayerWorldY() - gp.getPlayerSCREENY() &&
                            object.getWorldY() - gp.getTileSize() < gp.getPlayerWorldY() + gp.getPlayerSCREENY()) {
                        g2.drawImage(object.getImage(), screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
                        if(object.collision){
                            if(gp.getGameState() != gp.getPauseState()){
                                object.button.draw(g2,screenX,screenY,32,32);
                            }
                        }
                    }


                }else{
                    screenX = object.getWorldX() - gp.getTarget().getWorldX() + gp.getTarget().getSCREENX();
                    screenY = object.getWorldY() - gp.getTarget().getWorldY() + gp.getTarget().getSCREENY();


                    if (    object.getWorldX() + gp.getTileSize() > gp.getTarget().getWorldX() - gp.getTarget().getSCREENX() &&
                            object.getWorldX() - gp.getTileSize() < gp.getTarget().getWorldX() + gp.getTarget().getSCREENX() &&
                            object.getWorldY() + gp.getTileSize() > gp.getTarget().getWorldY() - gp.getTarget().getSCREENY() &&
                            object.getWorldY() - gp.getTileSize() < gp.getTarget().getWorldY() + gp.getTarget().getSCREENY()) {
                        g2.drawImage(object.getImage(), screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
                        if(object.collision){
                            if(gp.getGameState() != gp.getPauseState()){
                                object.button.draw(g2,screenX,screenY,32,32);
                            }
                        }
                    }
                }
            }
        }
    }

    public void getObjectsRemaining(){
        for (int i = 0; i < objects.size(); i++) {
            Object object = objects.get(i);
            switch (object.getType()){
                case "plastic":
                    plasticRemaining++;
                    break;
                case "glass":
                    glassRemaining++;
                    break;
                case "metal":
                    break;
                case "paper":
                    break;
            }
        }
    }


    public ArrayList<Object> getObjects() {
        return objects;
    }

    private void setImages(){
        try {
            plasticImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objectAssets/soda-can-2.png"));
            metalImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objectAssets/metal-can.png"));
            paperImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objectAssets/paper.png"));
            glassImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objectAssets/glass.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public BufferedImage getPlasticImage() {
        return plasticImage;
    }

    public BufferedImage getMetalImage() {
        return metalImage;
    }

    public BufferedImage getPaperImage() {
        return paperImage;
    }

    public BufferedImage getGlassImage() {
        return glassImage;
    }

    public int getPlasticRemaining() {
        return plasticRemaining;
    }

    public int getGlassRemaining() {
        return glassRemaining;
    }

    public void setPlasticRemaining() {
        this.plasticRemaining += 1;
    }

    public void setGlassRemaining() {
        this.glassRemaining +=1;
    }

    public int getPaperRemaining() {
        return paperRemaining;
    }

    public int getMetalRemaining() {
        return metalRemaining;
    }

    public void setMetalRemaining() {
        this.metalRemaining += 1;
    }

    public void setPaperRemaining() {
        this.paperRemaining +=1;
    }
}
