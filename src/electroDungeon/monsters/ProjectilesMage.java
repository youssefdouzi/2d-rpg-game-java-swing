package electroDungeon.monsters;

import Main.GamePanel;
import entity.Capacity;
import entity.Entity;
import entity.Projectile;

public class ProjectilesMage implements Capacity {

    Entity user;
    GamePanel gamePanel;
    Projectile projectile1,projectile2;



    public ProjectilesMage(GamePanel gamePanel, Entity user) {
        this.user = user;
        this.gamePanel = gamePanel;
        projectile1 = new ProjectileMage1(gamePanel, user);
        projectile2 = new ProjectileMage2(gamePanel, user);
    }

    @Override
    public boolean canUseCapacity() {
        return false;
    }

    @Override
    public void useCapacity(Entity target) {

    }

    @Override
    public void endCapacity() {

    }

    @Override
    public String capacityUsed() {
        return "";
    }
}
