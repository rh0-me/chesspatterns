package org.example.models;

import org.example.Board;

public class King extends Piece{
    public King(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.row = row;
        this.column = col;
        this.isWhite = isWhite;

        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;

        this.sprite = getSprite(isWhite ? "K-W.png" : "K-B.png");
    }
}
