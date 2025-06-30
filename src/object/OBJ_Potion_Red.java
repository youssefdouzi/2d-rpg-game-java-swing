package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Potion_Red extends Entity implements Consummable {

    GamePanel gamePanel;

    public OBJ_Potion_Red(GamePanel gamePanel, int amount) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_consumable;
        value = 5;
        name = "Red Potion";
        down1 = setup("/objects/potion_red", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nHeals your life by " + value + ".";

        super.dialogues[0][0] = "You drink the " + name + "\nYour life has been recovered by " + value + ".";
        price = 65;
        stackable = true;
    }



    @Override
    public void use(Entity entity) {
        speak();
        entity.life += value;
        if (gamePanel.player.life > gamePanel.player.maxLife) {
            gamePanel.player.life = gamePanel.player.maxLife;
        }
        amount --;
        if (amount == 0) {
            inventory.remove(this);
        }
        gamePanel.playSE(2);
    }



}
