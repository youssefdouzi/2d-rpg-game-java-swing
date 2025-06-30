package object;

import Main.GamePanel;
import entity.Entity;
import entity.Player;


public class OBJ_Chest extends Entity {

    public boolean opened;
    public Entity loot;

    public OBJ_Chest(GamePanel gamePanel, Entity loot, boolean opened) {
        super(gamePanel);
        name = "Chest";
        if (!opened)
            down1 = setup("/objects/chest (OLD)", gamePanel.tileSize, gamePanel.tileSize);
        else
            down1 = setup("/objects/chestOpened (OLD)", gamePanel.tileSize, gamePanel.tileSize);

        this.loot = loot;
    }

    @Override
    public void interact(Entity entity) {
        if (!opened) {
            if (entity instanceof Player p) {
                gamePanel.gameState=gamePanel.dialogueState;
                gamePanel.keyHandler.enterPressed = false;
                p.inventory.add(loot);

                gamePanel.ui.currentDialogue[0] = "You got a " + name + "!";

                down1 = setup("/objects/chestOpened (OLD)", gamePanel.tileSize, gamePanel.tileSize);
                loot = null;
            }
        }

    }

}
