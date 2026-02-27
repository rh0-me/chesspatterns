package org.example.chesspatterns.pattern.strategy;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.model.pieces.Piece;

public class KingMoveStrategy implements MoveStrategy {
    @Override
    public boolean isValidMove(Board board, int startRow, int startCol, int endRow, int endCol, boolean isWhite) {
        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);

        // Default move 1
        if (rowDiff <= 1 && colDiff <= 1) {
            Piece targetPiece = board.getPiece(endRow, endCol);
            return targetPiece == null || targetPiece.isWhite() != isWhite;
        }

        // Castling
        Piece king = board.getPiece(startRow, startCol);
        if (rowDiff == 0 && colDiff == 2 && !king.hasMoved()) {

            // King or Queen side castling?
            int direction = (endCol > startCol) ? 1 : -1;
            int rookCol = (direction == 1) ? 7 : 0;

            Piece rook = board.getPiece(startRow, rookCol);

            // Has rook moved?
            if (rook == null || rook.hasMoved() || rook.isWhite() != isWhite) {
                return false;
            }

            // Check intermediate
            int currentCol = startCol + direction;
            while (currentCol != rookCol) {
                if (board.getPiece(startRow, currentCol) != null) {
                    return false;
                }
                currentCol += direction;
            }
            return true;
        }
        return false;
    }
}
