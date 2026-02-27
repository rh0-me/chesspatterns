package org.example.chesspatterns.pattern.state;

import org.example.chesspatterns.core.GameManager;
import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.model.pieces.Piece;
import org.example.chesspatterns.pattern.command.CastlingCommand;
import org.example.chesspatterns.pattern.command.Command;
import org.example.chesspatterns.pattern.command.MoveCommand;

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
                if (Math.abs(startCol - endCol) == 2
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
