package entity;

public class Swiftness implements Capacity{

    private Entity user;

    public Swiftness(Entity user) {
        this.user = user;
    }


    @Override
    public boolean canUseCapacity() {
        if (user.gamePanel.fightingStyle)
            return user.gamePanel.gameState == user.gamePanel.fightingState && user.capacityCooldown == 0;
        return user.capacityCooldown == 0;
    }

    @Override
    public void useCapacity(Entity target) {
        user.baseSpeed += 5;
        user.speed += 5;
        if (user.gamePanel.fightingStyle)
            user.capacityCooldown = 999;
        else
            user.capacityCooldown = 120*60;
    }

    @Override
    public void endCapacity() {
        if (user.gamePanel.fightingStyle) {
            if (user.capacityCooldown == 999-4) {
                user.speed -= 5;
            }
        }
        else if (user.capacityCooldown == (120-10)*60) {
                user.baseSpeed -= 5;
            }
    }

    @Override
    public String capacityUsed() {
        return "Swiftness to greatly raise its speed";
    }
}
