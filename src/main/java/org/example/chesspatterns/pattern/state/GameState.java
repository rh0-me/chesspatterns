package org.example.chesspatterns.pattern.state;

import org.example.chesspatterns.core.GameManager;
import org.example.chesspatterns.model.pieces.Piece;

public interface GameState {

    boolean handleMove(GameManager context, int startRow, int startCol, int endRow, int endCol);


    String getStateName();
}
