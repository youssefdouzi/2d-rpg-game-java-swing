package electroDungeon.objects;

import Main.GamePanel;
import entity.Entity;
import tile_interactive.MovingTile;

import java.awt.*;

public class Lumix extends MovingTile {
    private final ElectricalComponent powerSource;
    private int poweredCount = 0;

    public Lumix(GamePanel gamePanel, int worldX, int worldY, int speed) {
        super(gamePanel, worldX, worldY, speed);
        powerSource = new ElectricalComponent(gamePanel, worldX/gamePanel.tileSize, worldY/gamePanel.tileSize);
        this.worldX = worldX;
        this.worldY = worldY;
        solidArea.x = 10;
        solidArea.y = 10;
        solidArea.width = 28;
        solidArea.height = 38;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        powerSource.setImages("/electroDungeon/objects/lumix");
        powerSource.setMaxFormCount(60);
        type = type_interactive;
        name = "Lumix";
        collision = true;
    }
    public void setLocation(int x, int y) {
        worldX = x;
        worldY = y;
        powerSource.worldX = x;
        powerSource.worldY = y;
    }

    @Override
    public void update() {
        if (poweredCount == 0)
            powerSource.setPowered(false);
        else
            poweredCount--;
    }

    @Override
    public void interact(Entity e) {
        if (poweredCount < 60) {
            super.interact(e);
            powerSource.worldX = worldX;
            powerSource.worldY = worldY;
        }

    }

    public void setPowered(boolean powered) {
        powerSource.setPowered(powered);
        poweredCount = 180;
    }

    @Override
    public void draw(Graphics2D g2) {
        if (canDraw()) {
            powerSource.setFormCount(powerSource.getFormCount() + 1);
            if (powerSource.getFormCount() == powerSource.getMaxFormCount()) {
                powerSource.setFormCount(0);
            }
            if (powerSource.getFormCount() < powerSource.getMaxFormCount()/4)
                powerSource.setCurrentForm(0);
            else if (powerSource.getFormCount() < powerSource.getMaxFormCount()/4 * 2)
                powerSource.setCurrentForm(1);
            else if (powerSource.getFormCount() < powerSource.getMaxFormCount()/4 * 3)
                powerSource.setCurrentForm(2);
            else
                powerSource.setCurrentForm(3);

            powerSource.draw(g2);
        }
    }


    public ElectricalComponent getPowerSource() {
        return powerSource;
    }

    public int getPoweredCount() {
        return poweredCount;
    }
}
