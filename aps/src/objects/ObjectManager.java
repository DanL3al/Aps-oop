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

        if(!objects.isEmpty()){
            for (Object object : objects) {
                int screenX = object.getWorldX() - gp.getPlayerWorldX() + gp.getPlayerSCREENX();
                int screenY = object.getWorldY() - gp.getPlayerWorldY() + gp.getPlayerSCREENY();

                if (object.getWorldX() + gp.getTileSize() > gp.getPlayerWorldX() - gp.getPlayerSCREENX() &&
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
            }
        }
    }

    public ArrayList<Object> getObjects() {
        return objects;
    }
}
