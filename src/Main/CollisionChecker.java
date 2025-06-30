package Main;

import electroDungeon.objects.Lumix;
import entity.Entity;
import entity.Player;

import java.awt.*;

public class CollisionChecker {

    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public Entity getEntity(Entity entity, String direction) {
        int col = entity.worldX/gamePanel.tileSize;
        int row = entity.worldY/gamePanel.tileSize;
        switch (direction) {
            case "UP" -> {
                row --;
            }
            case "DOWN" -> {
                row++;
            }
            case "LEFT" -> {

                col --;
            }
            case "RIGHT" -> {
                col ++;
            }
        }

        for (int i = 0; i <  gamePanel.objects[1].length; i++) {
            if (gamePanel.objects[gamePanel.currentMap][i] != null) {
                Entity entity1 = gamePanel.objects[gamePanel.currentMap][i];
                if ((entity1.worldX/gamePanel.tileSize == col && entity1.worldY/gamePanel.tileSize == row) ||
                        (entity1.worldX/gamePanel.tileSize == entity.worldX/gamePanel.tileSize && entity1.worldY/gamePanel.tileSize == entity.worldY/gamePanel.tileSize) )
                {   if (entity1 instanceof Lumix) {
                    return entity1;
                }
                }
            }
        }
        return null;
    }




    public void checkTile(Entity entity) {
        entity.collisionOn = false;
        // So basically we're calculating the location of our solid area on the world

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        // And then based on these coordinates, we find out their column and row numbers

        int entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
        int entityRightCol = entityRightWorldX / gamePanel.tileSize;
        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;

        // We only have to check two tiles for each direction the player would take.
        // For example, if the player goes up, we have to check for the top left corner and the top right corner

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "UP":
                entityTopRow = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityRightCol][entityTopRow];
                if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "DOWN":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "LEFT":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityLeftCol][entityBottomRow];
                if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "RIGHT":
                entityRightCol = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }
    public int checkObject(Entity entity, boolean player) {
        int index = -1;

        // We check if the player is hitting any object, if so, we return the index of the object

        for (int i = 0; i <  gamePanel.objects[1].length; i++) {
            if (gamePanel.objects[gamePanel.currentMap][i] != null) {
                if (gamePanel.objects[gamePanel.currentMap][i].equals(entity)) {
                    return -1;
                }
                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                // Get the gamePanel.object's solid area position
                gamePanel.objects[gamePanel.currentMap][i].solidArea.x = gamePanel.objects[gamePanel.currentMap][i].worldX + gamePanel.objects[gamePanel.currentMap][i].solidArea.x;
                gamePanel.objects[gamePanel.currentMap][i].solidArea.y = gamePanel.objects[gamePanel.currentMap][i].worldY + gamePanel.objects[gamePanel.currentMap][i].solidArea.y;
                //System.out.println(entity.solidArea.x + " " + entity.solidArea.y + "          " + gamePanel.objects[gamePanel.currentMap][i].solidArea.x + " " + gamePanel.objects[gamePanel.currentMap][i].solidArea.y);

                switchEntityDirection(entity);
                if (entity.solidArea.intersects(gamePanel.objects[gamePanel.currentMap][i].solidArea) && gamePanel.objects[gamePanel.currentMap][i].type != entity.type_invisible) {
                    if (gamePanel.objects[gamePanel.currentMap][i].collision) {
                        entity.collisionOn = true;
                    }
                    if (player) {
                        index = i;
                    }
                }
                // We need to reset this entity's and object's solid area otherwise it keeps increasing
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamePanel.objects[gamePanel.currentMap][i].solidArea.x = gamePanel.objects[gamePanel.currentMap][i].solidAreaDefaultX;
                gamePanel.objects[gamePanel.currentMap][i].solidArea.y = gamePanel.objects[gamePanel.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }
    private void switchEntityDirection(Entity entity) {
        switch (entity.direction) {
            case "UP": entity.solidArea.y -= entity.speed; break;
            case "DOWN": entity.solidArea.y += entity.speed; break;
            case "LEFT": entity.solidArea.x -= entity.speed; break;
            case "RIGHT": entity.solidArea.x += entity.speed; break;
        }
    }
    // NPC OR MONSTER COLLISION
    public int checkEntity(Entity entity, Entity[][] target) {
        int index = -1;

        // We check if the player is hitting any object, if so, we return the index of the object

        for (int i = 0; i <  target[1].length; i++) {
            if (target[gamePanel.currentMap][i] != null) {

                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                // Get object's solid area position
                target[gamePanel.currentMap][i].solidArea.x = target[gamePanel.currentMap][i].worldX + target[gamePanel.currentMap][i].solidArea.x;
                target[gamePanel.currentMap][i].solidArea.y = target[gamePanel.currentMap][i].worldY + target[gamePanel.currentMap][i].solidArea.y;

                switch (entity.direction) {
                    case "UP", "STANDING_UP": entity.solidArea.y -= entity.speed; break;
                    case "DOWN", "STANDING_DOWN": entity.solidArea.y += entity.speed; break;
                    case "LEFT", "STANDING_LEFT": entity.solidArea.x -= entity.speed; break;
                    case "RIGHT", "STANDING_RIGHT": entity.solidArea.x += entity.speed; break;
                }
                if (entity.solidArea.intersects(target[gamePanel.currentMap][i].solidArea)) {
                    if (target[gamePanel.currentMap][i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }
                // We need to reset this entity's and object's solid area otherwise it keeps increasing
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[gamePanel.currentMap][i].solidArea.x = target[gamePanel.currentMap][i].solidAreaDefaultX;
                target[gamePanel.currentMap][i].solidArea.y = target[gamePanel.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }
    public boolean checkPlayer(Entity entity) {

        boolean contactPlayer = false;

        // Get entity's solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        // Get the gamePanel.object's solid area position
        gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
        gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;

        switchEntityDirection(entity);
        if (entity.solidArea.intersects(gamePanel.player.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }
        // We need to reset this entity's and object's solid area otherwise it keeps increasing
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
        gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;

        return contactPlayer;
    }



}
