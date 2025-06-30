package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Magic_Wand extends Entity {


    public OBJ_Magic_Wand(GamePanel gamePanel) {
        super(gamePanel);

        name = "Magic Wand";
        down1 = setup("/objects/magic_wand", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 1;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\nA magic wand \nhelpful to cast spells.";
        price = 150;
    }
}
