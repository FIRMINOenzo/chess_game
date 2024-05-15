package chess.pieces;

import board.entities.Board;
import chess.entities.ChessPiece;
import chess.enums.Color;

public class King extends ChessPiece {
    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        return new boolean[this.getBoard().getRows()][this.getBoard().getColumns()];
    }
}
