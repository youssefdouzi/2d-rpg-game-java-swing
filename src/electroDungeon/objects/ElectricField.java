package electroDungeon.objects;

import Main.GamePanel;
import entity.Entity;
import entity.Player;


public class ElectricField extends ElectricalComponent {


    public ElectricField(GamePanel gamePanel, int col, int row) {
        super(gamePanel, col, row);
        setMaxFormCount(30);
        setOn1(setup("/electroDungeon/objects/electric_gate_up_on_3",gamePanel.tileSize, gamePanel.tileSize));
        setOff1(setup("/electroDungeon/objects/electric_field_off_1",gamePanel.tileSize, gamePanel.tileSize));
        setOff2(setup("/electroDungeon/objects/electric_field_off_2",gamePanel.tileSize, gamePanel.tileSize));
        setOff3(setup("/electroDungeon/objects/electric_field_off_3",gamePanel.tileSize, gamePanel.tileSize));
        setOff4(setup("/electroDungeon/objects/electric_field_off_4",gamePanel.tileSize, gamePanel.tileSize));
        type = type_interactive;
        collision = true;
    }

    @Override
    public void setCurrentForm(int form) {
        switch (form) {
            case 0 -> this.currentForm = on1;
            case 1 -> this.currentForm = off1;
            case 2 -> this.currentForm = off2;
            case 3 -> this.currentForm = off3;
            case 4 -> this.currentForm = off4;
        }
    }

    @Override
    public void update() {
        super.update();

        if (isPowered()) {
            if (getHead() != null)
                if (!getHead().isPowered())
                    getHead().setPowered(true);
            type = type_invisible;
            collision = false;
            setCurrentForm(0);
            return;
        }
        else {
            if (getHead() != null)
                if (getHead().isPowered())
                    getHead().setPowered(false);
            type = type_interactive;
            collision = true;
            if (getFormCount() < getMaxFormCount()) {
                setFormCount(getFormCount() + 1);
            }
            else {
                setFormCount(0);
            }
        }
        if (getFormCount() < getMaxFormCount()/4)
            setCurrentForm(1);
        else if (getFormCount() < getMaxFormCount()/4 * 2)
            setCurrentForm(2);
        else if (getFormCount() < getMaxFormCount()/4 * 3)
            setCurrentForm(3);
        else
            setCurrentForm(4);

    }

    @Override
    public void interact(Entity entity) {
        if (!(entity instanceof Player)) {
            return;
        }
        if (isPowered())
            return;
        damagePlayer(1);
        switch (entity.direction) {
            case "DOWN" -> entity.worldY -= 30;
            case "UP" -> entity.worldY += 30;
            case "LEFT" -> entity.worldX += 30;
            case "RIGHT" -> entity.worldX -= 30;
        }
        gamePanel.keyHandler.resetValues();
    }

}

