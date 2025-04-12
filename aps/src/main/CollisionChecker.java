package main;

import entity.Player;

public class CollisionChecker {

    private GamePanel gp;

    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    public void checkTile(Player entity){
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidAreaX();
        int entityRightWorldX = entity.getWorldX() + entity.getSolidAreaX() + entity.getSolidAreaWidth();
        int entityTopWorldY = entity.getWorldY() + entity.getSolidAreaY();
        int entityBottomWorldY = entity.getWorldY() + entity.getSolidAreaY() + entity.getSolidAreaHeight();

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
}
