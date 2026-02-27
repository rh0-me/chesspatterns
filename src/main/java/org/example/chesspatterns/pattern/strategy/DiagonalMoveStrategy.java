package org.example.chesspatterns.pattern.strategy;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.model.pieces.Piece;

public class DiagonalMoveStrategy implements MoveStrategy {
    @Override
    public boolean isValidMove(Board board, int startRow, int startCol, int endRow, int endCol, boolean isWhite) {
        // Check if move is diagonal
        if (Math.abs(startRow - endRow) != Math.abs(startCol - endCol)) {
            return false;
        }
       
        Piece targetPiece = board.getPiece(endRow, endCol);
        if (targetPiece != null && targetPiece.isWhite() == isWhite) {
            return false;
        }

        int rowDirection = Integer.compare(endRow, startRow);
        int colDirection = Integer.compare(endCol, startCol);

        int currentRow = startRow + rowDirection;
        int currentCol = startCol + colDirection;

        // Check for collision
        while (currentRow != endRow && currentCol != endCol) {
            if (board.getPiece(currentRow, currentCol) != null) {
                return false; 
            }
            currentRow += rowDirection;
            currentCol += colDirection;
        }

        return true;
    }
}
