package org.example.models;

import org.example.Board;

public class Knight extends Piece {


    public Knight(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.row = col;
        this.column = row;
        this.isWhite = isWhite;

        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;

        this.sprite = getSprite(isWhite ? "N-W.png" : "N-B.png");
    }
}
