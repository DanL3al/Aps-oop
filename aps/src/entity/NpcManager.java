package entity;

import main.GamePanel;

import java.awt.*;

public class NpcManager {

    GamePanel gp;
    private NPC[] npcs = new NPC[10];

    public NpcManager(GamePanel gp){
        this.gp = gp;
        setNpcs();
    }

    private void setNpcs(){
        npcs[0] = new NPC(gp,"npcassets/alienigena", "plastic");
        npcs[0].setCoordinates(gp.getTileSize() * 21, gp.getTileSize() * 21);

        npcs[1] = new NPC(gp, "npcassets/rosena", "glass");
        npcs[1].setCoordinates(gp.getTileSize() * 25, gp.getTileSize() * 23);
    }

    public void draw(Graphics2D g2){
        for (int i = 0; i < npcs.length; i++) {
            if(npcs[i] != null){
                npcs[i].draw(g2);
            }
        }
    }

    public void update(){
        for (int i = 0; i < npcs.length; i++) {
            if(npcs[i] != null){
                npcs[i].update();
            }
        }
    }

    public NPC[] getNpcs() {
        return npcs;
    }
}
