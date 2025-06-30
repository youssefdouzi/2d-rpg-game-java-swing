package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Axe extends Entity {
    public OBJ_Axe(GamePanel gamePanel) {
        super(gamePanel);

        type = type_axe;
        name = "Woodcutter's Axe";
        down1 = setup("/objects/axe", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\nA bit rusty but still \ncan cut some trees.";
        price = 75;
        motion1_duration = 20;
        motion2_duration = 40;
    }
}
