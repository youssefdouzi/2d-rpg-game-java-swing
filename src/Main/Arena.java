package Main;

import entity.Entity;
import entity.Player;

import java.awt.*;
import java.util.Random;

public class Arena {
    GamePanel gamePanel;
    Player player;
    Entity opponent;
    int turn = 0;
    Entity fastestEntity;
    Entity slowestEntity;
    int action = 0; // 0 = Garde | 1 = Attaque basique | 2 = Capacité | 3 = Fuite | 4 = Inventaire
    Random rand = new Random();
    Graphics2D g2;
    String sentence = null;




    int width;
    int height;
    int x0;
    int x1;
    int x2;
    int x3;
    int x4;
    int y1stRow;
    int y2ndRow;

    public Arena(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        width = gamePanel.screenWidth;
        height = gamePanel.screenHeight;
        x0 = 2 * gamePanel.tileSize;
        x1 = width/2-gamePanel.tileSize;
        x2 = width - 5*gamePanel.tileSize;
        x3 = 5*gamePanel.tileSize;
        x4 = width - 7*gamePanel.tileSize;
        y1stRow = height - 140;
        y2ndRow = height - 70;
    }

    public void setOpponent(Entity opponent) {
        this.opponent = opponent;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private void attack(Entity attacker, Entity target) {
        int damage = attacker.getAttack() * (40/(40+target.getDefense()));
        if (target.guarding) {
            target.life -= (int) Math.ceil((double) (damage)/3);
        }
        else {
            target.life -= damage;
        }
        if (target.life <= 0) {
            target.alive = false;
            target.dying = true;
            gamePanel.ui.addMessage(" killed the " + target.name + "!");
            player.exp += target.exp;
            gamePanel.ui.addMessage(" Exp + " + target.exp);
            player.checkLevelUp();
            endFight();
        }
    }

    public void update() {
        if (sentence != null) {
            return;
        }
        if (turn%2 == 0) {
            if (gamePanel.keyHandler.enterPressed) {
                switch (action) {
                    case 0 ->{
                        player.guarding = true;
                        setSentence(player, 0);
                    }
                    case 1 -> {
                        attack(player, opponent);
                        setSentence(player, 1);
                    }
                    case 2 -> {
                        if (player.capacity.canUseCapacity()) {
                            player.capacity.useCapacity(opponent);
                            setSentence(player, 2);
                        }
                    }
                    case 3 -> {
                        if (!(rand.nextInt(100) >= 100 - (opponent.speed - player.speed))) {
                            endFight();
                        }
                    }
                    case 4 -> {gamePanel.gameState = gamePanel.characterState;}
                }
                resetValues(opponent);
                player.capacity.endCapacity();
                player.capacityCooldown = player.capacityCooldown == 0 ? 0 : player.capacityCooldown - 1;
            }


        }
        else {
            switch (rand.nextInt(3)) {
                case 0 -> {
                    opponent.guarding = true;
                    setSentence(opponent, 0);
                }
                case 1 -> {
                    attack(opponent, player);
                    setSentence(opponent, 1);
                }
                case 2 -> {
                    opponent.capacity.useCapacity(player);
                    setSentence(opponent, 2);
                }
            }
            if (opponent.capacity != null) {
                opponent.capacity.endCapacity();
                opponent.capacityCooldown = opponent.capacityCooldown == 0 ? 0 : opponent.capacityCooldown - 1;
            }player.guarding = false;
        }

    }

    private void setSentence(Entity entity, int choice) {
        if (choice == -1) {sentence = null; return;}
        StringBuilder str = new StringBuilder();
        str.append(entity.name).append(" used ");
        switch (choice) {
            case 0 -> {str.append("Guard to reduce incoming damage");}
            case 1 -> {str.append("basic Attack to inflict damage");}
            case 2 -> {str.append(entity.capacity.capacityUsed());}
        }
        sentence = str.toString();
    }



    private void resetValues(Entity e) {
        e.guarding = false;
        gamePanel.keyHandler.enterPressed = false;
    }

    private void endFight() {
        turn = 0;
        action = 0;
        gamePanel.gameState =  player.life <= 0 ? gamePanel.gameOverState : gamePanel.playState;
        if (player.life > 0)
            player.invincible = true;
        player.capacityCooldown = 0;
        player.strength = player.baseStrength;
        player.dexterity = player.baseDexterity;
        sentence = null;
        gamePanel.keyHandler.resetValues();
    }

    public void startFight(Player player, Entity opponent) {
        setPlayer(player);
        setOpponent(opponent);
        gamePanel.gameState = gamePanel.fightingState;
        gamePanel.keyHandler.resetValues();
        sentence = null;
        turn = 0;
        action = 0;
    }




    public void drawFight(Graphics2D g2) {
        this.g2 = g2;
        /*
        if (action == 4) {
            gamePanel.ui.g2D = g2;
            gamePanel.ui.drawInventory(player,true);
        }*/
        //else {
            drawBackground(g2);
            drawEntities(g2);
            if (sentence != null)
                drawChoiceUsed(g2);
            else
                drawChoices(g2);
        //}
    }

    private void drawBackground(Graphics2D g2) {
        g2.drawImage(gamePanel.environmentManager.getBackground(),0,0,960,376,null);
    }

    private void drawEntities(Graphics2D g2) {
        int width = gamePanel.screenWidth;
        int height = gamePanel.screenHeight;

        //Draw player
        g2.drawImage(player.attackRight2, 70,height-360,320,160,null);

        gamePanel.ui.g2D = g2;
        gamePanel.ui.drawPlayerLife();

        //Draw opponent
        int opponentX = width-300;
        int opponentY = 70;
        int opponentWidth = 160;
        g2.drawImage(opponent.image, opponentX, opponentY, opponentWidth, 160, null);


        double oneScale = (double) opponentWidth / opponent.maxLife;
        double hpBarValue = oneScale * opponent.life;
        if (opponent.life <= 0) {
            hpBarValue = 0;
        }

        g2.setColor(new Color(35, 35, 35));
        g2.fillRect(opponentX - 15, opponentY - 16, opponentWidth+10, 30);
        g2.setColor(new Color(255, 0, 30));
        g2.fillRect(opponentX - 10, opponentY - 11, (int) hpBarValue, 20);

    }

    private void drawChoiceUsed(Graphics2D g2) {
        int width = gamePanel.screenWidth;
        int height = gamePanel.screenHeight;
        gamePanel.ui.g2D = g2;
        gamePanel.ui.drawSubWindow(0,height-200,width,200);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
        g2.drawString(sentence, 0 + gamePanel.tileSize, height-200+gamePanel.tileSize);
        if (gamePanel.keyHandler.enterPressed) {
            sentence = null;
            turn ++;
            gamePanel.keyHandler.enterPressed = false;
        }
    }

    private void drawChoices(Graphics2D g2) {

        gamePanel.ui.g2D = g2;
        gamePanel.ui.drawSubWindow(0,height-200,width,200);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));



        g2.drawString("Guard",x0,y1stRow);
        g2.drawString("Attack",x1,y1stRow);
        g2.drawString("Capacity",x2,y1stRow);
        g2.drawString("Run",x3,y2ndRow);
        g2.drawString("Inventory",x4,y2ndRow);



        g2.setColor(Color.ORANGE);
        g2.setStroke(new BasicStroke(3));

        int cursorX = 0;
        int cursorY;
        if (action < 3) cursorY = y1stRow - 50;
        else cursorY = y2ndRow - 50;

        switch (action) {
            case 0 -> cursorX = x0 - 50;
            case 1 -> cursorX = x1 - 50;
            case 2 -> cursorX = x2 - 50;
            case 3 -> cursorX = x3 - 50;
            case 4 -> cursorX = x4 - 50;
        }

        int cursorWidth = gamePanel.tileSize*4;
        int cursorHeight = 90;
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
    }



    public void setAction(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }
}
