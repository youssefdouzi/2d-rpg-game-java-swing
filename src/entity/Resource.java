package entity;

public class Resource {
    private int quantity;
    private int maxQuantity;

    public Resource(int quantity, int maxQuantity) {
        this.quantity = quantity;
        this.maxQuantity = maxQuantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int quantity) {
        this.maxQuantity = quantity;
    }

    public void use(int quantity) {
        this.quantity -= quantity;
    }

    public void replenish() {
        quantity = maxQuantity;
    }

    public void replenish(int quantity) {
        if (this.quantity + quantity > maxQuantity)
            this.quantity = maxQuantity;
        else
            this.quantity += quantity;
    }

    @Override
    public String toString() {
        return quantity + "/" + maxQuantity;
    }
}
