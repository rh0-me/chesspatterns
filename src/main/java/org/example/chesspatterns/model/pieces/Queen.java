package org.example.chesspatterns.model.pieces;

import org.example.chesspatterns.pattern.strategy.MoveStrategy;


public class Queen extends Piece {
    public Queen(boolean isWhite, MoveStrategy moveStrategy) {
        super(isWhite, moveStrategy);
    }

    @Override
    public String getImageName() {
        return isWhite ? "Q-W" : "Q-B";
    }
}