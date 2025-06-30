package entity;

import Main.GamePanel;

import java.awt.*;

public class Teleporter extends Entity {

    private final int[] teleportPosition;
    private Rectangle entityArea = new Rectangle();

    public Teleporter(GamePanel gamePanel, int col, int row, int[] teleportPosition, int direction) {
        super(gamePanel);
        worldX = gamePanel.tileSize * col;
        worldY = gamePanel.tileSize * row;
        // 0 = UP     1 = DOWN      2 = LEFT       3 = RIGHT
        switch (direction) {
            case 0 -> {
                solidArea.x = 5;
                solidArea.y = 0;
                solidArea.width = gamePanel.tileSize-10;
                solidArea.height = gamePanel.tileSize/2;
            }
            case 1 -> {
                solidArea.x = 5;
                solidArea.y = gamePanel.tileSize/2;
                solidArea.width = gamePanel.tileSize-10;
                solidArea.height = gamePanel.tileSize/2;
            }
            case 2 -> {
                solidArea.x = 0;
                solidArea.y = 5;
                solidArea.width = gamePanel.tileSize/2;
                solidArea.height = gamePanel.tileSize-10;
            }
            case 3 -> {
                solidArea.x = gamePanel.tileSize/2;
                solidArea.y = 5;
                solidArea.width = gamePanel.tileSize/2;
                solidArea.height = gamePanel.tileSize-10;
            }
        }
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        collision = false;
        this.teleportPosition = teleportPosition;
        type = type_interactive;
    }



    @Override
    public void interact(Entity e) {
        entityArea.setBounds(e.worldX + e.solidArea.x, e.worldY + e.solidArea.y, e.solidArea.width, e.solidArea.height);
        solidArea.setLocation(solidArea.x + worldX, solidArea.y + worldY);
        if (entityArea.intersects(solidArea)) {
            gamePanel.eventHandler.teleport(teleportPosition[0], teleportPosition[1], teleportPosition[2]);
        }
        solidArea.setLocation(solidAreaDefaultX, solidAreaDefaultY);
    }

}
