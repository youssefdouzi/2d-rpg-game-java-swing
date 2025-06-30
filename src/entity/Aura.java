package entity;


public class Aura implements Capacity {

    private Entity user;

    public Aura(Entity user) {
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
        user.strength += 2;
        user.dexterity += 2;
        if (user.gamePanel.fightingStyle)
            user.capacityCooldown = 999;
        else
            user.capacityCooldown = 120*60;
    }

    @Override
    public void endCapacity() {
        if (user.gamePanel.fightingStyle) {
            if (user.capacityCooldown == 999 - 2) {
                user.strength -= 2;
                user.dexterity -= 2;
            }
        }
        else if (user.capacityCooldown == (120-15)*60) {
                user.strength -= 2;
                user.dexterity -= 2;
            }
    }

    @Override
    public String capacityUsed() {
        return "Aura to raise its strength and dexterity";
    }
}
