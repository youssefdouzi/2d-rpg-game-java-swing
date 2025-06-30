package object;

import Main.GamePanel;
import entity.Entity;
import entity.Player;

public class OBJ_Door extends Entity {

    public boolean opened;

    public OBJ_Door(GamePanel gamePanel,  boolean opened) {
        super(gamePanel);
        name = "Door";
        collision = true;
        down1 = setup("/objects/door", gamePanel.tileSize, gamePanel.tileSize);
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        type = type_obstacle;
        this.opened = opened;
    }

    @Override
    public void interact(Entity entity){
        if (!opened) {
            if (entity instanceof Player p) {
                gamePanel.gameState=gamePanel.dialogueState;
                OBJ_Key tempKey = new OBJ_Key(gamePanel,1);
                gamePanel.keyHandler.enterPressed = false;
                if (p.hasItem(tempKey)) {
                    p.getItem(tempKey).use(this);
                    down1 = setup("/objects/door", gamePanel.tileSize, gamePanel.tileSize);
                    solidArea.width = 2;
                }
                else
                    gamePanel.ui.currentDialogue[0]="You need a key to open this";
            }
        }
    }


}
