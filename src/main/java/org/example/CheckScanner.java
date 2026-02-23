package org.example;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.pattern.command.Move;
import org.example.chesspatterns.model.pieces.Piece;

public class CheckScanner {

    Board board;

    public CheckScanner(Board board) {
        this.board = board;
    }


    public boolean isInCheck(Move move) {
        Piece movingPiece = move.piece;

        int oldCol = movingPiece.column;
        int oldRow = movingPiece.row;

        Piece capturedPiece = move.targetPiece;

        if (capturedPiece == null) {
            capturedPiece = board.getPieceAtLocation(move.newCol, move.newRow);
        }

        if (capturedPiece != null) {
            board.pieces.remove(capturedPiece);
        }

        
        
        Piece king = board.findKing(move.piece.isWhite);

        assert king != null : "King should not be null in a valid game";

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
