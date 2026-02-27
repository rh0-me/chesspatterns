package org.example.chesspatterns.pattern.strategy;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.model.pieces.Piece;

public class PawnMoveStrategy implements MoveStrategy {
    @Override
    public boolean isValidMove(Board board, int startRow, int startCol, int endRow, int endCol, boolean isWhite) {
        int direction = isWhite ? -1 : 1;
        int startRowForTwoSteps = isWhite ? 6 : 1; // Standard-Startreihen im Schach

        int rowDiff = endRow - startRow;
        int colDiff = Math.abs(startCol - endCol);
        Piece targetPiece = board.getPiece(endRow, endCol);

        // Normal move (1)
        if (colDiff == 0 && rowDiff == direction) {
            return targetPiece == null; 
        }

        // First move (2)
        if (colDiff == 0 && rowDiff == 2 * direction && startRow == startRowForTwoSteps) {
            Piece intermediatePiece = board.getPiece(startRow + direction, startCol);
            return intermediatePiece == null && targetPiece == null; 
        }

        // Capture
        if (colDiff == 1 && rowDiff == direction) {
            return targetPiece != null && targetPiece.isWhite() != isWhite; 
        }

        return false;
    }
}
