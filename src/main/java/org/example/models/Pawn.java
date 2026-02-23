package org.example.models;

import org.example.Board;

public class Pawn extends Piece {
    public Pawn(Board board, int col, int row, boolean isWhite) {
        super(board, col, row, isWhite);

        this.sprite = getSprite(isWhite ? "P-W.png" : "P-B.png");
    }
}
