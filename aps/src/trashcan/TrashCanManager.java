package trashcan;

import entity.Entity;
import entity.Player;
import main.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class TrashCanManager {
    GamePanel gp;
    ArrayList<TrashCan> trashCans = new ArrayList<>();
    ArrayList<Integer> trashCanPositions = new ArrayList<>();


    public TrashCanManager(GamePanel gp){
        this.gp = gp;
        startTrashCans();
    }

    public void draw(Graphics2D g2){
        int screenX = 0,screenY = 0;

        if(!trashCans.isEmpty()){
            for (int i = 0; i < trashCans.size(); i++) {
                TrashCan trashCan = trashCans.get(i);
                if(gp.getGameState() != gp.getSpectatingState()){
                    screenX = trashCan.getWorldX() - gp.getPlayerWorldX() + gp.getPlayerSCREENX();
                    screenY = trashCan.getWorldY() - gp.getPlayerWorldY() + gp.getPlayerSCREENY();
                    if (trashCan.getWorldX() + gp.getTileSize() > gp.getPlayerWorldX() - gp.getPlayerSCREENX() &&
                            trashCan.getWorldX() - gp.getTileSize() < gp.getPlayerWorldX() + gp.getPlayerSCREENX() &&
                            trashCan.getWorldY() + gp.getTileSize() > gp.getPlayerWorldY() - gp.getPlayerSCREENY() &&
                            trashCan.getWorldY() - gp.getTileSize() < gp.getPlayerWorldY() + gp.getPlayerSCREENY()) {
                        if(gp.getTalkingDone()){
                            trashCan.event.draw(g2,screenX,screenY, 32,32);
                        }
                        g2.drawImage(trashCan.getImage(), screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
                    }
                }else{
                    screenX = trashCan.getWorldX() - gp.getTarget().getWorldX() + gp.getTarget().getSCREENX();
                    screenY = trashCan.getWorldY() - gp.getTarget().getWorldY() + gp.getTarget().getSCREENY();
                    if (trashCan.getWorldX() + gp.getTileSize() > gp.getTarget().getWorldX() - gp.getTarget().getSCREENX() &&
                            trashCan.getWorldX() - gp.getTileSize() < gp.getTarget().getWorldX() + gp.getTarget().getSCREENX() &&
                            trashCan.getWorldY() + gp.getTileSize() > gp.getTarget().getWorldY() - gp.getTarget().getSCREENY() &&
                            trashCan.getWorldY() - gp.getTileSize() < gp.getTarget().getWorldY() + gp.getTarget().getSCREENY()) {
                        if(gp.getTalkingDone()){
                            trashCan.event.draw(g2,screenX,screenY, 32,32);
                        }
                        g2.drawImage(trashCan.getImage(), screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
                    }
                }

            }
        }
    }

    private void startTrashCans(){
        GlassCan glassCan = new GlassCan(gp,this,24,21);
        PaperCan paperCan = new PaperCan(gp,this, 26, 21);
        MetalCan metalCan = new MetalCan(gp,this, 28, 21);
        PlasticCan plasticCan = new PlasticCan(gp,this, 30, 21);
        trashCanPositions.add(glassCan.worldX);trashCanPositions.add(glassCan.worldX);trashCanPositions.add(glassCan.worldX);trashCanPositions.add(glassCan.worldX);
    }

    public void add(TrashCan trashCan){
        trashCans.add(trashCan);
    }

    public boolean checkPosition(Entity entity){
        for (int i = 0; i < trashCans.size(); i++) {
            if(trashCans.get(i).worldY < entity.getWorldY()){
                return true;
            }
        }
        return false;
    }


    public ArrayList<TrashCan> getTrashCans() {
        return this.trashCans;
    }
}
