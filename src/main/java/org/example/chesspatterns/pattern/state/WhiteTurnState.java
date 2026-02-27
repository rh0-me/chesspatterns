package org.example.chesspatterns.pattern.state;

import org.example.chesspatterns.core.GameManager;
import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.model.pieces.Piece;
import org.example.chesspatterns.pattern.command.CastlingCommand;
import org.example.chesspatterns.pattern.command.Command;
import org.example.chesspatterns.pattern.command.MoveCommand;

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


                if (Math.abs(startCol - endCol) == 2
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
