package Main;

import entity.Entity;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class UI {

    GamePanel gamePanel;
    Graphics2D g2D;
    Font maruMonica, purisaBold;
    public BufferedImage menuImage, heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin;
    public boolean messageOn = false;
    public ArrayList<String> message = new ArrayList<>();
    public ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    public String[] currentDialogue = new String[20];
    int dialogueCounter = 0;
    public int commandNum = 0;
    public int titleScreenState = 0; // 0: the first screen, 1: the second screen
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    public int subState = 0;
    public int counter = 0;
    public Entity npc;
    public Map<Entity, Set<String>> dialogues;


    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        try {
            InputStream is = getClass().getResourceAsStream("/fonts/maruMonica.ttf");
            assert is != null;
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/fonts/purisaBold.ttf");
            assert is != null;
            purisaBold = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        // MENU IMAGE
        setupMenuImage();

        // CREATE HUD OBJECTS
        Entity heart = new OBJ_Heart(gamePanel);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
        Entity crystal = new OBJ_ManaCrystal(gamePanel);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;
        Entity bronzeCoin = new OBJ_Coin_Bronze(gamePanel);
        coin = bronzeCoin.down1;
    }
    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }
    public void draw(Graphics2D g2D) {
        this.g2D = g2D;
        g2D.setFont(maruMonica);
        g2D.setColor(Color.WHITE);

        // TITLE STATE
        if (gamePanel.gameState == gamePanel.titleState) {
            drawTitleScreen();
        }

        // GAMEMODE STATE
        if (gamePanel.gameState == gamePanel.gameModeState) {
            drawGameModeScreen();
        }

        // PLAY STATE
        if (gamePanel.gameState == gamePanel.playState) {
            drawPlayerLife();
            drawMessage();
        }

        // DIALOGUE STATE
        if (gamePanel.gameState == gamePanel.dialogueState) {
            drawDialogueScreen();
        }

        // PAUSE STATE
        if (gamePanel.gameState == gamePanel.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }

        // CHARACTER STATE
        if (gamePanel.gameState == gamePanel.characterState) {
            drawCharacterScreen();
            drawInventory(gamePanel.player, true);
        }
        // OPTIONS STATE
        if (gamePanel.gameState == gamePanel.optionsState) {
            drawOptionsScreen();
        }
        // GAME OVER STATE
        if (gamePanel.gameState == gamePanel.gameOverState) {
            drawGameOverScreen();
        }
        // TRANSITION STATE
        if (gamePanel.gameState == gamePanel.transitionState) {
            drawTransition();
        }
        // TRADE STATE
        if (gamePanel.gameState == gamePanel.tradeState) {
            drawTradeScreen();
        }


    }
    public void drawPlayerLife() {

        int x = gamePanel.tileSize / 2;
        int y = gamePanel.tileSize / 2;
        int i = 0;

        // DRAW MAX LIFE
        while (i < gamePanel.player.maxLife / 2) {
            g2D.drawImage(heart_blank, x, y, null);
            i++;
            x += gamePanel.tileSize;
        }

        // RESET
        x = gamePanel.tileSize / 2;
        i = 0;

        // DRAW CURRENT LIFE
        while (i < gamePanel.player.life) {
            g2D.drawImage(heart_half, x, y, null);
            i++;
            if (i < gamePanel.player.life) {
                g2D.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gamePanel.tileSize;
        }

        if (Objects.equals(gamePanel.player.playerClass, "Sorcerer")) {
            // DRAW MAX MANA
            x = (gamePanel.tileSize / 2) - 5;
            y = (int) (gamePanel.tileSize * 1.5);
            i = 0;
            while (i < gamePanel.player.resource.getMaxQuantity()) {
                g2D.drawImage(crystal_blank, x, y, null);
                i++;
                x += 35;
            }

            // DRAW CURRENT MANA
            x = (gamePanel.tileSize / 2) - 5;
            y = (int) (gamePanel.tileSize * 1.5);
            i = 0;
            while (i < gamePanel.player.resource.getQuantity()) {
                g2D.drawImage(crystal_full, x, y, null);
                i++;
                x += 35;
            }
        }
    }
    public void drawMessage() {
        int messageX = gamePanel.tileSize;
        int messageY = gamePanel.tileSize * 4;
        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 32F));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {
                g2D.setColor(Color.BLACK);
                g2D.drawString(message.get(i), messageX + 2, messageY + 2);
                g2D.setColor(Color.WHITE);
                g2D.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1; // messageCounter++
                messageCounter.set(i, counter); // set the counter to the array
                messageY += 50;

                if (messageCounter.get(i) > 180) { // 3 seconds
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }


        }
    }
    public void drawPauseScreen() {
        g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gamePanel.screenHeight / 2;

        g2D.drawString(text, x, y);

    }
    public void drawTitleScreen() {

        if (titleScreenState == 0) {
            g2D.setColor(new Color(0, 0, 0)); // To change the background color
            g2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

            // TITLE NAME
            g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 96F));
            String gameTitle = "Zelledaaahh";
            int x =  getXforCenteredText(gameTitle);
            int y = gamePanel.tileSize * 3;

            // SHADOW
            g2D.setColor(Color.GRAY);
            g2D.drawString(gameTitle, x + 5, y + 5);

            // MAIN COLOR
            g2D.setColor(Color.WHITE);
            g2D.drawString(gameTitle, x, y);

            // MAIN CHARACTER IMAGE
            x = gamePanel.screenWidth / 2 - (gamePanel.tileSize*2/2);
            y += gamePanel.tileSize * 2;

            g2D.drawImage(menuImage, x, y, gamePanel.tileSize * 2, gamePanel.tileSize * 2, null);

            // MENU
            g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 48F));
            String text = "NEW GAME";
            x = getXforCenteredText(text);
            y += (int) (gamePanel.tileSize * 3.5);
            g2D.drawString(text, x, y);
            if (commandNum == 0) {
                g2D.drawString(">", x - gamePanel.tileSize, y);
            }

            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize;
            g2D.drawString(text, x, y);
            if (commandNum == 1) {
                g2D.drawString(">", x - gamePanel.tileSize, y);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize;
            g2D.drawString(text, x, y);
            if (commandNum == 2) {
                g2D.drawString(">", x - gamePanel.tileSize, y);
            }
        } else if (titleScreenState == 1) {

            // CLASS SELECTION SCREEN
            g2D.setColor(Color.WHITE);
            g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 42F));

            String text = "Select your class: ";
            int x = getXforCenteredText(text);
            int y = gamePanel.tileSize * 3;
            g2D.drawString(text, x, y);

            text = "Fighter";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize * 3;
            g2D.drawString(text, x, y);
            if (commandNum == 0) {
                g2D.drawString(">", x - gamePanel.tileSize, y);
            }

            text = "Thief";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize;
            g2D.drawString(text, x, y);
            if (commandNum == 1) {
                g2D.drawString(">", x - gamePanel.tileSize, y);
            }

            text = "Sorcerer";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize;
            g2D.drawString(text, x, y);
            if (commandNum == 2) {
                g2D.drawString(">", x - gamePanel.tileSize, y);
            }

            text = "Back";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize * 2;
            g2D.drawString(text, x, y);
            if (commandNum == 3) {
                g2D.drawString(">", x - gamePanel.tileSize, y);
            }

        } else if (titleScreenState == 2) {
            // GAME MODE SELECTION SCREEN
            g2D.setColor(Color.WHITE);
            g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 42F));

            String text = "Select your game mode: ";
            int x = getXforCenteredText(text);
            int y = gamePanel.tileSize * 3;
            g2D.drawString(text, x, y);

            text = "RPG mode";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize * 3;
            g2D.drawString(text, x, y);
            if (commandNum == 0) {
                g2D.drawString(">", x - gamePanel.tileSize, y);
            }

            text = "Turn based mode";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize;
            g2D.drawString(text, x, y);
            if (commandNum == 1) {
                g2D.drawString(">", x - gamePanel.tileSize, y);
            }

            text = "Back";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize * 2;
            g2D.drawString(text, x, y);
            if (commandNum == 2) {
                g2D.drawString(">", x - gamePanel.tileSize, y);
            }
        }
    }

    public void drawGameModeScreen() {






    }
    public void drawDialogueScreen() {
        // WINDOW
        int x = gamePanel.tileSize * 3;
        int y = gamePanel.tileSize / 2;
        int width = gamePanel.screenWidth - (gamePanel.tileSize * 6);
        int height = gamePanel.tileSize * 4;
        drawSubWindow(x, y, width, height);

        g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 32F));
        x += gamePanel.tileSize;
        y += gamePanel.tileSize;
        if (currentDialogue[dialogueCounter] != null) {
            for (String line : currentDialogue[dialogueCounter].split("\n")) {
                g2D.drawString(line, x, y);
                y += 40;
            }
            if (gamePanel.keyHandler.enterPressed) {
                dialogueCounter++;
                gamePanel.keyHandler.enterPressed = false;
            }
        }
        else {
            currentDialogue = new String[20];
            dialogueCounter = 0;
            gamePanel.gameState = gamePanel.playState;
        }
    }



    public void drawCharacterScreen() {

        // CREATE A FRAME
        final int frameX = gamePanel.tileSize * 2;
        final int frameY = gamePanel.tileSize;
        final int frameWidth = gamePanel.tileSize * 5;
        final int frameHeight = gamePanel.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // TEXT
        g2D.setColor(Color.WHITE);
        g2D.setFont(g2D.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gamePanel.tileSize;
        final int lineHeight = 35;

        // NAMES
        g2D.drawString("Level", textX, textY); textY += lineHeight;
        g2D.drawString("Life", textX, textY); textY += lineHeight;
        if (Objects.equals(gamePanel.player.playerClass, "Sorcerer")){
            g2D.drawString("Mana", textX, textY); textY += lineHeight;
        }
        g2D.drawString("Strength", textX, textY); textY += lineHeight;
        g2D.drawString("Dexterity", textX, textY); textY += lineHeight;
        g2D.drawString("Attack", textX, textY); textY += lineHeight;
        g2D.drawString("Defense", textX, textY); textY += lineHeight;
        g2D.drawString("Exp", textX, textY); textY += lineHeight;
        g2D.drawString("Next Level", textX, textY); textY += lineHeight;
        int lineHeight2 = 20;
        if (gamePanel.player.playerClass.equals("Sorcerer")) {
            lineHeight2 = 10;
        }
        g2D.drawString("Coin", textX, textY); textY += lineHeight + lineHeight2;
        g2D.drawString("Weapon", textX, textY); textY += lineHeight + 15;
        g2D.drawString("Shield", textX, textY);

        // VALUES
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gamePanel.tileSize;
        String value;

        value = String.valueOf(gamePanel.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = gamePanel.player.life + "/" + gamePanel.player.maxLife;
        textX = getXforAlignToRightText(value, tailX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight;

        if (Objects.equals(gamePanel.player.playerClass, "Sorcerer")) {
            value = gamePanel.player.resource.toString();
            textX = getXforAlignToRightText(value, tailX);
            g2D.drawString(value, textX, textY);
            textY += lineHeight;
        }

        value = String.valueOf(gamePanel.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.defense);
        textX = getXforAlignToRightText(value, tailX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight;
        int space = 14;
        if (gamePanel.player.playerClass.equals("Sorcerer")) {
            space = 24;
        }
        g2D.drawImage(gamePanel.player.currentWeapon.down1, tailX - gamePanel.tileSize, textY - space, null);
        textY += gamePanel.tileSize;

        g2D.drawImage(gamePanel.player.currentShield.down1, tailX - gamePanel.tileSize, textY - space, null);
    }
    public void drawGameOverScreen() {
        g2D.setColor(new Color(0, 0, 0, 150));
        g2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        int x;
        int y;
        String text;
        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 110f));

        text = "Game Over";
        // Shadow
        g2D.setColor(Color.BLACK);
        x = getXforCenteredText(text);
        y = gamePanel.tileSize * 4;
        g2D.drawString(text, x, y);
        // Main
        g2D.setColor(Color.WHITE);
        g2D.drawString(text, x - 4, y - 4);

        // Retry
        g2D.setFont(g2D.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize * 4;
        g2D.drawString(text, x, y);
        if (commandNum == 0) {
            g2D.drawString(">", x - 40, y);
        }

        // Back to the title screen
        text = "Quit";
        x = getXforCenteredText(text);
        y += 55;
        g2D.drawString(text, x, y);
        if (commandNum == 1) {
            g2D.drawString(">", x - 40, y);
        }

    }
    public void drawOptionsScreen() {
        g2D.setColor(Color.WHITE);
        g2D.setFont(g2D.getFont().deriveFont(32F));

        // SUB WINDOW
        int frameX = gamePanel.tileSize * 6;
        int frameY = gamePanel.tileSize;
        int frameWidth = gamePanel.tileSize * 8;
        int frameHeight = gamePanel.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0 -> options_top(frameX, frameY);
            case 1 -> options_control(frameX, frameY);
            case 2 -> options_endGameConfirmation(frameX, frameY);
        }

        gamePanel.keyHandler.enterPressed = false;

    }
    public void drawTradeScreen() {
        switch (subState) {
            case 0 -> trade_select();
            case 1 -> trade_buy();
            case 2 -> trade_sell();
        }
        gamePanel.keyHandler.enterPressed = false;
    }
    public void trade_select() {


        // DRAW WINDOW
        int x = gamePanel.tileSize * 15;
        int y = gamePanel.tileSize * 4;
        int height = (int) (gamePanel.tileSize * 3.5);
        int width = gamePanel.tileSize * 3;
        drawSubWindow(x, y, width, height);

        g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 32F));


        // DRAW TEXTS
        x += gamePanel.tileSize;
        y += gamePanel.tileSize;
        g2D.drawString("Buy", x, y);
        if (commandNum == 0) {
            g2D.drawString(">", x - 24, y);
            if (gamePanel.keyHandler.enterPressed) {
                subState = 1;
            }
        }
        y += gamePanel.tileSize;

        g2D.drawString("Sell", x, y);
        if (commandNum == 1) {
            g2D.drawString(">", x - 24, y);
            if (gamePanel.keyHandler.enterPressed) {
                subState = 2;
            }
        }
        y += gamePanel.tileSize;

        g2D.drawString("Leave", x, y);
        if (commandNum == 2) {
            g2D.drawString(">", x - 24, y);
            if (gamePanel.keyHandler.enterPressed) {
                commandNum = 0;
                gamePanel.gameState = gamePanel.dialogueState;
                currentDialogue[0] = "Come again, hehe!";
            }
        }
        drawDialogueScreen();

    }
    public void trade_buy() {
        // DRAW PLAYER INVENTORY
        drawInventory(gamePanel.player, false);
        // DRAW NPC INVENTORY
        drawInventory(npc, true);

        // DRAW HINT WINDOW
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize * 9;
        int width = gamePanel.tileSize * 6;
        int height = gamePanel.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2D.drawString("[ESC] Back", x + 34, y + 60);

        // DRAW PLAYER COIN WINDOW
        x = gamePanel.tileSize * 12;
        drawSubWindow(x, y, width, height);
        g2D.drawString("Your coin: " + gamePanel.player.coin, x + 34, y + 60);

        // DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if (itemIndex < npc.inventory.size()) {
            x = (int) (gamePanel.tileSize * 5.5);
            y = (int) (gamePanel.tileSize * 5.5);
            width = (int) (gamePanel.tileSize * 2.5);
            height = gamePanel.tileSize;
            drawSubWindow(x, y, width, height);
            g2D.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = npc.inventory.get(itemIndex).price;
            String text = String.valueOf(price);
            x = getXforAlignToRightText(text, gamePanel.tileSize * 8 - 20);
            g2D.drawString(text, x, y + 34);

            // BUY AN ITEM
            if (gamePanel.keyHandler.enterPressed) {
                if (npc.inventory.get(itemIndex).price > gamePanel.player.coin) {
                    subState = 0;
                    gamePanel.gameState = gamePanel.dialogueState;
                    currentDialogue[0] =  "You need more coin to buy that hehe.";
                    drawDialogueScreen();
                } else if (gamePanel.player.inventory.size() == gamePanel.player.maxInventorySize) {
                    subState = 0;
                    gamePanel.gameState = gamePanel.dialogueState;
                    currentDialogue[0] =  "You cannot carry any more!";
                    drawDialogueScreen();
                } else {
                    gamePanel.player.coin -= npc.inventory.get(itemIndex).price;
                    gamePanel.player.addItem(npc.inventory.get(itemIndex));
                }
            }
        }
    }
    public void trade_sell() {
        // DRAW PLAYER INVENTORY
        drawInventory(gamePanel.player, true);

        int x;
        int y;
        int width;
        int height;

        // DRAW HINT WINDOW
        x = gamePanel.tileSize * 2;
        y = gamePanel.tileSize * 9;
        width = gamePanel.tileSize * 6;
        height = gamePanel.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2D.drawString("[ESC] Back", x + 34, y + 60);

        // DRAW PLAYER COIN WINDOW
        x = gamePanel.tileSize * 12;
        drawSubWindow(x, y, width, height);
        g2D.drawString("Your coin: " + gamePanel.player.coin, x + 34, y + 60);

        // DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if (itemIndex < gamePanel.player.inventory.size()) {
            x = (int) (gamePanel.tileSize * 15.5);
            y = (int) (gamePanel.tileSize * 5.5);
            width = (int) (gamePanel.tileSize * 2.5);
            height = gamePanel.tileSize;
            drawSubWindow(x, y, width, height);
            g2D.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = gamePanel.player.inventory.get(itemIndex).price / 2;
            String text = String.valueOf(price);
            x = getXforAlignToRightText(text, gamePanel.tileSize * 18 - 20);
            g2D.drawString(text, x, y + 34);

            // SELL AN ITEM
            if (gamePanel.keyHandler.enterPressed) {
                if (gamePanel.player.inventory.get(itemIndex) == gamePanel.player.currentWeapon ||
                        gamePanel.player.inventory.get(itemIndex) == gamePanel.player.currentShield) {
                    commandNum = 0;
                    subState = 0;
                    gamePanel.gameState = gamePanel.dialogueState;
                    currentDialogue[0] =  "You cannot sell an equipped item...";
                } else {
                    gamePanel.player.inventory.remove(itemIndex);
                    gamePanel.player.coin += price;
                }
            }
        }
    }
    public void drawTransition() {
        counter++;
        g2D.setColor(new Color(0, 0, 0, counter * 5));
        g2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        if (counter == 50) {
            counter = 0;
            gamePanel.gameState = gamePanel.playState;
            gamePanel.currentMap = gamePanel.eventHandler.tempMap;
            gamePanel.player.worldX = gamePanel.tileSize * gamePanel.eventHandler.tempCol;
            gamePanel.player.worldY = gamePanel.tileSize * gamePanel.eventHandler.tempRow;
            gamePanel.eventHandler.previousEventX = gamePanel.player.worldX;
            gamePanel.eventHandler.previousEventY = gamePanel.player.worldY;
            gamePanel.environmentManager.setEnvironment(gamePanel.currentMap);
        }

    }
    public void options_top(int frameX, int frameY) {

        int textX;
        int textY;

        // TITLE
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gamePanel.tileSize;
        g2D.drawString(text, textX, textY);

        // FULL SCREEN ON/OFF
        textX = frameX + gamePanel.tileSize;
        textY += gamePanel.tileSize * 2;
        g2D.drawString("Full Screen", textX, textY);
        if (commandNum == 0) {
            g2D.drawString(">", textX - 25, textY);
            if (!gamePanel.fullScreenOn && gamePanel.keyHandler.enterPressed) {
                gamePanel.fullScreenOn = true;
            } else if (gamePanel.fullScreenOn && gamePanel.keyHandler.enterPressed) {
                gamePanel.fullScreenOn = false;
            }
            //gamePanel.fullScreenOn = gamePanel.keyHandler.enterPressed && !gamePanel.fullScreenOn;
        }

        // MUSIC
        textY += gamePanel.tileSize;
        g2D.drawString("Music", textX, textY);
        if (commandNum == 1) {
            g2D.drawString(">", textX - 25, textY);
        }

        // SE
        textY += gamePanel.tileSize;
        g2D.drawString("SE", textX, textY);
        if (commandNum == 2) {
            g2D.drawString(">", textX - 25, textY);
        }

        // CONTROL
        textY += gamePanel.tileSize;
        g2D.drawString("Control", textX, textY);
        if (commandNum == 3) {
            g2D.drawString(">", textX - 25, textY);
            if (gamePanel.keyHandler.enterPressed) {
                subState = 1;
                commandNum = 0;
            }
        }

        // END GAME
        textY += gamePanel.tileSize;
        g2D.drawString("End Game", textX, textY);
        if (commandNum == 4) {
            g2D.drawString(">", textX - 25, textY);
            if (gamePanel.keyHandler.enterPressed) {
                subState = 2;
                commandNum = 0;
            }
        }

        // BACK
        textY += gamePanel.tileSize * 2;
        g2D.drawString("Back", textX, textY);
        if (commandNum == 5) {
            g2D.drawString(">", textX - 25, textY);
            if (gamePanel.keyHandler.enterPressed) {
                gamePanel.gameState = gamePanel.playState;
                commandNum = 0;
            }
        }

        // FULL SCREEN CHECK BOX
        textX = (int) (frameX + gamePanel.tileSize * 4.5);
        textY = frameY + gamePanel.tileSize * 2 + 24;
        g2D.setStroke(new BasicStroke(3));
        g2D.drawRect(textX, textY, 24, 24);
        if (gamePanel.fullScreenOn) {
            g2D.fillRect(textX, textY, 24, 24);
        }

        // MUSIC VOLUME
        textY += gamePanel.tileSize;
        g2D.drawRect(textX, textY, 120, 24); // 120 / 5 = 24
        int volumeWidth = 24 * gamePanel.music.volumeScale;
        g2D.fillRect(textX, textY, volumeWidth, 24);

        // SE VOLUME
        textY += gamePanel.tileSize;
        g2D.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gamePanel.se.volumeScale;
        g2D.fillRect(textX, textY, volumeWidth, 24);

        gamePanel.config.saveConfig();
    }
    public void options_control(int frameX, int frameY) {
        int textX;
        int textY;

        // TITLE
        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY + gamePanel.tileSize;
        g2D.drawString(text, textX, textY);

        textX = frameX + gamePanel.tileSize;
        textY += gamePanel.tileSize;
        g2D.drawString("Move", textX, textY); textY += gamePanel.tileSize;
        g2D.drawString("Confirm/Attack", textX, textY); textY += gamePanel.tileSize;
        g2D.drawString("Shoot/Cast", textX, textY); textY += gamePanel.tileSize;
        g2D.drawString("Character Screen", textX, textY); textY += gamePanel.tileSize;
        g2D.drawString("Pause", textX, textY); textY += gamePanel.tileSize;
        g2D.drawString("Options", textX, textY);

        textX = frameX + gamePanel.tileSize * 6;
        textY = frameY + gamePanel.tileSize * 2;
        g2D.drawString("WASD", textX, textY); textY += gamePanel.tileSize;
        g2D.drawString("ENTER", textX, textY); textY += gamePanel.tileSize;
        g2D.drawString("F", textX, textY); textY += gamePanel.tileSize;
        g2D.drawString("C", textX, textY); textY += gamePanel.tileSize;
        g2D.drawString("P", textX, textY); textY += gamePanel.tileSize;
        g2D.drawString("ESC", textX, textY);

        // BACK
        textX = frameX + gamePanel.tileSize;
        textY = frameY + gamePanel.tileSize * 9;
        g2D.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2D.drawString(">", textX - 25, textY);
            if (gamePanel.keyHandler.enterPressed) {
                subState = 0;
                commandNum = 3;
            }
        }
    }
    public void options_endGameConfirmation(int frameX, int frameY) {
        int textX = frameX + gamePanel.tileSize;
        int textY = frameY + gamePanel.tileSize * 3;

        currentDialogue[0] = "Quit the game and \nreturn to the title screen?";
        for (String line : currentDialogue[0].split("\n")) {
            g2D.drawString(line, textX, textY);
            textY += 40;
        }

        // YES
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += gamePanel.tileSize * 3;
        g2D.drawString(text, textX, textY);
        if (commandNum == 0) {
            g2D.drawString(">", textX - 25, textY);
            if (gamePanel.keyHandler.enterPressed) {
                subState = 0;
                gamePanel.gameState = gamePanel.titleState;
                gamePanel.stopMusic();
            }
        }

        // NO
        text = "No";
        textX = getXforCenteredText(text);
        textY += gamePanel.tileSize;
        g2D.drawString(text, textX, textY);
        if (commandNum == 1) {
            g2D.drawString(">", textX - 25, textY);
            if (gamePanel.keyHandler.enterPressed) {
                subState = 0;
                commandNum = 4;
            }
        }
    }
    public void drawInventory(Entity entity, boolean cursor) {

        int frameX;
        int frameY;
        int frameWidth;
        int frameHeight;
        int slotCol;
        int slotRow;

        if (entity == gamePanel.player) {
            frameX = gamePanel.tileSize * 12;
            frameY = gamePanel.tileSize;
            frameWidth = gamePanel.tileSize * 6;
            frameHeight = gamePanel.tileSize * 5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        }
        else {
            frameX = gamePanel.tileSize * 2;
            frameY = gamePanel.tileSize;
            frameWidth = gamePanel.tileSize * 6;
            frameHeight = gamePanel.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        // FRAME

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // SLOT
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gamePanel.tileSize + 3;

        // DRAW PLAYER'S ITEMS
        for (int i = 0; i < entity.inventory.size(); i++) {

            // EQUIP CURSOR
            if (entity.inventory.get(i) == entity.currentWeapon ||
                    entity.inventory.get(i) == entity.currentShield) {
                g2D.setColor(new Color(240, 190, 90));
                g2D.fillRoundRect(slotX, slotY, gamePanel.tileSize, gamePanel.tileSize, 10, 10);
            }


            g2D.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
            //DISPLAY THE AMOUNT

            if(entity.inventory.get(i).amount>1){
                g2D.setFont(g2D.getFont().deriveFont(32f));
                int amountX;
                int amountY;

                String s=""+ entity.inventory.get(i).amount;
                amountX=getXforAlignToRightText(s,slotX+44);
                amountY=slotY+gamePanel.tileSize;

                //SHADOW
                g2D.setColor(Color.white);
                g2D.drawString(s,amountX-3,amountY-3);
            }


            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        // CURSOR
        if (cursor) {
            int cursorX = slotXStart + (slotSize * slotCol);
            int cursorY = slotYStart + (slotSize * slotRow);
            int cursorWidth = gamePanel.tileSize;
            int cursorHeight = gamePanel.tileSize;

            // DRAW CURSOR
            g2D.setColor(Color.WHITE);
            g2D.setStroke(new BasicStroke(3));
            g2D.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            // DESCRIPTION FRAME
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gamePanel.tileSize * 3;

            // DRAW DESCRIPTION TEXT
            int textX = dFrameX + 20;
            int textY = dFrameY + gamePanel.tileSize;
            g2D.setFont(g2D.getFont().deriveFont(28F));

            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
            if (itemIndex < entity.inventory.size()) {
                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
                for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
                    g2D.drawString(line, textX, textY);
                    textY += 32;
                }
            }
        }

    }



    public int getItemIndexOnSlot(int slotCol, int slotRow) {
        return slotCol + slotRow * 5;
    }
    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0, 0, 0, 210); // last argument is for the opacity
        g2D.setColor(c);
        g2D.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2D.setColor(c);
        g2D.setStroke(new BasicStroke(5));
        g2D.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

    }
    public int getXforCenteredText(String text) {
        int length = (int) g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
        int x = gamePanel.screenWidth / 2 - (length / 2);
        return x;
    }
    public int getXforAlignToRightText(String text, int tailX) {
        int length = (int) g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
        int x = tailX - length;
        return x;
    }
    private void setupMenuImage() {
        UtilityTool uTool = new UtilityTool();
        try {
            menuImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_down_1.png")));
            menuImage = uTool.scaleImage(menuImage, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}
