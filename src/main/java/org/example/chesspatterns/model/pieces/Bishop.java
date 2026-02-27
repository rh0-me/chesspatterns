package org.example.chesspatterns.model.pieces;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.pattern.strategy.MoveStrategy;

//public class Bishop extends Piece {
//    public Bishop(Board board, int col, int row, boolean isWhite) {
//        super(board, col, row, isWhite);
//
//    }
//
//    @Override
//    public boolean isMovePatternValid(int newCol, int newRow) {
//        int colDiff = Math.abs(newCol - this.column);
//        int rowDiff = Math.abs(newRow - this.row);
//
//        // Bishops move diagonally, so the absolute difference between columns and rows must be the same
//        return colDiff == rowDiff;
//    }
//
//
//    @Override
//    public boolean moveCollidesWithPieces(int newCol, int newRow) {
//        int colDiff = newCol - this.column;
//        int rowDiff = newRow - this.row;
//
//        int colStep = Integer.signum(colDiff);
//        int rowStep = Integer.signum(rowDiff);
//
//        int steps = Math.abs(colDiff); // or Math.abs(rowDiff), they are the same for a diagonal move
//
//        for (int i = 1; i < steps; i++) {
//            int intermediateCol = this.column + i * colStep;
//            int intermediateRow = this.row + i * rowStep;
//            if (board.getPieceAtLocation(intermediateCol, intermediateRow) != null) {
//                return true; // Collision detected
//            }
//        }
//
//        return false; // No collision
//    }
//
//    @Override
//    public Piece copyWithBoard(Board newBoard) {
//        return new Bishop(newBoard, this.column, this.row, this.isWhite);
//    }
//}


public class Bishop extends Piece {
    public Bishop(boolean isWhite, MoveStrategy moveStrategy) {
        super(isWhite, moveStrategy);
    }

    @Override
    public String getImageName() {
        return isWhite ? "B-W" : "B-B";
    }
}