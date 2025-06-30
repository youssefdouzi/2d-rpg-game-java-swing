package object;

import Main.GamePanel;
import entity.Entity;
import entity.Player;

public class OBJ_SkeletonDoor extends Entity {

    public boolean opened;

    public OBJ_SkeletonDoor(GamePanel gamePanel, boolean opened) {
        super(gamePanel);
        name = "Skeleton Door";
        collision = true;
        down1 = setup("/objects/skeleton_door_closed", gamePanel.tileSize, gamePanel.tileSize);
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
                gamePanel.gameState = gamePanel.dialogueState;
                OBJ_Skeleton_Key tempKey = new OBJ_Skeleton_Key(gamePanel);
                gamePanel.keyHandler.enterPressed = false;
                if (p.hasItem(tempKey)) {
                    p.getItem(tempKey).use(this);
                    collision = false;
                    down1 = setup("/objects/skeleton_door_opened", gamePanel.tileSize, gamePanel.tileSize);
                }
                else
                    gamePanel.ui.currentDialogue[0]="You need something special to open this...";
            }
        }
    }
}
