package chess.entities;

import board.entities.Board;
import board.entities.Piece;
import board.entities.Position;
import chess.enums.Color;
import chess.exceptions.ChessException;
import chess.pieces.King;
import chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;

public class ChessMatch {
    private Integer turn;
    private Color currentPlayer;
    private Boolean check;
    private Boolean checkMate;
    private final ArrayList<ChessPiece> piecesOnTheBoard = new ArrayList<>();
    private final ArrayList<ChessPiece> capturedPieces = new ArrayList<>();
    private final Board board;

    public ChessMatch() {
        this.turn = 1;
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
        this.piecesOnTheBoard.add(piece);
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

        this.validateSourcePosition(source);
        this.validateTargetPosition(source, target);

        ChessPiece capturedPiece = (ChessPiece) this.performMove(source, target);

        if (this.testCheck(this.currentPlayer)) {
            this.undoMove(source, target, capturedPiece);
            throw new ChessException("[Chess Position error]: You cannot put yourself in check.");
        }

        this.check = this.testCheck(this.opponent(this.currentPlayer));

        this.nextTurn();

        return capturedPiece;
    }

    public boolean[][] possibleMoves(ChessPosition chessPosition) {
        Position position = chessPosition.toPosition();
        this.validateSourcePosition(position);
        return this.board.piece(position).possibleMoves();
    }

    private Piece performMove(Position source, Position target) {
        Piece sourcePiece = this.board.removePiece(source);
        Piece targetPiece = this.board.removePiece(target);

        this.board.placePiece(sourcePiece, target);

        if (targetPiece != null) {
            this.piecesOnTheBoard.remove(((ChessPiece) targetPiece));
            this.capturedPieces.add((ChessPiece) targetPiece);
        }

        return targetPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece) {
        Piece sourcePiece = this.board.removePiece(target);
        this.board.placePiece(sourcePiece, source);

        if (capturedPiece != null) {
            this.board.placePiece(capturedPiece, target);

            this.capturedPieces.remove((ChessPiece) capturedPiece);
            this.piecesOnTheBoard.add((ChessPiece) capturedPiece);
        }
    }

    private void validateSourcePosition(Position position) {
        if (!this.board.thereIsAPiece(position)) {
            throw new ChessException("[Chess Position error]: There is no piece at informed position.");
        }

        if (this.currentPlayer != ((ChessPiece) this.board.piece(position)).getColor()) {
            throw new ChessException("[Chess Position error]: Chosen piece is not yours.");
        }

        if (!this.board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("[Chess Position error]: There is no possible move for this piece.");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!this.board.piece(source).possibleMove(target)) {
            throw new ChessException("[Chess Position error]: Chosen piece cannot move to target position.");
        }
    }

    private void nextTurn() {
        this.turn += 1;
        this.currentPlayer = this.currentPlayer == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king(Color color) {
        return this.piecesOnTheBoard
                .stream()
                .filter(piece -> piece instanceof King && piece.getColor() == color)
                .findFirst()
                .orElseThrow(() -> new ChessException("[Game error]: There is no " + color + " king on board."));
    }

    private Boolean testCheck(Color color) {
        Position kingPosition = this.king(color).getChessPosition().toPosition();

        List<ChessPiece> opponentPieces = this.piecesOnTheBoard
                .stream()
                .filter(piece -> piece.getColor() != color).toList();

        for (ChessPiece piece : opponentPieces) {
            boolean[][] possibleMoves = piece.possibleMoves();

            if (possibleMoves[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }

        return false;
    }

    private Color opponent(Color color) {
        return color == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    public Integer getTurn() {
        return this.turn;
    }

    public Color getCurrentPlayer() {
        return this.currentPlayer;
    }

    public ArrayList<ChessPiece> getCapturedPieces() {
        return this.capturedPieces;
    }

    public Boolean getCheck() {
        return this.check;
    }
}
