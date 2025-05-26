package main;

import entity.Entity;
import entity.NPC;
import entity.Player;
import objects.Object;
import trashcan.TrashCan;

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


    public void checkPlayerEntityCollision(Player player, ArrayList<NPC> npcs){

        for (NPC npc : npcs){
            player.getSolidArea().x = player.getWorldX() + player.getSolidAreaDefaultX();
            player.getSolidArea().y = player.getWorldY() + player.getSolidAreaDefaultY();

            npc.getSolidArea().x = npc.getWorldX() + npc.getSolidAreaDefaultX();
            npc.getSolidArea().y = npc.getWorldY() + npc.getSolidAreaDefaultY();

            switch (player.getDirection()) {
                case "up":
                    player.getSolidArea().y -= player.getSpeed();
                    if (player.getSolidArea().intersects(npc.getSolidArea())) {
                        player.setCollisionOn(true);
                        if(npc.isTarget()){
                            player.setCollidingWithEntity(true);
                            npc.setCollidingWithEntity(true);
                        }
                    }else{
                        player.setCollidingWithEntity(false);
                        npc.setCollidingWithEntity(false);
                    }
                    break;
                case "down":
                    player.getSolidArea().y += player.getSpeed();
                    if (player.getSolidArea().intersects(npc.getSolidArea())) {
                        player.setCollisionOn(true);
                        if(npc.isTarget()){
                            player.setCollidingWithEntity(true);
                            npc.setCollidingWithEntity(true);
                        }
                    }else{
                        player.setCollidingWithEntity(false);
                        npc.setCollidingWithEntity(false);
                    }
                    break;
                case "left":
                    player.getSolidArea().x -= player.getSpeed();
                    if (player.getSolidArea().intersects(npc.getSolidArea())) {
                        player.setCollisionOn(true);
                        if(npc.isTarget()){
                            player.setCollidingWithEntity(true);
                            npc.setCollidingWithEntity(true);
                        }
                    }else{
                        player.setCollidingWithEntity(false);
                        npc.setCollidingWithEntity(false);
                    }
                    break;
                case "right":
                    player.getSolidArea().x += player.getSpeed();
                    if (player.getSolidArea().intersects(npc.getSolidArea())) {
                        player.setCollisionOn(true);
                        if(npc.isTarget()){
                            player.setCollidingWithEntity(true);
                            npc.setCollidingWithEntity(true);
                        }
                    }else{
                        player.setCollidingWithEntity(false);
                        npc.setCollidingWithEntity(false);
                    }
                    break;
            }
            player.getSolidArea().x = player.getSolidAreaDefaultX();
            player.getSolidArea().y = player.getSolidAreaDefaultY();

            npc.getSolidArea().x = npc.getSolidAreaDefaultX();
            npc.getSolidArea().y = npc.getSolidAreaDefaultY();
        }
    }

    public void checkEntityEntityCollision(Entity entity){
        //CHECK ENTITY (PLAYER) ENTITY (NPC) COLLISION AND ENTITY (NPC) ENTITY (NPC) COLLISION;
        //CHECK THE WHOLE ARRAY OF NPC
        for (Entity secondEntity : gp.getNpcs()) {

            //IF THIS INDEX HAS AN NPC WE WILL CHECK IT
            if (secondEntity != null) {
                //IF ENTITY == SECOND ENTITY MEANS THE ENTITY IS AN NPC, SO WE DON'T HAVE TO CHECK THE COLLISION
                //IN THIS INDEX, SINCE THE ENTITY CANNOT COLLIDE WITH ITSELF (IF WE CHECKED IT THE CHARACTER WOULD STAY
                //STUCK) SO WE USE THIS ONE LOOP ITERATION TO CHECK COLLISION WITH THE PLAYER
                if(entity == secondEntity){

                    Entity player = gp.getPlayer();

                    entity.getSolidArea().x = entity.getWorldX() + entity.getSolidAreaDefaultX();
                    entity.getSolidArea().y = entity.getWorldY() + entity.getSolidAreaDefaultY();

                    player.getSolidArea().x = player.getWorldX() + player.getSolidAreaDefaultX();
                    player.getSolidArea().y = player.getWorldY() + player.getSolidAreaDefaultY();

                    switch (entity.getDirection()) {
                        case "up":
                            entity.getSolidArea().y -= entity.getSpeed();
                            if (entity.getSolidArea().intersects(player.getSolidArea())) {
                                entity.setCollisionOn(true);
                            }
                            break;
                        case "down":
                            entity.getSolidArea().y += entity.getSpeed();
                            if (entity.getSolidArea().intersects(player.getSolidArea())) {
                                entity.setCollisionOn(true);
                            }
                            break;
                        case "left":
                            entity.getSolidArea().x -= entity.getSpeed();
                            if (entity.getSolidArea().intersects(player.getSolidArea())) {
                                entity.setCollisionOn(true);
                            }
                            break;
                        case "right":
                            entity.getSolidArea().x += entity.getSpeed();
                            if (entity.getSolidArea().intersects(player.getSolidArea())) {
                                entity.setCollisionOn(true);
                            }
                            break;
                    }
                    entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                    entity.getSolidArea().y = entity.getSolidAreaDefaultY();

                    player.getSolidArea().x = player.getWorldX() + player.getSolidAreaDefaultX();
                    player.getSolidArea().y = player.getWorldY() + player.getSolidAreaDefaultY();

                }else{
                    entity.getSolidArea().x = entity.getWorldX() + entity.getSolidAreaDefaultX();
                    entity.getSolidArea().y = entity.getWorldY() + entity.getSolidAreaDefaultY();

                    secondEntity.getSolidArea().x = secondEntity.getWorldX() + secondEntity.getSolidAreaDefaultX();
                    secondEntity.getSolidArea().y = secondEntity.getWorldY() + secondEntity.getSolidAreaDefaultY();

                    switch (entity.getDirection()) {
                        case "up":
                            entity.getSolidArea().y -= entity.getSpeed();
                            if (entity.getSolidArea().intersects(secondEntity.getSolidArea())) {
                                entity.setCollisionOn(true);
                            }
                            break;
                        case "down":
                            entity.getSolidArea().y += entity.getSpeed();
                            if (entity.getSolidArea().intersects(secondEntity.getSolidArea())) {
                                entity.setCollisionOn(true);
                            }
                            break;
                        case "left":
                            entity.getSolidArea().x -= entity.getSpeed();
                            if (entity.getSolidArea().intersects(secondEntity.getSolidArea())) {
                                entity.setCollisionOn(true);
                            }
                            break;
                        case "right":
                            entity.getSolidArea().x += entity.getSpeed();
                            if (entity.getSolidArea().intersects(secondEntity.getSolidArea())) {
                                entity.setCollisionOn(true);
                            }
                            break;
                    }
                    entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                    entity.getSolidArea().y = entity.getSolidAreaDefaultY();
                    secondEntity.getSolidArea().x = secondEntity.getSolidAreaDefaultX();
                    secondEntity.getSolidArea().y = secondEntity.getSolidAreaDefaultY();
                }

            }
        }
    }

    public void checkEntityTrashCanCollision(Entity entity, ArrayList<TrashCan> trashCans){
        for (TrashCan trashCan : trashCans) {
            entity.getSolidArea().x = entity.getWorldX() + entity.getSolidAreaDefaultX();
            entity.getSolidArea().y = entity.getWorldY() + entity.getSolidAreaDefaultY();

            trashCan.getSolidArea().x = trashCan.getWorldX() + trashCan.getSolidAreaDefaultX();
            trashCan.getSolidArea().y = trashCan.getWorldY() + trashCan.getSolidAreaDefaultY();

            switch (entity.getDirection()){
                case "up":
                    entity.getSolidArea().y -= entity.getSpeed();
                    if(entity.getSolidArea().intersects(trashCan.getSolidArea())){
                        if(entity == gp.getPlayer()){
                            gp.getPlayer().setCollidingWithTrashCan(true);
                        }
                        entity.setCollisionOn(true);
                        trashCan.setCollision(true);
                    }else{
                        trashCan.setCollision(false);
                    }
                    break;
                case "down":
                    entity.getSolidArea().y += entity.getSpeed();
                    if(entity.getSolidArea().intersects(trashCan.getSolidArea())){
                        if(entity == gp.getPlayer()){
                            gp.getPlayer().setCollidingWithTrashCan(true);
                        }
                        entity.setCollisionOn(true);
                        trashCan.setCollision(true);
                    }else{
                        trashCan.setCollision(false);
                    }
                    break;
                case "left":
                    entity.getSolidArea().x -= entity.getSpeed();
                    if(entity.getSolidArea().intersects(trashCan.getSolidArea())){
                        if(entity == gp.getPlayer()){
                            gp.getPlayer().setCollidingWithTrashCan(true);
                        }
                        entity.setCollisionOn(true);
                        trashCan.setCollision(true);
                    }else{
                        trashCan.setCollision(false);
                    }
                    break;
                case "right":
                    entity.getSolidArea().x += entity.getSpeed();
                    if(entity.getSolidArea().intersects(trashCan.getSolidArea())){
                        if(entity == gp.getPlayer()){
                            gp.getPlayer().setCollidingWithTrashCan(true);
                        }
                        entity.setCollisionOn(true);
                        trashCan.setCollision(true);
                    }else{
                        trashCan.setCollision(false);
                    }
                    break;
            }
        }
    }


    public void checkObjectCollision(Player player, ArrayList<Object> obj) {
        if(!obj.isEmpty()){
            for (int i = 0; i < obj.size(); i++) {

                Object object = obj.get(i);

                player.getSolidArea().x = player.getWorldX() + player.getSolidAreaDefaultX();
                player.getSolidArea().y = player.getWorldY() + player.getSolidAreaDefaultY();

                object.getSolidArea().x = object.getWorldX() + object.getSolidAreaDefaultX();
                object.getSolidArea().y = object.getWorldY() + object.getSolidAreaDefaultY();

                if (player.getSolidArea().intersects(object.getSolidArea())) {
                    player.setCollidingWithObject(true);
                    object.setCollision(true);
                }else{
                    object.setCollision(false);
                }

                player.getSolidArea().x = player.getSolidAreaDefaultX();
                player.getSolidArea().y = player.getSolidAreaDefaultY();
                object.getSolidArea().x = object.getSolidAreaDefaultX();
                object.getSolidArea().y = object.getSolidAreaDefaultY();
            }
        }
    }



    /*public void checkPlayerObject(Player player, ArrayList<Object> objects){

        if(!objects.isEmpty()){
            for (int i = 0; i < objects.size(); i++) {
                Object object = objects.get(i);
                int screenX = object.getWorldX() - gp.getPlayerWorldX() + gp.getPlayerSCREENX();
                int screenY = object.getWorldY() - gp.getPlayerWorldY() + gp.getPlayerSCREENY();

                Rectangle objectRect = new Rectangle(screenX, screenY, 48, 48);

                if(player.solidAreaTeste().intersects(objectRect)){
                    player.setCollidingWithObject(true);
                    object.setCollision(true);
                }else{
                    object.setCollision(false);
                }
            }
        }
    }

    public void checkPlayerEntityCollision(Player entity, NPC[] target){

        for (int i = 0; i < target.length; i++) {
            if(target[i] != null){
                NPC npc = target[i];

                entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

                npc.getSolidArea().x = npc.getWorldX() + npc.getSolidArea().x;
                npc.getSolidArea().y = npc.getWorldY() + npc.getSolidArea().y;

                switch (entity.getDirection()){
                    case "up":
                        entity.getSolidArea().y -= entity.getSpeed();
                        if(entity.getSolidArea().intersects(npc.getSolidArea())){
                            entity.setCollisionOn(true);
                            entity.setCollidingWithNpc(true);
                            entity.getEntity(npc);
                        }else{
                            entity.setCollidingWithNpc(false);
                        }
                        break;
                    case "down":
                        entity.getSolidArea().y += entity.getSpeed();
                        if(entity.getSolidArea().intersects(npc.getSolidArea())){
                            entity.setCollisionOn(true);
                            entity.setCollidingWithNpc(true);
                            entity.getEntity(npc);
                        }else{
                            entity.setCollidingWithNpc(false);
                        }
                        break;
                    case "left":
                        entity.getSolidArea().x -= entity.getSpeed();
                        if(entity.getSolidArea().intersects(npc.getSolidArea())){
                            entity.setCollisionOn(true);
                            entity.setCollidingWithNpc(true);
                            entity.getEntity(npc);
                        }else{
                            entity.setCollidingWithNpc(false);
                        }
                        break;
                    case "right":
                        entity.getSolidArea().x += entity.getSpeed();
                        if(entity.getSolidArea().intersects(npc.getSolidArea())){
                            entity.setCollisionOn(true);
                            entity.setCollidingWithNpc(true);
                            entity.getEntity(npc);
                        }else{
                            entity.setCollidingWithNpc(false);
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

    public void checkPlayerTrashCanCollision(Player player, ArrayList<TrashCan> trashCans){

        if(!trashCans.isEmpty()) {
            for (TrashCan trashCan : trashCans) {
                player.getSolidArea().x = player.getWorldX() + player.getSolidArea().x;
                player.getSolidArea().y = player.getWorldY() + player.getSolidArea().y;

                trashCan.getSolidArea().x = trashCan.getWorldX() + trashCan.getSolidArea().x;
                trashCan.getSolidArea().y = trashCan.getWorldY() + trashCan.getSolidArea().y;

                switch (player.getDirection()) {
                    case "up":
                        player.getSolidArea().y -= player.getSpeed();
                        if (player.getSolidArea().intersects(trashCan.getSolidArea())) {
                            player.setCollisionOn(true);
                            player.setCollidingWithTrashCan(true);
                            gp.getTrashCanManager().cleanBackpack(player, trashCan);
                        } else {
                            player.setCollidingWithTrashCan(false);
                        }
                        break;
                    case "down":
                        player.getSolidArea().y += player.getSpeed();
                        if (player.getSolidArea().intersects(trashCan.getSolidArea())) {
                            player.setCollisionOn(true);
                            player.setCollidingWithTrashCan(true);
                            gp.getTrashCanManager().cleanBackpack(player, trashCan);
                        } else {
                            player.setCollidingWithTrashCan(false);
                        }
                        break;
                    case "left":
                        player.getSolidArea().x -= player.getSpeed();
                        if (player.getSolidArea().intersects(trashCan.getSolidArea())) {
                            player.setCollisionOn(true);
                            player.setCollidingWithTrashCan(true);
                            gp.getTrashCanManager().cleanBackpack(player, trashCan);
                        } else {
                            player.setCollidingWithTrashCan(false);
                        }
                        break;
                    case "right":
                        player.getSolidArea().x += player.getSpeed();
                        if (player.getSolidArea().intersects(trashCan.getSolidArea())) {
                            player.setCollisionOn(true);
                            player.setCollidingWithTrashCan(true);
                            gp.getTrashCanManager().cleanBackpack(player, trashCan);
                        } else {
                            player.setCollidingWithTrashCan(false);
                        }
                        break;
                }
                player.getSolidArea().x = player.getSolidAreaDefaultX();
                player.getSolidArea().y = player.getSolidAreaDefaultY();
                trashCan.getSolidArea().x = trashCan.getSolidAreaDefaultX();
                trashCan.getSolidArea().y = trashCan.getSolidAreaDefaultY();
            }
        }
    }


    public void checkEntityTrashCanCollision(Entity entity, ArrayList<TrashCan> trashCans){

        if(!trashCans.isEmpty()) {
            for (TrashCan trashCan : trashCans) {
                entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

                trashCan.getSolidArea().x = trashCan.getWorldX() + trashCan.getSolidArea().x;
                trashCan.getSolidArea().y = trashCan.getWorldY() + trashCan.getSolidArea().y;

                switch (entity.getDirection()) {
                    case "up":
                        entity.getSolidArea().y -= entity.getSpeed();
                        if (entity.getSolidArea().intersects(trashCan.getSolidArea())) {
                            entity.setCollisionOn(true);
                        }
                        break;
                    case "down":
                        entity.getSolidArea().y += entity.getSpeed();
                        if (entity.getSolidArea().intersects(trashCan.getSolidArea())) {
                            entity.setCollisionOn(true);
                        }
                        break;
                    case "left":
                        entity.getSolidArea().x -= entity.getSpeed();
                        if (entity.getSolidArea().intersects(trashCan.getSolidArea())) {
                            entity.setCollisionOn(true);
                        }
                        break;
                    case "right":
                        entity.getSolidArea().x += entity.getSpeed();
                        if (entity.getSolidArea().intersects(trashCan.getSolidArea())) {
                            entity.setCollisionOn(true);
                        }
                }
                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();
                trashCan.getSolidArea().x = trashCan.getSolidAreaDefaultX();
                trashCan.getSolidArea().y = trashCan.getSolidAreaDefaultY();
            }
        }
    }


    public void checkEntityEntityCollision(Entity entity, NPC[] npcs){

        entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
        entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

        for (int i = 0; i < npcs.length; i++) {
            if(npcs[i] != null && npcs[i] != entity){
                NPC npc = npcs[i];

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
    }*/
}

