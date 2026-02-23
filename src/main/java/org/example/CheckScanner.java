package org.example;

import org.example.models.Piece;

public class CheckScanner {

    Board board;

    public CheckScanner(Board board) {
        this.board = board;
    }


    public boolean isInCheck(Move move) {
        Piece king = board.findKing(move.piece.isWhite);

        if (king == null) {
            return false; // No king found, should not happen in a valid game
        }

        for (Piece piece : board.pieces) {
            if (piece.isWhite != move.piece.isWhite) { // Check only opponent's pieces
                Move simulatedAttack = new Move(board, piece, king.column, king.row);
                if (board.isValidMoveWithOutCheck(simulatedAttack)) {
                    return true; // King is under attack
                }
            }
        }
        return false; // King is safe
    }
}
