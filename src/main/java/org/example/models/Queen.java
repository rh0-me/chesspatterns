package org.example.models;

import org.example.Board;

public class Queen extends Piece {
    public Queen(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.row = col;
        this.column = row;
        this.isWhite = isWhite;

        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;

        this.sprite = getSprite(isWhite ? "Q-W.png" : "Q-B.png");
    }
}
