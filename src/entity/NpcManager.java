package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class NpcManager {

    GamePanel gp;
    private ArrayList<NPC> npcs = new ArrayList<>();
    private ArrayList<NPC> target = new ArrayList<>();
    private int spawnCounter = 0;
    private boolean hasTarget = false;
    private int targetTimer = 0;


    public NpcManager(GamePanel gp){
        this.gp = gp;
        setNpcs();
    }

    private void setNpcs(){
        NPC npc_1 = new NPC(gp,"npcassets/alienigena", "plastic");
        npc_1.setCoordinates(gp.getTileSize() * 23, gp.getTileSize() * 10);

        NPC npc_2 = new NPC(gp, "npcassets/npc", "glass");
        npc_2.setCoordinates(gp.getTileSize() * 25, gp.getTileSize() * 23);

        NPC npc_3 = new NPC(gp, "npcassets/rosena", "metal");
        npc_3.setCoordinates(gp.getTileSize() * 38, gp.getTileSize() * 16);

        NPC npc_4 = new NPC(gp,"assets/character" ,"paper");
        npc_4.setCoordinates(gp.getTileSize() * 19, gp.getTileSize() * 18);

        npcs.add(npc_1);npcs.add(npc_2);npcs.add(npc_3);npcs.add(npc_4);
    }

    public void draw(Graphics2D g2){
        for (NPC npc : npcs) {
            npc.draw(g2);
        }
    }

    public void update(){
        if(target.isEmpty() || !hasTarget && target.size() != npcs.size()){
            spawnCounter++;
            if(spawnCounter == 120){
                setTarget();
                spawnCounter = 0;
            }
        }

        for (NPC npc : npcs) {
            npc.update();
            if(gp.getGameState() == gp.getSpectatingState()){
                targetTimer++;
                if(targetTimer == 120){
                    gp.setGameState(gp.getPlayState());
                    targetTimer = 0;
                }
            }
        }
    }

    public void setTarget(){
        int i = (int) Math.round((Math.random() * (npcs.size() - 1)));
        while(target.contains(npcs.get(i))){
            i = (int) Math.round((Math.random() * (npcs.size() - 1)));
        }
        target.add(npcs.get(i));
        npcs.get(i).setTarget(true);
        hasTarget = true;
        gp.setGameState(gp.getSpectatingState());
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

    public void setHasTarget(boolean hasTarget) {
        this.hasTarget = hasTarget;
    }
    
    public NPC getTarget(){
        if(hasTarget){
            for (int i = 0; i < npcs.size(); i++) {
                if(npcs.get(i).isTarget()){
                    return npcs.get(i);
                }
            }
        }
        return null;
    }
    
}
