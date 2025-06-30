package Main;

import entity.Entity;

public class EventHandler {

    GamePanel gamePanel;
    EventRect[][][] eventRect;
    int previousEventX, previousEventY;
    boolean canTouchEvent = true; // prevent an event to happen repeatedly
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        eventRect = new EventRect[gamePanel.MAX_MAP][gamePanel.MAX_WORLD_COL][gamePanel.MAX_WORLD_ROW];

        int map = 0;
        int col = 0;
        int row = 0;
        while (map < gamePanel.MAX_MAP && col < gamePanel.MAX_WORLD_COL && row < gamePanel.MAX_WORLD_ROW) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col ++;
            if (col == gamePanel.MAX_WORLD_COL) {
                col = 0;
                row++;

                if (row == gamePanel.MAX_WORLD_ROW) {
                    row = 0;
                    map++;
                }
            }
        }
    }

    public void checkEvent() {

        // Check if the player character is more than 1 tile away from the last event
        int xDistance = Math.abs(gamePanel.player.worldX - previousEventX);
        int yDistance = Math.abs(gamePanel.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gamePanel.tileSize) {
            canTouchEvent = true;
        }

        if (canTouchEvent) {
            if (hit(0, 27, 16, "RIGHT")) { damagePit(gamePanel.dialogueState);}
            else if (hit(0, 23, 12, "UP")) { healingPool(gamePanel.dialogueState);}
            else if (hit(0, 10, 39, "any")) { teleport(1, 12, 13);}
            else if (hit(1, 12, 13, "any")) { teleport(0, 10, 39);}
            else if (hit(1, 12, 9, "UP")) { speak(gamePanel.npc[1][0]);}
            

        }
    }
    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;

        if (map == gamePanel.currentMap) {
            gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
            gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;
            eventRect[map][col][row].x = col * gamePanel.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gamePanel.tileSize + eventRect[map][col][row].y;

            if (gamePanel.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) {
                if (gamePanel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;

                    previousEventX = gamePanel.player.worldX;
                    previousEventY = gamePanel.player.worldY;
                }
            }

            gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
            gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }


        return hit;
    }
    public void damagePit(int gameState) {
        gamePanel.gameState = gameState;
        gamePanel.playSE(6);
        gamePanel.ui.currentDialogue[0] = "You fall into a pit!";
        gamePanel.player.life -= 1;
        canTouchEvent = false;
    }
    public void healingPool(int gameState) {
        if (gamePanel.keyHandler.enterPressed) {
            gamePanel.gameState = gameState;
            gamePanel.playSE(2);
            gamePanel.player.attackCanceled = true;
            gamePanel.ui.currentDialogue[0] = "You drink the water. \nYour life and mana has been recovered.";
            gamePanel.player.life =  gamePanel.player.maxLife;
            gamePanel.player.resource.replenish();
            gamePanel.objectSetter.setMonster();
            gamePanel.saver.save("user-data");
        }
    }
    public void teleport(int mapNum, int col, int row) {
        gamePanel.gameState = gamePanel.transitionState;
        tempMap = mapNum;
        tempCol = col;
        tempRow = row;

        canTouchEvent = false;
        gamePanel.playSE(14);
        gamePanel.objectSetter.setMap(mapNum);
    }
    public void speak(Entity entity) {
        if (gamePanel.keyHandler.enterPressed) {
            gamePanel.gameState = gamePanel.dialogueState;
            gamePanel.player.attackCanceled = true;
            gamePanel.keyHandler.enterPressed = false;
            entity.speak();
        }
    }



}
