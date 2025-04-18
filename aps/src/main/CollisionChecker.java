package main;

import entity.Entity;
import entity.NPC;
import entity.Player;
import objects.Object;
import objects.ObjectManager;
import trashcan.TrashCan;
import trashcan.TrashCanManager;

import java.awt.*;
import java.util.ArrayList;

public class CollisionChecker {

    private GamePanel gp;

    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    public void checkTile(Entity entity){
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidAreaDefaultX();
        int entityRightWorldX = entity.getWorldX() + entity.getSolidAreaDefaultX() + entity.getSolidArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidAreaDefaultY();
        int entityBottomWorldY = entity.getWorldY() + entity.getSolidAreaDefaultY() + entity.getSolidArea().height;

        int entityLeftCol = entityLeftWorldX/gp.getTileSize();
        int entityRightCol = entityRightWorldX/gp.getTileSize();
        int entityTopRow = entityTopWorldY / gp.getTileSize();
        int entityBottomRow = entityBottomWorldY/gp.getTileSize();

        int tileNum1, tileNum2;

        switch (entity.getDirection()){
            case "up":
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.getMapTileNum(entityLeftCol,entityTopRow);
                tileNum2 = gp.getMapTileNum(entityRightCol,entityTopRow);
                if(gp.getTile(tileNum1).getCollision() || gp.getTile(tileNum2).getCollision()){
                    entity.setCollisionOn(true);
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.getMapTileNum(entityLeftCol,entityBottomRow);
                tileNum2 = gp.getMapTileNum(entityRightCol,entityBottomRow);
                if(gp.getTile(tileNum1).getCollision() || gp.getTile(tileNum2).getCollision()){
                    entity.setCollisionOn(true);
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.getMapTileNum(entityLeftCol,entityTopRow);
                tileNum2 = gp.getMapTileNum(entityLeftCol,entityBottomRow);
                if(gp.getTile(tileNum1).getCollision() || gp.getTile(tileNum2).getCollision()){
                    entity.setCollisionOn(true);
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.getMapTileNum(entityRightCol,entityTopRow);
                tileNum2 = gp.getMapTileNum(entityRightCol,entityBottomRow);
                if(gp.getTile(tileNum1).getCollision() || gp.getTile(tileNum2).getCollision()){
                    entity.setCollisionOn(true);
                }
                break;
        }
    }

    public void checkPlayerObject(Player player, ArrayList<Object> objects){

        if(!objects.isEmpty()){
            for (int i = 0; i < objects.size(); i++) {
                Object object = objects.get(i);
                int screenX = object.getWorldX() - gp.getPlayerWorldX() + gp.getPlayerSCREENX();
                int screenY = object.getWorldY() - gp.getPlayerWorldY() + gp.getPlayerSCREENY();

                Rectangle objectRect = new Rectangle(screenX, screenY, 48, 48);

                if(player.getSolidArea().intersects(objectRect)){
                    player.setCollidingWithObject(true);
                    object.setCollision(true);
                }else{
                    object.setCollision(false);
                }
            }
        }
    }

    public void checkPlayerEntityCollision(Entity entity, Entity[] target){

        for (int i = 0; i < target.length; i++) {
            if(target[i] != null){
                Entity npc = target[i];

                entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

                npc.getSolidArea().x = npc.getWorldX() + npc.getSolidArea().x;
                npc.getSolidArea().y = npc.getWorldY() + npc.getSolidArea().y;

                switch (entity.getDirection()){
                    case "up":
                        entity.getSolidArea().y -= entity.getSpeed();
                        if(entity.getSolidArea().intersects(npc.getSolidArea())){
                            entity.setCollisionOn(true);
                        }
                        break;
                    case "down":
                        entity.getSolidArea().y += entity.getSpeed();
                        if(entity.getSolidArea().intersects(npc.getSolidArea())){
                            entity.setCollisionOn(true);
                        }
                        break;
                    case "left":
                        entity.getSolidArea().x -= entity.getSpeed();
                        if(entity.getSolidArea().intersects(npc.getSolidArea())){
                            entity.setCollisionOn(true);
                        }
                        break;
                    case "right":
                        entity.getSolidArea().x += entity.getSpeed();
                        if(entity.getSolidArea().intersects(npc.getSolidArea())){
                            entity.setCollisionOn(true);
                        }
                        break;
                }

                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();
                npc.getSolidArea().x = npc.getSolidAreaDefaultX();
                npc.getSolidArea().y = npc.getSolidAreaDefaultY();
            }
        }
    }

    public void checkEntityPlayerCollision(Entity entity){

            Entity player = gp.getPlayer();

            entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
            entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

            player.getSolidArea().x = player.getWorldX() + player.getSolidArea().x;
            player.getSolidArea().y = player.getWorldY() + player.getSolidArea().y;

            switch (entity.getDirection()){
                case "up":
                    entity.getSolidArea().y -= entity.getSpeed();
                    if(entity.getSolidArea().intersects(player.getSolidArea())){
                        entity.setCollisionOn(true);
                    }
                    break;
                case "down":
                    entity.getSolidArea().y += entity.getSpeed();
                    if(entity.getSolidArea().intersects(player.getSolidArea())){
                        entity.setCollisionOn(true);
                    }
                    break;
                case "left":
                    entity.getSolidArea().x -= entity.getSpeed();
                    if(entity.getSolidArea().intersects(player.getSolidArea())){
                        entity.setCollisionOn(true);
                    }
                    break;
                case "right":
                    entity.getSolidArea().x += entity.getSpeed();
                    if(entity.getSolidArea().intersects(player.getSolidArea())){
                        entity.setCollisionOn(true);
                    }
                    break;
            }

            entity.getSolidArea().x = entity.getSolidAreaDefaultX();
            entity.getSolidArea().y = entity.getSolidAreaDefaultY();
            player.getSolidArea().x = player.getSolidAreaDefaultX();
            player.getSolidArea().y = player.getSolidAreaDefaultY();
    }

    public void checkTrashCanCollision(Player player, ArrayList<TrashCan> trashCans){

        if(!trashCans.isEmpty()){
            for (int i = 0; i < trashCans.size(); i++) {
                TrashCan trashCan = trashCans.get(i);
                int screenX = trashCan.getWorldX() - gp.getPlayerWorldX() + gp.getPlayerSCREENX();
                int screenY = trashCan.getWorldY() - gp.getPlayerWorldY() + gp.getPlayerSCREENY();

                Rectangle objectRect = new Rectangle(screenX, screenY, 48, 48);

                if(player.getSolidArea().intersects(objectRect)){
                    player.setCollisionOn(true);
                    switch (trashCan.getType()){
                        case "plastic":
                            player.setPlasticCollected(0);
                            break;
                        case "metal":
                            break;
                    }
                }
            }
        }

    }
}

