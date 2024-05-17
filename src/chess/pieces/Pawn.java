package chess.pieces;

import board.entities.Board;
import board.entities.Position;
import chess.entities.ChessPiece;
import chess.enums.Color;

public class Pawn extends ChessPiece {
    public Pawn(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        return this.possibleMovesByColor(this.getColor());
    }

    private boolean[][] possibleMovesByColor(Color color) {
        Integer rowModification = color == Color.WHITE ? -1 : +1;
        boolean[][] possibleMoves = new boolean[this.getBoard().getRows()][this.getBoard().getColumns()];
        Position auxiliaryPosition = new Position(0, 0);

        // first position
        auxiliaryPosition.setValues(this.position.getRow() + rowModification, this.position.getColumn());
        if (this.getBoard().positionExists(auxiliaryPosition) && !this.getBoard().thereIsAPiece(auxiliaryPosition)) {
            possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;

            // second position in first move
            if (this.getMoveCount() == 0) {
                auxiliaryPosition.setValues(this.position.getRow() + (rowModification * 2), this.position.getColumn());
                if (this.getBoard().positionExists(auxiliaryPosition) && !this.getBoard().thereIsAPiece(auxiliaryPosition)) {
                    possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
                }
            }
        }

        // nw
        auxiliaryPosition.setValues(this.position.getRow() + rowModification, this.position.getColumn() - 1);
        if (this.getBoard().positionExists(auxiliaryPosition) && this.isThereOpponentPiece(auxiliaryPosition)) {
            possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }

        // ne
        auxiliaryPosition.setValues(this.position.getRow() + rowModification, this.position.getColumn() + 1);
        if (this.getBoard().positionExists(auxiliaryPosition) && this.isThereOpponentPiece(auxiliaryPosition)) {
            possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }

        return possibleMoves;
    }
}
