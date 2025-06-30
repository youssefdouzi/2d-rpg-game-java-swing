package Main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EnvironmentManager {

    private Filter filter;
    private Environment environment;
    private Time time;
    BufferedImage[] backgrounds;

    public EnvironmentManager(GamePanel gamePanel) {
        this.filter = new Filter(gamePanel);
        this.time = gamePanel.time;
        backgrounds = new BufferedImage[4];
        backgrounds[0] = gamePanel.player.setup("/background/forest",960,376);
        backgrounds[1] = gamePanel.player.setup("/background/castle",960,376);
    }

    public void update() {
        if (environment != Environment.DUNGEON) {
            if (time.hasHourChanged()) {
                filter.update(time.getState());
                time.setHourChanged(false);
            }
        }
        else {
            filter.update(DayState.NIGHT);
        }

    }

    public void draw(Graphics2D g2) {
        if (environment.isOutside() || environment == Environment.DUNGEON) {

            filter.draw(g2);
        }
    }

    public void setEnvironment(int currentMap) {
        switch (currentMap) {
            case 0 -> environment = Environment.OUTSIDE_FOREST;
            case 4,5 -> environment = Environment.DUNGEON;
            default -> environment = Environment.HOUSE;
        }
    }

    public BufferedImage getBackground() {
        return switch (environment) {
            case OUTSIDE_FOREST -> backgrounds[0];
            case DUNGEON -> backgrounds[1];
            case HOUSE -> backgrounds[1];
            default -> backgrounds[0];
        };
    }
}
