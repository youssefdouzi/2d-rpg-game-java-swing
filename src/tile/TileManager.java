package tile;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class TileManager {

    GamePanel gamePanel;
    public Tile[] tile;
    public int[][][] mapTileNumber;
    boolean drawPath = true;
    List<Integer> collision = List.of(16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,47,48,49,50,51,52,53,54,55,56,57,58,63,64,65,66,67,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,104);
    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[999]; // add more if we want more than 10 types of tiles
        mapTileNumber = new int[gamePanel.MAX_MAP][gamePanel.MAX_WORLD_COL][gamePanel.MAX_WORLD_ROW];

        getTileImage();
        loadMap("/startingVillage/startingVillageMap.txt", 0);
        loadMap("/maps/merchant_house.txt", 1);
        loadMap("/greatForest/greatForestMap.txt", 2);
        loadMap("/maps/basicHouse2.txt", 3);
        loadMap("/castleDungeon/castleDungeonFloor1.txt", 4);
        loadMap("/castleDungeon/castleDungeonFloor2.txt", 5);
        loadMap("/castleDungeon/castleDungeonFloor3.txt", 6);
        loadMap("/electroDungeon/electroDungeon.txt", 7);
        loadMap("/electroDungeon/electro_arena.txt", 8);


    }



    public void getTileImage() {

        for (int i = 0; i < 133; i++) {
            String name = "";
            if (i < 10)
                name += "00" + i;
            else if (i < 100)
                name += "0" + i;
            else  name += i;
            setup(i, name, collision.contains(i));
        }

    }
    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imageName + ".png")));
            tile[index].image = uTool.scaleImage(tile[index].image, gamePanel.tileSize, gamePanel.tileSize);
            tile[index].collision = collision;


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void loadMap(String filename, int mapNum) {
        try {
            Scanner scanner = new Scanner(new File("ressources/" + filename));

            String line = scanner.nextLine();
            int i = 0;
            while (scanner.hasNextLine()) {
                String[] numbers = line.split(" ");
                for (int j = 0; j < numbers.length; j++) {
                    mapTileNumber[mapNum][j][i] = Integer.parseInt(numbers[j]);
                }
                i++;
                line = scanner.nextLine();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    public void draw(Graphics2D g2D) {

        int worldCol = 0;
        int worldRow = 0;


        while (worldCol < gamePanel.MAX_WORLD_COL && worldRow < gamePanel.MAX_WORLD_ROW) {

            int tileNum = mapTileNumber[gamePanel.currentMap][worldCol][worldRow];

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY
            ) {
                gamePanel.map.explore(gamePanel.currentMap, worldCol, worldRow);
                if(tile[tileNum] != null) g2D.drawImage(tile[tileNum].image, screenX, screenY, null);
            }


            worldCol++;

            if (worldCol == gamePanel.MAX_WORLD_COL) {
                worldCol = 0;
                worldRow ++;
            }
        }
        /*
        // Comment this if statement if you don't want to see the path on the screen
        if (drawPath) {
            g2D.setColor(new Color(255, 0, 0, 70));

            for (int i = 0; i < gamePanel.pFinder.pathList.size(); i++) {
                int worldX = gamePanel.pFinder.pathList.get(i).col * gamePanel.tileSize;
                int worldY = gamePanel.pFinder.pathList.get(i).row * gamePanel.tileSize;
                int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
                int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

                g2D.fillRect(screenX, screenY, gamePanel.tileSize, gamePanel.tileSize);
            }
        }*/



    }


}
