package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Skeleton_Key extends Entity{

    public OBJ_Skeleton_Key(GamePanel gamePanel) {
        super(gamePanel);
        name = "Skeleton Key";
        down1 = setup("/objects/skeleton_key", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nObtained after beating the Skeleton Lord. \nCan be used to open a skeleton door...";
    }

    @Override
    public void use(Entity e) {
        if (e instanceof OBJ_SkeletonDoor door) {
            gamePanel.ui.currentDialogue[0] = "Skeleton key used to open the door";
            door.opened = true;
            amount --;
            if (amount == 0) {
                gamePanel.player.inventory.remove(this);
            }
        }
    }
}
