package entity;

public class Ammo extends Resource {

    public Ammo(int maxQuantity) {
        super(0,maxQuantity);
    }

    public Ammo(int quantity, int maxQuantity) {
        super(quantity,maxQuantity);
    }



}
