package object;

import Main.GamePanel;
import entity.Capacity;
import entity.Entity;
import entity.Projectile;

import java.util.Random;

public class OBJ_Rock extends Projectile implements Capacity {
    GamePanel gamePanel;

    public OBJ_Rock(GamePanel gamePanel, Entity user) {
        super(gamePanel, user);
        this.gamePanel = gamePanel;
        name = "Rock";
        speed = 8;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/projectiles/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/projectiles/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/projectiles/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/projectiles/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/projectiles/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/projectiles/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/projectiles/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/projectiles/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
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
            set(user.worldX, user.worldY, user.direction, true, 2);
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
