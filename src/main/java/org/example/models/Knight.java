package org.example.models;

import org.example.Board;

public class Knight extends Piece {


    public Knight(Board board, int col, int row, boolean isWhite) {
        super(board, col, row, isWhite);
        this.sprite = getSprite(isWhite ? "N-W.png" : "N-B.png");
    }

    @Override
    public boolean isValidMove(int newCol, int newRow) {
        int colDiff = Math.abs(newCol - this.column);
        int rowDiff = Math.abs(newRow - this.row);

        return (colDiff == 2 && rowDiff == 1) || (colDiff == 1 && rowDiff == 2);
    }

}
