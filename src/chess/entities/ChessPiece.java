package chess.entities;

import board.entities.Board;
import board.entities.Piece;
import board.entities.Position;
import chess.enums.Color;

public abstract class ChessPiece extends Piece {
    private final Color color;
    private Integer moveCount;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
        this.moveCount = 0;
    }

    public Color getColor() {
        return this.color;
    }

    public Integer getMoveCount() {
        return this.moveCount;
    }

    public void increaseMoveCount() {
        this.moveCount += 1;
    }

    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece chessPiece = (ChessPiece) this.getBoard().piece(position);

        return chessPiece != null && chessPiece.getColor() != this.color;
    }

    @Override
    public String toString() {
        return this
                .getClass()
                .getSimpleName()
                .substring(0, 1)
                .toUpperCase();
    }
}

