package chess.entities;

import board.entities.Board;
import board.entities.Piece;
import board.entities.Position;
import chess.enums.Color;
import chess.exceptions.ChessException;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
    private Integer turn;
    private Color currentPlayer;
    private Boolean check;
    private Boolean checkMate;
    private final Board board;

    public ChessMatch() {
        this.turn = 0;
        this.currentPlayer = Color.WHITE;
        this.check = false;
        this.checkMate = false;
        this.board = new Board(8, 8);

        this.initialSetup();
    }

    private void initialSetup() {
        this.placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        this.placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        this.placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        this.placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        this.placeNewPiece('d', 1, new King(board, Color.WHITE));

        this.placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        this.placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        this.placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        this.placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        this.placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        this.placeNewPiece('d', 8, new King(board, Color.BLACK));
    }

    private void placeNewPiece(Character column, Integer row, ChessPiece piece) {
        this.board.placePiece(piece, new ChessPosition(row, column).toPosition());
    }

    public ChessPiece[][] getPieces() {
        int boardRows = this.board.getRows();
        int boardColumns = this.board.getColumns();

        ChessPiece[][] chessBoard = new ChessPiece[boardRows][boardColumns];

        for (int i = 0; i < boardRows; i++) {
            for (int j = 0; j < boardColumns; j++) {
                chessBoard[i][j] = (ChessPiece) this.board.piece(i, j);
            }
        }

        return chessBoard;
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();

        validateSourcePosition(source);

        Piece capturedPiece = performMove(source, target);

        return (ChessPiece) capturedPiece;
    }

    private Piece performMove(Position source, Position target) {
        Piece sourcePiece = this.board.removePiece(source);
        Piece targetPiece = this.board.removePiece(target);

        this.board.placePiece(sourcePiece, target);

        return targetPiece;
    }

    private void validateSourcePosition(Position position) {
        if (!this.board.thereIsAPiece(position)) {
            throw new ChessException("[Chess Position error]: There is no piece at informed position.");
        }
    }
}
