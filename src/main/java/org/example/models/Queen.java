package org.example.models;

import org.example.Board;

public class Queen extends Piece {
    public Queen(Board board, int col, int row, boolean isWhite) {
        super(board, col, row, isWhite);
        this.sprite = getSprite(isWhite ? "Q-W.png" : "Q-B.png");
    }
}
