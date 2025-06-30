package monster;
import Main.GamePanel;
import entity.Entity;
import object.*;

import java.util.Random;

public class MON_SkeletonLord extends Entity {
    boolean inRage;
    GamePanel gamePanel;
    public static final String monName = "Skeleton Lord";
    public MON_SkeletonLord(GamePanel gamePanel) {
        super(gamePanel);

        this.gamePanel = gamePanel;

        type = type_monster;
        name = monName;
        speed = 1;
        maxLife = 2;
        life = maxLife;
        attack = 10;
        defense = 0;
        exp = 50;

        int size = gamePanel.tileSize * 5;
        solidArea.x = 48;
        solidArea.y = 48;
        solidArea.width = size - 48 * 2;
        solidArea.height = size - 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 170;
        attackArea.height = 170;
        motion1_duration = 25;
        motion2_duration = 50;

        getImage();
        getAttackImage();
    }

    public void getAttackImage() {

        int i = 5;

        if (!inRage) {
            attackUp1 = setup("/monsters/skeletonlord_attack_up_1", gamePanel.tileSize * i, gamePanel.tileSize * i * 2);
            attackUp2 = setup("/monsters/skeletonlord_attack_up_2", gamePanel.tileSize * i, gamePanel.tileSize * i * 2);
            attackDown1 = setup("/monsters/skeletonlord_attack_down_1", gamePanel.tileSize * i, gamePanel.tileSize * i * 2);
            attackDown2 = setup("/monsters/skeletonlord_attack_down_2", gamePanel.tileSize * i, gamePanel.tileSize * i * 2);
            attackLeft1 = setup("/monsters/skeletonlord_attack_left_1", gamePanel.tileSize * i * 2, gamePanel.tileSize * i);
            attackLeft2 = setup("/monsters/skeletonlord_attack_left_2", gamePanel.tileSize * i * 2, gamePanel.tileSize * i);
            attackRight1 = setup("/monsters/skeletonlord_attack_right_1", gamePanel.tileSize * i * 2, gamePanel.tileSize * i);
            attackRight2 = setup("/monsters/skeletonlord_attack_right_2", gamePanel.tileSize * i * 2, gamePanel.tileSize * i);
        } else {
            attackUp1 = setup("/monsters/skeletonlord_phase2_attack_up_1", gamePanel.tileSize * i, gamePanel.tileSize * i * 2);
            attackUp2 = setup("/monsters/skeletonlord_phase2_attack_up_2", gamePanel.tileSize * i, gamePanel.tileSize * i * 2);
            attackDown1 = setup("/monsters/skeletonlord_phase2_attack_down_1", gamePanel.tileSize * i, gamePanel.tileSize * i * 2);
            attackDown2 = setup("/monsters/skeletonlord_phase2_attack_down_2", gamePanel.tileSize * i, gamePanel.tileSize * i * 2);
            attackLeft1 = setup("/monsters/skeletonlord_phase2_attack_left_1", gamePanel.tileSize * i * 2, gamePanel.tileSize * i);
            attackLeft2 = setup("/monsters/skeletonlord_phase2_attack_left_2", gamePanel.tileSize * i * 2, gamePanel.tileSize * i);
            attackRight1 = setup("/monsters/skeletonlord_phase2_attack_right_1", gamePanel.tileSize * i * 2, gamePanel.tileSize * i);
            attackRight2 = setup("/monsters/skeletonlord_phase2_attack_right_2", gamePanel.tileSize * i * 2, gamePanel.tileSize * i);

        }


    }

    public void getImage() {

        int i = 5;

        if (!inRage) {
            up1 = setup("/monsters/skeletonlord_up_1", gamePanel.tileSize * i, gamePanel.tileSize * i);
            up2 = setup("/monsters/skeletonlord_up_2", gamePanel.tileSize * i, gamePanel.tileSize * i);
            down1 = setup("/monsters/skeletonlord_down_1", gamePanel.tileSize * i, gamePanel.tileSize * i);
            down2 = setup("/monsters/skeletonlord_down_2", gamePanel.tileSize * i, gamePanel.tileSize * i);
            left1 = setup("/monsters/skeletonlord_left_1", gamePanel.tileSize * i, gamePanel.tileSize * i);
            left2 = setup("/monsters/skeletonlord_left_2", gamePanel.tileSize * i, gamePanel.tileSize * i);
            right1 = setup("/monsters/skeletonlord_right_1", gamePanel.tileSize * i, gamePanel.tileSize * i);
            right2 = setup("/monsters/skeletonlord_right_2", gamePanel.tileSize * i, gamePanel.tileSize * i);
        } else {
            up1 = setup("/monsters/skeletonlord_phase2_up_1", gamePanel.tileSize * i, gamePanel.tileSize * i);
            up2 = setup("/monsters/skeletonlord_phase2_up_2", gamePanel.tileSize * i, gamePanel.tileSize * i);
            down1 = setup("/monsters/skeletonlord_phase2_down_1", gamePanel.tileSize * i, gamePanel.tileSize * i);
            down2 = setup("/monsters/skeletonlord_phase2_down_2", gamePanel.tileSize * i, gamePanel.tileSize * i);
            left1 = setup("/monsters/skeletonlord_phase2_left_1", gamePanel.tileSize * i, gamePanel.tileSize * i);
            left2 = setup("/monsters/skeletonlord_phase2_left_2", gamePanel.tileSize * i, gamePanel.tileSize * i);
            right1 = setup("/monsters/skeletonlord_phase2_right_1", gamePanel.tileSize * i, gamePanel.tileSize * i);
            right2 = setup("/monsters/skeletonlord_phase2_right_2", gamePanel.tileSize * i, gamePanel.tileSize * i);

        }




        image = setup("/monsters/skeletonlord_down_1", gamePanel.tileSize, gamePanel.tileSize);
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

        if (!inRage && life <= maxLife / 2) {
            inRage = true;
            getImage();
            getAttackImage();
            speed++;
            attack *= 2;
        }
        if (getTileDistance(gamePanel.player) < 10) {
            moveTowardPlayer(60);
        } else {

            // Get a random direction
            getRandomDirection(120);
        }

        // Check if it attacks
        if (!attacking) {
            checkAttackOrNot(60, gamePanel.tileSize * 10, gamePanel.tileSize * 5);
        }
    }
    public void damageReaction() {
        actionLockCounter = 0;
    }
    public void checkDrop() {
        dropItem(new OBJ_Skeleton_Key(gamePanel));

    }






}
