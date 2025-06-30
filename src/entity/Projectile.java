package entity;

import Main.GamePanel;

import java.awt.*;

public class Projectile extends Entity {

    public Entity user;
    public int particleSize;
    public int particleSpeed;
    public int particleMaxLife;
    public Color particleColor;

    public Projectile(GamePanel gamePanel, Entity user) {
        super(gamePanel);
        this.user = user;
    }

    public void set(int worldX, int worldY, String direction, boolean alive, int attack) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.attack = attack;
        this.life = this.maxLife;
    }
    public void update() {

        if (user == gamePanel.player) {
            int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monsters);
            if (monsterIndex != -1) {
                gamePanel.player.damageMonster(monsterIndex, attack);
                generateParticle((Projectile) user.capacity, gamePanel.monsters[gamePanel.currentMap][monsterIndex]);
                alive = false;
            }
        } else {
            boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);
            if (contactPlayer && !gamePanel.player.invincible) {
                damagePlayer(attack);
                generateParticle((Projectile) user.capacity, gamePanel.player);
                alive = false;
            }
        }


        switch (direction) {
            case "UP" -> worldY -= speed;
            case "DOWN" -> worldY += speed;
            case "LEFT" -> worldX -= speed;
            case "RIGHT" -> worldX += speed;
        }

        life--;
        if (life <= 0) {
            alive = false; // means that it disappears after 80 frames
        }

        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNumber == 1) {
                spriteNumber = 2;
            }
            else if (spriteNumber == 2) {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }
    }


    @Override
    public int getParticleSize() {
        return particleSize;
    }

    @Override
    public int getParticleSpeed() {
        return particleSpeed;
    }

    @Override
    public int getParticleMaxLife() {
        return particleMaxLife;
    }

    @Override
    public Color getParticleColor() {
        return particleColor;
    }

    public boolean haveResource(Entity user) {
        return user.resource.getQuantity() >= useCost;

    }
    public void consumeResource(Entity user) {
        user.resource.use(useCost);
    }
}
