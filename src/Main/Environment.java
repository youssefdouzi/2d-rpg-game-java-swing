package Main;

public enum Environment {
    OUTSIDE_FOREST, OUTSIDE_ROAD, DUNGEON, HOUSE;

    public boolean isOutside() {
        return switch (this) {
            case OUTSIDE_FOREST, OUTSIDE_ROAD -> true;
            case DUNGEON, HOUSE -> false;
        };
    }
}
