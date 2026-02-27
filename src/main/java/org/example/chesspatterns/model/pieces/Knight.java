package org.example.chesspatterns.model.pieces;

import org.example.chesspatterns.pattern.strategy.MoveStrategy;




public class Knight extends Piece {

    public Knight(boolean isWhite, MoveStrategy moveStrategy) {
        super(isWhite, moveStrategy);
    }

    @Override
    public String getImageName() {
        return isWhite ? "N-W" : "N-B";
    }
}  