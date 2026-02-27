package org.example.chesspatterns.pattern.strategy;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.model.pieces.Piece;

public interface MoveStrategy {
    boolean isValidMove(Board board, int startRow, int startCol, int endRow, int endCol, boolean isWhite);
}
