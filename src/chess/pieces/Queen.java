package chess.pieces;

import board.entities.Board;
import board.entities.Position;
import chess.entities.ChessPiece;
import chess.enums.Color;
import chess.enums.Direction;

public class Queen extends ChessPiece {
        public Queen(Board board, Color color) {
                super(board, color);
        }

        @Override
        public boolean[][] possibleMoves() {
                boolean[][] possibleMoves = new boolean[this.getBoard().getRows()][this.getBoard().getColumns()];

                possibleMoves = this.possibleMovesByDirection(Direction.ABOVE,
                                possibleMoves);
                possibleMoves = this.possibleMovesByDirection(Direction.BELOW,
                                possibleMoves);
                possibleMoves = this.possibleMovesByDirection(Direction.LEFT,
                                possibleMoves);
                possibleMoves = this.possibleMovesByDirection(Direction.RIGHT,
                                possibleMoves);
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
                                (direction.getRowChange()),
                                this.position.getColumn() + (direction.getColumnChange()));

                while (this.getBoard().positionExists(auxiliaryPosition) &&
                                !this.getBoard().thereIsAPiece(auxiliaryPosition)) {
                        possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
                        auxiliaryPosition.setValues(auxiliaryPosition.getRow() +
                                        (direction.getRowChange()),
                                        auxiliaryPosition.getColumn() + (direction.getColumnChange()));
                }

                if (this.getBoard().positionExists(auxiliaryPosition) &&
                                this.isThereOpponentPiece(auxiliaryPosition)) {
                        possibleMoves[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
                }

                return possibleMoves;
        }
}
