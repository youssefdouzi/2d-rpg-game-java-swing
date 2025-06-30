package Main;

import ai.Pathfinder;
import entity.Entity;
import entity.Player;
import tile.Map;
import tile.TileManager;
import tile_interactive.InteractiveTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 title
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int MAX_SCREEN_COL = 20;
    public final int MAX_SCREEN_ROW = 12;
    public final int screenWidth = tileSize * MAX_SCREEN_COL; // 960 pixels
    public final int screenHeight = tileSize * MAX_SCREEN_ROW; // 576 pixels

    // WORLD SETTINGS
    public final int MAX_WORLD_COL = 100;
    public final int MAX_WORLD_ROW = 100;
    public final int MAX_MAP = 10;
    public int currentMap = 6;

    // FOR FULL SCREEN
    int screenWidth2 = screenWidth;2
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2D;
    public boolean fullScreenOn = false;

    // FPS
    int FPS = 60;

    // SYSTEM
    public TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public ObjectSetter objectSetter = new ObjectSetter(this);
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    public Config config = new Config(this);
    public Pathfinder pFinder = new Pathfinder(this);
    public Map map = new Map(this);
    public Arena arena = new Arena(this);
    public Saver saver = new Saver(this);
    //True means Fighting Turn by turn
    public boolean fightingStyle = false;
    public Time time;
    public EnvironmentManager environmentManager;
    Thread gameThread;

    // ENTITY AND OBJECTS
    public Player player = new Player(this, keyHandler);
    public Entity[][] objects = new Entity[MAX_MAP][150]; // means that we can display up to 10 objects at the same time
    public Entity[][] npc = new Entity[MAX_MAP][10];
    public Entity[][] monsters = new Entity[MAX_MAP][20];
    public InteractiveTile[][] iTile = new InteractiveTile[MAX_MAP][50];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();


    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;
    public final int mapState = 9;
    public final int fightingState = 10;
    public final int gameModeState = 11;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        map.setExploredMaps(new boolean[MAX_MAP][MAX_WORLD_COL][MAX_WORLD_ROW]);
        map.initializeMaps();
    }



    public void start() {
        objectSetter.setMap(currentMap);
        environmentManager = new EnvironmentManager(this);
        environmentManager.setEnvironment(currentMap);
        gameState = playState;
        playMusic(0);
    }

    public void setupGame() {

        objectSetter.setNPC();
        objectSetter.setMonster();
        objectSetter.setInteractiveTile();
        gameState = titleState;

        //tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        //g2D = (Graphics2D) tempScreen.getGraphics();

        if (fullScreenOn) {
            //setFullScreen();
        }
    }
    public void retry() {
        player.setDefaultPositions();
        player.restoreLifeAndMana();
        //objectSetter.setObject();
        objectSetter.setNPC();
        playMusic(0);
    }
    public void restart() {
        player.setDefaultPositions();
        player.setItems();
        objectSetter.setMap(currentMap);
        //objectSetter.setNPC();
        objectSetter.setMonster();
        objectSetter.setInteractiveTile();
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {

        double drawInterval = (double) 1000000000 / FPS; //0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            update();

            repaint();
            //drawToTempScreen(); // draw everything to the buffered image
            //drawToScreen(); // draw the buffered image to the screen


            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public void update() {

        if (gameState == playState) {
            // PLAYER
            player.update();
            time.update();
            environmentManager.update();
            // NPC
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }
            // MONSTERS
            for (int i = 0; i < monsters[1].length; i++) {
                if (monsters[currentMap][i] != null) {
                    if (monsters[currentMap][i].alive && !monsters[currentMap][i].dying){
                        monsters[currentMap][i].update();
                    }
                    if (!monsters[currentMap][i].alive) {
                        monsters[currentMap][i].checkDrop();
                        monsters[currentMap][i] = null;
                    }
                }
            }
            for (int i = 0; i < objects[1].length; i++) {
                if (objects[currentMap][i] != null) {
                        objects[currentMap][i].update();
                    }
            }


            // PROJECTILES
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    if (projectileList.get(i).alive) {
                        projectileList.get(i).update();
                    } else if (!projectileList.get(i).alive) {
                        projectileList.remove(i);
                    }
                }
            }
            // PARTICLES
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    if (particleList.get(i).alive) {
                        particleList.get(i).update();
                    } else if (!particleList.get(i).alive) {
                        particleList.remove(i);
                    }
                }
            }
            // INTERACTIVE TILES
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }
        }
        else if (gameState == pauseState) {
            // nothing
        }
        else if (gameState == fightingState)
            arena.update();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        // DEBUG
        long drawStart = 0;
        if (keyHandler.showDebugText) {
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2D);
        }
        else if (gameState == mapState) {
            map.drawFullMapScreen(g2D);
        }
        else if (gameState == fightingState)
            arena.drawFight(g2D);
        // OTHERS
        else {
            // TILE
            tileManager.draw(g2D); // draw tiles before player otherwise player will be hidden by the tiles

            // INTERACTIVE TILE
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2D);
                }
            }

            entityList.add(player);

           // ADD ENTITIES TO THE LIST

            //entityList.addAll(Arrays.asList(npc));

            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }
            for (int i = 0; i < objects[1].length; i++) {
                if (objects[currentMap][i] != null) {
                    entityList.add(objects[currentMap][i]);
                }
            }
            for (int i = 0; i < monsters[1].length; i++) {
                if (monsters[currentMap][i] != null) {
                    entityList.add(monsters[currentMap][i]);
                }
            }
            for (Entity projectile : projectileList) {
                if (projectile != null) {
                    entityList.add(projectile);
                }
            }
            for (Entity particle : particleList) {
                if (particle != null) {
                    entityList.add(particle);
                }
            }

            // SORT
            entityList.sort(Comparator.comparingInt(e -> e.worldY));

            // DRAW ENTITIES
            for (Entity entity : entityList){
                entity.draw(g2D);
            }

            // EMPTY ENTITY LIST
            entityList.clear();

            environmentManager.draw(g2D);

            // UI
            ui.draw(g2D);
        }



        //DEBUG
        if (keyHandler.showDebugText) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2D.setFont(new Font("Arial", Font.PLAIN, 20));
            g2D.setColor(Color.WHITE);
            int x = 10;
            int y = 400;
            int lineHeight = 20;
            g2D.drawString("WorldX " + player.worldX, x, y); y += lineHeight;
            g2D.drawString("WorldY " + player.worldY, x, y); y += lineHeight;
            g2D.drawString("Col " + (player.worldX + player.solidArea.x) / tileSize, x, y); y += lineHeight;
            g2D.drawString("Row " + (player.worldY + player.solidArea.y) / tileSize, x, y); y += lineHeight;
            g2D.drawString("Draw Time : " + passed, x, y);

            System.out.println("Draw Time : " + passed);
        }

        g2D.dispose();
    }
    public void drawToTempScreen(){
        // DEBUG
        long drawStart = 0;
        if (keyHandler.showDebugText) {
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2D);
        }
        // OTHERS
        else {
            // TILE
            tileManager.draw(g2D); // draw tiles before player otherwise player will be hidden by the tiles

            // INTERACTIVE TILE
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2D);
                }
            }


            // ADD ENTITIES TO THE LIST
            entityList.add(player);

            //entityList.addAll(Arrays.asList(npc));

            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }

            for (int i = 0; i < objects[1].length; i++) {
                if (objects[currentMap][i] != null) {
                    entityList.add(objects[currentMap][i]);
                }
            }

            for (int i = 0; i < monsters[1].length; i++) {
                if (monsters[currentMap][i] != null) {
                    entityList.add(monsters[currentMap][i]);
                }
            }

            for (Entity projectile : projectileList) {
                if (projectile != null) {
                    entityList.add(projectile);
                }
            }
            for (Entity particle : particleList) {
                if (particle != null) {
                    entityList.add(particle);
                }
            }

            // SORT
            entityList.sort(Comparator.comparingInt(e -> e.worldY));

            // DRAW ENTITIES
            for (Entity entity : entityList){
                entity.draw(g2D);
            }

            // EMPTY ENTITY LIST
            entityList.clear();

            // UI
            ui.draw(g2D);
        }



        //DEBUG
        if (keyHandler.showDebugText) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2D.setFont(new Font("Arial", Font.PLAIN, 20));
            g2D.setColor(Color.WHITE);
            int x = 10;
            int y = 400;
            int lineHeight = 20;
            g2D.drawString("WorldX " + player.worldX, x, y); y += lineHeight;
            g2D.drawString("WorldY " + player.worldY, x, y); y += lineHeight;
            g2D.drawString("Col " + (player.worldX + player.solidArea.x) / tileSize, x, y); y += lineHeight;
            g2D.drawString("Row " + (player.worldY + player.solidArea.y) / tileSize, x, y); y += lineHeight;
            g2D.drawString("Draw Time : " + passed, x, y);

            System.out.println("Draw Time : " + passed);
        }
    }
    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }
    public void setFullScreen() {
        // GET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        // GET FULL SCREEN WIDTH AND HEIGHT
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {
        music.stop();
    }
    public void playSE(int i) { // sound effect
        se.setFile(i);
        se.play();
    }
}
