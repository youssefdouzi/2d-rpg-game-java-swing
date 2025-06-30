package electroDungeon.objects;

public enum Shape {

    HORIZONTAL_RIGHT(0,17,48,14), HORIZONTAL_LEFT(0,17,48,14),
    VERTICAL_UP(17,0,14,48), VERTICAL_DOWN(17,0,14,48),
    CORNER_UP_LEFT(0,17,48-17,48-17), CORNER_RIGHT_DOWN(0,17,48-17,48-17),
    CORNER_UP_RIGHT(17,17,48-17,48-17), CORNER_LEFT_DOWN(17,17,48-17,48-17),
    CORNER_RIGHT_UP(0,0,48-17,48-17), CORNER_DOWN_LEFT(0,0,48-17,48-17),
    CORNER_LEFT_UP(17,0,48-17,48-17), CORNER_DOWN_RIGHT(17,0,48-17,48-17);

    public final int x, y, width, height;

    @Override
    public String toString() {
        return switch (this) {
            case HORIZONTAL_RIGHT -> "horizontal_right";
            case HORIZONTAL_LEFT -> "horizontal_left";
            case VERTICAL_UP -> "vertical_up";
            case VERTICAL_DOWN -> "vertical_down";
            case CORNER_UP_LEFT -> "corner_up_left";
            case CORNER_RIGHT_DOWN -> "corner_right_down";
            case CORNER_UP_RIGHT -> "corner_up_right";
            case CORNER_LEFT_DOWN -> "corner_left_down";
            case CORNER_RIGHT_UP -> "corner_right_up";
            case CORNER_DOWN_LEFT -> "corner_down_left";
            case CORNER_LEFT_UP -> "corner_left_up";
            case CORNER_DOWN_RIGHT -> "corner_down_right";
        };
    }

    public String direction() {
        return switch (this) {
            case HORIZONTAL_RIGHT, CORNER_RIGHT_DOWN, CORNER_RIGHT_UP -> "LEFT";
            case HORIZONTAL_LEFT, CORNER_LEFT_DOWN, CORNER_LEFT_UP -> "RIGHT";
            case VERTICAL_UP, CORNER_UP_LEFT, CORNER_UP_RIGHT -> "DOWN";
            case VERTICAL_DOWN, CORNER_DOWN_LEFT, CORNER_DOWN_RIGHT -> "UP";
        };
    }

    Shape(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
