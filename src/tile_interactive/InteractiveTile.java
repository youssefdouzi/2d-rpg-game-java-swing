package tile_interactive;

import Main.GamePanel;
import entity.Entity;

import java.awt.*;

public class InteractiveTile extends Entity {

    GamePanel gamePanel;
    public boolean destructible = false;
    public int col;
    public int row;

    public InteractiveTile(GamePanel gamePanel, int col, int row) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        this.col = col;
        this.row = row;
    }

    public boolean isCorrectItem(Entity entity) {
        return false;
    }

    public void update() {
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 20) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void playSE() {}

    public InteractiveTile getDestroyedForm() {
        return null;
    }
    public void draw(Graphics2D g2D) {

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (canDraw()
        ) {
            g2D.drawImage(down1, screenX, screenY, null);
        }
    }


}
