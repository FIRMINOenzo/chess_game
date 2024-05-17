package board.entities;

public abstract class Piece {
    protected Position position;
    private final Board board;

    public Piece(Board board) {
        this.board = board;
        this.position = null;
    }

    public abstract boolean[][] possibleMoves();

    public boolean possibleMove(Position position) {
        return this.possibleMoves()[position.getRow()][position.getColumn()];
    }

    public boolean isThereAnyPossibleMove() {
        boolean[][] possibleMoves = this.possibleMoves();

        for (boolean[] row : possibleMoves) {
            for (boolean column : row) {
                if (column)
                    return true;
            }
        }

        return false;
    }

    protected Board getBoard() {
        return this.board;
    }
}
