import UI.UI;
import UI.exceptions.UIException;
import board.entities.Board;
import board.exceptions.BoardException;
import chess.entities.ChessMatch;
import chess.entities.ChessPiece;
import chess.entities.ChessPosition;
import chess.enums.Color;
import chess.exceptions.ChessException;
import chess.pieces.King;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        Scanner scanner = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();

        while (true) {
            try {
                UI.clearScreen();
                UI.printMatch(chessMatch);

                System.out.print("\nSource position: ");
                ChessPosition sourcePosition = UI.readChessPosition(scanner);

                boolean[][] possibleMoves = chessMatch.possibleMoves(sourcePosition);

                UI.clearScreen();
                UI.printChessBoard(chessMatch.getPieces(), possibleMoves);

                System.out.print("\nTarget position: ");
                ChessPosition targetPosition = UI.readChessPosition(scanner);

                ChessPiece capturedPiece = chessMatch.performChessMove(sourcePosition, targetPosition);
            } catch (ChessException | UIException exception) {
                System.out.println(exception.getMessage());
                scanner.nextLine();
            } catch (BoardException exception) {
                System.out.println("wwww");
            }
        }
    }
}