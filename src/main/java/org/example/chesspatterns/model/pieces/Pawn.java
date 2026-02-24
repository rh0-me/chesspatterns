package org.example.chesspatterns.model.pieces;

import org.example.chesspatterns.model.board.Board;

import java.awt.*;

public class Pawn extends Piece {
    public Pawn(Board board, int col, int row, boolean isWhite) {
        super(board, col, row, isWhite);

    }

    @Override
    public boolean isMovePatternValid(int targetCol, int targetRow) {
        int colDiffAbs = Math.abs(targetCol - this.column);
        int rowDiff = targetRow - this.row;

        // Pawns move forward (direction depends on color)
        int direction = isWhite ? -1 : 1;

        // Standard move: one square forward
        if (colDiffAbs == 0 && rowDiff == direction) {
            return board.getPieceAtLocation(targetCol, targetRow) == null; // Must be empty
        }

        // First move: two squares forward
        if (colDiffAbs == 0 && rowDiff == 2 * direction && isFirstMove) {
            // Both squares must be empty
            return board.getPieceAtLocation(targetCol, targetRow) == null &&
                    board.getPieceAtLocation(targetCol, this.row + direction) == null;
        }

        if (colDiffAbs == 1 && rowDiff == direction) {
            Piece targetPiece = board.getPieceAtLocation(targetCol, targetRow);

            if (targetPiece != null && targetPiece.isWhite != this.isWhite)
                return true;

            Point enPassantTile = board.enPassantTile;
            if (targetPiece == null
                    && enPassantTile != null
                    && enPassantTile.x == targetCol
                    && enPassantTile.y == targetRow )
                return true;
        }

        return false; // Invalid move
    }

    @Override
    public void updatePosition(int col, int row) {
        int direction = isWhite ? -1 : 1;

        if (isFirstMove && Math.abs(row - this.row) == 2) {
            board.enPassantTile = new Point(col, row - direction);
        }
        super.updatePosition(col, row);
    }
}
