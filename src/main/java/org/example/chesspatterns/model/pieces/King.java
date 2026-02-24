package org.example.chesspatterns.model.pieces;

import org.example.chesspatterns.model.board.Board;

public class King extends Piece{
    public King(Board board, int col, int row, boolean isWhite) {
        super(board, col, row, isWhite);

    }
    
    @Override
    public boolean isValidMove(int targetCol, int targetRow) {
        if (!super.isValidMove(targetCol, targetRow)) {
            return false;
        }

        int colDiff = Math.abs(targetCol - this.column);
        int rowDiff = Math.abs(targetRow - this.row);

        // King can move one square in any direction
        return (colDiff <= 1 && rowDiff <= 1);
    }
}
