package natureDungeon.objects;

import Main.GamePanel;
import entity.Entity;

import java.awt.*;

public class Ronces extends Entity {
    public Ronces(GamePanel gamePanel, int col, int row) {
        super(gamePanel);
        worldX = col * gamePanel.tileSize;
        worldY = row * gamePanel.tileSize;
        down1 = setup("/natureDungeon/objects/ronces",gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/electroDungeon/objects/electric_gate_up_on_3",gamePanel.tileSize, gamePanel.tileSize);
        collision = true;
        solidArea.setBounds(0,0,gamePanel.tileSize,gamePanel.tileSize);
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }

    @Override
    public void setAction() {
        collision = false;
    }

    @Override
    public void draw(Graphics2D g2) {
        if (canDraw()) {
            if (collision)
                g2.drawImage(down1, worldX, worldY, null);
            else
                g2.drawImage(down2, worldX, worldY, null);
        }
    }

}
