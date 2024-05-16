package chess.pieces;

import board.entities.Board;
import board.entities.Position;
import chess.entities.ChessPiece;
import chess.enums.Color;

public class King extends ChessPiece {
    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[this.getBoard().getRows()][this.getBoard().getColumns()];

        Position auxiliaryPosition = new Position(0, 0);

        // above
        auxiliaryPosition.setValues(this.position.getRow() - 1, this.position.getColumn());
        if (this.getBoard().positionExists(auxiliaryPosition) && this.canMove(auxiliaryPosition)) {
            possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }

        // below
        auxiliaryPosition.setValues(this.position.getRow() + 1, this.position.getColumn());
        if (this.getBoard().positionExists(auxiliaryPosition) && this.canMove(auxiliaryPosition)) {
            possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }

        // right
        auxiliaryPosition.setValues(this.position.getRow(), this.position.getColumn() + 1);
        if (this.getBoard().positionExists(auxiliaryPosition) && this.canMove(auxiliaryPosition)) {
            possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }

        // left
        auxiliaryPosition.setValues(this.position.getRow(), this.position.getColumn() - 1);
        if (this.getBoard().positionExists(auxiliaryPosition) && this.canMove(auxiliaryPosition)) {
            possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }

        // nw
        auxiliaryPosition.setValues(this.position.getRow() - 1, this.position.getColumn() - 1);
        if (this.getBoard().positionExists(auxiliaryPosition) && this.canMove(auxiliaryPosition)) {
            possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }

        // ne
        auxiliaryPosition.setValues(this.position.getRow() - 1, this.position.getColumn() + 1);
        if (this.getBoard().positionExists(auxiliaryPosition) && this.canMove(auxiliaryPosition)) {
            possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }

        // sw
        auxiliaryPosition.setValues(this.position.getRow() + 1, this.position.getColumn() - 1);
        if (this.getBoard().positionExists(auxiliaryPosition) && this.canMove(auxiliaryPosition)) {
            possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }

        // se
        auxiliaryPosition.setValues(this.position.getRow() + 1, this.position.getColumn() + 1);
        if (this.getBoard().positionExists(auxiliaryPosition) && this.canMove(auxiliaryPosition)) {
            possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }

        return possibleMoves;
    }

    private Boolean canMove(Position position) {
        ChessPiece chessPiece = (ChessPiece) this.getBoard().piece(position);
        return chessPiece == null || chessPiece.getColor() != this.getColor();
    }
}   
