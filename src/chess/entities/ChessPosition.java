package chess.entities;

import board.entities.Position;
import chess.exceptions.ChessException;

public class ChessPosition {
    private final Integer row;
    private final Character column;

    public ChessPosition(Integer row, Character column) {
        if ((column < 'a' || column > 'h') || (row < 1 || row > 8)) {
            throw new ChessException("[Chess Position error]: Invalid values passed.");
        }

        this.row = row;
        this.column = column;
    }

    protected Position toPosition() {
        return new Position((8 - this.row), (this.column - 'a'));
    }

    protected static ChessPosition fromPosition(Position position) {
        return new ChessPosition(8 - position.getRow(), (char) ('a' + position.getColumn()));
    }

    public Integer getRow() {
        return this.row;
    }

    public Character getColumn() {
        return this.column;
    }

    @Override
    public String toString() {
        return String.valueOf(this.column) + this.row;
    }
}
