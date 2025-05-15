package objects;

import main.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public class ObjectManager {

    GamePanel gp;
    ArrayList<Object> objects;

    public ObjectManager(GamePanel gp){
        objects = new ArrayList<>();
        this.gp = gp;
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

    public ArrayList<Object> getObjects() {
        return objects;
    }
}
