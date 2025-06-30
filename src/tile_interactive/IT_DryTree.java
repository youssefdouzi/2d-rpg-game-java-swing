package tile_interactive;

import Main.GamePanel;
import entity.Entity;

import java.awt.*;

public class IT_DryTree extends InteractiveTile {

    GamePanel gamePanel;

    public IT_DryTree(GamePanel gamePanel, int col, int row) {
        super(gamePanel, col, row);
        this.gamePanel = gamePanel;

        this.worldX = gamePanel.tileSize * col;
        this.worldY = gamePanel.tileSize * row;

        down1 = setup("/tiles_interactives/drytree", gamePanel.tileSize, gamePanel.tileSize);
        destructible = true;
        life = 1;
    }
    public boolean isCorrectItem(Entity entity) {
        return entity.currentWeapon.type == type_axe;
    }
    public void playSE() {
        gamePanel.playSE(12);
    }
    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = new IT_Trunk(gamePanel, worldX / gamePanel.tileSize, worldY / gamePanel.tileSize);
        return tile;
    }
    public Color getParticleColor() {
        return new Color(65, 50, 30);
    }
    public int getParticleSize() {
        return 6;
    }
    public int getParticleSpeed() {
        return 1;
    }
    public int getParticleMaxLife() {
        return 20;
    }
}
