import UI.UI;
import UI.exceptions.UIException;
import chess.entities.ChessMatch;
import chess.entities.ChessPosition;
import chess.exceptions.ChessException;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        Scanner scanner = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();

        while (!chessMatch.getCheckmate()) {
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

                chessMatch.performChessMove(sourcePosition, targetPosition);

                if (chessMatch.getPromoted() != null) {
                    while (true) {
                        try {
                            System.out.print("Choose the piece for promotion [R | N | B | Q]: ");
                            String pieceChoose = scanner.nextLine().trim().toUpperCase();
                            chessMatch.replacePromotedPiece(pieceChoose);
                            break;
                        } catch (ChessException exception) {
                            System.out.println(exception.getMessage());
                            scanner.nextLine();
                        }
                    }
                }
            } catch (ChessException | UIException exception) {
                System.out.println(exception.getMessage());
                scanner.nextLine();
            }
        }

        UI.clearScreen();
        UI.printMatch(chessMatch);
    }
}