package Main;

import entity.Entity;
import entity.Player;
import object.OBJ_Chest;
import object.OBJ_Door;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Saver {

    GamePanel gamePanel;

    public Saver(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }


    public void save(String directoryPath) {
        savePlayer(directoryPath);
        saveMapsState(directoryPath);
        saveExploredMaps(directoryPath);
    }

    private void savePlayer(String directoryPath) {
        File fnew=new File(directoryPath + "/player.txt");
        FileWriter f2;
        Player player = gamePanel.player;
        try {
            f2 = new FileWriter(fnew,false);
            StringBuilder str = new StringBuilder();
            str.append(player.worldX).append(" ").append(player.worldY).append(" ").append(player.direction).append("\n");
            //location
            f2.write(str.toString());
            str.delete(0,str.length());
            str.append(player.playerClass).append(" ")
                    .append(player.name).append(" ")
                    .append(player.maxLife).append(" ")
                    .append(player.life).append(" ")
                    .append(player.resource.getClass()).append(" ")
                    .append(player.resource.getQuantity()).append(" ")
                    .append(player.resource.getMaxQuantity()).append(" ")
                    .append(player.speed).append(" ")
                    .append(player.baseSpeed).append(" ")
                    .append(player.level).append(" ")
                    .append(player.strength).append(" ")
                    .append(player.dexterity).append(" ")
                    .append(player.exp).append(" ")
                    .append(player.nextLevelExp).append(" ").
                    append(player.coin).append(" ").
                    append(player.capacity.getClass()).append("\n");
            //stats
            f2.write(str.toString());
            str.delete(0,str.length());

            //inventory
            str.append(player.inventory.indexOf(player.currentWeapon)).append(" ")
                    .append(player.inventory.indexOf(player.currentShield)).append(" ");
            for (Entity object : player.inventory) {
                str.append(object.getClass()).append(" ").append(object.amount).append(" ");
            }
            str.append("\n");
            f2.write(str.toString());
            str.delete(0,str.length());

            str.append(gamePanel.time.getHour()).append("\n");
            f2.write(str.toString());
            str.delete(0,str.length());

            f2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }



    private void saveMapsState(String directoryPath) {
        File fnew=new File(directoryPath + "/maps.txt");
        FileWriter f2;
        try {
            f2 = new FileWriter(fnew,false);
            StringBuilder str = new StringBuilder();

            for (int map = 0; map < gamePanel.MAX_MAP; map ++) {

                for (int object = 0; object < gamePanel.objects[map].length; object ++) {
                    Entity entity = gamePanel.objects[map][object];
                    if (entity != null) {
                        str.append(entity.getClass()).append(" ").append(entity.amount).append(" ").append(entity.worldX).append(" ").append(entity.worldY).append(" ");
                        if (entity instanceof OBJ_Chest chest) {
                            if (chest.opened)
                                str.append(0).append(" ");
                            else
                                str.append(1).append(" ");
                            str.append(chest.loot.getClass()).append(" ").append(chest.loot.amount).append("\n");
                        }
                        else if (entity instanceof OBJ_Door door) {
                            if (door.opened)
                                str.append(0).append(" ");
                            else
                                str.append(1).append(" ");
                            str.append("null\n");
                        }
                        else
                            str.append("1 null\n");
                    }
                }
                str.append("//").append("\n");


                f2.write(str.toString());
                str.delete(0,str.length());
            }




            f2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void saveExploredMaps(String directoryPath) {
        File fnew=new File(directoryPath + "/exploredMaps.txt");
        FileWriter f2;
        try {
            f2 = new FileWriter(fnew,false);
            StringBuilder str = new StringBuilder();

            for (int map = 0; map < gamePanel.MAX_MAP; map ++) {

                for (int row = 0; row < gamePanel.MAX_WORLD_ROW; row ++) {
                    for (int col = 0; col < gamePanel.MAX_WORLD_COL; col ++)
                        str.append(gamePanel.map.getExploredMaps()[map][col][row] ? 0 : 1).append(" ");

                    str.append("\n");
                }
                str.append("//").append("\n");
            }
            f2.write(str.toString());


            f2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
