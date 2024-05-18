package chess.enums;

public enum Direction {
    ABOVE(-1, 0),
    BELOW(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    TOP_LEFT(-1, -1),
    TOP_RIGHT(-1, 1),
    BOTTOM_LEFT(1, -1),
    BOTTOM_RIGHT(1, 1);

    private final Integer rowChange;
    private final Integer columnChange;

    Direction(Integer rowChange, Integer columnChange) {
        this.rowChange = rowChange;
        this.columnChange = columnChange;
    }

    public Integer getRowChange() {
        return this.rowChange;
    }

    public Integer getColumnChange() {
        return this.columnChange;
    }
}
