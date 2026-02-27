package org.example.chesspatterns.model.pieces;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.pattern.strategy.MoveStrategy;

//public class Knight extends Piece {
//
//
//    public Knight(Board board, int col, int row, boolean isWhite) {
//        super(board, col, row, isWhite);
//    }
//
//    @Override
//    public boolean isMovePatternValid(int newCol, int newRow) {
//        int colDiff = Math.abs(newCol - this.column);
//        int rowDiff = Math.abs(newRow - this.row);
//
//        return (colDiff == 2 && rowDiff == 1) || (colDiff == 1 && rowDiff == 2);
//    }
//
//    @Override
//    public Piece copyWithBoard(Board newBoard) {
//        return new Knight(newBoard, this.column, this.row, this.isWhite);
//    }
//
//}


public class Knight extends Piece {

    public Knight(boolean isWhite, MoveStrategy moveStrategy) {
        super(isWhite, moveStrategy);
    }

    @Override
    public String getImageName() {
        return isWhite ? "N-W" : "N-B";
    }
}  