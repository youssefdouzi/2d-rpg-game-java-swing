package electroDungeon.objects;

import Main.GamePanel;
import entity.Entity;

public class ElectricGate extends ElectricalComponent{

    public ElectricGate(GamePanel gamePanel, int col, int row) {
        super(gamePanel, col, row);
        setMaxFormCount(30);
        setOff1(setup("/electroDungeon/objects/electric_gate_up_off",gamePanel.tileSize, gamePanel.tileSize));
        setOn1(setup("/electroDungeon/objects/electric_gate_up_on_1",gamePanel.tileSize, gamePanel.tileSize));
        setOn2(setup("/electroDungeon/objects/electric_gate_up_on_2",gamePanel.tileSize, gamePanel.tileSize));
        setOn3(setup("/electroDungeon/objects/electric_gate_up_on_3",gamePanel.tileSize, gamePanel.tileSize));

    }
    @Override
    public void setCurrentForm(int form) {
        switch (form) {
            case 0 -> this.currentForm = off1;
            case 1 -> this.currentForm = on1;
            case 2 -> this.currentForm = on2;
            case 3 -> this.currentForm = on3;
        }
    }

    @Override
    public void update() {
        super.update();

        if (isPowered()) {
            type = type_invisible;
            collision = false;
            if (getFormCount() < getMaxFormCount()) {
                setFormCount(getFormCount() + 1);
            }
        }
        else {
            type = type_obstacle;
            collision = true;
            setFormCount(0);
        }

        if (getFormCount() < getMaxFormCount()/4)
            setCurrentForm(0);
        else if (getFormCount() < getMaxFormCount()/4 * 2)
            setCurrentForm(1);
        else if (getFormCount() < getMaxFormCount()/4 * 3)
            setCurrentForm(2);
        else
            setCurrentForm(3);

    }



}
