package org.example.models;

import org.example.Board;

public class Bishop extends Piece {
    public Bishop(Board board, int col, int row, boolean isWhite) {
        super(board, col, row, isWhite);

        this.sprite = getSprite(isWhite ? "B-W.png" : "B-B.png");
    }
}
