package chess.pieces;

import board.entities.Board;
import board.entities.Position;
import chess.entities.ChessPiece;
import chess.enums.Color;
import chess.enums.Direction;

public class Knight extends ChessPiece {

    public Knight(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[this.getBoard().getRows()][this.getBoard().getColumns()];

        possibleMoves = this.possibleMovesByDirection(Direction.TOP_LEFT,
                possibleMoves);
        possibleMoves = this.possibleMovesByDirection(Direction.TOP_RIGHT,
                possibleMoves);
        possibleMoves = this.possibleMovesByDirection(Direction.BOTTOM_LEFT,
                possibleMoves);
        possibleMoves = this.possibleMovesByDirection(Direction.BOTTOM_RIGHT,
                possibleMoves);

        return possibleMoves;
    }

    private boolean[][] possibleMovesByDirection(Direction direction, boolean[][] possibleMoves) {
        Position auxiliaryPosition = new Position(0, 0);

        auxiliaryPosition.setValues(this.position.getRow() +
                        (direction.getRowChange() * 2),
                this.position.getColumn() + (direction.getColumnChange()));

        if (this.getBoard().positionExists(auxiliaryPosition) &&
                !this.getBoard().thereIsAPiece(auxiliaryPosition)) {
            possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }

        if (this.getBoard().positionExists(auxiliaryPosition) &&
                this.isThereOpponentPiece(auxiliaryPosition)) {
            possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }

        auxiliaryPosition.setValues(this.position.getRow() +
                        (direction.getRowChange()),
                this.position.getColumn() + (direction.getColumnChange() * 2));

        if (this.getBoard().positionExists(auxiliaryPosition) &&
                !this.getBoard().thereIsAPiece(auxiliaryPosition)) {
            possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }

        if (this.getBoard().positionExists(auxiliaryPosition) &&
                this.isThereOpponentPiece(auxiliaryPosition)) {
            possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }

        return possibleMoves;
    }

    @Override
    public String toString() {
        return "N";
    }

}
