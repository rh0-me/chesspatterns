package org.example.models;

import org.example.Board;

public class Rook extends Piece {
    public Rook(Board board, int col, int row, boolean isWhite) {
        super(board, col, row, isWhite);

        this.sprite = getSprite(isWhite ? "R-W.png" : "R-B.png");
    }

    @Override
    public boolean isValidMove(int newCol, int newRow) {
        // Rooks move in straight lines, so either the column or the row must be the same
        return (newCol == this.column || newRow == this.row) && super.isValidMove(newCol, newRow);
    }

    @Override
    public boolean moveCollidesWithPieces(int newCol, int newRow) {
        // Check if there are any pieces in the way of the rook's movement
        if (newCol == this.column) {
            // Moving vertically
            int step = (newRow > this.row) ? 1 : -1;
            for (int r = this.row + step; r != newRow; r += step) {
                if (board.getPieceAtLocation(this.column, r) != null) {
                    return true; // Collision detected
                }
            }
        } else if (newRow == this.row) {
            // Moving horizontally
            int step = (newCol > this.column) ? 1 : -1;
            for (int c = this.column + step; c != newCol; c += step) {
                if (board.getPieceAtLocation(c, this.row) != null) {
                    return true; // Collision detected
                }
            }
        }
        return false; // No collision
    }
}
