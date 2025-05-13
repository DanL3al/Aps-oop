package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class NpcManager {

    GamePanel gp;
    private ArrayList<NPC> npcs = new ArrayList<>();
    int targetTeste;
    private ArrayList<NPC> target = new ArrayList<>();

    public NpcManager(GamePanel gp){
        this.gp = gp;
        setNpcs();
        targetTeste = npcs.size() - 1;
        npcs.get(targetTeste).setTarget(true);
    }

    private void setNpcs(){
        NPC npc_1 = new NPC(gp,"npcassets/alienigena", "plastic");
        npc_1.setCoordinates(gp.getTileSize() * 21, gp.getTileSize() * 21);

        NPC npc_2 = new NPC(gp, "npcassets/npc", "glass");
        npc_2.setCoordinates(gp.getTileSize() * 25, gp.getTileSize() * 23);

        npcs.add(npc_1);npcs.add(npc_2);
        target = npcs;
    }

    public void draw(Graphics2D g2){
        for (NPC npc : npcs) {
            npc.draw(g2);
        }
    }

    public void update(){
        for (NPC npc : npcs) {
            npc.update();
        }
    }

    public void setTarget(){
        if(targetTeste > 0){
            targetTeste--;
        }
        for (int i = target.size() - 1; i >= 0; i--) {
            NPC npc = target.get(i);
            if(i == targetTeste){
                npc.setTarget(true);
            }
        }
    }


    public ArrayList<NPC> getNpcs() {
        return npcs;
    }

    public BufferedImage getTargetImage(){
        for (NPC npc : npcs){
            if(npc.isTarget()){
                return npc.getDown1();
            }
        }
        return null;
    }

}
