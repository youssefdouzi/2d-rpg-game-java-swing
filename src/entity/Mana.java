package entity;

public class Mana extends Resource {


    public Mana(int maxQuantity) {
        super(0,maxQuantity);
    }

    public Mana(int quantity, int maxQuantity) {
        super(quantity,maxQuantity);
    }
}
