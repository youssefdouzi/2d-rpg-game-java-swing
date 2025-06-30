package tile_interactive;

import Main.CollisionChecker;
import Main.GamePanel;
import entity.Entity;

import java.awt.*;

public class MovingTile extends Entity {

    public final CollisionChecker collisionChecker;


    public MovingTile(GamePanel gamePanel, int worldX, int worldY, int speed) {
        super(gamePanel);
        this.worldX = worldX;
        this.worldY = worldY;
        type = type_obstacle;
        this.speed = speed;
        collisionChecker = new CollisionChecker(gamePanel);
    }

    @Override
    public void draw(Graphics2D g2D) {
        if (canDraw()) {

            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
            g2D.drawImage(down1, screenX, screenY, null);
        }
    }


    @Override
    public void interact(Entity e) {
        this.direction = e.direction;
        collisionChecker.checkTile(this);
        collisionChecker.checkObject(this,false);
        if (!collisionOn)
            switch (e.direction) {
                case "DOWN" -> {worldY += speed; e.worldY += speed;}
                case "UP" -> {worldY -= speed; e.worldY -= speed;}
                case "LEFT" -> {worldX -= speed; e.worldX -= speed;}
                case "RIGHT" -> {worldX += speed; e.worldX += speed;}
            }


    }

}
