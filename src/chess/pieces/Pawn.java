package chess.pieces;

import board.entities.Board;
import board.entities.Position;
import chess.entities.ChessPiece;
import chess.enums.Color;
import chess.enums.Direction;

public class Pawn extends ChessPiece {
    public Pawn(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[this.getBoard().getRows()][this.getBoard().getColumns()];
        Direction direction = this.getColor() == Color.WHITE ? Direction.ABOVE : Direction.BELOW;

        this.possibleMovesByDirection(direction, possibleMoves);

        return possibleMoves;
    }

    private void possibleMovesByDirection(Direction direction, boolean[][] possibleMoves) {
        Position auxiliaryPosition = new Position(0, 0);

        auxiliaryPosition.setValues(
                this.position.getRow() + (direction.getRowChange()),
                this.position.getColumn());

        if (this.getBoard().positionExists(auxiliaryPosition) && !this.getBoard().thereIsAPiece(auxiliaryPosition)) {
            possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;

            // second position in first move
            if (this.getMoveCount() == 0) {
                auxiliaryPosition.setValues(
                        auxiliaryPosition.getRow() + (direction.getRowChange()),
                        auxiliaryPosition.getColumn());

                if (this.getBoard().positionExists(auxiliaryPosition)
                        && !this.getBoard().thereIsAPiece(auxiliaryPosition)) {
                    possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
                }
            }
        }

        // top or bottom left
        auxiliaryPosition.setValues(
                this.position.getRow() + direction.getRowChange(),
                this.position.getColumn() - 1);

        if (this.getBoard().positionExists(auxiliaryPosition) && this.isThereOpponentPiece(auxiliaryPosition)) {
            possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }

        // top or bottom right
        auxiliaryPosition.setValues(
                this.position.getRow() + direction.getRowChange(),
                this.position.getColumn() + 1);

        if (this.getBoard().positionExists(auxiliaryPosition) && this.isThereOpponentPiece(auxiliaryPosition)) {
            possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }
    }
}
