package org.example.models;

import org.example.Board;

public class Queen extends Piece {
    public Queen(Board board, int col, int row, boolean isWhite) {
        super(board, col, row, isWhite);
        this.sprite = getSprite(isWhite ? "Q-W.png" : "Q-B.png");
    }

    @Override
    public boolean isValidMove(int newCol, int newRow) {
        int colDiff = Math.abs(newCol - this.column);
        int rowDiff = Math.abs(newRow - this.row);

        return (colDiff == 0 || rowDiff == 0 || colDiff == rowDiff) && super.isValidMove(newCol, newRow);
    }

    @Override
    public boolean moveCollidesWithPieces(int newCol, int newRow) {
        int colDiff = newCol - this.column;
        int rowDiff = newRow - this.row;

        int colStep = Integer.signum(colDiff);
        int rowStep = Integer.signum(rowDiff);

        int steps = Math.max(Math.abs(colDiff), Math.abs(rowDiff)); // Number of steps to move

        for (int i = 1; i < steps; i++) {
            int intermediateCol = this.column + i * colStep;
            int intermediateRow = this.row + i * rowStep;
            if (board.getPieceAtLocation(intermediateCol, intermediateRow) != null) {
                return true; // Collision detected
            }
        }

        return false; // No collision
    }
}
