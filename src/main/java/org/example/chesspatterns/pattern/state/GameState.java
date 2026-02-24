package org.example.chesspatterns.pattern.state;

import org.example.chesspatterns.core.GameManager;
import org.example.chesspatterns.model.pieces.Piece;

public interface GameState {
    boolean canSelectPiece(Piece piece);

    void nextTurn(GameManager gameManager);

    String getStatusText();

}
