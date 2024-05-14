package chess.entities;

import board.entities.Board;
import board.entities.Position;
import chess.enums.Color;
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
}
