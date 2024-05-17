package chess.pieces;

import board.entities.Board;
import board.entities.Position;
import chess.entities.ChessPiece;
import chess.enums.Color;

public class Bishop extends ChessPiece {
    public Bishop(Board board, Color color) {
        super(board, color);
    }
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[this.getBoard().getRows()][this.getBoard().getColumns()];

        Position auxiliaryPosition = new Position(0, 0);

        // nw
        auxiliaryPosition.setValues(this.position.getRow() - 1, this.position.getColumn() - 1);
        while (this.getBoard().positionExists(auxiliaryPosition) && !this.getBoard().thereIsAPiece(auxiliaryPosition)) {
            possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
            auxiliaryPosition.setValues(auxiliaryPosition.getRow() - 1, auxiliaryPosition.getColumn() - 1);
        }

        // ne
        auxiliaryPosition.setValues(this.position.getRow() - 1, this.position.getColumn() + 1);
        while (this.getBoard().positionExists(auxiliaryPosition) && !this.getBoard().thereIsAPiece(auxiliaryPosition)) {
            possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
            auxiliaryPosition.setValues(auxiliaryPosition.getRow() - 1, auxiliaryPosition.getColumn() + 1);
        }

        // sw
        auxiliaryPosition.setValues(this.position.getRow() + 1, this.position.getColumn() - 1);
        while (this.getBoard().positionExists(auxiliaryPosition) && !this.getBoard().thereIsAPiece(auxiliaryPosition)) {
            possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
            auxiliaryPosition.setValues(auxiliaryPosition.getRow() + 1, auxiliaryPosition.getColumn() - 1);
        }

        // se
        auxiliaryPosition.setValues(this.position.getRow() + 1, this.position.getColumn() + 1);
        while (this.getBoard().positionExists(auxiliaryPosition) && !this.getBoard().thereIsAPiece(auxiliaryPosition)) {
            possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
            auxiliaryPosition.setValues(auxiliaryPosition.getRow() + 1, auxiliaryPosition.getColumn() + 1);
        }

        return possibleMoves;
    }
}
