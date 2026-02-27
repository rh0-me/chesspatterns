package org.example.chesspatterns.model.pieces;

import org.example.chesspatterns.pattern.strategy.MoveStrategy;




public class King extends Piece {
    public King(boolean isWhite, MoveStrategy moveStrategy) {
        super(isWhite, moveStrategy);
    }

    @Override
    public String getImageName() {
        return isWhite ? "K-W" : "K-B";
    }
}