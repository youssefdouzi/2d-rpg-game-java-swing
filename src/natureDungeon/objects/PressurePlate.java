package natureDungeon.objects;

import Main.GamePanel;
import entity.Entity;

import java.awt.*;
import java.util.List;

public class PressurePlate extends Entity {

    private boolean pressed = false;
    private List<Entity> linkedEntities;

    public PressurePlate(GamePanel gamePanel, int x, int y) {
        super(gamePanel);
        worldX = x;
        worldY = y;
        collision = false;
        dialogues[0][0] = "";
        down1 = setup("/natureDungeon/objects/pressurePlate",gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setLinkedEntities(List<Entity> linkedEntities) {
        this.linkedEntities = linkedEntities;
    }

    @Override
    public void interact(Entity e) {
        if (!pressed) {
            gamePanel.gameState = gamePanel.dialogueState;
            gamePanel.ui.currentDialogue[0] = dialogues[0][0];
            for (Entity linkedEntity : linkedEntities) {
                linkedEntity.setAction();
            }
        }
        pressed = true;
    }

    @Override
    public void draw(Graphics2D g2) {
        if (canDraw()) {
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
            g2.drawImage(down1, screenX, screenY, null);
        }
    }

}
