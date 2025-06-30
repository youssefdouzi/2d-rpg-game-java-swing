package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Coin_Bronze extends Entity {

    GamePanel gamePanel;

    public OBJ_Coin_Bronze(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_pickUpOnly;
        value = 1;
        name = "Bronze Coin";
        down1 = setup("/objects/coin_bronze", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void use(Entity entity) {

        gamePanel.playSE(1);
        gamePanel.ui.addMessage("Coin + " + value);
        gamePanel.player.coin += value;
    }



}
