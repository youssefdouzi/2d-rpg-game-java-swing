package object;

import Main.GamePanel;
import entity.Capacity;
import entity.Entity;
import entity.Projectile;

import java.awt.*;
import java.util.Random;

public class OBJ_Fireball extends Projectile implements Capacity {

    GamePanel gamePanel;

    public OBJ_Fireball(GamePanel gamePanel, Entity user) {
        super(gamePanel,user);
        this.gamePanel = gamePanel;
        name = "Fireball";
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        particleSize = 10;
        particleSpeed = 1;
        particleMaxLife = 20;
        particleColor = new Color(240, 50, 0);
        getImage();
    }

    public void getImage() {
        super.getImage("/projectiles/fireball");
    }


    @Override
    public boolean canUseCapacity() {
        if (gamePanel.fightingStyle) return gamePanel.gameState == gamePanel.fightingState && haveResource(user);

        else return !alive && user.capacityCooldown == 0 && haveResource(user);
    }


    @Override
    public void useCapacity(Entity target) {
        if (gamePanel.fightingStyle) {
            Random rand = new Random();
            if (!(rand.nextInt(30) >= 30-target.speed))
                target.life -= user.getAttack();
            user.resource.use(useCost);
            gamePanel.playSE(11);
        }
        else {
            // SET DEFAULT COORDINATES, DIRECTION AND USER
            set(user.worldX, user.worldY, user.direction, true, user.getAttack());

            // CONSUME RESOURCE
            consumeResource(user);

            // ADD IT TO THE LIST
            gamePanel.projectileList.add(this);

            user.capacityCooldown = 30;

            gamePanel.playSE(11);
        }
    }

    @Override
    public void endCapacity() {
    }

    @Override
    public String capacityUsed() {
        return "Magic to throw a " + name +" at the ennemy";
    }
}
