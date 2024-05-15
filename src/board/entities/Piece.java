package board.entities;

import java.util.Arrays;

public abstract class Piece {
    protected Position position;
    private final Board board;

    public Piece(Board board) {
        this.board = board;
        this.position = null;
    }

    // ao invés de retornar a matriz inteira, retornar apenas um array
    // com as posicoes possiveis;
    public abstract boolean[][] possibleMoves();

    // buscar pelo item dentro do array;
    public boolean possibleMove(Position position) {
        return this.possibleMoves()[position.getRow()][position.getColumn()];
    }

    // length de possibleMoves > 0;
    public boolean isThereAnyPossibleMove() {
        boolean[][] possibleMoves = this.possibleMoves();

        for(boolean[] row: possibleMoves) {
            for(boolean column: row) {
                if (column) return true;
            }
        }

        return false;
    }

    protected Board getBoard() {
        return this.board;
    }
}
