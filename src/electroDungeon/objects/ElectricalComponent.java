package electroDungeon.objects;

import Main.GamePanel;
import entity.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ElectricalComponent extends Entity {
    public boolean powered = false;
    private ElectricalComponent tail;
    private ElectricalComponent head;
    public BufferedImage on1, on2, on3, on4, off1, off2, off3, off4, currentForm;
    private int formCount;
    private int maxFormCount;


    public ElectricalComponent(GamePanel gamePanel, int col, int row) {
        super(gamePanel);
        this.worldX = gamePanel.tileSize * col;
        this.worldY = gamePanel.tileSize * row;
        type = type_obstacle;
        collision = true;
    }



    public void setTail(ElectricalComponent tail) {
        this.tail = tail;
    }

    public void setHead(ElectricalComponent head) {
        this.head = head;
        head.setTail(this);
    }

    public boolean isPowered() {
        return powered;
    }

    public void setPowered(boolean powered) {
        this.powered = powered;
    }

    @Override
    public void update() {
        if (powered) {
            if (head != null)
                if (!head.isPowered() && formCount == maxFormCount)
                    head.setPowered(true);
        }
        else {
            if (head != null)
                if (head.isPowered() && formCount == 0)
                    head.setPowered(false);
        }
    }


    @Override
    public void draw(Graphics2D g2) {

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
        if (canDraw()) {
            g2.drawImage(currentForm, screenX, screenY, null);
        }
    }

    public void setOn1(BufferedImage on1) {
        this.on1 = on1;
    }

    public void setOn2(BufferedImage on2) {
        this.on2 = on2;
    }

    public void setOn3(BufferedImage on3) {
        this.on3 = on3;
    }

    public void setOn4(BufferedImage on4) {
        this.on4 = on4;
    }

    public void setOff1(BufferedImage off1) {
        this.off1 = off1;
    }

    public void setOff2(BufferedImage off2) {
        this.off2 = off2;
    }

    public void setOff3(BufferedImage off3) {
        this.off3 = off3;
    }


    public void setOff4(BufferedImage off4) {
        this.off4 = off4;
    }

    public void setCurrentForm(BufferedImage currentForm) {
        this.currentForm = currentForm;
    }

    public int getFormCount() {
        return formCount;
    }

    public int getMaxFormCount() {
        return maxFormCount;
    }

    public void setCurrentForm(int form) {
        if (powered) {
            switch (form) {
                case 0 -> this.currentForm = on1;
                case 1 -> this.currentForm = on2;
                case 2 -> this.currentForm = on3;
                case 3 -> this.currentForm = on4;
            }
        }
        else {
            switch (form) {
                case 0 -> this.currentForm = off1;
                case 1 -> this.currentForm = off2;
                case 2 -> this.currentForm = off3;
                case 3 -> this.currentForm = off4;
            }
        }


    }

    public void setMaxFormCount(int maxFormCount) {
        this.maxFormCount = maxFormCount;
    }

    public void setFormCount(int formCount) {
        this.formCount = formCount;
    }



    public void setImages(String imagePath) {
        on1 = setup(imagePath + "_on_1", gamePanel.tileSize, gamePanel.tileSize);
        on2 = setup(imagePath + "_on_2", gamePanel.tileSize, gamePanel.tileSize);
        on3 = setup(imagePath + "_on_3", gamePanel.tileSize, gamePanel.tileSize);
        on4 = setup(imagePath + "_on_4", gamePanel.tileSize, gamePanel.tileSize);
        off1 = setup(imagePath + "_off_1", gamePanel.tileSize, gamePanel.tileSize);
        off2 = setup(imagePath + "_off_2", gamePanel.tileSize, gamePanel.tileSize);
        off3 = setup(imagePath + "_off_3", gamePanel.tileSize, gamePanel.tileSize);
        off4 = setup(imagePath + "_off_4", gamePanel.tileSize, gamePanel.tileSize);
        currentForm = off1;
    }

    public ElectricalComponent getTail() {
        return tail;
    }

    public ElectricalComponent getHead() {
        return head;
    }
}
