package entity;

import Main.GamePanel;

import java.awt.*;

public class Particle extends Entity{

    Entity generator;
    Color color;
    int size;
    int xd;
    int yd;

    public Particle(GamePanel gamePanel, Entity generator, Color color, int size, int speed, int maxLife, int xd, int yd) {
        super(gamePanel);

        this.generator = generator;
        this.color = color;
        this.xd = xd;
        this.yd = yd;
        this.speed = speed;
        this.size = size;
        this.maxLife = maxLife;

        life = maxLife;
        int offset = (gamePanel.tileSize / 2) - (size / 2);
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
    }
    public void update() {

        life--;

        if (life < maxLife / 3) {
            yd++;
        }

        worldX += xd * speed;
        worldY += yd * speed;

        if (life == 0) {
            alive = false;
        }
    }
    public void draw(Graphics2D g2D) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        g2D.setColor(color);
        g2D.fillRect(screenX, screenY, size, size);
    }

}
