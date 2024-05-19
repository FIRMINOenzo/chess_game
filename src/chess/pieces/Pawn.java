package chess.pieces;

import board.entities.Board;
import board.entities.Position;
import chess.entities.ChessPiece;
import chess.enums.Color;
import chess.enums.Direction;

public class Pawn extends ChessPiece {
    private Boolean enPassantVulnerable = false;

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

        // Special move -> En Passant
        this.enPassantChecker(direction, 1, possibleMoves);
        this.enPassantChecker(direction, -1, possibleMoves);
    }

    private void enPassantChecker(Direction direction, Integer columnModification, boolean[][] possibleMoves) {
        Position auxiliaryPosition = new Position(0, 0);

        auxiliaryPosition.setValues(this.position.getRow(),
                this.position.getColumn() + columnModification);
        if (this.getBoard().positionExists(auxiliaryPosition) && this.isThereOpponentPiece(auxiliaryPosition)
                && this.getBoard().piece(auxiliaryPosition) instanceof Pawn) {
            Pawn pawnOnPosition = (Pawn) this.getBoard().piece(auxiliaryPosition);

            if (pawnOnPosition.enPassantVulnerable) {
                possibleMoves[this.position.getRow() + direction.getRowChange()][this.position.getColumn()
                        + columnModification] = true;
            }
        }
    }

    public Boolean getEnPassantVulnerable() {
        return this.enPassantVulnerable;
    }

    public void setEnPassantVulnerable(Boolean vulnerable) {
        this.enPassantVulnerable = vulnerable;
    }

    public Integer getPositionRow() {
        return this.position.getRow();
    }

    public Integer getPositionColumn() {
        return this.position.getColumn();
    }
}
