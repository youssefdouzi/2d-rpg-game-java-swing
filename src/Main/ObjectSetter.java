package Main;

import electroDungeon.objects.*;
import entity.Entity;
import entity.Teleporter;
import monster.*;
import object.Grass;
import entity.LootTable;
import object.OBJ_Coin_Bronze;
import object.OBJ_SkeletonDoor;
import object.Pot;
import startingVillage.npcs.NPC_Merchant;

import startingVillage.npcs.NPC_OldMan;
import startingVillage.objects.BasicHouse;
import startingVillage.objects.MerchantHouse;


import java.util.*;

public class ObjectSetter {

    GamePanel gamePanel;
    Map<String, LootTable> lootTables;

    public ObjectSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setLootTables();
    }

    private void setLootTables() {
        lootTables = new HashMap<>();
        List<Entity> objects;
        List<Integer> dropRates;

        objects = List.of(new OBJ_Coin_Bronze(gamePanel));
        dropRates = List.of(50);
        lootTables.put("Pot", new LootTable(objects, dropRates));

        lootTables.put("Grass", new LootTable(objects, dropRates));
    }

    public LootTable getLootTable(String className) {
        return lootTables.get(className);
    }

    private int addObjects(List<ElectricalComponent> objects, int i, int map) {

        for (Entity entity : objects) {
            gamePanel.objects[map][i] = entity;
            i++;
        }
        return i;

    }
    private void linkElectricComponents(List<ElectricalComponent> components) {
        for (int i = 0; i < components.size()-1; i++) {
            components.get(i).setHead(components.get(i+1));
        }
    }


    public void setMap(int map) {
        switch (map) {
            case 0 -> loadStartingVillage();
            case 1 -> loadMerchantHouse();
            case 2 -> loadGreatForest();
            case 3 -> loadBasicHouse();
            case 4 -> loadDungeonFloor1();
            case 5 -> loadDungeonFloor2();
            case 6 -> loadDungeonFloor3();
            case 7 -> loadElectroDungeon();
        }
    }
    private void loadDungeonFloor1() {
        int i = 0;
        gamePanel.objects[4][i] = new Teleporter(gamePanel,44,17, new int[]{5,14,44},0);
        i++;

        /*
        gamePanel.monsters[4][i] = new MON_Bat(gamePanel);
        gamePanel.monsters[4][i].worldX = gamePanel.tileSize * 23;
        gamePanel.monsters[4][i].worldY = gamePanel.tileSize * 30;
        i++;

        gamePanel.monsters[4][i] = new MON_Bat(gamePanel);
        gamePanel.monsters[4][i].worldX = gamePanel.tileSize * 22;
        gamePanel.monsters[4][i].worldY = gamePanel.tileSize * 26;
        i++;

        gamePanel.monsters[4][i] = new MON_Bat(gamePanel);
        gamePanel.monsters[4][i].worldX = gamePanel.tileSize * 11;
        gamePanel.monsters[4][i].worldY = gamePanel.tileSize * 11;
        i++;

        gamePanel.monsters[4][i] = new MON_Bat(gamePanel);
        gamePanel.monsters[4][i].worldX = gamePanel.tileSize * 21;
        gamePanel.monsters[4][i].worldY = gamePanel.tileSize * 5;
        i++;

        gamePanel.monsters[4][i] = new MON_Bat(gamePanel);
        gamePanel.monsters[4][i].worldX = gamePanel.tileSize * 25;
        gamePanel.monsters[4][i].worldY = gamePanel.tileSize * 12;
        i++;

        gamePanel.monsters[4][i] = new MON_Bat(gamePanel);
        gamePanel.monsters[4][i].worldX = gamePanel.tileSize * 36;
        gamePanel.monsters[4][i].worldY = gamePanel.tileSize * 9;
        i++;

        gamePanel.monsters[4][i] = new MON_Bat(gamePanel);
        gamePanel.monsters[4][i].worldX = gamePanel.tileSize * 42;
        gamePanel.monsters[4][i].worldY = gamePanel.tileSize * 4;
        i++;

        gamePanel.monsters[4][i] = new MON_Bat(gamePanel);
        gamePanel.monsters[4][i].worldX = gamePanel.tileSize * 40;
        gamePanel.monsters[4][i].worldY = gamePanel.tileSize * 16;
        i++;

        gamePanel.monsters[4][i] = new MON_Orc(gamePanel);
        gamePanel.monsters[4][i].worldX = gamePanel.tileSize * 35;
        gamePanel.monsters[4][i].worldY = gamePanel.tileSize * 10;
        i++;

        gamePanel.monsters[4][i] = new MON_Orc(gamePanel);
        gamePanel.monsters[4][i].worldX = gamePanel.tileSize * 10;
        gamePanel.monsters[4][i].worldY = gamePanel.tileSize * 10;
        i++;

        gamePanel.monsters[4][i] = new MON_Demon(gamePanel);
        gamePanel.monsters[4][i].worldX = gamePanel.tileSize * 44;
        gamePanel.monsters[4][i].worldY = gamePanel.tileSize * 15;


         */


    }
    private void loadDungeonFloor2() {
        int i = 0;
        gamePanel.objects[5][i] = new Teleporter(gamePanel,14,43, new int[]{4,44,16},0);
        i++;
        gamePanel.objects[5][i] = new Teleporter(gamePanel,10,1, new int[]{7,39,17},0); // end of dungeon
        i++;
        gamePanel.objects[5][i] = new Teleporter(gamePanel,48,39, new int[]{6,8,30},2); // boss room
        i++;

        gamePanel.objects[5][i] = new OBJ_SkeletonDoor(gamePanel,false);
        gamePanel.objects[5][i].worldX = gamePanel.tileSize * 10;
        gamePanel.objects[5][i].worldY = gamePanel.tileSize * 10;
        i++;


        /*
        gamePanel.monsters[5][i] = new MON_Orc(gamePanel);
        gamePanel.monsters[5][i].worldX = gamePanel.tileSize * 12;
        gamePanel.monsters[5][i].worldY = gamePanel.tileSize * 46;
        i++;

        gamePanel.monsters[5][i] = new MON_Demon(gamePanel);
        gamePanel.monsters[5][i].worldX = gamePanel.tileSize * 36;
        gamePanel.monsters[5][i].worldY = gamePanel.tileSize * 44;
        i++;

        gamePanel.monsters[5][i] = new MON_Bat(gamePanel);
        gamePanel.monsters[5][i].worldX = gamePanel.tileSize * 9;
        gamePanel.monsters[5][i].worldY = gamePanel.tileSize * 14;
        i++;

        gamePanel.monsters[5][i] = new MON_Bat(gamePanel);
        gamePanel.monsters[5][i].worldX = gamePanel.tileSize * 12;
        gamePanel.monsters[5][i].worldY = gamePanel.tileSize * 24;
        i++;

        gamePanel.monsters[5][i] = new MON_Bat(gamePanel);
        gamePanel.monsters[5][i].worldX = gamePanel.tileSize * 26;
        gamePanel.monsters[5][i].worldY = gamePanel.tileSize * 27;
        i++;

        gamePanel.monsters[5][i] = new MON_Bat(gamePanel);
        gamePanel.monsters[5][i].worldX = gamePanel.tileSize * 24;
        gamePanel.monsters[5][i].worldY = gamePanel.tileSize * 42;
        i++;

        gamePanel.monsters[5][i] = new MON_Bat(gamePanel);
        gamePanel.monsters[5][i].worldX = gamePanel.tileSize * 30;
        gamePanel.monsters[5][i].worldY = gamePanel.tileSize * 13;
        i++;

        gamePanel.monsters[5][i] = new MON_Bat(gamePanel);
        gamePanel.monsters[5][i].worldX = gamePanel.tileSize * 33;
        gamePanel.monsters[5][i].worldY = gamePanel.tileSize * 27;
        i++;

        gamePanel.monsters[5][i] = new MON_Bat(gamePanel);
        gamePanel.monsters[5][i].worldX = gamePanel.tileSize * 37;
        gamePanel.monsters[5][i].worldY = gamePanel.tileSize * 18;
        i++;

        gamePanel.monsters[5][i] = new MON_Orc(gamePanel);
        gamePanel.monsters[5][i].worldX = gamePanel.tileSize * 18;
        gamePanel.monsters[5][i].worldY = gamePanel.tileSize * 35;
        i++;

        gamePanel.monsters[5][i] = new MON_Orc(gamePanel);
        gamePanel.monsters[5][i].worldX = gamePanel.tileSize * 10;
        gamePanel.monsters[5][i].worldY = gamePanel.tileSize * 5;
        i++;

         */
    }
    private void loadDungeonFloor3() {
        int i = 0;
        gamePanel.objects[6][i] = new Teleporter(gamePanel,7,30, new int[]{5,47,39},3);
        i++;

        gamePanel.monsters[6][i] = new MON_SkeletonLord(gamePanel);
        gamePanel.monsters[6][i].worldX = gamePanel.tileSize * 20;
        gamePanel.monsters[6][i].worldY = gamePanel.tileSize * 23;


    }
    private void loadGreatForest() {
        int i = 0;
        gamePanel.objects[2][i] = new Teleporter(gamePanel,32,19, new int[]{0,37,36},0);
        i++;


        i = 0;
        gamePanel.monsters[2][i] = new MON_GreenSlime(gamePanel);
        gamePanel.monsters[2][i].worldX = gamePanel.tileSize * 23;
        gamePanel.monsters[2][i].worldY = gamePanel.tileSize * 30;
        i++;
        gamePanel.monsters[2][i] = new MON_GreenSlime(gamePanel);
        gamePanel.monsters[2][i].worldX = gamePanel.tileSize * 23;
        gamePanel.monsters[2][i].worldY = gamePanel.tileSize * 27;
        i++;
        gamePanel.monsters[2][i] = new MON_GreenSlime(gamePanel);
        gamePanel.monsters[2][i].worldX = gamePanel.tileSize * 24;
        gamePanel.monsters[2][i].worldY = gamePanel.tileSize * 30;
        i++;
        gamePanel.monsters[2][i] = new MON_GreenSlime(gamePanel);
        gamePanel.monsters[2][i].worldX = gamePanel.tileSize * 35;
        gamePanel.monsters[2][i].worldY = gamePanel.tileSize * 30;

    }
    private void loadMerchantHouse() {
        int i = 0;

        gamePanel.objects[1][i] = new Teleporter(gamePanel,25,23, new int[]{0,42,27},1);
        i++;
        gamePanel.objects[1][i] = new Pot(gamePanel,18,16,i);
        i++;
        gamePanel.objects[1][i] = new Pot(gamePanel,29,16,i);
        i++;
        gamePanel.objects[1][i] = new Pot(gamePanel,29,22,i);
        i++;
        gamePanel.objects[1][i] = new Pot(gamePanel,18,22,i);
        i = 0;

        gamePanel.npc[1][i] = new NPC_Merchant(gamePanel);
        gamePanel.npc[1][i].worldX = gamePanel.tileSize * 23;
        gamePanel.npc[1][i].worldY = gamePanel.tileSize * 16;
    }

    private int createRandomGrassField(int map, int index, int col, int row, int width, int height) {
        int tempIndex = 0;
        Random rand = new Random();
        int tilesNumber = width * height;
        int tempCol = col;
        int tempRow = row;
        for (int i = 0; i < tilesNumber; i++) {
            if (rand.nextBoolean()) {
                gamePanel.objects[map][index] = new Grass(gamePanel, tempCol, tempRow, index);
                index++;
                tempIndex ++;
            }
            tempCol ++;
            if (tempCol == col + width) {
                tempCol = col;
                tempRow++;
            }
        }
        return tempIndex;
    }

    private void loadBasicHouse() {
        int i = 0;

        gamePanel.objects[3][i] = new Teleporter(gamePanel,25,23, new int[]{0,48,27},1);


        //3 22
        i++;
        gamePanel.objects[3][i] = new Pot(gamePanel,18,16,i);
        i++;
        gamePanel.objects[3][i] = new Pot(gamePanel,29,16,i);
        i++;
        gamePanel.objects[3][i] = new Pot(gamePanel,29,22,i);
        i++;
        gamePanel.objects[3][i] = new Pot(gamePanel,18,22,i);
        i = 0;
    }
    private void loadStartingVillage() {
        int i = 0;
        MerchantHouse house1 = new MerchantHouse(gamePanel,41,23,true,new int[]{1,25,22});
        gamePanel.objects[0][i] = house1;
        i++;
        BasicHouse house2 = new BasicHouse(gamePanel,47,23,true,new int[]{3,25,22});
        gamePanel.objects[0][i] = house2;
        i++;
        BasicHouse house3 = new BasicHouse(gamePanel,50,11,false,new int[]{1,12,12});
        gamePanel.objects[0][i] = house3;
        i++;
        BasicHouse house4 = new BasicHouse(gamePanel,42,11,false,new int[]{1,12,12});
        gamePanel.objects[0][i] = house4;
        i++;
        gamePanel.objects[0][i] = new ElectricWire(gamePanel, 21,25,Shape.VERTICAL_DOWN);
        i++;
        gamePanel.objects[0][i] = new Lumix(gamePanel,25*48,25*48,4);
        i++;
        //i += createRandomGrassField(0, i,21,25,7,4);
        /*
        Lumix lumix = new Lumix(gamePanel,45*48,18*48,2);
        ElectricWire e1 = new ElectricWire(gamePanel, 39,18, Shape.VERTICAL_UP);
        ElectricWire e2 = new ElectricWire(gamePanel, 39,17, Shape.VERTICAL_UP);
        ElectricWire e3 = new ElectricWire(gamePanel, 39,16, Shape.VERTICAL_UP);
        ElectricGate gate = new ElectricGate(gamePanel, 29,16);
        ElectricGate gate2 = new ElectricGate(gamePanel, 29,17);
        ElectricGate gate3 = new ElectricGate(gamePanel, 29,18);
        ElectricGate gate4 = new ElectricGate(gamePanel, 29,19);
        ElectroModule module = new ElectroModule(gamePanel,37,14);
        e1.setHead(e2);
        e2.setHead(e3);
        e3.setHead(module);
        gate4.setHead(gate3);
        gate3.setHead(gate2);
        gate2.setHead(gate);
        module.setHead(gate4);
        i++;
        gamePanel.objects[0][i] = lumix;
        i++;
        gamePanel.objects[0][i] = e1;
        i++;
        gamePanel.objects[0][i] = e2;
        i++;
        gamePanel.objects[0][i] = e3;
        i++;
        gamePanel.objects[0][i] = gate;
        i++;
        gamePanel.objects[0][i] = gate2;
        i++;
        gamePanel.objects[0][i] = gate3;
        i++;
        gamePanel.objects[0][i] = gate4;
        i++;
        gamePanel.objects[0][i] = module;
        i++;
        */
        gamePanel.objects[0][i] = new Teleporter(gamePanel,37,37, new int[]{2,32,20},1);
        i++;

        gamePanel.objects[0][i] = new Teleporter(gamePanel,14,16, new int[]{4,4,22},3);
        i = 0;
        gamePanel.npc[0][i] = new NPC_OldMan(gamePanel);
        gamePanel.npc[0][i].worldX = gamePanel.tileSize * 25;
        gamePanel.npc[0][i].worldY = gamePanel.tileSize * 17;
        gamePanel.npc[0][i].direction = "RIGHT";

        i = 0;
        /*gamePanel.monsters[0][i] = new MON_Electro(gamePanel);
        gamePanel.monsters[0][i].worldX = gamePanel.tileSize * 27;
        gamePanel.monsters[0][i].worldY = gamePanel.tileSize * 22;
    */}

    private void loadElectroDungeon() {
        int i = 0;
        gamePanel.objects[7][i] = new Lumix(gamePanel, 33*gamePanel.tileSize, 53*gamePanel.tileSize,4);
        i++;
        gamePanel.objects[7][i] = new Lumix(gamePanel, 48*gamePanel.tileSize, 31*gamePanel.tileSize,4);

        i++;
        i = load1stModule(i);
        i++;
        i = load2ndModule(i);
        i++;
        i = load3rdModule(i);
        i++;
        gamePanel.objects[7][i] = new Teleporter(gamePanel,74,88,new int[]{8,51,84},1);
    }

    private int load3rdModule(int i) {

        ElectricalComponent module1 = new ElectroModule(gamePanel, 50, 64);

        ElectricalComponent wire1 = new ElectricWire(gamePanel, 48, 63, Shape.CORNER_UP_RIGHT);
        ElectricalComponent wire2 = new ElectricWire(gamePanel, 49, 63, Shape.HORIZONTAL_RIGHT);
        ElectricalComponent wire3 = new ElectricWire(gamePanel, 50, 63, Shape.CORNER_RIGHT_DOWN);
        wire1.setHead(wire2);
        wire2.setHead(wire3);
        wire3.setHead(module1);

        ElectricWire wire4 = new ElectricWire(gamePanel, 48, 65, Shape.CORNER_DOWN_RIGHT);
        ElectricWire wire5 = new ElectricWire(gamePanel, 49, 65, Shape.HORIZONTAL_RIGHT);
        ElectricWire wire6 = new ElectricWire(gamePanel, 50, 65, Shape.CORNER_RIGHT_UP);
        wire4.setHead(wire5);
        wire5.setHead(wire6);
        wire6.setHead(module1);

        ElectricalComponent wire7 = new ElectricWire(gamePanel, 49, 64, Shape.HORIZONTAL_RIGHT);
        wire7.setHead(module1);



        List<ElectricalComponent> components = new ArrayList<>(List.of(
                module1,
                new ElectricWire(gamePanel, 51, 64, Shape.HORIZONTAL_RIGHT),
                new ElectricWire(gamePanel, 52, 64, Shape.CORNER_RIGHT_UP),
                new ElectricWire(gamePanel, 52, 63, Shape.CORNER_UP_RIGHT),
                new ElectricWire(gamePanel, 53, 63, Shape.CORNER_RIGHT_UP),
                new ElectricWire(gamePanel, 53, 62, Shape.VERTICAL_UP),
                new ElectricWire(gamePanel, 53, 61, Shape.CORNER_UP_RIGHT),
                new ElectricWire(gamePanel, 54, 61, Shape.CORNER_RIGHT_UP),
                new ElectricWire(gamePanel, 54, 60, Shape.CORNER_UP_RIGHT),
                new ElectricWire(gamePanel, 55, 60, Shape.HORIZONTAL_RIGHT),
                new ElectricWire(gamePanel, 56, 60, Shape.CORNER_RIGHT_DOWN),
                new ElectricGate(gamePanel, 56,63),
                new ElectricGate(gamePanel, 56,62),
                new ElectricGate(gamePanel, 56,61)
        ));
        linkElectricComponents(components);


        components.addAll(List.of(wire1,wire2,wire3,wire4,wire5,wire6,wire7));

        i = addObjects(components,i,7);


        return i;
    }

    private int load2ndModule(int i) {


        List<ElectricalComponent> components = List.of(
                new ElectricWire(gamePanel, 57, 69, Shape.VERTICAL_UP),
                new ElectroModule(gamePanel, 57, 68),
                new ElectricField(gamePanel,44,59),
                new ElectricField(gamePanel,45,59),
                new ElectricField(gamePanel,46,59),
                new ElectricField(gamePanel,47,59),
                new ElectricField(gamePanel,48,59)
                );
        linkElectricComponents(components);
        i = addObjects(components,i,7);
        return i;
    }

    private int load1stModule(int index) {
        ElectricalComponent module1 = new ElectroModule(gamePanel, 31, 71);

        ElectricalComponent wire1 = new ElectricWire(gamePanel, 32, 71, Shape.CORNER_UP_LEFT);
        wire1.setHead(module1);
        ElectricalComponent wire2 = new ElectricWire(gamePanel, 31, 72, Shape.CORNER_LEFT_UP);
        wire2.setHead(module1);

        List<ElectricalComponent> components = new ArrayList<>(List.of(
                module1,
                new ElectricWire(gamePanel, 30, 71, Shape.HORIZONTAL_LEFT),
                new ElectricWire(gamePanel, 29, 71, Shape.CORNER_LEFT_DOWN),
                new ElectricWire(gamePanel, 29, 72, Shape.VERTICAL_DOWN),
                new ElectricWire(gamePanel, 29, 73, Shape.VERTICAL_DOWN),
                new ElectricWire(gamePanel, 29, 74, Shape.CORNER_DOWN_LEFT),
                new ElectricWire(gamePanel, 28, 74, Shape.HORIZONTAL_LEFT),
                new ElectricWire(gamePanel, 27, 74, Shape.CORNER_LEFT_DOWN),
                new ElectricWire(gamePanel, 27, 75, Shape.CORNER_DOWN_LEFT),
                new ElectricWire(gamePanel, 26, 75, Shape.CORNER_LEFT_DOWN),
                new ElectricWire(gamePanel, 26, 76, Shape.VERTICAL_DOWN),

                new ElectricWire(gamePanel, 26, 77, Shape.VERTICAL_DOWN),
                new ElectricWire(gamePanel, 26, 78, Shape.CORNER_DOWN_RIGHT),
                new ElectricWire(gamePanel, 27, 78, Shape.HORIZONTAL_RIGHT),
                new ElectricWire(gamePanel, 28, 78, Shape.HORIZONTAL_RIGHT),
                new ElectricWire(gamePanel, 29, 78, Shape.CORNER_RIGHT_DOWN),
                new ElectricWire(gamePanel, 29, 79, Shape.CORNER_DOWN_RIGHT),
                new ElectricWire(gamePanel, 30, 79, Shape.HORIZONTAL_RIGHT),
                new ElectricWire(gamePanel, 31, 79, Shape.CORNER_RIGHT_DOWN),
                new ElectricWire(gamePanel, 31, 80, Shape.CORNER_DOWN_RIGHT),
                new ElectricWire(gamePanel, 32, 80, Shape.HORIZONTAL_RIGHT),

                new ElectricWire(gamePanel, 33, 80, Shape.CORNER_RIGHT_DOWN),
                new ElectricWire(gamePanel, 33, 81, Shape.CORNER_DOWN_RIGHT),
                new ElectricWire(gamePanel, 34, 81, Shape.HORIZONTAL_RIGHT),
                new ElectricWire(gamePanel, 35, 81, Shape.HORIZONTAL_RIGHT),
                new ElectricWire(gamePanel, 36, 81, Shape.HORIZONTAL_RIGHT),
                new ElectricWire(gamePanel, 37, 81, Shape.CORNER_RIGHT_DOWN),
                new ElectricWire(gamePanel, 37, 82, Shape.VERTICAL_DOWN),
                new ElectricWire(gamePanel, 37, 83, Shape.VERTICAL_DOWN),
                new ElectricWire(gamePanel, 37, 84, Shape.VERTICAL_DOWN),
                new ElectricWire(gamePanel, 37, 85, Shape.VERTICAL_DOWN),

                new ElectricWire(gamePanel, 37, 86, Shape.CORNER_DOWN_RIGHT),
                new ElectricWire(gamePanel, 38, 86, Shape.HORIZONTAL_RIGHT),
                new ElectricWire(gamePanel, 39, 86, Shape.CORNER_RIGHT_DOWN),
                new ElectricWire(gamePanel, 39, 87, Shape.CORNER_DOWN_RIGHT),
                new ElectricWire(gamePanel, 40, 87, Shape.HORIZONTAL_RIGHT),
                new ElectricWire(gamePanel, 41, 87, Shape.HORIZONTAL_RIGHT),
                new ElectricWire(gamePanel, 42, 87, Shape.CORNER_RIGHT_UP),
                new ElectricWire(gamePanel, 42, 86, Shape.CORNER_UP_RIGHT),
                new ElectricWire(gamePanel, 43, 86, Shape.CORNER_RIGHT_UP),
                new ElectricWire(gamePanel, 43, 85, Shape.CORNER_UP_RIGHT),

                new ElectricWire(gamePanel, 44, 85, Shape.CORNER_RIGHT_UP),
                new ElectricWire(gamePanel, 44, 84, Shape.CORNER_UP_RIGHT),
                new ElectricWire(gamePanel, 45, 84, Shape.HORIZONTAL_RIGHT),
                new ElectricWire(gamePanel, 46, 84, Shape.HORIZONTAL_RIGHT),
                new ElectricWire(gamePanel, 47, 84, Shape.HORIZONTAL_RIGHT),
                new ElectricWire(gamePanel, 48, 84, Shape.CORNER_RIGHT_UP),
                new ElectricWire(gamePanel, 48, 83, Shape.CORNER_UP_RIGHT),
                new ElectricWire(gamePanel, 49, 83, Shape.HORIZONTAL_RIGHT),
                new ElectricWire(gamePanel, 50, 83, Shape.CORNER_RIGHT_UP),

                new ElectricWire(gamePanel, 50, 82, Shape.CORNER_UP_RIGHT),
                new ElectricWire(gamePanel, 51, 82, Shape.CORNER_RIGHT_UP),
                new ElectricWire(gamePanel, 51, 81, Shape.CORNER_UP_RIGHT),
                new ElectricWire(gamePanel, 52, 81, Shape.HORIZONTAL_RIGHT),
                new ElectricWire(gamePanel, 53, 81, Shape.CORNER_RIGHT_UP),

                new ElectricGate(gamePanel, 53, 73),
                new ElectricGate(gamePanel, 53, 74),
                new ElectricGate(gamePanel, 53, 75),
                new ElectricGate(gamePanel, 53, 76),
                new ElectricGate(gamePanel, 53, 77),
                new ElectricGate(gamePanel, 53, 78),
                new ElectricGate(gamePanel, 53, 79),
                new ElectricGate(gamePanel, 53, 80)


        ));
        linkElectricComponents(components);
        components.add(wire1);
        components.add(wire2);
        index = addObjects(components,index,7);



        return index;

    }

    public void setNPC() {
        int mapNum = 0;
        int i = 0;
        // MAP 0

        i++;

        // MAP 1
        mapNum = 1;

    }
    public void setMonster() {
        /*
        int i = 0;
        int mapNum = 0;


        i++;
        gamePanel.monsters[mapNum][i] = new MON_GreenSlime(gamePanel);
        gamePanel.monsters[mapNum][i].worldX = gamePanel.tileSize * 38;
        gamePanel.monsters[mapNum][i].worldY = gamePanel.tileSize * 42;
        */

    }
    public void setInteractiveTile() {
        /*
        int i = 0;
        int mapNum = 0;

        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 27, 12); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 28, 12); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 28, 12); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 29, 12); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 30, 12); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 31, 12); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 32, 12); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 33, 12); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 18, 40); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 17, 40); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 16, 40); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 15, 40); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 14, 40); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 13, 40); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 13, 41); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 12, 41); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 11, 41); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 10, 41); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 10, 40);*/
    }
}
