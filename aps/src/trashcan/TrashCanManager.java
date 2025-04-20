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

    public void cleanBackpack(Player entity, TrashCan trashCan){
        switch (trashCan.getType()){
            case "plastic":
                entity.setPlasticCollected(0);
                break;
            case "metal":
                entity.setMetalCollected(0);
                break;
            case "paper":
                entity.setPaperCollected(0);
                break;
            case "glass":
                entity.setGlassCollected(0);
                break;
        }
    }

    private void startTrashCans(){
        GlassCan glassCan = new GlassCan(gp,this,24,21);
        PaperCan paperCan = new PaperCan(gp,this, 26, 21);
        MetalCan metalCan = new MetalCan(gp,this, 28, 21);
        PlasticCan plasticCan = new PlasticCan(gp,this, 30, 21);
    }

    public void add(TrashCan trashCan){
        trashCans.add(trashCan);
    }

    public ArrayList<TrashCan> getTrashCans() {
        return this.trashCans;
    }
}
