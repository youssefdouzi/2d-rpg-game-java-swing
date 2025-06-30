package electroDungeon.objects;

import Main.GamePanel;
import entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class ElectroModule extends ElectricalComponent {

    List<ElectricalComponent> tail = new ArrayList<ElectricalComponent>();

    public ElectroModule(GamePanel gamePanel, int col, int row) {
        super(gamePanel, col, row);
        setMaxFormCount(120);
        setOff1(setup("/electroDungeon/objects/electro_module_off",gamePanel.tileSize, gamePanel.tileSize));
        setOn1(setup("/electroDungeon/objects/electro_module_on_1",gamePanel.tileSize, gamePanel.tileSize));
        setOn2(setup("/electroDungeon/objects/electro_module_on_2",gamePanel.tileSize, gamePanel.tileSize));
        setOn3(setup("/electroDungeon/objects/electro_module_on_3",gamePanel.tileSize, gamePanel.tileSize));
        collision = true;
        type = type_obstacle;
    }
    @Override
    public void setCurrentForm(int form) {
        switch (form) {
            case 0 -> setCurrentForm(off1);
            case 1 -> setCurrentForm(on1);
            case 2 -> setCurrentForm(on2);
            case 3 -> setCurrentForm(on3);
        }
    }

    @Override
    public void setTail(ElectricalComponent electricalComponent) {
        tail.add(electricalComponent);
    }
    @Override
    public void setPowered(boolean powered) {
        if (powered) {
            for (ElectricalComponent electricalComponent : tail) {
                if (!electricalComponent.isPowered() || electricalComponent.getFormCount() != electricalComponent.getMaxFormCount()) {
                    return;
                }
            }
            this.powered = true;
        }
        else
            this.powered = false;
    }
    @Override
    public void update() {
        super.update();
        if (isPowered() && getFormCount() < getMaxFormCount()) {
            setFormCount(getFormCount() + 1);
        }
        else if (!isPowered() && getFormCount() > 0) {
            setFormCount(getFormCount() - 1);
        }

        if (getFormCount() == 0) {
            setCurrentForm(0);
        }else if (getFormCount() < getMaxFormCount()/3) {
            setCurrentForm(1);
        }
        else if (getFormCount() < 2*getMaxFormCount()/3) {
            setCurrentForm(2);
        }
        else {
            setCurrentForm(3);
        }
    }
}
