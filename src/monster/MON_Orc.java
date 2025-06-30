package monster;
import Main.GamePanel;
import entity.Entity;
import object.*;

import java.util.Random;

public class MON_Orc extends Entity {

    GamePanel gamePanel;
    public MON_Orc(GamePanel gamePanel) {
        super(gamePanel);

        this.gamePanel = gamePanel;

        type = type_monster;
        name = "Orc";
        speed = 1;
        maxLife = 20;
        life = maxLife;
        attack = 20;
        defense = 10;
        exp = 10;
        motion1_duration = 40;
        motion2_duration = 85;

        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 40;
        solidArea.height = 44;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 48;
        attackArea.height = 48;
        
        getImage();
        getAttackImage();
    }

    public void getAttackImage() {
        attackUp1 = setup("/monsters/orc_attack_up_1", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackUp2 = setup("/monsters/orc_attack_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackDown1 = setup("/monsters/orc_attack_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackDown2 = setup("/monsters/orc_attack_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackLeft1 = setup("/monsters/orc_attack_left_1", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackLeft2 = setup("/monsters/orc_attack_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackRight1 = setup("/monsters/orc_attack_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackRight2 = setup("/monsters/orc_attack_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
    }
    
    public void getImage() {

        up1 = setup("/monsters/orc_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/monsters/orc_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/monsters/orc_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/monsters/orc_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/monsters/orc_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/monsters/orc_left_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/monsters/orc_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/monsters/orc_right_2", gamePanel.tileSize, gamePanel.tileSize);

        


        image = setup("/monsters/orc_down_1", gamePanel.tileSize, gamePanel.tileSize);
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

        // Check if it attacks
        if (!attacking) {
            checkAttackOrNot(30, gamePanel.tileSize * 4, gamePanel.tileSize);
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
