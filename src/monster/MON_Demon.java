package monster;
import Main.GamePanel;
import entity.Entity;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MON_Demon extends Entity {

    BufferedImage right3, right4, left3, left4;
    GamePanel gamePanel;
    
    public MON_Demon(GamePanel gamePanel) {
        super(gamePanel);

        this.gamePanel = gamePanel;

        type = type_monster;
        name = "Demon";
        speed = 2;
        maxLife = 4;
        life = maxLife;
        attack = 40;
        defense = 0;
        exp = 30;
        multiple_sprites = true;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {

        up1 = setup("/monsters/demon_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/monsters/demon_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/monsters/demon_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/monsters/demon_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/monsters/demon_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/monsters/demon_left_2", gamePanel.tileSize, gamePanel.tileSize);
        left3 = setup("/monsters/demon_left_3", gamePanel.tileSize, gamePanel.tileSize);
        left4 = setup("/monsters/demon_left_4", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/monsters/demon_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/monsters/demon_right_2", gamePanel.tileSize, gamePanel.tileSize);
        right3 = setup("/monsters/demon_right_3", gamePanel.tileSize, gamePanel.tileSize);
        right4 = setup("/monsters/demon_right_4", gamePanel.tileSize, gamePanel.tileSize);
        image = setup("/monsters/demon_left_3", gamePanel.tileSize, gamePanel.tileSize);
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
    public void draw(Graphics2D g2D) {

        BufferedImage image = null;

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (canDraw()) {
            int tempScreenX = screenX;
            int tempScreenY = screenY;

            switch (direction) {
                case "UP":
                    if (spriteNumber == 1) image = up1;
                    if (spriteNumber == 2) image = up2;
                    break;
                case "DOWN":
                    if (spriteNumber == 1) image = down1;
                    if (spriteNumber == 2) image = down2;
                    break;
                case "LEFT":
                    if (spriteNumber == 1) image = left1;
                    if (spriteNumber == 2) image = left2;
                    if (spriteNumber == 3) image = left3;
                    if (spriteNumber == 4) image = left4;
                    break;
                case "RIGHT":
                    if (spriteNumber == 1) image = right1;
                    if (spriteNumber == 2) image = right2;
                    if (spriteNumber == 3) image = right3;
                    if (spriteNumber == 4) image = right4;
                    break;
            }

            // MONSTER HP BAR
            if (type == type_monster && hpBarOn) {

                double oneScale = (double) gamePanel.tileSize / maxLife;
                double hpBarValue = oneScale * life;
                if (life <= 0) {
                    hpBarValue = 0;
                }

                g2D.setColor(new Color(35, 35, 35));
                g2D.fillRect(screenX - 1, screenY - 16, gamePanel.tileSize + 2, 12);
                g2D.setColor(new Color(255, 0, 30));
                g2D.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);

                hpBarCounter++;

                if (hpBarCounter > 600) { // 10 sec
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            if (invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2D, 0.4f);
            }
            if (dying) {
                dyingAnimation(g2D);
            }
            g2D.drawImage(image, tempScreenX, tempScreenY, null);
            changeAlpha(g2D, 1f);
        }
    }




}
