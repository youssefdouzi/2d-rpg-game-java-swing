package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Shield_Blue extends Entity {
    public OBJ_Shield_Blue(GamePanel gamePanel) {
        super(gamePanel);

        type = type_shield;
        name = "Blue Shield";
        down1 = setup("/objects/shield_blue", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 2;
        description = "[" + name + "]\nA shiny blue shield.";
        price = 200;


    }
}
