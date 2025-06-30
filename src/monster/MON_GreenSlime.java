package monster;
import Main.GamePanel;
import entity.Entity;
import object.*;

import java.util.Random;

public class MON_GreenSlime extends Entity {

    GamePanel gamePanel;
    public MON_GreenSlime(GamePanel gamePanel) {
        super(gamePanel);

        this.gamePanel = gamePanel;

        type = type_monster;
        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 2;
        capacity = new OBJ_Rock(gamePanel,this);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {

        up1 = setup("/monsters/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/monsters/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/monsters/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/monsters/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/monsters/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/monsters/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/monsters/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/monsters/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        image = setup("/monsters/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
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


            if (capacity.canUseCapacity()) {
                capacity.useCapacity(null);
            }
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
