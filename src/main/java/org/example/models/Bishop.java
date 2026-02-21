package org.example.models;

import org.example.Board;

public class Bishop extends Piece {
    public Bishop(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.row = col;
        this.column = row;
        this.isWhite = isWhite;

        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;

        this.sprite = getSprite(isWhite ? "B-W.png" : "B-B.png");
    }
}
