import UI.UI;
import board.entities.Board;
import chess.entities.ChessMatch;
import chess.enums.Color;
import chess.pieces.King;

public class Main {
    public static void main(String[] args) {
        ChessMatch chessMatch = new ChessMatch();

        UI.printChessBoard(chessMatch.getPieces());
    }
}