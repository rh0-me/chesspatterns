package org.example.chesspatterns.model.pieces;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.pattern.strategy.MoveStrategy;

import java.awt.*;

// Datei: Pawn.java
public class Pawn extends Piece {
    public Pawn(boolean isWhite, MoveStrategy moveStrategy) {
        super(isWhite, moveStrategy);
    }

    @Override
    public String getImageName() {
        return isWhite ? "P-W" : "P-B";
    }
}