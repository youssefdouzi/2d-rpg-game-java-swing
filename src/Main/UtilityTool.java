package Main;

import entity.*;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {

    public BufferedImage scaleImage(BufferedImage original, int width, int height) {

        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2D = scaledImage.createGraphics();
        g2D.drawImage(original, 0, 0, width, height, null);
        g2D.dispose();

        return scaledImage;

    }


    public Entity stringToObject(String className, GamePanel gamePanel, int amount, boolean opened, Entity loot) {
        return switch (className) {
            case "object.OBJ_Axe" -> new OBJ_Axe(gamePanel);
            case "object.OBJ_Boots" -> new OBJ_Boots(gamePanel);
            case "object.OBJ_Coin_Bronze" -> new OBJ_Coin_Bronze(gamePanel);
            case "object.OBJ_Door" -> new OBJ_Door(gamePanel, opened);
            case "object.OBJ_Chest" -> new OBJ_Chest(gamePanel, loot, opened);
            case "object.OBJ_Heart" -> new OBJ_Heart(gamePanel);
            case "object.OBJ_Key" -> new OBJ_Key(gamePanel, amount);
            case "object.OBJ_ManaCrystal" -> new OBJ_ManaCrystal(gamePanel);
            case "object.OBJ_Potion_Red" -> new OBJ_Potion_Red(gamePanel,amount);
            case "object.OBJ_Shield_Blue" -> new OBJ_Shield_Blue(gamePanel);
            case "object.OBJ_Shield_Wood" -> new OBJ_Shield_Wood(gamePanel);
            case "object.OBJ_Sword_Normal" -> new OBJ_Sword_Normal(gamePanel);
            case "object.OBJ_Magic_Wand" -> new OBJ_Magic_Wand(gamePanel);
            default -> null;
        };
    }

    public Resource stringToResource(String className, int quantity, int maxQuantity) {
        return switch (className) {
            case "entity.Mana" -> new Mana(quantity, maxQuantity);
            case "entity.Ammo" -> new Ammo(quantity, maxQuantity);
            case "entity.Null" -> new Null();
            default -> null;
        };
    }

    public boolean intToBool (int bool) {
        return bool == 0;
    }


    public Capacity StringToCapacity(String capacity, Entity user) {
        return switch (capacity) {
            case "object.OBJ_Fireball" -> new OBJ_Fireball(user.gamePanel, user);
            case "entity.Swiftness" -> new Swiftness(user);
            case "entity.Aura" -> new Aura(user);
            default -> null;
        };
    }

}
