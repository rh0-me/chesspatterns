package org.example.chesspatterns.pattern.strategy;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.model.pieces.Piece;

public class LShapeMoveStrategy implements MoveStrategy {

    @Override
    public boolean isValidMove(Board board, int startRow, int startCol, int endRow, int endCol, boolean isWhite) {
        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);

        // Prüfung auf L-Form (2 zu 1 oder 1 zu 2)
        boolean isLShape = (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
        if (!isLShape) {
            return false;
        }

        // Nur prüfen, ob das Zielfeld blockiert ist durch eigene Farbe
        Piece targetPiece = board.getPiece(endRow, endCol);
        if (targetPiece != null && targetPiece.isWhite() == isWhite) {
            return false;
        }

        return true;
    }
}
