package chess.entities;

import board.entities.Board;
import board.entities.Piece;
import board.entities.Position;
import chess.enums.Color;
import chess.exceptions.ChessException;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class ChessMatch {
    private final ArrayList<ChessPiece> piecesOnTheBoard = new ArrayList<>();
    private final ArrayList<ChessPiece> capturedPieces = new ArrayList<>();
    private final Board board;
    private Pawn enPassantVulnerable;
    private ChessPiece promoted;
    private Integer turn;
    private Color currentPlayer;
    private Boolean check;
    private Boolean checkmate;

    public ChessMatch() {
        this.turn = 1;
        this.currentPlayer = Color.WHITE;
        this.check = false;
        this.checkmate = false;
        this.enPassantVulnerable = null;
        this.board = new Board(8, 8);

        this.initialSetup();
    }

    private void initialSetup() {
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
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

    public void performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();

        this.validateSourcePosition(source);
        this.validateTargetPosition(source, target);

        ChessPiece capturedPiece = (ChessPiece) this.performMove(source, target);
        ChessPiece movedPiece = (ChessPiece) this.board.piece(target);

        this.promoted = null;
        Integer boardEndIndex = movedPiece.getColor() == Color.WHITE ? 0 : 7;
        if (movedPiece instanceof Pawn && target.getRow() == boardEndIndex) {
            this.promoted = movedPiece;
        }

        if (this.testCheck(this.currentPlayer)) {
            this.undoMove(source, target, capturedPiece);
            throw new ChessException("[Chess Position error]: You cannot put yourself in check.");
        }

        if (this.enPassantVulnerable != null)
            this.enPassantVulnerable.setEnPassantVulnerable(false);

        this.enPassantVulnerable = null;

        if (movedPiece instanceof Pawn
                && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
            this.enPassantVulnerable = (Pawn) movedPiece;
        }

        this.check = this.testCheck(this.opponent(this.currentPlayer));
        this.checkmate = this.testCheckmate(this.opponent(this.currentPlayer));

        if (!this.checkmate) {
            this.nextTurn();
        }
    }

    public boolean[][] possibleMoves(ChessPosition chessPosition) {
        Position position = chessPosition.toPosition();
        this.validateSourcePosition(position);
        return this.board.piece(position).possibleMoves();
    }

    public ChessPiece replacePromotedPiece(String classFirstLetter) {
        if (this.promoted == null)
            throw new ChessException("[Promotion error]: There is no piece to be promoted.");

        if (!classFirstLetter.equals("R") &&
                !classFirstLetter.equals("N") &&
                !classFirstLetter.equals("B") &&
                !classFirstLetter.equals("Q")) {
            throw new ChessException("[Promotion error]: Invalid type selected for promotion.");
        }

        Position promotedPosition = this.promoted.getChessPosition().toPosition();
        Piece removedPawn = board.removePiece(promotedPosition);
        this.piecesOnTheBoard.remove(removedPawn);

        ChessPiece newPromotedPiece = this.createChessPieceByInicial(classFirstLetter, promoted.getColor());
        this.board.placePiece(newPromotedPiece, promotedPosition);
        this.piecesOnTheBoard.add(newPromotedPiece);
        this.promoted = newPromotedPiece;

        return newPromotedPiece;
    }

    private ChessPiece createChessPieceByInicial(String inicial, Color color) {
        ChessPiece createdPiece = null;

        switch (inicial) {
            case "R" -> {
                createdPiece = new Rook(this.board, color);
            }
            case "N" -> {
                createdPiece = new Knight(this.board, color);
            }
            case "B" -> {
                createdPiece = new Bishop(this.board, color);
            }
            default -> {
                createdPiece = new Queen(this.board, color);
            }
        }

        return createdPiece;
    }

    private Piece performMove(Position source, Position target) {
        ChessPiece sourcePiece = (ChessPiece) this.board.removePiece(source);
        Piece targetPiece = this.board.removePiece(target);

        sourcePiece.increaseMoveCount();

        this.board.placePiece(sourcePiece, target);

        if (targetPiece != null) {
            this.piecesOnTheBoard.remove(((ChessPiece) targetPiece));
            this.capturedPieces.add((ChessPiece) targetPiece);
        }

        if (sourcePiece instanceof King) {
            if (target.getColumn() == source.getColumn() + 2) {
                Position rookSourcePosition = new Position(source.getRow(), source.getColumn() + 3);
                Position rookTargetPosition = new Position(source.getRow(), source.getColumn() + 1);
                ChessPiece rook = (ChessPiece) this.board.removePiece(rookSourcePosition);
                this.board.placePiece(rook, rookTargetPosition);
                rook.increaseMoveCount();
            } else if (target.getColumn() == source.getColumn() - 2) {
                Position rookSourcePosition = new Position(source.getRow(), source.getColumn() - 4);
                Position rookTargetPosition = new Position(source.getRow(), source.getColumn() - 1);
                ChessPiece rook = (ChessPiece) this.board.removePiece(rookSourcePosition);
                this.board.placePiece(rook, rookTargetPosition);
                rook.increaseMoveCount();
            }
        }

        if (sourcePiece instanceof Pawn) {
            Integer enPassantRowToCheck = sourcePiece.getColor() == Color.WHITE ? -2 : 2;

            if (target.getRow() == source.getRow() + enPassantRowToCheck) {
                Pawn pawnSourcePiece = (Pawn) sourcePiece;
                this.enPassantVulnerable = pawnSourcePiece;
                pawnSourcePiece.setEnPassantVulnerable(true);
            } else if (targetPiece == null && target.getColumn() != source.getColumn()) {
                targetPiece = (Piece) this.board.removePiece(new Position(
                        this.enPassantVulnerable.getPositionRow(),
                        this.enPassantVulnerable.getPositionColumn()));

                this.piecesOnTheBoard.remove((ChessPiece) targetPiece);
                this.capturedPieces.add((ChessPiece) targetPiece);
            }
        }

        return targetPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece sourcePiece = (ChessPiece) this.board.removePiece(target);
        this.board.placePiece(sourcePiece, source);

        sourcePiece.decreaseMoveCount();

        if (capturedPiece != null) {
            this.board.placePiece(capturedPiece, target);

            this.capturedPieces.remove((ChessPiece) capturedPiece);
            this.piecesOnTheBoard.add((ChessPiece) capturedPiece);
        }

        if (sourcePiece instanceof King) {
            if (target.getColumn() == source.getColumn() + 2) {
                Position rookSourcePosition = new Position(source.getRow(), source.getColumn() + 3);
                Position rookTargetPosition = new Position(source.getRow(), source.getColumn() + 1);
                ChessPiece rook = (ChessPiece) this.board.removePiece(rookTargetPosition);
                this.board.placePiece(rook, rookSourcePosition);
                rook.decreaseMoveCount();
            } else if (target.getColumn() == source.getColumn() - 2) {
                Position rookSourcePosition = new Position(source.getRow(), source.getColumn() - 4);
                Position rookTargetPosition = new Position(source.getRow(), source.getColumn() - 1);
                ChessPiece rook = (ChessPiece) this.board.removePiece(rookTargetPosition);
                this.board.placePiece(rook, rookSourcePosition);
                rook.decreaseMoveCount();
            }
        }

        if (sourcePiece instanceof Pawn) {
            if (this.enPassantVulnerable != null && capturedPiece == null &&
                    target.getColumn() == this.enPassantVulnerable.getPositionColumn()) {
                System.out.println("Desfeito...");
                ChessPiece pawn = (ChessPiece) this.board.removePiece(target);
                Position rigthPawnPosition = new Position(0, 0);

                switch (sourcePiece.getColor()) {
                    case WHITE -> {
                        rigthPawnPosition.setValues(3, target.getColumn());
                    }
                    case BLACK -> {
                        rigthPawnPosition.setValues(4, target.getColumn());
                    }
                }

                this.board.placePiece(pawn, rigthPawnPosition);
            }
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

        List<ChessPiece> opponentPieces = this.piecesByColor(this.opponent(color));

        for (ChessPiece piece : opponentPieces) {
            boolean[][] possibleMoves = piece.possibleMoves();

            if (possibleMoves[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }

        return false;
    }

    private Boolean testCheckmate(Color color) {
        if (!testCheck(color)) {
            return false;
        }

        List<ChessPiece> pieces = this.piecesByColor(color);

        for (ChessPiece piece : pieces) {
            boolean[][] possibleMoves = piece.possibleMoves();

            for (int i = 0; i < possibleMoves.length; i++) {
                for (int j = 0; j < possibleMoves[i].length; j++) {
                    if (possibleMoves[i][j]) {
                        Position source = piece.getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = this.performMove(source, target);
                        Boolean keepInCheck = this.testCheck(color);
                        undoMove(source, target, capturedPiece);

                        if (!keepInCheck)
                            return false;
                    }
                }
            }
        }

        return true;
    }

    private List<ChessPiece> piecesByColor(Color color) {
        return this.piecesOnTheBoard
                .stream()
                .filter(piece -> piece.getColor() == color).toList();
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

    public Boolean getCheckmate() {
        return this.checkmate;
    }

    public ChessPiece getPromoted() {
        return this.promoted;
    }
}
