package startingVillage.objects;

import Main.GamePanel;
import entity.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class House extends Entity {
    private Rectangle doorSolidArea;
    private Rectangle entityArea = new Rectangle();
    private boolean opened;
    private final int[] teleportPosition;

    public House(GamePanel gamePanel,int col, int row,boolean opened, int[] teleportPosition) {
        super(gamePanel);
        worldX = col * gamePanel.tileSize;
        worldY = row * gamePanel.tileSize;

        this.opened = opened;
        this.teleportPosition = teleportPosition;
        type = type_obstacle;
        collision = true;
        dialogues[0][0] = "It seems this door is locked";
    }

    public void setHouseArea(Rectangle houseArea) {
        solidArea = houseArea;
        solidAreaDefaultX = houseArea.x;
        solidAreaDefaultY = houseArea.y;
    }

    public void setDoorSolidArea(Rectangle doorSolidArea) {
        this.doorSolidArea = doorSolidArea;
        doorSolidArea.setLocation(doorSolidArea.x + worldX, doorSolidArea.y + worldY);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void interact(Entity e) {
        entityArea.setBounds(e.worldX + e.solidArea.x, e.worldY + e.solidArea.y, e.solidArea.width, e.solidArea.height);
        if (entityArea.intersects(doorSolidArea) && e.direction.equals("UP")) {
            if (opened)
                gamePanel.eventHandler.teleport(teleportPosition[0],teleportPosition[1],teleportPosition[2]);
            else {
                dialogueSet = 0;
                super.speak();
            }
        }

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
