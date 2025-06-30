package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gamePanel;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, capacityKeyPressed, spacePressed;
    public String lastKeyPressed = "DOWN";

    // DEBUG
    boolean showDebugText = false;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        // TITLE STATE
        if (gamePanel.gameState == gamePanel.titleState) {
            titleState(code);
        }
        // PLAY STATE
        else if (gamePanel.gameState == gamePanel.playState) {
            playState(code);
        }
        // PAUSE STATE
        else if (gamePanel.gameState == gamePanel.pauseState) {
            pauseState(code);
        }
        // DIALOGUE STATE
        else if (gamePanel.gameState == gamePanel.dialogueState) {
           dialogueState(code);
        }
        // CHARACTER STATE
        else if (gamePanel.gameState == gamePanel.characterState) {
            characterState(code);
        }
        // OPTIONS STATE
        else if (gamePanel.gameState == gamePanel.optionsState) {
            optionsState(code);
        }
        // GAME OVER STATE
        else if (gamePanel.gameState == gamePanel.gameOverState) {
            gameOverState(code);
        }
        // TRADE STATE
        else if (gamePanel.gameState == gamePanel.tradeState) {
            tradeState(code);
        }

        // MAP STATE
        else if (gamePanel.gameState == gamePanel.mapState) {
            mapState(code);
        }

        // FIGHTING STATE
        else if (gamePanel.gameState == gamePanel.fightingState) {
            fightingState(code);
        }

    }

    private void fightingState(int code) {
        switch (code) {
            case KeyEvent.VK_W -> {
                gamePanel.arena.setAction(0);
            }
            case KeyEvent.VK_S -> {
                gamePanel.arena.setAction(3);
            }
            case KeyEvent.VK_A -> {
                gamePanel.arena.setAction(gamePanel.arena.getAction() == 0 ? 0 : gamePanel.arena.getAction() - 1);
            }
            case KeyEvent.VK_D -> {
                gamePanel.arena.setAction(gamePanel.arena.getAction() == 4 ? 4 : gamePanel.arena.getAction() + 1);
            }
            case KeyEvent.VK_ENTER -> {enterPressed = true;}
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_W -> {
                upPressed = false;
                lastKeyPressed = "UP";
            }
            case KeyEvent.VK_S -> {
                downPressed = false;
                lastKeyPressed = "DOWN";
            }
            case KeyEvent.VK_A -> {
                leftPressed = false;
                lastKeyPressed = "LEFT";
            }
            case KeyEvent.VK_D -> {
                rightPressed = false;
                lastKeyPressed = "RIGHT";
            }
            case KeyEvent.VK_ENTER ->
                    enterPressed = false;
            case KeyEvent.VK_F ->
                    capacityKeyPressed = false;

            case KeyEvent.VK_SPACE -> spacePressed = false;

        }
    }
    public void titleState(int code) {

        switch (gamePanel.ui.titleScreenState) {
            case 0:
                switch (code) {
                    case KeyEvent.VK_W:
                        gamePanel.ui.commandNum--;
                        if (gamePanel.ui.commandNum < 0) {
                            gamePanel.ui.commandNum = 2;
                        }
                        break;
                    case KeyEvent.VK_S:
                        gamePanel.ui.commandNum++;
                        if (gamePanel.ui.commandNum > 2) {
                            gamePanel.ui.commandNum = 0;
                        }
                        break;
                    case KeyEvent.VK_ENTER:
                        if (gamePanel.ui.commandNum == 0) {
                            gamePanel.ui.titleScreenState = 1;
                        } else if (gamePanel.ui.commandNum == 1) {

                            Loader loader = new Loader(gamePanel);
                            loader.loadGame("user-data");
                            // load game option later
                            System.out.println("Game loading...");
                            gamePanel.start();
                        } else if (gamePanel.ui.commandNum == 2) {
                            System.exit(0);
                        }
                        break;
                }
                break;
            case 1:
                switch (code) {
                    case KeyEvent.VK_W:
                        gamePanel.ui.commandNum--;
                        if (gamePanel.ui.commandNum < 0) {
                            gamePanel.ui.commandNum = 3;
                        }
                        break;
                    case KeyEvent.VK_S:
                        gamePanel.ui.commandNum++;
                        if (gamePanel.ui.commandNum > 3) {
                            gamePanel.ui.commandNum = 0;
                        }
                        break;
                    case KeyEvent.VK_ENTER:
                        Loader loader = new Loader(gamePanel);
                        switch (gamePanel.ui.commandNum) {
                            case 0:
                                loader.loadPlayer("ressources/player/fighter");
                                // FIGHTER PLAYER CLASS
                                gamePanel.ui.titleScreenState = 2;
                                break;
                            case 1:
                                loader.loadPlayer("ressources/player/thief");
                                // THIEF PLAYER CLASS
                                gamePanel.ui.titleScreenState = 2;
                                break;
                            case 2:
                                loader.loadPlayer("ressources/player/sorcerer");
                                // SORCERER PLAYER CLASS
                                gamePanel.ui.titleScreenState = 2;
                                break;
                            case 3:
                                gamePanel.ui.titleScreenState = 0;
                                gamePanel.ui.commandNum = 0;
                                break;
                        }
                        break;
                }
                break;
            case 2:
                switch (code) {
                    case KeyEvent.VK_W:
                        gamePanel.ui.commandNum--;
                        if (gamePanel.ui.commandNum < 0) {
                            gamePanel.ui.commandNum = 3;
                        }
                        break;
                    case KeyEvent.VK_S:
                        gamePanel.ui.commandNum++;
                        if (gamePanel.ui.commandNum > 3) {
                            gamePanel.ui.commandNum = 0;
                        }
                        break;
                    case KeyEvent.VK_ENTER:
                        switch (gamePanel.ui.commandNum) {
                            case 0:
                                // RPG MODE
                                gamePanel.start();
                                break;
                            case 1:
                                // TURN BASED MODE
                                gamePanel.fightingStyle = true;
                                gamePanel.start();
                                break;
                            case 2:
                                gamePanel.ui.titleScreenState = 0;
                                gamePanel.ui.commandNum = 0;
                                break;
                        }
                        break;
                }
        }
    }





//        if (gamePanel.ui.titleScreenState == 0) {
//            if (code == KeyEvent.VK_W) {
//                gamePanel.ui.commandNum--;
//                if (gamePanel.ui.commandNum < 0) {
//                    gamePanel.ui.commandNum = 2;
//                }
//            }
//            if (code == KeyEvent.VK_S) {
//                gamePanel.ui.commandNum++;
//                if (gamePanel.ui.commandNum > 2) {
//                    gamePanel.ui.commandNum = 0;
//                }
//            }
//            if (code == KeyEvent.VK_ENTER) {
//                if (gamePanel.ui.commandNum == 0) {
//                    gamePanel.ui.titleScreenState = 1;
//                }
//                else if (gamePanel.ui.commandNum == 1) {
//                    // load game option later
//                }
//                else if (gamePanel.ui.commandNum == 2) {
//                    System.exit(0);
//                }
//            }
//        }
//        else if (gamePanel.ui.titleScreenState == 1) {
//            if (code == KeyEvent.VK_W) {
//                gamePanel.ui.commandNum--;
//                if (gamePanel.ui.commandNum < 0) {
//                    gamePanel.ui.commandNum = 3;
//                }
//            }
//            if (code == KeyEvent.VK_S) {
//                gamePanel.ui.commandNum++;
//                if (gamePanel.ui.commandNum > 3) {
//                    gamePanel.ui.commandNum = 0;
//                }
//            }
//            if (code == KeyEvent.VK_ENTER) {
//                if (gamePanel.ui.commandNum == 0) {
//                    // FIGHTER PLAYER CLASS
//                    System.out.println("Do some fighter specific stuff");
//                    gamePanel.gameState = gamePanel.playState;
//                    gamePanel.playMusic(0);
//                }
//                else if (gamePanel.ui.commandNum == 1) {
//                    // THIEF PLAYER CLASS
//                    System.out.println("Do some thief specific stuff");
//                    gamePanel.player.playerClass = "Thief";
//                    gamePanel.player.setDefaultValues();
//                    gamePanel.player.getPlayerImage();
//                    gamePanel.gameState = gamePanel.playState;
//                    gamePanel.playMusic(0);
//                }
//                else if (gamePanel.ui.commandNum == 2) {
//                    // SORCERER PLAYER CLASS
//                    System.out.println("Do some sorcerer specific stuff");
//                    gamePanel.player.playerClass = "Sorcerer";
//                    gamePanel.player.setDefaultValues();
//                    gamePanel.player.getPlayerImage();
//                    gamePanel.gameState = gamePanel.playState;
//                    gamePanel.playMusic(0);
//                }
//                else if (gamePanel.ui.commandNum == 3) {
//                    gamePanel.ui.titleScreenState = 0;
//                    gamePanel.ui.commandNum = 0;
//                }
//            }
//        }

    public void playState(int code) {
        switch (code) {
            case KeyEvent.VK_W -> upPressed = true;
            case KeyEvent.VK_S -> downPressed = true;
            case KeyEvent.VK_A -> leftPressed = true;
            case KeyEvent.VK_D -> rightPressed = true;
            case KeyEvent.VK_P -> gamePanel.gameState = gamePanel.pauseState;
            case KeyEvent.VK_ENTER -> enterPressed = true;
            case KeyEvent.VK_T -> showDebugText = !showDebugText;
            case KeyEvent.VK_C -> gamePanel.gameState = gamePanel.characterState;
            case KeyEvent.VK_R -> {
                switch (gamePanel.currentMap) {
                    case 0 -> gamePanel.tileManager.loadMap("startingVillage/startingVillageMap.txt", 0);
                    case 1 -> gamePanel.tileManager.loadMap("/maps/merchant_house.txt", 1);
                }

            }
            case KeyEvent.VK_ESCAPE -> gamePanel.gameState = gamePanel.optionsState;
            case KeyEvent.VK_SPACE -> spacePressed = true;
            case KeyEvent.VK_M -> gamePanel.gameState = gamePanel.mapState;
            case KeyEvent.VK_F -> {
                if (!gamePanel.fightingStyle)
                    capacityKeyPressed = true;
            }
        }
    }
    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
            gamePanel.gameState = gamePanel.playState;
        }
    }
    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
    }
    public void characterState(int code) {
        playerInventory(code);
        switch (code) {
            case KeyEvent.VK_C: gamePanel.gameState = gamePanel.playState; break;
            case KeyEvent.VK_ENTER:
                gamePanel.player.selectItem();
                break;
        }
    }
    public void optionsState(int code) {
        int maxCommandNum = 0;
        switch (gamePanel.ui.subState) {
            case 0 -> maxCommandNum = 5;
            case 2 -> maxCommandNum = 1;
        }

        switch (code) {
            case KeyEvent.VK_ESCAPE -> gamePanel.gameState = gamePanel.playState;
            case KeyEvent.VK_ENTER -> enterPressed = true;
            case KeyEvent.VK_W -> {
                gamePanel.ui.commandNum--;
                gamePanel.playSE(10);
                if (gamePanel.ui.commandNum < 0) {
                    gamePanel.ui.commandNum = maxCommandNum;
                }
            }
            case KeyEvent.VK_S -> {
                gamePanel.ui.commandNum++;
                gamePanel.playSE(10);
                if (gamePanel.ui.commandNum > maxCommandNum) {
                    gamePanel.ui.commandNum = 0;
                }
            }
            case KeyEvent.VK_A -> {
                if (gamePanel.ui.subState == 0) {
                    if (gamePanel.ui.commandNum == 1 && gamePanel.music.volumeScale > 0) {
                        gamePanel.music.volumeScale--;
                        gamePanel.music.checkVolume();
                        gamePanel.playSE(10);
                    }
                    if (gamePanel.ui.commandNum == 2 && gamePanel.se.volumeScale > 0) {
                        gamePanel.se.volumeScale--;
                        gamePanel.playSE(10);
                    }
                }
            }
            case KeyEvent.VK_D -> {
                if (gamePanel.ui.subState == 0) {
                    if (gamePanel.ui.commandNum == 1 && gamePanel.music.volumeScale < 5) {
                        gamePanel.music.volumeScale++;
                        gamePanel.music.checkVolume();
                        gamePanel.playSE(10);
                    }
                    if (gamePanel.ui.commandNum == 2 && gamePanel.se.volumeScale < 5) {
                        gamePanel.se.volumeScale++;
                        gamePanel.playSE(10);
                    }
                }
            }
        }

    }
    public void gameOverState(int code) {
        if (code == KeyEvent.VK_W) {
            gamePanel.ui.commandNum--;
            if (gamePanel.ui.commandNum < 0) {
                gamePanel.ui.commandNum = 1;
            }
            gamePanel.playSE(10);
        }
        else if (code == KeyEvent.VK_S) {
            gamePanel.ui.commandNum++;
            if (gamePanel.ui.commandNum > 1) {
                gamePanel.ui.commandNum = 0;
            }
            gamePanel.playSE(10);
        }
        else if (code == KeyEvent.VK_ENTER) {
            if (gamePanel.ui.commandNum == 0) {
                gamePanel.gameState = gamePanel.playState;
                gamePanel.retry();
            } else if (gamePanel.ui.commandNum == 1) {
                gamePanel.gameState = gamePanel.titleState;
                gamePanel.restart();
            }
        }
    }
    public void tradeState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        switch (gamePanel.ui.subState) {
            case 0 -> {
                if (code == KeyEvent.VK_W) {
                    gamePanel.ui.commandNum--;
                    if (gamePanel.ui.commandNum < 0) {
                        gamePanel.ui.commandNum = 2;
                    }
                    gamePanel.playSE(10);
                }
                if (code == KeyEvent.VK_S) {
                    gamePanel.ui.commandNum++;
                    if (gamePanel.ui.commandNum > 2) {
                        gamePanel.ui.commandNum = 0;
                    }
                    gamePanel.playSE(10);
                }
            }
            case 1 -> {
                npcInventory(code);
                if (code == KeyEvent.VK_ESCAPE) {
                    gamePanel.ui.subState = 0;
                }
            }
            case 2 ->{
                playerInventory(code);
                if (code == KeyEvent.VK_ESCAPE) {
                    gamePanel.ui.subState = 0;
                }
            }
        };
    }
    public void playerInventory(int code) {
        switch (code) {
            case KeyEvent.VK_W -> {
                if (gamePanel.ui.playerSlotRow != 0) {
                    gamePanel.ui.playerSlotRow--;
                    gamePanel.playSE(10);
                }
            }
            case KeyEvent.VK_S -> {
                if (gamePanel.ui.playerSlotRow != 3) {
                    gamePanel.ui.playerSlotRow++;
                    gamePanel.playSE(10);
                }
            }
            case KeyEvent.VK_A -> {
                if (gamePanel.ui.playerSlotCol != 0) {
                    gamePanel.ui.playerSlotCol--;
                    gamePanel.playSE(10);
                }
            }
            case KeyEvent.VK_D -> {
                if (gamePanel.ui.playerSlotCol != 4) {
                    gamePanel.ui.playerSlotCol++;
                    gamePanel.playSE(10);
                }
            }
        }
    }
    public void npcInventory(int code) {
        switch (code) {
            case KeyEvent.VK_W -> {
                if (gamePanel.ui.npcSlotRow != 0) {
                    gamePanel.ui.npcSlotRow--;
                    gamePanel.playSE(10);
                }
            }
            case KeyEvent.VK_S -> {
                if (gamePanel.ui.npcSlotRow != 3) {
                    gamePanel.ui.npcSlotRow++;
                    gamePanel.playSE(10);
                }
            }
            case KeyEvent.VK_A -> {
                if (gamePanel.ui.npcSlotCol != 0) {
                    gamePanel.ui.npcSlotCol--;
                    gamePanel.playSE(10);
                }
            }
            case KeyEvent.VK_D -> {
                if (gamePanel.ui.npcSlotCol != 4) {
                    gamePanel.ui.npcSlotCol++;
                    gamePanel.playSE(10);
                }
            }
        }
    }


    private void mapState(int code) {
        gamePanel.gameState = code == KeyEvent.VK_M ? gamePanel.playState : gamePanel.mapState;
    }

    public void resetValues() {
        enterPressed = false;
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        capacityKeyPressed = false;
        spacePressed = false;
        lastKeyPressed = "DOWN";
    }
}
