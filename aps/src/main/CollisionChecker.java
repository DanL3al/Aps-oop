package main;

import entity.Player;
import objects.Object;
import objects.ObjectManager;

import java.awt.*;
import java.util.ArrayList;

public class CollisionChecker {

    private GamePanel gp;

    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    public void checkTile(Player entity){
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidAreaDefaultX();
        int entityRightWorldX = entity.getWorldX() + entity.getSolidAreaDefaultX() + entity.getSolidAreaWidth();
        int entityTopWorldY = entity.getWorldY() + entity.getSolidAreaDefaultY();
        int entityBottomWorldY = entity.getWorldY() + entity.getSolidAreaDefaultY() + entity.getSolidAreaHeight();

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

    public void checkObject(Player player, ArrayList<Object> objects){

        if(!objects.isEmpty()){
            for (int i = 0; i < objects.size(); i++) {
                Object object = objects.get(i);
                int screenX = object.getWorldX() - gp.getPlayerWorldX() + gp.getPlayerSCREENX();
                int screenY = object.getWorldY() - gp.getPlayerWorldY() + gp.getPlayerSCREENY();

                Rectangle objectRect = new Rectangle(screenX, screenY, 48, 48);

                if(player.getSolidArea().intersects(objectRect)){
                    player.setCollidingWithObject(true);
                }
            }
        }
    }
}

