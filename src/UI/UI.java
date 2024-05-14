package UI;

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
        System.out.print((piece == null ? "-" : piece.toString()) + " ");
    }
}
