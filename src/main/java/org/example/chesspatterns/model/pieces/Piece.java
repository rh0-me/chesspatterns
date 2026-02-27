package org.example.chesspatterns.model.pieces;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.pattern.strategy.MoveStrategy;

public abstract class Piece {
    protected boolean isWhite;
    protected boolean hasMoved = false;
    protected MoveStrategy moveStrategy;


    public Piece(boolean isWhite, MoveStrategy moveStrategy) {
        this.isWhite = isWhite;
        this.moveStrategy = moveStrategy;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public boolean canMove(Board board, int startRow, int startCol, int endRow, int endCol) {
        return moveStrategy.isValidMove(board, startRow, startCol, endRow, endCol, isWhite);
    }

    public abstract String getImageName();

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}
