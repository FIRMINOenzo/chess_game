package UI;

import UI.enums.AnsiBackgroundColor;
import UI.enums.AnsiColor;
import UI.exceptions.UIException;
import chess.entities.ChessMatch;
import chess.entities.ChessPiece;
import chess.entities.ChessPosition;
import chess.enums.Color;

import java.util.ArrayList;
import java.util.Scanner;

public class UI {
    public static ChessPosition readChessPosition(Scanner scanner) {
        String userInput = scanner.nextLine().trim().toLowerCase();

        if (userInput.length() != 2) {
            throw new UIException("[Input error]: Input length must be 2.");
        }

        char column = userInput.charAt(0);
        if (column < 'a' || column > 'h') {
            throw new UIException("[Input error]: First character must be a letter from a to h.");
        }

        int row;
        try {
            row = Integer.parseInt(userInput.substring(1, 2));
        } catch (NumberFormatException e) {
            throw new UIException("[Input error]: Second character must be a number from 1 to 8.");
        }

        if (row < 1 || row > 8) {
            throw new UIException("[Input error]: Second character must be a number from 1 to 8.");
        }

        return new ChessPosition(row, column);
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printMatch(ChessMatch chessMatch) {
        UI.printChessBoard(chessMatch.getPieces());
        System.out.println();
        UI.printCapturedPieces(chessMatch.getCapturedPieces());
        System.out.println();
        System.out.println("Turn: " + chessMatch.getTurn());
        System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
        if (chessMatch.getCheck()) {
            System.out.println("CHECK!");
        }
    }

    public static void printChessBoard(ChessPiece[][] chessBoard) {
        System.out.println("  a b c d e f g h");
        for (int i = 0; i < chessBoard.length; i++) {
            System.out.print((8 - i) + " ");

            for (int j = 0; j < chessBoard[i].length; j++) {
                printChessPiece(chessBoard[i][j]);
            }

            System.out.print((8 - i) + "\n");
        }

        System.out.println("  a b c d e f g h");
    }

    public static void printChessBoard(ChessPiece[][] chessBoard, boolean[][] possibleMoves) {
        System.out.println("  a b c d e f g h");
        for (int i = 0; i < chessBoard.length; i++) {
            System.out.print((8 - i) + " ");

            for (int j = 0; j < chessBoard[i].length; j++) {
                printChessPiece(chessBoard[i][j], possibleMoves[i][j]);
            }

            System.out.print((8 - i) + "\n");
        }

        System.out.println("  a b c d e f g h");
    }

    private static void printChessPiece(ChessPiece piece) {
        if (piece == null) {
            System.out.print(AnsiColor.ANSI_WHITE.code() + "-" + AnsiColor.ANSI_RESET.code() + " ");
            return;
        }

        switch (piece.getColor()) {
            case BLACK -> System.out.print(AnsiColor.ANSI_YELLOW.code() + piece + AnsiColor.ANSI_RESET.code());
            case WHITE -> System.out.print(piece);
        }

        System.out.print(" ");
    }

    private static void printChessPiece(ChessPiece piece, Boolean background) {
        if (background) {
            System.out.print(AnsiBackgroundColor.ANSI_BLUE_BACKGROUND.code());
        }

        if (piece == null) {
            System.out.print("-" + AnsiColor.ANSI_RESET.code() + " ");
            return;
        }

        switch (piece.getColor()) {
            case BLACK -> System.out.print(AnsiColor.ANSI_YELLOW.code() + piece + AnsiColor.ANSI_RESET.code());
            case WHITE -> System.out.print(AnsiColor.ANSI_WHITE.code() + piece + AnsiColor.ANSI_RESET.code());
        }

        System.out.print(" ");
    }

    private static void printCapturedPieces(ArrayList<ChessPiece> capturedPieces) {
        ArrayList<ChessPiece> whiteCapturedPieces = new ArrayList<>();
        ArrayList<ChessPiece> blackCapturedPieces = new ArrayList<>();

        for (ChessPiece piece : capturedPieces) {
            if (piece.getColor() == Color.WHITE) {
                whiteCapturedPieces.add(piece);
                continue;
            }

            blackCapturedPieces.add(piece);
        }

        System.out.println("Captured pieces:");
        System.out.println(AnsiColor.ANSI_WHITE.code() + "White: " + whiteCapturedPieces + AnsiColor.ANSI_RESET.code());
        System.out.println(AnsiColor.ANSI_YELLOW.code() + "Black: " + blackCapturedPieces + AnsiColor.ANSI_RESET.code());
    }

}
