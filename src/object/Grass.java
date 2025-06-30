package object;

import Main.GamePanel;

public class Grass extends Breakable {
    public Grass(GamePanel gamePanel, int col, int row, int index) {
        super(gamePanel, col, row, true, 1, index);
        image = setup("/objects/grass",gamePanel.tileSize,gamePanel.tileSize);
        setTableLoot(gamePanel.objectSetter.getLootTable("Grass"));
    }
}
