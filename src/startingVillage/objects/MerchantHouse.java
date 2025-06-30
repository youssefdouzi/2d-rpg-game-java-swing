package startingVillage.objects;

import Main.GamePanel;

import java.awt.*;

public class MerchantHouse extends House{
    public MerchantHouse(GamePanel gamePanel, int col, int row, boolean opened, int[] teleportPosition) {
        super(gamePanel, col, row, opened, teleportPosition);
        setHouseArea(new Rectangle(0, 50,gamePanel.tileSize*4,gamePanel.tileSize*4-50));
        setDoorSolidArea(new Rectangle(50, gamePanel.tileSize*4-70,30,80));
        setImage(setup("/startingVillage/objects/merchant_house",gamePanel.tileSize*4,gamePanel.tileSize*4));
    }
}
