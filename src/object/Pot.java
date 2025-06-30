package object;

import Main.GamePanel;

public class Pot extends Breakable {
    public Pot(GamePanel gamePanel, int col, int row, int index) {
        super(gamePanel, col, row, true, 1, index);
        image = setup("/objects/pot",gamePanel.tileSize,gamePanel.tileSize);
        setTableLoot(gamePanel.objectSetter.getLootTable("Pot"));
    }
}
