package Main;

import entity.Entity;
import entity.Player;
import object.OBJ_Fireball;
import tile.Map;

import java.io.*;
import java.util.Arrays;

public class Loader {
    GamePanel gamePanel;
    UtilityTool utilityTool = new UtilityTool();
    public Loader(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void loadGame(String directoryPath) {
        loadPlayer(directoryPath);
        loadMapsState(directoryPath);
        loadExploredMaps(directoryPath);
    }
    public void loadPlayer(String directoryPath) {
        gamePanel.player = new Player(gamePanel, gamePanel.keyHandler);
        try {
            InputStream is = new FileInputStream(directoryPath + "/player.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String locationLine = br.readLine();
            loadLocation(locationLine);

            String statsLine = br.readLine();
            loadStats(statsLine);

            String inventoryLine = br.readLine();
            loadInventory(inventoryLine);

            String timeLine = br.readLine();
            loadTime(timeLine);

            br.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        gamePanel.player.attack = gamePanel.player.getAttack();
        gamePanel.player.defense = gamePanel.player.getDefense();
        gamePanel.player.instantiatePlayer();
        gamePanel.arena.setPlayer(gamePanel.player);


    }

    private void loadTime(String timeLine) {
        gamePanel.time = new Time(Integer.parseInt(timeLine));
    }

    public void loadLocation(String locationLine) {
        String[] location = locationLine.split(" ");
        gamePanel.player.worldX = Integer.parseInt(location[0]);
        gamePanel.player.worldY = Integer.parseInt(location[1]);
        gamePanel.player.direction = location[2];
    }

    public void loadStats(String statsLine) {
        String[] stats = statsLine.split(" ");
        gamePanel.player.playerClass = stats[0];
        gamePanel.player.name = stats[1];
        gamePanel.player.maxLife = Integer.parseInt(stats[2]);
        gamePanel.player.life = Integer.parseInt(stats[3]);
        gamePanel.player.resource = utilityTool.stringToResource(stats[5], Integer.parseInt(stats[6]), Integer.parseInt(stats[7]));
        gamePanel.player.speed = Integer.parseInt(stats[8]);
        gamePanel.player.baseSpeed = Integer.parseInt(stats[9]);
        gamePanel.player.level = Integer.parseInt(stats[10]);
        gamePanel.player.strength = Integer.parseInt(stats[11]);
        gamePanel.player.baseStrength = gamePanel.player.strength;
        gamePanel.player.dexterity = Integer.parseInt(stats[12]);
        gamePanel.player.baseDexterity = gamePanel.player.dexterity;
        gamePanel.player.exp = Integer.parseInt(stats[13]);
        gamePanel.player.nextLevelExp = Integer.parseInt(stats[14]);
        gamePanel.player.coin = Integer.parseInt(stats[15]);
        gamePanel.player.capacity = utilityTool.StringToCapacity(stats[17], gamePanel.player);


    }



    public void loadInventory(String inventoryLine) {
        if (inventoryLine != null) {
            String[] objects = inventoryLine.split(" ");
            int currentWeaponIndex = Integer.parseInt(objects[0]);
            int currentShieldIndex = Integer.parseInt(objects[1]);
            int index = 0;
            for (int i = 3; i < objects.length; i+=3) {
                Entity entity = utilityTool.stringToObject(objects[i], gamePanel,Integer.parseInt(objects[i+1]), false,null);
                gamePanel.player.inventory.add(entity);
                if (index == currentWeaponIndex) {
                    gamePanel.player.currentWeapon = entity;
                }
                if (index == currentShieldIndex) {
                    gamePanel.player.currentShield = entity;
                }
                index ++;
            }
            /*
            String[] equippedItems = data[1].split(" ");
            for (String object : equippedItems) {
                inventory.equip(Object.intToObject(Integer.parseInt(object)));
            }*/
        }
    }

    public void loadMapsState(String directoryPath) {
        try {
            InputStream is = new FileInputStream(directoryPath + "/maps.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int map = 0; map < gamePanel.MAX_MAP; map++) {
                String mapStateLine = br.readLine();
                if (mapStateLine == null) {
                    break;
                }
                int objectCount = 0;
                while (!mapStateLine.equals("//")) {
                    String[] object = mapStateLine.split(" ");
                    Entity loot;
                    if (object[5].equals("null"))
                        loot = null;
                    else
                        loot = utilityTool.stringToObject(object[7], gamePanel,Integer.parseInt(object[8]), false,null);

                    Entity entity = utilityTool.stringToObject(object[1], gamePanel, Integer.parseInt(object[2]) ,utilityTool.intToBool(Integer.parseInt(object[5])), loot);
                    entity.worldX = Integer.parseInt(object[3]);
                    entity.worldY = Integer.parseInt(object[4]);
                    gamePanel.objects[map][objectCount] = entity;
                    objectCount++;
                    mapStateLine = br.readLine();
                }
            }

            br.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadExploredMaps(String directoryPath) {
        try {
            InputStream is = new FileInputStream(directoryPath + "/exploredMaps.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            gamePanel.map = new Map(gamePanel);
            boolean[][][] exploredMaps = new boolean[gamePanel.MAX_MAP][gamePanel.MAX_WORLD_COL][gamePanel.MAX_WORLD_ROW];
            String rowLine = br.readLine();
            int row = 0;
            int map = 0;
            while (rowLine != null) {
                if (rowLine.equals("//")){
                    rowLine = br.readLine(); row = 0; map ++;}

                if (rowLine == null)  break;
                String[] cols = rowLine.split(" ");

                for (int col = 0; col < cols.length; col ++) {
                    exploredMaps[map][col][row] = utilityTool.intToBool(Integer.parseInt(cols[col]));
                }
                row ++;
                rowLine = br.readLine();
            }

            gamePanel.map.setExploredMaps(exploredMaps);
            gamePanel.map.initializeMaps();
            br.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
