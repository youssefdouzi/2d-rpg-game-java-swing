package tile;

import Main.GamePanel;
import entity.Entity;
import tile_interactive.InteractiveTile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Map extends TileManager{

    GamePanel gamePanel;
    BufferedImage[] worldMaps;
    boolean[][][] exploredMaps;

    public Map(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
    }

    public void initializeMaps() {


        worldMaps = new BufferedImage[gamePanel.MAX_MAP];
        int worldMapsWidth = gamePanel.tileSize * gamePanel.MAX_WORLD_COL;
        int worldMapsHeight = gamePanel.tileSize * gamePanel.MAX_WORLD_ROW;

        for (int mapNumber = 0; mapNumber < gamePanel.MAX_MAP; mapNumber ++) {
            worldMaps[mapNumber] = new BufferedImage(worldMapsWidth, worldMapsHeight, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2 = (Graphics2D) worldMaps[mapNumber].createGraphics();
            g2.setColor(Color.black);

            for (int col = 0; col < gamePanel.MAX_WORLD_COL; col++) {
                for (int row = 0; row < gamePanel.MAX_WORLD_ROW; row++) {
                    if (exploredMaps[mapNumber][col][row]) {
                        g2.drawImage(tile[mapTileNumber[mapNumber][col][row]].image, col * gamePanel.tileSize, row * gamePanel.tileSize, null);
                    }
                    else
                        g2.fillRect(col * gamePanel.tileSize,row * gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize);
                }
            }
            g2.dispose();

        }
    }



    public void explore(int mapNumber, int col, int row) {
        Graphics2D g2 = (Graphics2D) worldMaps[mapNumber].getGraphics();

        for (Entity object : gamePanel.objects[mapNumber])
            if (object != null)
                if (object.type == object.type_obstacle && object.worldX/gamePanel.tileSize == col && object.worldY/gamePanel.tileSize == row)
                    g2.drawImage(object.image, col * gamePanel.tileSize, row * gamePanel.tileSize, null);



        if (!exploredMaps[mapNumber][col][row]) {
            if (tile[mapTileNumber[mapNumber][col][row]] != null) {
                g2.drawImage(tile[mapTileNumber[mapNumber][col][row]].image, col * gamePanel.tileSize, row * gamePanel.tileSize, null);
            }
            exploredMaps[mapNumber][col][row] = true;

        }
        g2.dispose();

    }



    public void drawFullMapScreen(Graphics2D g2) {
        //Background color
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        //Draw Map
        int width = 500;
        int height = 500;
        int x = gamePanel.screenWidth/2 - width/2;
        int y = gamePanel.screenHeight/2 - height/2;
        g2.drawImage(worldMaps[gamePanel.currentMap], x, y, width, height, null);

        //Draw Player
        double scale = (double) (gamePanel.tileSize * gamePanel.MAX_WORLD_COL) / width;
        int playerX = (int) (x + gamePanel.player.worldX/scale);
        int playerY = (int) (y + gamePanel.player.worldY/scale);
        int playerSize = (int) (gamePanel.tileSize/scale);
        BufferedImage player = null;
        switch (gamePanel.player.direction) {
            case "UP"-> player = gamePanel.player.upStanding;
            case "DOWN"-> player = gamePanel.player.downStanding;
            case "LEFT"-> player = gamePanel.player.leftStanding;
            case "RIGHT"-> player = gamePanel.player.rightStanding;
        }
        g2.drawImage(player, playerX, playerY, playerSize, playerSize, null);

        //Hint
        /*
        g2.setFont(gamePanel.ui.maruMonica.deriveFont(321));
        g2.setColor(Color.white);
        g2.drawString("Press M to close", 750, 550);
        */
    }





    public boolean[][][] getExploredMaps() {
        return exploredMaps;
    }

    public void setExploredMaps(boolean[][][] exploredMaps) {
        this.exploredMaps = exploredMaps;
    }
}
