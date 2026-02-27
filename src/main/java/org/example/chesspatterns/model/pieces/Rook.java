package org.example.chesspatterns.model.pieces;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.pattern.strategy.MoveStrategy;

public class Rook extends Piece {
    public Rook(boolean isWhite, MoveStrategy strategy) {
        super(isWhite, strategy);
    }

    @Override
    public String getImageName() {
        return isWhite ? "R-W" : "R-B";
    }
}