package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_ManaCrystal extends Entity {

    GamePanel gamePanel;

    public OBJ_ManaCrystal(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_pickUpOnly;
        name = "Mana Crystal";
        value = 1;
        down1 = setup("/objects/manacrystal_full", gamePanel.tileSize, gamePanel.tileSize);
        image = setup("/objects/manacrystal_full", gamePanel.tileSize, gamePanel.tileSize);
        image2 = setup("/objects/manacrystal_blank", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void use(Entity entity) {
        gamePanel.playSE(2);
        gamePanel.ui.addMessage("Mana + " + value);
        entity.resource.replenish(value);
    }
}
