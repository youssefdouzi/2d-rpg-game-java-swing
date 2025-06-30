package startingVillage.npcs;

import Main.GamePanel;
import entity.Entity;

import java.util.Random;

public class NPC_OldMan extends Entity {


    public NPC_OldMan(GamePanel gamePanel) {
        super(gamePanel);

        type = type_npc;
        direction = "DOWN";
        speed = 2;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        setDialogue();
    }

    public void getImage() {
        super.getImage("/npc/oldman");
    }
    
    public void setAction() {



//            int goalCol = (gamePanel.player.worldX + gamePanel.player.solidArea.x) / gamePanel.tileSize;
//            int goalRow = (gamePanel.player.worldY + gamePanel.player.solidArea.y) / gamePanel.tileSize;



         if (canLetPlayerEnterDungeon) {


             actionLockCounter++;

             getRandomDirection(120);
         }

    }

    public void speak() {

        if (gamePanel.player.level >= 5 && !canLetPlayerEnterDungeon) {
            canLetPlayerEnterDungeon = true;

            dialogueSet = 1;
        }
        else if (canLetPlayerEnterDungeon) {
            dialogueSet = 2;
        }

        // Do this character specific stuff

        super.speak();
        facePlayer();
    }

    public void setDialogue() {
        dialogues[0][0] = "Hello, bro.";
        dialogues[0][1] = "So you've come to this island to \nfind the legendary treasure?";
        dialogues[0][2] = "I used to be a great wizard but now...\nI'm a bit too old for taking an adventure.";
        dialogues[0][3] = "I'm sorry but I can't let you enter here. \nCome back stronger...";

        dialogues[1][0] = "Oh I already told you that you need \nto become stronger.";
        dialogues[1][1] = "What? How is it possible?! \nYou fought against those slimes?";
        dialogues[1][2] = "And you didn't die. \nI think you're now ready to enter here.";
        dialogues[1][3] = "But be aware, there are creatures inside... \nMay the Force be with you...";

        dialogues[2][0] = "Good luck little adventurer.";
        dialogues[2][1] = "I'll be moving around if you need my help \nI might not be very useful...";
    }

    public void update() {
        if (canLetPlayerEnterDungeon) {
            if (worldX == gamePanel.tileSize * 25 && worldY == gamePanel.tileSize * 18) {
                direction = "UP";
                return;
            }
            searchPath(25, 18);
            super.update();
        }
    }
}
