package org.example.chesspatterns.pattern.state;

import org.example.chesspatterns.core.GameManager;
import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.model.pieces.Pawn;
import org.example.chesspatterns.model.pieces.Piece;
import org.example.chesspatterns.pattern.command.CastlingCommand;
import org.example.chesspatterns.pattern.command.Command;
import org.example.chesspatterns.pattern.command.MoveCommand;
import org.example.chesspatterns.pattern.command.PromotionCommand;
import org.example.chesspatterns.pattern.factory.PieceType;

import javax.swing.*;

public class WhiteTurnState implements GameState {
    @Override
    public boolean handleMove(GameManager context, int startRow, int startCol, int endRow, int endCol) {
        Board board = context.getBoard();
        Piece pieceToMove = board.getPiece(startRow, startCol);

        if (pieceToMove == null || !pieceToMove.isWhite()) {
            System.out.println("Wrong piece selected. Please select a white piece to move.");
            return false;
        }

        // Is the move valid?
        if (pieceToMove.canMove(board, startRow, startCol, endRow, endCol)) {
            // Does the move put the white king in check? 
            if (!board.wouldMoveCauseCheck(startRow, startCol, endRow, endCol, true)) {

                Command cmd;
                // Promotion
                if (pieceToMove instanceof Pawn && (endRow == 0)) {
                    String[] options = {
                            PieceType.QUEEN.name(),
                            PieceType.ROOK.name(),
                            PieceType.BISHOP.name(),
                            PieceType.KNIGHT.name()};

                    int choice = JOptionPane.showOptionDialog(null,
                            "Congratulations! Your pawn can be promoted. Choose a piece:",
                            "Promotion",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, options, options[0]);

                    // Fallback
                    PieceType selectedType = (choice >= 0) ? PieceType.valueOf(options[choice]) : PieceType.QUEEN;

                    Piece promotedPiece = context.getPieceFactory().createPiece(selectedType, true);

                    cmd = new PromotionCommand(board, startRow, startCol, endRow, endCol, promotedPiece);

                }
                // Castling
                else if (Math.abs(startCol - endCol) == 2
                        && pieceToMove instanceof org.example.chesspatterns.model.pieces.King) {
                    cmd = new CastlingCommand(board, startRow, startCol, endCol);
                }
                // Normal move
                else {
                    cmd = new MoveCommand(board, startRow, startCol, endRow, endCol);
                }

                context.getCommandManager().executeCommand(cmd);

                boolean blackInCheckBeforeMove = board.isKingInCheck(false);
                boolean blackHasMovesBeforeMove = board.hasAnyValidMoves(false);

                if (!blackHasMovesBeforeMove) {
                    if (blackInCheckBeforeMove) {
                        context.setState(new GameOverState("Checkmate! White wins!"));
                    } else {
                        context.setState(new GameOverState("Stalemate! It's a draw!"));
                    }
                } else {

                    context.setState(new BlackTurnState());
                }

                return true;
            }
        }

        System.out.println("Invalid move for this piece.");
        return false;
    }

    @Override
    public String getStateName() {
        return "White's Turn";
    }
}
