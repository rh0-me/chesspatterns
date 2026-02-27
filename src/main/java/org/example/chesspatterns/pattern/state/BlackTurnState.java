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

public class BlackTurnState implements GameState {
    @Override
    public boolean handleMove(GameManager context, int startRow, int startCol, int endRow, int endCol) {
        Board board = context.getBoard();
        Piece pieceToMove = board.getPiece(startRow, startCol);

        // 1. Prüfung: Gibt es da überhaupt eine Figur und ist sie SCHWARZ?
        if (pieceToMove == null || pieceToMove.isWhite()) {
            System.out.println("Schwarz ist am Zug. Du kannst keine weißen Figuren bewegen!");
            return false;
        }

        // 2. Prüfung (Strategy)
        if (pieceToMove.canMove(board, startRow, startCol, endRow, endCol)) {

            if (!board.wouldMoveCauseCheck(startRow, startCol, endRow, endCol, true)) {
                // 3. Command ausführen
                Command cmd;
                if (pieceToMove instanceof Pawn && (endRow == 7)) {
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
                    Piece promotedPiece = context.getPieceFactory().createPiece(selectedType, false);

                    // 3. PromotionCommand erstellen
                    cmd = new PromotionCommand(board, startRow, startCol, endRow, endCol, promotedPiece);
                } else if (Math.abs(startCol - endCol) == 2
                        && pieceToMove instanceof org.example.chesspatterns.model.pieces.King) {
                    cmd = new CastlingCommand(board, startRow, startCol, endCol);
                } else {
                    cmd = new MoveCommand(board, startRow, startCol, endRow, endCol);
                }

                context.getCommandManager().executeCommand(cmd);


                boolean whiteInCheckBeforeMove = board.isKingInCheck(true);
                boolean whiteHasMovesBeforeMove = board.hasAnyValidMoves(true);

                if (!whiteHasMovesBeforeMove) {
                    if (whiteInCheckBeforeMove) {
                        context.setState(new GameOverState("Schachmatt! Schwarz gewinnt."));
                    } else {
                        context.setState(new GameOverState("Patt! Unentschieden!"));
                    }
                } else {
                    context.setState(new WhiteTurnState());
                }

                // 4. ZUSTANDSWECHSEL! Schwarz ist fertig, Weiß ist dran.
                context.setState(new WhiteTurnState());
                return true;
            }
        }

        System.out.println("Ungültiger Zug für diese Figur.");
        return false;
    }

    @Override
    public String getStateName() {
        return "Schwarz ist am Zug";
    }
}
