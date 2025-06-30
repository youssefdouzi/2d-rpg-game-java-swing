package startingVillage.npcs;

import Main.GamePanel;
import entity.Entity;
import object.*;

public class NPC_Merchant extends Entity {

    public NPC_Merchant(GamePanel gamePanel) {
        super(gamePanel);

        type = type_npc;
        direction = "DOWN";
        speed = 1;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = gamePanel.tileSize*2-16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        setDialogue();
        setItems();
    }
    @Override
    public void update() {
        super.update();
        worldY -= speed;
    }
    public void getImage() {
        up1 = setup("/npc/merchant_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/merchant_down_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/npc/merchant_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/merchant_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/npc/merchant_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/merchant_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/npc/merchant_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/merchant_down_2", gamePanel.tileSize, gamePanel.tileSize);
    }
    public void setDialogue() {
        dialogues[0][0] = "He he, so you found me. \nI have some good stuff.\nDo you want to trade?";
    }
    public void setItems() {
        inventory.add(new OBJ_Potion_Red(gamePanel,4));
        inventory.add(new OBJ_Key(gamePanel,5));
        inventory.add(new OBJ_Sword_Normal(gamePanel));
        inventory.add(new OBJ_Axe(gamePanel));
        inventory.add(new OBJ_Shield_Blue(gamePanel));
        inventory.add(new OBJ_Shield_Wood(gamePanel));
    }
    public void speak() {
        super.speak();
        gamePanel.gameState = gamePanel.tradeState;
        gamePanel.ui.npc = this;
    }


}
