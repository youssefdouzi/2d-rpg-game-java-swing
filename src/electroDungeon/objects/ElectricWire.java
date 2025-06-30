package electroDungeon.objects;

import Main.CollisionChecker;
import Main.GamePanel;
import entity.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ElectricWire extends ElectricalComponent {

    private Shape shape;
    private final CollisionChecker collisionChecker;

    public ElectricWire(GamePanel gamePanel, int col, int row, Shape shape) {
        super(gamePanel, col, row);
        collisionChecker = new CollisionChecker(gamePanel);
        setShape(shape);
        setMaxFormCount(30);
    }

    public Shape getShape() {
        return shape;
    }

    @Override
    public void setImages(String imagePath) {
        setOn1(setup(imagePath + "_on_1", gamePanel.tileSize, gamePanel.tileSize));
        setOn2(setup(imagePath + "_on_2", gamePanel.tileSize, gamePanel.tileSize));
        setOn3(setup(imagePath + "_on_3", gamePanel.tileSize, gamePanel.tileSize));
        setOn4(setup(imagePath + "_on_4", gamePanel.tileSize, gamePanel.tileSize));
        BufferedImage base = setup(imagePath + "_off", gamePanel.tileSize, gamePanel.tileSize);
        setOff1(base);
        setOff2(base);
        setOff3(base);
        setOff4(base);
        setCurrentForm(base);
    }

    @Override
    public void setCurrentForm(int form) {
        switch (form) {
            case 0 -> this.currentForm = off1;
            case 1 -> this.currentForm = on2;
            case 2 -> this.currentForm = on3;
            case 3 -> this.currentForm = on4;
        }
    }



    private void setShape(Shape shape) {
        this.shape = shape;
        setImages("/electroDungeon/objects/" + shape + "_wire");
        solidArea = new Rectangle(shape.x, shape.y, shape.width, shape.height);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    @Override
    public void update() {
        super.update();
        if (getTail() == null) {
            Entity entity1 = collisionChecker.getEntity(this, shape.direction());
            if (entity1 instanceof Lumix l) {
                if (!l.getPowerSource().isPowered())
                    l.setPowered(true);
                if (l.getPoweredCount() == 180) {
                    switch (shape.direction()) {
                        case "UP" -> l.setLocation(worldX, worldY - gamePanel.tileSize);
                        case "DOWN" -> l.setLocation(worldX, worldY + gamePanel.tileSize);
                        case "LEFT" -> l.setLocation(worldX - gamePanel.tileSize, worldY);
                        case "RIGHT" -> l.setLocation(worldX + gamePanel.tileSize, worldY);
                    }
                }
                if (!isPowered()) {
                    setPowered(true);
                }
            }
            else if (isPowered()) {
                setPowered(false);
            }
        }
        if (isPowered()) {
            if (getFormCount() < getMaxFormCount()) {
                setFormCount(getFormCount() + 1);
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
        else {
            setFormCount(0);
        }



    }


}
