package startingVillage.objects;

import Main.GamePanel;

import java.awt.*;

public class BasicHouse extends House{
    public BasicHouse(GamePanel gamePanel, int col, int row, boolean opened, int[] teleportPosition) {
        super(gamePanel, col, row, opened, teleportPosition);
        setHouseArea(new Rectangle(0, 50,gamePanel.tileSize*4,gamePanel.tileSize*4-50));
        setDoorSolidArea(new Rectangle(50, gamePanel.tileSize*4-70,30,80));
        setImage(setup("/startingVillage/objects/basic_house",gamePanel.tileSize*4,gamePanel.tileSize*4));
    }
}
