package UI;

import UI.enums.AnsiColor;
import chess.entities.ChessPiece;

public class UI {
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
}
