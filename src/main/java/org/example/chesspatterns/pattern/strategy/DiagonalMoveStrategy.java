package org.example.chesspatterns.pattern.strategy;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.model.pieces.Piece;

public class DiagonalMoveStrategy implements MoveStrategy {
    @Override
    public boolean isValidMove(Board board, int startRow, int startCol, int endRow, int endCol, boolean isWhite) {
        if (Math.abs(startRow - endRow) != Math.abs(startCol - endCol)) {
            return false;
        }
       
        Piece targetPiece = board.getPiece(endRow, endCol);
        if (targetPiece != null && targetPiece.isWhite() == isWhite) {
            return false;
        }

        // Richtungen bestimmen (entweder -1 oder 1)
        int rowDirection = Integer.compare(endRow, startRow);
        int colDirection = Integer.compare(endCol, startCol);

        int currentRow = startRow + rowDirection;
        int currentCol = startCol + colDirection;

        // Den diagonalen Weg ablaufen und auf Blockaden prüfen
        while (currentRow != endRow && currentCol != endCol) {
            if (board.getPiece(currentRow, currentCol) != null) {
                return false; // Eine Figur steht im Weg
            }
            currentRow += rowDirection;
            currentCol += colDirection;
        }

        // Zielfeld prüfen (Eigene Figuren dürfen nicht geschlagen werden)

        return true;
    }
}
