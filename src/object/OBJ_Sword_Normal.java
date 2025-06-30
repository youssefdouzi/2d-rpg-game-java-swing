package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Sword_Normal extends Entity {

    public OBJ_Sword_Normal(GamePanel gamePanel) {
        super(gamePanel);

        type = type_sword;
        name = "Normal Sword";
        down1 = setup("/objects/sword_normal", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nAn old sword.";
        price = 20;
        motion1_duration = 5;
        motion2_duration = 25;

    }
}
