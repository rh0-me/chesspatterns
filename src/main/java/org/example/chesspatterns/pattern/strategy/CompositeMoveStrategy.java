package org.example.chesspatterns.pattern.strategy;

import org.example.chesspatterns.model.board.Board;

import java.util.Arrays;
import java.util.List;

public class CompositeMoveStrategy implements MoveStrategy {
    private final List<MoveStrategy> strategies;

    public CompositeMoveStrategy(MoveStrategy... strategies) {
        this.strategies = Arrays.asList(strategies);
    }

    @Override
    public boolean isValidMove(Board board, int startRow, int startCol, int endRow, int endCol, boolean isWhite) {
        for (MoveStrategy strategy : strategies) {
            if (strategy.isValidMove(board, startRow, startCol, endRow, endCol, isWhite)) {
                return true;
            }
        }
        return false;
    }
}
