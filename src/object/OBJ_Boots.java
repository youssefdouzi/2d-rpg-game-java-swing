package object;

import Main.GamePanel;
import entity.Entity;


public class OBJ_Boots extends Entity {


    public OBJ_Boots(GamePanel gamePanel) {
        super(gamePanel);
        name = "Boots";
        collision = true;
        down1 = setup("/objects/boots", gamePanel.tileSize, gamePanel.tileSize);
        price = 100;

    }

}
