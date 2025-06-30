package entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Entity {

    public GamePanel gamePanel;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, upStanding, downStanding, leftStanding, rightStanding;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2,
            guardUp, guardDown, guardLeft, guardRight;
    public BufferedImage image, image2, image3;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;
    public String[][] dialogues = new String[20][20];

    // STATE
    public String direction = "DOWN";
    public int worldX, worldY;
    public int spriteNumber = 1;
    public int dialogueIndex = 0;
    public int dialogueSet = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean guarding = false;
    public boolean transparent = false;
    public boolean multiple_sprites = false;
    public boolean canLetPlayerEnterDungeon = false;

    // COUNTER
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int dyingCounter = 0;
    public int hpBarCounter = 0;
    public int capacityCooldown = 0;

    // CHARACTER ATTRIBUTES
    public String name;
    public int maxLife;
    public int life;
    public Resource resource;
    public int speed;
    public int baseSpeed;
    public int level;
    public int strength;
    public int baseStrength;
    public int dexterity;
    public int baseDexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public int motion1_duration;
    public int motion2_duration;
    public Entity currentWeapon;
    public Entity currentShield;
    public Capacity capacity;

    // ITEM ATTRIBUTES
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;
    public int price;
    public int amount = 1;
    public boolean stackable = false;

    // TYPE
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickUpOnly = 7;
    public final int type_obstacle = 8;
    public final int type_interactive = 9;
    public final int type_invisible = 10;
    public int type; // 0 = player, 1 = npc, 2 = monster

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setAction() {}
    public void checkDrop() {}
    public void dropItem(Entity droppedItem) {
        if (droppedItem == null) {return;}
        for (int i = 0; i < gamePanel.objects[gamePanel.currentMap].length; i++) {
            if (gamePanel.objects[gamePanel.currentMap][i] == null) {
                gamePanel.objects[gamePanel.currentMap][i] = droppedItem;
                gamePanel.objects[gamePanel.currentMap][i].worldX = worldX; // the dead monster's worldX and worldY
                gamePanel.objects[gamePanel.currentMap][i].worldY = worldY;
                break;
            }
        }
    }
    public int getAttack() {
        if (currentWeapon != null) {
            attackArea = currentWeapon.attackArea;
            return attack = strength * currentWeapon.attackValue;
        }
        return attack = strength;
    }
    public int getDefense() {
        return defense = currentShield != null ? dexterity * currentShield.defenseValue : dexterity;
    }
    public void damageReaction() {}
    /*public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gamePanel.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gamePanel.player.direction) {
            case "UP":
                direction = "DOWN";
                break;
            case "DOWN":
                direction = "UP";
                break;
            case "LEFT":
                direction = "RIGHT";
                break;
            case "RIGHT":
                direction = "LEFT";
                break;
        }
    }*/
    public void speak() {
        startDialogue(dialogueSet);
    }
    public void startDialogue(int setNum) {
        gamePanel.gameState = gamePanel.dialogueState;
        gamePanel.ui.currentDialogue = dialogues[setNum];
        dialogueSet = setNum;
        gamePanel.keyHandler.resetValues();
    }
    public void facePlayer() {
        switch (gamePanel.player.direction) {
            case "UP":
                direction = "DOWN";
                break;
            case "DOWN":
                direction = "UP";
                break;
            case "LEFT":
                direction = "RIGHT";
                break;
            case "RIGHT":
                direction = "LEFT";
                break;
        }
    }
    public void interact(Entity entity) {}
    public void use(Entity entity) {}
    public Color getParticleColor() {
        return null;
    }
    public int getParticleSize() {
        return 0;
    }
    public int getParticleSpeed() {
        return 0;
    }
    public int getParticleMaxLife() {
        return 0;
    }
    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gamePanel, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gamePanel, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gamePanel, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gamePanel, target, color, size, speed, maxLife, 2, 1);
        gamePanel.particleList.add(p1);
        gamePanel.particleList.add(p2);
        gamePanel.particleList.add(p3);
        gamePanel.particleList.add(p4);
    }
    public void checkCollision() {
        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.monsters);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.iTile);
        boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);

        if (this.type == type_monster && contactPlayer && !gamePanel.player.invincible) {
            if (gamePanel.fightingStyle)
                gamePanel.arena.startFight(gamePanel.player, this);
            else
                damagePlayer(attack);
        }
    }
    public void update() {

        if (attacking) {
            attacking();
        }
        else if (type == type_monster || type == type_npc) {
            setAction();
            checkCollision();

            // IF COLLISION IS FALSE, CAN MOVE
            if (!collisionOn) {
                switch (direction) {
                    case "UP":
                        worldY -= speed;
                        break;
                    case "DOWN":
                        worldY += speed;
                        break;
                    case "LEFT":
                        worldX -= speed;
                        break;
                    case "RIGHT":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 24) {
                if (spriteNumber == 1) {
                    spriteNumber = 2;
                } else if (spriteNumber == 2) {
                    if (multiple_sprites && !Objects.equals(direction, "UP") && !Objects.equals(direction, "DOWN")) {
                        spriteNumber = 3;
                    } else {
                        spriteNumber = 1;
                    }
                } else if (spriteNumber == 3) {
                    spriteNumber = 4;
                } else if (spriteNumber == 4) {
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }

            if (invincible) {
                invincibleCounter++;
                if (invincibleCounter > 40) {
                    invincible = false;
                    invincibleCounter = 0;
                }
            }
            if (capacity != null) {
                capacity.endCapacity();
                capacityCooldown = capacityCooldown > 0 ? capacityCooldown - 1 : 0;
            }
        }
    }
    public void attacking() {
        spriteCounter++;

        if (spriteCounter <= motion1_duration) {
            spriteNumber = 1;
        }
        if (spriteCounter > motion1_duration && spriteCounter <= motion2_duration) {
            spriteNumber = 2;

            // Save the current worldX, worldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;

            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Adjust player's worldX/Y for the attackArea
            switch (direction) {
                case "UP": worldY -= attackArea.height; break;
                case "DOWN": worldY += attackArea.height; break;
                case "LEFT": worldX -= attackArea.width; break;
                case "RIGHT": worldX += attackArea.width; break;
            }

            // attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            if (type == type_monster) {
                if (gamePanel.collisionChecker.checkPlayer(this)) {
                    damagePlayer(attackValue);
                }
            } else {
                // Check monster collision with the updated worldX, worldY and solidArea
                int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monsters);
                if (gamePanel.player.playerClass.equals("Sorcerer"))
                    gamePanel.player.damageMonster(monsterIndex, 1);
                else
                    gamePanel.player.damageMonster(monsterIndex, attack);
                int iTileIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.iTile);
                gamePanel.player.damageInteractiveTile(iTileIndex);
            }




            // After checking collision, restore the original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > motion2_duration) {
            spriteNumber = 1;
            spriteCounter = 0;
            attacking = false;
        }

    }
    public int getCenterX() {
         return worldX + left1.getWidth() / 2;
    }
    public int getCenterY() {
        return worldY + up1.getHeight() / 2;
    }
    public int getXDistance(Entity target) {
        return Math.abs(getCenterX() - target.getCenterX());
    }
    public int getYDistance(Entity target) {
        return Math.abs(getCenterY() - target.getCenterY());
    }
    public int getTileDistance(Entity target) {
        return (getXDistance(target) + getYDistance(target));
    }
    public int getGoalCol(Entity target) {
        return (target.worldX + target.solidArea.x) / gamePanel.tileSize;
    }
    public int getGoalRow(Entity target) {
        return (target.worldY + target.solidArea.y) / gamePanel.tileSize;
    }
    public void checkAttackOrNot(int rate, int straight, int horizontal) {
        boolean targetInRange = false;
        int xDis = getXDistance(gamePanel.player);
        int yDis = getYDistance(gamePanel.player);

        switch(direction) {
            case "UP" -> {
                if (gamePanel.player.getCenterY() < getCenterY() && yDis < straight && xDis < horizontal) {
                    targetInRange = true;
                }
            }
            case "DOWN" -> {
                if (gamePanel.player.getCenterY() > getCenterY() && yDis < straight && xDis < horizontal) {
                    targetInRange = true;
                }
            }
            case "LEFT" -> {
                if (gamePanel.player.getCenterX() < getCenterX() && xDis < straight && yDis < horizontal) {
                    targetInRange = true;
                }
            }
            case "RIGHT" ->{
                if (gamePanel.player.getCenterX() > getCenterX() && xDis < straight && yDis < horizontal) {
                    targetInRange = true;
                }
            }
        }

        if (targetInRange) {
            // Check if it initiates an attack
            int i = new Random().nextInt(rate);
            if (i == 0) {
                attacking = true;
                spriteNumber = 1;
                spriteCounter = 0;
            }
        }
    }
    public void damagePlayer(int attack) {
        if (!gamePanel.player.invincible) {

            int damage = attack - gamePanel.player.defense;
            // Get an opposite direction of this attack
            String canGuardDirection = getOppositeDirection(direction);
            if (gamePanel.player.guarding && Objects.equals(gamePanel.player.direction, canGuardDirection)) {
                damage /= 3;
                gamePanel.playSE(14);
            }
            else {
                gamePanel.playSE(6);
                if (damage < 1) { damage = 1; }
            }
            if (damage != 0) {
                gamePanel.player.transparent = true;
            }



            gamePanel.player.life -= damage;
            gamePanel.player.invincible = true;
        }
    }
    public String getOppositeDirection(String direction) {
        return switch (direction) {
            case "UP" -> "DOWN";
            case "DOWN" -> "UP";
            case "LEFT" -> "RIGHT";
            case "RIGHT" -> "LEFT";
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        };

    }
    public void checkStartChasingOrNot(Entity target, int distance, int rate) {
        if (getTileDistance(target) < distance) {
            int i = new Random().nextInt(rate);
            if (i == 0) {
                onPath = true;
            }
        }
    }
    public void checkStopChasingOrNot(Entity target, int distance, int rate) {
        if (getTileDistance(target) > distance) {
            int i = new Random().nextInt(rate);
            if (i == 0) {
                onPath = false;
            }
        }
    }
    public void checkShootOrNot(int rate, int shotInterval) {
    }
    public void getRandomDirection(int interval) {
        actionLockCounter++;

        if (actionLockCounter > interval) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // pick up a number from 1 to 100

            if (i <= 25) {direction = "UP";}
            if (i > 25 && i <= 50) {direction = "DOWN";}
            if (i > 50 && i <= 75) {direction = "LEFT";}
            if (i > 75) {direction = "RIGHT";}
            actionLockCounter = 0;
        }
    }
    public boolean haveResource(Entity user) {return false;}
    public void consumeResource(Entity user) {}
    public void draw(Graphics2D g2D) {

        BufferedImage image = null;

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (canDraw()) {
            int tempScreenX = screenX;
            int tempScreenY = screenY;

            switch (direction) {
                case "UP":
                    if (attacking) {
                        tempScreenY = screenY - up1.getHeight();
                        if (spriteNumber == 1) image = attackUp1;
                        if (spriteNumber == 2) image = attackUp2;
                    } else if (guarding) {
                        image = guardUp;
                    } else {
                        if (spriteNumber == 1) image = up1;
                        if (spriteNumber == 2) image = up2;
                    }
                    break;
                case "DOWN":
                    if (attacking) {
                        if (spriteNumber == 1) image = attackDown1;
                        if (spriteNumber == 2) image = attackDown2;
                    } else if (guarding) {
                        image = guardDown;
                    } else {
                        if (spriteNumber == 1) image = down1;
                        if (spriteNumber == 2) image = down2;
                    }
                    break;
                case "LEFT":
                    if (attacking) {
                        tempScreenX = screenX - left1.getWidth();
                        if (spriteNumber == 1) image = attackLeft1;
                        if (spriteNumber == 2) image = attackLeft2;
                    } else if (guarding) {
                        image = guardLeft;
                    } else {
                        if (spriteNumber == 1) image = left1;
                        if (spriteNumber == 2) image = left2;
                    }
                    break;
                case "RIGHT":
                    if (attacking) {
                        if (spriteNumber == 1) image = attackRight1;
                        if (spriteNumber == 2) image = attackRight2;
                    } else if (guarding) {
                        image = guardRight;
                    } else {
                        if (spriteNumber == 1) image = right1;
                        if (spriteNumber == 2) image = right2;
                    }
                    break;
            }

            // MONSTER HP BAR
            if (type == type_monster && hpBarOn) {

                double oneScale = (double) gamePanel.tileSize / maxLife;
                double hpBarValue = oneScale * life;
                if (life <= 0) {
                    hpBarValue = 0;
                }

                g2D.setColor(new Color(35, 35, 35));
                g2D.fillRect(screenX - 1, screenY - 16, gamePanel.tileSize + 2, 12);
                g2D.setColor(new Color(255, 0, 30));
                g2D.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);

                hpBarCounter++;

                if (hpBarCounter > 600) { // 10 sec
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            if (invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2D, 0.4f);
            }
            if (dying) {
                dyingAnimation(g2D);
            }
            g2D.drawImage(image, tempScreenX, tempScreenY, null);
            changeAlpha(g2D, 1f);
        }
    }
    public void dyingAnimation(Graphics2D g2D) {
        dyingCounter ++;

        int i = 5;

        if (dyingCounter <= i){ changeAlpha(g2D, 0f);}
        else if (dyingCounter > i && dyingCounter <= i*2){ changeAlpha(g2D, 1f);}
        else if (dyingCounter > i*2 && dyingCounter <= i*3){ changeAlpha(g2D, 0f); }
        else if (dyingCounter > i*3 && dyingCounter <= i*4){ changeAlpha(g2D, 1f);}
        else if (dyingCounter > i*4 && dyingCounter <= i*5){ changeAlpha(g2D, 0f);}
        else if (dyingCounter > i*5 && dyingCounter <= i*6){changeAlpha(g2D, 1f);}
        else if (dyingCounter > i*6 && dyingCounter <= i*7){changeAlpha(g2D, 0f);}
        else if (dyingCounter > i*7 && dyingCounter <= i*8){changeAlpha(g2D, 1f);}
        else if (dyingCounter > i*8 ) {
            alive = false;
        }
    }
    public void changeAlpha(Graphics2D g2D, float alphaValue) {
        g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    public void searchPath(int goalCol, int goalRow) {

        int startCol = (worldX + solidArea.x) / gamePanel.tileSize;
        int startRow = (worldY + solidArea.y) / gamePanel.tileSize;

        gamePanel.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if (gamePanel.pFinder.search()) {

            // Next worldX and worldY
            int nextX = gamePanel.pFinder.pathList.getFirst().col * gamePanel.tileSize;
            int nextY = gamePanel.pFinder.pathList.getFirst().row * gamePanel.tileSize;
            // Entity's solidArea position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;
            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gamePanel.tileSize) {
                direction = "UP";
                checkCollision();
                if (collisionOn) {
                    direction = "LEFT";
                }
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gamePanel.tileSize) {
                direction = "DOWN";
            } else if (enTopY >= nextY && enBottomY < nextY + gamePanel.tileSize) {
                // left or right
                if (enLeftX > nextX) {
                    direction = "LEFT";
                } else if (enLeftX < nextX) {
                    direction = "RIGHT";
                }
            } else if (enTopY > nextY && enLeftX > nextX) {
                // up or left
                direction = "UP";
                checkCollision();
                if (collisionOn) {
                    direction = "LEFT";
                }

            } else if (enTopY > nextY && enLeftX < nextX) {
                // up or right
                direction = "UP";
                checkCollision();
                if (collisionOn) {
                    direction = "RIGHT";
                }

            } else if (enTopY < nextY && enLeftX > nextX) {
                // down or left
                direction = "DOWN";
                checkCollision();
                if (collisionOn) {
                    direction = "LEFT";
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                // down or right
                direction = "DOWN";
                checkCollision();
                if (collisionOn) {
                    direction = "RIGHT";
                }
            }

            // If reaches the goal, stop the search
//            int nextCol = gamePanel.pFinder.pathList.getFirst().col;
//            int nextRow = gamePanel.pFinder.pathList.getFirst().row;
//            if (nextCol == goalCol && nextRow == goalRow) {
//                onPath = false;
//            }
        }

    }
    public void moveTowardPlayer(int interval) {
        actionLockCounter++;

        if (actionLockCounter > interval) {
            if (getXDistance(gamePanel.player) > getYDistance(gamePanel.player)) {
                if (gamePanel.player.getCenterX() < getCenterX()) {
                    direction = "LEFT";
                } else {
                    direction = "RIGHT";
                }
            } else if (getXDistance(gamePanel.player) < getYDistance(gamePanel.player)) {
                if (gamePanel.player.getCenterY() < getCenterY()) {
                    direction = "UP";
                } else {
                    direction = "DOWN";
                }
            }
            actionLockCounter = 0;
        }
    }
    public void getImage(String filePath) {
        up1 = setup(filePath + "_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup(filePath + "_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup(filePath + "_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup(filePath + "_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup(filePath + "_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup(filePath + "_left_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup(filePath + "_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup(filePath + "_right_2", gamePanel.tileSize, gamePanel.tileSize);
        upStanding = up1;
        downStanding = down1;
        leftStanding = left1;
        rightStanding = right1;
        if (this instanceof Player) {
        attackUp1 = setup(filePath + "_attack_up_1", gamePanel.tileSize, gamePanel.tileSize*2);
        attackUp2 = setup(filePath + "_attack_up_2", gamePanel.tileSize, gamePanel.tileSize*2);}
        /*attackDown1 = setup(filePath + "_attack_down_1", gamePanel.tileSize, gamePanel.tileSize);
        attackDown2 = setup(filePath + "_attack_down_2", gamePanel.tileSize, gamePanel.tileSize);
        attackLeft1 = setup(filePath + "_attack_left_1", gamePanel.tileSize, gamePanel.tileSize);
        attackLeft2 = setup(filePath + "_attack_left_2", gamePanel.tileSize, gamePanel.tileSize);
        attackRight1 = setup(filePath + "_attack_right_1", gamePanel.tileSize, gamePanel.tileSize);
        attackRight2 = setup(filePath + "_attack_right_2", gamePanel.tileSize, gamePanel.tileSize);
        }
    */}
    public Entity getItem(Entity item) {

        for (Entity object : inventory) {
            if (object.getClass().equals(item.getClass())) {
                return object;
            }
        }
        return null;
    }
    public void addItem(Entity item) {
        if (item.stackable && getItem(item) != null) {
            getItem(item).amount+= item.amount;
        }
    }
/*
    @Override
    public String toString() {
        return name;
    }
*/
    public boolean canDraw() {
        return worldX + solidArea.x + solidArea.width > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - solidArea.x - solidArea.width < gamePanel.player.worldX + gamePanel.player.screenX + gamePanel.tileSize/2&&
                worldY + solidArea.y + solidArea.height > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - solidArea.y - solidArea.height < gamePanel.player.worldY + gamePanel.player.screenY + gamePanel.tileSize/2;
    }
}
