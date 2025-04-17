package trashcan;

import main.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public class TrashCanManager {
    GamePanel gp;
    ArrayList<TrashCan> trashCans = new ArrayList<>();


    public TrashCanManager(GamePanel gp){
        this.gp = gp;
    }

    public void draw(Graphics2D g2){
        if(!trashCans.isEmpty()){
            for (int i = 0; i < trashCans.size(); i++) {
                TrashCan trashCan = trashCans.get(i);
                int screenX = trashCan.getWorldX() - gp.getPlayerWorldX() + gp.getPlayerSCREENX();
                int screenY = trashCan.getWorldY() - gp.getPlayerWorldY() + gp.getPlayerSCREENY();


                if (trashCan.getWorldX() + gp.getTileSize() > gp.getPlayerWorldX() - gp.getPlayerSCREENX() &&
                        trashCan.getWorldX() - gp.getTileSize() < gp.getPlayerWorldX() + gp.getPlayerSCREENX() &&
                        trashCan.getWorldY() + gp.getTileSize() > gp.getPlayerWorldY() - gp.getPlayerSCREENY() &&
                        trashCan.getWorldY() - gp.getTileSize() < gp.getPlayerWorldY() + gp.getPlayerSCREENY()) {
                    g2.drawImage(trashCan.getImage(), screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
                }
            }
        }
    }

    public void add(TrashCan trashCan){
        trashCans.add(trashCan);
    }

    public ArrayList<TrashCan> getTrashCans() {
        return trashCans;
    }
}
