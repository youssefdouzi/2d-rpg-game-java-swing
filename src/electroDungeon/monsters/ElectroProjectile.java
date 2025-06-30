package electroDungeon.monsters;

import Main.GamePanel;
import entity.Capacity;
import entity.Entity;
import entity.Projectile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ElectroProjectile extends Projectile implements Capacity {
    private BufferedImage sprite;
    GamePanel gamePanel;
    private int traveledDistance = 0;
    public ElectroProjectile(GamePanel gamePanel, Entity user) {
        super(gamePanel, user);
        this.gamePanel = gamePanel;
        name = "Electron";
        speed = 10;
        attack = 5;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/electroDungeon/monsters/electro_projectile_up", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/electroDungeon/monsters/electro_projectile_down", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/electroDungeon/monsters/electro_projectile_left", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/electroDungeon/monsters/electro_projectile_right", gamePanel.tileSize, gamePanel.tileSize);
    }

    @Override
    public void update() {

        boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);
        if (contactPlayer && !gamePanel.player.invincible) {
            damagePlayer(attack);
            alive = false;
        }



        switch (direction) {
        case "UP" -> {worldY -= speed; sprite = up1;}
        case "DOWN" -> {worldY += speed; sprite = down1;}
        case "LEFT" -> {worldX -= speed; sprite = left1;}
        case "RIGHT" -> {worldX += speed; sprite = right1;}
        }
        traveledDistance += speed;
        if (traveledDistance > 150) {
            alive = false;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (canDraw()) {
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
            g2.drawImage(sprite, screenX, screenY, null);
        }
    }

    @Override
    public boolean canUseCapacity() {
        if (gamePanel.fightingStyle) {
            return gamePanel.gameState == gamePanel.fightingState;
        }
        else {
            int i = new Random().nextInt(200) + 1;
            return i > 197 && !alive && user.capacityCooldown == 0;
        }
    }


    @Override
    public void useCapacity(Entity target) {
        if (gamePanel.fightingStyle) {
            if (target != null) {
                Random rand = new Random();
                if (!(rand.nextInt(30) >= 30 - target.speed))
                    target.life -= attack;
            }
        }
        else {
            set(user.worldX, user.worldY, user.direction, true, attack);
            gamePanel.projectileList.add(this);
            user.capacityCooldown = 30;
        }
    }

    @Override
    public void endCapacity() {
    }


    @Override
    public String capacityUsed() {
        return "Bullet Throw to inflict damage";
    }
}
