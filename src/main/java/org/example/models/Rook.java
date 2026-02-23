package org.example.models;

import org.example.Board;

public class Rook extends Piece {
    public Rook(Board board, int col, int row, boolean isWhite) {
        super(board, col, row, isWhite);

        this.sprite = getSprite(isWhite ? "R-W.png" : "R-B.png");
    }

}
