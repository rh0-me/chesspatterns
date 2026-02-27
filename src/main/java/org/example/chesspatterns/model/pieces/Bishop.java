package org.example.chesspatterns.model.pieces;

import org.example.chesspatterns.pattern.strategy.MoveStrategy;


public class Bishop extends Piece {
    public Bishop(boolean isWhite, MoveStrategy moveStrategy) {
        super(isWhite, moveStrategy);
    }

    @Override
    public String getImageName() {
        return isWhite ? "B-W" : "B-B";
    }
}