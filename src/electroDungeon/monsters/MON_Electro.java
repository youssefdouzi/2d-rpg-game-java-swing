package electroDungeon.monsters;

import Main.GamePanel;
import entity.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MON_Electro extends Entity {

    private BufferedImage on1, on2, off1, off2, currentSprite;

    public MON_Electro(GamePanel gamePanel) {
        super(gamePanel);
        name = "Electro";
        type = type_monster;
        setImages();
        capacity = new ElectroProjectile(gamePanel,this);
        maxLife = 3;
        speed = 3;
        attack = 1;
        solidArea.setBounds(0,0,gamePanel.tileSize - 1,gamePanel.tileSize - 1);
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
        exp = 5;
        direction = "CACA";
    }

    private void setImages() {
        on1 = setup("/electroDungeon/monsters/electro_mob_on_1", gamePanel.tileSize, gamePanel.tileSize);
        on2 = setup("/electroDungeon/monsters/electro_mob_on_2", gamePanel.tileSize, gamePanel.tileSize);
        off1 = setup("/electroDungeon/monsters/electro_mob_off_1", gamePanel.tileSize, gamePanel.tileSize);
        off2 = setup("/electroDungeon/monsters/electro_mob_off_2", gamePanel.tileSize, gamePanel.tileSize);
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
                case "CACA" : break;
            }
        }
        spriteCounter++;
        if (spriteCounter == 120) {
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
        if (spriteCounter < 60) {
            if (onPath)
                currentSprite = on1;
            else
                currentSprite = off1;
        }
        else if (spriteCounter < 120) {
            if (onPath)
                currentSprite = on2;
            else
                currentSprite = off2;
        }

    }
    @Override
    public void setAction() {
        if (onPath) {

            if (capacity.canUseCapacity()) {
                capacity.useCapacity(null);
            }
            // Search the direction to go
            searchPath(getGoalCol(gamePanel.player), getGoalRow(gamePanel.player));

        } else {
            direction = "CACA";
            // Check if it starts chasing
            checkStartChasingOrNot(gamePanel.player, 5, 100);

            // Get a random direction
        }
    }
    @Override
    public int getCenterX() {
        return worldX + on1.getWidth() / 2;
    }
    @Override
    public int getCenterY() {
        return worldY + on1.getHeight() / 2;
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
