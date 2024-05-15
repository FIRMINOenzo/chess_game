package chess.pieces;

import board.entities.Board;
import chess.entities.ChessPiece;
import chess.enums.Color;

public class Rook extends ChessPiece {
    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        return new boolean[this.getBoard().getRows()][this.getBoard().getColumns()];
    }
}
