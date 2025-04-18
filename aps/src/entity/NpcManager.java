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
        npcs[0] = new NPC(gp,"npcassets/alienigena");
        npcs[0].setCoordinates(gp.getTileSize() * 21, gp.getTileSize() * 21);
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
