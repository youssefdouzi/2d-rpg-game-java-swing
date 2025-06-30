package object;

import Main.GamePanel;
import entity.Entity;
import entity.Player;


public class OBJ_Key extends Entity {


    public OBJ_Key(GamePanel gamePanel, int amount) {
        super(gamePanel);
        name = "Key";
        down1 = setup("/objects/key", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nIt opens a door.";
        price = 25;
        stackable = true;
        this.amount = amount;
    }

    @Override
    public void use(Entity e) {
        if (e instanceof OBJ_Door door) {
            gamePanel.ui.currentDialogue[0] = "Key used to open the door";
            door.opened = true;
            amount --;
            if (amount == 0) {
                gamePanel.player.inventory.remove(this);
            }
        }
    }

}
