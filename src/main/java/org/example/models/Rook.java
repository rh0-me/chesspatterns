package org.example.models;

import org.example.Board;

public class Rook extends Piece {
    public Rook(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.row = row;
        this.column = col;
        this.isWhite = isWhite;

        this.xPos = this.column * board.tileSize;
        this.yPos = this.row * board.tileSize;

        this.sprite = getSprite(isWhite ? "R-W.png" : "R-B.png");
    }

}
