package trashcan;

import entity.Player;
import main.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public class TrashCanManager {
    GamePanel gp;
    ArrayList<TrashCan> trashCans = new ArrayList<>();


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
        GlassCan glassCan = new GlassCan(gp,this,19,22);
        PlasticCan plasticCan = new PlasticCan(gp,this, 21, 22);
        PaperCan paperCan = new PaperCan(gp,this, 26, 22);
        MetalCan metalCan = new MetalCan(gp,this, 28, 22);
    }

    public void add(TrashCan trashCan){
        trashCans.add(trashCan);
    }

    public ArrayList<TrashCan> getTrashCans() {
        return this.trashCans;
    }
}
