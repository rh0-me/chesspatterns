package org.example.chesspatterns.pattern.strategy;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.model.pieces.Piece;

public class KingMoveStrategy implements MoveStrategy {
    @Override
    public boolean isValidMove(Board board, int startRow, int startCol, int endRow, int endCol, boolean isWhite) {
        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);

        // --- Standard-Königszug (1 Feld) ---
        if (rowDiff <= 1 && colDiff <= 1) {
            Piece targetPiece = board.getPiece(endRow, endCol);
            return targetPiece == null || targetPiece.isWhite() != isWhite;
        }

        // --- ROCHADE (2 Felder horizontal) ---
        Piece king = board.getPiece(startRow, startCol);
        if (rowDiff == 0 && colDiff == 2 && !king.hasMoved()) {

            // Geht die Rochade nach rechts (kurz) oder links (lang)?
            int direction = (endCol > startCol) ? 1 : -1;
            int rookCol = (direction == 1) ? 7 : 0; // Wo steht der Turm normalerweise?

            Piece rook = board.getPiece(startRow, rookCol);

            // 1. Prüfen, ob da wirklich ein Turm steht und ob er sich schon bewegt hat
            if (rook == null || rook.hasMoved() || rook.isWhite() != isWhite) {
                return false;
            }

            // 2. Prüfen, ob die Felder ZWISCHEN König und Turm leer sind
            int currentCol = startCol + direction;
            while (currentCol != rookCol) {
                if (board.getPiece(startRow, currentCol) != null) {
                    return false; // Figur steht im Weg!
                }
                currentCol += direction;
            }

            // Hinweis: Die Regel "König darf nicht im Schach stehen oder durchs Schach ziehen"
            // bauen wir später in den GameManager ein, da die Strategy nur reine Bewegungsmuster kennt!
            return true;
        }

        return false;
    }
}
