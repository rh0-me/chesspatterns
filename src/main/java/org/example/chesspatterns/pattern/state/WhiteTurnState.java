package org.example.chesspatterns.pattern.state;

import org.example.chesspatterns.core.GameManager;
import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.model.pieces.Pawn;
import org.example.chesspatterns.model.pieces.Piece;
import org.example.chesspatterns.pattern.command.CastlingCommand;
import org.example.chesspatterns.pattern.command.Command;
import org.example.chesspatterns.pattern.command.MoveCommand;
import org.example.chesspatterns.pattern.factory.PieceType;

import javax.swing.*;

public class WhiteTurnState implements GameState {
    @Override
    public boolean handleMove(GameManager context, int startRow, int startCol, int endRow, int endCol) {
        Board board = context.getBoard();
        Piece pieceToMove = board.getPiece(startRow, startCol);

        // 1. Prüfung: Gibt es da überhaupt eine Figur und ist sie WEISS?
        if (pieceToMove == null || !pieceToMove.isWhite()) {
            System.out.println("Weiß ist am Zug. Du kannst keine schwarzen Figuren bewegen!");
            return false;
        }

        // 2. Prüfung: Erlaubt die Strategy (Bewegungsregel) der Figur diesen Zug?
        if (pieceToMove.canMove(board, startRow, startCol, endRow, endCol)) {

            if (!board.wouldMoveCauseCheck(startRow, startCol, endRow, endCol, true)) {

                // 3. Command erstellen und ausführen
                Command cmd;
                if (pieceToMove instanceof Pawn && (endRow == 0)) {
                    // 1. Spieler fragen (Auswahl-Dialog)
                    String[] options = {
                            PieceType.QUEEN.name(),
                            PieceType.ROOK.name(),
                            PieceType.BISHOP.name(),
                            PieceType.KNIGHT.name()};

                    int choice = JOptionPane.showOptionDialog(null,
                            "Wähle eine Figur für die Umwandlung:",
                            "Bauernumwandlung",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, options, options[0]);

                    // Fallback, falls der Spieler das Fenster schließt (Standard: Dame)
                    PieceType selectedType = (choice >= 0) ? PieceType.valueOf(options[choice]) : PieceType.QUEEN;


                    // 2. Factory nutzt das Pattern, um die Figur zu erstellen!
                    Piece promotedPiece = context.getPieceFactory().createPiece(selectedType, true);

                    // 3. PromotionCommand erstellen
                    cmd = new PromotionCommand(board, startRow, startCol, endRow, endCol, promotedPiece);
                } else if (Math.abs(startCol - endCol) == 2
                        && pieceToMove instanceof org.example.chesspatterns.model.pieces.King) {
                    cmd = new CastlingCommand(board, startRow, startCol, endCol);
                } else {
                    cmd = new MoveCommand(board, startRow, startCol, endRow, endCol);
                }

                context.getCommandManager().executeCommand(cmd);

                boolean blackInCheckBeforeMove = board.isKingInCheck(false);
                boolean blackHasMovesBeforeMove = board.hasAnyValidMoves(false);

                if (!blackHasMovesBeforeMove) {
                    if (blackInCheckBeforeMove) {
                        context.setState(new GameOverState("Schachmatt! Weiß gewinnt."));
                    } else {
                        context.setState(new GameOverState("Patt! Unentschieden!"));
                    }
                } else {

                    context.setState(new BlackTurnState());
                }


                // 4. ZUSTANDSWECHSEL! Weiß ist fertig, Schwarz ist dran.
                return true;
            }
        }

        System.out.println("Ungültiger Zug für diese Figur.");
        return false;
    }

    @Override
    public String getStateName() {
        return "Weiß ist am Zug";
    }
}
