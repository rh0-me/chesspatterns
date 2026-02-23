package org.example.models;

import org.example.Board;

public class Knight extends Piece {


    public Knight(Board board, int col, int row, boolean isWhite) {
        super(board, col, row, isWhite);
        this.sprite = getSprite(isWhite ? "N-W.png" : "N-B.png");
    }
}
