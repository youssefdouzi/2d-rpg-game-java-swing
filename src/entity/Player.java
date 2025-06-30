package entity;

import Main.GamePanel;
import Main.KeyHandler;
import object.OBJ_Key;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;
import tile_interactive.InteractiveTile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    KeyHandler keyHandler;
    public final int screenX;
    public final int screenY; // it's basically where we have to draw the player on the screen
    public String playerClass = "Fighter";
    public boolean attackCanceled = false;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel);

        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize/2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize/2);
        // SOLID AREA
        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x; //
        solidAreaDefaultY = solidArea.y;
    }

    public void setDefaultValues() {
//        worldX = gamePanel.tileSize * 23; // the player position in the world
//        worldY = gamePanel.tileSize * 21;
//        worldX = gamePanel.tileSize * 12;
//        worldY = gamePanel.tileSize * 10;


        switch (playerClass) {
            case "Fighter":
                setPlayerDefaultAttributes(8, 3, 7, 1, 1, 0, 5, 0);
                break;
            case "Sorcerer":
                setPlayerDefaultAttributes(4, 4, 1, 1, 1, 0, 5, 0);
                //maxMana = 4;
                //mana = maxMana;
                //projectile = new OBJ_Fireball(gamePanel);
                break;
            case "Thief":
                setPlayerDefaultAttributes(6, 5, 1, 1, 1, 0, 5, 500);
                break;
        }
        direction = "DOWN";
    }
    public void setDefaultPositions() {
        // ajouter ici des pos differentes selon la map
        if (gamePanel.currentMap == 5) {
            worldX = gamePanel.tileSize * 14; // the player position in the world
            worldY = gamePanel.tileSize * 44;
        } else {
            worldX = gamePanel.tileSize * 23; // the player position in the world
            worldY = gamePanel.tileSize * 21;
            direction = "DOWN";
        }

    }
    public void restoreLifeAndMana() {
        life = maxLife;
        resource.replenish();
        invincible = false;
    }
    public void setItems() {
        // ADD PLAYER CLASS SWITCH STATEMENT LATER
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gamePanel,1));
    }
    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        motion1_duration = currentWeapon.motion1_duration;
        motion2_duration = currentWeapon.motion2_duration;
        return attack = strength * currentWeapon.attackValue;
    }
    public int getDefense() {
        return defense = dexterity * currentShield.defenseValue;
    }
    public void quitFight() {
        gamePanel.keyHandler.enterPressed = false;
        gamePanel.ui.npcSlotCol = 0;
        gamePanel.ui.npcSlotRow = 0;
        gamePanel.gameState = gamePanel.playState;
    }
    public void update() {
            guarding = keyHandler.spacePressed;
            speed = guarding ? baseSpeed / 2 : baseSpeed;
            if (attacking) {
                attacking();
            }
            else if (keyHandler.rightPressed || keyHandler.downPressed || keyHandler.upPressed ||
                    keyHandler.leftPressed || keyHandler.enterPressed) {
                if (keyHandler.upPressed) {
                    direction = "UP";
                } else if (keyHandler.downPressed) {
                    direction = "DOWN";
                } else if (keyHandler.leftPressed) {
                    direction = "LEFT";
                } else if (keyHandler.rightPressed) {
                    direction = "RIGHT";
                }

                // CHECK TILE COLLISION
                gamePanel.collisionChecker.checkTile(this);

                // CHECK OBJECT COLLISION
                int objIndex = gamePanel.collisionChecker.checkObject(this, true);
                pickUpObject(objIndex);

                // CHECK NPC COLLISION
                int npcIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
                if (keyHandler.enterPressed) {
                    interactNPC(npcIndex);
                }

                // CHECK MONSTER COLLISION
                int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monsters);
                contactMonster(monsterIndex);

                // CHECK INTERACTIVE TILES COLLISION
                gamePanel.collisionChecker.checkEntity(this, gamePanel.iTile);

                // CHECK EVENT
                gamePanel.eventHandler.checkEvent();

                // IF NO COLLISION DETECTED, PLAYER CAN MOVE
                if (!collisionOn && (keyHandler.downPressed || keyHandler.upPressed || keyHandler.leftPressed || keyHandler.rightPressed)) {
                    switch (direction) {
                        case "UP" -> worldY -= speed;
                        case "DOWN" -> worldY += speed;
                        case "LEFT" -> worldX -= speed;
                        case "RIGHT" -> worldX += speed;
                    }
                }

                if (keyHandler.enterPressed && !attackCanceled) {
                    gamePanel.playSE(7);
                    attacking = true;
                    spriteCounter = 0;
                }
                attackCanceled = false;

                spriteCounter++;
                if (spriteCounter > 12) {
                    if (spriteNumber == 1) {
                        spriteNumber = 2;
                    } else if (spriteNumber == 2) {
                        spriteNumber = 1;
                    }
                    spriteCounter = 0;
                }
            }

            if (gamePanel.keyHandler.capacityKeyPressed && capacity.canUseCapacity()) {
                    capacity.useCapacity(null);
            }
            //        else {
            //            standCounter++;
            //            if (standCounter == 20) {
            //                spriteNumber = 1;
            //                standCounter = 0;
            //            }
            //        }

            //        if (!keyHandler.upPressed && !keyHandler.downPressed && !keyHandler.leftPressed && !keyHandler.rightPressed) {
            //            switch (keyHandler.lastKeyPressed) {
            //                case "UP": direction = "STANDING_UP"; break;
            //                case "DOWN": direction = "STANDING_DOWN"; break;
            //                case "LEFT": direction = "STANDING_LEFT"; break;
            //                case "RIGHT": direction = "STANDING_RIGHT"; break;
            //            }
            //        }

            // This needs to be outside of key if statement
            if (invincible) {
                invincibleCounter++;
                if (invincibleCounter > 60) {
                    transparent = false;
                    invincible = false;
                    invincibleCounter = 0;
                }
            }

            if (life > maxLife) {
                life = maxLife;
            }
            //if (mana > maxMana) {
             //   mana = maxMana;
            //}
            if (life <= 0) {
                gamePanel.gameState = gamePanel.gameOverState;
                gamePanel.ui.commandNum = -1;
                gamePanel.stopMusic();
                gamePanel.playSE(13);
            }
            capacity.endCapacity();
            capacityCooldown = capacityCooldown > 0 ? capacityCooldown - 1 : 0;

    }
    public void pickUpObject(int index) {
        if (index != -1) {
            Entity object = gamePanel.objects[gamePanel.currentMap][index];
            if (object.type == type_invisible) {
                return;
            }
            // PICK UP ONLY ITEMS
            if (object.type == type_pickUpOnly) {
                object.use(this);
                gamePanel.objects[gamePanel.currentMap][index] = null;
            }
            else if(object.type == type_obstacle) {
                if (keyHandler.enterPressed) {
                    attackCanceled=true;
                    object.interact(this);
                }
            }
            else if (object.type == type_interactive) {
                object.interact(this);
            }

            else {
                // INVENTORY ITEMS
                String text;
                if (object.stackable && getItem(object) != null) {
                    addItem(object);
                    gamePanel.playSE(1);
                    text = "Got a " + gamePanel.objects[gamePanel.currentMap][index].name + " !";
                }
                else if (inventory.size() != maxInventorySize) {
                    inventory.add(gamePanel.objects[gamePanel.currentMap][index]);
                    gamePanel.playSE(1);
                    text = "Got a " + gamePanel.objects[gamePanel.currentMap][index].name + " !";
                } else {
                    text = "You cannot carry any more!";
                }
                gamePanel.ui.addMessage(text);
                gamePanel.objects[gamePanel.currentMap][index] = null;
            }


        }
    }
    public void interactNPC(int index) {
        if (index != -1) {
            attackCanceled = true;
            gamePanel.gameState = gamePanel.dialogueState;
            gamePanel.npc[gamePanel.currentMap][index].speak();
        }
    }
    public void contactMonster(int i) {
        if (i != -1) {
            if (gamePanel.fightingStyle) {
                gamePanel.arena.startFight(this, gamePanel.monsters[gamePanel.currentMap][i]);
            }
            else if (!invincible && !gamePanel.monsters[gamePanel.currentMap][i].dying) {
                gamePanel.playSE(6);

                int damage = gamePanel.monsters[gamePanel.currentMap][i].attack - gamePanel.player.defense;
                if (damage < 0) { damage = 0; }

                life -= damage;
                invincible = true;
                transparent = true;

            }
        }
    }
    public void damageMonster(int i, int attack) {
        if (i != -1) {
            if (gamePanel.fightingStyle) {
                gamePanel.arena.startFight(this, gamePanel.monsters[gamePanel.currentMap][i]);
            }
            else if (!gamePanel.monsters[gamePanel.currentMap][i].invincible) {
                gamePanel.playSE(5);

                int damage = attack - gamePanel.monsters[gamePanel.currentMap][i].defense;
                if (damage < 0) { damage = 0; }

                gamePanel.monsters[gamePanel.currentMap][i].life -= damage;
                gamePanel.ui.addMessage(damage + " damage!");
                gamePanel.monsters[gamePanel.currentMap][i].invincible = true;
                gamePanel.monsters[gamePanel.currentMap][i].damageReaction();

                if (gamePanel.monsters[gamePanel.currentMap][i].life <= 0) {
                    gamePanel.monsters[gamePanel.currentMap][i].dying = true;
                    gamePanel.ui.addMessage(" killed the " + gamePanel.monsters[gamePanel.currentMap][i].name + "!");
                    exp += gamePanel.monsters[gamePanel.currentMap][i].exp;
                    gamePanel.ui.addMessage(" Exp + " + gamePanel.monsters[gamePanel.currentMap][i].exp);
                    checkLevelUp();
                }
            }
        }
    }
    public void damageInteractiveTile(int i) {

        InteractiveTile it = null;

        if (i!=-1)
            it = (InteractiveTile) gamePanel.iTile[gamePanel.currentMap][i];

        if (i != -1 && it.destructible && it.isCorrectItem(this)
                && !gamePanel.iTile[gamePanel.currentMap][i].invincible) {
            it.playSE();
            gamePanel.iTile[gamePanel.currentMap][i].life--;
            gamePanel.iTile[gamePanel.currentMap][i].invincible = true;

            // Generate particle
            generateParticle(gamePanel.iTile[gamePanel.currentMap][i], gamePanel.iTile[gamePanel.currentMap][i]);

            if (gamePanel.iTile[gamePanel.currentMap][i].life == 0) {
                it.getDestroyedForm();

            }
        }
    }
    public void checkLevelUp() {
        if (exp >= nextLevelExp) {
            level++;
            nextLevelExp = nextLevelExp * 2;
            switch (playerClass) {
                case "Fighter" -> {
                    strength++;
                    dexterity++;
                }
                case "Thief" ->{;
                    if (level % 2 == 0) dexterity++;
                    strength+=2;
                }
                case "Sorcerer" ->{
                    strength+=3;
                }
            }
            if (level%5 == 0) maxLife += 2;

            attack = getAttack();
            defense = getDefense();

            gamePanel.playSE(8); // or 9
            gamePanel.gameState = gamePanel.dialogueState;
            gamePanel.ui.currentDialogue[0] = "You are level " + level + " now!\n" + "You feel stronger.";
        }
    }
    public void selectItem() {
        int itemIndex = gamePanel.ui.getItemIndexOnSlot(gamePanel.ui.playerSlotCol, gamePanel.ui.playerSlotRow);

        if (itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == type_sword || selectedItem.type == type_axe) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImages();
            } else if (selectedItem.type == type_shield) {
                currentShield = selectedItem;
                defense = getDefense();
            } else if (selectedItem.type == type_consumable) {
                selectedItem.use(this);
            }
        }
    }
    public void draw(Graphics2D g2D) {


        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "UP":
                if (attacking) {
                    tempScreenY = screenY - gamePanel.tileSize;
                    if (spriteNumber == 1) image = attackUp1;
                    if (spriteNumber == 2) image = attackUp2;
                } else if (guarding) {
                    image = guardUp;
                }
                else {
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
                }
                else {
                    if (spriteNumber == 1) image = down1;
                    if (spriteNumber == 2) image = down2;
                }
                break;
            case "LEFT":
                if (attacking) {
                    tempScreenX = screenX - gamePanel.tileSize;
                    if (spriteNumber == 1) image = attackLeft1;
                    if (spriteNumber == 2) image = attackLeft2;
                } else if (guarding) {
                    image = guardLeft;
                }
                else {
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
                }
                else {
                    if (spriteNumber == 1) image = right1;
                    if (spriteNumber == 2) image = right2;
                }
                break;
            case "STANDING_UP":
                if (attacking) {
                    tempScreenY = screenY - gamePanel.tileSize;
                    if (spriteNumber == 1) image = attackUp1;
                    if (spriteNumber == 2) image = attackUp2;
                } else if (guarding) {
                    image = guardUp;
                }
                else {
                    image = upStanding;
                }
                break;
            case "STANDING_DOWN":
                if (attacking) {
                    if (spriteNumber == 1) image = attackDown1;
                    if (spriteNumber == 2) image = attackDown2;
                } else if (guarding) {
                    image = guardDown;
                }
                else {
                    image = downStanding;
                }
                break;
            case "STANDING_LEFT":
                if (attacking) {
                    tempScreenX = screenX - gamePanel.tileSize;
                    if (spriteNumber == 1) image = attackLeft1;
                    if (spriteNumber == 2) image = attackLeft2;
                } else if (guarding) {
                    image = guardLeft;
                }
                else {
                    image = leftStanding;

                }
                break;
            case "STANDING_RIGHT":
                if (attacking) {
                    if (spriteNumber == 1) image = attackRight1;
                    if (spriteNumber == 2) image = attackRight2;
                } else if (guarding) {
                    image = guardRight;
                }
                else {
                    image = rightStanding;
                }
                break;
        }

        if (transparent) {
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2D.drawImage(image, tempScreenX, tempScreenY, null);

        // Reset alpha
        changeAlpha(g2D, 1f);


    }
    public void getPlayerImage() {
        switch (playerClass) {
            case "Fighter":
                super.getImage("/player/boy");
                break;
            case "Sorcerer":
                super.getImage("/player/sorcerer/sorcerer");
                break;
            case "Thief":
                super.getImage("/player/thief/thief");
                break;
        }
    }
    public void getPlayerAttackImages() {

        if (currentWeapon.type == type_sword) {
            attackUp1 = setup("/player/boy_attack_up_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackUp2 = setup("/player/boy_attack_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown1 = setup("/player/boy_attack_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown2 = setup("/player/boy_attack_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackLeft1 = setup("/player/boy_attack_left_1", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackLeft2 = setup("/player/boy_attack_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight1 = setup("/player/boy_attack_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight2 = setup("/player/boy_attack_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
        } else if (currentWeapon.type == type_axe) {
            attackUp1 = setup("/player/boy_axe_up_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackUp2 = setup("/player/boy_axe_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown1 = setup("/player/boy_axe_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown2 = setup("/player/boy_axe_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackLeft1 = setup("/player/boy_axe_left_1", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackLeft2 = setup("/player/boy_axe_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight1 = setup("/player/boy_axe_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight2 = setup("/player/boy_axe_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
        }
    }
    public void getGuardImages() {
        guardUp = setup("/player/boy_guard_up", gamePanel.tileSize, gamePanel.tileSize );
        guardDown = setup("/player/boy_guard_down", gamePanel.tileSize, gamePanel.tileSize);
        guardLeft = setup("/player/boy_guard_left", gamePanel.tileSize, gamePanel.tileSize);
        guardRight = setup("/player/boy_guard_right", gamePanel.tileSize, gamePanel.tileSize);
    }


    public void setPlayerDefaultAttributes(int maxLife, int speed, int level, int strength, int dexterity, int exp,
                                           int nextLevelExp, int coin) {
        baseSpeed = speed;
        this.maxLife = maxLife; // 1 life equals a half heart
        this.life = maxLife;
        this.speed = speed;
        this.level = level;
        this.strength = strength; // The more he has, the more damage he gives.
        this.dexterity = dexterity; // The more he has, the less damage he receives.
        this.exp = exp;
        this.nextLevelExp = nextLevelExp;
        this.coin = coin;
        this.currentWeapon = new OBJ_Sword_Normal(gamePanel);
        this.currentShield = new OBJ_Shield_Wood(gamePanel);
        this.attack = getAttack();
        this.defense = getDefense();
    }
    public void instantiatePlayer() {
        setDefaultValues();
        getPlayerImage();
        getGuardImages();
        getPlayerAttackImages();
        //setItems();
    }


    public boolean hasItem(Entity item) {
        for (Entity object : inventory) {
            if (object.getClass().equals(item.getClass())) {
                return true;
            }
        }
        return false;
    }



}
