package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Shield_Wood extends Entity {

    public OBJ_Shield_Wood(GamePanel gamePanel) {
        super(gamePanel);

        type = type_shield;
        name = "Wood Shield";
        down1 = setup("/objects/shield_wood", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 1;
        description = "[" + name + "]\nMade by wood.";
        price = 100;

    }
}
