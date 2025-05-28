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
        NPC npc_1 = new NPC(gp,"npcassets/bronny", "plastic");
        npc_1.setCoordinates(gp.getTileSize() * 23, gp.getTileSize() * 7);

        NPC npc_2 = new NPC(gp, "npcassets/larry", "glass");
        npc_2.setCoordinates(gp.getTileSize() * 25, gp.getTileSize() * 23);

        NPC npc_3 = new NPC(gp, "npcassets/danny", "metal");
        npc_3.setCoordinates(gp.getTileSize() * 38, gp.getTileSize() * 16);

        NPC npc_4 = new NPC(gp,"npcassets/vally" ,"paper");
        npc_4.setCoordinates(gp.getTileSize() * 19, gp.getTileSize() * 18);

        NPC npc_5 = new NPC(gp, "npcassets/betty", "paper");
        npc_5.setCoordinates(gp.getTileSize() * 20, gp.getTileSize() * 18);

        npcs.add(npc_1);npcs.add(npc_2);npcs.add(npc_3);npcs.add(npc_4);npcs.add(npc_5);
    }

    public void draw(Graphics2D g2){
        for (NPC npc : npcs) {
            npc.draw(g2);
        }
    }

    public void drawShadow(Graphics2D g2){
        int screenX,screenY;
        for (NPC npc : npcs){
            if(gp.getGameState() != gp.getSpectatingState()){
                screenX = npc.worldX - gp.getPlayerWorldX() + gp.getPlayerSCREENX();
                screenY = npc.worldY - gp.getPlayerWorldY() + gp.getPlayerSCREENY();
            }else{
                screenX = npc.worldX - gp.getTarget().getWorldX() + gp.getTarget().getSCREENX();
                screenY = npc.worldY - gp.getTarget().getWorldY() + gp.getTarget().getSCREENY();
            }
            npc.drawShadow(g2,screenX,screenY);
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
