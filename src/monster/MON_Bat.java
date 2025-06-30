package monster;
import Main.GamePanel;
import entity.Entity;
import object.*;

import java.util.Random;

public class MON_Bat extends Entity {

    GamePanel gamePanel;
    public MON_Bat(GamePanel gamePanel) {
        super(gamePanel);

        this.gamePanel = gamePanel;

        type = type_monster;
        name = "Bat";
        speed = 2;
        maxLife = 2;
        life = maxLife;
        attack = 9;
        defense = 0;
        exp = 1;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {

        up1 = setup("/monsters/bat_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/monsters/bat_down_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/monsters/bat_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/monsters/bat_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/monsters/bat_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/monsters/bat_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/monsters/bat_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/monsters/bat_down_2", gamePanel.tileSize, gamePanel.tileSize);
        image = setup("/monsters/bat_down_1", gamePanel.tileSize, gamePanel.tileSize);
    }
    public void update() {
        if (gamePanel.gameState != gamePanel.fightingState) {
            super.update();

            int xDistance = Math.abs(worldX - gamePanel.player.worldX);
            int yDistance = Math.abs(worldY - gamePanel.player.worldY);
            int tileDistance = (xDistance + yDistance) / gamePanel.tileSize;

            if (!onPath && tileDistance < 5) {
                int i = new Random().nextInt(100) + 1;
                if (i > 50) {
                    onPath = true; // it becomes aggro 50% of the time
                }
            }
//        if (onPath && tileDistance > 20) {
//            onPath = false;
//        }
        }
    }
    public void setAction() {
        if (onPath) {

            // Check if it stops chasing
            checkStopChasingOrNot(gamePanel.player, 15, 100);

            // Search the direction to go
            searchPath(getGoalCol(gamePanel.player), getGoalRow(gamePanel.player));

        } else {

            // Check if it starts chasing
            checkStartChasingOrNot(gamePanel.player, 5, 100);

            // Get a random direction
            getRandomDirection(120);
        }
    }
    public void damageReaction() {
        actionLockCounter = 0;
        //direction = gamePanel.player.direction;
        onPath = true;
    }
    public void checkDrop() {
        // CAST A DIE
        int i = new Random().nextInt(100) + 1;

        // SET THE MONSTER DROP
        if (i < 50) {
            dropItem(new OBJ_Coin_Bronze(gamePanel));
        } else if (i >= 50 && i < 75) {
            dropItem(new OBJ_Heart(gamePanel));
        } else if (i >= 75 && i < 100 ) {
            if (gamePanel.player.playerClass.equals("Sorcerer")) {
                dropItem(new OBJ_ManaCrystal(gamePanel));
            } else {
                dropItem(new OBJ_Key(gamePanel,1));
            }
        }
    }






}
