package org.example.chesspatterns.model.pieces;

import org.example.chesspatterns.model.board.Board;

public class Queen extends Piece {
    public Queen(Board board, int col, int row, boolean isWhite) {
        super(board, col, row, isWhite);
    }

    @Override
    public boolean isMovePatternValid(int newCol, int newRow) {
        int colDiff = Math.abs(newCol - this.column);
        int rowDiff = Math.abs(newRow - this.row);

        return (colDiff == 0 || rowDiff == 0 || colDiff == rowDiff);
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

    @Override
    public Piece copyWithBoard(Board newBoard) {
        return new Queen(newBoard, this.column, this.row, this.isWhite);
    }
}
