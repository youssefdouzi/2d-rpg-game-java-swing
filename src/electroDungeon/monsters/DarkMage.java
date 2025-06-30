package electroDungeon.monsters;

import Main.GamePanel;
import entity.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class DarkMage extends Entity {
    boolean on;
    BufferedImage currentSprite;

    public DarkMage(GamePanel gamePanel) {
        super(gamePanel);
        down1 = setup("/electroDungeon/mage_down_off",gamePanel.tileSize,gamePanel.tileSize);
        down2 = setup("/electroDungeon/mage_down_on",gamePanel.tileSize,gamePanel.tileSize);
        left1 = setup("/electroDungeon/mage_left_off",gamePanel.tileSize,gamePanel.tileSize);
        left2 = setup("/electroDungeon/mage_left_on",gamePanel.tileSize,gamePanel.tileSize);
        right1 = setup("/electroDungeon/mage_right_off",gamePanel.tileSize,gamePanel.tileSize);
        right2 = setup("/electroDungeon/mage_right_on",gamePanel.tileSize,gamePanel.tileSize);
        up1 = setup("/electroDungeon/mage_up",gamePanel.tileSize,gamePanel.tileSize);
        up2 = setup("/electroDungeon/mage_up",gamePanel.tileSize,gamePanel.tileSize);
        type = type_monster;
        solidArea.setBounds(0,0,gamePanel.tileSize - 1,gamePanel.tileSize - 1);
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }

    @Override
    public void setAction(){
        if (onPath) {

            if (capacity.canUseCapacity()) {
                capacity.useCapacity(null);
            }
            // Search the direction to go
            searchPath(getGoalCol(gamePanel.player), getGoalRow(gamePanel.player));

        } else {
            // Check if it starts chasing
            checkStartChasingOrNot(gamePanel.player, 5, 100);

            // Get a random direction
        }
    }

    @Override
    public void update() {
        if (gamePanel.gameState == gamePanel.fightingState) {
            return;
        }
        setAction();
        checkCollision();

        // IF COLLISION IS FALSE, CAN MOVE
        if (!collisionOn) {
            switch (direction) {
                case "UP":
                    worldY -= speed;
                    break;
                case "DOWN":
                    worldY += speed;
                    break;
                case "LEFT":
                    worldX -= speed;
                    break;
                case "RIGHT":
                    worldX += speed;
                    break;
            }
        }
        spriteCounter++;
        if (spriteCounter == 60) {
            spriteCounter = 0;
        }
        setCurrentSprite();
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (capacityCooldown > 0)
            capacityCooldown--;


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

    private void setCurrentSprite() {
        switch (direction) {
            case "UP" -> {
                if (onPath) currentSprite = up2;
                else currentSprite = up1;
            }
            case "DOWN" -> {
                if (onPath) currentSprite = down2;
                else currentSprite = down1;
            }
            case "LEFT" -> {
                if (onPath) currentSprite = left2;
                else currentSprite = left1;
            }
            case "RIGHT" -> {
                if (onPath) currentSprite = right2;
                else currentSprite = right1;
            }
        }
        if (spriteCounter > 30) {
            if (onPath) currentSprite = down2;
            else currentSprite = down1;
        }
    }


    @Override
    public void draw(Graphics2D g2) {
        if (canDraw()) {
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
            g2.drawImage(currentSprite, screenX, screenY, null);
        }
    }
}
