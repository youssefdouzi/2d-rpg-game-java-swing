package object;

import Main.GamePanel;
import Main.Sound;
import entity.Entity;
import entity.LootTable;
import entity.Player;

import java.awt.*;

public abstract class Breakable extends Entity {

    private Sound breakSound;
    private LootTable tableLoot;
    private int index;

    public Breakable(GamePanel gamePanel, int col, int row, boolean collision, int maxLife, int index) {
        super(gamePanel);
        worldX = gamePanel.tileSize * col;
        worldY = gamePanel.tileSize * row;
        type = type_obstacle;
        this.collision = collision;
        this.maxLife = maxLife;
        life = maxLife;
        this.index = index;
    }

    public void setTableLoot(LootTable tableLoot) {
        this.tableLoot = tableLoot;
    }

    public void setBreakSound(Sound breakSound) {
        this.breakSound = breakSound;
    }

    @Override
    public void interact(Entity e) {
        Player p = (Player) e;
        p.attackCanceled = false;
        if (life > 0) {
            dropItem(tableLoot.dropRandomItem());
            if (breakSound != null)
                breakSound.play();
            life --;
        }
        if (life == 0)
            gamePanel.objects[gamePanel.currentMap][index] = null;


    }

    @Override
    public void draw(Graphics2D g2) {
        if (canDraw()) {
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
            g2.drawImage(image, screenX, screenY, null);
        }
    }

}
