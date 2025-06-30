package entity;


import java.util.*;

public class LootTable {
    private Map<Entity, Integer> dropRates;
    private Random rand = new Random();

    public LootTable(List<Entity> entities, List<Integer> dropsRates) {
        this.dropRates = new HashMap<>();
        for (int i = 0; i < entities.size(); i++) {
            dropRates.put(entities.get(i), dropsRates.get(i));
        }
    }

    public LootTable(List<Object> objects) {
    }


    public Entity dropRandomItem() {
        int r = rand.nextInt(100);
        Iterator<Entity> it = dropRates.keySet().iterator();
        Entity drop = it.next();
        int dropRate = dropRates.get(drop);
        if (r < dropRate) {
            return drop;
        } else {
            while (it.hasNext()) {
                drop = it.next();
                dropRate += dropRates.get(drop);
                if (r < dropRate) {
                    return (drop);
                }
            }
        }
        return null;
    }
}
