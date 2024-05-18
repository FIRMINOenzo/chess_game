package chess.pieces;

import board.entities.Board;
import board.entities.Position;
import chess.entities.ChessPiece;
import chess.enums.Color;
import chess.enums.Direction;

public class King extends ChessPiece {
    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[this.getBoard().getRows()][this.getBoard().getColumns()];

        this.possibleMovesByDirection(Direction.ABOVE, possibleMoves);
        this.possibleMovesByDirection(Direction.BELOW, possibleMoves);
        this.possibleMovesByDirection(Direction.LEFT, possibleMoves);
        this.possibleMovesByDirection(Direction.RIGHT, possibleMoves);
        this.possibleMovesByDirection(Direction.TOP_LEFT, possibleMoves);
        this.possibleMovesByDirection(Direction.TOP_RIGHT, possibleMoves);
        this.possibleMovesByDirection(Direction.BOTTOM_LEFT, possibleMoves);
        this.possibleMovesByDirection(Direction.BOTTOM_RIGHT, possibleMoves);

        return possibleMoves;
    }

    private void possibleMovesByDirection(Direction direction, boolean[][] possibleMoves) {
        Position auxiliaryPosition = new Position(0, 0);

        auxiliaryPosition.setValues(
                this.position.getRow() + (direction.getRowChange()),
                this.position.getColumn() + (direction.getColumnChange()));

        if (this.getBoard().positionExists(auxiliaryPosition) && this.canMove(auxiliaryPosition)) {
            possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
            auxiliaryPosition.setValues(
                    auxiliaryPosition.getRow() + (direction.getRowChange()),
                    auxiliaryPosition.getColumn() + (direction.getColumnChange()));
        }

        int columnModification = direction == Direction.LEFT ? 4 : 3;

        this.calculateRookCastling(direction, columnModification, possibleMoves);
    }

    private Boolean canMove(Position position) {
        ChessPiece chessPiece = (ChessPiece) this.getBoard().piece(position);
        return chessPiece == null || chessPiece.getColor() != this.getColor();
    }

    private Boolean rookAvailableToCastling(Position position) {
        ChessPiece piece = (ChessPiece) this.getBoard().piece(position);
        return piece instanceof Rook &&
                piece.getColor() == this.getColor() &&
                piece.getMoveCount() == 0;
    }

    private void calculateRookCastling(Direction direction, Integer columnModification, boolean[][] possibleMoves) {
        if ((direction != Direction.LEFT && direction != Direction.RIGHT) || this.getMoveCount() > 0) {
            return;
        }

        Position auxiliaryPosition = new Position(0, 0);
        auxiliaryPosition.setValues(
                this.position.getRow(),
                this.position.getColumn() + (direction.getColumnChange() * columnModification));

        if (this.rookAvailableToCastling(auxiliaryPosition)) {
            for (int i = (columnModification - 1); i > 0; i--) {
                auxiliaryPosition.setValues(
                        this.position.getRow(),
                        this.position.getColumn() + (direction.getColumnChange() * i));

                if (!this.getBoard().positionExists(auxiliaryPosition)) {
                    continue;
                }

                if (this.getBoard().piece(auxiliaryPosition) != null) {
                    return;
                }
            }
        }
        // erro aqui quando for verificar o lado esquerdo;
        possibleMoves[this.position.getRow()][this.position.getColumn() + (direction.getColumnChange() * 2)] = true;
    }
}
