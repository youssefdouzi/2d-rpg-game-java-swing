package entity;

public interface Capacity {

    public boolean canUseCapacity();
    public void useCapacity(Entity target);
    public void endCapacity();
    public String capacityUsed();

}
